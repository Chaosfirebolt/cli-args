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
import com.github.chaosfirebolt.converter.cli.api.exception.UnrecoverableException;
import com.github.chaosfirebolt.converter.cli.internal.container.ContainerFactory;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public final class DelegatingIntrospector implements Introspector {

  private final Function<Class<?>, IntrospectionType> introspectionResolver;
  private final Map<IntrospectionType, Introspector> delegates;

  DelegatingIntrospector(Function<Class<?>, IntrospectionType> introspectionResolver, Map<IntrospectionType, Introspector> delegates) {
    this.introspectionResolver = introspectionResolver;
    this.delegates = delegates;
  }

  public DelegatingIntrospector() {
    this(new IntrospectionResolver(), initDelegates());
  }

  private static Map<IntrospectionType, Introspector> initDelegates() {
    EnumMap<IntrospectionType, Introspector> delegates = new EnumMap<>(IntrospectionType.class);
    delegates.put(IntrospectionType.UNKNOWN, new NoOpIntrospector());
    return delegates;
  }

  @Override
  public <T extends ArgumentsContainer> ContainerFactory<T> introspect(Class<T> clazz) throws UnrecoverableException {
    IntrospectionType introspectionType = introspectionResolver.apply(clazz);
    Introspector delegate = delegates.get(introspectionType);
    return delegate.introspect(clazz);
  }

  public void registerFieldTypeIntrospector(Introspector introspector) {
    delegates.put(IntrospectionType.BEAN_FIELD, introspector);
  }

  public void registerSetterIntrospector(Introspector introspector) {
    delegates.put(IntrospectionType.BEAN_SETTER, introspector);
  }

  public void validate() {
    boolean unregisteredTypes = Stream.of(IntrospectionType.values()).anyMatch(type -> !delegates.containsKey(type));
    if (unregisteredTypes) {
      throw new IllegalStateException("There are unregistered introspection types");
    }
  }
}
