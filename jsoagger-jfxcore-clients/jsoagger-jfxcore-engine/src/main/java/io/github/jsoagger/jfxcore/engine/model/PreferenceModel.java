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




import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;import javafx.beans.property.SimpleStringProperty;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class PreferenceModel extends ObjectModel {

  private static final long serialVersionUID = -3942154140178584395L;

  private SimpleStringProperty key = new SimpleStringProperty();
  private SimpleStringProperty value = new SimpleStringProperty();
  private SimpleStringProperty owner = new SimpleStringProperty();


  /**
   * Constructor
   */
  public PreferenceModel() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  protected void setData(Object newValue) {
    super.setData(newValue);
    ReflectionUIUtils.setPropertyValue(newValue, this, "key");
    ReflectionUIUtils.setPropertyValue(newValue, this, "value");
    ReflectionUIUtils.setPropertyValue(newValue, this, "owner");
  }


  public final SimpleStringProperty keyProperty() {
    return this.key;
  }


  public final java.lang.String getKey() {
    return this.keyProperty().get();
  }


  public final void setKey(final java.lang.String key) {
    this.keyProperty().set(key);
  }


  public final SimpleStringProperty valueProperty() {
    return this.value;
  }


  public final java.lang.String getValue() {
    return this.valueProperty().get();
  }


  public final void setValue(final java.lang.String value) {
    this.valueProperty().set(value);
  }


  public final SimpleStringProperty ownerProperty() {
    return this.owner;
  }


  public final java.lang.String getOwner() {
    return this.ownerProperty().get();
  }


  public final void setOwner(final java.lang.String owner) {
    this.ownerProperty().set(owner);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("PreferenceModel [");
    if (key != null) {
      builder.append("key=");
      builder.append(key);
      builder.append(", ");
    }
    if (value != null) {
      builder.append("value=");
      builder.append(value);
      builder.append(", ");
    }
    if (owner != null) {
      builder.append("owner=");
      builder.append(owner);
    }
    builder.append("]");
    return builder.toString();
  }
}
