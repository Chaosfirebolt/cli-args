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

public class TestArgumentsContainer implements ArgumentsContainer {

  @Argument(name = "--string")
  private String string;
  @Argument(name = "--int", aliases = "-i")
  private int integer;
  @Argument(name = "--realNumber", aliases = {"-rn"})
  private Double realNum;
  @Argument(name = "--bool", aliases = "-b", mandatory = true)
  private Boolean bool;
  @Argument(name = "#cc")
  private CustomClass customClass;
  @Argument(name = "\\sb")
  private byte someByte;

  public String getString() {
    return string;
  }

  public void setString(String string) {
    this.string = string;
  }

  public int getInteger() {
    return integer;
  }

  public void setInteger(int integer) {
    this.integer = integer;
  }

  public Double getRealNum() {
    return realNum;
  }

  public void setRealNum(Double realNum) {
    this.realNum = realNum;
  }

  public Boolean getBool() {
    return bool;
  }

  public void setBool(Boolean bool) {
    this.bool = bool;
  }

  public CustomClass getCustomClass() {
    return customClass;
  }

  public void setCustomClass(CustomClass customClass) {
    this.customClass = customClass;
  }

  public byte getSomeByte() {
    return someByte;
  }

  public void setSomeByte(byte someByte) {
    this.someByte = someByte;
  }
}
