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



import io.github.jsoagger.jfxcore.api.IComponentProcessor;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.layoutproc.SimpleDetailsViewAllFieldsetsProcessor;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.scene.Node;
import javafx.scene.control.Tooltip;

/**
 * @author Administrator
 *
 */
public class InfoVerticalTab extends VerticalTab {

  /**
   * Constructor
   *
   * @param tabPane
   */
  public InfoVerticalTab(VerticalTabPane tabPane) {
    super(tabPane);
  }


  @Override
  public void buildFrom(VLViewComponentXML tabCfg, AbstractViewController controller) {
    this.controller = controller;

    iconAction.setFocusTraversable(false);
    iconAction.getStyleClass().add("vertical-tab-icon");

    final Tooltip tooltip = new Tooltip("All attributes");
    iconAction.setTooltip(tooltip);

    getChildren().add(iconAction);
    iconAction.setOnAction(e -> {
      tabPane.setTabContent(getTabContent());
    });

    setStyle("-fx-max-height: 52");
  }


  @Override
  public Node getTabContent() {
    if (tabContent == null) {
      final IComponentProcessor componentProcessor = new SimpleDetailsViewAllFieldsetsProcessor();
      tabContent = componentProcessor.process((IJSoaggerController) controller, tabPane.config());
    }
    return tabContent;
  }
}
