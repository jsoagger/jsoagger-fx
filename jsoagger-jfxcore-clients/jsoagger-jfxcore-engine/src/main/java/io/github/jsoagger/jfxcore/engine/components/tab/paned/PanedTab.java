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

package io.github.jsoagger.jfxcore.engine.components.tab.paned;



import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class PanedTab extends StackPane {

  /*-----------------------------------------------------------------------------
  | Private fields
  *=============================================================================*/
  private final static String PANED_TAB_ITEM_CONTAINER = "paned-tab-item-container";
  private final static String PANED_TAB_ITEM_TITLE = "paned-tab-item-title";
  private final PseudoClass selected = PseudoClass.getPseudoClass("selected");

  /*-----------------------------------------------------------------------------
  | Private fields
  *=============================================================================*/
  private final Label title;
  private AbstractViewController controller;


  /*-----------------------------------------------------------------------------
  | CONSTRUCTOR
  *=============================================================================*/
  /**
   * Constructor
   *
   * @param config
   * @param text
   */
  public PanedTab(String controllerId, String tabTile) {
    this.title = new Label();
    this.title.setText(tabTile.toUpperCase());
    this.title.getStyleClass().add(PANED_TAB_ITEM_TITLE);

    getStyleClass().add(PANED_TAB_ITEM_CONTAINER);
    setAlignment(Pos.BOTTOM_CENTER);
    NodeHelper.setHVGrow(this);

    getChildren().add(title);

    // create tab content
    // this.controller = ApplicationRootViewUtils.forId(controllerId);
  }


  /*-----------------------------------------------------------------------------
  | PUBLIC METHODS
  *=============================================================================*/
  public void select(boolean value) {
    pseudoClassStateChanged(selected, value);
  }


  /**
   * @return the title
   */
  public Label getTitle() {
    return title;
  }


  /**
   * @return the content
   */
  public Node getContent() {
    if (controller.processedView() == null) {
      // avoid null pointer
      return new StackPane();
    }

    return controller.processedView();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((title == null) ? 0 : title.hashCode());
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
    final PanedTab other = (PanedTab) obj;
    if (title == null) {
      if (other.title != null) {
        return false;
      }
    } else if (!title.getText().equals(other.title.getText())) {
      return false;
    }
    return true;
  }
}
