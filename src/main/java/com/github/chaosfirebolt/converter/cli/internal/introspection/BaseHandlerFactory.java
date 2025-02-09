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

import java.lang.reflect.AccessibleObject;
import java.util.List;
import java.util.function.Function;

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
  public final ArgumentHandler create(Function<List<String>, ?> parser) {
    BeanMutator mutator = createMutator(member);
    return new ArgumentHandler(argument, mutator, parser);
  }

  abstract BeanMutator createMutator(T member);
}
