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

import com.github.chaosfirebolt.converter.cli.api.exception.CollectionInstantiationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Optional;

final class ConcreteTypeCollectionFactory extends BaseCollectionFactory {

  @Override
  void validate(Class<? extends Collection<?>> collectionClass) {
    if (Modifier.isAbstract(collectionClass.getModifiers())) {
      throw new CollectionInstantiationException(getClass().getSimpleName() + " cannot be used for abstract collection types");
    }
  }

  @Override
  Collection<Object> doInstantiate(Class<? extends Collection<?>> collectionClass, int size) {
    return createCollection(collectionClass, size);
  }

  private static Collection<Object> createCollection(Class<? extends Collection<?>> collectionClass, int size) {
    Optional<Constructor<? extends Collection<?>>> sizeConstructor = getConstructor(collectionClass, int.class);
    if (sizeConstructor.isPresent()) {
      return initCollection(sizeConstructor.get(), size);
    }
    Optional<Constructor<? extends Collection<?>>> emptyConstructor = getConstructor(collectionClass);
    if (emptyConstructor.isEmpty()) {
      throw new CollectionInstantiationException("Appropriate constructor not found for - " + collectionClass);
    }
    return initCollection(emptyConstructor.get());
  }

  private static Optional<Constructor<? extends Collection<?>>> getConstructor(Class<? extends Collection<?>> collectionClass, Class<?>... params) {
    try {
      return Optional.of(collectionClass.getDeclaredConstructor(params));
    } catch (NoSuchMethodException exc) {
      return Optional.empty();
    }
  }

  @SuppressWarnings("unchecked")
  private static Collection<Object> initCollection(Constructor<? extends Collection<?>> constructor, Object... params) {
    try {
      return (Collection<Object>) constructor.newInstance(params);
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException exc) {
      throw new CollectionInstantiationException("Unable to create new instance of " + constructor.getDeclaringClass(), exc);
    }
  }
}
