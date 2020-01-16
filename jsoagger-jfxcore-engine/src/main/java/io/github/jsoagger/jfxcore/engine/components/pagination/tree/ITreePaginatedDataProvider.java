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

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.scene.control.TreeItem;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 20 févr. 2018
 */
public interface ITreePaginatedDataProvider {

  public void setRootContext(AbstractViewController controller);


  public abstract TreeItem<OperationData> getRootItem();


  public abstract void loadChildren(TreeItem<OperationData> parent);


  public abstract List<? extends Object> loadContent(TreeItem<OperationData> parent);


  public abstract long countChildren(TreeItem<OperationData> lazyTreeItem) throws Exception;


  public abstract void reloadItem(TreeItem<OperationData> parent);


  public abstract void setAlwaysQueryChildrenOnExpand(boolean alwaysQueryChildrenOnExpand);


  public abstract void setDisplayChildrenCount(boolean displayChildrenCount);

}
