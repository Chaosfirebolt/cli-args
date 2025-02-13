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

package com.github.chaosfirebolt.converter.cli.internal.container;

import com.github.chaosfirebolt.converter.cli.api.ArgumentsContainer;
import com.github.chaosfirebolt.converter.cli.internal.introspection.ArgumentHandler;
import com.github.chaosfirebolt.converter.cli.internal.parse.Options;

import java.util.List;
import java.util.function.Supplier;

/**
 * Factory creating java bean type containers - no args constructor, setters.
 */
public class BeanContainerFactory<T extends ArgumentsContainer> implements ContainerFactory<T> {

  private final Supplier<T> instanceSupplier;
  private final List<ArgumentHandler> handlers;

  /**
   * Create an instance with provided arguments.
   *
   * @param instanceSupplier supplier of an instance to work with
   * @param handlers         handlers
   */
  public BeanContainerFactory(Supplier<T> instanceSupplier, List<ArgumentHandler> handlers) {
    this.instanceSupplier = instanceSupplier;
    this.handlers = handlers;
  }

  @Override
  public T createContainer(Options options) {
    T container = instanceSupplier.get();
    for (ArgumentHandler argument : handlers) {
      argument.handle(options, container);
    }
    return container;
  }
}
