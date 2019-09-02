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



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import com.google.gson.JsonObject;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.INoContentPane;
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.components.search.AdvancedSearchResutHeader;
import io.github.jsoagger.jfxcore.components.search.comps.ISearchResultHeader;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.message.NoContentPaneHelper;
import io.github.jsoagger.jfxcore.engine.controller.main.DoubleHeaderRootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardController;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.concurrent.Task;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public abstract class SearchController extends StandardController {

  protected SearchControllerWrapper current = null;
  protected JsonObject form;
  protected VLViewComponentXML searchComponents;

  protected StackPane searchResultLayout = new StackPane();
  protected StackPane searchFormLayout = new StackPane();
  protected ISearchResultHeader searchHeader = new AdvancedSearchResutHeader();
  protected Node noContentPane;
  private final Button filterButton = new Button();

  /**
   * Holds common data for each type. Thats why rootBlocContents is builded according to this
   * controller model. Each custom type has its own model.
   */
  protected SearchRootFormController rootFormController;


  /**
   * Constructor
   */
  public SearchController() {
    super();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected void process() {
    super.process();
    searchComponents = getRootComponent().getComponentById("SearchComponents").orElse(null);

    // build no content pane
    final VLViewComponentXML noCtntCfg = getRootComponent().getComponentById("NoContenPane").orElse(null);
    if (noCtntCfg != null) {
      final INoContentPane pane = NoContentPaneHelper.buildFrom(this, noCtntCfg);
      noContentPane = pane.getDisplay();
    }

    // root form is form common to all types
    // like modification date and creation date
    buildRootForm();

    // header of the search result
    // contains tabs and filters each tab
    searchHeader.buildFrom(this, null);

    // when first processed display no contentpane by default
    // OR DEFAULT VIEW IF DECLARED
    if (noContentPane != null) {
      searchResultLayout.getChildren().add(noContentPane);
    }

    final boolean withFilters = getRootComponent().getBooleanProperty("withFilters", false);
    if (withFilters) {
      buidFilterButton();
    }

    processedView(searchResultLayout);
  }


  /**
   * !!IMPORTANT, SERACH HEADER COMPONENT MUST BE USED WITH DOUBLE HEADER STRUCTURE
   * COMPONENT!!OTHERWIZE NO WORKING
   */
  public void doSearch() {
    commitForm();
    current.doSearch(form);

    // !!IMPORTANT, SERACH HEADER COMPONENT MUST BE USED WITH DOUBLE HEADER
    // * STRUCTURE COMPONENT!!OTHERWIZE NO WORKING
    if (getRootStructure() instanceof DoubleHeaderRootStructureController) {
      if (searchHeader.getDisplay().isVisible()) {
        ((DoubleHeaderRootStructureController) getRootStructure()).setSecondaryheader(searchHeader.getDisplay());
      }
    }
  }


  private void updateTabs() {
    // update the header and content
    searchResultLayout.getChildren().clear();
    searchHeader.clearTabs();
    searchHeader.clearFilters();
    final Map<String, SearchResultController> results = current.getSearchResult();
    for (final String key : results.keySet()) {
      final SearchResultTab tab = new SearchResultTab(key, results.get(key));
      searchHeader.addTab(tab);

      tab.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
        for(SearchResultTab t: searchHeader.allTabs()) {
          t.select(false, null);
        }
        tab.select(true, searchResultLayout);
      });
    }

    // select first tab
    searchHeader.selectFirstTab(searchResultLayout);
  }


  public Pane searchForm() {
    return searchFormLayout;
  }


  public Pane searchResult() {
    return searchResultLayout;
  }


  private void setProcessing() {
    searchResultLayout.getChildren().clear();
    searchResultLayout.getChildren().add(NodeHelper.getProcessingIndicator());
  }


  public void commitForm() {
    form = rootFormController.getForm(true);
    JsonObject f = current.getForm(true);
    for(String key : f.keySet()) {
      form.add(key, f.get(key));
    }
  }


  /**
   * Getter of actions to be displayed in the form.
   *
   * @return
   */
  public List<Node> getActions() {
    final List<IBuildable> buildables = rootFormController.getActions();
    final List<Node> nodes = new ArrayList<>();
    if (buildables != null) {
      for(IBuildable e: buildables) {
        nodes.add(e.getDisplay());
      }
    }
    return nodes;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getNodeOnPosition(ViewLayoutPosition position) {
    if (position == ViewLayoutPosition.RIGHT) {
      return searchResultLayout;
    } else if (position == ViewLayoutPosition.LEFT) {
      return searchFormLayout;
    } else if (position == ViewLayoutPosition.TOP) {
      return rootFormController.blocContents.get(0).getDisplay();
    }

    return super.getNodeOnPosition(position);
  }

  /**
   * Each resuting tab.
   *
   * @author Ramilafananana  VONJISOA
   *
   */
  public class SearchResultTab extends HBox {

    private final SearchResultController content;
    private String displayName;

    private Text name = new Text();
    private Label count = new Label();

    /**
     * Constructor
     *
     * @param title
     */
    public SearchResultTab(String title, SearchResultController content) {
      this.content = content;
      getType(title, d -> {
        setDisplayName((String) d.getAttributes().get("displayName"));
        name.setText(displayName);
        name.getStyleClass().add("ep-search-result-header-tab-item-name");

        count.setText(String.valueOf(content.resultCount.get()));
        count.getStyleClass().add("ep-search-result-header-tab-item-count");
      });

      getChildren().addAll(name, count);
      getStyleClass().add("ep-search-result-header-tab");
    }


    /**
     * @param displayName
     */
    private void setDisplayName(String displayName) {
      this.displayName = displayName;
    }

    /**
     * @return
     */
    public SearchResultController content() {
      return content;
    }

    /**
     *
     * @param select
     * @param parent
     */
    public void select(boolean select, Pane parent) {
      if (select) {
        Platform.runLater(() -> {
          searchHeader.clearFilters();
          parent.getChildren().clear();
          parent.getChildren().add(content.processedView());
          searchHeader.setFilters(content.sortingActionComponents());
        });
      }
      pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), select);
    }
  }

  /**
   * Group form and result by type. Only for entities sharing same root type.
   *
   * @author Ramilafananana  VONJISOA
   *
   */
  public class SearchControllerWrapper {

    private final String[] types;
    private SearchFormController searchFormController;

    private final String searchFormViewId;
    private final String searchResultViewId;

    /**
     * One search result by subtype selected
     */
    private final ObservableMap<String, SearchResultController> searchResultsByTypeControllers = FXCollections.observableHashMap();


    /**
     * Only for entities sharing same root type.
     */
    public SearchControllerWrapper(String[] types, String rootType, VLViewComponentXML configuration) {
      this.types = types;

      // VIEWS ARE CALCULATED RELATOVE TO ROOTTYPE
      final Map<String, Object> params = new HashMap<>();
      params.put("viewContext", getSearchContext());
      params.put("type", rootType);
      params.put("action", "searchForm");
      searchFormViewId = getSearchFormViewId(params);

      final Map<String, Object> params2 = new HashMap<>();
      params2.put("viewContext", getSearchContext());
      params2.put("type", rootType);
      params2.put("action", "searchResult");
      searchResultViewId = getSearchResultViewId(params2);

      // HEADER COMPONENTS HAS NO STRUCTURE CONTENT BECAUSE THEY DONT
      // BELONG TO ANY STRUCTURE CONTENT, THEY ARE COMMON TO ALL VIEW.
      // SO ALL COMPONENTS CONTEXTUAL TO IT WILL BE LIKE IT.
      searchFormController = (SearchFormController) StandardViewUtils.forId(getRootStructure(), searchFormViewId);

      // when searchResultsByTypeControllers create tab
      searchResultsByTypeControllers.addListener((MapChangeListener<String, SearchResultController>) change -> {
        if (change.wasAdded()) {
          updateTabs();
        }
      });
    }


    @SuppressWarnings("unchecked")
    public void doSearch(JsonObject form) {
      final CompletableFuture<Void>[] feats = new CompletableFuture[types.length];
      searchResultsByTypeControllers.clear();
      int i = 0;

      setprocessing();

      for (final String s : types) {
        final JsonObject cloneform = form.deepCopy();
        final Task<SearchResultController> task = buildSearchTask(s, searchResultViewId, cloneform);
        final CompletableFuture<Void> cf = CompletableFuture.runAsync(task);
        feats[i] = cf;
        i++;
      }

      CompletableFuture.allOf(feats).join();
    }


    private Task<SearchResultController> buildSearchTask(String type, String searchResultViewId, JsonObject cloneform) {
      cloneform.addProperty("business.type", type);
      final Task<SearchResultController> t = new Task<SearchResultController>() {

        @Override
        protected SearchResultController call() throws Exception {
          SearchResultController searchResultController = null;
          if (getStructureContent() == null) {
            searchResultController = (SearchResultController) StandardViewUtils.forId(getRootStructure(), searchResultViewId);
          } else {
            searchResultController = (SearchResultController) StandardViewUtils.forId(getRootStructure(), getStructureContent(), searchResultViewId);
          }

          searchResultController.setParent(getParent());
          searchResultController.doSearch(searchFormController, cloneform);
          return searchResultController;
        }


        @Override
        protected void failed() {
          super.failed();
        }


        @Override
        protected void setException(Throwable t) {
          super.setException(t);
          t.printStackTrace();
          if(t.getCause() != null)
            t.getCause().printStackTrace();
        }


        @Override
        protected void succeeded() {
          searchResultsByTypeControllers.put(type, getValue());
          super.succeeded();
        }
      };

      return t;
    }


    private JsonObject getForm(boolean commit) {
      return searchFormController.getForm(commit).deepCopy();
    }


    public void destroy() {
      searchFormController.destroy();
    }


    public SearchFormController getSearchFormController() {
      return searchFormController;
    }


    public void setSearchFormController(SearchFormController searchFormController) {
      this.searchFormController = searchFormController;
    }


    public Map<String, SearchResultController> getSearchResult() {
      return searchResultsByTypeControllers;
    }
  }


  protected void buildRootForm() {
    final String rootFormId = getSearchRootFormViewId(new HashMap<>());
    //System.out.println(">>>>>>>>>>>>>>>>>>>< Form identifier : " + rootFormId);
    if (getStructureContent() == null) {
      rootFormController = (SearchRootFormController) StandardViewUtils.forId(getRootStructure(), rootFormId);
    } else {
      rootFormController = (SearchRootFormController) StandardViewUtils.forId(getRootStructure(), getStructureContent(), rootFormId);
    }

    searchFormLayout.getChildren().add(rootFormController.processedView());
    rootFormController.setSearchController(this);
  }


  protected String getSearchContext() {
    final VLViewComponentXML rootComp = getRootComponent();
    return rootComp.getPropertyValue("searchContext", "__MUST_BE_PROVIDED");
  }


  /**
   * Get a type by its full id.
   *
   * @param fullId
   * @param cs
   */
  protected void getType(String fullId, Consumer<OperationData> cs) {
    final JsonObject query = new JsonObject();
    query.addProperty("fullId", fullId);
    query.addProperty("oid", fullId);
    final IOperation op = (IOperation) Services.getBean("PersistableLoadSimpleModelOperation");
    op.doOperation(query, res -> {
      final SingleResult sr = (SingleResult) res;
      final OperationData data = sr.getData();
      cs.accept(data);
    });
  }


  /**
   * Search input component is the first item in search form.
   */
  protected void buidFilterButton() {
    filterButton.getStyleClass().addAll("iconless-header-search-filter-button");
    filterButton.setText("Filters");

    if (rootFormController.getSearchMasterAttribute() != null) {
      rootFormController.getSearchMasterAttribute().getSearchfield().setRight(filterButton);
    }

    filterButton.setOnAction(e -> filterButtonClicked());
  }


  /**
   */
  private void filterButtonClicked() {
    if (rootFormController.getSearchController().getLayoutManager() != null) {
      rootFormController.getSearchController().getLayoutManager().display(ViewLayoutPosition.LEFT);
    }
  }


  private void setprocessing() {
    final Node p = NodeHelper.getProcessingIndicator();

    Platform.runLater(() -> {
      searchResultLayout.getChildren().clear();
      searchResultLayout.getChildren().add(p);
    });
  }


  protected abstract String getSearchFormViewId(Map<String, Object> params);


  protected abstract String getSearchResultViewId(Map<String, Object> params);


  protected abstract String getSearchRootFormViewId(Map<String, Object> params);
}
