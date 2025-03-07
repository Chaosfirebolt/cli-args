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

import com.github.chaosfirebolt.converter.cli.api.exception.InaccessibleBeanException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class TargetClassResolver {

  private static final Map<String, Class<?>> PRIMITIVE_NAME_TO_WRAPPER = Map.of(
          "int", Integer.class,
          "byte", Byte.class,
          "short", Short.class,
          "long", Long.class,
          "double", Double.class,
          "float", Float.class,
          "boolean", Boolean.class,
          "char", Character.class,
          "void", Void.class
  );

  private TargetClassResolver() {
  }

  static TargetClass resolveTarget(Type genericType) {
    if (genericType instanceof Class<?> clazz && clazz.isArray()) {
      return new TargetClass(clazz, List.of(resolveTarget(clazz.getComponentType())));
    }
    if (genericType instanceof ParameterizedType parameterizedType) {
      Type rawType = parameterizedType.getRawType();
      List<TargetClass> parametricTypes = new ArrayList<>();
      for (Type type : parameterizedType.getActualTypeArguments()) {
        parametricTypes.add(resolveTarget(type));
      }
      return new TargetClass(loadClass(rawType), parametricTypes);
    }
    return new TargetClass(loadClass(genericType), List.of());
  }

  private static Class<?> loadClass(Type type) {
    Class<?> primitiveWrapperClass = PRIMITIVE_NAME_TO_WRAPPER.get(type.getTypeName());
    if (primitiveWrapperClass != null) {
      //Class object for primitives cannot be created using forName()
      //fallback to the class object for wrappers
      return primitiveWrapperClass;
    }
    try {
      return Class.forName(type.getTypeName());
    } catch (ClassNotFoundException exc) {
      throw new InaccessibleBeanException("Could not load class " + type.getTypeName(), exc);
    }
  }
}
