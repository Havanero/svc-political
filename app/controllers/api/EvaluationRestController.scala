
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

case class Summary(mostSpeaches: String =null, mostSecurity: String = null, leastWordy: String = null)

@Singleton
class EvaluationRestController @Inject()(cc: ControllerComponents, evalService: EvaluationService)
extends AbstractController(cc) {

  implicit val evalListJson = Json.format[Evaluation]
  implicit val evalJson = Json.format[Summary]
  
  def foo(value: Any, params: Map[String, String]) = value match {
    case Some("mostSpeaches") => getField(params) 
    case default => None
  }
  def getField(implicit params: Map[String, String]) = {
    println("gettting most field")
    val date = params.get("date").getOrElse(null)
    if(date!=null){
      date
    }
  }

  def process(implicit params: Map[String, String]): Future[Result] = {
    val date = params.get("date").getOrElse(null)
    if(date!=null){
      print("ddddddddddddd")
    }
    evalService.getMost(LocalDate.parse(date))map {item=>
    Ok("ok") 
    }
  }
  def getAll() = Action.async { implicit request: Request[AnyContent] =>
      val params = request.queryString.map { case (k,v) => k -> v.mkString }
      val query=params.get("query")
      val rs=query match {
          case Some("mostSpeaches")=> process(params)
          case Some("mostSecurity")=> process(params)
          case Some("leastWordy")=> process(params)
          case default=> "Nope"
       }  
      var date = foo(query, params)
      val b = date.asInstanceOf[String]
      evalService.getMost(LocalDate.parse(b))map {item=>
      //Ok(Json.toJson(Summary(item.get.speaker)))
      }
      evalService.listAllItems map { items =>

        val mostSpeaches = items.groupBy(_.speaker).values.map(_.maxBy(_.words)).toList

        val mostSecurity = items.groupBy(l => l.subject).map(t => (t._1, t._2.length))

        val mostSecurity = items.groupBy(_.speaker).values.map(_.maxBy(_.subject)).filter(_.subject=="Internal Security")
        //val mostSpeaches = items.groupBy(_.speaker).mapValues(_.maxBy(_.words))

        //print(items)
        println("--------------")
        println(mostSpeaches)
        println("sec")
        print(mostSecurity)
        println("xxx" + mostSpeachesX)
    
       Ok(Json.toJson(Summary()))
     }
  }
  
   def options(id: Option[Long], name: Option[String], transaction: Option[Int]) = Action {
      log(name.get)
      log(""+id.get)
      Ok("OK")
  }
  def log(message: String, level: String = "INFO") = println(s"$level: $message")
}

