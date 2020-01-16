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

package io.github.jsoagger.jfxcore.engine.components.browser;




import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import io.github.jsoagger.jfxcore.engine.client.components.ComponentToButtonBaseHelper;
import io.github.jsoagger.jfxcore.engine.client.utils.IPageRequest;
import io.github.jsoagger.jfxcore.engine.client.utils.IPageResult;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.INoContentPane;
import io.github.jsoagger.jfxcore.api.InjectableComponent;
import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.message.ErrorPane;
import io.github.jsoagger.jfxcore.engine.components.message.NoContentPaneHelper;
import io.github.jsoagger.jfxcore.engine.components.security.CriteriaContext;
import io.github.jsoagger.jfxcore.engine.components.simpledetails.SimpleDetailsPane;
import io.github.jsoagger.jfxcore.engine.components.table.cell.ActionnableCell;
import io.github.jsoagger.jfxcore.engine.components.table.cell.CheckBoxTableCell;
import io.github.jsoagger.jfxcore.engine.components.table.simple.STVBottomToolbar;
import io.github.jsoagger.jfxcore.engine.components.table.simple.STVTopToolbar;
import io.github.jsoagger.jfxcore.engine.components.table.simple.VLTableDataUpdataMode;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.model.ObjectModel;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;
import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.CheckBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class FolderContentBrowserTableview extends AnchorPane implements InjectableComponent, IBuildable {

  private final SimpleObjectProperty<FolderTableViewStatus> status = new SimpleObjectProperty<>(FolderTableViewStatus.BUILDING_VIEW);

  protected BorderPane tableLayout = new BorderPane();
  protected STVTopToolbar topToolbar = null;
  protected STVBottomToolbar bottomToolbar = null;
  protected TableView<ObjectModel> tableView = null;

  protected VLTableDataUpdataMode updateMode = VLTableDataUpdataMode.REPLACE;
  protected SimpleBooleanProperty selectable = new SimpleBooleanProperty(false);

  protected IPageResult currentPageResult = null;
  protected SimpleBooleanProperty isModifying = null;
  protected Pane readyPane = new StackPane();

  protected AbstractViewController controller = null;
  protected VLViewComponentXML configuration = null;

  protected Node errorPane = null;

  protected CriteriaContext criteriaContext;
  protected Node materialButton = null;
  protected VBox rightPane = new VBox();


  /**
   * Constructor
   */
  public FolderContentBrowserTableview() {
    super();
    isModifying = new SimpleBooleanProperty(false);

    setDataLoader(null);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    status.addListener((ChangeListener<FolderTableViewStatus>) (observable, oldValue, newValue) -> {
      statusChanged(observable, oldValue, newValue);
    });

    this.controller = (AbstractViewController) controller;
    this.configuration = configuration;
    bottomToolbar = new STVBottomToolbar();

    NodeHelper.setHVGrow(this);
    NodeHelper.setHgrow(bottomToolbar);

    // selectable rows or not?
    final boolean selectable = configuration.getBooleanProperty(XMLConstants.SELECTABLE);
    this.selectable.set(selectable);

    buildTableView();
    buildRightPane();
    buildNoContentPane();
    loadFirstPage();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }


  /*-----------------------------------------------------------------------------
  | TABLE VIEW STATUS MANAGEMENT
   *=============================================================================*/
  private void statusChanged(ObservableValue<? extends FolderTableViewStatus> observable, FolderTableViewStatus oldValue, FolderTableViewStatus newValue) {

    switch (newValue) {
      case BUILDING_VIEW:
        // setProcessing();
        break;

      case LOADING_DATA:
        setProcessing();
        break;

      case DATA_LOADED:
        dataLoaderSucceed();
        break;

      case NO_CONTENT:
        setNoContent();
        break;

      case READY:
        setReady();
        break;

      case ERROR:
        setTechnicalErrorOccurs();
        break;

      default:
        setProcessing();
        break;
    }
  }

  /*-----------------------------------------------------------------------------
  | TABLE VIEW  SECTION
   *=============================================================================*/
  // @formatter:off
  /**
   * If, true, the table is displayed if table's items are empty. By default if the items are empty,
   * the table is hidden and the no content pane is displayed.
   *
   * @param data.value
   */
  private boolean displayTableIfEmpty = false;

  protected void buildTableView() {
    tableView = new TableView<>();
    final List<TableColumnBase> tableColumns = getTableColumns();
    for(TableColumnBase t: tableColumns) {
      tableView.getColumns().add((TableColumn<ObjectModel, ?>) t);
    }

    // single or multiple, default is multiple
    final String selectionMode = configuration.getPropertyValue("selectionMode", "multiple");
    if ("multiple".equalsIgnoreCase(selectionMode)) {
      tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    } else {
      tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    // displayTableIfEmpty
    displayTableIfEmpty = configuration.getBooleanProperty("displayTableIfEmpty", false);
  }

  public List<TableColumnBase> getTableColumns() {

    final VLViewComponentXML toResolveColumnsCfg = configuration.getComponentById("Columns").orElse(null);
    final List<VLViewComponentXML> resolveColsCfg = ComponentUtils.resolveDefinitions(controller, toResolveColumnsCfg.getSubcomponents());

    final List<TableColumnBase> tableColumns = new ArrayList<>();
    for (final VLViewComponentXML columnConfig : resolveColsCfg) {

      // final TableColumnBase tableColumn = (TableColumnBase)
      // ReflectionUtils.newInstance(columnClass
      // .getCanonicalName());
      final TableColumnBase tableColumn = new TableColumn<>();
      tableColumn.setId(columnConfig.getId());

      // css
      final String css = columnConfig.getPropertyValue("styleClass");
      if (StringUtils.isNotBlank(css)) {
        for(String e :css.split(",")) {
          tableColumn.getStyleClass().add(e);
        }
      }

      // visibility
      final boolean visible = columnConfig.getBooleanProperty(XMLConstants.VISIBLE, true);
      tableColumn.setVisible(visible);

      // label
      final String labelString = NodeHelper.getLabel(columnConfig, controller);
      tableColumn.setText(labelString.toUpperCase());

      // cell width
      final String prefWidth = columnConfig.getPropertyValue(XMLConstants.PREF_WIDTH);
      if (!StringUtils.isEmpty(prefWidth)) {
        tableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(Double.valueOf(prefWidth)));
      } else {
        tableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(Double.valueOf(0.10)));
      }

      // sortable
      final boolean sortable = columnConfig.getBooleanProperty(XMLConstants.SORTABLE);
      tableColumn.setSortable(sortable);

      // cell value factory
      final String valueFactory = columnConfig.getPropertyValue(XMLConstants.CELL_VALUE_FACTORY);
      if (!StringUtils.isEmpty(valueFactory)) {
        if (tableColumn instanceof TableColumn) {
          ((TableColumn) tableColumn).setCellValueFactory(new PropertyValueFactory<>(valueFactory));
        }

        if (tableColumn instanceof TreeTableColumn) {
          ((TreeTableColumn) tableColumn).setCellValueFactory(new TreeItemPropertyValueFactory(valueFactory));
        }
      }

      // cell factory
      final String cellFactory = columnConfig.getPropertyValue(XMLConstants.CELL_FACTORY);
      if (!StringUtils.isEmpty(cellFactory)) {
        if (tableColumn instanceof TableColumn) {
          ((TableColumn) tableColumn).setCellFactory(arg0 -> {

            Object cell = null;

            try {

              final Class clazz = Class.forName(cellFactory);

              // cell may be an actionnable or something else
              if (ActionnableCell.class.isAssignableFrom(clazz)) {
                try {

                  final Constructor constructor = clazz.getConstructor(new Class[] {AbstractViewController.class, VLViewComponentXML.class});
                  cell = constructor.newInstance(controller, columnConfig);

                } catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e1) {
                  e1.printStackTrace();
                }
              } else {

                cell = clazz.newInstance();
              }

              return (TableCell) cell;
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e2) {
              e2.printStackTrace();
            }
            return null;
          });
        }

        if (tableColumn instanceof TreeTableColumn) {
          ((TreeTableColumn) tableColumn).setCellFactory(arg0 -> {

            Object cell = null;

            try {

              final Class clazz = Class.forName(cellFactory);

              try {

                final Constructor constructor = clazz.getConstructor(new Class[] {AbstractViewController.class, VLViewComponentXML.class});
                cell = constructor.newInstance(controller, columnConfig);

              } catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e1) {
                e1.printStackTrace();
              }

              return (TreeTableCell) cell;
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e2) {
              e2.printStackTrace();
            }
            return null;
          });
        }

        // graphic
        final VLViewComponentXML graphicCong = columnConfig.getComponentById("Graphic").orElse(null);
        if (graphicCong != null) {
          final String graphicClass = graphicCong.getPropertyValue(XMLConstants.CLASS);
          final ButtonBase node = (ButtonBase) ReflectionUIUtils.newInstance(graphicClass);
          ComponentToButtonBaseHelper.setOnAction(graphicCong, node, controller);
          tableColumn.setGraphic(node);
        }
      }

      tableColumns.add(tableColumn);
    }

    return tableColumns;
  }
  // @formatter:on

  /*-----------------------------------------------------------------------------
  | TABLE VIEW HEADER SECTION
   *=============================================================================*/
  // @formatter:off
  protected TableColumn headerCheckboxCol = new TableColumn<>();
  protected CheckBox headerColCheckbox = new CheckBox();
  protected SimpleBooleanProperty hasSelectedRowProperty = new SimpleBooleanProperty(false);
  protected VBox topContainer = new VBox();

  /**
   * If true, if the table is empty the listViewPaneHeader is displayed, the table is hidden and no
   * content pane is displayed in center.
   */
  private boolean displayHeaderIfEmpty = false;

  protected void buildHeader() {
    topToolbar = new STVTopToolbar(controller, configuration);
    // topToolbar.setTableView(this);
    topToolbar.build();

    topContainer.getChildren().add(topToolbar);
    tableLayout.setTop(topContainer);
    NodeHelper.setHgrow(topToolbar, topContainer);

    addSelectColumn();

    headerColCheckbox.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
      setHasSelected(headerColCheckbox.isSelected());
    });

    headerColCheckbox.setOnAction(e -> {
      if (headerColCheckbox.isSelected()) {
        for(ObjectModel i: tableView.getItems()) {
          i.selectedProperty().set(true);
        }
        topToolbar.setSelectedItems(tableView.getItems().size());
      } else {
        for(ObjectModel i: tableView.getItems()) {
          i.selectedProperty().set(false);
        }
        tableView.getSelectionModel().clearSelection();
        topToolbar.setSelectedItems(0);
      }
    });

    // displayHeaderIfEmpty
    displayHeaderIfEmpty = configuration.getBooleanProperty("displayHeaderIfEmpty", false);
  }
  // @formatter:on

  // @formatter:off
  /*-----------------------------------------------------------------------------
  | NO CONTENT PANE SECTION
   *=============================================================================*/
  private INoContentPane noContentPane = null;
  private INoContentPane processingPane = null;
  private VLViewComponentXML noContentPaneConfiguration = null;

  protected void buildNoContentPane() {
    // no content pane, when data size is 0
    if (noContentPaneConfiguration == null) {
      noContentPaneConfiguration = configuration.getComponentById("NoContentPane").orElse(null);
    }
    noContentPane = NoContentPaneHelper.buildFrom(controller, noContentPaneConfiguration);

    // no content error pane when technical error occurs
    final VLViewComponentXML techNoContentPaneConfiguration = configuration.getComponentById("TechnicalNoContentPane").orElse(null);
    if (techNoContentPaneConfiguration != null) {
      final INoContentPane errorPane = NoContentPaneHelper.buildFrom(controller, techNoContentPaneConfiguration);
      this.errorPane = errorPane.getDisplay();
    } else {
      errorPane = new ErrorPane();
    }

    // processing pane
    final VLViewComponentXML processingContentPaneConfiguration = configuration.getComponentById("ProcessingPane").orElse(null);
    if (processingContentPaneConfiguration != null) {
      processingPane = NoContentPaneHelper.buildFrom(controller, processingContentPaneConfiguration);
    } else {
      errorPane = new ErrorPane();
    }
  }

  public void setNoContent() {
    if (displayHeaderIfEmpty) {
      setTableView();
      // tableLayout.setBottom(noContentPane);
    } else {
      getChildren().clear();
      getChildren().add(noContentPane.getDisplay());
      AnchorPane.setTopAnchor(noContentPane.getDisplay(), 0.0);
      AnchorPane.setLeftAnchor(noContentPane.getDisplay(), 0.0);
      AnchorPane.setRightAnchor(noContentPane.getDisplay(), 0.0);
      AnchorPane.setBottomAnchor(noContentPane.getDisplay(), 0.0);
      // buildMaterialButton();
    }
  }

  public void setTechnicalErrorOccurs() {
    bottomToolbar.setPageResult(null);
    getChildren().clear();
    getChildren().add(errorPane);
    AnchorPane.setTopAnchor(errorPane, 0.0);
    AnchorPane.setLeftAnchor(errorPane, 0.0);
    AnchorPane.setRightAnchor(errorPane, 0.0);
    AnchorPane.setBottomAnchor(errorPane, 0.0);
  }


  /*-----------------------------------------------------------------------------
  | DATA LOADER
   *=============================================================================*/
  public void setDataLoader(Function<IPageRequest, IPageResult> dataLoader) {}


  private void dataLoaderSucceed() {}


  // @formatter:on
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


  public void setReady() {
    readyPane.setStyle("-fx-border-color:-external-border-color;" + "-fx-border-width: 0.2 0 0.2 0;");
    tableLayout.setCenter(readyPane);
    topToolbar.setVisible(true);
    bottomToolbar.setVisible(true);
  }


  public void setProcessing() {
    bottomToolbar.setPageResult(null);
    getChildren().clear();
    getChildren().add(processingPane.getDisplay());
    AnchorPane.setLeftAnchor(processingPane.getDisplay(), 0.0);
    AnchorPane.setRightAnchor(processingPane.getDisplay(), 0.0);
    AnchorPane.setTopAnchor(processingPane.getDisplay(), 0.0);
    AnchorPane.setBottomAnchor(processingPane.getDisplay(), 0.0);
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


  public void setTitle(String title) {
    topToolbar.setTitle(title);
  }


  public void setTitle(Node title) {
    topToolbar.setTitle(title);
  }


  /**
   * Load data, current page index 0 whatever the update mode.
   */
  public void loadFirstPage() {}


  public void refreshCurrentPage() {}


  public void scrollDatasDown() {}


  public void scrollDatasUp() {}


  /**
   * {@inheritDoc}
   */
  @Override
  public String getInternalId() {
    return configuration.getId();
  }


  public TableView<ObjectModel> getTableView() {
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
    isModifying.set(modifying);;
  }


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
    // cell.setTableView(this);
    return cell;
  }


  /**
   * hide all checkbox
   */
  public void cancelModify() {
    if (isModifying()) {
      headerCheckboxCol.setVisible(false);
      for(ObjectModel e: tableView.getItems()) {
        e.selectedProperty().set(false);
      }

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


  public void removeItems(List<Long> toDelete) {
    final List<ObjectModel> toDeleteObjs = new ArrayList<>();
    for(ObjectModel item: getTableView().getItems()) {
      if (item.isSelected()) {
        toDeleteObjs.add(item);
      }
    }

    for (final ObjectModel model : toDeleteObjs) {
      getTableView().getItems().remove(model);
    }
  }


  public CriteriaContext criteriaContext() {
    return criteriaContext;
  }

  /**
   * Enum for handling table status
   *
   * @author Ramilafananana VONJISOA
   * @mailto yvonjisoa@nexitia.com
   * @date 2019
   */
  private enum FolderTableViewStatus {
    BUILDING_VIEW, READY, LOADING_DATA, DATA_LOADED, ERROR, NO_CONTENT;
  }
}
