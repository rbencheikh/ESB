package tn.besoftilys.transformation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.besoftilys.transformation.dto.MessageDto;
import tn.besoftilys.transformation.service.IReceivedMessage;

import java.util.Set;


@RestController
@RequestMapping("/api/messages")
public class ReceivedMessageController {
@Autowired
    IReceivedMessage iReceivedMessage;
    private String receivedKey;
    @PostMapping("/key")
    public ResponseEntity<String> receiveKey(@RequestBody String key) {
        System.out.println("Received Key: " + key);
        // Store the key for later use
        this.receivedKey = key;
        return new ResponseEntity<>("Key received: " + key, HttpStatus.OK);
    }
    @PostMapping("/{process}")
    public ResponseEntity<String> receiveMessage(@PathVariable String process,@RequestBody String body, @RequestHeader(HttpHeaders.CONTENT_TYPE) String contentType) throws JsonProcessingException {
        // Print received headers and body
        System.out.println("Received Content-Type: " + contentType);
        System.out.println("Received Body: " + body);

        MessageDto messageDto = new MessageDto();
        messageDto.setBody(body);
        messageDto.setContentType(contentType);

        // Transform the message
        String transformedMessage = iReceivedMessage.transformMessage(messageDto,receivedKey);


        // Return a response
        return new ResponseEntity<>(transformedMessage, HttpStatus.OK);
    }


    @PostMapping("/processFile")
    public ResponseEntity<String> processFile(@RequestBody String fileBody, @RequestHeader("Content-Type") String contentType) throws Exception {
        // Log received headers and body
        System.out.println("Received Content-Type: " + contentType);
        System.out.println("Received File Body: " + fileBody);

        // Process the file body and content type
       Set<String> processedContent= iReceivedMessage.processData(fileBody,contentType);

        // Log the processed content
        System.out.println("Processed File Body: " + processedContent);

        return ResponseEntity.ok(processedContent.toString());
    }


}
