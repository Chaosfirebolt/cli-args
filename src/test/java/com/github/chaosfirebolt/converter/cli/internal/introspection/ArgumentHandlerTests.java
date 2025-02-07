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
import com.github.chaosfirebolt.converter.cli.api.ArgumentsContainer;
import com.github.chaosfirebolt.converter.cli.api.exception.MissingMandatoryArgumentException;
import com.github.chaosfirebolt.converter.cli.internal.parse.Option;
import com.github.chaosfirebolt.converter.cli.internal.parse.Options;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ArgumentHandlerTests {

  private Argument argument;
  private BeanMutator beanMutator;

  private final Options options = mock(Options.class);
  private final ArgumentsContainer container = mock();

  private final Object parseResult = new Object();
  private ArgumentHandler argumentHandler;

  @BeforeEach
  public void setUp() {
    this.argument = mock();
    this.beanMutator = mock();
    Function<List<String>, Object> parser = mock();

    when(argument.name()).thenReturn("--name");
    when(argument.aliases()).thenReturn(new String[]{"-n"});

    doNothing().when(beanMutator).set(any(), any());

    when(parser.apply(any())).thenReturn(parseResult);

    this.argumentHandler = new ArgumentHandler(argument, beanMutator, parser);
  }

  @Test
  public void nonMandatory_NameFound_ShouldInvoke() {
    when(argument.mandatory()).thenReturn(false);
    when(options.get("--name")).thenReturn(Optional.of(Option.builder().setKey("--name").addValue("").build()));

    argumentHandler.handle(options, container);
    verify(beanMutator, times(1)).set(container, parseResult);
  }

  @Test
  public void nonMandatory_AliasFound_ShouldInvoke() {
    when(argument.mandatory()).thenReturn(false);
    when(options.get("-n")).thenReturn(Optional.of(Option.builder().setKey("-n").addValue("").build()));

    argumentHandler.handle(options, container);
    verify(beanMutator, times(1)).set(container, parseResult);
  }

  @Test
  public void nonMandatory_NotFound_ShouldNotInvoke() {
    when(argument.mandatory()).thenReturn(false);

    argumentHandler.handle(options, container);
    verify(beanMutator, never()).set(any(), any());
  }

  @Test
  public void mandatory_NotFound_ShouldThrowException() {
    when(argument.mandatory()).thenReturn(true);
    assertThrows(MissingMandatoryArgumentException.class, () -> argumentHandler.handle(options, container), "Exception not thrown for missing mandatory argument");
  }
}
