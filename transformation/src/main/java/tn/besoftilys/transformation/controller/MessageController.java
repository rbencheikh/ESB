package tn.besoftilys.transformation.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @PostMapping
    public ResponseEntity<String> receiveMessage(@RequestBody String body, @RequestHeader(HttpHeaders.CONTENT_TYPE) String contentType) {
        // Print received headers and body
        System.out.println("Received Content-Type: " + contentType);
        System.out.println("Received Body: " + body);

        // Return a response
        return new ResponseEntity<>("Message received successfully", HttpStatus.OK);
    }
}
