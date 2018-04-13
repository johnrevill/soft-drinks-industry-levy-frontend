/*
 * Copyright 2018 HM Revenue & Customs
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

package sdil.controllers.variation

import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, AnyContent}
import sdil.actions.VariationAction
import sdil.config.AppConfig
import sdil.connectors.SoftDrinksIndustryLevyConnector
import sdil.forms.FormHelpers
import sdil.models.variations.UpdatedBusinessDetails
import uk.gov.hmrc.http.cache.client.SessionCache
import uk.gov.hmrc.play.bootstrap.controller.FrontendController
import views.html.softdrinksindustrylevy.variations.business_details

import scala.concurrent.Future

class BusinessDetailsController(val messagesApi: MessagesApi,
                                sdilConnector: SoftDrinksIndustryLevyConnector,
                                cache: SessionCache,
                                variationAction: VariationAction)
                               (implicit config: AppConfig)
  extends FrontendController with I18nSupport {

  def show: Action[AnyContent] = variationAction { implicit request =>
    Ok(business_details(BusinessDetailsForm().fill(request.data.updatedBusinessDetails)))
  }

  def submit: Action[AnyContent] = variationAction.async { implicit request =>
    BusinessDetailsForm().bindFromRequest().fold(
      errors => Future.successful(BadRequest(business_details(errors))),
      data => {
        val updated = request.data.copy(updatedBusinessDetails = data)
        cache.cache("variationData", updated) map { _ =>
          Redirect(routes.VariationsController.show())
        }
      }
    )
  }
}

object BusinessDetailsForm extends FormHelpers {
  def apply(): Form[UpdatedBusinessDetails] = Form(mapping(
    "tradingName" -> nonEmptyText,
    "businessAddress" -> addressMapping
  )(UpdatedBusinessDetails.apply)(UpdatedBusinessDetails.unapply))
}