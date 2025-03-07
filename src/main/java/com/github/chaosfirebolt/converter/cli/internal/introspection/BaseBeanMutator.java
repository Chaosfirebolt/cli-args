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
import com.github.chaosfirebolt.converter.cli.api.converter.ValueConverter;
import com.github.chaosfirebolt.converter.cli.api.exception.InaccessibleBeanException;
import com.github.chaosfirebolt.converter.cli.internal.parse.Option;

import java.lang.reflect.AccessibleObject;
import java.util.function.BiFunction;

abstract class BaseBeanMutator<T extends AccessibleObject> implements BeanMutator {

  protected final T member;
  private final ValueConverter<Object> converter;
  private final BiFunction<ValueConverter<Object>, TargetClass, OptionParser> optionParserFactory;

  BaseBeanMutator(T member, ValueConverter<Object> converter, BiFunction<ValueConverter<Object>, TargetClass, OptionParser> optionParserFactory) {
    this.member = member;
    this.converter = converter;
    this.optionParserFactory = optionParserFactory;
    allowAccess();
  }

  BaseBeanMutator(T member, ValueConverter<Object> converter) {
    this(member, converter, DelegatingOptionParser::new);
  }

  private void allowAccess() {
    member.setAccessible(true);
  }

  @Override
  public final void mutate(ArgumentsContainer bean, Option option) {
    TargetClass targetClass = resolveTargetClass();
    OptionParser optionParser = optionParserFactory.apply(converter, targetClass);
    Object valueToSet = option.parse(optionParser);
    try {
      doSet(bean, valueToSet);
    } catch (IllegalAccessException exc) {
      throw new InaccessibleBeanException("Unable to set value in bean", exc);
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }
  }

  abstract TargetClass resolveTargetClass();

  abstract void doSet(Object bean, Object value) throws ReflectiveOperationException;
}
