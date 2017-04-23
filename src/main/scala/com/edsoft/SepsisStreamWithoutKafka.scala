package com.edsoft

import java.nio.file.{Files, Paths}

import com.edsoft.iot.Data
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SQLContext
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkContext, SparkConf}

/**
  * Created by edsoft on 02.03.2016.
  */
object SepsisStreamWithoutKafka {
  def main(args: Array[String]) {


    //params


    //5 seconds interval
    val batchInterval = 5

    //check if the rules file exists

    //setup spark. Toggle below two lines for local v/s yarn
    val sparkConf = new SparkConf().setAppName("Sepsis Rules Streaming Evaluation").setMaster("local[4]")
    //  val sparkConf = new SparkConf().setMaster("local[2]").setAppName("Sepsis Rules Streaming Evaluation")

    val sc = new SparkContext(sparkConf)
    val ssc = new StreamingContext(sc, Seconds(batchInterval))
    val sqc = new SQLContext(sc)
    // ssc.sparkContext.setLogLevel("ERROR") //spark 1.4 thingy

    //setup a queue rdd to simulate streaming rdd
    val patientQueueRDD = scala.collection.mutable.Queue[RDD[Data]]()
    val patientStream = ssc.queueStream(patientQueueRDD)

    //setup rules executor
    val rulesExecutor = new RulesExecutor()

    //setup tsdbupdater


    // 1000 RDDs of random data
    for (batch <- 1 to 100) {
      val randomPatients = ClickStreamDataGenerator.getPatientList(batch)
      val rdd = ssc.sparkContext.parallelize(randomPatients)
      patientQueueRDD += rdd
    }

    //store incoming data in hbase

    //to-add filler logic to create snapshot

    //logic for each dstream
    patientStream.foreachRDD(rdd => {

      //evaluate all the rules for the batch
      val evaluatedPatients = rdd.mapPartitions(incomingEvents => {
        rulesExecutor.evalRulesClick(incomingEvents)
      })

      evaluatedPatients.foreach(f => f.addTime())
      evaluatedPatients.foreach(f => SimpleProducer.sendKafkaAgain(f))


      //store the evaluation results in hbase

      //convert to dataframe
      val patientdf = sqc.applySchema(evaluatedPatients, classOf[Data])

      //print batch details

      //compute statistics and print them

      //opentsdb update statistics

    })


    ssc.start()
    ssc.awaitTermination()
  }

}
