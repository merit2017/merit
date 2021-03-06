package com.edsoft

import com.edsoft.iot.Data
import com.edsoft.kafka.DataEncoder
import org.apache.spark.sql.SQLContext
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by edsoft on 25.12.2015.
  */
object iotStreamKafka {
  def main(args: Array[String]) {

    val batchInterval = 3

    //setup spark. Toggle below two lines for local v/s yarn
    val sparkConf = new SparkConf().setMaster("local[4]").setAppName("IoT Rules Streaming Evaluation")
    val sc = new SparkContext(sparkConf)
    val ssc = new StreamingContext(sc, Seconds(batchInterval))
    val sql = new SQLContext(sc)

    //setup a queue rdd to simulate streaming rdd
    val kafkaConf = Map(
      "metadata.broker.list" -> "localhost:9092",
      "zookeeper.connect" -> "localhost:2181",
      "group.id" -> "kafka-spark-streaming-example",
      "zookeeper.connection.timeout.ms" -> "1000")

    val lines = KafkaUtils.createStream[Array[Byte], Data,
      DataEncoder, DataEncoder](
      ssc, kafkaConf, Map("test" -> 1), StorageLevel.MEMORY_ONLY_SER).map(_._2)

    val rulesExecutor = new RulesExecutor()
    val b = lines.mapPartitions(a => {
      rulesExecutor.evalRulesClick(a)
    })

    b.foreachRDD(rdd => {
      val schema = sql.createDataFrame(rdd, classOf[Data])
      val matrix = schema.select("id").groupBy().mean()
      matrix.show()
    })

    ssc.start()
    ssc.awaitTermination()
  }
}
