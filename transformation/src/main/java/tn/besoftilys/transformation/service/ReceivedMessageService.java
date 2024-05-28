package tn.besoftilys.transformation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;
import tn.besoftilys.transformation.dto.MessageDto;

import java.util.Iterator;
import java.util.Map;


@Service
public class ReceivedMessageService implements IReceivedMessage {
    private final ObjectMapper objectMapper;
    public ReceivedMessageService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    public String transformMessage(MessageDto messageDto, String key) throws JsonProcessingException {
        String contentType = messageDto.getContentType().toLowerCase();
        String body = messageDto.getBody();

        // If contentType and key are the same, return the body as is
        if (contentType.contains(key.toLowerCase())) {
            return body;
        }

        if (contentType.contains("json")) {
            // Parse JSON input
            JsonNode jsonNode = objectMapper.readTree(body);

            // Check the target format specified by 'key'
            if (key.equalsIgnoreCase("xml")) {
                // Convert JSON to XML
                JSONObject jsonObject = new JSONObject(jsonNode.toString());
                return XML.toString(jsonObject);
            }else if (key.equalsIgnoreCase("text")) {
                // Convert JSON to text
                return processNode(jsonNode);
            }
        } else if (contentType.contains("xml")) {
            try {
                // Create XmlMapper
                XmlMapper xmlMapper = new XmlMapper();

                // Read XML string to JsonNode
                JsonNode jsonNode = xmlMapper.readTree(body.getBytes());

                // Check the target format specified by 'key'
                if (key.equalsIgnoreCase("json")) {
                    // Convert XML to JSON string
                    return objectMapper.writeValueAsString(jsonNode);
                }else if (key.equalsIgnoreCase("text")) {
                    // Convert XML to text
                    return processNode(jsonNode);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        // If contentType or key doesn't match expected values, return the body as is
        return body;
    }
    private String processNode(JsonNode node) {
        StringBuilder result = new StringBuilder();

        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                result.append(entry.getKey()).append(":\n");
                result.append(processNode(entry.getValue()));
            }
        } else if (node.isArray()) {
            Iterator<JsonNode> elements = node.elements();
            while (elements.hasNext()) {
                JsonNode element = elements.next();
                result.append(processNode(element));
            }
        } else {
            result.append(node.asText()).append("\n");
        }

        return result.toString();
    }
}
