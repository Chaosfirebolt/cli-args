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
import com.github.chaosfirebolt.converter.cli.internal.parse.Option;

interface BeanMutator {

  /**
   * Sets the value in the bean, using provided option.
   *
   * @param bean   bean to set value in
   * @param option option to extract the parsed value from
   */
  void mutate(ArgumentsContainer bean, Option option);
}
