/*-
 * ========================LICENSE_START=================================
 * JSoagger 
 * %%
 * Copyright (C) 2019 JSOAGGER
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */

package io.github.jsoagger.jfxcore.engine.components.converter;




import java.util.HashSet;
import java.util.Set;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class StringBooleanConverter extends StringConverter<Boolean> {

  private static final Set<String> trueValues = new HashSet<String>(4);

  private static final Set<String> falseValues = new HashSet<String>(4);

  static {
    trueValues.add("true");
    trueValues.add("on");
    trueValues.add("yes");
    trueValues.add("1");

    falseValues.add("false");
    falseValues.add("off");
    falseValues.add("no");
    falseValues.add("0");
  }


  /**
   * Constructor
   */
  public StringBooleanConverter() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public String toString(Boolean source) {
    if (source == null)
      return "false";
    return source.toString().toLowerCase();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Boolean fromString(String source) {
    String value = source.trim();
    if ("".equals(value)) {
      return null;
    }
    value = value.toLowerCase();

    if (trueValues.contains(value)) {
      return Boolean.TRUE;
    } else if (falseValues.contains(value)) {
      return Boolean.FALSE;
    }
    return Boolean.FALSE;
  }
}
