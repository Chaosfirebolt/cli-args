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

package com.github.chaosfirebolt.converter.cli.internal.parse;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KeyPrefixTests {

  private final KeyPrefix keyPrefix = new KeyPrefix();

  @ParameterizedTest
  @ValueSource(strings = {"--a", "--abc", "-test"})
  public void argIsKey_IsKeyShouldReturnTrue(String arg) {
    assertTrue(keyPrefix.isKey(arg), "Incorrect check for key");
  }

  @ParameterizedTest
  @ValueSource(strings = {"asd", "qwe-rty", "te--st", "123", "gh57i"})
  public void argIsValue_IsKeyShouldReturnFalse(String arg) {
    assertFalse(keyPrefix.isKey(arg), "Incorrect check for key");
  }

  @ParameterizedTest
  @ValueSource(strings = {"--a", "--abc", "-test"})
  public void argIsKey_IsValueShouldReturnFalse(String arg) {
    assertFalse(keyPrefix.isValue(arg), "Incorrect check for value");
  }

  @ParameterizedTest
  @ValueSource(strings = {"asd", "qwe-rty", "te--st", "123", "gh57i"})
  public void argIsValue_IsValueShouldReturnTrue(String arg) {
    assertTrue(keyPrefix.isValue(arg), "Incorrect check for value");
  }
}
