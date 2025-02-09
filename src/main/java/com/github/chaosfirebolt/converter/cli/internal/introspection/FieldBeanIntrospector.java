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

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Introspector for fields of beans.
 */
public class FieldBeanIntrospector extends BeanIntrospector<Field> {

  /**
   * Creates new instance.
   *
   * @param instanceSupplier supplier of an instance to set data in
   * @param argumentNames    container for names of all arguments
   * @param parser           the parser
   */
  public FieldBeanIntrospector(Supplier<ArgumentsContainer> instanceSupplier, Set<String> argumentNames, Function<List<String>, ?> parser) {
    super(instanceSupplier, argumentNames, parser);
  }

  @Override
  Stream<Field> getAnnotatedElements(Class<?> clazz) {
    return Stream.of(clazz.getDeclaredFields());
  }

  @Override
  ArgumentHandlerFactory createArgumentHandlerFactory(Field member) {
    return new FieldArgumentHandlerFactory(member);
  }
}
