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

//    @PostMapping("/{processFile}")
//    public String processFile(@PathVariable String processFile , @RequestBody String fileContent) {
//        // Process the file content here
//        // Example processing: Convert content to uppercase
//        String processedContent = fileContent.toUpperCase();
//        System.out.println(processedContent);
//        return processedContent;
//    }



}
