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
import com.github.chaosfirebolt.converter.cli.api.exception.MissingMandatoryArgumentException;
import com.github.chaosfirebolt.converter.cli.internal.parse.Option;
import com.github.chaosfirebolt.converter.cli.internal.parse.Options;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Set argument value in a bean.
 */
public final class ArgumentHandler {

  private final Argument argument;
  private final BeanMutator mutator;
  private final Function<List<String>, ?> parser;

  ArgumentHandler(Argument argument, BeanMutator mutator, Function<List<String>, ?> parser) {
    this.argument = argument;
    this.mutator = mutator;
    this.parser = parser;
  }

  /**
   * Extracts the value from the parsed arguments and sets it in the container.
   *
   * @param options   parsed arguments
   * @param container container
   * @throws MissingMandatoryArgumentException if the argument is mandatory and is not found
   */
  public void handle(Options options, ArgumentsContainer container) {
    Optional<Option> extractedOption = extract(options);
    extractedOption
            .map(option -> option.parse(parser))
            .ifPresent(value -> mutator.set(container, value));
  }

  private Optional<Option> extract(Options options) {
    Optional<Option> option = options.get(argument.name()).or(() -> findByAliases(options));
    if (option.isEmpty() && argument.mandatory()) {
      throw new MissingMandatoryArgumentException(argument.name());
    }
    return option;
  }

  private Optional<Option> findByAliases(Options options) {
    for (String alias : argument.aliases()) {
      Optional<Option> option = options.get(alias);
      if (option.isPresent()) {
        return option;
      }
    }
    return Optional.empty();
  }
}
