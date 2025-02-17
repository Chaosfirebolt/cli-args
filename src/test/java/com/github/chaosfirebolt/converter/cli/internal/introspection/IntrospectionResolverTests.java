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

import com.github.chaosfirebolt.converter.cli.api.annotation.Argument;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
class IntrospectionResolverTests {

  @ParameterizedTest
  @MethodSource
  void shouldReturnCorrectType(Class<?> clazz, IntrospectionType expected) {
    IntrospectionResolver resolver = new IntrospectionResolver();
    IntrospectionType result = resolver.apply(clazz);
    assertEquals(expected, result, "Incorrect resolving");
  }

  private static Stream<Arguments> shouldReturnCorrectType() {
    return Stream.of(
            Arguments.of(Class1.class, IntrospectionType.BEAN_FIELD),
            Arguments.of(Class2.class, IntrospectionType.BEAN_SETTER),
            Arguments.of(Class3.class, IntrospectionType.BEAN_SETTER),
            Arguments.of(Class4.class, IntrospectionType.UNKNOWN)
    );
  }

  private static final class Class1 {

    @Argument(name = "-n")
    private String name;

    public void setName(String name) {
      this.name = name;
    }
  }

  private static final class Class2 {

    private String name;

    @Argument(name = "-n")
    public void setName(String name) {
      this.name = name;
    }
  }

  private static final class Class3 {

    @Argument(name = "--name")
    private String name;

    @Argument(name = "-n")
    public void setName(String name) {
      this.name = name;
    }
  }

  private static final class Class4 {

    private String name;

    public void setName(String name) {
      this.name = name;
    }
  }
}
