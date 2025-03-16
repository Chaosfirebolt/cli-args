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

import com.github.chaosfirebolt.converter.cli.api.exception.CollectionInstantiationException;

import java.lang.reflect.Modifier;
import java.util.Collection;

abstract class AbstractCollectionFactory extends BaseCollectionFactory {

  @Override
  final void validate(Class<? extends Collection<?>> collectionClass) {
    if (!Modifier.isAbstract(collectionClass.getModifiers())) {
      throw new CollectionInstantiationException(getClass().getSimpleName() + " cannot be used for abstract collection types");
    }
    validateExactType(collectionClass);
  }

  abstract void validateExactType(Class<? extends Collection<?>> collectionClass);
}
