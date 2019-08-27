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

package io.github.jsoagger.jfxcore.engine.components.menu.ternary;




import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class TernaryMenuTabHeader extends VBox {

  private static final String SECONDARY_MENU_PIN_CONTAINER = "secondary-menu-pin-container";

  /** First row, tab icons */
  private final HBox firstRowLayout = new HBox();
  private final HBox leftTabIconsContainer = new HBox();
  private final HBox rightTabIconsContainer = new HBox();

  /** Title */
  private final HBox secondRowLayout = new HBox();
  private final HBox leftTabTitleContainer = new HBox();
  private final HBox rightTabTitleContainer = new HBox();


  /**
   * Constructor
   */
  public TernaryMenuTabHeader() {

    firstRowLayout.getChildren().addAll(leftTabIconsContainer, NodeHelper.horizontalSpacer(), rightTabIconsContainer);
    secondRowLayout.getChildren().addAll(leftTabTitleContainer, NodeHelper.horizontalSpacer(), rightTabTitleContainer);

    getChildren().addAll(firstRowLayout, secondRowLayout);
    getStyleClass().addAll("ternary-menu-header");
    rightTabIconsContainer.getStyleClass().add(SECONDARY_MENU_PIN_CONTAINER);

    firstRowLayout.setAlignment(Pos.CENTER_LEFT);
    leftTabIconsContainer.setAlignment(Pos.CENTER_LEFT);
    rightTabIconsContainer.setAlignment(Pos.CENTER_LEFT);
    secondRowLayout.setAlignment(Pos.CENTER_LEFT);

    leftTabTitleContainer.setStyle("-fx-padding: 16;");
    rightTabTitleContainer.setStyle("-fx-padding: 16;");
    leftTabIconsContainer.setStyle("-fx-padding: 0 0 0 1;");
    rightTabIconsContainer.setStyle("-fx-padding: 16;");

    NodeHelper.setHVGrow(secondRowLayout);
  }


  /**
   * @param node
   */
  public void addTab(TernaryMenuTab node) {
    if (node != null) {
      leftTabIconsContainer.getChildren().add(node);
    }
  }


  /**
   * @param node
   */
  public void setPinContentActionButton(Node node) {
    Platform.runLater(()->{
      rightTabIconsContainer.getChildren().clear();
      rightTabIconsContainer.getChildren().add(node);
    });
  }


  /**
   * @param title
   */
  public void setTitle(Node title) {
    leftTabTitleContainer.setAlignment(Pos.CENTER_LEFT);
    NodeHelper.setHgrow(leftTabTitleContainer);
    Platform.runLater(()->{
      leftTabTitleContainer.getChildren().clear();
      leftTabTitleContainer.getChildren().add(title);
    });
  }


  /**
   * @param node
   */
  public void setCustomActions(Node node) {
    Platform.runLater(()->{
      rightTabTitleContainer.getChildren().clear();
      rightTabTitleContainer.getChildren().add(node);
    });
  }
}
