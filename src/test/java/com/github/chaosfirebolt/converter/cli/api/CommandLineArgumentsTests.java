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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandLineArgumentsTests {

  private CommandLineArguments arguments;

  @BeforeEach
  public void setUp() {
    arguments = CommandLineArguments.builder()
            .addPrefixes("--", "-")
            .addPrefix("#")
            .addPrefixes(List.of("\\"))
            .addConverter(CustomClass.class, new CustomClassConverter())
            .build();
  }

  @Test
  public void parseCorrectly() {
    String string = "test";
    int integer = 11;
    double doubleValue = 12.34;
    boolean boolValue = true;
    String customClassValue = "test";
    byte someByte = 123;
    String[] args = {
            "--string", string,
            "-i", Integer.toString(integer),
            "-rn", Double.toString(doubleValue),
            "-b", Boolean.toString(boolValue),
            "#cc", customClassValue,
            "\\sb", Byte.toString(someByte)
    };

    TestArgumentsContainer container = arguments.parse(TestArgumentsContainer.class, args);
    assertEquals(string, container.getString(), "String parsed incorrectly");
    assertEquals(integer, container.getInteger(), "Integer parsed incorrectly");
    assertEquals(doubleValue, container.getRealNum(), "Double parsed incorrectly");
    assertTrue(container.getBool(), "Boolean parsed incorrectly");
    assertEquals(new CustomClass(customClassValue), container.getCustomClass(), "Custom class parsed incorrectly");
    assertEquals(someByte, container.getSomeByte(), "Byte parsed incorrectly");
  }
}
