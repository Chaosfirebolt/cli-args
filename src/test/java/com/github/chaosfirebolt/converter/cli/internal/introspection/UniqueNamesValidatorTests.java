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

import com.github.chaosfirebolt.converter.cli.api.Argument;
import com.github.chaosfirebolt.converter.cli.api.exception.DuplicateArgumentNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UniqueNamesValidatorTests {

  private final Argument first = mock();
  private final Argument second = mock();
  private final List<Argument> arguments = List.of(first, second);

  private final UniqueNamesValidator validator = new UniqueNamesValidator(new HashSet<>());

  @BeforeEach
  public void setUp() {
    when(first.name()).thenReturn("--first");
    when(first.aliases()).thenReturn(new String[]{"-f"});
  }

  @Test
  public void allUnique_ShouldNotThrowException() {
    when(second.name()).thenReturn("--second");
    when(second.aliases()).thenReturn(new String[]{"-s"});
    assertDoesNotThrow(() -> arguments.forEach(validator::validate), "Validation should succeed without duplicates");
  }

  @Test
  public void duplicates_ShouldThrowException() {
    when(second.name()).thenReturn("--second");
    when(second.aliases()).thenReturn(new String[]{"-f"});
    assertThrows(DuplicateArgumentNameException.class, () -> arguments.forEach(validator::validate), "Validation should fail with duplicates");
  }
}
