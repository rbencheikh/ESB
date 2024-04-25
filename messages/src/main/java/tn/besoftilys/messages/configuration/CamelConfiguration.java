package tn.besoftilys.messages.configuration;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
public class CamelConfiguration {
    public static final String RABBIT_URI = "rabbitmq:amq.direct?queue=message&routingKey=%s&autoDelete=false";
    @Bean
    public ConnectionFactory rabbitConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        try {
            factory.newConnection(); // Attempt connection
            Logger.getLogger(CamelConfiguration.class.getName()).info("Connected to RabbitMQ successfully!");
        } catch (Exception e) {
            Logger.getLogger(CamelConfiguration.class.getName()).severe("Failed to connect to RabbitMQ: " + e.getMessage());
        }
        return factory;
    }

}
