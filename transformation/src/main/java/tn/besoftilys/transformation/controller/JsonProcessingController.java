package tn.besoftilys.transformation.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping
public class JsonProcessingController {

    @PostMapping(value = "/process", consumes = {"application/json", "application/xml"})
    public Set<String> processData(@RequestBody String data, @RequestHeader("Content-Type") String contentType) throws Exception {
        ObjectMapper mapper;

        // Determine the appropriate mapper based on the content type
        if (contentType.equals("application/xml")) {
            mapper = new XmlMapper();
        } else {
            mapper = new ObjectMapper();
        }

        // Parse the input data into a JsonNode tree
        JsonNode rootNode = mapper.readTree(data);

        // Create a set to store unique values
        Set<String> uniqueValues = new HashSet<>();

        // Process the root node and store values in the set
        processNode(rootNode, uniqueValues);

        // Return the set of unique values
        return uniqueValues;
    }

    private void processNode(JsonNode node, Set<String> uniqueValues) {
        if (node.isValueNode()) {
            // If the node is a value node (textual, numeric, boolean, etc.), add its value to the set
            uniqueValues.add(node.asText());
        } else if (node.isObject()) {
            // If the node is an object, iterate over its fields
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                String key = entry.getKey();
                uniqueValues.add(key);  // Add the key to the set
                JsonNode value = entry.getValue();
                processNode(value, uniqueValues);
            }
        } else if (node.isArray()) {
            // If the node is an array, iterate over its elements
            Iterator<JsonNode> elements = node.elements();
            while (elements.hasNext()) {
                JsonNode element = elements.next();
                processNode(element, uniqueValues);
            }
        }
    }
}
