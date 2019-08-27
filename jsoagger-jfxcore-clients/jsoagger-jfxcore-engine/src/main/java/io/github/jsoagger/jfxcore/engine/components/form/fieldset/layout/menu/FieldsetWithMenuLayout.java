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


import java.util.List;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IFieldset;
import io.github.jsoagger.jfxcore.api.IFieldsetGroupLayout;
import io.github.jsoagger.jfxcore.api.ISelectableComponent;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Layout fieldsets with a menu on left, on right for selecting its content to display. Only
 * subcomponents of type {@link ISelectableComponent} can be selectable viw the menu.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class FieldsetWithMenuLayout extends HBox implements IFieldsetGroupLayout {

  private ObservableList<IFieldset> fieldsets = FXCollections.observableArrayList();
  private FieldsetSelectorMenu menu;
  private boolean displayGroupSelector;


  /**
   * Constructor
   */
  public FieldsetWithMenuLayout() {
    getStyleClass().add("fieldset-with-menu-wrapper");
    NodeHelper.setHVGrow(this);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setDisplaySelectors(Boolean displayGroupSelector) {
    this.displayGroupSelector = displayGroupSelector;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }


  /**
   * Clear current selection
   */
  public void clearSelection() {
    menu.clearSelection();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void addFieldset(IFieldset fieldset) {
    fieldsets.add(fieldset);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void displayAll() {
    if (displayGroupSelector) {
      menu = new FieldsetSelectorMenu(this);

      menu.managedProperty().bind(menu.visibleProperty());
      menu.setVisible(displayGroupSelector);

      menu.setParentLayout(this);
      menu.buildFrom(fieldsets);
      getChildren().add(0, menu);
    } else {

      VBox layout = new VBox();
      layout.setSpacing(32);
      NodeHelper.setHVGrow(layout);

      getChildren().add(layout);

      for (IFieldset f : fieldsets) {
        List<ISelectableComponent> comps = f.getFieldsetContent().getSelectableComponents();
        for (ISelectableComponent comp : comps) {
          comp.content().setVisible(true);
          layout.getChildren().add(comp.content());
        }
      }
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public ObservableList<IFieldset> getFieldsets() {
    return fieldsets;
  }


  /**
   * Getter of menu
   *
   * @return the menu
   */
  public Node getMenu() {
    return menu;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setRootConfig(VLViewComponentXML fieldsetListConfig) {}
}
