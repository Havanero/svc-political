package controllers

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.FakeRequest
import play.api.test.Helpers._


class ResEvaluations extends PlaySpec with GuiceOneAppPerTest {
  "RawEvaluations index" should {
    "return evaluaton json obj" in {
      val request = FakeRequest(GET, "/evaluations?mostSpeaches&date=2012-11-05")
      val rawEvaluations = route(app, request).get

      status(rawEvaluations) mustBe OK
      contentType(rawEvaluations) mustBe Some("application/json")
      contentAsString(rawEvaluations)
      //TODO: assert again type of object
    }
  }
}
