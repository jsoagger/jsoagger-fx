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

package io.github.jsoagger.jfxcore.engine.components.validation;



import java.util.Locale;

import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.engine.utils.LocaleResolver;

/**
 * VLConstraintMaxLength
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class VLConstraintMaxLength extends VLConstraint {

  private int length;

  // do not forget afterPropertiesSet

  /**
   * Constructor
   *
   * @param max
   */
  public VLConstraintMaxLength() {}


  @Override
  public void afterPropertiesSet() throws Exception {
    Locale locale = LocaleResolver.getLocale();
    if (StringUtils.isNotBlank(errorMessageKey)) {
      Object[] args = {length};
      //this.errorMessage = ms.getMessage(errorMessageKey, args, locale);
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public boolean isValidInputFor(String text) {
    if (text != null) {
      return text.length() < length;
    }

    return true;
  }


  /**
   */
  public int getLength() {
    return length;
  }


  /**
   * Setter of length
   *
   * @param length the length to set
   */
  public void setLength(int length) {
    this.length = length;
  }
}
