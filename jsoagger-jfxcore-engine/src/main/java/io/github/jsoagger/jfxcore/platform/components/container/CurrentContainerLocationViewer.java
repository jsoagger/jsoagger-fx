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

package io.github.jsoagger.jfxcore.platform.components.container;




import com.google.gson.JsonObject;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.header.CurrentLocationViewer;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * CurrentContainerLocationViewer is a {@link Label} displaying the current Container location of
 * the root structure and its children. Its is used to be displayed on the top header of the
 * structure. There is at most one {@link CurrentLocationViewer} by
 * {@link CurrentContainerLocationViewer}.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class CurrentContainerLocationViewer extends StackPane implements IBuildable {

  private Label label = new Label();


  /**
   * Constructor
   */
  public CurrentContainerLocationViewer() {
    getChildren().add(label);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    RootStructureController rootStructure = null;
    if (controller instanceof RootStructureController) {
      rootStructure = (RootStructureController) controller;
    }

    else {
      rootStructure = (RootStructureController) controller.getRootStructure();
    }

    NodeHelper.styleClassAddAll(label, configuration, "labelStyleClass", "current-container-location-viewer-label");
    JsonObject model = (JsonObject) rootStructure.getModel();
    label.setText(model.get("name").getAsString());
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }
}
