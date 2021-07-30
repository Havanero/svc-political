
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
  
  def getAll() = Action.async { implicit request: Request[AnyContent] =>
        val params = request.queryString.map { case (k,v) => k -> v.mkString }
        val query=params.get("query")
        
        evalService.listAllItems map { items =>
        
        //val mostSpeaches = ll.groupBy(identity).mapValues(_.size).maxBy(_._2)
        //val mostSecurity = items.groupBy(l => l.subject).map(t => (t._1, t._2.length))

        val mostSecurity = items.groupBy(_.subject).values.map(_.maxBy(_.words)).filter(_.subject=="Internal Security").headOption.get
        val mostSpeaches = items.groupBy(_.subject).values.map(_.maxBy(_.words)).filter(_.date=="2013").headOption.getOrElse(null)
        //val mostSpeaches = items.groupBy(_.speaker).mapValues(_.maxBy(_.worwordsds))
        //val max_food = items.groupBy(record => record.words.toInt)
        //val allV = items.collect{ case Evaluation(id,_,_,_,words) => words }.sum

        println("--------------")
        println(mostSpeaches)
        println("sec")
   //     print(mostSecurity)
        println("-----------")
      ////  println(ll)
    
       Ok(Json.toJson(Summary(mostSecurity=mostSecurity.speaker)))
     }
  }
  
}

