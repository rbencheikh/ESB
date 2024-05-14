package tn.besoftilys.kafkaproducerdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@Service
public class KafkaProducer {
    private static final String TOPIC = "stock-message";
    @Autowired
    private KafkaTemplate<String,String>  kafkaTemplate;

    public void sendMesaage(String message , String destination){
        Message<String> kafkaMessage = MessageBuilder
                .withPayload(message)
                .setHeader(KafkaHeaders.TOPIC, TOPIC)
                .setHeader(KafkaHeaders.RECEIVED_KEY, destination)
                .build();

        kafkaTemplate.send(kafkaMessage);
    }
}
