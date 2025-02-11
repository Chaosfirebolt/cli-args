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

import com.github.chaosfirebolt.converter.cli.api.exception.InvalidArgumentsException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ArgumentParserTests {

  @Test
  public void firstArgIsValue_ShouldThrowException() {
    ArgumentParser parser = new ArgumentParser(new KeyPrefix());
    String[] args = {"asd", "-a", "1"};
    assertThrows(InvalidArgumentsException.class, () -> parser.parse(args), "Exception was not thrown");
  }

  @Test
  public void shouldParseCorrectly() {
    ArgumentParser parser = new ArgumentParser(new KeyPrefix());
    String[] args = {"--a", "b", "-c", "d", "e", "-f"};
    Options options = parser.parse(args);

    List<ExpectedOption> expectedOptions = List.of(
            new ExpectedOption("--a", List.of("b")),
            new ExpectedOption("-c", List.of("d", "e")),
            new ExpectedOption("-f", List.of())
    );
    for (ExpectedOption expectedOption : expectedOptions) {
      Optional<Option> res = options.get(expectedOption.key);
      assertTrue(res.isPresent(), () -> "Expected option not found - " + expectedOption.key);
      Option option = res.get();
      assertEquals(expectedOption.key, option.key(), "Incorrect option found");
      @SuppressWarnings("unchecked")
      List<String> actualValues = (List<String>) option.parse(values -> values);
      assertEquals(expectedOption.values, actualValues, () -> "Incorrectly parsed option - " + expectedOption.key);
    }
  }

  private record ExpectedOption(String key, List<String> values) {
  }
}
