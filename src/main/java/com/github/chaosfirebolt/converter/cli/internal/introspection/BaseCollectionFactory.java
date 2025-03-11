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

import java.util.Collection;

abstract class BaseCollectionFactory implements CollectionFactory {

  @Override
  public final Collection<Object> instantiate(Class<? extends Collection<?>> collectionClass, int size) {
    validate(collectionClass);
    return doInstantiate(collectionClass, size);
  }

  abstract void validate(Class<? extends Collection<?>> collectionClass);

  abstract Collection<Object> doInstantiate(Class<? extends Collection<?>> collectionClass, int size);
}
