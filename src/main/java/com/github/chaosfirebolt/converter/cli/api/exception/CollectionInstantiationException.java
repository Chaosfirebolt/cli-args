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

package com.github.chaosfirebolt.converter.cli.api.exception;

/**
 * Signal that a {@link java.util.Collection} cannot be instantiated for whatever reason.
 */
public class CollectionInstantiationException extends UnrecoverableException {

  /**
   * @param message exception message
   * @see Exception#Exception(String) parent constructor
   */
  public CollectionInstantiationException(String message) {
    super(message);
  }

  /**
   * @param message exception message
   * @param cause cause of this exception
   * @see Exception#Exception(String, Throwable) parent constructor
   */
  public CollectionInstantiationException(String message, Throwable cause) {
    super(message, cause);
  }
}
