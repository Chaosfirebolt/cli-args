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

final class CollectionMultiValueOptionParser extends BaseOptionParser {

  private final CollectionFactory collectionFactory;

  CollectionMultiValueOptionParser(ValueConverter<Object> converter, TargetClass targetClass, CollectionFactory collectionFactory) {
    super(converter, targetClass);
    this.collectionFactory = collectionFactory;
  }

  CollectionMultiValueOptionParser(ValueConverter<Object> converter, TargetClass targetClass) {
    //TODO set real collection factory
    this(converter, targetClass, null);
  }

  @Override
  public Object parse(List<String> values) {
    //delegation should make sure this cast does not fail
    @SuppressWarnings("unchecked")
    Class<? extends Collection<?>> collectionClass = (Class<? extends Collection<?>>) targetClass.mainType();
    if (isEmpty(values)) {
      return collectionFactory.instantiate(collectionClass, 0);
    }
    Collection<Object> collection = collectionFactory.instantiate(collectionClass, values.size());
    Class<?> componentType = targetClass.parametricTypes().get(0).mainType();
    for (String value : values) {
      collection.add(converter.convert(componentType, value));
    }
    return collection;
  }
}
