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



import java.util.concurrent.CompletableFuture;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.pagination.tree.ITreePaginatedDataProvider;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.SingleTableStructure;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.table.TableResponsiveMatrix;
import io.github.jsoagger.jfxcore.engine.components.tree.cell.EpTreeCell;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 20 févr. 2018
 */
public class TreeContent extends SingleTableStructure {

  protected TreeView<OperationData> treeView;
  protected TableResponsiveMatrix tableResponsiveMatrix;

  protected ITreePaginatedDataProvider treePaginatedDataProvider;
  protected ITreePaginatedDataProvider tagsPaginatedDataProvider;

  protected String tagsLoader;


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
    treeView = new TreeView<>();
    final String treePaginatedDataProvider = contentConfiguration.getPropertyValue("dataLoader");

    if (StringUtils.isNotBlank(treePaginatedDataProvider)) {
      this.treePaginatedDataProvider = (ITreePaginatedDataProvider) Services.getBean(treePaginatedDataProvider);
      this.treePaginatedDataProvider.setRootContext(controller);
    }

    treeView.setCache(true);
    treeView.setCacheHint(CacheHint.SPEED);

    final String presenter = contentConfiguration.getPropertyValue("presenter");
    if (StringUtils.isNotBlank(presenter)) {
      final Object userdata = this;
      treeView.setCellFactory(param -> {
        final EpTreeCell<OperationData> cell = new EpTreeCell<>();
        cell.setPresenterId(presenter);
        cell.setUserData(userdata);
        cell.setConfiguration(contentConfiguration);
        cell.setController(controller);
        return cell;
      });
    }

    // the root item
    final TreeItem dummyRoot = new TreeItem<>();

    if (this.treePaginatedDataProvider != null) {

      final TreeItem root = this.treePaginatedDataProvider.getRootItem();
      dummyRoot.getChildren().addAll(root);

      tagsLoader = contentConfiguration.getPropertyValue("tagsLoader");
      if (StringUtils.isNotBlank(tagsLoader)) {
        this.tagsPaginatedDataProvider = (ITreePaginatedDataProvider) Services.getBean(tagsLoader);
        final TreeItem tagsRoot = this.tagsPaginatedDataProvider.getRootItem();
        dummyRoot.getChildren().addAll(tagsRoot);
      }

      treeView.setRoot(dummyRoot);
      treeView.setShowRoot(false);
    }

    // style
    NodeHelper.setStyleClass(treeView, contentConfiguration, "treeViewStyleClass", true);
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
