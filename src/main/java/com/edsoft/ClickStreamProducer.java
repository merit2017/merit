package com.edsoft;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

/**
 * Created by edsoft on 3/22/16.
 */
public class ClickStreamProducer {
    private static Producer<String, ClickStream> producer;
    private static final Properties properties;

    static {
        properties = new Properties();
        properties.put("metadata.broker.list", "localhost:9092");
        properties.put("request.required.acks", "1");
        properties.put("serializer.class", "com.edsoft.kafka.DataEncoder");
        producer = new Producer<>(new ProducerConfig(properties));
    }

    public static void sendKafkaFromDataset(ClickStream data) {
        KeyedMessage<String, ClickStream> iotData = new KeyedMessage<>("cooja1", data);
        producer.send(iotData);
    }
}
