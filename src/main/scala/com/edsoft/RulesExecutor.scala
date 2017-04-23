package com.edsoft
import com.edsoft.iot.Data

class RulesExecutor() extends Serializable {

  //evaluate all the rules and send the result back to
  def evalRulesClick(incomingEvents: Iterator[Data]): Iterator[Data] = {
    val ksession = KieSessionFactory.getKieSession
    val events = incomingEvents.map(event => {
      event.addTime()
      ksession.execute(event)
      event
    })
    events
  }

  def evalRuleSingleClick(event: Data) = {

    val ksession = KieSessionFactory.getKieSession
    ksession.execute(event)
  }

  def evalRulesIoT(incomingEvents: Iterator[Data]): Iterator[Data] = {
    val ksession = KieSessionFactory.getKieSession

    val patients = incomingEvents.map(patient => {
      ksession.execute(patient)
      patient
    })
    patients
  }
}