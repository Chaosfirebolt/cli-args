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

/**
 * Converts values to required type.
 *
 * @param <T> type to convert to
 */
public sealed interface ValueConverter<T> permits DelegatingValueConverter, BaseValueConverter {

  /**
   * Converts provided values to the target type.
   *
   * @param targetClass type to convert to
   * @param value       values to be converted
   * @return converted value
   * @throws com.github.chaosfirebolt.converter.cli.api.exception.UnsupportedConversionException if unable to convert value to target type
   */
  T convert(Class<?> targetClass, String value);
}
