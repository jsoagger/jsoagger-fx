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



import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.jfoenix.controls.JFXButton;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.InjectableComponent;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.components.control9.CustomTextField;
import io.github.jsoagger.jfxcore.components.search.controller.SearchController;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.AbstractTableStructure;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import io.github.jsoagger.jfxcore.engine.controller.main.DoubleHeaderRootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.StructureContentController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.PushStructureContentEvent;
import io.github.jsoagger.jfxcore.engine.controller.utils.RootStructureUtils;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * Use this input in the header of application only. Root structure must be a
 * {@link DoubleHeaderRootStructureController}.
 * <p>
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class SearchHeaderComponent extends HBox implements IBuildable, InjectableComponent {

  private SearchController searchController;

  private CustomTextField searchfield = new CustomTextField();
  private Button searchButton = new JFXButton();
  private Button filterButton = new JFXButton();

  private AbstractViewController controller;
  private VLViewComponentXML configuration;
  private String mode = null;
  private StandardViewController  savedSearch ;


  private PseudoClass notEmptySearchField = PseudoClass.getPseudoClass("notEmptyText");

  /**
   * Constructor
   */
  public SearchHeaderComponent() {
    getStyleClass().add("ep-header-search-comp-wrapper");
  }


  protected void buildSearchButton() {
    if("mobile".equalsIgnoreCase(mode)) {
      searchButton.getStyleClass().remove("button");
      filterButton.getStyleClass().remove("button");
      filterButton.getStyleClass().add("hand-hover");
      searchfield.getStyleClass().remove(0);
    }
    else {
      filterButton.getStyleClass().add("ep-button");
    }

    searchButton.getStyleClass().addAll("button-small", "button-primary", "ep-button");
    searchButton.setText("Go");
    searchButton.setOnAction(e -> doSearch());
    if(!AbstractApplicationRunner.isMobile()) getChildren().addAll(searchButton);
    IconUtils.setFontIcon("fa-search:12", searchButton);

    // search can be triggered with enter key
    searchfield.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
      if (e.getCode() == KeyCode.ENTER) {

        Task<Void> th = new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            autoCompletionLearnWord(searchfield.getText().trim());
            doSearch();
            return null;
          }};
          new Thread(th).start();
      }
    });

    // if the text field is not empty, add style class
    // to it to handle its color behavior
    searchfield.textProperty().addListener(new ChangeListener<String>() {

      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        searchfield.pseudoClassStateChanged(notEmptySearchField, newValue != null && !newValue.trim().equals(""));
      }});


    if(AbstractApplicationRunner.isDesktop()) {
      searchfield.focusedProperty().addListener(new ChangeListener<Boolean>() {

        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
          if(newValue) {
            StackPane p = new StackPane();
            p.setStyle("-fx-background-color:white");
            if(controller.getRootStructure() instanceof DoubleHeaderRootStructureController) {
              ((DoubleHeaderRootStructureController)controller.getRootStructure()).hideSecondaryHeader();
            }

            controller.getRootStructure().pushContent((StandardViewController) controller, p);
            if(savedSearch == null) {
              savedSearch = StandardViewUtils.forId(controller.getRootStructure(), "MySavedSearchTableView");
              savedSearch.setParent(searchController);
            }
            else {
              ((AbstractTableStructure)savedSearch.processedElement()).refreshDatas();
            }

            p.getChildren().add(savedSearch.processedView());
          }
        }});
    }
  }


  protected void buildMySavedSearchTableView() {

  }


  /**
   * !!IMPORTANT, SERACH HEADER COMPONENT MUST BE USED WITH DOUBLE HEADER STRUCTURE
   * COMPONENT!!OTHERWIZE NO WORKING
   */
  public void doSearch() {
    if (searchController != null) {
      searchController.doSearch();

      StructureContentController scc = (StructureContentController) Services.getBean("StructureContentController");
      scc.processedView(searchController.searchResult());
      PushStructureContentEvent ev = new PushStructureContentEvent.Builder().processedContent(scc).build();
      searchController.dispatchEvent(ev);

      if (Platform.isFxApplicationThread()) {
        //controller.getRootStructure().pushContent(null, searchController.searchResult());
        // controller.getStructureContentController().getRootStructureContentController().display(ev);
        // controller.getStructureContent().getRootStructureContentController()...getContentStack().getChildren().clear();
        // controller.getRootStructure().getContentStack().getChildren().add(searchController.processedView());

      } else {
        Platform.runLater(() -> {
          // controller.getStructureContent().getRootStructureContentController().display(ev);
          // controller.getRootStructure().getContentStack().getChildren().clear();
          // controller.getRootStructure().getContentStack().getChildren().add(searchController.processedView());
        });
      }
    }
  }


  /**
   * Search header component by is used with {@link DoubleHeaderRootStructureController}. When the
   * user clicks on the filter button, the content is pushSecondaryRootStructureContent into it.
   * <p>
   * IF YOU WANT TO USE THIS SEARCH COMPONENT OVERRIDE IT AND HANDE LAYOUT AND DISPLAY OF FILTER
   */
  protected void buildFilterButton() {
    filterButton.getStyleClass().addAll("iconless-header-search-filter-button");

    if(AbstractApplicationRunner.isMobile()) {
      getChildren().add(filterButton);
      filterButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("mobile"), true);
      IconUtils.setFontIcon("gmi-tune:24", filterButton);
    }
    else {
      filterButton.setText("Filters");
      searchfield.setRight(filterButton);
    }

    filterButton.setOnAction(e -> {
      Task<Void> th = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
          if(searchController != null) {
            doit(null);
          }
          return null;
        }

        @Override
        protected void setException(Throwable t) {
          super.setException(t);
          t.printStackTrace();
        }
      };
      new Thread(th).start();
    });

    Task<Void> th = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        buildSearchForm();
        return null;
      }
    };
    new Thread(th).start();
  }


  private void buildSearchForm() {
    if(rsc == null) {
      rsc = RootStructureUtils.forId("SearchFiltersInSecondaryRSView");
      rsc.sourceRootStructure(controller.getRootStructure());
      searchController.getRootStructure().sourceRootStructure(controller.getRootStructure());

      Platform.runLater(() -> {
        // !!bricolage
        if(searchController != null) {
          final AnchorPane ap = (AnchorPane) rsc.processedView();
          AnchorPane.setTopAnchor(searchController.searchForm(), 72.0);
          AnchorPane.setLeftAnchor(searchController.searchForm(), 0.);
          AnchorPane.setRightAnchor(searchController.searchForm(), 0.);
          AnchorPane.setBottomAnchor(searchController.searchForm(), 0.);

          ap.getChildren().add(searchController.searchForm());
          //controller.getRootStructure().setSecondaryRootStructureContent(rsc.processedView(), searchController.getActions());
        }
      });
    }
  }

  private RootStructureController rsc = null;
  protected void doit(OperationData d) {
    if(searchController != null) {
      controller.getRootStructure().setSecondaryRootStructureContent(rsc.processedView(), searchController.getActions());
    }
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.configuration = configuration;
    this.controller = (AbstractViewController) controller;

    final String searchViewId = configuration.getPropertyValue("searchViewId");
    if(searchViewId != null && !"".equals(searchViewId.trim())) {
      searchController = (SearchController) StandardViewUtils.forId((RootStructureController) controller.getRootStructure(), searchViewId);
    }

    mode = configuration.getPropertyValue("mode");

    getChildren().addAll(searchfield);

    buildFilterButton();
    buildSearchButton();

    searchfield.getStyleClass().remove("custom-text-field");
    searchfield.getStyleClass().add("ep-header-search-textfield");
    searchfield.focusedProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
      if (newValue) {

      } else {
        // controller.popContent();
      }
    });

    if(searchController != null) searchController.addComponent(this);
  }

  private void _doBuild() {
    final String searchViewId = configuration.getPropertyValue("searchViewId");
    if(searchViewId != null && !"".equals(searchViewId.trim())) {
      searchController = (SearchController) StandardViewUtils.forId(controller.getRootStructure(), searchViewId);
      if(searchController != null) searchController.addComponent(this);
    }
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }


  public TextField getSearchfield() {
    return searchfield;
  }


  public void setSearchfield(TextField searchfield) {
    this.searchfield = (CustomTextField) searchfield;
  }


  public Button getSearchButton() {
    return searchButton;
  }


  public void setSearchButton(Button searchButton) {
    this.searchButton = searchButton;
  }


  public Button getFilterButton() {
    return filterButton;
  }


  public void setFilterButton(Button filterButton) {
    this.filterButton = filterButton;
  }


  public AbstractViewController getController() {
    return controller;
  }


  public void setController(AbstractViewController controller) {
    this.controller = controller;
  }


  public VLViewComponentXML getConfiguration() {
    return configuration;
  }


  public void setConfiguration(VLViewComponentXML configuration) {
    this.configuration = configuration;
  }


  /**
   * {@inheritDoc}
   *
   * @return
   */
  @Override
  public String getInternalId() {
    return "SearchHeaderComponent";
  }

  //private AutoCompletionBinding<String> autoCompletionBinding;
  private final String[] _possibleSuggestions = {};
  private final Set<String> possibleSuggestions = new HashSet<>(Arrays.asList(_possibleSuggestions));


  private void autoCompletionLearnWord(String newWord) {
    possibleSuggestions.add(newWord);

    // we dispose the old binding and recreate a new binding
    //    if (autoCompletionBinding != null) {
    //      autoCompletionBinding.dispose();
    //    }
    //    autoCompletionBinding = TextFields.bindAutoCompletion(searchfield, possibleSuggestions);
  }


  /**
   * @return the mode
   */
  public String getMode() {
    return mode;
  }


  /**
   * @param mode the mode to set
   */
  public void setMode(String mode) {
    this.mode = mode;
  }

}
