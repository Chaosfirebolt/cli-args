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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DelegatingConverterTests {

  private final UnsupportedOperationConverter unsupportedOperationConverter = spy(new UnsupportedOperationConverter());
  private final DelegatingValueConverter converter = new DelegatingValueConverter(unsupportedOperationConverter);

  @Test
  public void unsupportedClassConversion_ShouldThrowException() {
    String input = "hello";
    assertThrows(UnsupportedConversionException.class, () -> converter.convert(UnsupportedClass.class, input), "Converting unsupported type should throw exception");
    verify(unsupportedOperationConverter, times(1)).convert(UnsupportedClass.class, input);
  }

  @Test
  public void supportedClassConversion_ShouldDelegateToCorrectConverter() {
    IntegerConverter delegate = spy(new IntegerConverter());
    converter.registerConverter(Integer.class, delegate);

    String input = "11111";
    Object result = assertDoesNotThrow(() -> converter.convert(Integer.class, input), "Converting supported type should pass");
    Object expected = Integer.valueOf(input);
    assertEquals(expected, result, "Incorrect conversion result");
    verify(delegate, times(1)).convert(Integer.class, input);
  }
}
