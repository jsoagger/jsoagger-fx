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

package io.github.jsoagger.jfxcore.engine.components.pagination.tree;



import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.tree.LazyTreeItem;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import com.google.gson.JsonObject;

import javafx.scene.control.TreeItem;

/**
 *
 * Navigation is done via modelFullId of model of current controller.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 20 janv. 2018
 */
public abstract class FolderTreeNavigationDataLoader implements ITreePaginatedDataProvider {

  protected IOperation countFolderedOperation;
  protected IOperation getRootFolderOperation;
  protected IOperation getChildrenFolderOperation;
  protected IOperation paginateFolderedOperation;

  protected AbstractViewController controller;
  protected LazyTreeItem rootItem;


  /**
   * Default Constructor
   */
  public FolderTreeNavigationDataLoader() {}


  protected void onLoadRootItemSuccess(IOperationResult operationResult) {
    SingleResult singleResult = (SingleResult) operationResult;
    OperationData operationData = (OperationData) singleResult.rootData();
    this.rootItem = new LazyTreeItem();
    this.rootItem.setTreeDataLoader(this);
    this.rootItem.setValue(operationData);
    this.rootItem.setExpanded(false);
  }


  protected void onLoadRootItemError(Throwable throwable) {
    // hmmm
    rootItem = new LazyTreeItem();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void loadChildren(TreeItem<OperationData> parent) {
    if (getChildrenFolderOperation != null) {
      JsonObject query = new JsonObject();
      String folderOid = (String) parent.getValue().getAttributes().get("fullId");
      query.addProperty("folderOid", folderOid);
      getChildrenFolderOperation.doOperation(query, result -> {
        if (((MultipleResult) result).hasElements()) {
          List<LazyTreeItem> lti = new ArrayList<>();
          for (OperationData data : ((MultipleResult) result).getData()) {
            LazyTreeItem lazyTreeItem = new LazyTreeItem();
            lazyTreeItem.setModel(data);
            lazyTreeItem.setTreeDataLoader(this);
            lti.add(lazyTreeItem);
          }
          parent.getChildren().setAll(lti);
        }
      });
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public List<? extends Object> loadContent(TreeItem<OperationData> parent) {
    return null;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public long countChildren(TreeItem<OperationData> lazyTreeItem) throws Exception {
    return 0;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void reloadItem(TreeItem<OperationData> parent) {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setAlwaysQueryChildrenOnExpand(boolean alwaysQueryChildrenOnExpand) {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setDisplayChildrenCount(boolean displayChildrenCount) {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setRootContext(AbstractViewController controller) {
    this.controller = controller;
  }


  /**
   * Getter of countFolderedOperation
   *
   * @return the countFolderedOperation
   */
  public IOperation getCountFolderedOperation() {
    return countFolderedOperation;
  }


  /**
   * Setter of countFolderedOperation
   *
   * @param countFolderedOperation the countFolderedOperation to set
   */
  public void setCountFolderedOperation(IOperation countFolderedOperation) {
    this.countFolderedOperation = countFolderedOperation;
  }


  /**
   * Getter of getRootFolderOperation
   *
   * @return the getRootFolderOperation
   */
  public IOperation getGetRootFolderOperation() {
    return getRootFolderOperation;
  }


  /**
   * Setter of getRootFolderOperation
   *
   * @param getRootFolderOperation the getRootFolderOperation to set
   */
  public void setGetRootFolderOperation(IOperation getRootFolderOperation) {
    this.getRootFolderOperation = getRootFolderOperation;
  }


  /**
   * Getter of getChildrenFolderOperation
   *
   * @return the getChildrenFolderOperation
   */
  public IOperation getGetChildrenFolderOperation() {
    return getChildrenFolderOperation;
  }


  /**
   * Setter of getChildrenFolderOperation
   *
   * @param getChildrenFolderOperation the getChildrenFolderOperation to set
   */
  public void setGetChildrenFolderOperation(IOperation getChildrenFolderOperation) {
    this.getChildrenFolderOperation = getChildrenFolderOperation;
  }


  /**
   * Getter of paginateFolderedOperation
   *
   * @return the paginateFolderedOperation
   */
  public IOperation getPaginateFolderedOperation() {
    return paginateFolderedOperation;
  }


  /**
   * Setter of paginateFolderedOperation
   *
   * @param paginateFolderedOperation the paginateFolderedOperation to set
   */
  public void setPaginateFolderedOperation(IOperation paginateFolderedOperation) {
    this.paginateFolderedOperation = paginateFolderedOperation;
  }


  /**
   * Getter of controller
   *
   * @return the controller
   */
  public AbstractViewController getController() {
    return controller;
  }


  /**
   * Setter of controller
   *
   * @param controller the controller to set
   */
  public void setController(AbstractViewController controller) {
    this.controller = controller;
  }


  /**
   * Setter of rootItem
   *
   * @param rootItem the rootItem to set
   */
  public void setRootItem(TreeItem<OperationData> rootItem) {
    this.rootItem = (LazyTreeItem) rootItem;
  }
}
