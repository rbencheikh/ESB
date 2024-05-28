package tn.besoftilys.messages.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tn.besoftilys.messages.entity.Message;
import tn.besoftilys.messages.service.IMessage;
import java.util.Date;

@Component
public class FileMoveRoute extends RouteBuilder {
    final String HTTP_ENDPOINT = "http://localhost:8092/api/messages/processFile";
    @Autowired
    IMessage iMessage;
    @Override
    public void configure() throws Exception {
        from("file:///C://Users//rbencheikh//Desktop//Input")
                .process(exchange -> {
                    // Get content type
                    String contentType = iMessage.getContentType(exchange.getIn().getBody(String.class));
                    exchange.getMessage().setHeader(Exchange.CONTENT_TYPE, contentType);
                    System.out.println(contentType);
                    Object body = exchange.getIn().getBody();
                    // Get file size
                    int fileSize = Math.toIntExact(exchange.getIn().getHeader(Exchange.FILE_LENGTH, Long.class));
                    System.out.println("File size: " + fileSize + " bytes");
                    //set current date
                    Date comingDate = new Date();
                    System.out.println(comingDate);
                    //set destination
                    String messageDestination = "Output";
                    //save in database
                    iMessage.saveMessage(new Message(fileSize, contentType, messageDestination, comingDate));
                })
                .log("${headers}")
                .log("${body}")
                .to(HTTP_ENDPOINT)
                .to("file:///C://Users//rbencheikh//Desktop//Output");
    }

}
