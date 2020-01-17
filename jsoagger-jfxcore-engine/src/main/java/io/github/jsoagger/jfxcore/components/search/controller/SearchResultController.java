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


import java.util.List;
import java.util.concurrent.ExecutionException;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.operation.JsonUtils;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.AbstractTableStructure;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.table.TableContent;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.table.TableResponsiveMatrix;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;
import com.google.gson.JsonObject;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * May be a list, a table or a flow of content.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 27 févr. 2018
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class SearchResultController extends StandardViewController {

  protected TableView tableView;
  protected TableResponsiveMatrix tableResponsiveMatrix;
  protected SimpleIntegerProperty resultCount = new SimpleIntegerProperty(-1);
  protected SimpleObjectProperty searchSummary = new SimpleObjectProperty();
  private JsonObject initialQuery = null;

  private List<IBuildable> sortingActionComponents;
  private SimpleBooleanProperty showTypeInSummary = new SimpleBooleanProperty(true);

  private IOperation getTypeByOidOperation;// needs GetTypeByOidOperation


  /**
   * Default Constructor
   */
  public SearchResultController() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void postProcess() {
    super.postProcess();

    if (processedElement() != null) {
      final AbstractTableStructure table = (AbstractTableStructure) processedElement();
      table.setNoContent();
      if (table instanceof TableContent) {
        ((TableContent) table).setResponsiveColumnsMatrix(tableResponsiveMatrix);
      }

      if (table.getTableStructure() instanceof TableView) {
        tableView = (TableView) table.getTableStructure();
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableView.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> selectedElementProperty().set((OperationData) newValue));
      }

      // showTypeAndSummary
      String showTypeAndSummary = table.getContentConfig().getPropertyValue("showTypeAndSummary");
      if (StringUtils.isNotBlank(showTypeAndSummary) && Boolean.valueOf(showTypeAndSummary) == Boolean.FALSE) {
        this.showTypeInSummary.set(false);
      }

      // process filters
      buildSorting();
    }
  }


  protected void buildSorting() {
    final AbstractTableStructure table = (AbstractTableStructure) processedElement();
    final VLViewComponentXML actionsConfig = table.rootConfiguration().getComponentById("SortingActions").orElse(null);
    if (actionsConfig != null) {
      sortingActionComponents = ComponentUtils.resolveAndGenerate(this, actionsConfig.getSubcomponents());
    }
  }


  public void doSearch(SearchFormController formController, JsonObject query) {
    if (processedElement() != null) {
      initialQuery = query;

      searchSummaryProperty().set(null);

      final AbstractTableStructure table = (AbstractTableStructure) processedElement();
      final MultipleResult multipleResult = (MultipleResult) IOperationResult.emptyMultipleResult();
      multipleResult.addMetaData("pageSize", 5);
      multipleResult.addMetaData("pageNumber", 0);
      multipleResult.addMetaData("totalPages", 0);
      multipleResult.addMetaData("hasNextPage", true);
      multipleResult.addMetaData("hasPreviousPage", false);
      multipleResult.addMetaData("totalElements", 10);

      table.modelProperty().addListener(new ChangeListener<Object>() {

        @Override
        public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
          MultipleResult mr = (MultipleResult) newValue;
          resultCount.set(mr.totaElements());
          if (showTypeInSummary.get()) {
            // commented because ugly
            //processSearchSummary(formController, query);
          }
        }});

      table.clearItems();
      table.setLoading();

      multipleResult.getMetaData().putAll(JsonUtils.toMap(query));
      table.firstPage(new SimpleObjectProperty<>(multipleResult));
    }
  }


  /**
   * We do it only for type managed objects.
   *
   * @param formController
   *
   * @param query
   */
  private void processSearchSummary(SearchFormController formController, JsonObject query) {
    final String businessType = query.get("business.type").getAsString();
    if (StringUtils.isNotBlank(businessType)) {

      final HBox node = new HBox();
      NodeHelper.setHgrow(node);
      searchSummary.set(node);
      node.setStyle("-fx-alignment:CENTER_LEFT;-fx-padding:0 0 0 8;-fx-spacing:8");
      final Label forx = new Label(formController.getGLocalised("SEARCH_RESULT_FOR").concat(" "));
      forx.getStyleClass().add("ep-as-search-result-count-label");
      Platform.runLater(()->node.getChildren().addAll(forx));

      for (final String bt : businessType.split("#")) {
        final GetTypeTask<IOperationResult> gtt = new GetTypeTask<>(bt);
        gtt.setOnSucceeded(worker -> {

          try {
            final IOperationResult operationResult = gtt.get();
            final String tlp = (String) ((SingleResult) operationResult).getData().getAttributes().get("displayName");
            final Label type = new Label(tlp);
            type.getStyleClass().add("ep-as-search-result-type-item-label");
            node.getChildren().addAll(type);

            // open search form
            type.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
              formController.getSearchRootFormController().getSearchController().getLayoutManager().displaySwitchable(ViewLayoutPosition.LEFT);
            });

          } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            searchSummary.set(null);
          }
        });

        gtt.exceptionProperty().addListener((a,b,c)->c.printStackTrace());
        final Thread t = new Thread(gtt);
        t.start();
      }
    }
  }


  public void sortResult() {
    final AbstractTableStructure table = (AbstractTableStructure) processedElement();
    final MultipleResult multipleResult = (MultipleResult) IOperationResult.emptyMultipleResult();
    multipleResult.addMetaData("pageSize", 5);
    multipleResult.addMetaData("pageNumber", 0);
    multipleResult.addMetaData("totalPages", 0);
    multipleResult.addMetaData("hasNextPage", true);
    multipleResult.addMetaData("hasPreviousPage", false);
    table.setLoading();

    multipleResult.getMetaData().putAll(JsonUtils.toMap(initialQuery));
    table.firstPage(new SimpleObjectProperty<>(multipleResult));
    resultCount.set(((MultipleResult)this.modelProperty().get()).totaElements());
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getNodeOnPosition(ViewLayoutPosition position) {
    final AbstractTableStructure table = (AbstractTableStructure) processedElement();
    if (position == ViewLayoutPosition.TOP && table != null) {
      return null;
    }

    if (position == ViewLayoutPosition.BOTTOM) {
      if (table.getPagination().isPresent()) {
        return table.getPagination().get();
      }
    }

    if (position == ViewLayoutPosition.CENTER) {
      return table.getTableStructure();
    }

    return super.getNodeOnPosition(position);
  }


  public SimpleIntegerProperty resultCountProperty() {
    return resultCount;
  }


  /**
   * Get the current selected element in underlaying table.
   */
  public OperationData getSelectedModel() {
    return (OperationData) tableView.getSelectionModel().selectedItemProperty().get();
  }


  /**
   * Getter of tableResponsiveMatrix
   *
   * @return the tableResponsiveMatrix
   */
  public TableResponsiveMatrix getTableResponsiveMatrix() {
    return tableResponsiveMatrix;
  }


  /**
   * Setter of tableResponsiveMatrix
   *
   * @param tableResponsiveMatrix the tableResponsiveMatrix to set
   */
  public void setTableResponsiveMatrix(TableResponsiveMatrix tableResponsiveMatrix) {
    this.tableResponsiveMatrix = tableResponsiveMatrix;
  }


  /**
   * Curreny search summary
   *
   * @return
   */
  public SimpleObjectProperty<Node> searchSummaryProperty() {
    return searchSummary;
  }


  /**
   * @return
   */
  public JsonObject currentQuery() {
    return initialQuery;
  }


  /**
   * @return
   */
  public List<IBuildable> sortingActionComponents() {
    return sortingActionComponents;
  }


  private class GetTypeTask<IOperationResult> extends Task<IOperationResult> {

    private final String fullId;


    public GetTypeTask(String fullId) {
      this.fullId = fullId;
    }


    @Override
    protected IOperationResult call() throws Exception {
      final SingleResult result = new SingleResult();
      final JsonObject params = new JsonObject();
      params.addProperty("fullId", fullId);
      params.addProperty("oid", fullId);
      getTypeByOidOperation.doOperation(params, res -> {
        final SingleResult singleResult = (SingleResult) res;
        result.setData(singleResult.getData());
      }, ex -> ex.printStackTrace());
      return (IOperationResult) result;
    }
  }

  public boolean showTypeInSummary() {
    return showTypeInSummary.get();
  }

  public void showTypeInSummary(boolean p) {
    showTypeInSummary.set(p);
  }

  public SimpleBooleanProperty showTypeInSummaryProperty() {
    return showTypeInSummary;
  }


  /**
   * @return the getTypeByOidOperation
   */
  public IOperation getGetTypeByOidOperation() {
    return getTypeByOidOperation;
  }


  /**
   * @param getTypeByOidOperation the getTypeByOidOperation to set
   */
  public void setGetTypeByOidOperation(IOperation getTypeByOidOperation) {
    this.getTypeByOidOperation = getTypeByOidOperation;
  }
}
