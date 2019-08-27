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

package io.github.jsoagger.jfxcore.engine.components.tab.paned;



import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * Tab used in pane like in right nav
 * 
 * @author Ramilafananana  VONJISOA
 *
 */
public class PanedTabPane extends HBox {

  /*-----------------------------------------------------------------------------
  | CSS fields
  *=============================================================================*/
  private static final String TAB_PANED_CONTAINER_CSS = "paned-tab-items-container";
  private static final String TAB_PANED_CONTENT_CONTAINER = "paned-tab-content-container";

  /*-----------------------------------------------------------------------------
  | Private fields
  *=============================================================================*/
  /** List of tabs */
  private final List<PanedTab> tabs = new ArrayList<PanedTab>();
  /** The content of selected tab is sent through this callback */
  private Function<Node, Boolean> selectCallBack;


  /*-----------------------------------------------------------------------------
  | CONSTRUCTOR
  *=============================================================================*/
  public PanedTabPane(final VLViewComponentXML tabConfig) {
    getStyleClass().add(TAB_PANED_CONTAINER_CSS);
  }


  /*-----------------------------------------------------------------------------
  | PUBLIC METHODS
  *=============================================================================*/
  /**
   * Add a new tab
   * 
   * @param tab
   */
  public void addTab(final PanedTab tab, Function<Node, Boolean> selectCallBack) {
    this.selectCallBack = selectCallBack;
    tabs.add(tab);
    getChildren().add(tab);
    tab.setOnMouseClicked(e -> {
      final Node content = selectTab(tab);
      selectCallBack.apply(content);
      // avoid event propagation on parent
      e.consume();
    });
  }



  /**
   * Select a tab
   * 
   * @param tab
   */
  public Node selectTab(final PanedTab tab) {

    // call pseudo class to update state
    tab.select(true);
    for (final PanedTab styledTab : tabs) {
      if (!styledTab.getTitle().getText().equals(tab.getTitle().getText())) {
        styledTab.select(false);
      }
    }

    return tab.getContent();
  }


  /**
   * Get tabs list
   * 
   * @return the tabs
   */
  public List<PanedTab> getTabs() {
    return tabs;
  }


  /**
   * Select tab with this index.
   * 
   * @param i
   */
  public Node selectTab(final int i) {
    return selectTab(tabs.get(i));
  }
}
