package com.edsoft

class RulesExecutor() extends Serializable {

  //evaluate all the rules and send the result back to


  def evalRulesClick(incomingEvents: Iterator[ClickStream]): Iterator[ClickStream] = {
    val ksession = KieSessionFactory.getKieSession()
    //ksession.execute(incomingEvents)
    val patients = incomingEvents.map(patient => {
      patient.addTime()
      ksession.execute(patient)
      patient
    })
    patients
  }

  def evalRuleSingleClick(event: ClickStream) = {

    val ksession = KieSessionFactory.getKieSession()
    ksession.execute(event)
  }

  def evalRulesIoT(incomingEvents: Iterator[ClickStream]): Iterator[ClickStream] = {
    val ksession = KieSessionFactory.getKieSession()

    val patients = incomingEvents.map(patient => {
      ksession.execute(patient)
      patient
    })
    patients
  }
}