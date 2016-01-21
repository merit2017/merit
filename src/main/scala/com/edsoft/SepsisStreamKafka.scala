package com.edsoft

import com.edsoft.kafka.ClickStreamEncoder
import org.apache.spark.sql.SQLContext
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by edsoft on 25.12.2015.
 */
object SepsisStreamKafka {
  def main(args: Array[String]) {

    //5 seconds interval
    val batchInterval = 3


    //setup spark. Toggle below two lines for local v/s yarn
    val sparkConf = new SparkConf().setMaster("local[4]").setAppName("Sepsis Rules Streaming Evaluation")

    val sc = new SparkContext(sparkConf)
    val ssc = new StreamingContext(sc, Seconds(batchInterval))
    val sql = new SQLContext(sc)





    //setup a queue rdd to simulate streaming rdd


    val kafkaConf = Map(
      "metadata.broker.list" -> "localhost:9092",
      "zookeeper.connect" -> "localhost:2181",
      "group.id" -> "kafka-spark-streaming-example",
      "zookeeper.connection.timeout.ms" -> "1000")

    val lines = KafkaUtils.createStream[Array[Byte], ClickStream,
  ClickStreamEncoder, ClickStreamEncoder](
  ssc, kafkaConf, Map("testThree" -> 1), StorageLevel.MEMORY_ONLY_SER).map(_._2)

    ssc.checkpoint("/home/edsoft/checkpoints")
    //setup rules executor
    val rulesExecutor = new RulesExecutor()

    /*for (count <- 1 to 10) {
      SimpleProducer.getClickStreamValues(randomAccess)
    }*/

    //logic for each dstream


    lines.foreachRDD(rdd => {
      //Verinin Kafkadan çıkışı
      rdd.foreach(f => f.addTime())
    })


    val b = lines.mapPartitions(a => {
      //Verinin Drools'a girişi
      rulesExecutor.evalRulesClick(a)
    })




    /*b.foreachRDD(rdd => {
      //Verinin tekrar kafkaya gönderilişi
      rdd.foreach(l => SimpleProducer.sendKafkaAgain(l))
    })*/






    //SMS gönderimi drools kuralı içerisinde olacak

    //save hadoop

    //b.saveAsTextFiles()

    //val c = b.filter(f => f.isAssing)

    //  c.saveAsTextFiles()

    b.foreachRDD(rdd => {
      val schema = sql.createDataFrame(rdd, classOf[ClickStream])
      val matrix = schema.select("clickCount").groupBy().mean()
      matrix.show()
      /*if (0 != matrix.first().getDouble(0).toInt) {
        //Drools Update
        ClickStream.THRESHOLD = matrix.first().getDouble(0).toInt
      }*/
    })


    ssc.start()
    ssc.awaitTermination()
  }
}
