package com.edsoft.iot;

import com.google.gson.Gson;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by edsoft on 07.01.2016.
 */
public class DataProducer {
    private static Producer<String, Data> producer;
    private static final Properties properties;
    private static RandomAccessFile randomAccessFile;
    private static Gson gson;

    static {
        gson = new Gson();
        properties = new Properties();                                           //topic özellikleri belirleniyor.
        properties.put("metadata.broker.list", "localhost:9092");                //topic özellikleri belirleniyor.
        properties.put("request.required.acks", "1");                            //topic özellikleri belirleniyor.
        properties.put("serializer.class", "com.edsoft.kafka.DataEncoder");      //topic özellikleri belirleniyor.
        producer = new Producer<>(new ProducerConfig(properties));               //topic özellikleri belirleniyor.
        //       try {
//            randomAccessFile = new RandomAccessFile("/home/edsoft/AAL-RTIS/src/main/resources/test/test_result_new2.json", "rw");
        //       } catch (FileNotFoundException e) {
        //           e.printStackTrace();
        //       }
    }

    public static void sendKafkaFromSensors(Data data/*, Data memvalue,Data battvalue,Data bandvalue*/) {
        //data.addTime();//Kafkaya giriş
        //System.out.println("SEND KAFKA FROM IOT SENSORS");
        //System.out.println();
        KeyedMessage<String, Data> iotdata = new KeyedMessage<>("cooja1", data);          //cooja1 isimli topic ve gönderilecek veri oluşturuluyor.
        //  KeyedMessage<String, Data> memiot = new KeyedMessage<>("cooja1", memvalue);          //cooja1 isimli topic ve gönderilecek veri oluşturuluyor.
        //  KeyedMessage<String, Data> battiot = new KeyedMessage<>("cooja1", battvalue);         //cooja1 isimli topic ve gönderilecek veri oluşturuluyor.
        //  KeyedMessage<String, Data> bandiot = new KeyedMessage<>("cooja1", bandvalue);         //cooja1 isimli topic ve gönderilecek veri oluşturuluyor.
        List<KeyedMessage<String, Data>> alist=new ArrayList<KeyedMessage<String, Data>>();
        alist.add(iotdata);
        //   alist.add(memiot);
        //   alist.add(battiot);
        //   alist.add(bandiot);
        producer.send(alist);          //sensorlerden gelen veri cooja1 topic e gönderiliyor.
    }
    public static void sendKafkaFromSensors(Data value1, Data value2/*,Data battvalue,Data bandvalue*/) {
        //data.addTime();//Kafkaya giriş
        //System.out.println("SEND KAFKA FROM IOT SENSORS");
        //System.out.println();
        KeyedMessage<String, Data> iot1 = new KeyedMessage<>("cooja1", value1);          //cooja1 isimli topic ve gönderilecek veri oluşturuluyor.
        KeyedMessage<String, Data> iot2 = new KeyedMessage<>("cooja1", value2);          //cooja1 isimli topic ve gönderilecek veri oluşturuluyor.
        //  KeyedMessage<String, Data> battiot = new KeyedMessage<>("cooja1", battvalue);         //cooja1 isimli topic ve gönderilecek veri oluşturuluyor.
        //  KeyedMessage<String, Data> bandiot = new KeyedMessage<>("cooja1", bandvalue);         //cooja1 isimli topic ve gönderilecek veri oluşturuluyor.
        List<KeyedMessage<String, Data>> alist=new ArrayList<KeyedMessage<String, Data>>();
        alist.add(iot1);
        alist.add(iot2);
        //   alist.add(battiot);
        //   alist.add(bandiot);
        producer.send(alist);          //sensorlerden gelen veri cooja1 topic e gönderiliyor.
    }
    public static void sendKafkaFromEsper(Data data) {
        //data.addTime();
        // convertJSON(data);
        KeyedMessage<String, Data> esperData = new KeyedMessage<>("esper1", data);            //esper1 isimli topic ve gönderilecek veri oluşturuluyor.
        producer.send(esperData);                                                             //esperden gelen veri esper1 topic e gönderiliyor.
    }

    private static void convertJSON(Data clickStream) {
        try {
            randomAccessFile.write((gson.toJson(clickStream) + "\r\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public static void provenanceToKafka(Document data) {
        KeyedMessage<String, Document> esperData = new KeyedMessage<>("aalT", data);
        producer.send(esperData);
    }*/
}
