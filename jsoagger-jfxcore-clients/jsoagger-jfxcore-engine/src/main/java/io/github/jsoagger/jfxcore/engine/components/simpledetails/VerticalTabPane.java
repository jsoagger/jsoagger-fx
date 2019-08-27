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




import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.jfxcore.viewdefinition.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * @author Administrator
 *
 */
public class VerticalTabPane extends HBox {

  private SimpleDetailsPane simpleDetailsPane;
  private final List<VerticalTab> tabs = new ArrayList<>();
  private VLViewComponentXML tabPaneCfg;


  /**
   * Constructor
   */
  public VerticalTabPane() {
    setStyle("-fx-padding: 16 16 16 20;" + "-fx-spacing: 32;" + "-fx-background-color: white;" + "-fx-border-width: 1;" + "-fx-border-color: -grey-color-300;");
  }


  /**
   * @param tabPaneCfg
   * @param controller
   */
  public void buildFrom(VLViewComponentXML tabPaneCfg, AbstractViewController controller) {
    this.tabPaneCfg = tabPaneCfg;

    // build the first tab containing all others values
    final InfoVerticalTab firstTab = new InfoVerticalTab(this);
    firstTab.buildFrom(tabPaneCfg, controller);
    tabs.add(firstTab);
    getChildren().add(firstTab);

    if (tabPaneCfg != null && tabPaneCfg.hasSubComponent()) {
      for (final VLViewComponentXML tab : tabPaneCfg.getSubcomponents()) {

        final boolean visible = tab.booleanPropertyValueOf(XMLConstants.VISIBLE).orElse(true);

        if (visible) {
          final VerticalTab verticalTab = new VerticalTab(this);
          verticalTab.buildFrom(tab, controller);

          tabs.add(verticalTab);
          getChildren().add(verticalTab);
        }
      }
    }
  }


  public VLViewComponentXML config() {
    return tabPaneCfg;
  }


  public void select(int index) {
    final VerticalTab tab = tabs.get(index);
    tab.select(true);
  }


  /**
   * @param simpleDetailsPane
   */
  public void setContentContainer(SimpleDetailsPane simpleDetailsPane) {
    this.simpleDetailsPane = simpleDetailsPane;
  }


  /**
   * @param tabContent
   */
  public void setTabContent(Node tabContent) {
    simpleDetailsPane.setTabContent(tabContent);
  }
}
