package tn.besoftilys.kafkaproducerdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tn.besoftilys.kafkaproducerdemo.service.KafkaProducer;

@RestController
public class KafkaController {
    @Autowired
    private KafkaProducer kafkaProducer;

    @PostMapping("/sendMessage")
    public void sendMessageToKafka(@RequestBody String message){
        kafkaProducer.sendMesaage(message);
    }
}
