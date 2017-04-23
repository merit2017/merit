package com.edsoft.kafka;

import com.edsoft.ClickStream;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.serializer.Decoder;
import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

import java.io.IOException;

/**
 * Created by edsoft on 25.12.2015.
 */
public class ClickStreamEncoder implements Encoder<ClickStream>, Decoder<ClickStream> {

    static ObjectMapper objectMapper = new ObjectMapper();

    public ClickStreamEncoder(VerifiableProperties verifiableProperties) {

    }

    @Override
    public byte[] toBytes(ClickStream clickStream) {
        try {
            return objectMapper.writeValueAsString(clickStream).getBytes();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "".getBytes();
    }

    @Override
    public ClickStream fromBytes(byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(bytes, ClickStream.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
