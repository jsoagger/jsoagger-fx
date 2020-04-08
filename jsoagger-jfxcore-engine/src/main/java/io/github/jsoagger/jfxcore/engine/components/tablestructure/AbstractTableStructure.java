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

package io.github.jsoagger.jfxcore.engine.components.tablestructure;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IMinimizable;
import io.github.jsoagger.jfxcore.api.IModifiableToolbarHolder;
import io.github.jsoagger.jfxcore.api.ITableStructure;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.components.message.NoContentPaneHelper;
import io.github.jsoagger.jfxcore.engine.components.pagination.IPageable;
import io.github.jsoagger.jfxcore.engine.components.pagination.IPaginationBar;
import io.github.jsoagger.jfxcore.engine.components.security.CriteriaContext;
import io.github.jsoagger.jfxcore.engine.components.table.cell.EpTableCell;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.table.TableRowSelectPresenter;
import io.github.jsoagger.jfxcore.engine.components.toolbar.AbstractToolbar;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.events.ElementRemovedFromTableEvent;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;
import io.github.jsoagger.jfxcore.engine.utils.ToolbarUtils;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Node;

/**
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 12 févr. 2018
 */
public abstract class AbstractTableStructure
    implements ITableStructure, IPageable, IModifiableToolbarHolder, IMinimizable {

  protected AbstractToolbar toolbar = null;
  protected IPaginationBar pagination = null;
  protected IBuildable header = null;
  protected IBuildable footer = null;
  protected IBuildable noContentPane = null;

  protected AbstractViewController controller = null;
  protected VLViewComponentXML rootConfiguration = null;

  protected ObservableSet<OperationData> selections = FXCollections.observableSet();

  protected SimpleBooleanProperty modifying = new SimpleBooleanProperty(false);
  protected SimpleObjectProperty<IOperationResult> model = new SimpleObjectProperty<>();
  protected SimpleIntegerProperty elementsCount = new SimpleIntegerProperty();
  protected SimpleStringProperty label = new SimpleStringProperty();
  protected List<IBuildable> defaultActions;
  /** May be used to extract query parameters */
  protected MultipleResult initialQuery = null;
  protected boolean loadFirstPageData = true;


  protected VLViewComponentXML headerConfiguration;
  protected VLViewComponentXML footerConfiguration;
  protected VLViewComponentXML toolbarConfiguration;
  protected VLViewComponentXML contentConfiguration;
  protected VLViewComponentXML paginationConfiguration;
  protected VLViewComponentXML noContentPaneConfiguration;
  protected VLViewComponentXML defaultActionsConfiguration;


  /**
   * Default Constructor
   */
  public AbstractTableStructure() {}


  /**
   * @{inheritedDoc}
   */
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.controller = (AbstractViewController) controller;
    this.rootConfiguration = configuration;

    noContentPaneConfiguration = rootConfiguration.getComponentById("NoContentPane").orElse(null);
    headerConfiguration = rootConfiguration.getComponentById("Header").orElse(null);
    footerConfiguration = rootConfiguration.getComponentById("Footer").orElse(null);
    toolbarConfiguration = rootConfiguration.getComponentById("Toolbar").orElse(null);
    contentConfiguration = rootConfiguration.getComponentById("Content").orElse(null);
    paginationConfiguration = rootConfiguration.getComponentById("Pagination").orElse(null);
    defaultActionsConfiguration = rootConfiguration.getComponentById("DefaultActions").orElse(null);
    loadFirstPageData = contentConfiguration.getBooleanProperty("loadFirstPage", true);

    buildToolbar();
    buildNoContentPane();
    buildHeader();
    buildFooter();
    buildContent();
    buildPagination();
    buildDefaultActions();

    model.addListener((ChangeListener<IOperationResult>) (observable, oldValue, newValue) -> {
      onModelUpdated(newValue);
    });

    if (controller.getModel() != null) {
      final IOperationResult result = (IOperationResult) controller.getModel();
      if (result.rootData() != null) {
        loadFirstPage();
      } else {
        System.out.println("[ERROR] Data have not been loaded but not root data found");
      }
    } else {
      System.out
          .println("[ERROR] Controller model is null, data have not been loaded for the table");
    }

    final ChangeListener<Boolean> mc = this::modifyingChanged;
    modifying.addListener(mc);
  }


  protected void modifyingChanged(ObservableValue<? extends Boolean> observable, Boolean oldValue,
      Boolean newValue) {
    if (newValue == Boolean.TRUE) {
    } else {
    }
  }

  public void buildHeader() {
    headerConfiguration = rootConfiguration.getComponentById("Header").orElse(null);
    String headerImpl = "ListViewPaneHeader";
    if (headerConfiguration != null) {
      headerImpl = headerConfiguration.getPropertyValue("headerImpl", "ListViewPaneHeader");
      header = (IBuildable) Services.getBean(headerImpl);
      header.buildFrom(controller, headerConfiguration);
    }
  }

  public void buildFooter() {
    footerConfiguration = rootConfiguration.getComponentById("Footer").orElse(null);
    String footerImpl = "";
    if (footerConfiguration != null) {
      footerImpl = footerConfiguration.getPropertyValue("footerImpl", null);
      footer = (IBuildable) Services.getBean(footerImpl);
      footer.buildFrom(controller, headerConfiguration);
    }
  }


  public void buildNoContentPane() {
    noContentPaneConfiguration = null;
    List<VLViewComponentXML> comps = rootConfiguration.getComponentsById("NoContentPane");
    if (comps.size() > 0) {
      for (VLViewComponentXML comp : comps) {
        if (StringUtils.isBlank(comp.getCriteria())) {
          noContentPaneConfiguration = comp;
          break;
        } else {
          boolean ok = controller.evaluateFilter(comp);
          if (ok) {
            noContentPaneConfiguration = comp;
            break;
          }
        }
      }
    }

    if (noContentPaneConfiguration != null)
      noContentPane = NoContentPaneHelper.buildFrom(controller, noContentPaneConfiguration);
  }

  protected void buildToolbar() {
    if (toolbarConfiguration != null) {
      toolbar = ToolbarUtils.buildToolbar(controller, this).orElse(null);
    }
  }

  /**
   * Build the pagination
   */
  public void buildPagination() {
    if (paginationConfiguration != null) {
      final String paginationImpl =
          paginationConfiguration.getPropertyValue("paginationImpl", "SimplePaginationBar");
      pagination = (IPaginationBar) Services.getBean(paginationImpl);
      pagination.buildFrom(controller, paginationConfiguration);
      pagination.setPageable(this);

      final Integer rpp = rootConfiguration.getIntPropertyValue("rowPerPage");
      if (rpp > 0) {
        pagination.setCurrentPageSize(rpp.toString());
      }

      pagination.getDisplay().visibleProperty()
          .addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
          });
    }
  }



  public abstract ObservableList<OperationData> getItems();

  public abstract void forceLoadFirstPage();

  /**
   * @{inheritedDoc}
   */
  public SimpleIntegerProperty elementsCountProperty() {
    return elementsCount;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void loadLess(SimpleObjectProperty<MultipleResult> model) {

  }


  /**
   * Reload current Page, make server call.
   */
  public void refreshDatas() {
    selections.clear();
    getItems().clear();
    loadFirstPageData = true;
    if (childTree().isEmpty()) {
      forceLoadFirstPage();
    }
    // reload last item in tree
    else {
      OperationData data = childTree().get(childTree().size() - 1);
      SingleResult res = new SingleResult();
      res.setData(data);
      controller.setModel(res);
      forceLoadFirstPage();
    }
  }

  /**
   * Reload current Page, make server call.
   */
  public void refreshCurrentPage() {
    selections.clear();
    getItems().clear();
    if (childTree().isEmpty()) {
    }
    // reload last item in tree
    else {

    }
  }


  /**
   * Redisplay current datas, do not make remote server call
   */
  public void redisplayCurrentDatas() {
    selections.clear();
  }


  @Override
  public abstract void nextPage(SimpleObjectProperty<MultipleResult> model);


  @Override
  public abstract void previousPage(SimpleObjectProperty<MultipleResult> model);

  @Override
  public abstract void firstPage(SimpleObjectProperty<MultipleResult> model);


  @Override
  public abstract void lastPage(SimpleObjectProperty<MultipleResult> model);


  /**
   * @{inheritedDoc}
   */
  @Override
  public VLViewComponentXML getToolbarConfiguration() {
    return toolbarConfiguration;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public CriteriaContext criteriaContext() {
    return null;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void selectCheckboxes(boolean b) {
    if (b) {
      selectAllRows();
    } else {
      deselectAllRows();
    }
  }


  protected void selectAllRows() {

  }


  protected void deselectAllRows() {

  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void modify() {
    modifying.set(true);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void cancelModify() {
    modifying.set(false);
    endRowsEdition();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public SimpleBooleanProperty modifyingProperty() {
    return modifying;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void listCellSelected() {}


  public SimpleStringProperty labelProperty() {
    return label;
  }


  public Optional<Node> getToolbar() {
    if (toolbar == null) {
      return Optional.empty();
    }
    return Optional.of(toolbar.getDisplay());
  }


  public Optional<Node> getPagination() {
    if (pagination == null) {
      return Optional.empty();
    }

    return Optional.of(pagination.getDisplay());
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void minimize() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void maximize() {}


  public abstract Node getTableStructure();


  public abstract void setNoContent();


  public abstract void setLoading();


  public abstract void setData(MultipleResult multipleResult);


  /**
   * Getter of header
   *
   * @return the header
   */
  public IBuildable getHeader() {
    return header;
  }


  /**
   *
   */
  public void beginRowsEdition() {

  }


  public void endRowsEdition() {
    selections.clear();
    deselectAllRows();
  }


  public void addToSelection(Object item) {
    if (item != null && item instanceof TableRowSelectPresenter) {
      final TableRowSelectPresenter ts = (TableRowSelectPresenter) item;
      final Object data = ((EpTableCell) ts.getCell()).getTableRow().getItem();
      selections.add((OperationData) data);
    }
  }


  public void removeFromSelection(Object item) {
    if (item instanceof TableRowSelectPresenter) {
      OperationData data = (OperationData) ((TableRowSelectPresenter) item).getCell().getItem();
      if (data != null) {
        selections.remove(data);
      }
    } else {
      if (item != null) {
        selections.remove(item);
      }
    }
  }


  public void deleteSelectedRows() {
    selections.clear();
  }

  public enum TableStructureState {
    BUILDING, BUILDED;
  }


  public List<OperationData> getSelectedElements() {
    return new ArrayList<>(selections);
  }


  public boolean isAllRowsSelected() {
    return selections.size() > 0 && selections.size() == getItems().size();
  }


  public void deleteItem(OperationData data) {
    getItems().remove(data);
    selections.clear();

    if (getItems().size() == 0) {
      setNoContent();
    }

    final ElementRemovedFromTableEvent e = new ElementRemovedFromTableEvent();
    e.setSourceController(controller);
    controller.dispatchEvent(e);
  }

  /**
   * When the table displays structure, whe need to keep track of children hyerarchy for back button
   * handling. Reponsability of client to manage this.
   */
  protected ObservableList<OperationData> childTree = FXCollections.observableArrayList();


  public ObservableList<OperationData> childTree() {
    return childTree;
  }


  public void clearItems() {
    getItems().clear();
  }


  public void clearChildrenTree() {
    childTree.clear();
  }


  public void pushChildrenTree(OperationData data) {
    childTree.add(data);
  }


  public OperationData popChildrenTree() {
    if (childTree.size() > 1) {
      // this is current item, remove it
      childTree.remove(childTree.size() - 1);

      if (childTree.size() > 0) {
        // last item becomes current
        final OperationData data = childTree.get(childTree.size() - 1);
        return data;
      }

      return null;
    }

    return null;
  }


  @Override
  public VLViewComponentXML getContentConfig() {
    return contentConfiguration;
  }


  public VLViewComponentXML rootConfiguration() {
    return rootConfiguration;
  }


  @Override
  public MultipleResult initialQuery() {
    return initialQuery;
  }

  /**
   * Get header configuration.
   *
   * @return
   */
  public VLViewComponentXML headerConfiguration() {
    return headerConfiguration;
  }

  /**
   *
   * @return
   */
  public SimpleObjectProperty<IOperationResult> modelProperty() {
    return model;
  }

  public VLViewComponentXML getRootConfiguration() {
    return rootConfiguration;
  }

  public abstract FilteredList<OperationData> getFilteredDatas();


  public List<IBuildable> getDefaultActions() {
    return new ArrayList<>();
  }

  protected abstract void loadFirstPage();


  public abstract void onModelUpdated(IOperationResult newValue);


  protected void buildDefaultActions() {
    if (defaultActionsConfiguration != null && defaultActionsConfiguration.hasSubComponent()) {
      defaultActions = ComponentUtils.resolveAndGenerate(controller,
          defaultActionsConfiguration.getSubcomponents());
    }
  }

  public abstract void buildContent();


  public boolean filteringAlwaysShown() {
    return headerConfiguration.getBooleanProperty("filteringAlwaysShown", false);
  }


  /**
   * @return the footer
   */
  public IBuildable getFooter() {
    return footer;
  }
}
