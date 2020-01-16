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
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.INoContentPane;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * No content pane with list of grouped actions
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class GroupedActionsContentPane extends VBox implements INoContentPane {

  protected String labelStyleClass = null;


  /**
   * Constructor
   */
  public GroupedActionsContentPane() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {

    // icon maybe
    Label icon = new Label();
    IconUtils.setIcon(icon, configuration);
    icon.setContentDisplay(ContentDisplay.TOP);

    // process css
    String styleClass = configuration.getPropertyValue("styleClass");
    if (io.github.jsoagger.core.utils.StringUtils.isBlank(styleClass)) {
      styleClass = "no-content-pane";
    }
    getStyleClass().addAll(styleClass.split(","));

    // process css of message
    labelStyleClass = configuration.getPropertyValue("labelStyleClass");
    if (io.github.jsoagger.core.utils.StringUtils.isBlank(labelStyleClass)) {
      labelStyleClass = "no-content-pane-message";
    }

    getChildren().add(NodeHelper.verticalSpacer());
    getChildren().add(icon);

    if (configuration.hasSubComponent()) {
      Iterator<VLViewComponentXML> iterator = configuration.getSubcomponents().iterator();
      while (iterator.hasNext()) {
        VLViewComponentXML config = iterator.next();
        Node node = buildGroup((AbstractViewController) controller, config);
        getChildren().add(node);

        if (iterator.hasNext()) {
          getChildren().add(NodeHelper.verticalSpacer());
          getChildren().add(buildSpacer());
          getChildren().add(NodeHelper.verticalSpacer());
        }
      }
    }

    getChildren().add(NodeHelper.verticalSpacer());
    NodeHelper.setHVGrow(this);
  }


  protected Node buildSpacer() {
    HBox node = new HBox();
    node.setAlignment(Pos.CENTER);
    node.getStyleClass().add("ep-no-contentpane-spacer-wrapper");
    Label or = new Label("Or");
    or.getStyleClass().add("no-content-pane-message-connector");
    Pane spacer1 = getSpacer();
    Pane spacer2 = getSpacer();
    
    node.getChildren().addAll(spacer1, or, spacer2);
    NodeHelper.setHgrow(node, spacer1, spacer2);
    return node;
  }


  private Pane getSpacer() {
    Pane spacer = new Pane();
    spacer.setMinHeight(1);
    spacer.setMaxHeight(1);
    spacer.getStyleClass().add("ep-no-contentpane-spacer");
    return spacer;
  }


  protected Node buildGroup(AbstractViewController controller, VLViewComponentXML groupConfig) {
    Label message = new Label();
    message.setWrapText(true);
    message.getStyleClass().addAll(labelStyleClass.split(","));

    String lbl = groupConfig.getPropertyValue("message");
    if(StringUtils.isNotBlank(lbl)) {
      String msg = controller.getLocalised(lbl);
      message.setText(msg);
    }

    IconUtils.setIcon(message, groupConfig);
    message.setContentDisplay(ContentDisplay.TOP);

    Node actionNode = null;

    // The action node
    String actionNodeId = groupConfig.getPropertyValue("actionNode");
    VLViewComponentXML resolved = ComponentUtils.resolveModel(controller, actionNodeId);

    VBox wrapper = new VBox();
    wrapper.getChildren().addAll(message);
    wrapper.getStyleClass().add("no-content-pane-group");

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
