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

import com.github.chaosfirebolt.converter.cli.api.Argument;
import com.github.chaosfirebolt.converter.cli.api.ArgumentsContainer;
import com.github.chaosfirebolt.converter.cli.api.exception.DuplicateArgumentNameException;
import com.github.chaosfirebolt.converter.cli.internal.container.BeanContainerFactory;
import com.github.chaosfirebolt.converter.cli.internal.container.ContainerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public class BeanIntrospector implements Introspector {

  private final Supplier<ArgumentsContainer> instanceSupplier;
  private final UniqueNamesValidator uniqueNamesValidator;
  private final Function<List<String>, ?> parser;

  BeanIntrospector(Supplier<ArgumentsContainer> instanceSupplier, UniqueNamesValidator uniqueNamesValidator, Function<List<String>, ?> parser) {
    this.instanceSupplier = instanceSupplier;
    this.uniqueNamesValidator = uniqueNamesValidator;
    this.parser = parser;
  }

  public BeanIntrospector(Supplier<ArgumentsContainer> instanceSupplier, Set<String> argumentNames, Function<List<String>, ?> parser) {
    this(instanceSupplier, new UniqueNamesValidator(argumentNames), parser);
  }

  @Override
  public ContainerFactory introspect(Class<?> clazz) {
    List<ArgumentHandler> handlers = new ArrayList<>();
    for (Field field : clazz.getDeclaredFields()) {
      Argument declaredArgument = field.getAnnotation(Argument.class);
      if (declaredArgument == null) {
        continue;
      }
      uniqueNamesValidator.validate(declaredArgument);

      ArgumentHandler handler = new ArgumentHandler(declaredArgument, new FieldMutator(field), parser);
      handlers.add(handler);
    }
    return new BeanContainerFactory(instanceSupplier, handlers);
  }

  //TODO use intermediate record and stream
}
