/*
 * Copyright 2018 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.spinnaker.swabbie.front50

import com.fasterxml.jackson.annotation.JsonTypeName
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.should.shouldMatch
import com.netflix.spinnaker.swabbie.InMemoryCache
import com.netflix.spinnaker.swabbie.model.Application
import com.netflix.spinnaker.swabbie.model.Resource
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Test

object Front50ApplicationResourceOwnerResolutionStrategyTest {
  val front50ApplicationCache: InMemoryCache<Application> = mock()
  private val subject = Front50ApplicationResourceOwnerResolutionStrategy(front50ApplicationCache)

  @Test
  fun `should resolve resource owner from application config`() {
    whenever(front50ApplicationCache.get()) doReturn
      setOf(
        Application(name = "name", email = "name@netflix.com"),
        Application(name = "test", email = "test@netflix.com")
      )

    subject.resolve(R()) shouldMatch equalTo("name@netflix.com")
  }
}

@JsonTypeName("R")
data class R(
  override val resourceId: String = "id",
  override val name: String = "name",
  override val resourceType: String = "type",
  override val cloudProvider: String = "provider"
) : Resource()

