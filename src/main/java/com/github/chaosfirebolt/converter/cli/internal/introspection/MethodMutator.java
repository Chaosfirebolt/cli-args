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

import com.github.chaosfirebolt.converter.cli.api.converter.ValueConverter;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

class MethodMutator extends BaseBeanMutator<Method> {

  MethodMutator(Method member, ValueConverter<Object> converter) {
    super(member, converter);
  }

  @Override
  TargetClass resolveTargetClass() {
    //methods which do not have exactly 1 parameter are filtered out
    Parameter parameter = member.getParameters()[0];
    Type parameterType = parameter.getParameterizedType();
    return TargetClassResolver.resolveTarget(parameterType);
  }

  @Override
  void doSet(Object bean, Object value) throws ReflectiveOperationException {
    member.invoke(bean, value);
  }
}
