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

package com.github.chaosfirebolt.converter.cli.api;

import com.github.chaosfirebolt.converter.cli.api.converter.ValueConverter;
import com.github.chaosfirebolt.converter.cli.internal.container.ContainerFactory;
import com.github.chaosfirebolt.converter.cli.internal.introspection.FieldBeanIntrospector;
import com.github.chaosfirebolt.converter.cli.internal.introspection.Introspector;
import com.github.chaosfirebolt.converter.cli.internal.parse.ArgumentParser;
import com.github.chaosfirebolt.converter.cli.internal.parse.KeyPrefix;
import com.github.chaosfirebolt.converter.cli.internal.parse.Options;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public final class CommandLineArguments {

  private final ArgumentParser argumentParser;
  private final Map<Class<?>, Supplier<ArgumentsContainer>> instanceSuppliers;
  private final ValueConverter<Object> converter;
  private final Set<String> argumentNames;
  private final Introspector introspector;

  CommandLineArguments(KeyPrefix keyPrefix, Map<Class<?>, Supplier<ArgumentsContainer>> instanceSuppliers, ValueConverter<Object> converter) {
    this.argumentParser = new ArgumentParser(keyPrefix);
    this.instanceSuppliers = instanceSuppliers;
    this.converter = converter;
    this.argumentNames = new HashSet<>();
    this.introspector = new FieldBeanIntrospector(instanceSuppliers, argumentNames, converter);
  }

  public static CliArgsBuilder builder() {
    return new CliArgsBuilder();
  }

  public List<ArgumentsContainer> parse(String... args) {
    Options options = argumentParser.parse(args);
    return instanceSuppliers.keySet()
            .stream()
            .map(introspector::introspect)
            .map(containerFactory -> containerFactory.createContainer(options))
            .toList();
  }

  public <T extends ArgumentsContainer> T parse(Class<T> clazz, String... args) {
    //TODO enhance introspector with registration, modifying the map outside is bad
    instanceSuppliers.computeIfAbsent(clazz, cl -> new DefaultContainerInstanceSupplier(clazz));
    Options options = argumentParser.parse(args);
    ContainerFactory containerFactory = introspector.introspect(clazz);
    @SuppressWarnings("unchecked")
    T container = (T) containerFactory.createContainer(options);
    return container;
  }
}
