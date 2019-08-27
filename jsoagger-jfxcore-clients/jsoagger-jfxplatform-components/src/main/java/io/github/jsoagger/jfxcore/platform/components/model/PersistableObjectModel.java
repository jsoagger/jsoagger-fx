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

package io.github.jsoagger.jfxcore.platform.components.model;



import java.text.SimpleDateFormat;

import io.github.jsoagger.jfxcore.engine.model.ObjectModel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * An entry is a data from which the each column of a row is populated.
 *
 * @author Ramilafananana  VONJISOA
 */
public class PersistableObjectModel extends ObjectModel {

  private static final long serialVersionUID = -1409421164795225928L;

  protected final SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
  protected SimpleStringProperty label = new SimpleStringProperty();

  protected IntegerProperty index = new SimpleIntegerProperty();
  protected SimpleStringProperty icon = new SimpleStringProperty();
  protected LongProperty id = new SimpleLongProperty();
  protected final SimpleStringProperty context = new SimpleStringProperty();
  protected final SimpleStringProperty actionIcon = new SimpleStringProperty();
  protected final SimpleStringProperty quickViewIcon = new SimpleStringProperty();


  /**
   * Constructor
   */
  public PersistableObjectModel() {
    super();
  }


  /**
   * @param newValue
   */
  @Override
  protected void setData(Object newValue) {
    try {
      super.setData(newValue);
      setSelected(false);
    } catch (Exception e) {
    }
  }


  public final SimpleStringProperty labelProperty() {
    return this.label;
  }


  public final java.lang.String getLabel() {
    return this.labelProperty().get();
  }


  public final void setLabel(final java.lang.String label) {
    this.labelProperty().set(label);
  }


  public final IntegerProperty indexProperty() {
    return this.index;
  }


  public final int getIndex() {
    return this.indexProperty().get();
  }


  public final void setIndex(final int index) {
    this.indexProperty().set(index);
  }


  public final SimpleStringProperty iconProperty() {
    return this.icon;
  }


  public final java.lang.String getIcon() {
    return this.iconProperty().get();
  }


  public final void setIcon(final java.lang.String icon) {
    this.iconProperty().set(icon);
  }


  public final SimpleStringProperty contextProperty() {
    return this.context;
  }


  public final java.lang.String getContext() {
    return this.contextProperty().get();
  }


  public final void setContext(final java.lang.String context) {
    this.contextProperty().set(context);
  }


  public final SimpleStringProperty actionIconProperty() {
    return this.actionIcon;
  }


  public final java.lang.String getActionIcon() {
    return this.actionIconProperty().get();
  }


  public final void setActionIcon(final java.lang.String actionIcon) {
    this.actionIconProperty().set(actionIcon);
  }


  public final SimpleStringProperty quickViewIconProperty() {
    return this.quickViewIcon;
  }


  public final java.lang.String getQuickViewIcon() {
    return this.quickViewIconProperty().get();
  }


  public final void setQuickViewIcon(final java.lang.String quickViewIcon) {
    this.quickViewIconProperty().set(quickViewIcon);
  }


  public final LongProperty idProperty() {
    return this.id;
  }


  public final long getId() {
    return this.idProperty().get();
  }


  public final void setId(final long id) {
    this.idProperty().set(id);
  }
}
