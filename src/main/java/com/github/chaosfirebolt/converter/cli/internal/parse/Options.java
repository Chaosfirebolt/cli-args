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

import com.github.chaosfirebolt.converter.cli.api.exception.DuplicateArgumentNameException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Container for all parsed {@link Option}s.
 */
public final class Options {

  private final Map<String, Option> options;

  private Options(Map<String, Option> options) {
    this.options = options;
  }

  /**
   * Creates an empty instance.
   *
   * @return an empty instance
   */
  public static Options empty() {
    return new Options(new HashMap<>(0));
  }

  /**
   * Creates non-empty instance.
   *
   * @return non-empty instance
   */
  public static Options of() {
    return new Options(new HashMap<>());
  }

  /**
   * Adds the option to the options.
   *
   * @param option option to add
   */
  public void add(Option option) {
    if (options.containsKey(option.key())) {
      throw new DuplicateArgumentNameException(option.key());
    }
    options.put(option.key(), option);
  }

  /**
   * Gets the option by the specified key and returns an {@link Optional} describing the option associated with the key.
   *
   * @param key key to search by
   * @return an empty optional if there is option for this key, or optional with present value otherwise
   */
  public Optional<Option> get(String key) {
    return Optional.ofNullable(options.get(key));
  }
}
