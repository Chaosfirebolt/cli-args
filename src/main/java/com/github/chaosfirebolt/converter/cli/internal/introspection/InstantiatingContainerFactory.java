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

import com.github.chaosfirebolt.converter.cli.api.ArgumentsContainer;
import com.github.chaosfirebolt.converter.cli.api.exception.InaccessibleBeanException;
import com.github.chaosfirebolt.converter.cli.internal.container.ContainerFactory;
import com.github.chaosfirebolt.converter.cli.internal.parse.Options;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Creates an instance from any class.
 */
record InstantiatingContainerFactory<T extends ArgumentsContainer>(Class<T> clazz) implements ContainerFactory<T> {

  private static final Map<Class<?>, Object> TYPE_DEFAULTS = Map.of(
          byte.class, (byte) 0,
          short.class, (short) 0,
          int.class, 0,
          long.class, 0L,
          float.class, 0F,
          double.class, 0.0D,
          boolean.class, false,
          char.class, '\u0000'
  );

  @Override
  public T createContainer(Options options) {
    Constructor<T> constructor = leastArgumentsConstructor();
    try {
      return constructor.newInstance(initArgs(constructor));
    } catch (ReflectiveOperationException exc) {
      throw new InaccessibleBeanException("Could not instantiate " + clazz, exc);
    }
  }

  @SuppressWarnings("unchecked")
  private Constructor<T> leastArgumentsConstructor() {
    return (Constructor<T>) Stream.of(clazz.getDeclaredConstructors())
            .min(Comparator.comparing(Constructor::getParameterCount))
            .orElseThrow(() -> new RuntimeException("No constructor found for " + clazz));
  }

  private Object[] initArgs(Constructor<T> constructor) {
    return Stream.of(constructor.getParameters())
            .map(Parameter::getType)
            .map(TYPE_DEFAULTS::get)
            .toArray(Object[]::new);
  }
}