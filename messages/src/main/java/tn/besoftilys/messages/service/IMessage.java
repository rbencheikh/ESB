package tn.besoftilys.messages.service;

import org.springframework.http.ResponseEntity;
import tn.besoftilys.messages.entity.Message;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IMessage {
    Message saveMessage(Message message);
    Map<String, Long> countMessagesByContentType();
    String getContentType(String messageBody);

    ResponseEntity<List<Message>>getAllMessages();
    ResponseEntity<?>countAllMessages();

}
