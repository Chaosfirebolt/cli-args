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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IntrospectionTypeTests {

  @ParameterizedTest
  @NullAndEmptySource
  @MethodSource
  void highestPriority_InvalidArray_ShouldThrowException(IntrospectionType[] types) {
    assertThrows(IllegalArgumentException.class, () -> IntrospectionType.highest(types), "Expected exception on invalid types array was not throws");
  }

  private static Stream<Arguments> highestPriority_InvalidArray_ShouldThrowException() {
    return Stream.of(() -> new IntrospectionType[]{null, null, null});
  }

  @ParameterizedTest
  @MethodSource
  void highestPriority_ArrayHasValues_ShouldThrowException(IntrospectionType[] types, IntrospectionType expected) {
    IntrospectionType actual = IntrospectionType.highest(types);
    assertEquals(expected, actual, "Incorrect highest priority result");
  }

  private static Stream<Arguments> highestPriority_ArrayHasValues_ShouldThrowException() {
    return Stream.of(
            Arguments.of(new IntrospectionType[]{IntrospectionType.BEAN_SETTER, IntrospectionType.BEAN_FIELD}, IntrospectionType.BEAN_SETTER),
            Arguments.of(new IntrospectionType[]{IntrospectionType.BEAN_SETTER, null}, IntrospectionType.BEAN_SETTER),
            Arguments.of(new IntrospectionType[]{null, IntrospectionType.BEAN_FIELD}, IntrospectionType.BEAN_FIELD),
            Arguments.of(new IntrospectionType[]{IntrospectionType.UNKNOWN, IntrospectionType.BEAN_FIELD}, IntrospectionType.BEAN_FIELD),
            Arguments.of(new IntrospectionType[]{null, IntrospectionType.UNKNOWN}, IntrospectionType.UNKNOWN)
    );
  }
}
