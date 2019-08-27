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

package io.github.jsoagger.jfxcore.engine.components.contextmenu;




import java.util.List;

import org.kordamp.ikonli.javafx.FontIcon;

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.security.CriteriaContext;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.StackPane;

/**
 * Generates an hyperlink with ELLIPSIS_V or ELLIPSIS_H. When clicked, shows the menu.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class EllipsisActionButton extends StackPane {

  private final Hyperlink ellipsisButton = new Hyperlink();
  private ContextMenu contextMenu;


  /**
   * Constructor
   */
  public EllipsisActionButton() {}


  public void build(VLViewComponentXML configuration, AbstractViewController controller, CriteriaContext criteriaContext) {
    List<VLViewComponentXML> configurations = configuration.getSubcomponents();
    build(configurations, controller, criteriaContext, null);

    String ellipsysStyleClass = configuration.getPropertyValue("ellipsysStyleClass");
    if (StringUtils.isNotBlank(ellipsysStyleClass)) {
      ellipsisButton.getStyleClass().addAll(ellipsysStyleClass.split(","));
    }
  }


  /**
   * @param configuration
   * @param controller
   * @param contextualTo
   */
  public void build(List<VLViewComponentXML> configuration, AbstractViewController controller, CriteriaContext criteriaContext, Node contextualTo) {
    contextMenu = new ContextMenu(controller, configuration, criteriaContext);
    contextMenu.setContextualTo(contextualTo);

    ellipsisButton.managedProperty().bind(ellipsisButton.visibleProperty());
    ellipsisButton.setOnAction(e -> {
      e.consume();
      //      if (contextMenu.isShowing()) {
      //        contextMenu.hide();
      //        return;
      //      }
      //      contextMenu.show(ellipsisButton);
    });
  }


  public void setVerlical() {
    FontIcon icon = new FontIcon("mdi-dots-vertical:20");
    icon.getStyleClass().add("grey-ikonli");
    ellipsisButton.setGraphic(icon);
    getChildren().add(ellipsisButton);
  }


  public void setHorizontal() {
    FontIcon icon = new FontIcon("mdi-dots-horizontal:20");
    icon.getStyleClass().add("grey-ikonli");
    ellipsisButton.setGraphic(icon);
    getChildren().add(ellipsisButton);
  }


  public ContextMenu contextMenu() {
    return contextMenu;
  }
}
