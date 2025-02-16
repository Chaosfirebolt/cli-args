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

import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Introspector for methods of beans. Filters out non-setter methods.
 * Setter methods are considered those with single argument.
 */
public class MethodBeanIntrospector extends BeanIntrospector<Method> {

  /**
   * Creates new instance.
   *
   * @param argumentNames container for names of all arguments
   * @param converter     the converter
   */
  public MethodBeanIntrospector(Set<String> argumentNames, ValueConverter<Object> converter) {
    super(argumentNames, converter);
  }

  @Override
  Stream<Method> getAnnotatedElements(Class<?> clazz) {
    return Stream.of(clazz.getDeclaredMethods()).filter(method -> method.getParameters().length == 1);
  }

  @Override
  ArgumentHandlerFactory createArgumentHandlerFactory(Method member) {
    return new MethodArgumentHandlerFactory(member);
  }
}
