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
    @PostMapping
    public ResponseEntity<String> receiveMessage(@RequestBody String body, @RequestHeader(HttpHeaders.CONTENT_TYPE) String contentType) throws JsonProcessingException {
        // Print received headers and body
        System.out.println("Received Content-Type: " + contentType);
        System.out.println("Received Body: " + body);

        MessageDto messageDto = new MessageDto();
        messageDto.setBody(body);
        messageDto.setContentType(contentType);

        // Transform the message
        String transformedMessage = iReceivedMessage.transformMessage(messageDto);


        // Return a response
        return new ResponseEntity<>(transformedMessage, HttpStatus.OK);
    }


}
