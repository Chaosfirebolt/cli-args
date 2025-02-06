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

import java.util.List;

/**
 * Prefix used to signify that an argument is a key.
 */
public final class KeyPrefix {

  private final List<String> prefixes;

  /**
   * Create new instance with provided prefixes.
   *
   * @param prefixes prefixes marking an argument as a key
   */
  public KeyPrefix(List<String> prefixes) {
    this.prefixes = List.copyOf(prefixes);
  }

  /**
   * Create new instance with default prefixes - "--" and "-".
   */
  public KeyPrefix() {
    this(List.of("--", "-"));
  }

  /**
   * Tests whether the argument is a key.
   *
   * @param argument argument to test
   * @return {@code true} if argument is key, {@code false} otherwise
   */
  public boolean isKey(String argument) {
    return prefixes.stream().anyMatch(argument::startsWith);
  }

  /**
   * Tests whether the argument is a value.
   *
   * @param argument argument to test
   * @return {@code true} if argument is value, {@code false} otherwise
   */
  public boolean isValue(String argument) {
    return !isKey(argument);
  }
}
