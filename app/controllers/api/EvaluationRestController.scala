
package controllers.api
import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import scala.collection.mutable
import play.api.data.FormError
import models.{Evaluation, EvaluationForm}

import java.time.LocalDate
import services.EvaluationService
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class Summary(mostSpeaches: Option[String]=None, mostSecurity: Option[String]=None, leastWordy: Option[String]=None)

@Singleton
class EvaluationRestController @Inject()(cc: ControllerComponents, evalService: EvaluationService)
extends AbstractController(cc) {

  implicit val evalListJson = Json.format[Evaluation]
  implicit val evalJson = Json.format[Summary]
  var mostSpeachVar: Option[String]=None 
  var mostSecVar: Option[String]=None 

  def getAll() = Action.async { implicit request: Request[AnyContent] =>
        val params = request.queryString.map { case (k,v) => k -> v.mkString }
        val query=params.get("query")
        val isQuery = query.exists(_.trim.nonEmpty) 
        evalService.listAllItems map { items =>
          
        val defaultSpeaches = items groupBy(_.speaker) map { case (k,v) => k -> (v map (_.words) sum)  }
        val leastWordy=defaultSpeaches.toSeq.sortBy(_._1).last._1

        val response = isQuery match {
            case true =>{
                
                val isDate = params.get("date").exists(_.trim.nonEmpty)
                val isSubject = params.get("subject").exists(_.trim.nonEmpty)

                if (isDate){
                    val parsedDate = params.get("date").get.split("-").headOption.getOrElse("2013")
                    val byDate = items.filter(f=>f.date.toString.contains(parsedDate))
                    val topByDate = byDate.groupBy(_.speaker) map { case (k,v) => k -> (v map (_.words) sum)  }
                    var mostSpeaches =  topByDate.headOption.get._1
                    mostSpeachVar = Some(mostSpeaches)
                }
                if (isSubject){
                    val parsedSubject = params.get("subject").get
                    mostSecVar = Some(processMostSecurity(items, parsedSubject))
                }
                Summary(mostSpeaches=mostSpeachVar,mostSecurity=mostSecVar,leastWordy=Some(leastWordy))
                }
            case default => {
                val mostSpeaches = defaultSpeaches.headOption.get._1
                val mostSecurity = processMostSecurity(items, "internal security")
                Summary(Some(mostSpeaches), Some(mostSecurity), Some(leastWordy))
                }
        }
        Ok(Json.toJson(response))
        }  

  }
  def getAllRaw() = Action.async { implicit request: Request[AnyContent] =>
          evalService.listAllItems map { items =>
          Ok(Json.toJson(items))
       }
    }

  def processMostSecurity(items: Seq[Evaluation], subject: String)={
     val bySubject = items.filter(f=>f.subject.toLowerCase.contains(subject.toLowerCase))
     println("subject" + subject)
     val topBySubject = bySubject groupBy(_.speaker) map { case (k,v) => k -> (v map (_.words) sum)  }
     topBySubject.last._1
  }
    
  
}

