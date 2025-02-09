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

/**
 * Extension point for converters.
 *
 * @param <T> type to convert to
 */
public abstract non-sealed class BaseValueConverter<T> implements ValueConverter<T> {

  private final Class<T> resultClass;

  /**
   * @param resultClass type to convert to
   */
  protected BaseValueConverter(Class<T> resultClass) {
    this.resultClass = resultClass;
  }

  @Override
  public final T convert(Class<?> targetClass, String value) {
    validateTargetType(targetClass);
    return convert(value);
  }

  private void validateTargetType(Class<?> targetClass) {
    if (resultClass != targetClass) {
      throw new UnsupportedConversionException(targetClass + " is not assignable to " + resultClass);
    }
  }

  /**
   * Performs the actual conversion operation.
   *
   * @param value value to convert
   * @return converted value
   */
  protected abstract T convert(String value);
}
