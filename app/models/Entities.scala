package models

import java.time.LocalDate

case class Evaluation(id: Long, speaker: String, subject: String, date:LocalDate, words: Int)
