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
 * Base exception for fatal errors in cli args parsing.
 */
public abstract class UnrecoverableException extends RuntimeException {

  /**
   * @param message exception message
   */
  protected UnrecoverableException(String message) {
    super(message);
  }

  /**
   * @param message exception message
   * @param cause   causing exception
   */
  protected UnrecoverableException(String message, Throwable cause) {
    super(message, cause);
  }
}
