package tn.besoftilys.messages.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.besoftilys.messages.entity.Message;
import tn.besoftilys.messages.repository.MessageRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageService implements IMessage{
    @Autowired
    MessageRepository messageRepository;
    @Override
    public Message saveMessage(Message message) {
       return messageRepository.save(message);
    }

    @Override
    public Map<String, Long> countMessagesByContentType() {
        List<String> contentTypes = Arrays.asList("application/json", "application/javascript", "application/xml", "text/html","text/plain");
        Map<String, Long> counts = new HashMap<>();

        for (String contentType : contentTypes) {
            Long count = messageRepository.countByContentType(contentType);
            counts.put(contentType, count);
        }

        return counts;
    }
}
