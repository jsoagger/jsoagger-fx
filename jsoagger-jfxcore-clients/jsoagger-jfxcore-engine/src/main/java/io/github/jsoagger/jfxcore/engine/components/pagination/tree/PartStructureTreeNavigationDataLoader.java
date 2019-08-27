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
public class PartStructureTreeNavigationDataLoader implements ITreePaginatedDataProvider {

  protected AbstractViewController controller;
  protected LazyTreeItem rootItem;

  // needs GetPartUsagesOperation
  protected IOperation loadPartUsesOperation;


  /**
   * Default Constructor
   */
  public PartStructureTreeNavigationDataLoader() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public TreeItem<OperationData> getRootItem() {
    // the root item is the current model of the current controller
    SingleResult model = (SingleResult) controller.getModel();
    OperationData data = model.getData();
    this.rootItem = new LazyTreeItem();
    this.rootItem.setValue(data);
    this.rootItem.setTreeDataLoader(this);
    this.rootItem.setExpanded(false);
    return rootItem;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setRootContext(AbstractViewController controller) {
    this.controller = controller;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void loadChildren(TreeItem<OperationData> parent) {
    if (loadPartUsesOperation != null) {
      OperationData data = parent.getValue();
      // loads the usage, without the link
      // no filter, and no restriction --> the roleB is the latest version
      // of the used part
      JsonObject query = new JsonObject();
      query.addProperty("roleAFullId", data.getAttributes().get("fullId").toString());

      // not supported for now
      query.addProperty("effectivity", "");
      query.addProperty("querySpec", "");

      if (parent instanceof LazyTreeItem) {
        // ((LazyTreeItem) parent).setProcessing();
        // parent.getChildren().clear();
      }

      loadPartUsesOperation.doOperation(query, result -> {
        onLoadSuccess((LazyTreeItem) parent, result);
      });
    }
  }


  public void onLoadSuccess(LazyTreeItem ti, IOperationResult or) {
    List<LazyTreeItem> lti = new ArrayList<>();
    if (((MultipleResult) or).hasElements()) {
      for (OperationData d : ((MultipleResult) or).getData()) {
        LazyTreeItem lazyTreeItem = new LazyTreeItem();
        lazyTreeItem.setModel(d);
        lazyTreeItem.setTreeDataLoader(this);
        lti.add(lazyTreeItem);
      }
    }
    ti.getChildren().setAll(lti);
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
}
