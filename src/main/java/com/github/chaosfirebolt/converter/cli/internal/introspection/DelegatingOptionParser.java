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

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;

class DelegatingOptionParser extends BaseOptionParser {

  DelegatingOptionParser(ValueConverter<Object> converter, TargetClass targetClass) {
    super(converter, targetClass);
  }

  @Override
  public Object parse(List<String> values) {
    OptionParser delegate = resolveTargetType().createParser(converter, targetClass);
    return delegate.parse(values);
  }

  private TargetType resolveTargetType() {
    //TODO extract the enum in own file and make a method to test for match
    Class<?> mainClass = targetClass.mainType();
    if (Iterable.class.isAssignableFrom(mainClass)) {
      return TargetType.ITERABLE;
    }
    if (Collection.class.isAssignableFrom(mainClass)) {
      return TargetType.COLLECTION;
    }
    if (mainClass.isArray()) {
      return TargetType.ARRAY;
    }
    return TargetType.SINGLE;
  }

  private enum TargetType {

    ITERABLE(CollectionMultiValueOptionParser::new),
    COLLECTION(CollectionMultiValueOptionParser::new),
    ARRAY(ArrayMultiValueOptionParser::new),
    SINGLE(SingleValueOptionParser::new);

    private final BiFunction<ValueConverter<Object>, TargetClass, OptionParser> parserFactory;

    TargetType(BiFunction<ValueConverter<Object>, TargetClass, OptionParser> parserFactory) {
      this.parserFactory = parserFactory;
    }

    private OptionParser createParser(ValueConverter<Object> converter, TargetClass targetClass) {
      return parserFactory.apply(converter, targetClass);
    }
  }
}
