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
import com.github.chaosfirebolt.converter.cli.api.converter.ValueConverter;
import com.github.chaosfirebolt.converter.cli.internal.container.BeanContainerFactory;
import com.github.chaosfirebolt.converter.cli.internal.container.ContainerFactory;

import java.lang.reflect.AccessibleObject;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

abstract class BeanIntrospector<T extends AccessibleObject> implements Introspector {

  private final Supplier<ArgumentsContainer> instanceSupplier;
  private final UniqueNamesValidator uniqueNamesValidator;
  private final ValueConverter<Object> converter;

  BeanIntrospector(Supplier<ArgumentsContainer> instanceSupplier, UniqueNamesValidator uniqueNamesValidator, ValueConverter<Object> converter) {
    this.instanceSupplier = instanceSupplier;
    this.uniqueNamesValidator = uniqueNamesValidator;
    this.converter = converter;
  }

  BeanIntrospector(Supplier<ArgumentsContainer> instanceSupplier, Set<String> argumentNames, ValueConverter<Object> converter) {
    this(instanceSupplier, new UniqueNamesValidator(argumentNames), converter);
  }

  @Override
  public final ContainerFactory introspect(Class<?> clazz) {
    List<ArgumentHandler> handlers = getAnnotatedElements(clazz)
            .map(this::createArgumentHandlerFactory)
            .filter(ArgumentHandlerFactory::isAnnotated)
            .peek(factory -> uniqueNamesValidator.validate(factory.argument()))
            .map(factory -> factory.create(converter))
            .toList();
    return new BeanContainerFactory(instanceSupplier, handlers);
  }

  abstract Stream<T> getAnnotatedElements(Class<?> clazz);

  abstract ArgumentHandlerFactory createArgumentHandlerFactory(T member);
}
