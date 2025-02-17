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

import com.github.chaosfirebolt.converter.cli.api.TestArgumentsContainer;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DelegatingIntrospectorTests {

  @Test
  public void notAllIntrospectionTypesAreRegistered_ShouldThrowIllegalStateException() {
    DelegatingIntrospector introspector = new DelegatingIntrospector();
    assertThrows(IllegalStateException.class, introspector::validate, "Not all introspection types are registered");
  }

  @Test
  public void allIntrospectionTypesAreRegistered_ShouldThrowIllegalStateException() {
    DelegatingIntrospector introspector = new DelegatingIntrospector();
    introspector.registerSetterIntrospector(mock());
    introspector.registerFieldTypeIntrospector(mock());
    assertDoesNotThrow(introspector::validate, "All introspection types are registered, should not throw");
  }

  @Test
  public void verifyIntrospectBehaviour() {
    IntrospectionResolver resolver = mock();
    final IntrospectionType expectedIntrospectionType = IntrospectionType.BEAN_FIELD;
    when(resolver.apply(any())).thenReturn(expectedIntrospectionType);

    Introspector fieldIntrospector = mock();
    Introspector setterIntrospector = mock();
    Map<IntrospectionType, Introspector> delegates = mock();
    when(delegates.get(IntrospectionType.BEAN_FIELD)).thenReturn(fieldIntrospector);
    when(delegates.get(IntrospectionType.BEAN_SETTER)).thenReturn(setterIntrospector);

    DelegatingIntrospector delegatingIntrospector = new DelegatingIntrospector(resolver, delegates);

    delegatingIntrospector.introspect(TestArgumentsContainer.class);

    verify(resolver, times(1)).apply(TestArgumentsContainer.class);
    verify(delegates, times(1)).get(expectedIntrospectionType);
    verify(fieldIntrospector, times(1)).introspect(TestArgumentsContainer.class);
    verify(setterIntrospector, never()).introspect(any());
  }
}
