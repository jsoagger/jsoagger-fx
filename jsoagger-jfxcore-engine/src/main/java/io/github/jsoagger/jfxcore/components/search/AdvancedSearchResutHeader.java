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

package io.github.jsoagger.jfxcore.components.search;



import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.components.search.comps.ISearchResultHeader;
import io.github.jsoagger.jfxcore.components.search.controller.SearchController.SearchResultTab;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * Header for advanced search, this header displays tabs fo each seleted type.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class AdvancedSearchResutHeader extends HBox implements ISearchResultHeader {

  private HBox rootContainer = new HBox();
  private HBox tabsContainer = new HBox();
  private HBox filtersContainer = new HBox();

  private AbstractViewController controller;
  private VLViewComponentXML configuration;

  /**
   * These are fixed actions node displayed on the right side of the header
   **/
  private HBox actionNode = new HBox();

  /**
   * These are current tabs for current search
   */
  private List<SearchResultTab> tabs = new ArrayList<>();
  private HBox sortingActionNode = new HBox();


  /**
   * Constructor
   */
  public AdvancedSearchResutHeader() {
    managedProperty().bind(rootContainer.visibleProperty());
    getChildren().addAll(rootContainer);
    getDisplay().setVisible(false);

    NodeHelper.setHgrow(rootContainer);
    rootContainer.getChildren().addAll(tabsContainer);

    getStyleClass().add("ep-as-search-result-header-pane-wrapper");
    tabsContainer.getStyleClass().add("ep-as-search-result-header-pane-internal-wrapper");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.controller = (AbstractViewController) controller;

    VLViewComponentXML config = ComponentUtils.resolveDefinition((AbstractViewController)controller, "SearchResultToolBar").orElse(null);
    this.configuration = config;

    if (config != null) {
      getDisplay().setVisible(true);
      NodeHelper.styleClassAddAll(rootContainer, config, "styleClass", "ep-as-search-result-header-pane");
      actionNode.getStyleClass().add("ep-as-search-result-header-actions-wrapper");
      sortingActionNode.getStyleClass().add("ep-as-search-result-header-sorting-actions-wrapper");

      buildActions();

      rootContainer.getChildren().add(NodeHelper.horizontalSpacer());
      rootContainer.getChildren().add(sortingActionNode);

      if (actionNode.getChildren().size() > 0) {
        //rootContainer.getChildren().add(NodeHelper.verticalSeparator());
        rootContainer.getChildren().add(actionNode);
      }
    }
  }


  protected void buildActions() {
    VLViewComponentXML actionsConfig = configuration.getComponentById("Actions").orElse(null);
    if (actionsConfig != null) {
      List<IBuildable> actions = ComponentUtils.resolveAndGenerate(controller, actionsConfig.getSubcomponents());
      for(IBuildable b :actions) {
        actionNode.getChildren().add(b.getDisplay());
      }
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void clearTabs() {
    tabs.clear();
    tabsContainer.getChildren().clear();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void addTab(SearchResultTab tab) {
    tabsContainer.getChildren().add(tab);
    tabs.add(tab);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public List<SearchResultTab> allTabs() {
    return tabs;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void selectFirstTab(Pane parent) {
    parent.getChildren().clear();
    tabs.get(0).select(true, parent);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void clearFilters() {
    sortingActionNode.getChildren().clear();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void setFilters(List<IBuildable> sortingActionComponents) {
    if (sortingActionComponents != null) {
      for(IBuildable b: sortingActionComponents) {
        sortingActionNode.getChildren().add(b.getDisplay());
      }
    }
  }
}
