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

import com.github.chaosfirebolt.converter.cli.api.exception.UnrecoverableException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Parsed argument. Consists of a key (mandatory) and zero or more values associated with the key.
 */
public final class Option {

  private final String key;
  private final List<String> values;

  private Option(String key, List<String> values) {
    this.key = key;
    this.values = values;
  }

  /**
   * Creates a new {@link Builder}.
   *
   * @return a new instance of builder
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Validates the values using provided validator. The validator must return {@code true} if the values are valid.
   * Creates and throws an exception when values are invalid using provided error factory.
   *
   * @param validator    validator for the values
   * @param errorFactory exception factory
   * @throws NullPointerException if either validator or error factory is null
   */
  public void validate(Predicate<List<String>> validator, BiFunction<String, List<String>, UnrecoverableException> errorFactory) {
    Objects.requireNonNull(validator);
    Objects.requireNonNull(errorFactory);
    boolean isValid = validator.test(values);
    if (!isValid) {
      throw errorFactory.apply(key, values);
    }
  }

  /**
   * Parses the values to the required type using provided parser.
   *
   * @param parser the parser
   * @param <T>    type of the result
   * @return a parsed value
   * @throws NullPointerException if parser is null
   */
  public <T> T parse(Function<List<String>, T> parser) {
    Objects.requireNonNull(parser);
    return parser.apply(values);
  }

  /**
   * Getter for the key.
   *
   * @return the key
   */
  public String key() {
    return key;
  }

  /**
   * Builder for {@link Option}.
   */
  public static final class Builder {

    private String key;
    private final List<String> values;

    private Builder() {
      this.values = new ArrayList<>(3);
    }

    /**
     * Sets the key of this option.
     *
     * @param key the key
     * @return this builder
     */
    public Builder setKey(String key) {
      this.key = key;
      return this;
    }

    /**
     * Adds a value, associated with this key.
     *
     * @param value a value
     * @return this builder
     */
    public Builder addValue(String value) {
      this.values.add(value);
      return this;
    }

    /**
     * Creates a new instance of {@link Option} with the data in this builder.
     * The list of values is copied to an immutable list.
     *
     * @return new instance of option
     * @throws NullPointerException if key is null
     */
    public Option build() {
      return new Option(Objects.requireNonNull(key, "key"), List.copyOf(values));
    }
  }
}
