package tn.besoftilys.transformation.controller;

import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
@RestController
public class TestController {
    @PostMapping("/processJson")
    public Map<String, String> processJson(@RequestBody String jsonString) throws Exception {
        // Create an ObjectMapper instance for JSON processing
        ObjectMapper mapper = new ObjectMapper();

        // Parse the JSON string into a JsonNode tree
        JsonNode rootNode = mapper.readTree(jsonString);

        // Create a map to store key-value pairs
        Map<String, String> keyValuePairs = new HashMap<>();

        // Process the root node and store values in the map
        processNode(rootNode, "", keyValuePairs);

        // Return all key-value pairs
        return keyValuePairs;
    }

    private void processNode(JsonNode node, String parentKey, Map<String, String> keyValuePairs) {
        if (node.isTextual()) {
            // If the node is textual, add the parent key and text value to the map
            keyValuePairs.put(parentKey, node.asText());
        } else if (node.isObject()) {
            // If the node is an object, iterate over its fields
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                String key = entry.getKey();
                JsonNode value = entry.getValue();
                processNode(value, parentKey.isEmpty() ? key : parentKey + "." + key, keyValuePairs);
            }
        } else if (node.isArray()) {
            // If the node is an array, iterate over its elements
            Iterator<JsonNode> elements = node.elements();
            int index = 0;
            while (elements.hasNext()) {
                JsonNode element = elements.next();
                processNode(element, parentKey + "[" + index + "]", keyValuePairs);
                index++;
            }
        }
    }
}
