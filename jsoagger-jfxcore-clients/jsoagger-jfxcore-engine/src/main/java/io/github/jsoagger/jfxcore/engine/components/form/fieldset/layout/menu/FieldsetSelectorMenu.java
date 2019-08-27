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

package io.github.jsoagger.jfxcore.engine.components.form.fieldset.layout.menu;



import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.jfxcore.api.IFieldset;

import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class FieldsetSelectorMenu extends VBox {

  private static final String FIELDSET_SELECTOR_MENU_CSS = "fieldset-selector-menu";

  private List<FiedsetSelectorMenuRow> rows = new ArrayList<>();
  private FieldsetWithMenuLayout parentLayout;


  /**
   * Constructor
   */
  public FieldsetSelectorMenu(FieldsetWithMenuLayout parentLayout) {
    this.parentLayout = parentLayout;
    getStyleClass().add(FIELDSET_SELECTOR_MENU_CSS);
    managedProperty().bind(visibleProperty());

    // do not grow infinitly just wrapp height of current menu
    setMaxHeight(USE_PREF_SIZE);
  }


  /**
   * @param fieldsets
   */
  public synchronized void buildFrom(ObservableList<IFieldset> fieldsets) {
    for (IFieldset fieldset : fieldsets) {
      FiedsetSelectorMenuRow row = new FiedsetSelectorMenuRow();
      row.setParentLayout(parentLayout);
      row.buildFrom(fieldset);
      getChildren().add(row);

      rows.add(row);
    }

    // select first row
    if (rows.size() > 0) {
      rows.get(0).select();
      rows.get(rows.size() - 1).setLast();
    }

    // display only if have more than one selector
    if (rows.size() < 2) {
      setVisible(false);
    }
  }


  /**
   * Getter of rows
   *
   * @return the rows
   */
  public List<FiedsetSelectorMenuRow> getRows() {
    return rows;
  }


  /**
   * Setter of rows
   *
   * @param rows the rows to set
   */
  public void setRows(List<FiedsetSelectorMenuRow> rows) {
    this.rows = rows;
  }


  /**
   * Getter of parentLayout
   *
   * @return the parentLayout
   */
  public FieldsetWithMenuLayout getParentLayout() {
    return parentLayout;
  }


  /**
   * Setter of parentLayout
   *
   * @param parentLayout the parentLayout to set
   */
  public void setParentLayout(FieldsetWithMenuLayout parentLayout) {
    this.parentLayout = parentLayout;
  }


  /**
   * Tell to children to remove all marker of selection
   */
  public void clearSelection() {
    for(FiedsetSelectorMenuRow row: rows) {
      row.clearSelection();
    }
  }
}
