package tn.besoftilys.messages.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.besoftilys.messages.entity.Message;
import tn.besoftilys.messages.service.IMessage;

import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
    @Autowired
    IMessage iMessage;

    @GetMapping("/count-by-content-type")
    public ResponseEntity<Map<String, Long>> countByContentType() {
        Map<String, Long> counts = iMessage.countMessagesByContentType();
        return ResponseEntity.ok(counts);
    }

    @GetMapping("/getAllMessages")
    ResponseEntity<List<Message>>getAllMessages(){
        return iMessage.getAllMessages();
    }

    @GetMapping("/countAllMessages")
    public ResponseEntity<?> countAllMessages() {
        return iMessage.countAllMessages();
    }

}
