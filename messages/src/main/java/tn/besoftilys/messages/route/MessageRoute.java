package tn.besoftilys.messages.route;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tn.besoftilys.messages.configuration.CamelConfiguration;
import tn.besoftilys.messages.service.MessageService;
import static org.apache.camel.LoggingLevel.ERROR;


@Component
public class MessageRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        fromF(CamelConfiguration.RABBIT_URI, "message")
                .log(ERROR, "Received message: ${body}")
                .toF(CamelConfiguration.RABBIT_URI, "message")
                .log(ERROR, "Sent message: ${body}")
        ;
    }


}
