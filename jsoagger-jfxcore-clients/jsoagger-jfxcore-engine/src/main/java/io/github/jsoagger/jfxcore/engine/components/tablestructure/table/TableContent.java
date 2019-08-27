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

package io.github.jsoagger.jfxcore.engine.components.tablestructure.table;

import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.security.IContextParamSetter;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewTableSettingColumnXML;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewTableSettingXML;
import io.github.jsoagger.jfxcore.engine.client.utils.ClientClipBoard;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.client.utils.TableUtils;
import io.github.jsoagger.jfxcore.engine.components.table.cell.EpTableCell;
import io.github.jsoagger.jfxcore.engine.components.table.row.EpTableRow;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.SingleTableStructure;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.table.TableResponsiveMatrix.TableResponsiveOrder;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.ResizeFeatures;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class TableContent extends SingleTableStructure {

  protected TableView<OperationData> tableView;
  protected TableResponsiveMatrix tableResponsiveMatrix;
  protected TableColumnBase masterColumn;
  protected ProcessTableWidthChange processTableWidthChangeService = new ProcessTableWidthChange();
  protected PseudoClass nocontent = PseudoClass.getPseudoClass("nocontent");

  /**
   * {@inheritDoc}
   */
  @Override
  public void selectAllRows() {
    super.selectAllRows();
    final int size = tableView.getItems().size();
    final int selectIndex = getSelectColumnIndex();

    if (size > 0 && selectIndex >= 0) {
      for (int i = 0; i < size; i++) {
        final TableCell cell = TableUtils.getCellAt(tableView, i, selectIndex);
        if (cell instanceof EpTableCell) {
          final EpTableCell epcell = (EpTableCell) cell;
          if (epcell.getPresenter() instanceof TableRowSelectPresenter) {
            final TableRowSelectPresenter p = (TableRowSelectPresenter) epcell.getPresenter();
            p.selectCheckbox(true);
          }
        }
      }
    }
  }

  @Override
  public void deselectAllRows() {
    super.deselectAllRows();
    final int size = tableView.getItems().size();
    final int selectIndex = getSelectColumnIndex();

    if (size > 0 && selectIndex >= 0) {
      for (int i = 0; i < size; i++) {
        final TableCell cell = TableUtils.getCellAt(tableView, i, selectIndex);
        if (cell instanceof EpTableCell) {
          final EpTableCell epcell = (EpTableCell) cell;
          if (epcell.getPresenter() instanceof TableRowSelectPresenter) {
            final TableRowSelectPresenter p = (TableRowSelectPresenter) epcell.getPresenter();
            p.selectCheckbox(false);
          }
        }
      }
    }
  }

  private int getSelectColumnIndex() {
    for (int i = 0; i < tableView.getColumns().size(); i++) {
      final TableCell cell = TableUtils.getCellAt(tableView, 0, i);
      if (cell instanceof EpTableCell) {
        final EpTableCell epcell = (EpTableCell) cell;
        if (epcell.getPresenter() instanceof TableRowSelectPresenter) {
          return i;
        }
      }
    }
    return -1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteSelectedRows() {
    super.deleteSelectedRows();
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);

    String tableMode = contentConfiguration.getPropertyValue("tableMode");
    if (StringUtils.isNotBlank(tableMode) && tableMode.equalsIgnoreCase("edit")) {
      modify();
    }
  }

  public void setResponsiveColumnsMatrix(TableResponsiveMatrix tableResponsiveMatrix) {
    this.tableResponsiveMatrix = tableResponsiveMatrix;
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildContent() {
    super.buildContent();
    final boolean showTableHeader = contentConfiguration.getBooleanProperty("showTableHeader", true);
    // tableView = new FixedSizeTableView(showTableHeader);
    tableView = new TableView();
    tableView.setRowFactory(param -> {
      final EpTableRow row = new EpTableRow();
      row.setController(controller);
      row.setTableConfiguration(contentConfiguration);

      NodeHelper.styleClassAddAll(row, contentConfiguration, "tableRowStyleClass", "ep-table-row");
      return row;
    });

    final List<TableColumnBase> tableColumns = getTableColumns();
    for(TableColumnBase t: tableColumns) {
      tableView.getColumns().add((TableColumn) t);
    }

    tableView.widthProperty().addListener((ChangeListener<Number>) (observable, oldValue, newValue) -> {
      if (processTableWidthChangeService.isRunning()) {
        processTableWidthChangeService.cancel();
      }

      processTableWidthChangeService.setSValue(newValue.doubleValue());
      processTableWidthChangeService.restart();
    });

    tableView.setCache(true);
    tableView.setCacheHint(CacheHint.SPEED);
    tableView.setItems(filteredList);
  }

  private TableResponsiveOrder previousOrder;

  protected void applyTableResponsiveMatrix(Number newValue) {
    Platform.runLater(() -> {
      if (tableResponsiveMatrix != null) {
        final TableResponsiveOrder order = tableResponsiveMatrix.getOrdersOf(newValue.doubleValue());
        if (order != null && !order.equals(previousOrder)) {
          previousOrder = order;
          final List<String> displayableColumsOrder = order.getOrders();
          for (final ColumnsDef columnsDef : columnsDefs) {
            if (!displayableColumsOrder.contains(columnsDef.responsiveOrder)) {
              if (columnsDef.column.isVisible()) {
                columnsDef.column.setVisible(false);
              }
            } else {
              columnsDef.column.setVisible(true);
            }
          }
        }
      }
      autoFitTable(tableView);
    });
  }

  private final List<ColumnsDef> columnsDefs = new ArrayList<>();
  private TableColumnBase selectColumn = null;

  /**
   * 2 options:
   *
   * 1. Columns never change whatever the  resulting datas. In this case, declare columns in xml
   * 2. For search, columns may change according to user criteria. In this case,
   * columns must be calculated before displaying datas.
   *
   * @return
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public List<TableColumnBase> getTableColumns() {
    VLViewComponentXML toResolveColumnsCfg = contentConfiguration.getComponentById("Columns").orElse(null);
    if(toResolveColumnsCfg == null) {

      String columnsFiltersContextSetter = contentConfiguration.getPropertyValue("columnsFiltersContextSetter");

      if (StringUtils.isNotBlank(columnsFiltersContextSetter)) {
        final IContextParamSetter contextParamSetter = (IContextParamSetter) Services.getBean(columnsFiltersContextSetter);
        contextParamSetter.process(controller, controller.getModel(), controller.viewContext().getCriterias());
      }

      for(VLViewComponentXML componentXML: contentConfiguration.getSubcomponents()) {
        if(componentXML.getId().equals("CriteriaColumns")) {
          boolean izy = controller.evaluateFilter(componentXML);
          if(izy) {
            toResolveColumnsCfg = componentXML;
            break;
          }
        }
      }
    }

    List<VLViewComponentXML> resolveColsCfg = new ArrayList<>();
    if(toResolveColumnsCfg != null) {
      resolveColsCfg = ComponentUtils.resolveDefinitions(controller, toResolveColumnsCfg.getSubcomponents());
    }

    final List<TableColumnBase> tableColumns = new ArrayList<>();
    for (final VLViewComponentXML columnConfig : resolveColsCfg) {

      final TableColumnBase tableColumn = new TableColumn<>();
      tableColumn.setId(columnConfig.getId());

      if("RevisionColumn".equals(columnConfig.getId()))
        System.out.println(columnConfig.getId());


      final ColumnsDef columnsDef = new ColumnsDef(columnConfig, tableColumn);
      columnsDefs.add(columnsDef);

      // css
      final String css = columnConfig.getPropertyValue("columnHeaderStyleClass");
      if (StringUtils.isNotBlank(css)) {
        tableColumn.getStyleClass().addAll(css.split(","));
      }

      // is master column
      if ("true".equalsIgnoreCase(columnConfig.getMasterColumn())) {
        masterColumn = tableColumn;
      }

      // visibility
      final boolean visible = columnConfig.getBooleanProperty(XMLConstants.VISIBLE, true);
      tableColumn.setVisible(visible);

      // label
      if (StringUtils.isNotBlank(columnConfig.getPropertyValue("label"))) {
        final String labelString = NodeHelper.getLabel(columnConfig, controller);
        tableColumn.setText(labelString.toUpperCase());
      }

      // graphic
      if (StringUtils.isNotBlank(columnConfig.getPropertyValue("ikonli"))) {
        String presenter = columnConfig.getPropertyValue("presenter");
        if(!"TableRowActionPresenter".equalsIgnoreCase(presenter)) {
          final Label graphic = new Label();
          // separate header graphic and content graphic
          IconUtils.setIcon(graphic, columnConfig);
          tableColumn.setGraphic(graphic);
        }
      }

      // cell width
      if (columnConfig.getId().contentEquals("CoreBlankSpacerColumn")) {
        //tableColumn.prefWidthProperty().set(20);
        tableColumn.minWidthProperty().set(5);
      } else {
        String prefWidth = columnConfig.getPropertyValue(XMLConstants.PREF_WIDTH);
        if (StringUtils.isEmpty(prefWidth)) {
          //prefWidth = "50";
        }

        try {
          tableColumn.setPrefWidth(Integer.valueOf(prefWidth));
        } catch (final Exception ex) {
          tableColumn.setPrefWidth(50);
        }
      }

      if (columnConfig.getId().equalsIgnoreCase("CoreCheckboxSelectColumn")) {
        selectColumn = tableColumn;
        tableColumn.minWidthProperty().set(50);
        // selectColumn.setVisible(false);
      }

      // sortable
      final boolean sortable = columnConfig.getBooleanProperty(XMLConstants.SORTABLE, false);
      tableColumn.setSortable(sortable);

      // cell value factory
      ((TableColumn) tableColumn).setCellValueFactory(param -> {
        if (((CellDataFeatures) param).getValue() instanceof OperationData) {
          final OperationData operationData = (OperationData) ((CellDataFeatures) param).getValue();
          final SimpleObjectProperty<OperationData> op = new SimpleObjectProperty();
          op.set(operationData);
          return op;
        }
        return null;
      });

      // cell factory
      final String presenter = columnConfig.getPropertyValue("presenter");
      if (!StringUtils.isEmpty(presenter)) {
        if (tableColumn instanceof TableColumn) {
          ((TableColumn) tableColumn).setCellFactory(arg0 -> {
            final EpTableCell epTableCell = new EpTableCell<>();
            epTableCell.setController(controller);
            epTableCell.setConfiguration(columnConfig);
            epTableCell.setPresenteId(presenter);
            return epTableCell;
          });

          if (tableColumn instanceof TreeTableColumn) {
            ((TreeTableColumn) tableColumn).setCellFactory(arg0 -> {
              final EpTableCell epTableCell = new EpTableCell<>();
              epTableCell.setController(controller);
              epTableCell.setConfiguration(columnConfig);
              epTableCell.setPresenteId(presenter);
              return epTableCell;
            });
          }
        }
      }

      tableColumns.add(tableColumn);
    }

    return tableColumns;
  }

  @Override
  public void beginRowsEdition() {
    super.beginRowsEdition();
  }

  @Override
  public void endRowsEdition() {
    super.endRowsEdition();
  }

  /**
   * Reload the table according to setting (colums)
   *
   * @param tableSetting
   */
  public void reloadTableSetting(final VLViewTableSettingXML tableSetting) {
    final List<TableColumn> newcols = new ArrayList<>();
    for (final VLViewTableSettingColumnXML tcol : tableSetting.getColumns()) {
      for (final TableColumn col : tableView.getColumns()) {
        if (tcol.getId().equals(col.getId())) {
          col.setPrefWidth(tcol.getWidth());
          col.setVisible(tcol.isDisplayed());
          newcols.add(col);
        }
      }
    }

    tableView.getColumns().clear();
    for(TableColumn e: newcols) {
      tableView.getColumns().add(e);
    }
  }

  /**
   * Set table columns to columns from table wizardConfiguration
   *
   * @param tableColumns
   */
  public void setColumns(final List<TableColumn> tableColumns) {
    // if preferred setting is not null, load columns according to it
    final VLViewTableSettingXML preferredSetting = null;//DatatableSettingsHelper.instance().getPreferredFor("TO DO");
    if (preferredSetting != null) {
      reloadTableSetting(preferredSetting);
    } else {
      if (tableColumns != null && !tableColumns.isEmpty()) {
        for(TableColumn col: tableColumns) {
          tableView.getColumns().add(col);
        }
      }
    }
  }

  public void copyElement(ActionEvent actionEvent) {
    if (tableView.getSelectionModel().getSelectedItems().size() > 0) {
      final List<OperationData> toCopy = new ArrayList<>();
      //ClientClipBoard.copy(toCopy);
    }
  }

  /**
   * Callback
   *
   * @param element
   */
  public void modelUpdateFailed(Exception e) {
    final Label message = new Label(controller.getLocalised("ERROR_LOADING_DATAS"));
    tableView.setPlaceholder(message);
  }

  @Override
  public void setLoading() {
    Platform.runLater(() -> {
      if (tableView != null) {
        tableView.setPlaceholder(new ProgressIndicator());
      }
    });
  }

  public void pasteElement(ActionEvent e) {
    final boolean pasteable = true;
    for (final Object vo : ClientClipBoard.getElements()) {
    }

    if (pasteable) {
      final List<String> membersId = new ArrayList<>();
      refreshDatas();
    }
  }


  /**
   * Calling refresh() forces the TableView control to recreate and repopulate the
   * cells necessary to populate the visual bounds of the control.
   * In other words, this forces the TableView to update what it is showing to
   * the user. This is useful in cases where the underlying data source has changed
   * in a way that is not observed by the TableView itself.
   */
  public void refreshTable() {
    tableView.refresh();
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
  public void setNoContent() {
    Platform.runLater(() -> {
      if (noContentPane != null) {
        tableView.setPlaceholder(noContentPane.getDisplay());
        tableView.pseudoClassStateChanged(nocontent, true);
      }

      if(pagination != null) {
        pagination.getDisplay().setVisible(false);
      }
    });
  }

  /**
   * @{inheritedDoc}
   */
  @SuppressWarnings("rawtypes")
  @Override
  public void setData(MultipleResult multipleResult) {

    // update table columns may be!
    String columnsFiltersContextSetter = contentConfiguration.getPropertyValue("columnsFiltersContextSetter");
    if(StringUtils.isNotBlank(columnsFiltersContextSetter)) {
      List<TableColumnBase> c = getTableColumns();
      tableView.getColumns().clear();
      for(TableColumnBase tcb: c) {
        tableView.getColumns().add((TableColumn<OperationData, ?>) tcb);
      }
      applyTableResponsiveMatrix(tableView.getWidth());
    }

    final boolean isFirst = multipleResult.getCurrentPageIndex() <= 0;
    if (pagination != null && pagination.isLoadMorePagination() && !isFirst) {
      // items.clear();
    } else {
      items.clear();
    }

    items.addAll(multipleResult.getData());
    tableView.pseudoClassStateChanged(nocontent, false);
    tableView.refresh();
  }

  private class ColumnsDef {

    private final TableColumnBase column;
    private final VLViewComponentXML columnConfig;
    private final String columnId;
    private final String responsiveOrder;

    /**
     * Default Constructor
     */
    public ColumnsDef(VLViewComponentXML columnConfig, TableColumnBase column) {
      this.columnConfig = columnConfig;
      responsiveOrder = columnConfig.getResponsiveOrder();
      columnId = columnConfig.getId();
      this.column = column;
    }
  }

  public final Callback<ResizeFeatures, Boolean> MASTER_COLUMN_RESIZE_POLICY = new Callback<ResizeFeatures, Boolean>() {

    @Override
    public String toString() {
      return "master-column-resize";
    }

    @Override
    public Boolean call(ResizeFeatures prop) {
      // prop.getDelta() --> the amount of space added/removed from
      // the column/add/remove resize operation
      applyTableResponsiveMatrix(prop.getTable().getWidth());
      // System.out.println("########### " + prop.getDelta() + ", #######
      // " + prop.getTable().getWidth());
      return Double.compare(0.0, 0.0) == 0;
    }
  };

  public void autoFitTable(TableView tableView) {
    // redispatch only if have master column
    if (masterColumn != null) {
      double totalColWidth = 0.0;
      for (final Object column : tableView.getColumns()) {
        try {
          final TableColumn tableColumn = (TableColumn) column;
          if (tableColumn.isVisible()) {
            if (!tableColumn.equals(masterColumn)) {
              totalColWidth += tableColumn.widthProperty().get();
            }
          }
        } catch (final Exception e) {
          e.printStackTrace();
        }
      }

      final double remainingwidth = tableView.getWidth() - totalColWidth;
      if (remainingwidth > 0 && masterColumn != null) {
        if (remainingwidth < tableView.getWidth()) {
          masterColumn.prefWidthProperty().set(remainingwidth - 10);
        } else {
          masterColumn.minWidthProperty().set(200);
        }
      }
    }
  }

  /**
   * Task service for handling table width change.
   *
   * @author Ramilafananana  VONJISOA
   *
   */
  private class ProcessTableWidthChange extends Service<Void> {

    private Double value;

    public ProcessTableWidthChange() {}

    @Override
    protected Task<Void> createTask() {

      final Task<Void> task = new Task<Void>() {

        @Override
        protected Void call() throws Exception {
          applyTableResponsiveMatrix(value);
          return null;
        }
      };
      return task;
    }

    @Override
    protected void failed() {
      super.failed();
      Platform.runLater(() -> tableView.setVisible(true));
    }

    @Override
    protected void running() {
      super.running();
      Platform.runLater(() -> tableView.setVisible(false));
    }

    @Override
    protected void succeeded() {
      super.succeeded();
      Platform.runLater(() -> tableView.setVisible(true));
    }

    public Double getSValue() {
      return value;
    }

    public void setSValue(Double value) {
      this.value = value;
    }
  }
}
