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

package io.github.jsoagger.jfxcore.components.search.comps;



import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.components.control9.CustomTextField;
import io.github.jsoagger.jfxcore.engine.components.input.AbstractInputComponent;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;
import io.github.jsoagger.jfxcore.components.search.controller.SearchController;
import io.github.jsoagger.jfxcore.components.search.controller.SearchRootFormController;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

/**
 * A component displaying input text for searching data in remote database. Search Button and filter
 * button is diplayed horizontaly alongside with the input control.
 *
 * @author Ramilafananana  VONJISOA
 */
public class SearchInputComponent extends AbstractInputComponent implements IBuildable {

  private SearchController searchController;

  private CustomTextField searchfield = new CustomTextField();
  private Button searchButton = new Button();
  private final HBox layout = new HBox();
  private final Button filterButton = new Button();

  /**
   * Constructor
   */
  public SearchInputComponent() {
    layout.getStyleClass().add("ep-header-search-comp-wrapper");
  }

  protected void buildSearchButton() {
    searchButton.getStyleClass().addAll("button-small", "button-primary", "ep-button");
    searchButton.setText("Go");
    IconUtils.setFontIcon("fa-search:12", searchButton);

    searchButton.setOnAction(e -> {
      doSearch();
    });

    layout.getChildren().addAll(searchButton);

    // search can be triggered with enter key
    searchfield.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
      if (e.getCode() == KeyCode.ENTER) {
        doSearch();
      }
    });
  }

  private void doSearch() {
    // hide pane if needed
    final SearchRootFormController formController = (SearchRootFormController) controller;
    if (formController.getSearchController().getLayoutManager().isSwitchable()) {
      formController.getSearchController().getLayoutManager().closeSwitchable(ViewLayoutPosition.LEFT);
    }

    final Task<Void> go = new Task<Void>() {

      @Override
      protected Void call() throws Exception {
        if (searchController != null) {
          searchController.doSearch();
          if (searchController.getLayoutManager() != null) {
            Platform.runLater(() -> {
              searchController.getLayoutManager().display(ViewLayoutPosition.RIGHT);
              filterButton.setVisible(true);
            });
          }
        }
        return null;
      }

      @Override
      protected void setException(Throwable t) {
        super.setException(t);
        t.printStackTrace();
      }
    };

    final Thread thread = new Thread(go);
    thread.setName("emp__search_task");
    thread.setDaemon(true);
    thread.start();
  }

  protected void buidFilterButton() {
    filterButton.getStyleClass().addAll("header-search-filter-button");
    IconUtils.setFontIcon("fa-sliders:20", filterButton);
    searchfield.setRight(filterButton);
    filterButton.setVisible(false);

    filterButton.setOnAction(e -> {
      searchController.getLayoutManager().display(ViewLayoutPosition.LEFT);
      filterButton.setVisible(false);
    });
  }

  /**
   *
   */
  @Override
  public void commitModification() {
    super.commitModification();
    // owner.commitModification(false);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);

    // !! this form is displayed inside the input SearchRootFormController
    final SearchRootFormController src = (SearchRootFormController) controller;
    src.searchControllerProperty().addListener((ChangeListener<SearchController>) (observable, oldValue, newValue) -> {
      searchController = src.getSearchController();
    });

    layout.getChildren().addAll(searchfield);

    buildSearchButton();
    buidFilterButton();

    searchfield.getStyleClass().remove("custom-text-field");
    searchfield.getStyleClass().add("ep-search-input-comp-textfield");
    searchfield.focusedProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
      if (newValue) {

      } else {
        // controller.popContent();
      }
    });

    Bindings.bindBidirectional(owner.currentInternalValueProperty(), searchfield.textProperty(), owner.getConverter());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Node getDisplay() {
    return layout;
  }

  public CustomTextField getSearchfield() {
    return searchfield;
  }

  public void setSearchfield(CustomTextField searchfield) {
    this.searchfield = searchfield;
  }

  public Button getSearchButton() {
    return searchButton;
  }

  public void setSearchButton(Button searchButton) {
    this.searchButton = searchButton;
  }

  public AbstractViewController getController() {
    return controller;
  }

  public void setController(AbstractViewController controller) {
    this.controller = controller;
  }

  @Override
  public VLViewComponentXML getConfiguration() {
    return configuration;
  }

  @Override
  public void setConfiguration(VLViewComponentXML configuration) {
    this.configuration = configuration;
  }

  @Override
  public Node getComponent() {
    return searchfield;
  }
}
