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

import com.github.chaosfirebolt.converter.cli.api.converter.DelegatingValueConverter;
import com.github.chaosfirebolt.converter.cli.api.converter.ValueConverter;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

abstract class BaseOptionParserTest {

  protected ValueConverter<Object> converter;
  protected TargetClass targetClass;
  protected OptionParser parser;

  @BeforeEach
  public void setUp() {
    this.converter = spy(new DelegatingValueConverter());
    this.targetClass = mock();
    this.parser = createParser(converter, targetClass);
  }

  abstract OptionParser createParser(ValueConverter<Object> converter, TargetClass targetClass);
}
