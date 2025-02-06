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

package com.github.chaosfirebolt.converter.cli.internal.parse;

import com.github.chaosfirebolt.converter.cli.api.exception.InvalidArgumentsException;

/**
 * Parses argument list to an internal format.
 */
public final class ArgumentParser {

  private final KeyPrefix keyPrefix;

  public ArgumentParser(KeyPrefix keyPrefix) {
    this.keyPrefix = keyPrefix;
  }

  public Options parse(String[] args) {
    if (args.length == 0) {
      return Options.empty();
    }
    String firstArgument = args[0];
    if (keyPrefix.isValue(firstArgument)) {
      throw new InvalidArgumentsException("First argument is not a key: " + firstArgument);
    }
    Options options = Options.of();
    Option.Builder builder = Option.builder().setKey(firstArgument);
    for (int i = 1; i < args.length; i++) {
      String nextArgument = args[i];
      if (keyPrefix.isKey(nextArgument)) {
        options.add(builder.build());
        builder = Option.builder().setKey(nextArgument);
      } else {
        builder.addValue(nextArgument);
      }
    }
    options.add(builder.build());
    return options;
  }
}
