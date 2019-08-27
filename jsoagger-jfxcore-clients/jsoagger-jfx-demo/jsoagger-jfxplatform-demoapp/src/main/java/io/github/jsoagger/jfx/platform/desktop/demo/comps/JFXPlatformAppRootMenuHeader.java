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

package io.github.jsoagger.jfx.platform.desktop.demo.comps;



import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class JFXPlatformAppRootMenuHeader extends VBox implements IBuildable {

  private final Label				label				= new Label("EmaginFX PLATFORM");
  private final HBox				profileActionsPane	= new HBox();
  private AbstractViewController	controller;
  private VLViewComponentXML		configuration;

  /**
   * Constructor
   */
  public JFXPlatformAppRootMenuHeader() {
    label.getStyleClass().add("user-profile-username-label");
    profileActionsPane.getStyleClass().add("user-profile-actions-pane");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.configuration = configuration;
    this.controller = (AbstractViewController) controller;

    NodeHelper.styleClassAddAll(this, configuration);
    getChildren().add(NodeHelper.verticalSpacer());
    getChildren().add(label);
    getChildren().add(NodeHelper.verticalSpacer());
    getChildren().add(profileActionsPane);

    buildActions();
  }


  private void buildActions() {
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }
}
