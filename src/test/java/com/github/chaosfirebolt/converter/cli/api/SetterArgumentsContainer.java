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

package com.github.chaosfirebolt.converter.cli.api;

import com.github.chaosfirebolt.converter.cli.api.annotation.Argument;

@SuppressWarnings("unused")
public class SetterArgumentsContainer implements ArgumentsContainer {

  private String string;
  private int integer;
  private CustomClass customClass;

  public String getString() {
    return string;
  }

  @Argument(name = "-s")
  public void setString(String string) {
    this.string = string;
  }

  public int getInteger() {
    return integer;
  }

  @Argument(name = "-i")
  public void setInteger(int integer) {
    this.integer = integer;
  }

  public CustomClass getCustomClass() {
    return customClass;
  }

  @Argument(name = "-c")
  public void setCustomClass(CustomClass customClass) {
    this.customClass = customClass;
  }
}
