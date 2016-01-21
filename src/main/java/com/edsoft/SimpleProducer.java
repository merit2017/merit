package com.edsoft;


import com.textmagic.sms.TextMagicMessageService;
import com.textmagic.sms.exception.ServiceException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class SimpleProducer {


    public static void sendSMS(String smsText) {
        String dummyPhone = "****";
        TextMagicMessageService service =
                new TextMagicMessageService("****", "***");
        try {
            service.send(smsText, dummyPhone);
        } catch (ServiceException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void sendMail(String mailText) throws MessagingException {
        final String username = "***";
        final String password = "***";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

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

   /* public static void sendKafkaAgain(Data clickStream) {
        clickStream.addTime();
        KeyedMessage<String, Data> clickStreamKeyedMessage = new KeyedMessage<>("testFour", clickStream);
        producer.send(clickStreamKeyedMessage);
        convertJSON(clickStream);
    }

    public static void convertJSON(ClickStream clickStream) {
        try {

            randomAccessFile.write(gson.toJson(clickStream + ",\r\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}