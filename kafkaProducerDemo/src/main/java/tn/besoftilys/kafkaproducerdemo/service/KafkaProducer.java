package tn.besoftilys.kafkaproducerdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private static final String TOPIC = "stock-message";
    @Autowired
    private KafkaTemplate<String,String>  kafkaTemplate;

    public void sendMesaage(String message){
        kafkaTemplate.send(TOPIC,message);
    }
}
