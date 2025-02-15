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

import com.github.chaosfirebolt.converter.cli.api.converter.DelegatingValueConverter;
import com.github.chaosfirebolt.converter.cli.api.converter.ValueConverter;
import com.github.chaosfirebolt.converter.cli.internal.introspection.CachingIntrospector;
import com.github.chaosfirebolt.converter.cli.internal.introspection.FieldBeanIntrospector;
import com.github.chaosfirebolt.converter.cli.internal.introspection.Introspector;
import com.github.chaosfirebolt.converter.cli.internal.parse.ArgumentParser;
import com.github.chaosfirebolt.converter.cli.internal.parse.KeyPrefix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Builder for {@link CommandLineArguments}.
 */
public final class CliArgsBuilder {

  private final List<String> prefixes;
  private final DelegatingValueConverter delegatingValueConverter;
  private boolean cacheIntrospection;

  CliArgsBuilder() {
    this.prefixes = new ArrayList<>();
    this.delegatingValueConverter = new DelegatingValueConverter();
    this.cacheIntrospection = false;
  }

  /**
   * Add the specified prefix to the already existing prefixes.
   *
   * @param prefix prefix to add
   * @return this instance
   */
  public CliArgsBuilder addPrefix(String prefix) {
    this.prefixes.add(prefix);
    return this;
  }

  /**
   * Add the specified prefixes to the already existing prefixes.
   *
   * @param prefixes prefixes to add
   * @return this instance
   */
  public CliArgsBuilder addPrefixes(List<String> prefixes) {
    this.prefixes.addAll(prefixes);
    return this;
  }

  /**
   * Add the specified prefixes to the already existing prefixes.
   *
   * @param prefixes prefixes to add
   * @return this instance
   */
  public CliArgsBuilder addPrefixes(String... prefixes) {
    Collections.addAll(this.prefixes, prefixes);
    return this;
  }

  /**
   * Adds the converter for the specified type.
   *
   * @param targetClass class to be converted
   * @param converter   converter the class
   * @param <T>         exact type
   * @return this instance
   */
  public <T> CliArgsBuilder addConverter(Class<T> targetClass, ValueConverter<T> converter) {
    this.delegatingValueConverter.registerConverter(targetClass, converter);
    return this;
  }

  /**
   * Sets whether to cache introspection results.
   * Use {@code true} to enable caching, {@code false} to disable it.
   * Default state is disabled.
   *
   * @param cacheIntrospection flag whether to cache introspection result
   * @return this builder
   */
  public CliArgsBuilder setCacheIntrospection(boolean cacheIntrospection) {
    this.cacheIntrospection = cacheIntrospection;
    return this;
  }

  /**
   * Create new instance of {@link CommandLineArguments} from this builder.
   *
   * @return new instance of command line arguments
   */
  public CommandLineArguments build() {
    KeyPrefix prefix = prefixes.isEmpty() ? new KeyPrefix() : new KeyPrefix(prefixes);
    ArgumentParser argumentParser = new ArgumentParser(prefix);
    Introspector actualIntrospector = createActualIntrospector();
    Introspector introspector = cacheIntrospection ? new CachingIntrospector(new HashMap<>(), actualIntrospector) : actualIntrospector;
    return new CommandLineArguments(argumentParser, introspector);
  }

  private Introspector createActualIntrospector() {
    return new FieldBeanIntrospector(new HashSet<>(), delegatingValueConverter);
  }
}