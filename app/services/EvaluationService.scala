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

  def listAllItems: Future[Seq[Evaluation]] = {
    items.listAll
  }

  def getMost(date: LocalDate)  = {
    items.getSummary(date)
  }
}
