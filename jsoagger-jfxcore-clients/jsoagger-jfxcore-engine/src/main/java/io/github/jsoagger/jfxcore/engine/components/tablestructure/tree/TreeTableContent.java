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

package io.github.jsoagger.jfxcore.engine.components.tablestructure.tree;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewTableSettingColumnXML;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewTableSettingXML;
import io.github.jsoagger.jfxcore.engine.components.pagination.tree.ITreePaginatedDataProvider;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.SingleTableStructure;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.table.TableResponsiveMatrix;
import io.github.jsoagger.jfxcore.engine.components.tree.cell.EpTreeTableCell;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableView;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 20 févr. 2018
 */
public class TreeTableContent extends SingleTableStructure {

  protected TreeTableView<OperationData> treeView;
  protected TableResponsiveMatrix tableResponsiveMatrix;
  protected ITreePaginatedDataProvider treePaginatedDataProvider;


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.controller = (AbstractViewController) controller;
    this.rootConfiguration = configuration;

    noContentPaneConfiguration = rootConfiguration.getComponentById("NoContentPane").orElse(null);
    headerConfiguration = rootConfiguration.getComponentById("Header").orElse(null);
    toolbarConfiguration = rootConfiguration.getComponentById("Toolbar").orElse(null);
    contentConfiguration = rootConfiguration.getComponentById("Content").orElse(null);
    paginationConfiguration = rootConfiguration.getComponentById("Pagination").orElse(null);

    final CompletableFuture cp1 = CompletableFuture.runAsync(() -> buildToolbar());
    final CompletableFuture cp2 = CompletableFuture.runAsync(() -> buildHeader());
    final CompletableFuture cp3 = CompletableFuture.runAsync(() -> buildPagination());
    final CompletableFuture cp4 = CompletableFuture.runAsync(() -> buildNoContentPane());
    final CompletableFuture cp5 = CompletableFuture.runAsync(() -> buildContent());
    CompletableFuture.allOf(cp1, cp2, cp3, cp4, cp5).join();
  }


  protected void selectionChanged(TreeItem selectedItem) {}


  public void setResponsiveColumnsMatrix(TableResponsiveMatrix tableResponsiveMatrix) {
    this.tableResponsiveMatrix = tableResponsiveMatrix;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildContent() {
    treeView = new TreeTableView<>();
    final String treePaginatedDataProvider = contentConfiguration.getPropertyValue("dataLoader");
    this.treePaginatedDataProvider = (ITreePaginatedDataProvider) Services.getBean(treePaginatedDataProvider);
    this.treePaginatedDataProvider.setRootContext(controller);

    final List<TableColumnBase> tableColumns = getTableColumns();
    for(TableColumnBase e: tableColumns) {
      treeView.getColumns().add((TreeTableColumn<OperationData, ?>) e);
    }

    treeView.setCache(true);
    treeView.setCacheHint(CacheHint.SPEED);

    final TreeItem root = this.treePaginatedDataProvider.getRootItem();
    treeView.setRoot(root);
    treeView.setShowRoot(true);

    // style
    NodeHelper.setStyleClass(treeView, contentConfiguration, "treeViewStyleClass", true);
  }


  public List<TableColumnBase> getTableColumns() {
    final VLViewComponentXML toResolveColumnsCfg = contentConfiguration.getComponentById("Columns").orElse(null);
    final List<VLViewComponentXML> resolveColsCfg = ComponentUtils.resolveDefinitions(controller, toResolveColumnsCfg.getSubcomponents());

    final List<TableColumnBase> tableColumns = new ArrayList<>();
    for (final VLViewComponentXML columnConfig : resolveColsCfg) {

      final TableColumnBase tableColumn = new TreeTableColumn<>();
      tableColumn.setId(columnConfig.getId());

      // css
      final String css = columnConfig.getPropertyValue("styleClass");
      if (StringUtils.isNotBlank(css)) {
        tableColumn.getStyleClass().add(css.split(","));
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
        final Label graphic = new Label();
        IconUtils.setIcon(graphic, columnConfig);
        tableColumn.setGraphic(graphic);
      }

      // cell width
      if (columnConfig.getId().contentEquals("BlankSpacer")) {
        tableColumn.prefWidthProperty().set(20);
      } else {
        String prefWidth = columnConfig.getPropertyValue(XMLConstants.PREF_WIDTH);
        if (StringUtils.isEmpty(prefWidth)) {
          prefWidth = "50";
        }

        try {
          tableColumn.setPrefWidth(Integer.valueOf(prefWidth));
        } catch (final Exception ex) {
          tableColumn.setPrefWidth(50);
        }
      }

      // sortable
      final boolean sortable = columnConfig.getBooleanProperty(XMLConstants.SORTABLE, false);
      tableColumn.setSortable(sortable);

      // cell value factory
      ((TreeTableColumn) tableColumn).setCellValueFactory(param -> {
        if (((CellDataFeatures) param).getValue().getValue() instanceof OperationData) {
          final OperationData operationData = (OperationData) ((CellDataFeatures) param).getValue().getValue();
          final SimpleObjectProperty<OperationData> op = new SimpleObjectProperty();
          op.set(operationData);
          return op;
        }
        return null;
      });

      // cell factory
      final String presenter = columnConfig.getPropertyValue("presenter");
      if (!StringUtils.isEmpty(presenter)) {
        if (tableColumn instanceof TreeTableColumn) {
          ((TreeTableColumn) tableColumn).setCellFactory(arg0 -> {
            final EpTreeTableCell epTreeTableCell = new EpTreeTableCell<>();
            epTreeTableCell.setController(controller);
            epTreeTableCell.setConfiguration(columnConfig);
            epTreeTableCell.setPresenteId(presenter);
            return epTreeTableCell;
          });
        }
      }

      tableColumns.add(tableColumn);
    }

    return tableColumns;
  }


  /**
   * Reload the table according to setting (colums)
   *
   * @param tableSetting
   */
  public void reloadTableSetting(final VLViewTableSettingXML tableSetting) {
    final List<TreeTableColumn> newcols = new ArrayList<>();
    for (final VLViewTableSettingColumnXML tcol : tableSetting.getColumns()) {
      for (final TreeTableColumn col : treeView.getColumns()) {
        if (tcol.getId().equals(col.getId())) {
          col.setPrefWidth(tcol.getWidth());
          col.setVisible(tcol.isDisplayed());
          newcols.add(col);
        }
      }
    }

    treeView.getColumns().clear();
    for(TreeTableColumn e: newcols) {
      treeView.getColumns().add(e);
    }
  }


  /**
   * Set table columns to columns from table wizardConfiguration
   *
   * @param tableColumns
   */
  public void setColumns(final List<TreeTableColumn> tableColumns) {
    // if preferred setting is not null, load columns according to it
    final VLViewTableSettingXML preferredSetting = null;//DatatableSettingsHelper.instance().getPreferredFor("TO DO");
    if (preferredSetting != null) {
      reloadTableSetting(preferredSetting);
    } else {
      if ((tableColumns != null) && !tableColumns.isEmpty()) {
        for(TreeTableColumn e: tableColumns) {
          treeView.getColumns().add(e);
        }
      }
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getTableStructure() {
    return treeView;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setNoContent() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setLoading() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setData(MultipleResult multipleResult) {}
}
