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

package io.github.jsoagger.jfxcore.engine.components.simpledetails;


import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.api.IComponentProcessor;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.api.services.Services;

import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;

/**
 * @author Administrator
 *
 */
public class VerticalTab extends StackPane {

  protected final Hyperlink iconAction = new Hyperlink();
  protected Node tabContent;
  protected final VerticalTabPane tabPane;
  protected VLViewComponentXML tabCfg;
  protected AbstractViewController controller;


  /**
   * Constructor
   */
  public VerticalTab(VerticalTabPane tabPane) {
    this.tabPane = tabPane;
  }


  /**
   * @param tabPaneCfg
   * @param controller
   */
  public void buildFrom(VLViewComponentXML tabCfg, AbstractViewController controller) {
    this.tabCfg = tabCfg;
    this.controller = controller;

    iconAction.setFocusTraversable(false);
    final String iconName = tabCfg.getPropertyValue(XMLConstants.ICON);
    if (StringUtils.isNotBlank(iconName)) {
      iconAction.getStyleClass().add("vertical-tab-icon");
    }

    final String title = tabCfg.getPropertyValue(XMLConstants.TITLE);
    if (StringUtils.isNotBlank(title)) {
      final Tooltip tooltip = new Tooltip(controller.getLocalised(title));
      iconAction.setTooltip(tooltip);
    }

    getChildren().add(iconAction);
    iconAction.setOnAction(e -> {
      tabPane.setTabContent(getTabContent());
    });

    setStyle("-fx-max-height: 52");
  }


  public Node getTabContent() {
    if (tabContent == null) {
      IComponentProcessor processor = (IComponentProcessor) Services.getBean(tabCfg.getProcessor());
      tabContent = processor.process((IJSoaggerController) controller, tabCfg);
    }
    return tabContent;
  }


  /**
   * @param b
   */
  public void select(boolean b) {
    tabPane.setTabContent(getTabContent());
  }
}
