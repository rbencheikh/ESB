package tn.besoftilys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tn.besoftilys.service.ConsumeMessage;

@SpringBootApplication
public class KafkaConsumerDemoApplication {

    public static void main(String[] args) {
        String bootstrapServers = "localhost:29092";
        String groupId = "b725f72-3292-4cec-a55b-bb7cb80dcc44";
        String desiredKey = "app_2";
        ConsumeMessage consumeMessage = new ConsumeMessage(bootstrapServers,groupId,desiredKey);
        consumeMessage.startConsuming();
    }

}
