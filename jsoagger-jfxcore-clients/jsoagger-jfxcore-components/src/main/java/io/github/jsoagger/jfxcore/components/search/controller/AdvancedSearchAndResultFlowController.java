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

package io.github.jsoagger.jfxcore.components.search.controller;


import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardTabPaneController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;
import com.google.gson.JsonObject;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * Tab pane in left, result in right
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 27 févr. 2018
 */
public class AdvancedSearchAndResultFlowController extends StandardViewController {

  protected StandardTabPaneController advancedSearchController;
  protected SearchResultController searchResultController;

  protected SearchFormController searchFormController;

  protected StackPane left = new StackPane();
  protected StackPane right = new StackPane();

  /**
   * Default Constructor
   */
  public AdvancedSearchAndResultFlowController() {
    super();
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  protected void process() {
    super.process();

    buildAdvancedSearchTabpane();
    buildSearchResult();
  }

  public void buildAdvancedSearchTabpane() {
    final String searchView = getRootComponent().getPropertyValue("searchView");
    if (StringUtils.isNotBlank(searchView)) {
      advancedSearchController = (StandardTabPaneController) StandardViewUtils.forId(getRootStructure(), structureContentController, searchView);
      final String location = advancedSearchController.getRootComponent().getPropertyValue("location");
      if (StringUtils.isNotBlank(location)) {

      }

      Platform.runLater(() -> {
        final Node node = advancedSearchController.processedView();
        left.getChildren().clear();
        left.getChildren().add(node);
      });

      left.getStyleClass().add("ep-search-left-form-wrapper");
      searchFormController = (SearchFormController) advancedSearchController.getControllerOfTab("SearchFormTab");
    }
  }

  public void buildSearchResult() {
    final String searchResultView = getRootComponent().getPropertyValue("searchResultView");
    searchResultController = (SearchResultController) StandardViewUtils.forId(getRootStructure(), structureContentController, searchResultView);
    right.getChildren().add(searchResultController.processedView());

    if (searchFormController != null) {
      // searchFormController.setSearchResultController(searchResultController);
    }
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getNodeOnPosition(ViewLayoutPosition position) {
    if (position == ViewLayoutPosition.LEFT) {
      return left;
    }

    if (position == ViewLayoutPosition.RIGHT) {
      return right;
    }

    return super.getNodeOnPosition(position);
  }

  private void doSearch() {
    final Task<Void> task = new Task<Void>() {

      @Override
      protected Void call() throws Exception {
        // JsonObject json = searchFormController.getForm();
        searchResultController.doSearch(searchFormController, new JsonObject());
        return null;
      }

      /**
       * @{inheritedDoc}
       */
      @Override
      protected void running() {
        super.running();
        setProcessing();
      }
    };

    final Thread thread = new Thread(task);
    thread.setDaemon(true);
    thread.start();
  }

  private void setProcessing() {
    Platform.runLater(() -> {
      right.getChildren().clear();
      right.getChildren().add(searchResultController.processedView());
    });
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public IJSoaggerController getController() {
    return this;
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public VLViewComponentXML getConfiguration() {
    return getRootComponent();
  }
}
