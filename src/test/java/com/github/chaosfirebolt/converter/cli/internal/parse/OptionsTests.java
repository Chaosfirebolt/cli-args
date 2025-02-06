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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OptionsTests {

  private static final String KEY = "key";

  private Option option;
  private Options options;

  @BeforeEach
  public void setUp() {
    this.option = Option.builder().setKey(KEY).build();
    this.options = Options.of();
    options.add(option);
  }

  @Test
  public void existingOption_ShouldBeExtractedCorrectly() {
    Optional<Option> result = this.options.get(KEY);
    assertTrue(result.isPresent(), "Existing option not found");
    assertEquals(option, result.get(), "Incorrect option found");
  }

  @Test
  public void nonExistingOption_ShouldReturnEmpty() {
    Optional<Option> result = this.options.get("asd");
    assertTrue(result.isEmpty(), "Non-existing option was found");
  }
}
