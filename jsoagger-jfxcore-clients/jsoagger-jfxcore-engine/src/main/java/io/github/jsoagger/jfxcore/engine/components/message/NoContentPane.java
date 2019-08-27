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




import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.INoContentPane;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Is a {@link VBox} displaying message text and material button on the center if there is material
 * button defined in configuration file.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class NoContentPane extends VBox implements INoContentPane {

  protected Label noContentMessage = new Label();
  protected Node materialButton = null;


  /**
   * Constructor
   */
  public NoContentPane() {
    getStyleClass().add("no-content-pane");
    noContentMessage.setWrapText(true);
    noContentMessage.setStyle("no-content-pane-message");
    managedProperty().bind(visibleProperty());

    setAlignment(Pos.CENTER);
    NodeHelper.setHVGrow(this);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    getChildren().add(NodeHelper.verticalSpacer());

    if (configuration != null) {

      // process css of pane
      String styleClass = configuration.getPropertyValue("styleClass");
      if (io.github.jsoagger.core.utils.StringUtils.isNotBlank(styleClass)) {
        getStyleClass().addAll(styleClass.split(","));
      } else {
        // getStyleClass().add("no-content-pane-message");
      }

      // process css of message
      String labelStyleClass = configuration.getPropertyValue("labelStyleClass");
      if (io.github.jsoagger.core.utils.StringUtils.isNotBlank(labelStyleClass)) {
        noContentMessage.getStyleClass().addAll(labelStyleClass.split(","));
      } else {
        noContentMessage.getStyleClass().add("no-content-pane-message");
      }

      // the button if there is one
      VLViewComponentXML mtButtonCfg = configuration.getComponentById("MaterialButton").orElse(null);
      if ((mtButtonCfg != null) && mtButtonCfg.hasSubComponent()) {
        VLViewComponentXML mtButtonCfgRef = mtButtonCfg.getSubcomponents().get(0);
        VLViewComponentXML resolved = ComponentUtils.resolveModel((AbstractViewController) controller, mtButtonCfgRef.getReference());
        if (resolved != null) {
          Node button = ComponentUtils.buildMaterialButton((AbstractViewController) controller, resolved);
          getChildren().add(button);
        }
      }

      // the label for message displaying
      getChildren().add(noContentMessage);
      noContentMessage.setText(controller.getLocalised(configuration.getPropertyValue("noContentMessage")));

      // icon maybe
      IconUtils.setIcon(noContentMessage, configuration);
      noContentMessage.setContentDisplay(ContentDisplay.TOP);

      getChildren().add(NodeHelper.verticalSpacer());
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }
}
