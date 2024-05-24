package tn.besoftilys.messages.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileMoveRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("file:///C://Users//rbencheikh//Desktop//Input")
                .log("${headers}")
                .log("${body}")
                .to("file:///C://Users//rbencheikh//Desktop//Output");
    }
}
