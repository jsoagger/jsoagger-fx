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
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;

import javafx.scene.Node;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class HorizontalFlowItemPresenterFactory extends FlowItemPresenterFactory {

  /**
   * Constructor
   */
  public HorizontalFlowItemPresenterFactory() {
    super();
  }

  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);

    NodeHelper.styleClassSetAll(rootContainer, configuration, "rootContainerStyleClass", "ep-horizontal-flow-item-root-container");
    NodeHelper.styleClassSetAll(internalContainer, configuration, "rootInternalContainerStyleClass", "ep-horizontal-flow-item-root-internal-container");
    NodeHelper.styleClassSetAll(iconContainer, configuration, "iconContainerStyleClass", "ep-horizontal-flow-item-icon-container");
    NodeHelper.styleClassSetAll(centerContainer, configuration, "centerContainerStyleClass", "ep-horizontal-flow-item-center-container");
    NodeHelper.styleClassSetAll(mainLabelContainer, configuration, "mainLabelContainerStyleClass", "ep-horizontal-flow-item-main-label-container");
    NodeHelper.styleClassSetAll(secondaryLabelContainer, configuration, "secondaryLableContainerStyleClass", "ep-horizontal-flow-item-secondary-label-container");
    NodeHelper.styleClassSetAll(rightActionsContainer, configuration, "quickActionsContainerStyleClass", "ep-horizontal-flow-item-actions-container");

    iconContainer.getStyleClass().add("hand-mouse-hover");
    centerContainer.getStyleClass().add("hand-mouse-hover");
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
   * @return
   */
  @Override
  public URL getFXMLLocation() {
    final URL location = FlowItemPresenterFactory.class.getResource("HorizontalFlowItemPresenterFactory.fxml");
    return location;
  }
}
