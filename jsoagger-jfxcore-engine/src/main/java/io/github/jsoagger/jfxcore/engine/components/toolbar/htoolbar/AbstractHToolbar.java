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

package io.github.jsoagger.jfxcore.engine.components.toolbar.htoolbar;


import java.util.List;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IToolbarHolder;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.toolbar.AbstractToolbar;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public abstract class AbstractHToolbar extends AbstractToolbar {

  /*-----------------------------------------------------------------------------
  | Paths
   *=============================================================================*/
  private static final String LEFT_ACTION_PATH = "LeftActions";
  private static final String CENTER_LEFT_ACTION_PATH = "CenterLeftActions";
  private static final String CENTER_RIGHT_ACTION_PATH = "CenterRightActions";
  private static final String CENTER_ACTION_PATH = "CenterActions";

  /*-----------------------------------------------------------------------------
  | PRIVATE ATTRIBUTES
   *==========================================	===================================*/
  protected final HBox leftSection = new HBox();
  protected final HBox centerSection = new HBox();
  protected final HBox rightSection = new HBox();
  protected final HBox allOverWrapper = new HBox();

  /** need stackpane for wrapping modifying menu */
  protected final StackPane rootContainer = new StackPane();


  /**
   * Constructor
   */
  public AbstractHToolbar() {
    super();
    NodeHelper.setHgrow(allOverWrapper);

    leftSection.getStyleClass().add("h-toolbar");
    centerSection.getStyleClass().add("h-toolbar");
    rightSection.getStyleClass().add("h-toolbar");

    rootContainer.getChildren().add(allOverWrapper);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(AbstractViewController controller, IToolbarHolder toolbarHolder) {
    super.buildFrom(controller, toolbarHolder);
    allOverWrapper.getStyleClass().addAll("h-toolbar-wrapper");
    rootContainer.getStyleClass().add("h-toolbar-extra-wrapper");

    // extract css from configuration
    String styleClass = configuration.getPropertyValue("styleClass");
    if (io.github.jsoagger.core.utils.StringUtils.isNotBlank(styleClass)) {
      allOverWrapper.getStyleClass().addAll(styleClass.split(","));
    }

    buildRootActionsToolbar();
    buildEllypsisMenu();
  }


  /**
   * Build the root actions menu.
   */
  private void buildRootActionsToolbar() {
    if (rootMenuconfiguration != null) {
      VLViewComponentXML leftActions = rootMenuconfiguration.getComponentById("LeftActions").orElse(null);
      if (leftActions != null) {
        List<IBuildable> buildables = buildActionGroup(leftActions);
        for(IBuildable node: buildables) {
          if (node.getDisplay() != null) {
            leftSection.getChildren().add(node.getDisplay());
          }
        }
      }

      VLViewComponentXML centerActions = rootMenuconfiguration.getComponentById("CenterActions").orElse(null);
      if (centerActions != null) {
        List<IBuildable> buildables = buildActionGroup(centerActions);
        for(IBuildable node: buildables) {
          centerSection.getChildren().add(node.getDisplay());
        }
      }

      VLViewComponentXML rightActions = rootMenuconfiguration.getComponentById("RightActions").orElse(null);
      if (rightActions != null) {
        List<IBuildable> buildables = buildActionGroup(rightActions);
        for(IBuildable node: buildables) {
          rightSection.getChildren().add(node.getDisplay());
        }
      }
    }

    boolean addedLeft = false;
    if(!leftSection.getChildren().isEmpty()) {
      allOverWrapper.getChildren().addAll(leftSection);
      if(!AbstractApplicationRunner.isMobile()) {
        allOverWrapper.getChildren().addAll(NodeHelper.horizontalSpacer());
      }
      addedLeft = true;
    }

    if(!centerSection.getChildren().isEmpty()) {
      if(!addedLeft && !AbstractApplicationRunner.isMobile() && !AbstractApplicationRunner.isSimulMobile()) {
        allOverWrapper.getChildren().addAll(NodeHelper.horizontalSpacer());
      }
      allOverWrapper.getChildren().addAll(centerSection);
      if(!AbstractApplicationRunner.isMobile()&& !AbstractApplicationRunner.isSimulMobile()) {
        allOverWrapper.getChildren().addAll(NodeHelper.horizontalSpacer());
      }
    }
    if(!rightSection.getChildren().isEmpty()) {
      allOverWrapper.getChildren().addAll(NodeHelper.horizontalSpacer(), rightSection);
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return rootContainer;
  }
}
