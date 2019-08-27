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

package io.github.jsoagger.jfxcore.engine.components.listform;




import javafx.beans.property.SimpleBooleanProperty;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ListFormValue implements IListFormValue {

  private String displayedValue;
  private String savedValue;
  private IListFormCellPresenter preferenceItem;
  private final SimpleBooleanProperty selected = new SimpleBooleanProperty();


  /**
   * Constructor
   *
   * @param value
   */
  public ListFormValue(String value) {
    super();
    this.displayedValue = value;
    this.savedValue = value;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public IListFormCellPresenter preferenceItem() {
    return preferenceItem;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setPreferenceItem(IListFormCellPresenter preferenceItem) {
    this.preferenceItem = preferenceItem;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public SimpleBooleanProperty selectedProperty() {
    return selected;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getDisplayedValue() {
    return displayedValue;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getSavedValue() {
    return savedValue;
  }


  /**
   * Get the preferenceItem
   *
   * @return the preferenceItem
   */
  public IListFormCellPresenter getPreferenceItem() {
    return preferenceItem;
  }


  /**
   * Get the selected
   *
   * @return the selected
   */
  public SimpleBooleanProperty getSelected() {
    return selected;
  }


  /**
   * Set the displayedValue
   *
   * @param displayedValue the displayedValue to set
   */
  @Override
  public void setDisplayedValue(String displayedValue) {
    this.displayedValue = displayedValue;
  }


  /**
   * Set the savedValue
   *
   * @param savedValue the savedValue to set
   */
  public void setSavedValue(String savedValue) {
    this.savedValue = savedValue;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (savedValue == null ? 0 : savedValue.hashCode());
    return result;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final ListFormValue other = (ListFormValue) obj;
    if (savedValue == null) {
      if (other.savedValue != null) {
        return false;
      }
    } else if (!savedValue.equals(other.savedValue)) {
      return false;
    }
    return true;
  }


  @Override
  public void setSavedValue(Object value) {
    this.savedValue = value == null ? "" : String.valueOf(value);
  }

}
