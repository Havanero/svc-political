

package models

import java.sql.Date
import java.time.LocalDate
import com.google.inject.Inject
import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.data.Forms._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}
//import slick.jdbc.MySQLProfile.api._
import slick.jdbc.PostgresProfile.api._
import play.api.data.format.Formats._ 



case class EvaluationFormData(speaker: String, subject: String, date: LocalDate, words: Int)


object EvaluationForm {
  val form = Form(
    mapping(
      "speaker" -> nonEmptyText,
      "subject" -> nonEmptyText,
      "data" -> of[LocalDate],
      "words" -> of[Int]
    )(EvaluationFormData.apply)(EvaluationFormData.unapply)
  )
}

 class EvaluationTableDef(tag: Tag) extends Table[Evaluation](tag, "evaluation") {
 
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def speaker = column[String]("speaker")
  def subject = column[String]("subject")
  def date = column[LocalDate]("date")(localDateColumnType)
  def words = column[Int]("words")
 
  override def * = (id, speaker, subject, date, words) <> (Evaluation.tupled, Evaluation.unapply)
  implicit val localDateColumnType = MappedColumnType.base[LocalDate, Date](
    d => Date.valueOf(d),
    d => d.toLocalDate)

} 

class EvaluationList @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider
)(implicit executionContext: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {
 
  var evaluationList = TableQuery[EvaluationTableDef]
 
  def add(item: Evaluation): Future[String] = {
    dbConfig.db
      .run(evaluationList += item)
      .map(res => "Item successfully added")
      .recover {
        case ex: Exception => {
            printf(ex.getMessage())
            ex.getMessage
        }
      }
  }
 
  def get(id: Long): Future[Option[Evaluation]] = {
    dbConfig.db.run(evaluationList.filter(_.id === id).result.headOption)
  }
 
  def listAll: Future[Seq[Evaluation]] = {
    dbConfig.db.run(evaluationList.result)
  }
  var query = TableQuery[EvaluationTableDef]

  def getSummary(date: LocalDate)  = {
      print(date)
      val action = query.sortBy(_.words.desc).filter(_.date ===date).result.headOption
      println("XXXXXXXXXXxquery start")
      action.statements.foreach(println)
      print("runnnnnnnnnnnnnnnn" )
     // dbConfig.db.run(evaluationList.filter(_.id === x).result.headOption)
      dbConfig.db.run(action)
 //   print(date)
    //dbConfig.db.run(evaluationList.map(_.date).filter(d=>d >=cdate && d <=date).result.headOption)
   // dbConfig.db.run(evaluationList.map(_.date).filter(cdate).size.result)   
  }
}
