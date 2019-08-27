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

package io.github.jsoagger.jfxcore.engine.components.table.simple;




import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import io.github.jsoagger.jfxcore.engine.client.utils.IPageRequest;
import io.github.jsoagger.jfxcore.engine.client.utils.IPageResult;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.api.InjectableComponent;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.security.CriteriaContext;
import io.github.jsoagger.jfxcore.engine.components.simpledetails.SimpleDetailsPane;
import io.github.jsoagger.jfxcore.engine.components.table.cell.CheckBoxTableCell;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.model.ObjectModel;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * A wrapper for javafx {@link TableView}. Default VLTableDataUpdataMode is replace. Call of refresh
 * on the table will reload/load all data.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class SimpleTableView<T extends ObjectModel> extends AnchorPane implements InjectableComponent {

  private final BorderPane tableLayout = new BorderPane();
  private STVTopToolbar topToolbar = null;
  private STVBottomToolbar bottomToolbar = null;
  private final VBox topContainer = new VBox();

  private VLTableDataUpdataMode updateMode = VLTableDataUpdataMode.REPLACE;
  private final IPageResult currentPageResult = null;
  private SimpleBooleanProperty isModifying = null;
  private TableView<T> tableView = null;
  private Pane readyPane = null;
  private Pane noContentPane = null;
  private StackPane processingPane = null;
  private StackPane errorPane = null;
  private AbstractViewController controller = null;
  private VLViewComponentXML configuration = null;
  private Class modelClass = null;
  private final TableColumn headerCheckboxCol = new TableColumn<>();
  private CheckBox headerColCheckbox = null;
  private SimpleBooleanProperty hasSelectedRowProperty = null;
  private CriteriaContext criteriaContext;
  private final Node materialButton = null;
  private final VBox rightPane = new VBox();


  /**
   * Constructor
   */
  public SimpleTableView(AbstractViewController controller, VLViewComponentXML configuration) {
    super();

    this.controller = controller;
    this.configuration = configuration;

    readyPane = new StackPane();
    noContentPane = new StackPane();
    processingPane = new StackPane();
    errorPane = new StackPane();
    tableView = new TableView<>();
    isModifying = new SimpleBooleanProperty(false);
    bottomToolbar = new STVBottomToolbar();
    hasSelectedRowProperty = new SimpleBooleanProperty(false);
    headerColCheckbox = new CheckBox();

    NodeHelper.setHVGrow(this);
    NodeHelper.setHgrow(bottomToolbar);

    // selectable rows or not?
    final boolean selectable = configuration.getBooleanProperty(XMLConstants.SELECTABLE);

    final String modelName = configuration.getPropertyValue(XMLConstants.MODEL);
    if (StringUtils.isNotBlank(modelName)) {
      try {
        modelClass = Class.forName(modelName);
      } catch (final ClassNotFoundException e) {
      }
    } else {
      modelClass = ObjectModel.class;
    }

    topToolbar = new STVTopToolbar(controller, configuration);
    topToolbar.setTableView(this);
    topToolbar.build();

    topContainer.getChildren().add(topToolbar);
    NodeHelper.setHgrow(topToolbar, topContainer);

    tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    addSelectColumn();
    headerColCheckbox.setOnAction(e -> {
      if (headerColCheckbox.isSelected()) {
        //tableView.getItems().forEach(i -> i.selectedProperty().set(true));
        topToolbar.setSelectedItems(tableView.getItems().size());
      } else {
        //tableView.getItems().forEach(i -> i.selectedProperty().set(false));
        tableView.getSelectionModel().clearSelection();
        topToolbar.setSelectedItems(0);
      }
    });

    headerColCheckbox.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
      setHasSelected(headerColCheckbox.isSelected());
    });

    buildRightPane();

    tableLayout.setTop(topContainer);
    tableLayout.setStyle("-fx-background-color:white");

    setTableView();
  }


  /**
   * When row on tableviw is selected this pane is shown on the right side. Make it smarter!! If on
   * mobile view, i should be show over the table view.
   */
  private void buildRightPane() {
    rightPane.setStyle("-fx-background-color: white;" + "-fx-border-color: -divider-color; " + "-fx-border-width:0.5;" + "-fx-min-width: 500;");

    rightPane.managedProperty().bind(rightPane.visibleProperty());
    rightPane.setVisible(false);
    // setRight(rightPane);

    tableView.setRowFactory(param -> {

      final TableRow tableRow = new TableRow<>();
      tableRow.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseClicked -> {

        if (mouseClicked.getClickCount() == 2) {
          rightPane.setVisible(true);

          final SimpleDetailsPane condensedDetailsPane = new SimpleDetailsPane();
          rightPane.getChildren().clear();
          rightPane.getChildren().add(condensedDetailsPane);
        }
      });

      return tableRow;
    });
  }


  public SimpleBooleanProperty hasSelectedRowProperty() {
    return hasSelectedRowProperty;
  }


  public void setDataLoader(Function<IPageRequest, IPageResult> dataLoader) {
    initDataLoaderService();
  }


  private void initDataLoaderService() {}


  private void dataLoaderSucceed() {

  }


  public void setReady() {
    readyPane.setStyle("-fx-border-color:-external-border-color;" + "-fx-border-width: 0.2 0 0.2 0;");
    tableLayout.setCenter(readyPane);
    topToolbar.setVisible(true);
    bottomToolbar.setVisible(true);
  }


  public void setProcessing() {
    getChildren().clear();
    getChildren().add(processingPane);
    AnchorPane.setLeftAnchor(processingPane, 0.0);
    AnchorPane.setRightAnchor(processingPane, 0.0);
  }


  public void setNoContent() {
    if (displayHeaderIfEmpty) {
      setTableView();
      // tableLayout.setBottom(noContentPane);
    } else {
      getChildren().clear();
      getChildren().add(noContentPane);
      AnchorPane.setTopAnchor(noContentPane, 0.0);
      AnchorPane.setLeftAnchor(noContentPane, 0.0);
      AnchorPane.setRightAnchor(noContentPane, 0.0);

      // buildMaterialButton();
    }
  }

  private final boolean displayTopToolbar = true;
  private final boolean displayFooterPagination = true;


  public void setTableView() {

    getChildren().clear();
    getChildren().add(tableLayout);
    AnchorPane.setTopAnchor(tableLayout, 0.0);
    AnchorPane.setBottomAnchor(tableLayout, 0.0);
    AnchorPane.setLeftAnchor(tableLayout, 0.0);
    AnchorPane.setRightAnchor(tableLayout, 0.0);

    // buildMaterialButton();

    // if top toolbar is displayed, wrapp th table into a pane
    if (displayTopToolbar) {
      final VBox wrapper = new VBox();
      wrapper.setStyle("-fx-background-color:white");
      NodeHelper.setVgrow(wrapper, tableView);
      wrapper.setPadding(new Insets(32, 16, 16, 16));
      wrapper.getChildren().add(tableView);
      tableLayout.setCenter(wrapper);

      if (displayFooterPagination) {
        wrapper.getChildren().add(bottomToolbar);
      }
    } else {
      tableLayout.setCenter(tableView);
      if (displayFooterPagination) {
        tableLayout.setBottom(bottomToolbar);;
      }
    }

    tableLayout.setVisible(true);
  }


  public void setNoContent(Pane noContentPane) {
    this.noContentPane = noContentPane;
  }


  public void setErrorLoading() {
    getChildren().clear();
    getChildren().add(errorPane);
    AnchorPane.setLeftAnchor(errorPane, 0.0);
    AnchorPane.setRightAnchor(errorPane, 0.0);
  }


  public void setTitle(String title) {
    topToolbar.setTitle(title);
  }


  public void setTitle(Node title) {
    topToolbar.setTitle(title);
  }


  @SuppressWarnings({"unchecked"})
  public void setTableColumns(List<TableColumnBase> columns) {
    for(TableColumnBase e: columns) {
      tableView.getColumns().add((TableColumn<T, ?>) e);
    }
  }


  /**
   * Load data, current page index 0 whatever the update mode.
   */
  public void loadFirstPage() {
    cancelModify();
    updateMode = VLTableDataUpdataMode.REPLACE;
  }


  public void refreshCurrentPage() {
    final IPageRequest pageRequest = currentPageResult.sourceRequest();
  }


  public void scrollDatasDown() {
    final IPageRequest pageRequest = currentPageResult.getNextIPageRequest();
  }


  public void scrollDatasUp() {
    final IPageRequest pageRequest = currentPageResult.getNextIPageRequest();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public String getInternalId() {
    return configuration.getId();
  }


  public TableView<T> getTableView() {
    return tableView;
  }


  /**
   * Get the isModifying
   *
   * @return the isModifying
   */
  public boolean isModifying() {
    return isModifying.get();
  }


  /**
   * Set the isModifying
   *
   * @param isModifying the isModifying to set
   */
  private void setModifying(boolean modifying) {
    this.isModifying.set(modifying);;
  }


  @SuppressWarnings({"unchecked"})
  private void addSelectColumn() {
    headerCheckboxCol.setPrefWidth(40);
    headerCheckboxCol.setCellValueFactory(new PropertyValueFactory<>("selected"));
    headerCheckboxCol.setCellFactory(param -> buildCell());

    headerCheckboxCol.setGraphic(headerColCheckbox);
    headerCheckboxCol.setVisible(false);
    tableView.getColumns().add(0, headerCheckboxCol);
  }


  public void modify() {

    if (!isModifying()) {
      setModifying(true);
      headerCheckboxCol.setVisible(true);

      // clear tableview selection
      tableView.getSelectionModel().clearSelection();
    }
  }


  private CheckBoxTableCell buildCell() {
    final CheckBoxTableCell cell = new CheckBoxTableCell();
    cell.setTableView(this);
    return cell;
  }


  /**
   * hide all checkbox
   */
  public void cancelModify() {
    if (isModifying()) {
      headerCheckboxCol.setVisible(false);
      //tableView.getItems().forEach(e -> e.selectedProperty().set(false));
      tableView.getSelectionModel().clearSelection();

      topToolbar.done();
      headerColCheckbox.selectedProperty().set(false);
      hasSelectedRowProperty.set(false);
      topToolbar.setSelectedItems(0);
      setModifying(false);
    }
  }


  public void setHasSelected(boolean selected) {
    if (selected) {
      hasSelectedRowProperty().set(true);
    } else {
      boolean hasSelectedRow = false;
      for (final Object modelObject : getTableView().getItems()) {
        final ObjectModel model = (ObjectModel) modelObject;
        if (model.isSelected()) {
          hasSelectedRow = true;
          break;
        }
      }

      hasSelectedRowProperty().set(hasSelectedRow);
    }

    int selectedNum = 0;
    for (final Object modelObject : getTableView().getItems()) {
      final ObjectModel model = (ObjectModel) modelObject;
      if (model.isSelected()) {
        selectedNum++;
      }
    }

    topToolbar.setSelectedItems(selectedNum);
  }


  /**
   * @param toDelete
   */
  public void removeItems(List<Long> toDelete) {
    final List<ObjectModel> toDeleteObjs = new ArrayList<>();
    //    getTableView().getItems().forEach(item -> {
    //      if (item.isSelected()) {
    //        toDeleteObjs.add(item);
    //      }
    //    });

    for (final ObjectModel model : toDeleteObjs) {
      getTableView().getItems().remove(model);
    }
  }


  public CriteriaContext criteriaContext() {
    return criteriaContext;
  }

  /**
   * If, true, the table is displayed if table's items are empty. By default if the items are empty,
   * the table is hidden and the no content pane is displayed.
   *
   * @param data.value
   */
  private boolean displayTableIfEmpty = false;


  public void setDisplayTableIfEmpty(boolean value) {
    this.displayTableIfEmpty = value;
  }

  /**
   * If true, if the table is empty the listViewPaneHeader is displayed, the table is hidden and no
   * content pane is displayed in center.
   */
  private boolean displayHeaderIfEmpty = false;


  public void setDisplayHeaderIfEmpty(boolean b) {
    displayHeaderIfEmpty = b;
  }
}
