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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

public class ArrayMultiValueOptionParserTests extends BaseOptionParserTest {

  @Override
  @BeforeEach
  public void setUp() {
    super.setUp();
    doReturn(Character[].class).when(targetClass).mainType();
    doReturn(List.of(new TargetClass(Character.class, List.of()))).when(targetClass).parametricTypes();
  }

  @Override
  OptionParser createParser(ValueConverter<Object> converter, TargetClass targetClass) {
    return new ArrayMultiValueOptionParser(converter, targetClass);
  }

  @ParameterizedTest
  @NullAndEmptySource
  public void shouldReturnEmptyOnEmptyList(List<String> values) {
    Object result = parser.parse(values);
    final String errorMessage = "Incorrect result returned by parser for empty values";
    assertNotNull(result, errorMessage);
    Object[] resultArray = (Object[]) result;
    assertEquals(0, resultArray.length, errorMessage);
  }

  @Test
  public void shouldParseCorrectly() {
    List<String> values = Arrays.asList("a", "b", "c");
    Object result = assertDoesNotThrow(() ->  parser.parse(values), "Parser should not throw for many values");
    Object[] resultArray = (Object[]) result;
    Character[] expectedArray = values.stream().map(value -> value.charAt(0)).toArray(Character[]::new);
    assertArrayEquals(expectedArray, resultArray, "Incorrect array returned by parser");
  }
}
