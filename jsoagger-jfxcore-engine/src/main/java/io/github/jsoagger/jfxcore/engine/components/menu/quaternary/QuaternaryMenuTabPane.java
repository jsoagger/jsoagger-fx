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

package io.github.jsoagger.jfxcore.engine.components.menu.quaternary;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class QuaternaryMenuTabPane extends StackPane {

  /** CSS */
  private static final String TAB_ITEMS_HEADER_CONTAINER = "quat-tab-items-container";
  private static final String TAB_HEADER_CONTENT_CONTAINER = "quat-tab-content-container";

  /** This is the root component of the parent controller */
  private final VLViewComponentXML tabComponents;

  /** Container of tab listViewPaneHeader and tab content */
  private final VBox rootContainer = new VBox();

  /** The tab listViewPaneHeader container */
  private final HBox tabItemsContainer = new HBox();

  /** Tba content container */
  private final StackPane tabContentContainer = new StackPane();

  /** List of tabs */
  private final List<QuaternaryMenuTab> tabs = new ArrayList<>();
  private AbstractViewController controller;


  /**
   * Create tab pane from the given wizardConfiguration
   *
   * @param config The wizardConfiguration
   */
  public QuaternaryMenuTabPane(final VLViewComponentXML config, AbstractViewController controller) {
    this.tabComponents = config;
    this.controller = controller;

    getChildren().add(rootContainer);
    rootContainer.getChildren().add(tabContentContainer);
    rootContainer.getChildren().add(tabItemsContainer);

    NodeHelper.setHVGrow(tabContentContainer);
    tabContentContainer.getStyleClass().add(TAB_HEADER_CONTENT_CONTAINER);
    tabItemsContainer.getStyleClass().add(TAB_ITEMS_HEADER_CONTAINER);

    buildTabsArea();
  }


  /**
   * Get the associated hbox tabItemsContainer
   */
  public Node tabItemsContainer() {
    return tabItemsContainer;
  }


  /**
   * Add a new tab
   *
   * @param tab
   */
  public void addTab(final QuaternaryMenuTab tab) {
    tabs.add(tab);
    tabItemsContainer.getChildren().add(tab);
    tab.setOnMouseClicked(e -> {
      selectTab(tab);
    });
  }


  /**
   * Select a tab
   *
   * @param tab
   */
  public void selectTab(final QuaternaryMenuTab tab) {

    tab.select(true);
    tabContentContainer.getChildren().clear();

    if (tab.getContent() != null) {
      tabContentContainer.getChildren().add(tab.getContent());
      NodeHelper.setHVGrow(tab.getContent());
    }

    for (final QuaternaryMenuTab styledTab : tabs) {
      if (!styledTab.getTitle().getText().equals(tab.getTitle().getText())) {
        styledTab.select(false);
      }
    }
  }


  /**
   * Process tabs building
   */
  protected void buildTabsArea() {
    if (tabComponents != null) {
      if (tabComponents != null && tabComponents.getSubcomponents() != null && tabComponents.hasSubComponent()) {
        for (final VLViewComponentXML tabDefinition : tabComponents.getSubcomponents()) {

          // Process tab and its title
          final String title = tabDefinition.getPropertyValue(XMLConstants.TITLE);
          final QuaternaryMenuTab styledTab = new QuaternaryMenuTab(tabDefinition, controller.getLocalised(title));
          styledTab.setInternalId(tabDefinition.getId());
          addTab(styledTab);
        }
      }

      if (!getTabs().isEmpty()) {
        selectTab(getTabs().get(0));
      }
    }
  }


  /**
   * Get tabs list
   *
   * @return the tabs
   */
  public List<QuaternaryMenuTab> getTabs() {
    return tabs;
  }


  /**
   * Set tabs content
   *
   * @param contents
   */
  public void setTabContent(final Map<String, Node> contents) {
    if (contents != null) {
      for (final String tabId : contents.keySet()) {
        for (final QuaternaryMenuTab tab : tabs) {
          if (tabId.equals(tab.getInternalId())) {
            tab.setContent(contents.get(tabId));
          }
        }
      }
    }
  }


  /**
   * Select tab with this index.
   *
   * @param i
   */
  public void selectTab(final int i) {
    selectTab(tabs.get(i));
  }
}
