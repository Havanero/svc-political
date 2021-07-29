package services

import zamblauskas.csv.parser._
import scala.concurrent.Future
import javax.inject._
import java.time.LocalDate
import play.api.inject.ApplicationLifecycle
import models.Evaluation


case class CsvData(speaker: String, subject: String, date: String, words: String)

@Singleton
class StartUpService @Inject() (
  lifecycle: ApplicationLifecycle,
  evalService: EvaluationService
  ) {
  val path = getClass.getResource("/data/data.csv").getPath()
  
  val src = scala.io.Source.fromFile(path).mkString
  val result = Parser.parse[CsvData](src)
  
  for(l <- result) {
    l.map { c => 
        val date = LocalDate.parse(c.date);
        val item = Evaluation(1, c.speaker, c.subject, date, Integer.valueOf(c.words.trim()))
        evalService.addItem(item)
    }
  }

  lifecycle.addStopHook { () =>
    println("shutdown done")
    Future.successful(())
  }
}
