package tn.besoftilys.messages.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.besoftilys.messages.entity.Message;
import tn.besoftilys.messages.repository.MessageRepository;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.StringReader;
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

    @Override
    public String getContentType(String messageBody) {
        messageBody = messageBody.trim(); // Remove leading/trailing whitespace

        // Check for JSON structure (without parsing)
        if (messageBody.startsWith("{") && messageBody.endsWith("}") &&
                messageBody.indexOf(':') != -1 && messageBody.indexOf(',') != -1) {
            return "application/json";
        }

        // Check for JavaScript function definition
        if (messageBody.startsWith("(") && messageBody.endsWith(")") &&
                (messageBody.contains("function ") || messageBody.matches("^[^\\s]*\\([^\\)]*\\)$"))) {
            return "application/javascript";
        }

        // Check for HTML structure
        if (messageBody.startsWith("<") && messageBody.endsWith(">") &&
                messageBody.toLowerCase().contains("<html")) {
            return "text/html";
        }

        // Check for XML structure (basic check)
        if (messageBody.startsWith("<") && messageBody.endsWith(">")) {
            return "application/xml";
        }

        // Default for unknown content type
        return "text/plain";
    }

    @Override
    public ResponseEntity<List<Message>> getAllMessages() {
        try{
            List<Message> messages = messageRepository.findAll();
            if (messages.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                return new ResponseEntity<>(messages,HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
