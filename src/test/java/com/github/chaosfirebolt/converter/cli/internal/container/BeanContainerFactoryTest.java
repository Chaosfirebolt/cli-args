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

package com.github.chaosfirebolt.converter.cli.internal.container;

import com.github.chaosfirebolt.converter.cli.api.ArgumentsContainer;
import com.github.chaosfirebolt.converter.cli.internal.introspection.ArgumentHandler;
import com.github.chaosfirebolt.converter.cli.internal.parse.Options;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

public class BeanContainerFactoryTest {

  private final Supplier<ArgumentsContainer> instanceSupplier = mock();
  private final List<ArgumentHandler> handlers = List.of(mock(ArgumentHandler.class), mock(ArgumentHandler.class), mock(ArgumentHandler.class));

  @Test
  public void testAlgorithm() {
    ArgumentsContainer container = mock();
    when(instanceSupplier.get()).thenReturn(container);
    Options options = mock(Options.class);

    BeanContainerFactory factory = new BeanContainerFactory(instanceSupplier, handlers);
    ArgumentsContainer result = factory.createContainer(options);

    assertSame(container, result, "Unexpected container returned");
    for (ArgumentHandler handler : handlers) {
      verify(handler, times(1)).handle(options, container);
    }
  }
}
