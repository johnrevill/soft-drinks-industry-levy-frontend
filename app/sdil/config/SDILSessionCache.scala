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

package sdil.config

import play.api.{Configuration, Environment}
import play.api.Mode.Mode
import uk.gov.hmrc.http.cache.client.SessionCache
import uk.gov.hmrc.play.bootstrap.config.AppName
import uk.gov.hmrc.play.bootstrap.http.HttpClient
import uk.gov.hmrc.play.config.ServicesConfig

class SDILSessionCache(val http: HttpClient,
                       val configuration: Configuration,
                       environment: Environment)
  extends SessionCache with AppName with ServicesConfig {

  override def defaultSource: String = appName

  override def baseUri: String = baseUrl("cacheable.session-cache")

  override def domain: String = getConfString("cacheable.session-cache.domain", throw new RuntimeException("Missing config cacheable.session-cache.domain"))

  override protected def mode: Mode = environment.mode

  override protected def runModeConfiguration: Configuration = configuration
}