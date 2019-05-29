/*
 * Copyright 2019 HM Revenue & Customs
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
import uk.gov.hmrc.http.cache.client.SessionCache
import uk.gov.hmrc.play.bootstrap.config.{RunMode, ServicesConfig}
import uk.gov.hmrc.play.bootstrap.http.HttpClient

class SDILSessionCache(val http: HttpClient,
                       val configuration: Configuration,
                       val runMode: RunMode,
                       environment: Environment)
  extends ServicesConfig(configuration, runMode) with SessionCache {

  override def defaultSource: String = configuration.get[String]("appName")

  override def baseUri: String = baseUrl("cacheable.session-cache")

  override def domain: String = getConfString("cacheable.session-cache.domain", throw new RuntimeException("Missing config cacheable.session-cache.domain"))
}
