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

package com.github.chaosfirebolt.converter.cli.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to be applied on elements, indicating which arguments to map where.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Argument {

  /**
   * Name of the argument.
   *
   * @return the name of this argument
   */
  String name();

  /**
   * Aliases for the {@link #name()}.
   * Used only if the name is not found.
   * Defaults to no aliases.
   *
   * @return aliases of the name of this argument
   */
  String[] aliases() default {};

  /**
   * Whether this argument is mandatory or not.
   * Will throw an exception if argument is mandatory and is not found.
   * Defaults to false.
   *
   * @return {@code true} if argument is mandatory, {@code false} otherwise
   */
  boolean mandatory() default false;
}
