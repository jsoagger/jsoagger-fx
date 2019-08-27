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


import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.engine.model.EnumeratedValueModel;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class EnumeratedValueModelConverter extends StringConverter<EnumeratedValueModel> {

  /**
   * Constructor
   */
  public EnumeratedValueModelConverter() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public String toDisplay(String internalValue) {
    if (StringUtils.isEmpty(internalValue))
      return "";
    EnumeratedValueModel model = fromString(internalValue);
    return model.getValue();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String toString(EnumeratedValueModel object) {
    return object != null ? object.getSavedValue() : "";
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public EnumeratedValueModel fromString(String internalVal) {
    if (owner != null) {
      return (EnumeratedValueModel) owner.getEnumeratedValue(internalVal);
    }
    return null;
  }
}
