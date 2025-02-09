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

import com.github.chaosfirebolt.converter.cli.internal.container.ContainerFactory;

/**
 * Responsible for introspection.
 */
public interface Introspector {

  /**
   * Introspect a class and return a factory for containers.
   * Throws an exception in case of invalid setup.
   *
   * @param clazz class to be introspected
   * @return factory capable of creating a container
   * @throws com.github.chaosfirebolt.converter.cli.api.exception.UnrecoverableException in case of invalid setup
   */
  ContainerFactory introspect(Class<?> clazz);
}
