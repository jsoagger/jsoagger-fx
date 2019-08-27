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

package io.github.jsoagger.jfxcore.components.toolbar;



import io.github.jsoagger.jfxcore.api.IToolbarHolder;
import io.github.jsoagger.jfxcore.components.search.controller.SearchFormController;
import io.github.jsoagger.jfxcore.components.search.controller.SearchResultController;
import io.github.jsoagger.jfxcore.engine.components.toolbar.htoolbar.AbstractHToolbar;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class SearchToolbar extends AbstractHToolbar {


  private SearchFormController searchController;
  private SearchResultController searchResultController;

  private TextField searchfield = new TextField();
  private Button searchButton = new Button();
  private Button filterButton = new Button();

  /**
   * Constructor
   */
  public SearchToolbar() {}


  /**
   * {@inheritDoc}
   */
  @Override
  public void buildFrom(AbstractViewController controller, IToolbarHolder toolbarHolder) {
    super.buildFrom(controller, toolbarHolder);

    searchButton.getStyleClass().addAll("button-small", "button-primary");
    searchButton.setText("Go");
    IconUtils.setFontIcon("fa-search:12", searchButton);

    searchButton.setOnAction(e -> {
      if (searchController != null) {
        searchController.doSearch();
        controller.pushContent(null, searchResultController.processedView());
      }
    });

    searchController = (SearchFormController) StandardViewUtils.forId(controller.getRootStructure(), controller.getStructureContent(), "SearchAndAddPartFormView");
    searchResultController = (SearchResultController) StandardViewUtils.forId(controller.getRootStructure(), controller.getStructureContent(), "SearchAndAddPartSearchResultView");
    // searchController.setSearchResultController(searchResultController);

    filterButton.getStyleClass().addAll("button-small", "button-primary");
    IconUtils.setFontIcon("mdi-filter-list:12", filterButton);
    filterButton.setText("Filters");
    filterButton.setOnAction(e -> {
      controller.pushContent(null, searchController.processedView());
    });

    centerSection.getChildren().addAll(searchfield, searchButton, filterButton);
    searchfield.getStyleClass().add("ep-primary-search-textfield");
    searchfield.focusedProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {

      if (newValue) {

      } else {
        // controller.popContent();
      }
    });
  }
}
