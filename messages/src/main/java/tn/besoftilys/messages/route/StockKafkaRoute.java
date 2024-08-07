package tn.besoftilys.messages.route;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tn.besoftilys.messages.config.WebSocketMessageHandler;
import tn.besoftilys.messages.entity.MessageDTO;
import tn.besoftilys.messages.entity.Message;
import tn.besoftilys.messages.service.IMessage;

import java.nio.charset.Charset;
import java.util.Date;

import static org.apache.camel.LoggingLevel.ERROR;


@Component
public class StockKafkaRoute extends RouteBuilder {
    final String KAFKA_ENDPOINT = "kafka:%s?brokers=localhost:29092" + "&groupId=760bf933-5b8c-47b0-bd30-48151cae99e7";
    final
    String KAFKA_ENDPOINT2="kafka:stock-message-2" + "?brokers=localhost:29092" + "&groupId=b725f72-3292-4cec-a55b-bb7cb80dcc44";
    final String HTTP_ENDPOINT = "http://localhost:8092/api/messages/process";
    @Autowired
    IMessage iMessage;
    @Autowired
    WebSocketMessageHandler webSocketMessageHandler;
    @Override
    public void configure() throws Exception {
        fromF(KAFKA_ENDPOINT, "stock-message")
                .log(ERROR, "[${header.kafka.OFFSET}] [${body}]")
                .throttle(3) //protect  from overloading
                .process(exchange -> {
                    byte[] contentTypeBytes = exchange.getIn().getHeader("Content-Type", byte[].class);
                    String contentType = new String(contentTypeBytes, Charset.defaultCharset());
                    System.out.println("Content-Type: " + contentType);
                    String messageDestination = exchange.getIn().getHeader(KafkaConstants.KEY, String.class);
                    System.out.println("Kafka Key: " + messageDestination); // Print the key in the processing step
                    Object body = exchange.getIn().getBody();
                    int messageSize = 0;
                    String bodyString = "";
                    if (body instanceof String) {
                        messageSize = ((String) body).length();
                        bodyString = (String) body;
                        System.out.println("Message size: " + messageSize + " bytes");
                    }else if (body instanceof byte[]) {
                        messageSize = ((byte[]) body).length;
                        bodyString = new String((byte[]) body, Charset.defaultCharset());
                        System.out.println(messageSize);
                    } else {
                        // Handle non-String messages (e.g., byte arrays)
                        System.out.println("Message is not a String.");
                    }
                    Date comingDate = new Date();
                    iMessage.saveMessage(new Message(messageSize, contentType, messageDestination, comingDate));

                    exchange.getIn().setHeader(Exchange.CONTENT_TYPE, contentType);
                    exchange.getIn().setBody(body);
                    exchange.getIn().setHeader("messageDestination", messageDestination);

                    // Create DTO with all message information
                    MessageDTO messageDTO = new MessageDTO(contentType, messageDestination, messageSize, bodyString, comingDate);

                    // Broadcast the message via WebSocket
                    webSocketMessageHandler.broadcastMessage(messageDTO.getBody());

                })
                .to(HTTP_ENDPOINT)
                .process(exchange -> {
                    // Get the transformed message from the HTTP response
                    String transformedMessage = exchange.getIn().getBody(String.class);
                    exchange.getIn().setBody(transformedMessage);
                    // Broadcast the transformed message via WebSocket

                    webSocketMessageHandler.broadcastTransformedMessage(transformedMessage);
                })
                .toF(KAFKA_ENDPOINT2, "stock-message-2");
    }

}
