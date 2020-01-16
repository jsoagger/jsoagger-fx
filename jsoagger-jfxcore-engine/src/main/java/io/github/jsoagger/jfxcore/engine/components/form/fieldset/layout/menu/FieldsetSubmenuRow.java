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


import io.github.jsoagger.jfxcore.api.ISelectableComponent;

import javafx.css.PseudoClass;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class FieldsetSubmenuRow extends VBox {

  private PseudoClass selected = PseudoClass.getPseudoClass("selected");

  /**
   * The label of the menu, and items count if displayed content has countable elements
   */
  private Label label = new Label();
  protected Label count = new Label();

  private String componentId;
  private FiedsetSelectorMenuRow menuRow;
  private boolean isselected = false;


  /**
   * Constructor
   */
  public FieldsetSubmenuRow() {
    getStyleClass().add("fieldset-selector-submenu-row");
  }


  /**
   * @param fieldset
   */
  public void buildFrom(ISelectableComponent component) {
    this.componentId = component.getComponentId();

    String title = component.getSelectionLabel();
    label.getStyleClass().add("fieldset-selector-submenu-row-label");
    label.setText(title);
    getChildren().add(label);

    addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
      if (!isselected) {
        select();
      }
    });
  }


  /**
   * @param selected
   */
  public void select() {
    menuRow.clearSelection();
    pseudoClassStateChanged(selected, true);
    label.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
    menuRow.select(componentId);
  }


  /**
   * @param b
   * @return
   */
  public void clearSelection() {
    pseudoClassStateChanged(selected, false);
    label.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
  }


  /**
   * @param fiedsetSelectorMenuRow
   */
  public void setParentRow(FiedsetSelectorMenuRow fiedsetSelectorMenuRow) {
    this.menuRow = fiedsetSelectorMenuRow;
  }


  /**
   * Getter of count
   *
   * @return the count
   */
  public Label getCount() {
    return count;
  }
}
