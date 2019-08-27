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


import io.github.jsoagger.jfxcore.engine.components.form.row.InputComponentWrapper;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public abstract class StringConverter<T> extends javafx.util.StringConverter<T> {

  protected String displayFormat;
  protected String saveFormat;
  protected InputComponentWrapper owner;


  /**
   * Constructor
   */
  public StringConverter() {}


  public String toDisplay(String internalValue) {
    return internalValue;
  }


  /**
   * Constructor
   *
   * @param displayFormat
   * @param saveFormat
   */
  public StringConverter(String displayFormat, String saveFormat) {
    super();
    this.displayFormat = displayFormat;
    this.saveFormat = saveFormat;
  }


  /**
   * Getter of displayFormat
   *
   * @return the displayFormat
   */
  public String getDisplayFormat() {
    return displayFormat;
  }


  /**
   * Setter of displayFormat
   *
   * @param displayFormat the displayFormat to set
   */
  public void setDisplayFormat(String displayFormat) {
    this.displayFormat = displayFormat;
  }


  /**
   * Getter of saveFormat
   *
   * @return the saveFormat
   */
  public String getSaveFormat() {
    return saveFormat;
  }


  /**
   * Setter of saveFormat
   *
   * @param saveFormat the saveFormat to set
   */
  public void setSaveFormat(String saveFormat) {
    this.saveFormat = saveFormat;
  }


  /**
   * Getter of owner
   *
   * @return the owner
   */
  public InputComponentWrapper getOwner() {
    return owner;
  }


  /**
   * Setter of owner
   *
   * @param owner the owner to set
   */
  public void setOwner(InputComponentWrapper owner) {
    this.owner = owner;
  }
}
