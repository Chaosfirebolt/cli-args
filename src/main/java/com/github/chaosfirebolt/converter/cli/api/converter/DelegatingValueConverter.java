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

import java.util.HashMap;
import java.util.Map;

/**
 * Delegates the conversion operation to the correct converter.
 */
public final class DelegatingValueConverter implements ValueConverter<Object> {

  private final Map<Class<?>, ValueConverter<?>> converters;

  /**
   * Creates a new instance with the default supported converters.
   */
  public DelegatingValueConverter() {
    this.converters = initDefaultConverters();
  }

  private static Map<Class<?>, ValueConverter<?>> initDefaultConverters() {
    Map<Class<?>, ValueConverter<?>> converters = new HashMap<>();
    converters.put(String.class, new StringConverter());

    converters.put(Integer.class, new IntegerConverter());
    converters.put(Byte.class, new ByteConverter());
    converters.put(Short.class, new ShortConverter());
    converters.put(Long.class, new LongConverter());
    converters.put(Float.class, new FloatConverter());
    converters.put(Double.class, new DoubleConverter());
    converters.put(Boolean.class, new BooleanConverter());
    converters.put(Character.class, new CharConverter());
    return converters;
  }

  @Override
  public Object convert(Class<?> targetClass, String value) {
    ValueConverter<?> converter = converters.get(targetClass);
    if (converter == null) {
      throw new UnsupportedConversionException("Not supported conversion to " + targetClass);
    }
    return converter.convert(targetClass, value);
  }

  /**
   * Registers a converters. If a mapping already exists, replaces the already registered converter with the new one.
   *
   * @param targetClass class to convert to
   * @param converter   converter for the class
   * @param <T>         type to convert to
   */
  public <T> void registerConverter(Class<T> targetClass, ValueConverter<T> converter) {
    converters.put(targetClass, converter);
  }
}
