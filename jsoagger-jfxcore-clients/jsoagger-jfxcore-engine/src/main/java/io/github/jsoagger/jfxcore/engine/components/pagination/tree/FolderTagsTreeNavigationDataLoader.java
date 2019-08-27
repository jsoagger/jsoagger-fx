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



import java.util.List;

import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.services.Services;
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
public class FolderTagsTreeNavigationDataLoader implements ITreePaginatedDataProvider {

  TreeItem<OperationData> rootItem;


  /**
   * Default Constructor
   */
  public FolderTagsTreeNavigationDataLoader() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public TreeItem<OperationData> getRootItem() {
    rootItem = new TreeItem<>();
    OperationData data = new OperationData();
    data.getAttributes().put("name", "Tags");
    data.getBusinessType().put("internalType", "io.github.jsoagger.foldered.Tag/Red");
    rootItem.setValue(data);

    IOperation getAlltags = (IOperation) Services.getBean("GetAllFolderTagsOperation");
    JsonObject query = new JsonObject();
    query.addProperty("containerOid", "containerOid");
    getAlltags.doOperation(query, this::onTagsLoadedSuccess);

    return rootItem;
  }


  private void onTagsLoadedSuccess(IOperationResult operationResult) {
    MultipleResult multipleResult = (MultipleResult) operationResult;
    for (OperationData data : multipleResult.getData()) {
      LazyTreeItem lazyTreeItem = new LazyTreeItem();
      lazyTreeItem.setValue(data);
      lazyTreeItem.setChildrenCount(0);
      lazyTreeItem.setExpanded(true);
      rootItem.getChildren().add(lazyTreeItem);
    }
    rootItem.setExpanded(true);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setRootContext(AbstractViewController controller) {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void loadChildren(TreeItem<OperationData> parent) {}


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
  public long countChildren(TreeItem<OperationData> parent) throws Exception {
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

}
