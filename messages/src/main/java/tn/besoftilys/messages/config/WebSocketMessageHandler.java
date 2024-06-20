package tn.besoftilys.messages.config;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
@Component
public class WebSocketMessageHandler extends TextWebSocketHandler {
    private static final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // Handle incoming messages if needed
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    public void broadcastMessage(Object message) {
        synchronized (sessions) {
            String messageJson;
            try {
                messageJson = objectMapper.writeValueAsString(message);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            for (WebSocketSession session : sessions) {
                try {
                    session.sendMessage(new TextMessage(messageJson));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void broadcastTransformedMessage(String transformedMessage) {
        synchronized (sessions) {
            for (WebSocketSession session : sessions) {
                try {
                    session.sendMessage(new TextMessage(transformedMessage));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
