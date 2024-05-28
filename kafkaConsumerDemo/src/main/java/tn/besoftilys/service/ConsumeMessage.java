package tn.besoftilys.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class ConsumeMessage {

    private static final String TOPIC_NAME = "stock-message-2";
    private final KafkaConsumer<String, String> consumer;
    private final Set<String> desiredKeys;
    private final RestTemplate restTemplate;
    private final String targetUrl;

    public ConsumeMessage(String bootstrapServers, String groupId, Set<String> desiredKeys, String targetUrl) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("group.id", groupId);
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());

        this.consumer = new KafkaConsumer<>(props);
        this.desiredKeys = new HashSet<>(desiredKeys);
        this.restTemplate = new RestTemplate();
        this.targetUrl = targetUrl;
    }

    public boolean sendDesiredKey(String key) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(key, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(targetUrl + "/key", request, String.class);

        System.out.println("Response from microservice: " + response.getBody());

        return response.getStatusCode().is2xxSuccessful();
    }

    public void startConsuming() {
        consumer.subscribe(Collections.singletonList(TOPIC_NAME));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, String> record : records) {
                if (record.key() != null && desiredKeys.contains(record.key())) {
                    System.out.printf("Received message (key: %s, value: %s)\n", record.key(), record.value());
                    // Process the message as needed
                }
            }

            consumer.commitSync();
        }
    }
}
