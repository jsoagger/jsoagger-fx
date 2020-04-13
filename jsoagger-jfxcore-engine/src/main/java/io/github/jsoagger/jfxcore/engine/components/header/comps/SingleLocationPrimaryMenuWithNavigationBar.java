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

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.header.event.HeaderNavbarFireBackButton;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.SetCurrentLocationEvent;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.UpdateCurrentLocationEvent;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * * A header toolbar where primary menu is displayed in the top right side, just on its left is
 * displayed the previous location label and below the primary button is displayed the current
 * location.
 * <p>
 * This navigation bar should be used only in the top header right of the application.
 * <p>
 * For nomwn, this toolbar is not minimizable, but should be in future.
 * <p>
 * The configuration file must declare only one button that is supposed to be the primary button.
 * This button will be layed out automatically on the primary button location.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class SingleLocationPrimaryMenuWithNavigationBar extends VBox
    implements IBuildable, NavigableToolbar, IHeaderToolbar {

  protected final StackPane locationContainer = new StackPane();
  protected Button backIcon = NodeHelper.jfxButton("");
  protected AbstractViewController controller;
  protected IBuildable primaryMenuButton;

  protected final HBox topToolbar = new HBox();
  protected final HBox bottomToolbar = new HBox();


  /**
   * Constructor
   */
  public SingleLocationPrimaryMenuWithNavigationBar() {
    super();

    IconUtils.setHeaderNavigationBack(backIcon);
    backIcon.setOnAction(e -> goBack());
    backIcon.getStyleClass().removeAll("button", "jfx-button", "ep-back-button-on-header");
    backIcon.managedProperty().bind(backIcon.visibleProperty());
    backIcon.setVisible(false);

    final HBox box = NodeHelper.wrapInHbox(backIcon);
    box.getStyleClass().add("ep-navbar-top-toolbar-back-icon-container");
    topToolbar.getChildren().addAll(box, locationContainer);
    getChildren().addAll(topToolbar, bottomToolbar);

    topToolbar.getStyleClass().add("ep-primary-menu-with-navbar-top-toolbar");
    bottomToolbar.getStyleClass().add("ep-primary-menu-with-navbar-bottom-toolbar");
    getStyleClass().add("ep-primary-menu-with-navbar-toolbar");

    // location0.getStyleClass().add("current-location-item-label");

    topToolbar.minWidthProperty().bind(topToolbar.prefWidthProperty());
    topToolbar.minHeightProperty().bind(topToolbar.prefHeightProperty());
    topToolbar.maxHeightProperty().bind(topToolbar.prefHeightProperty());

    bottomToolbar.minWidthProperty().bind(bottomToolbar.prefWidthProperty());
    bottomToolbar.minHeightProperty().bind(bottomToolbar.prefHeightProperty());
    bottomToolbar.maxHeightProperty().bind(bottomToolbar.prefHeightProperty());

    bottomToolbar.managedProperty().bind(bottomToolbar.visibleProperty());
    NodeHelper.setHVGrow(topToolbar, bottomToolbar, this);

    bottomToolbar.setVisible(false);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.controller = (AbstractViewController) controller;
    NodeHelper.styleClassSetAll(backIcon, configuration, "backIconStyleClass", "app-header-button");
    backIcon.getStyleClass().add("hand-hover");
    final List<IBuildable> links = ComponentUtils
        .resolveAndGenerate((AbstractViewController) controller, configuration.getSubcomponents());
    if (links.size() > 1) {
      // prohibited!!!! but ..
    }

    if (links.size() == 1) {
      primaryMenuButton = links.get(0);
      final Node button = primaryMenuButton.getDisplay();
      topToolbar.getChildren().add(1, button);
    }
  }


  @Override
  public void updateLocation(UpdateCurrentLocationEvent current) {
    AbstractViewController c = current.getCurrentView().getCurrentContent();
    Node location = current.getLocationNode();
    if (location != null) {
      NodeHelper.setHgrow(location);
      Platform.runLater(() -> {
        locationContainer.getChildren().clear();
        locationContainer.getChildren().add(location);
      });
    } else {
      final String locationString = c != null && c.getRootComponent() != null
          ? c.getRootComponent().getPropertyValue("location")
          : "";
      if (StringUtils.isNotBlank(locationString)) {
        String translated = c.getLocalised(locationString);
        Label label = new Label(translated);
        label.getStyleClass().add("ep-header-static-location");
        Platform.runLater(() -> {
          locationContainer.getChildren().clear();
          locationContainer.getChildren().add(label);
        });
      }
    }

    final boolean hasPrevious = current.isHasPrevious();
    backIcon.setVisible(hasPrevious);
    if (topToolbar.getChildren().size() == 1) {
      Platform.runLater(() -> {
        topToolbar.getChildren().add(0, NodeHelper.horizontalSpacer());
      });
    }

    if (topToolbar.getChildren().size() == 2) {
      Platform.runLater(() -> {
        topToolbar.getChildren().add(1, NodeHelper.horizontalSpacer());
      });
    }
  }


  @Override
  public void setCurrentLocationTo(SetCurrentLocationEvent event) {
    if (event.getLocation() != null) {
      locationContainer.getChildren().clear();
      locationContainer.getChildren().add(event.getLocation());
    } else {
      if (event.getController() != null && event.getController().getRootComponent() != null) {
        AbstractViewController c = event.getController();
        final String locationString = c.getRootComponent().getPropertyValue("location");
        if (StringUtils.isNotBlank(locationString)) {
          String translated = c.getLocalised(locationString);
          Label label = new Label(translated);
          label.getStyleClass().add("ep-header-static-location");
          Platform.runLater(() -> {
            locationContainer.getChildren().clear();
            locationContainer.getChildren().add(label);
          });
        }
      }
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
    NodeHelper.goBack(controller);
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
    bottomToolbar.setVisible(false);
  }
}
