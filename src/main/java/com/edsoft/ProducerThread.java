package com.edsoft;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by edsoft on 3/22/16.
 */
public class ProducerThread implements Runnable {

    Thread t;

    public ProducerThread() {
        this.t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        String filename = "/home/edsoft/IdeaProjects/SeniorProject/ejb/src/main/resources/veri.csv";
        RandomAccessFile randomAccess = null;
        try {
            randomAccess = new RandomAccessFile(filename, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 15000; i++) {
            String[] lineSplit = null; //line.split(":")
            try {
                lineSplit = randomAccess.readLine().split(":");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (null == lineSplit) {
                System.exit(-1);
            }
            ClickStream clickStream = new ClickStream(lineSplit[0], lineSplit[1], Integer.valueOf(lineSplit[2]));
            clickStream.addTime();//Verinin Kafkaya girişi
            ClickStreamProducer.sendKafkaFromDataset(clickStream);


        }
        // System.out.println("Çalışıyor ama ");
    }
}