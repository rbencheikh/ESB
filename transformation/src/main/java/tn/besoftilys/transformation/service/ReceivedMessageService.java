package tn.besoftilys.transformation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;
import tn.besoftilys.transformation.dto.MessageDto;


@Service
public class ReceivedMessageService implements IReceivedMessage {
    private final ObjectMapper objectMapper;
    public ReceivedMessageService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String transformMessage(MessageDto messageDto) throws JsonProcessingException {
        if (messageDto.getContentType().toLowerCase().contains("json")) {
            // Parse JSON input
            JsonNode jsonNode = objectMapper.readTree(messageDto.getBody());

            // Convert JSON to XML
            JSONObject jsonObject = new JSONObject(jsonNode.toString());
            return XML.toString(jsonObject);
        } else if (messageDto.getContentType().toLowerCase().contains("xml")) {
            try {
                // Create XmlMapper
                XmlMapper xmlMapper = new XmlMapper();

                // Read XML string to JsonNode
                JsonNode jsonNode = xmlMapper.readTree(messageDto.getBody().getBytes());

                // Create ObjectMapper
                ObjectMapper objectMapper = new ObjectMapper();

                // Convert JsonNode to JSON string
                return objectMapper.writeValueAsString(jsonNode);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return messageDto.getBody();
        }

    }
}
