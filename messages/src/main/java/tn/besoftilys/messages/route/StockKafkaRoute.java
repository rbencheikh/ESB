package tn.besoftilys.messages.route;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


import static org.apache.camel.LoggingLevel.ERROR;


@Component
public class StockKafkaRoute extends RouteBuilder {
    final String KAFKA_ENDPOINT = "kafka:%s?brokers=localhost:29092";
    @Override
    public void configure() throws Exception {
        fromF(KAFKA_ENDPOINT, "stock-message")
                .log(ERROR, "[${header.kafka.OFFSET}] [${body}]")
        ;
    }

}
