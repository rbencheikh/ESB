package tn.besoftilys.service;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;


import java.time.Duration;
import java.util.Collections;
import java.util.Properties;


public class ConsumeMessage {
    private static final String TOPIC_NAME = "stock-message-2";
    private final Consumer<String, String> consumer;
    private final String desiredKey;

    public ConsumeMessage(String bootstrapServers, String groupId, String desiredKey) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("group.id", groupId);
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());

        this.consumer = new KafkaConsumer<>(props);
        this.desiredKey = desiredKey;

    }
    public void startConsuming() {
        consumer.subscribe(Collections.singletonList(TOPIC_NAME));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, String> record : records) {
                if ( record.key()!=null && record.key().equals(desiredKey)) {
                    System.out.printf("Received message (key: %s, value: %s)\n", record.key(), record.value());
                }
            }

            consumer.commitSync();
        }
    }
}
