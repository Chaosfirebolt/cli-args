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

package com.github.chaosfirebolt.converter.cli.api;

import com.github.chaosfirebolt.converter.cli.internal.introspection.FieldBeanIntrospector;
import com.github.chaosfirebolt.converter.cli.internal.introspection.Introspector;
import com.github.chaosfirebolt.converter.cli.internal.parse.ArgumentParser;
import com.github.chaosfirebolt.converter.cli.internal.parse.KeyPrefix;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public final class CliArgsBuilder {

  private List<String> prefixes;

  CliArgsBuilder() {
    this.prefixes = new ArrayList<>();
  }

  //TODO finish builder

  public CommandLineArguments build() {
    KeyPrefix prefix = prefixes.isEmpty() ? new KeyPrefix() : new KeyPrefix(prefixes);
    ArgumentParser argumentParser = new ArgumentParser(prefix);
    Introspector introspector = new FieldBeanIntrospector(new HashSet<>(), null);
    return new CommandLineArguments(argumentParser, introspector);
  }
}