package com.edsoft.kafka;

import com.edsoft.iot.Data;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.serializer.Decoder;
import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

import java.io.IOException;

/**
 * Created by edsoft on 07.01.2016.
 * Sınıfların JSON'a convert edilmesi
 */
public class DataEncoder implements Encoder<Data>, Decoder<Data> {

    static ObjectMapper objectMapper = new ObjectMapper();

    public DataEncoder(VerifiableProperties verifiableProperties) {

    }

    @Override
    public byte[] toBytes(Data data) {
        try {
            return objectMapper.writeValueAsString(data).getBytes();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "".getBytes();
    }

    @Override
    public Data fromBytes(byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(bytes, Data.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
