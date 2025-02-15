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
import com.github.chaosfirebolt.converter.cli.api.TestArgumentsContainer;
import com.github.chaosfirebolt.converter.cli.internal.container.ContainerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

public class CachingIntrospectorTests {

  private final Map<Class<?>, ContainerFactory<?>> cache = spy(new HashMap<>());
  private final Introspector delegate = mock();
  private final CachingIntrospector introspector = new CachingIntrospector(cache, delegate);

  @BeforeEach
  void setUp() {
    when(delegate.introspect(any())).thenAnswer(invocation -> mock(ContainerFactory.class));
  }

  @Test
  public void firstInvocation_ShouldInvokeDelegate() {
    firstInvocation();
  }

  private ContainerFactory<?> firstInvocation() {
    return invoke(TestArgumentsContainer.class, 1, 1);
  }

  private <T extends ArgumentsContainer> ContainerFactory<T> invoke(Class<T> clazz, int computeInvocations, int introspectInvocations) {
    ContainerFactory<T> result = introspector.introspect(clazz);
    verify(cache, times(computeInvocations)).computeIfAbsent(any(), any());
    verify(delegate, times(introspectInvocations)).introspect(any());
    return result;
  }

  @Test
  public void secondInvocation_DifferentClass_ShouldInvokeDelegate() {
    firstInvocation();
    invoke(ArgumentsContainer.class, 2, 2);
  }

  @Test
  public void secondInvocation_SameClass_ShouldNotInvokeDelegate() {
    ContainerFactory<?> firstResult = firstInvocation();
    ContainerFactory<?> secondResult = invoke(TestArgumentsContainer.class, 2, 1);
    assertSame(firstResult, secondResult, "Should return same factory for same class");
  }
}
