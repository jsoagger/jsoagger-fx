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

package io.github.jsoagger.jfxcore.engine.controller.main.layout.components;



import java.util.List;

import io.github.jsoagger.jfxcore.engine.components.tab.VLTabpane;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;

import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * A tab inside a {@link VLTabpane}
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class TopTabPanedItem extends VBox {

  private final PseudoClass selected = PseudoClass.getPseudoClass("selected");

  private Label title = new Label();
  private RootStructureController currentView;
  private List<RootStructureController> content = null;
  private Node icon;
  private String id;


  /**
   */
  public TopTabPanedItem(String title, Node icon) {

    this.icon = icon;
    this.title.setText(title);

    this.getStyleClass().add("ep-structure-tab-item");
    this.title.getStyleClass().add("ep-structure-tab-item-title");

    getChildren().add(this.title);
  }


  /**
   * Set seletion value of the tab
   *
   * @param value
   */
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
   * @param title the title to set
   */
  public void setTitle(Label title) {
    this.title = title;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }
}
