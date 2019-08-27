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

package io.github.jsoagger.jfxcore.engine.components.list.utils;




import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * @author Administrator
 *
 */
public class LoadingPane extends StackPane {

  private final Label label = new Label();
  private final Label icon = new Label();

  private final HBox rootContainer = new HBox();
  private final HBox iconContainer = new HBox();


  /**
   * Constructor
   */
  public LoadingPane() {

    setStyle("-fx-padding:64 32 32 32 64;");
    rootContainer.setStyle("-fx-min-height: 128;-fx-background-color: white; " +
        "-fx-border-color: transparent transparent -external-border-color -accent-color;" + "-fx-border-width: 0 0 0.2 6;"
        + "-fx-alignment:CENTER;" + "-fx-padding:16;");

    NodeHelper.setHVGrow(iconContainer);
    iconContainer.getChildren().add(icon);
    iconContainer.setStyle("-fx-background-color:white;" + "-fx-alignment:CENTER_LEFT;" + "-fx-padding:16 64 16 64");

    label.setText("I am loading datas :-)");
    label.setWrapText(true);
    label.setStyle("-fx-font-family:'Robotom Medium'; " + "-fx-font-size: 1.6em;" + "-fx-text-fill:-grey-color-600;");

    rootContainer.getChildren().addAll(NodeHelper.horizontalSpacer(), label, iconContainer, NodeHelper.horizontalSpacer());
    NodeHelper.setHgrow(rootContainer);
    getChildren().add(rootContainer);
  }


  public void setMaterialButton(Node node) {
    iconContainer.getChildren().clear();
    iconContainer.getChildren().add(0, node);
  }
}
