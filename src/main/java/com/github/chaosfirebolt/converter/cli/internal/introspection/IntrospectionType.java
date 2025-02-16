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

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

enum IntrospectionType {

  BEAN_FIELD(Integer.MIN_VALUE),
  BEAN_SETTER(Integer.MAX_VALUE);

  private static final Comparator<IntrospectionType> COMPARATOR = Comparator.comparing(type -> type.priority);

  private final int priority;

  IntrospectionType(int priority) {
    this.priority = priority;
  }

  static IntrospectionType highest(IntrospectionType... types) {
    if (isEmpty(types)) {
      throw new IllegalArgumentException("No introspection types specified");
    }
    return Stream.of(types).filter(Objects::nonNull).max(COMPARATOR).orElseThrow(() -> new IllegalArgumentException("No introspection types specified"));
  }

  private static boolean isEmpty(IntrospectionType[] type) {
    return type == null || type.length == 0;
  }
}
