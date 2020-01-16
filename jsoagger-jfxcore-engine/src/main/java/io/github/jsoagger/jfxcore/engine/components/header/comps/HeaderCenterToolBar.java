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

package io.github.jsoagger.jfxcore.engine.components.header.comps;


import java.util.List;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IMinimizable;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.notification.Notification;
import io.github.jsoagger.jfxcore.engine.components.notification.NotificationView;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class HeaderCenterToolBar extends VBox implements IMinimizable, IBuildable, IHeaderToolbar {

  private HBox topToolbar = new HBox();
  private HBox bottomToolbar = new HBox();


  /**
   * Constructor
   */
  public HeaderCenterToolBar() {
    super();

    minWidthProperty().bind(prefWidthProperty());
    maxWidthProperty().bind(prefWidthProperty());

    getChildren().addAll(topToolbar, bottomToolbar);
    NodeHelper.setVgrow(topToolbar, bottomToolbar);

    topToolbar.minWidthProperty().bind(topToolbar.prefWidthProperty());
    topToolbar.maxWidthProperty().bind(topToolbar.prefWidthProperty());
    topToolbar.minHeightProperty().bind(topToolbar.prefHeightProperty());
    topToolbar.maxHeightProperty().bind(topToolbar.prefHeightProperty());

    bottomToolbar.minWidthProperty().bind(bottomToolbar.prefWidthProperty());
    bottomToolbar.maxWidthProperty().bind(bottomToolbar.prefWidthProperty());
    bottomToolbar.minHeightProperty().bind(bottomToolbar.prefHeightProperty());
    bottomToolbar.maxHeightProperty().bind(bottomToolbar.prefHeightProperty());

    bottomToolbar.managedProperty().bind(bottomToolbar.visibleProperty());
    bottomToolbar.setVisible(false);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    List<IBuildable> links = ComponentUtils.resolveAndGenerate((AbstractViewController) controller, configuration.getSubcomponents());
    NodeHelper.styleClassSetAll(topToolbar, configuration, "header-center-toolbar");
    for (final IBuildable action : links) {
      action.getDisplay().setFocusTraversable(false);
      addItem(action.getDisplay());
    }
  }


  /**
   * @param node
   */
  public void addItem(Node node) {
    topToolbar.getChildren().add(node);
  }


  /**
   *
   * @param node
   */
  public void vlAddItem(Node node) {
    topToolbar.getChildren().add(node);
  }


  /**
   * @param o
   */
  public void showNotification(Notification o) {
    final Label label = new Label();
    label.setText(o.getTitle());
    topToolbar.getChildren().clear();
    topToolbar.getChildren().add(label);

    NotificationView.iconify(label, o.getLevel());

    final FadeTransition ft = new FadeTransition(Duration.millis(4000));
    ft.setNode(label);
    ft.setFromValue(1);
    ft.setCycleCount(1);
    ft.setToValue(0.0);
    ft.setAutoReverse(true);
    ft.play();

    label.setStyle("-fx-text-fill: white;" + "-fx-font-size:1.2em;" + "-fx-padding: 4 32 4 32;" + "-fx-background-color: -accent-color;" + "-fx-border-radius: 30;" + "-fx-background-radius:30;");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void minimize() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void maximize() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void hideBottomToolbar() {
    bottomToolbar.setVisible(false);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void showBottomToolbar() {
    bottomToolbar.setVisible(true);
    bottomToolbar.setVisible(false);
  }
}
