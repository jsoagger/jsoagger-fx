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

package io.github.jsoagger.jfxcore.engine.components.tablestructure.flow;




import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.ICountableElements;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IFlowItemResolver;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.presenter.MultiPresenterFactory;
import io.github.jsoagger.jfxcore.engine.components.table.FixedSizeTableView;
import io.github.jsoagger.jfxcore.engine.components.table.row.EpTableRow;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.SingleTableStructure;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.css.PseudoClass;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class FlowTableContent extends SingleTableStructure implements IBuildable, ICountableElements {

  /*-----------------------------------------------------------------------------
  | static fields
   *=============================================================================*/

  /*-----------------------------------------------------------------------------
  | Private fields
   *=============================================================================*/
  private String flowItemResolver;
  private String flowItemImpl;
  private MultipleResult currentData;

  /**
   * The main wrapper content of the flow pane. Some flow pane handles back action. Thats why we
   * maintain a list of content. When user requests back action we just pop from that list for
   * perfomance issue.
   */
  // FixedSizeTableView tableView = new FixedSizeTableView(false);
  FixedSizeTableView tableView = new FixedSizeTableView(false);


  /*-----------------------------------------------------------------------------
  | Public methods
   *=============================================================================*/
  /**
   * Constructor
   */
  public FlowTableContent() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);

    // extract css from configuration
    final String styleClass = rootConfiguration.getPropertyValue("styleClass");
    if (io.github.jsoagger.core.utils.StringUtils.isNotBlank(styleClass)) {
      // content.getStyleClass().addAll(styleClass.split(","));
    }
  }


  private TableColumn buildRootColumn() {
    final TableColumn t = new TableColumn();
    // cell value factory
    t.setCellValueFactory(param -> {
      if (((CellDataFeatures) param).getValue() instanceof OperationData) {
        final OperationData operationData = (OperationData) ((CellDataFeatures) param).getValue();
        final SimpleObjectProperty<OperationData> op = new SimpleObjectProperty();
        op.set(operationData);
        return op;
      }
      return null;
    });

    t.setPrefWidth(600);
    t.setMinWidth(600);

    // cell factory
    t.setCellFactory(arg0 -> new FlowTableCell());
    return t;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildContent() {
    super.buildContent();
    tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    tableView.setRowFactory(param -> {
      final EpTableRow row = new EpTableRow();
      row.setController(controller);
      row.setTableConfiguration(contentConfiguration);
      NodeHelper.styleClassAddAll(row, contentConfiguration, "tableRowStyleClass", "ep-flow-item-table-row");
      return row;
    });

    final TableColumnBase tableColumn = buildRootColumn();
    tableView.getColumns().add(tableColumn);
    tableView.setItems(filteredList);

    if (contentConfiguration != null) {
      // extract item factory
      flowItemImpl = contentConfiguration.getPropertyValue("flowItemImpl");
      flowItemResolver = contentConfiguration.getPropertyValue("flowItemResolver");
      if (StringUtils.isBlank(flowItemImpl) && StringUtils.isBlank(flowItemResolver)) {
        throw new IllegalArgumentException("flowItemImpl or flowItemResolver is mandatory");
      }

      final String flowContentStyleClass = contentConfiguration.getPropertyValue("flowContentStyleClass");
      if (io.github.jsoagger.core.utils.StringUtils.isNotBlank(flowContentStyleClass)) {
        // content.getStyleClass().addAll(flowContentStyleClass.split(","));
      } else {
        // content.getStyleClass().addAll("ep-flow-content-content-wrapper");
      }
    }

    modifyingProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
      for(MultiPresenterFactory f: factories) {
        f.deselect();
      }
    });

    tableView.setCache(true);
    tableView.setCacheHint(CacheHint.SPEED);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Optional<Node> getPagination() {
    // return empty because the pagination for flow content is not
    // a fixed component
    return super.getPagination();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void redisplayCurrentDatas() {
    super.redisplayCurrentDatas();
    if (currentData != null)
      setData(currentData);
  }

  private final List<MultiPresenterFactory> factories = new ArrayList<>();


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setData(MultipleResult multipleResult) {
    final int elementsCount = multipleResult.totaElements();
    this.elementsCount.set(elementsCount);
    currentData = multipleResult;

    final boolean isFirst = multipleResult.getCurrentPageIndex() <= 0;
    if (pagination != null) {
      pagination.getDisplay().setVisible(true);
    }

    if (pagination != null && pagination.isLoadMorePagination() && !isFirst) {
      // clear items!
    } else {
      items.clear();
    }

    items.addAll(multipleResult.getData());
    tableView.pseudoClassStateChanged(PseudoClass.getPseudoClass("nocontent"), false);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setNoContent() {
    if (Platform.isFxApplicationThread()) {
      _doSetNoContent();
    } else {
      Platform.runLater(() -> {
        _doSetNoContent();
      });
    }
  }


  private void _doSetNoContent() {
    if (noContentPane != null) {
      tableView.setPlaceholder(noContentPane.getDisplay());
      tableView.pseudoClassStateChanged(PseudoClass.getPseudoClass("nocontent"), true);
    }

    if (pagination != null) {
      pagination.getDisplay().setVisible(false);
      pagination.getDisplay().pseudoClassStateChanged(PseudoClass.getPseudoClass("nodata"), true);
    } ;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setLoading() {
    if (Platform.isFxApplicationThread()) {
      _doSetLoading();
    } else {
      Platform.runLater(() -> {
        _doSetLoading();
      });
    }
  }


  private void _doSetLoading() {
    if (pagination != null) {
      pagination.getDisplay().setVisible(false);
    }

    tableView.setPlaceholder(NodeHelper.getProcessingIndicator());
    tableView.pseudoClassStateChanged(PseudoClass.getPseudoClass("nocontent"), true);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getTableStructure() {
    return tableView;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return new StackPane();
  }

  /**
   * @author Ramilafananana  VONJISOA
   *
   */
  private class FlowTableCell extends TableCell {

    /**
     * Constructor
     */
    public FlowTableCell() {
      super();
      getStyleClass().add("ep-flow-table-cell");
    }


    @Override
    protected void updateItem(Object item, boolean empty) {
      super.updateItem(item, empty);
      setGraphic(null);
      if (!empty && item != null) {
        final IBuildable flowItem = (IBuildable) Services.getBean(getFlowItemId());
        ((MultiPresenterFactory) flowItem).setForData((OperationData) item);
        flowItem.buildFrom(controller, contentConfiguration);
        factories.add((MultiPresenterFactory) flowItem);

        if (((MultiPresenterFactory) flowItem).getSelectionPaneWrapper() != null) {
          ((MultiPresenterFactory) flowItem).getSelectionPaneWrapper().visibleProperty().bind(modifying);
        }

        ((MultiPresenterFactory) flowItem).getSelection().selectedProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
          if (newValue.booleanValue()) {
            selections.add((OperationData) item);
          } else {
            selections.remove(item);
          }
        });

        final Node display = flowItem.getDisplay();
        NodeHelper.setHgrow(display);
        setGraphic(display);
      }
    }
  }


  private String getFlowItemId() {
    if (StringUtils.isBlank(flowItemResolver)) {
      return flowItemImpl;
    }

    final IFlowItemResolver iFlowItemResolver = (IFlowItemResolver) Services.getBean(flowItemResolver);
    return iFlowItemResolver.getFlowItem(controller, this);
  }


  public String getFlowItemResolver() {
    return flowItemResolver;
  }


  public String getFlowItemImpl() {
    return flowItemImpl;
  }


  public MultipleResult getCurrentData() {
    return currentData;
  }


  public FixedSizeTableView getTableView() {
    return tableView;
  }


  public List<MultiPresenterFactory> getFactories() {
    return factories;
  }
}
