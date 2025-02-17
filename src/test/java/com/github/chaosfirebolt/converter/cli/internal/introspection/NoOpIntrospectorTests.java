/*
 *  Copyright 2025 Boyan Georgiev
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.github.chaosfirebolt.converter.cli.internal.introspection;

import com.github.chaosfirebolt.converter.cli.api.TestArgumentsContainer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class NoOpIntrospectorTests {

  @Test
  public void returnedContainerShouldHaveDefaultFields() {
    NoOpIntrospector introspector = new NoOpIntrospector();
    TestArgumentsContainer container = introspector.introspect(TestArgumentsContainer.class).createContainer(null);
    final String errorMessage = "Unexpected default value";
    assertNull(container.getString(), errorMessage);
    assertEquals(0, container.getInteger(), errorMessage);
    assertNull(container.getRealNum(), errorMessage);
    assertNull(container.getBool(), errorMessage);
    assertNull(container.getCustomClass(), errorMessage);
    assertEquals(0, container.getSomeByte(), errorMessage);
  }
}
