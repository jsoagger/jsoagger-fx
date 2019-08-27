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

package io.github.jsoagger.jfxcore.engine.components.table;



import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class TableSettingModel {

  protected BooleanProperty displayed = new SimpleBooleanProperty();
  protected SimpleStringProperty column = new SimpleStringProperty();
  protected SimpleDoubleProperty width = new SimpleDoubleProperty();

  public TableSettingModel(String column, boolean displayed, Double with) {
    setColumn(column);
    setWidth(with);
    setDisplayed(displayed);
  }

  public SimpleDoubleProperty widthProperty() {
    return width;
  }

  /**
   * @return the width
   */
  public Double getWidth() {
    return width.get();
  }

  /**
   * @param width the width to set
   */
  public void setWidth(Double widthcol) {
    width.set(widthcol);
  }

  public SimpleStringProperty columnProperty() {
    return column;
  }

  /**
   * @return the column
   */
  public String getColumn() {
    return column.get();
  }

  /**
   * @param columnname the column to set
   */
  public void setColumn(String columnname) {
    column.set(columnname);
  }

  public BooleanProperty displayedProperty() {
    return displayed;
  }

  /**
   * @return the displayed
   */
  public boolean getDisplayed() {
    return displayed.get();
  }

  /**
   * @param displayed the displayed to set
   */
  public void setDisplayed(Boolean isdisplayed) {
    displayed.set(isdisplayed);
  }
}
