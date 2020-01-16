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

package io.github.jsoagger.jfxcore.engine.components.presenter;




import java.net.URL;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IIDentifiable;
import io.github.jsoagger.jfxcore.api.IMinimizable;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.quickactions.ViewActionFactory;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class LargeItemPresenterFactory extends MultiPresenterFactory implements IMinimizable, IIDentifiable {

  private OperationData forData;

  @FXML
  protected Pane rootContainer;
  @FXML
  protected Pane internalContainer;
  @FXML
  protected Pane centerContainer;
  @FXML
  protected Pane iconContainer;
  @FXML
  protected Pane mainLabelContainer;
  @FXML
  protected Pane secondaryLabelContainer;
  @FXML
  protected Pane quickActionsContainer;
  @FXML
  protected Pane rightActionsContainer;


  /**
   * Constructor
   */
  public LargeItemPresenterFactory() {
    final URL location = getFXMLLocation();
    NodeHelper.loadFXML(location, this);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public String getIdentifier() {
    String id = (String) forData.getAttributes().get("fullId");
    if (StringUtils.isEmpty(id)) {
      id = (String) forData.getAttributes().get("oid");
      if (StringUtils.isEmpty(id)) {
        id = (String) forData.getAttributes().get("id");
      }
    }
    return StringUtils.isEmpty(id) ? IIDentifiable.super.getIdentifier() : id;
  }


  /**
   * @return
   */
  public URL getFXMLLocation() {
    final URL location = SmallItemPresenterFactory.class.getResource("LargeItemPresenterFactory2.fxml");
    return location;
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

    NodeHelper.styleClassSetAll(rootContainer, configuration, "rootContainerStyleClass", "");

    // The icon, may be set to null and build it in presenter
    if (iconPresenter != null) {
      final Node icon = iconPresenter.provideIcon(controller, configuration, forData);
      if (icon != null) {
        iconContainer.getChildren().clear();
        iconContainer.getChildren().add(icon);
      }
    }

    // The identity information
    if (identityPresenter != null) {
      final Node cell = identityPresenter.provideIdentityOf(controller, configuration, forData);
      if (cell != null) {
        if (StringUtils.isNotBlank(extraParameters.get("mainLabelStyleClass"))) {
          cell.getStyleClass().addAll(extraParameters.get("mainLabelStyleClass").split(","));
        }
        mainLabelContainer.getChildren().clear();
        mainLabelContainer.getChildren().add(cell);
      }
    }

    // the secondary label
    secondaryLabelContainer.managedProperty().bind(Bindings.isNotEmpty(secondaryLabelContainer.getChildren()));
    if (secondaryLabelPresenter != null) {
      final Node secondaryLabel = secondaryLabelPresenter.provideLabel(controller, configuration, forData);
      if (secondaryLabel != null) {
        secondaryLabelContainer.getChildren().clear();
        secondaryLabelContainer.getChildren().add(secondaryLabel);
      }
    }

    // build right actions
    if (rightActionsContainer != null) {
      final VLViewComponentXML rightActions = ComponentUtils.resolveModel((AbstractViewController) controller, "CellRightActions");
      rightActionsContainer.managedProperty().bind(Bindings.isNotEmpty(rightActionsContainer.getChildren()));
      if (rightActions != null) {
        final Node actions = ViewActionFactory.viewActions((AbstractViewController) controller, rightActions, forData);
        rightActionsContainer.getChildren().clear();
        rightActionsContainer.getChildren().add(actions);
        NodeHelper.setHgrow(actions);
      }
    }

    // build more actions
    if (quickActionsContainer != null) {
      final VLViewComponentXML moreActions = ComponentUtils.resolveModel((AbstractViewController) controller, "CellMiddleActions");
      quickActionsContainer.managedProperty().bind(Bindings.isNotEmpty(quickActionsContainer.getChildren()));
      if (moreActions != null) {
        final Node actions = ViewActionFactory.viewActions((AbstractViewController) controller, moreActions, forData);
        quickActionsContainer.getChildren().clear();
        quickActionsContainer.getChildren().add(actions);
      }
    }
  }

  @Override
  public Node getIconContainer() {
    return iconContainer;
  }


  @Override
  public Node getIdentityContainer() {
    return mainLabelContainer;
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
