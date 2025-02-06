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

package com.github.chaosfirebolt.converter.cli.internal.parse;

import com.github.chaosfirebolt.converter.cli.api.exception.InvalidArgumentsException;
import com.github.chaosfirebolt.converter.cli.api.exception.UnrecoverableException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OptionTests {

  @ParameterizedTest
  @ValueSource(strings = {"a", "abc", "qwerty"})
  public void keyShouldReturnCorrectly(String key) {
    Option option = Option.builder().setKey(key).build();
    assertEquals(key, option.key(), "Incorrect option key");
  }

  @Test
  public void invalidValues_ShouldThrowException() {
    Option option = Option.builder().setKey("invalid").addValue("a").build();

    Predicate<List<String>> validator = mock();
    when(validator.test(any())).thenReturn(false);

    BiFunction<String, List<String>, UnrecoverableException> errorFactory = mock();
    when(errorFactory.apply(any(), any())).thenReturn(new InvalidArgumentsException("Invalid arguments"));
    assertThrows(InvalidArgumentsException.class, () -> option.validate(validator, errorFactory), "Exception not thrown");

    verify(validator, times(1)).test(any());
    verify(errorFactory, times(1)).apply(any(), any());
  }

  @Test
  public void validValues_ShouldNotThrowException() {
    Option option = Option.builder().setKey("invalid").addValue("a").build();

    Predicate<List<String>> validator = mock();
    when(validator.test(any())).thenReturn(true);

    BiFunction<String, List<String>, UnrecoverableException> errorFactory = mock();
    assertDoesNotThrow(() -> option.validate(validator, errorFactory), "Exception thrown");

    verify(validator, times(1)).test(any());
    verify(errorFactory, never()).apply(any(), any());
  }

  @Test
  public void parse_ShouldReturnCorrectly() {
    Option option = Option.builder().setKey("invalid").addValue("1").build();
    List<String> expectedValues = List.of("1");

    Function<List<String>, Integer> parser = mock();
    when(parser.apply(expectedValues)).thenAnswer(invocation -> {
      List<String> optionValues = invocation.getArgument(0);
      return Integer.parseInt(optionValues.get(0));
    });

    int actualValue = option.parse(parser);
    assertEquals(1, actualValue, "Incorrect parse result");
    verify(parser, times(1)).apply(expectedValues);
  }

  @Test
  public void valuesShouldBeUnmodifiable() {
    Option option = Option.builder().setKey("invalid").addValue("1").addValue("2").addValue("3").build();
    List<String> extractedValues = option.parse(Function.identity());
    assertThrows(UnsupportedOperationException.class, extractedValues::clear, "Values list was modifiable");
  }
}
