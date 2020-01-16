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

package io.github.jsoagger.jfxcore.engine.components.tablestructure.tree.presenter;



import java.net.URL;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IMinimizable;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.presenter.MultiPresenterFactory;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.quickactions.ViewActionFactory;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class SmallTreeItemPresenterFactory extends MultiPresenterFactory implements IBuildable, IMinimizable {

  private OperationData forData;

  @FXML
  private Pane treeItemRootContainer;
  @FXML
  private Pane treeItemInternalContainer;
  @FXML
  private Pane treeItemIconContainer;
  @FXML
  private Pane treeItemMainLabelContainer;
  @FXML
  private Pane treeItemRightActionsContainer;


  /**
   * Constructor
   */
  public SmallTreeItemPresenterFactory() {
    URL location = SmallTreeItemPresenterFactory.class.getResource("SmallTreeItemPresenterFactory.fxml");
    NodeHelper.loadFXML(location, this);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return treeItemRootContainer;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.controller = (StandardViewController) controller;

    if (StringUtils.isNotBlank(extraParameters.get("rootContainerStyleClass"))) {
      treeItemRootContainer.getStyleClass().addAll(extraParameters.get("rootContainerStyleClass").split(","));
    }

    // The icon, may be set to null and build it in presenter
    if (iconPresenter != null) {
      Node icon = iconPresenter.provideIcon(controller, configuration, forData);
      if (icon != null) {
        treeItemIconContainer.getChildren().clear();
        treeItemIconContainer.getChildren().add(icon);
      }
    }

    // The identity information
    if (identityPresenter != null) {
      Node cell = identityPresenter.provideIdentityOf(controller, configuration, forData);
      if (cell != null) {
        if (StringUtils.isNotBlank(extraParameters.get("mainLabelStyleClass"))) {
          cell.getStyleClass().addAll(extraParameters.get("mainLabelStyleClass").split(","));
        }
        treeItemMainLabelContainer.getChildren().clear();
        treeItemMainLabelContainer.getChildren().add(cell);
      }
    }

    // build right actions
    VLViewComponentXML rightActions = ComponentUtils.resolveModel((AbstractViewController) controller, "CellRightActions");
    if (rightActions != null) {
      Node actions = ViewActionFactory.viewActions((AbstractViewController) controller, rightActions, forData);
      treeItemRightActionsContainer.getChildren().clear();
      treeItemRightActionsContainer.getChildren().add(actions);
    }
  }


  /**
   * Getter of forData
   *
   * @return the forData
   */
  @Override
  public OperationData getForData() {
    return forData;
  }


  /**
   * Setter of forData
   *
   * @param forData the forData to set
   */
  @Override
  public void setForData(OperationData forData) {
    this.forData = forData;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void minimize() {
    treeItemIconContainer.setVisible(false);
    treeItemIconContainer.setManaged(false);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void maximize() {
    if (iconPresenter != null) {
      treeItemIconContainer.setVisible(true);
      treeItemIconContainer.setManaged(true);
    }
  }

}
