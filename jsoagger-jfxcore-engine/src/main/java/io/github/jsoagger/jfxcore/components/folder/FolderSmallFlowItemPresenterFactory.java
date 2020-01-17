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

package io.github.jsoagger.jfxcore.components.folder;




import java.net.URL;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IIDentifiable;
import io.github.jsoagger.jfxcore.api.IMinimizable;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.presenter.MultiPresenterFactory;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.quickactions.ViewActionFactory;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.FullFlowViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class FolderSmallFlowItemPresenterFactory extends MultiPresenterFactory implements IIDentifiable, IBuildable, IMinimizable {

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
  private Pane rightActionsContainer;
  @FXML
  private Text fullPathLabel;
  @FXML
  private Node centerContainer;

  @FXML
  private Hyperlink loadChildrenLabel;


  /**
   * Constructor
   */
  public FolderSmallFlowItemPresenterFactory() {
    NodeHelper.loadFXML(getLocation(), this);
  }


  protected URL getLocation() {
    return FolderSmallFlowItemPresenterFactory.class.getResource("FolderSmallFlowItemPresenterFactory.fxml");
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
    super.buildFrom(controller, configuration);

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

    // build right actions
    VLViewComponentXML rightActions = ComponentUtils.resolveModel((AbstractViewController) controller, "CellRightActions");
    if (rightActions != null) {
      Node actions = ViewActionFactory.viewActions((AbstractViewController) controller, rightActions, forData);
      rightActionsContainer.getChildren().clear();
      rightActionsContainer.getChildren().add(actions);
    }
  }

  private boolean isLoadingChildren = false;


  protected void loadContent(Event e) {
    if (!isLoadingChildren) {
      FullFlowViewController fullFlowViewController = (FullFlowViewController) controller;
      fullFlowViewController.selectedElementProperty().set(forData);
    } else {
      isLoadingChildren = false;
    }
  }


  protected void loadChidren(Event e) {
    if (!isLoadingChildren) {
      isLoadingChildren = true;
      FullFlowViewController fullFlowViewController = (FullFlowViewController) controller;
      fullFlowViewController.navigate(forData);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public Node getIdentityContainer() {
    return centerContainer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Node getIconContainer() {
    return iconContainer;
  }
}
