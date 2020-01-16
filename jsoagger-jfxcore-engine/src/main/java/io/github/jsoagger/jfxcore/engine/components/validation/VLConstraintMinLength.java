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



/**
 * VLConstraintMaxLength
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class VLConstraintMinLength extends VLConstraint {

  private int length;

  /**
   *
   */
  public VLConstraintMinLength() {
  }


  /**
   * Constructor
   *
   * @param min
   */
  public VLConstraintMinLength(int min) {
    this.length = min;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public boolean isValidInputFor(String text) {
    if (text != null) {
      return text.length() > length;
    }

    return false;
  }


  /**
   * @return the length
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
