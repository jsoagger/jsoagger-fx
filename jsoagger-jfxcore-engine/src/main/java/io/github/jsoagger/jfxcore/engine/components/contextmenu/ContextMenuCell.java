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

package io.github.jsoagger.jfxcore.engine.components.contextmenu;




import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * A cell used for list view of master details view.
 *
 * @author Administrator
 *
 */
public class ContextMenuCell extends ListCell<Node> {

  /*-----------------------------------------------------------------------------
  | PRIVATE ATTRIBUTES
  *=============================================================================*/
  protected final HBox rootLayout = new HBox();
  protected final StackPane iconStackPane = new StackPane();
  protected final Label linkLabel = new Label();


  /*-----------------------------------------------------------------------------
  | Public method
  *=============================================================================*/
  /**
   * DefaultModelListCell Constructor
   */
  public ContextMenuCell() {
    super();
  }


  /**
   * Build the content
   */
  protected void buildContent(Node item) {

  }


  @Override
  protected void updateItem(Node item, boolean empty) {
    super.updateItem(item, empty);

    setGraphic(null);
    setText(null);

    if (!empty) {
      rootLayout.setStyle("-fx-pref-height: 40;" + "-fx-border-width: 0;" + "-fx-alignment: CENTER_LEFT; " + "-fx-padding: 16;" + "-fx-spacing: 8;");

      rootLayout.getChildren().add(item);
      setGraphic(rootLayout);
    }
  }
}
