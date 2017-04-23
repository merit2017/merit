package com.edsoft.iot;

import ch.ethz.inf.vs.californium.coap.Request;
import ch.ethz.inf.vs.californium.coap.Response;

/**
 * Created by user on 5/30/16.
 */
public class GetSensors2 {
    private static String url = "coap://[aaaa::c30c:0:0:2]:5683/test/sensors2";

    /*
     * Application entry point.
     *
     */
    public static void main(String args[]) throws InterruptedException {

        Request request = Request.newGet();

        // specify URI of target endpoint
        request.setURI(url);//URI
        request.setObserve();
        int count = 0;

        while (true) {//For observer, I use while(true)
            request.send();
            Response response = request.waitForResponse(2000);
            if (null != response) {
                String responseString = response.toString();//GET DATA
                System.out.println(responseString);
                if (responseString.startsWith("NON")) {
                    String[] values = responseString.split("\"");;
                    String[] values2 = values[1].split(",");
                    // response received, output a pretty-print
                    System.out.println(response);
                    Data battvalue = new Data(values2[0], values2[1], values2[2]);              //Alınan veriler Data nesnesi olarak tutuluyor.
                    Data bandvalue = new Data(values2[3], values2[4], values2[5]);             //Alınan veriler Data nesnesi olarak tutuluyor.
                    //   Data battvalue= new Data(values2[6], values2[7], values2[8]);             //Alınan veriler Data nesnesi olarak tutuluyor.
                    //   Data bandvalue= new Data(values2[9], values2[10], values2[11]);             //Alınan veriler Data nesnesi olarak tutuluyor.
                    //Document document = Little.convertDocument(d);
                    //System.out.println(document);
                    //ProvenanceProducer.provenanceToKafka(document);
                    // DataProducer.sendKafkaFromSensors(cpuvalue,memvalue,battvalue,bandvalue);                                 //Alınan veriler kafkaya gönderiliyor.
                    DataProducer.sendKafkaFromSensors(battvalue, bandvalue);
                } else {
                    System.out.println("No response received.");
                }

            }
        }

    }
}
