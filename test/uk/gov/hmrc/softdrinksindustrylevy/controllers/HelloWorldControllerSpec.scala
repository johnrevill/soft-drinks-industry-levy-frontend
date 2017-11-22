/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.softdrinksindustrylevy.controllers

import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{reset, times, verify, when}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.http.Status
import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.softdrinksindustrylevy.connectors.SoftDrinksIndustryLevyConnector
import uk.gov.hmrc.softdrinksindustrylevy.models.DesSubmissionResult

import scala.concurrent.Future


class HelloWorldControllerSpec extends PlayMessagesSpec with MockitoSugar with GuiceOneAppPerSuite with BeforeAndAfterEach {

  val mockSdilConnector: SoftDrinksIndustryLevyConnector = mock[SoftDrinksIndustryLevyConnector]
  val controller = new HelloWorldController(messagesApi, mockSdilConnector)

  override def beforeEach() {
    reset(mockSdilConnector)
  }

  "HelloWorldController" should {
    "return Status: 200 Message: Return result is: true for successful helloWorld" in {
      val request = FakeRequest("GET", "/hello-world")
      when(mockSdilConnector.retrieveHelloWorld()(any())).thenReturn(Future.successful(DesSubmissionResult(true)))
      val result = controller.backendRetrieve().apply(request)
      status(result) mustBe Status.OK
      verify(mockSdilConnector, times(1)).retrieveHelloWorld()(any())
      contentAsString(result) must include("Return result is: true")
    }

    "return Status: 200 Message: Return result is: false for successful helloWorld" in {
      val request = FakeRequest("GET", "/hello-world")
      when(mockSdilConnector.retrieveHelloWorld()(any())).thenReturn(Future.successful(DesSubmissionResult(false)))
      val result = controller.backendRetrieve().apply(request)
      status(result) mustBe Status.OK
      verify(mockSdilConnector, times(1)).retrieveHelloWorld()(any())
      contentAsString(result) must include("Return result is: false")
    }
  }
}