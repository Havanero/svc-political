package controllers

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.FakeRequest
import play.api.test.Helpers._

class HealthCheckApiTest extends PlaySpec with GuiceOneAppPerTest {
  "HealthCheckController" should {
    "return pong" in {
      val request = FakeRequest(GET, "/")
      val ping = route(app, request).get

      status(ping) mustBe OK
      contentType(ping) mustBe Some("text/plain")
      contentAsString(ping) must include("pong")
    }
  }
}


class RawEvaluations extends PlaySpec with GuiceOneAppPerTest {
  "RawEvaluations index" should {
    "return evaluaton json obj" in {
      val request = FakeRequest(GET, "/raw-evaluations")
      val rawEvaluations = route(app, request).get

      status(rawEvaluations) mustBe OK
      contentType(rawEvaluations) mustBe Some("application/json")
      //TODO: assert again type of object
    }
  }
}
