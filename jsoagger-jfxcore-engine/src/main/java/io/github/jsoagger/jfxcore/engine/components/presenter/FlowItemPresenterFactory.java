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
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class FlowItemPresenterFactory extends LargeItemPresenterFactory {

  /**
   * Constructor
   */
  public FlowItemPresenterFactory() {
    super();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);

    NodeHelper.styleClassAddAll(rootContainer, configuration, "rootContainerStyleClass", "ep-flow-item-root-container");
    NodeHelper.styleClassAddAll(internalContainer, configuration, "rootInternalContainerStyleClass", "ep-flow-item-root-internal-container");
    NodeHelper.styleClassAddAll(iconContainer, configuration, "iconContainerStyleClass", "ep-flow-item-icon-container");
    NodeHelper.styleClassAddAll(centerContainer, configuration, "centerContainerStyleClass", "ep-flow-item-center-container");
    NodeHelper.styleClassAddAll(mainLabelContainer, configuration, "mainLabelContainerStyleClass", "ep-flow-item-main-label-container");
    NodeHelper.styleClassAddAll(secondaryLabelContainer, configuration, "secondaryLableContainerStyleClass", "ep-flow-item-secondary-label-container");
    NodeHelper.styleClassAddAll(rightActionsContainer, configuration, "quickActionsContainerStyleClass", "ep-flow-item-actions-container");
  }


  /**
   * @return
   */
  @Override
  public URL getFXMLLocation() {
    final URL location = FlowItemPresenterFactory.class.getResource("FlowItemPresenterFactory.fxml");
    return location;
  }
}
