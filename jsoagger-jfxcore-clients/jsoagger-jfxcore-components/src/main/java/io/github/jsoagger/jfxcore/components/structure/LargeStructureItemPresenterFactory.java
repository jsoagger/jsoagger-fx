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

package io.github.jsoagger.jfxcore.components.structure;


import java.net.URL;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IMinimizable;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.presenter.MultiPresenterFactory;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.quickactions.ViewActionFactory;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class LargeStructureItemPresenterFactory extends MultiPresenterFactory implements IBuildable, IMinimizable {

  private OperationData forData;

  @FXML
  private Pane rootContainer;
  @FXML
  private Pane internalContainer;
  @FXML
  private Pane iconContainer;
  @FXML
  private Pane mainLabelContainer;
  @FXML
  private Pane secondaryLabelContainer;
  @FXML
  private Pane quickActionsContainer;
  @FXML
  private Pane rightActionsContainer;


  /**
   * Constructor
   */
  public LargeStructureItemPresenterFactory() {
    URL location = SmallStructureItemPresenterFactory.class.getResource("LargeStructureItemPresenterFactory.fxml");
    NodeHelper.loadFXML(location, this);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return rootContainer;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.controller = (AbstractViewController) controller;

    if (StringUtils.isNotBlank(extraParameters.get("rootContainerStyleClass"))) {
      rootContainer.getStyleClass().addAll(extraParameters.get("rootContainerStyleClass").split(","));
    }

    // The icon, may be set to null and build it in presenter
    if (iconPresenter != null) {
      Node icon = iconPresenter.provideIcon(controller, configuration, forData);
      if (icon != null) {
        iconContainer.getChildren().clear();
        iconContainer.getChildren().add(icon);
      }
    }

    // The identity information
    if (identityPresenter != null) {
      Node cell = identityPresenter.provideIdentityOf(controller, configuration, forData);
      if (cell != null) {
        if (StringUtils.isNotBlank(extraParameters.get("mainLabelStyleClass"))) {
          cell.getStyleClass().addAll(extraParameters.get("mainLabelStyleClass").split(","));
        }
        mainLabelContainer.getChildren().clear();
        mainLabelContainer.getChildren().add(cell);
      }
    }

    // the secondary label
    if (secondaryLabelPresenter != null) {
      Node secondaryLabel = secondaryLabelPresenter.provideLabel(controller, configuration, forData);
      if (secondaryLabel != null) {
        secondaryLabelContainer.getChildren().clear();
        secondaryLabelContainer.getChildren().add(secondaryLabel);
      }
    }

    // build right actions
    VLViewComponentXML rightActions = ComponentUtils.resolveModel((AbstractViewController) controller, "CellRightActions");
    if (rightActions != null) {
      Node actions = ViewActionFactory.viewActions((AbstractViewController) controller, rightActions, forData);
      rightActionsContainer.getChildren().clear();
      rightActionsContainer.getChildren().add(actions);
    }

    // build more actions
    VLViewComponentXML moreActions = ComponentUtils.resolveModel((AbstractViewController) controller, "CellMiddleActions");
    if (moreActions != null) {
      Node actions = ViewActionFactory.viewActions((AbstractViewController) controller, moreActions, forData);
      quickActionsContainer.getChildren().clear();
      quickActionsContainer.getChildren().add(actions);
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
    iconContainer.setVisible(false);
    iconContainer.setManaged(false);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void maximize() {
    if (iconPresenter != null) {
      iconContainer.setVisible(true);
      iconContainer.setManaged(true);
    }
  }

}
