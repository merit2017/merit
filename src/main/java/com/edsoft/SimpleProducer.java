package com.edsoft;


import com.edsoft.iot.Data;
import com.google.gson.Gson;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.RandomAccessFile;
import java.util.Properties;


public class SimpleProducer {

    private static RandomAccessFile randomAccessFile;
    private static Gson gson;
    private static String file = "/home/user/Desktop/test_result_new2.json";
    private static Producer<String, Data> producer;
    private static final Properties properties;
    static {
        gson = new Gson();
        properties = new Properties();                                           //topic özellikleri belirleniyor.
        properties.put("metadata.broker.list", "localhost:9092");                //topic özellikleri belirleniyor.
        properties.put("request.required.acks", "1");                            //topic özellikleri belirleniyor.
        properties.put("serializer.class", "com.edsoft.kafka.DataEncoder");      //topic özellikleri belirleniyor.
        producer = new Producer<>(new ProducerConfig(properties));               //topic özellikleri belirleniyor.
        //       try {
//            randomAccessFile = new RandomAccessFile("/home/user/Desktop/test_result_new2.json", "rw");
        //       } catch (FileNotFoundException e) {
        //           e.printStackTrace();
        //       }
    }
    public static void sendMail(String mailText) throws MessagingException {
        final String username = "***";
        final String password = "***";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        System.out.println("mailll");
        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress("***"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("****"));
            message.setSubject("Hasta Acil Bilgilendirme");
            message.setText(mailText);

            Transport.send(message);
        } catch (Exception e) {

        }
    }

    public static void sendKafkaAgain(Data data) {
        //data.addTime();

        //data.addTime();
        // convertJSON(data);
        System.out.println();
        KeyedMessage<String, Data> esperData = new KeyedMessage<>("esper1", data);            //esper1 isimli topic ve gönderilecek veri oluşturuluyor.
        producer.send(esperData);                                                             //esperden gelen veri esper1 topic e gönderiliyor.
    }
    public static void sendKafkaAgain2(Data data) {
        data.addTime();

        //data.addTime();
        // convertJSON(data);

       System.out.println("--------------------TAMAM ÇALIŞIYOR--------------------------");
        KeyedMessage<String, Data> esperData = new KeyedMessage<>("esper1", data);            //esper1 isimli topic ve gönderilecek veri oluşturuluyor.
        producer.send(esperData);                                                             //esperden gelen veri esper1 topic e gönderiliyor.
    }
}