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

package io.github.jsoagger.jfxcore.engine.components.message;




import java.util.Iterator;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.INoContentPane;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * No content pane with list of grouped actions
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ToolbarNoContentPane extends HBox implements INoContentPane {

  protected String labelStyleClass = null;


  /**
   * Constructor
   */
  public ToolbarNoContentPane() {
    setAlignment(Pos.CENTER_RIGHT);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    String styleClass = configuration.getPropertyValue("styleClass");
    if (io.github.jsoagger.core.utils.StringUtils.isBlank(styleClass)) {
      styleClass = "no-content-pane";
    }
    getStyleClass().addAll(styleClass.split(","));

    if (configuration.hasSubComponent()) {
      Iterator<VLViewComponentXML> iterator = configuration.getSubcomponents().iterator();
      while (iterator.hasNext()) {
        VLViewComponentXML config = iterator.next();
        Node node = buildGroup((AbstractViewController) controller, config);
        getChildren().add(node);
      }
    }

    NodeHelper.setHVGrow(this);
  }



  protected Node buildGroup(AbstractViewController controller, VLViewComponentXML groupConfig) {
    Node actionNode = null;

    // The action node
    String actionNodeId = groupConfig.getPropertyValue("actionNode");
    VLViewComponentXML resolved = ComponentUtils.resolveModel(controller, actionNodeId);

    VBox wrapper = new VBox();
    if (resolved != null) {
      actionNode = ComponentUtils.build(controller, resolved).getDisplay();

      if (actionNode != null) {
        wrapper.getChildren().addAll(actionNode);
      }

    }

    return wrapper;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }
}
