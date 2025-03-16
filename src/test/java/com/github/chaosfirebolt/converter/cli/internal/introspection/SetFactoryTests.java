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

import com.github.chaosfirebolt.converter.cli.api.exception.CollectionInstantiationException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

public class SetFactoryTests {

  private final SetFactory factory = new SetFactory();

  @ParameterizedTest
  @ValueSource(classes = { List.class, LinkedList.class })
  public void shouldThrowForNonSetTypes(Class<? extends Collection<?>> collectionClass) {
    assertThrows(CollectionInstantiationException.class, () -> factory.instantiate(collectionClass, 3), () -> "ListFactory can't instantiate - " + collectionClass);
  }

  @ParameterizedTest
  @ValueSource(classes = { HashSet.class, TreeSet.class })
  public void shouldThrowForConcreteSetTypes(Class<? extends Collection<?>> collectionClass) {
    assertThrows(CollectionInstantiationException.class, () -> factory.instantiate(collectionClass, 3), () -> "ListFactory can't instantiate - " + collectionClass);
  }

  @ParameterizedTest
  @ValueSource(classes = { Set.class, AbstractSet.class })
  public void shouldSucceedForAbstractSetTypes(Class<? extends Collection<?>> collectionClass) {
    Collection<Object> result = assertDoesNotThrow(() -> factory.instantiate(collectionClass, 3), () -> "ListFactory should instantiate - " + collectionClass);
    assertEquals(HashSet.class, result.getClass(), "Abstract set should be instantiated as HashSet");
  }
}
