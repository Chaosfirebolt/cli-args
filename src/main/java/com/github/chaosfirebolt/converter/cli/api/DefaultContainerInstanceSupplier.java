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

import com.github.chaosfirebolt.converter.cli.api.exception.InaccessibleBeanException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

class DefaultContainerInstanceSupplier implements Supplier<ArgumentsContainer> {

  private final Class<? extends ArgumentsContainer> clazz;

  DefaultContainerInstanceSupplier(Class<? extends ArgumentsContainer> clazz) {
    this.clazz = clazz;
  }

  @Override
  public ArgumentsContainer get() {
    try {
      Constructor<? extends ArgumentsContainer> constructor = clazz.getDeclaredConstructor();
      return constructor.newInstance();
    } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException exc) {
      throw new InaccessibleBeanException("Could not instantiate " + clazz, exc);
    }
  }
}
