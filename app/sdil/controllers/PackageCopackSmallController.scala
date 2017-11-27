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

package sdil.controllers

import java.util.UUID
import javax.inject.Inject

import play.api.data.{Form, Mapping}
import play.api.data.Forms.{boolean, mapping, optional, single, text}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, AnyContent}
import sdil.config.FormDataCache
import sdil.models.Identification
import uk.gov.hmrc.http.SessionKeys
import uk.gov.hmrc.http.cache.client.SessionCache
import uk.gov.hmrc.play.frontend.controller.FrontendController
import views.html.softdrinksindustrylevy.register

import scala.concurrent.Future

class PackageCopackSmallController @Inject()(val messagesApi: MessagesApi) extends FrontendController with I18nSupport {

  val cache: SessionCache = FormDataCache

  private val packageCopackSmallForm = Form(single(
    "isPackageCopackSmall" -> booleanMapping))

  def display = Action { implicit request =>
    Ok(register.package_copack_small(packageCopackSmallForm))
  }

  def submit = Action.async { implicit request =>
      packageCopackSmallForm.bindFromRequest.fold(
        formWithErrors => {
          Future.successful(
            BadRequest(register.package_copack_small(formWithErrors)))
        },
        validFormData => {
          if (validFormData){
            Redirect(routes.LitreageController.show("packageCopackSmallVol"))
          }
          else{
            Redirect(routes.CopackedController.display())
          }
        })
    }
  private lazy val booleanMapping: Mapping[Boolean] =
    optional(boolean).verifying("sdil.form.radio.error", _.nonEmpty).
      transform(_.getOrElse(false), x => Some(x))

}
