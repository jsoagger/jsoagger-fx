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

package io.github.jsoagger.jfxcore.engine.components.tablestructure.list;



import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IListviewPaneContent;
import io.github.jsoagger.jfxcore.api.IPaginatedDataProvider.Direction;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.list.impl.AbstractListCell;
import io.github.jsoagger.jfxcore.engine.components.list.utils.AbstractListView;
import io.github.jsoagger.jfxcore.engine.components.list.utils.FixedSizeListView;
import io.github.jsoagger.jfxcore.engine.components.pagination.IPageable;
import io.github.jsoagger.jfxcore.engine.components.presenter.CellPresenterFactory;
import io.github.jsoagger.jfxcore.engine.components.security.CriteriaContext;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.SingleTableStructure;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardListViewController;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;

/**
 * An advanced {@link ListView} with header toolbar and pagination.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ListViewContent extends SingleTableStructure
    implements IListviewPaneContent, IPageable {

  /*-----------------------------------------------------------------------------
  | static fields
   *=============================================================================*/
  /*-----------------------------------------------------------------------------
  | Private fields
   *=============================================================================*/
  protected StackPane contentWrapper = new StackPane();
  protected AbstractListView listView;

  /** For mono selection */
  protected ToggleGroup toggleGroup = new ToggleGroup();


  /**
   * Default Constructor
   */
  public ListViewContent() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setData(MultipleResult multipleResult) {
    try {

      items.addAll(multipleResult.getData());
      Platform.runLater(() -> {
        contentWrapper.getChildren().clear();
        contentWrapper.getChildren().add(listView);
      });

      contentWrapper.layout();
      if (pagination == null || !pagination.isLoadMorePagination()) {
        // listView.getItems().clear();
      }

      if (pagination != null && pagination.isLoadMorePagination()
          && pagination.currentDirection() == Direction.PREVIOUS) {
        // listView.getItems().clear();
      }

    } catch (Throwable e) {
      e.printStackTrace();
    }
  }


  /**
   * @{inheritedDoc}
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public void buildContent() {
    super.buildContent();
    if (contentConfiguration != null) {
      super.buildContent();

      listView = new FixedSizeListView<>();
      listView.setItems(items);

      NodeHelper.setStyleClass(listView, contentConfiguration, "listViewStyleClass", true);
      NodeHelper.setStyleClass(contentWrapper, contentConfiguration, "listWrapperStyleClass", true);

      String cellFactory = contentConfiguration.getPropertyValue("cellFactory");
      String cellPresenter = contentConfiguration.getPropertyValue("cellPresenter");
      if (StringUtils.isNotBlank(cellFactory)) {

        // do this because sometimes, cell needs extra datas from its
        // configuration
        Object userdata = this;
        listView.setCellFactory(param -> {
          final AbstractListCell cell = (AbstractListCell) Services.getBean(cellFactory);
          cell.setUserData(userdata);
          cell.setConfiguration(contentConfiguration);
          cell.setController(controller);

          // presenter can be global to all rows
          // or specific of each row.
          // if declared in cellFactory level, it is a global one.
          if (StringUtils.isNotBlank(cellPresenter)) {
            CellPresenterFactory presenter = (CellPresenterFactory) Services.getBean(cellPresenter);
            cell.setPresenter(presenter);
            presenter.setCell(cell);
            presenter.setConfiguration(contentConfiguration);
            presenter.setController(controller);
          }

          return cell;
        });
      }

      if (controller instanceof StandardListViewController) {
        ((StandardListViewController) controller).setListView(listView);
        listView.getSelectionModel().selectedItemProperty()
            .addListener((ChangeListener<OperationData>) (observable, oldValue, newValue) -> {
              ((StandardListViewController) controller).setSelectedModel(newValue);
            });
      }
    }
  }


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
  public void selectCheckboxes(boolean b) {}


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
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return null;
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


  /**
   * @{inheritedDoc}
   */
  @Override
  public SimpleIntegerProperty elementsCountProperty() {
    return elementsCount;
  }

  /*-----------------------------------------------------------------------------
  | Grag and drop of material button
   *=============================================================================*/
  private Button materialNode;


  private void initMaterialButton() {
    materialNode = new Button();
    IconUtils.setFontIcon("fa-plus-circle:70", materialNode);
    materialNode.getStyleClass().add("button-material");
    controller.getRootStructure().setMaterialNode(materialNode);
  }


  /**
   * Getter of items
   *
   * @return the items
   */
  @Override
  public ObservableList<OperationData> getItems() {
    return items;
  }


  /**
   *
   */
  public void resetItems() {
    listView.setItems(items);
  }


  public void setItems(ObservableList<OperationData> subentries) {
    listView.setItems(subentries);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getTableStructure() {
    return contentWrapper;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setNoContent() {
    Platform.runLater(() -> {
      contentWrapper.getChildren().clear();
      if (pagination != null) {
        pagination.getDisplay().pseudoClassStateChanged(nodata, true);
      }
      if (noContentPane != null) {
        contentWrapper.getChildren().add(noContentPane.getDisplay());
      }
    });
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setLoading() {
    Platform.runLater(() -> {
      contentWrapper.getChildren().clear();
      contentWrapper.getChildren().add(NodeHelper.getProcessingIndicator());
    });
  }

  public ToggleGroup getToggleGroup() {
    return toggleGroup;
  }
}
