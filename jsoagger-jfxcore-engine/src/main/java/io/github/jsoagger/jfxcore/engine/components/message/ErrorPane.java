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

package io.github.jsoagger.jfxcore.engine.components.message;




import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ErrorPane extends StackPane {

  private final Text label = new Text();
  private final Label icon = new Label();

  private final StackPane rootContainer = new StackPane();
  private final StackPane iconContainer = new StackPane();


  /**
   * Constructor
   */
  public ErrorPane() {

    rootContainer.setStyle("-fx-background-color: white; " + "-fx-alignment:CENTER;");
    getChildren().add(rootContainer);
    NodeHelper.setHVGrow(rootContainer);

    iconContainer.getChildren().add(icon);
    iconContainer.setStyle("-fx-background-color:white;" + "-fx-alignment:CENTER_LEFT;" + "-icons-color:red");

    label.setText("Oups, error occurs when laoding data :-(.\nIf the problem persist, please contact you admin.");
    label.setStyle("-fx-font-family:'Robotom Medium'; " + "-fx-font-size: 1.4em;" + "-fx-fill:-text-color-900;");

    rootContainer.getChildren().addAll(iconContainer, label, NodeHelper.horizontalSpacer());
  }


  public void setMaterialButton(Node node) {
    iconContainer.getChildren().clear();
    iconContainer.getChildren().add(0, node);
  }
}
