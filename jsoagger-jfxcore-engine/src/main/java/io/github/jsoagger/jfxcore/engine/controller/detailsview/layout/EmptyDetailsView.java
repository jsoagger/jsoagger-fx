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

package io.github.jsoagger.jfxcore.engine.controller.detailsview.layout;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.detailsview.IEmptyDetailsview;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 17 févr. 2018
 */
public class EmptyDetailsView extends StackPane implements IEmptyDetailsview {

  private Label messageLabel = new Label();


  /**
   * Default Constructor
   */
  public EmptyDetailsView() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    getChildren().add(NodeHelper.verticalSpacer());

    // VLViewComponentXML noContentPaneConfig =
    // configuration.getComponentById("NoContentPane").orElse(null);
    // INoContentPane contentPane =
    // NoContentPaneHelper.buildFrom(controller, noContentPaneConfig);
    // getChildren().add(contentPane.getDisplay());
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }
}
