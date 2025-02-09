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
import com.github.chaosfirebolt.converter.cli.internal.container.BeanContainerFactory;
import com.github.chaosfirebolt.converter.cli.internal.container.ContainerFactory;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

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
    List<ArgumentHandler> handlers = Stream.of(clazz.getDeclaredFields())
            .map(FieldArgumentHandlerFactory::new)
            .filter(ArgumentHandlerFactory::isAnnotated)
            .peek(factory -> uniqueNamesValidator.validate(factory.argument()))
            .map(factory -> factory.create(parser))
            .toList();
    return new BeanContainerFactory(instanceSupplier, handlers);
  }
}
