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
import com.github.chaosfirebolt.converter.cli.api.converter.ValueConverter;

import java.lang.reflect.AccessibleObject;

abstract class BaseHandlerFactory<T extends AccessibleObject> implements ArgumentHandlerFactory {

  private final Argument argument;
  private final T member;

  BaseHandlerFactory(T member) {
    this.argument = member.getAnnotation(Argument.class);
    this.member = member;
  }

  @Override
  public Argument argument() {
    return argument;
  }

  @Override
  public final ArgumentHandler create(ValueConverter<Object> converter) {
    BeanMutator mutator = createMutator(member, converter);
    return new ArgumentHandler(argument, mutator);
  }

  abstract BeanMutator createMutator(T member, ValueConverter<Object> converter);
}
