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




import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.header.event.HeaderNavbarBackButtonClicked;
import io.github.jsoagger.jfxcore.engine.components.header.event.HeaderNavbarFireBackButton;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.PopStructureContentEvent;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.SetCurrentLocationEvent;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.UpdateCurrentLocationEvent;

import io.github.jsoagger.jfxcore.engine.utils.IconUtils;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class SingleLocationNavigationBar extends HBox implements IBuildable, NavigableToolbar, IHeaderToolbar {

  private StackPane locationContainer = new StackPane();
  protected Button backIcon = new Button();
  protected AbstractViewController controller;


  /**
   * Constructor
   */
  public SingleLocationNavigationBar() {
    super();

    IconUtils.setHeaderNavigationBack(backIcon);
    backIcon.setOnAction(e -> goBack());
    backIcon.getStyleClass().addAll("app-header-button", "simple-button");
    backIcon.managedProperty().bind(backIcon.visibleProperty());
    backIcon.setDisable(true);

    getChildren().addAll(backIcon, locationContainer);

    getStyleClass().add("ep-primary-menu-with-navbar-top-toolbar");
    getStyleClass().add("ep-primary-menu-with-navbar-toolbar");

    managedProperty().bind(visibleProperty());
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.controller = (AbstractViewController) controller;
  }


  @Override
  public void updateLocation(UpdateCurrentLocationEvent current) {
    Node location = current.getLocationNode();
    boolean hasPrevious = current.isHasPrevious();
    backIcon.setDisable(!hasPrevious);

    if (location != null) {
      Platform.runLater(() -> {
        locationContainer.getChildren().clear();
        locationContainer.getChildren().add(location);
      });
    }
  }


  @Override
  public void setCurrentLocationTo(SetCurrentLocationEvent event) {
    if (event.getLocation() != null) {
      locationContainer.getChildren().clear();
      locationContainer.getChildren().add(event.getLocation());
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }


  /**
   * @param ev
   */
  @Override
  public void goBack(HeaderNavbarFireBackButton ev) {
    goBack();
  }


  @Override
  public void goBack() {
    PopStructureContentEvent pop = new PopStructureContentEvent();
    controller.dispatchEvent(pop);

    // must inform structure content that back was clicked
    // it may update its toolbar
    HeaderNavbarBackButtonClicked ev = new HeaderNavbarBackButtonClicked.Builder().build();
    controller.dispatchEvent(ev);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void hideBottomToolbar() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void showBottomToolbar() {}
}
