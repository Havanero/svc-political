package services

import com.google.inject.Inject
import models.{Evaluation, EvaluationList}

import java.time.LocalDate
import scala.concurrent.Future

class EvaluationService @Inject() (items: EvaluationList) {

  def addItem(item: Evaluation): Future[String] = {
    items.add(item)
  }


  def getItem(id: Long): Future[Option[Evaluation]] = {
    items.get(id)
  }

  def getByName(speaker: String): Future[Option[Evaluation]] = {
    items.getByName(speaker)
  }
  def listAllItems: Future[Seq[Evaluation]] = {
    items.listAll
  }

  def getMost(date: LocalDate)  = {
    items.getSummary(date)
  }

  def queryByType(queryName: String, parameter: Option[String]=None, parameterValue: Option[String]=None): Future[Option[Evaluation]] = {
    items.queryByType(queryName, parameter, parameterValue)
  }
}
