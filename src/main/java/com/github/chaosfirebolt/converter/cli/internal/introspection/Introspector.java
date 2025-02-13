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

/**
 * Responsible for introspection.
 */
public interface Introspector {

  /**
   * Introspect a class and return a factory for {@link ArgumentsContainer}s.
   * Throws an exception in case of invalid setup.
   *
   * @param clazz class to be introspected
   * @param <T>   exact type of the argument container class
   * @return factory capable of creating a container
   * @throws UnrecoverableException in case of invalid setup
   */
  <T extends ArgumentsContainer> ContainerFactory<T> introspect(Class<T> clazz) throws UnrecoverableException;
}
