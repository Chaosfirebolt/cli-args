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

import com.github.chaosfirebolt.converter.cli.api.exception.UnsupportedConversionException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CharConverterTests {

  private final CharConverter converter = new CharConverter();

  @ParameterizedTest
  @ValueSource(strings = {"", "as"})
  @NullSource
  public void invalidValue_ShouldThrowUnsupportedConversionException(String input) {
    assertThrows(UnsupportedConversionException.class, () -> converter.convert(Character.class, input), "Incorrect conversion exception");
  }

  @ParameterizedTest
  @ValueSource(strings = {"a", "1", "*", ";", " "})
  public void validValue(String input) {
    Character result = converter.convert(Character.class, input);
    Character expected = input.charAt(0);
    assertEquals(expected, result, "Incorrect char conversion result");
  }
}
