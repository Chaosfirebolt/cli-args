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

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TargetClassResolverTests {

  @Test
  public void shouldResolveCorrectly() throws NoSuchFieldException {
    Field field = TestClass.class.getDeclaredField("map");
    TargetClass actual = TargetClassResolver.resolveTarget(field.getGenericType());
    TargetClass expected = new TargetClass(Map.class, List.of(new TargetClass(String.class, List.of()), new TargetClass(List.class, List.of(new TargetClass(Integer.class, List.of())))));
    assertEquals(expected, actual, "Incorrect target class resolution");
  }

  private static final class TestClass {

    private final Map<String, List<Integer>> map = Map.of();
  }
}
