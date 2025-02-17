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

class TestInstantiationArgsContainer implements ArgumentsContainer {

  private final int integer;
  private final String string;
  private final boolean bool;

  TestInstantiationArgsContainer(int integer, String string, boolean bool) {
    this.integer = integer;
    this.string = string;
    this.bool = bool;
  }

  @SuppressWarnings("unused")
  TestInstantiationArgsContainer(int integer, String string) {
    //used for the test with reflective instantiation
    this(integer, string, true);
  }

  int getInteger() {
    return integer;
  }

  String getString() {
    return string;
  }

  boolean isBool() {
    return bool;
  }
}
