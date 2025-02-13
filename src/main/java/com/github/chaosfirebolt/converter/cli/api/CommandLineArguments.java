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

import com.github.chaosfirebolt.converter.cli.internal.container.ContainerFactory;
import com.github.chaosfirebolt.converter.cli.internal.introspection.Introspector;
import com.github.chaosfirebolt.converter.cli.internal.parse.ArgumentParser;
import com.github.chaosfirebolt.converter.cli.internal.parse.Options;

/**
 * Class parsing command line arguments, according to the configuration.
 */
public final class CommandLineArguments {

  private final ArgumentParser argumentParser;
  private final Introspector introspector;

  CommandLineArguments(ArgumentParser argumentParser, Introspector introspector) {
    this.argumentParser = argumentParser;
    this.introspector = introspector;
  }

  /**
   * Creates a new {@link CliArgsBuilder}.
   *
   * @return new builder instance
   */
  public static CliArgsBuilder builder() {
    return new CliArgsBuilder();
  }

  /**
   * Parses the provided arguments to the target class .
   *
   * @param targetClass class to parse the arguments into
   * @param args        arguments to parse
   * @param <T>         exact type to parse into
   * @return an instance of arguments container
   */
  public <T extends ArgumentsContainer> T parse(Class<T> targetClass, String... args) {
    Options options = argumentParser.parse(args);
    ContainerFactory<T> containerFactory = introspector.introspect(targetClass);
    return containerFactory.createContainer(options);
  }
}
