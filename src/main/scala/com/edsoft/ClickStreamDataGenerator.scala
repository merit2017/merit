package com.edsoft

import com.edsoft.iot.Data

/**
  * Created by edsoft on 02.03.2016.
  */
object ClickStreamDataGenerator {
  val rn = scala.util.Random

  def getPatientList(a: Int): scala.collection.mutable.MutableList[Data] = {
    val randomPatients = scala.collection.mutable.MutableList[Data]()
    for (i <- 1 to 1000) {

      val patient = new Data("1", "50", "A")
      patient.addTime()
      randomPatients += patient
    }
    randomPatients
  }
}
