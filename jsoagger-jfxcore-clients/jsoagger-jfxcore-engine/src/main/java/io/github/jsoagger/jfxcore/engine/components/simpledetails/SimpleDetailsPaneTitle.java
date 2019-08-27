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

package io.github.jsoagger.jfxcore.engine.components.simpledetails;




import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Administrator
 *
 */
public class SimpleDetailsPaneTitle extends HBox {

  private Label titleLabel = null;
  private Label descriptionLabel = null;

  private final VBox titleDescriptionContainer = new VBox();
  private final HBox rightContainer = new HBox();


  /**
   * Constructor
   */
  public SimpleDetailsPaneTitle() {

    setStyle(
        "-fx-background-color: transparent;" + "-fx-pref-height: 64;" + "-fx-padding: 2em;" + "-fx-border-color: -grey-color-300;" + "-fx-border-width: 0 1 0.4 1;" + "-fx-alignment: CENTER_LEFT;");

    titleDescriptionContainer.setStyle("-fx-background-color: transparent;" + "-fx-spacing: 10;" + "-fx-alignment:CENTER_LEFT;");

    titleLabel = new Label();
    titleLabel.getStyleClass().add("h2");
    titleDescriptionContainer.getChildren().add(titleLabel);

    descriptionLabel = new Label();
    descriptionLabel.setOpacity(0.44);
    descriptionLabel.visibleProperty().bind(descriptionLabel.textProperty().isNotEmpty());
    descriptionLabel.managedProperty().bind(descriptionLabel.visibleProperty());
    titleDescriptionContainer.getChildren().add(descriptionLabel);

    getChildren().add(titleDescriptionContainer);
    getChildren().add(rightContainer);

    NodeHelper.setHVGrow(titleDescriptionContainer);
    NodeHelper.setHgrow(rightContainer);

    rightContainer.setStyle("-fx-alignment:CENTER_RIGHT;");
  }


  public void setLabel(String label) {
    this.titleLabel.setText(label);
  }


  public void setDescription(String desc) {
    this.descriptionLabel.setText(desc);
  }


  public void setRightNode(Node rightNode) {
    NodeHelper.setHVGrow(rightNode);
    rightContainer.getChildren().clear();
    rightContainer.getChildren().add(rightNode);
  }
}
