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
import io.github.jsoagger.jfxcore.engine.components.header.event.HeaderNavbarFireBackButton;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.HeaderLessTwoPanesViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.SetCurrentLocationEvent;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.UpdateCurrentLocationEvent;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;
import com.jfoenix.controls.JFXButton;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * Use only with {@link HeaderLessTwoPanesViewController}.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class HeaderLessSingleLocationNavigationBar extends HBox implements IBuildable, NavigableToolbar, IHeaderToolbar {

  protected Button backIcon = new JFXButton();
  protected AbstractViewController controller;
  protected HeaderLessTwoPanesViewController twoPanesViewController;


  /**
   * Constructor
   */
  public HeaderLessSingleLocationNavigationBar() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.controller = (AbstractViewController) controller;

    backIcon = new Button();
    backIcon.setOnAction(e -> goBack());
    IconUtils.setHeaderNavigationBack(backIcon);

    backIcon.getStyleClass().addAll("ep-headerless-app-header-button", "hand-hover");
    backIcon.setDisable(true);
    backIcon.setVisible(true);
    backIcon.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    backIcon.setAlignment(Pos.CENTER);

    getChildren().addAll(backIcon);
    getStyleClass().addAll("hand-hover");
    //getStyleClass().add("ep-primary-menu-with-navbar-top-toolbar");
    //getStyleClass().add("ep-primary-menu-with-navbar-toolbar");
    setAlignment(Pos.CENTER);
    setMinHeight(54);

    addEventFilter(MouseEvent.MOUSE_CLICKED, e->{
    });

  }


  @Override
  public void updateLocation(UpdateCurrentLocationEvent current) {
    boolean hasPrevious = current.isHasPrevious();
    backIcon.setDisable(!hasPrevious);
  }


  @Override
  public void setCurrentLocationTo(SetCurrentLocationEvent event) {
    if (event.getLocation() != null) {
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


  @Override
  public void goBack() {
    if (twoPanesViewController != null) {
      twoPanesViewController.goBack();
    }
  }


  /**
   * @return the twoPanesViewController
   */
  public HeaderLessTwoPanesViewController getTwoPanesViewController() {
    return twoPanesViewController;
  }


  /**
   * @param twoPanesViewController the twoPanesViewController to set
   */
  public void setTwoPanesViewController(HeaderLessTwoPanesViewController twoPanesViewController) {
    this.twoPanesViewController = twoPanesViewController;
  }
}
