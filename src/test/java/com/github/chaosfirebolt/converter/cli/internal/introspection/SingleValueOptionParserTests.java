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

import com.github.chaosfirebolt.converter.cli.api.converter.ValueConverter;
import com.github.chaosfirebolt.converter.cli.api.exception.UnsupportedConversionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SingleValueOptionParserTests extends BaseOptionParserTest {

  @Override
  OptionParser createParser(ValueConverter<Object> converter, TargetClass targetClass) {
    return new SingleValueOptionParser(converter, targetClass);
  }

  @ParameterizedTest
  @NullAndEmptySource
  public void shouldReturnNullOnEmptyList(List<String> values) {
    Object result = parser.parse(values);
    assertNull(result, "Incorrect result returned by parser for empty values");
  }

  @Test
  public void shouldThrowExceptionOnTooManyValues() {
    List<String> values = Arrays.asList("a", "b", "c");
    assertThrows(UnsupportedConversionException.class, () -> parser.parse(values), "Parsing too many values should throw exception");
  }

  @Test
  public void shouldParseCorrectlyOnSingleValue() {
    doReturn(Integer.class).when(targetClass).mainType();
    List<String> values = List.of("11");
    Object result = parser.parse(values);

    assertEquals(11, result, "Incorrect result returned by parser");
    verify(targetClass, times(1)).mainType();
    verify(converter, times(1)).convert(Integer.class, "11");
  }
}
