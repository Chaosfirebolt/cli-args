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

package com.github.chaosfirebolt.converter.cli.api.converter;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BooleanConverterTests {

  private final BooleanConverter converter = new BooleanConverter();

  @ParameterizedTest
  @ValueSource(strings = {"true", "TRUE", "tRuE"})
  public void shouldParseAnyCaseToTrue(String value) {
    Boolean result = converter.convert(Boolean.class, value);
    assertTrue(result, () -> "Incorrectly parsed true value: " + value);
  }

  @ParameterizedTest
  @ValueSource(strings = {"false", "1", "qwerty"})
  public void anyNonTrueValueShouldBeFalse(String value) {
    Boolean result = converter.convert(Boolean.class, value);
    assertFalse(result, () -> "Incorrectly parsed false value: " + value);
  }
}
