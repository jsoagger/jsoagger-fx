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

package io.github.jsoagger.jfxcore.engine.model;




import io.github.jsoagger.jfxcore.api.IEnumeratedValueModel;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class EnumeratedValueModel implements IEnumeratedValueModel{

  private static final long serialVersionUID = -6706110560866001034L;

  protected SimpleStringProperty value = new SimpleStringProperty();
  protected SimpleStringProperty savedValue = new SimpleStringProperty();
  protected SimpleStringProperty description = new SimpleStringProperty();

  protected Object sourceModel;


  /**
   * Constructor
   */
  public EnumeratedValueModel() {}


  public static IEnumeratedValueModel empty() {
    final IEnumeratedValueModel val = new EnumeratedValueModel();

    // needs translate
    //val.setValue(rs.getMessage("SELECT_LABEL", null, null));
    val.setValue("SELECT_LABEL");
    val.setSavedValue("__VIDE__");
    return val;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + ((savedValue == null) ? 0 : savedValue.hashCode());
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
    final EnumeratedValueModel other = (EnumeratedValueModel) obj;
    if (savedValue == null) {
      if (other.savedValue != null) {
        return false;
      }
    } else if (!savedValue.equals(other.savedValue)) {
      return false;
    }
    return true;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String toString() {
    return getValue();
  }


  public final SimpleStringProperty valueProperty() {
    return this.value;
  }


  @Override
  public final java.lang.String getValue() {
    return this.valueProperty().get();
  }


  @Override
  public final void setValue(final java.lang.String value) {
    this.valueProperty().set(value);
  }


  @Override
  public final SimpleStringProperty savedValueProperty() {
    return this.savedValue;
  }


  @Override
  public final java.lang.String getSavedValue() {
    return this.savedValueProperty().get();
  }


  @Override
  public final void setSavedValue(final java.lang.String savedValue) {
    this.savedValueProperty().set(savedValue);
  }


  @Override
  public final SimpleStringProperty descriptionProperty() {
    return this.description;
  }


  @Override
  public final java.lang.String getDescription() {
    return this.descriptionProperty().get();
  }


  public final void setDescription(final java.lang.String description) {
    this.descriptionProperty().set(description);
  }

  public static class Builder {

    private SimpleStringProperty value;
    private SimpleStringProperty savedValue;
    private SimpleStringProperty description;


    public Builder value(SimpleStringProperty value) {
      this.value = value;
      return this;
    }


    public Builder savedValue(SimpleStringProperty savedValue) {
      this.savedValue = savedValue;
      return this;
    }


    public Builder description(SimpleStringProperty description) {
      this.description = description;
      return this;
    }


    public EnumeratedValueModel build() {
      return new EnumeratedValueModel(this);
    }
  }


  private EnumeratedValueModel(Builder builder) {
    this.value = builder.value;
    this.savedValue = builder.savedValue;
    this.description = builder.description;
  }


  /**
   * Getter of sourceModel
   *
   * @return the sourceModel
   */
  public Object getSourceModel() {
    return sourceModel;
  }


  /**
   * Setter of sourceModel
   *
   * @param sourceModel the sourceModel to set
   */
  public void setSourceModel(Object sourceModel) {
    this.sourceModel = sourceModel;
  }
}
