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
import com.github.chaosfirebolt.converter.cli.api.exception.InaccessibleBeanException;
import com.github.chaosfirebolt.converter.cli.api.exception.UnrecoverableException;
import com.github.chaosfirebolt.converter.cli.internal.container.BeanContainerFactory;
import com.github.chaosfirebolt.converter.cli.internal.container.ContainerFactory;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

abstract class BeanIntrospector<A extends AccessibleObject> implements Introspector {

  private final UniqueNamesValidator uniqueNamesValidator;
  private final ValueConverter<Object> converter;

  BeanIntrospector(UniqueNamesValidator uniqueNamesValidator, ValueConverter<Object> converter) {
    this.uniqueNamesValidator = uniqueNamesValidator;
    this.converter = converter;
  }

  BeanIntrospector(Set<String> argumentNames, ValueConverter<Object> converter) {
    this(new UniqueNamesValidator(argumentNames), converter);
  }

  @Override
  public <T extends ArgumentsContainer> ContainerFactory<T> introspect(Class<T> clazz) throws UnrecoverableException {
    List<ArgumentHandler> handlers = getAnnotatedElements(clazz)
            .map(this::createArgumentHandlerFactory)
            .filter(ArgumentHandlerFactory::isAnnotated)
            .peek(factory -> uniqueNamesValidator.validate(factory.argument()))
            .map(factory -> factory.create(converter))
            .toList();
    Supplier<T> instanceSupplier = instanceSupplier(clazz);
    return new BeanContainerFactory<>(instanceSupplier, handlers);
  }

  abstract Stream<A> getAnnotatedElements(Class<?> clazz);

  abstract ArgumentHandlerFactory createArgumentHandlerFactory(A member);

  <T extends ArgumentsContainer> Supplier<T> instanceSupplier(Class<T> clazz) {
    return () -> {
      try {
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        return constructor.newInstance();
      } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException exc) {
        throw new InaccessibleBeanException("Could not instantiate " + clazz, exc);
      }
    };
  }
}
