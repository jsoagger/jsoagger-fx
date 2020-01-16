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




import java.util.List;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.engine.components.pagination.tree.ITreePaginatedDataProvider;
import io.github.jsoagger.jfxcore.engine.components.tree.cell.EpTreeCell;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;

public class LazyTreeItem extends TreeItem<OperationData> {

  protected boolean displayChildrenCount = false;
  protected long childrenCount = -1;
  protected EpTreeCell<OperationData> epTreeCell;

  /** The depth, level, -1 means root level */
  protected int depth = -1;

  /** Data loader of this tree */
  protected ITreePaginatedDataProvider treeDataLoader;

  /** Indicates whether content have been loaded or not */
  protected final boolean hasLoadedContent = false;
  protected boolean hasLoadedChildren = false;
  protected boolean alwaysQueryChildrenOnExpand = false;

  /** Track the icon */
  protected ObjectProperty<Node> icon;


  /**
   * Constructor
   */
  public LazyTreeItem() {
    super();

    icon = new SimpleObjectProperty<>();

    // if true, count directly the number of children of this item
    // and update title after loading
    if (displayChildrenCount) {
      countChildren();
    }
    hasLoadedChildren = false;
    alwaysQueryChildrenOnExpand = true;
    expandedProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
      if (!hasLoadedChildren) {
        loadChildren();
        hasLoadedChildren = true;
      }
    });
  }


  /**
   * Set the model associated to the item.
   *
   * @param model
   */
  public void setModel(OperationData model) {
    setValue(model);
  }


  /**
   * Set the tree data loader
   *
   * @param treeDataLoader
   */
  public void setTreeDataLoader(ITreePaginatedDataProvider treeDataLoader) {
    this.treeDataLoader = treeDataLoader;
  }


  /**
   * Set the node icon
   *
   * @param icon
   */
  public void setIcon(Node icon) {
    this.icon.set(icon);
  }


  /**
   *
   */
  public ObjectProperty<Node> iconProperty() {
    return this.icon;
  }


  /**
   * Reload children items
   */
  public void reloadItem() {
    treeDataLoader.reloadItem(this);
  }


  /**
   * Count children without loading them
   */
  private void countChildren() {
    final LazyTreeItem item = this;
    final Task<Long> task = new Task<Long>() {

      @Override
      protected void succeeded() {
        super.succeeded();
        childrenCount = getValue();
      }


      @Override
      protected Long call() throws Exception {
        return count();
      }


      private long count() {
        try {
          return treeDataLoader.countChildren(item);
        } catch (final Exception e) {
          return -1L;
        }
      }
    };

    final Thread t = new Thread(task);
    t.start();
  }


  /**
   * Reload the children of this node
   */
  public void reloadChildren() {
    childrenCount = -1;
    hasLoadedChildren = false;
    loadChildren();
  }


  /**
   * Load chidlren of current node from database. Children are loaded from database just one time.
   */
  public void loadChildren() {
    if ((treeDataLoader != null) && !hasLoadedChildren) {
      final LazyTreeItem item = this;
      treeDataLoader.loadChildren(item);
      hasLoadedChildren = true;
    }
  }


  /**
   * Sometimes root item is fake one, to avoid api to try to call remote services with fake (will end
   * with exception), add first level children with this method.
   *
   * @param items
   */
  public <U extends LazyTreeItem> void setRootChildren(List<U> items) {
    for (final U item : items) {
      super.getChildren().add(item);
      ((LazyTreeItem) item).childrenCount = -1;
    }

    childrenCount = items.size();
    depth = -1;
  }


  @Override
  public boolean isLeaf() {
    if (hasLoadedChildren == false) {
      // loadChildren();
      return false;
    }
    return super.getChildren().isEmpty();
  }


  public boolean isRoot() {
    return getParent() == null;
  }


  /**
   * Set the depth
   */
  protected void setDepth(int depth) {
    this.depth = depth;
  }


  /**
   * Get the treeDataLoader
   *
   * @return the treeDataLoader
   */
  public ITreePaginatedDataProvider getTreeDataLoader() {
    return treeDataLoader;
  }


  public void hasLoadedChildren(boolean hasLoadedChildren) {
    this.hasLoadedChildren = hasLoadedChildren;
  }


  public void setChildrenCount(long count) {
    this.childrenCount = count;
  }


  public void alwaysQueryChildrenOnExpand(boolean value) {
    this.alwaysQueryChildrenOnExpand = value;
  }


  public void displayChildrenCount(boolean value) {
    this.displayChildrenCount = value;
  }


  /**
   * Get the displayChildrenCount
   *
   * @return the displayChildrenCount
   */
  public boolean isDisplayChildrenCount() {
    return displayChildrenCount;
  }


  /**
   * Get the alwaysQueryChildrenOnExpand
   *
   * @return the alwaysQueryChildrenOnExpand
   */
  public boolean isAlwaysQueryChildrenOnExpand() {
    return alwaysQueryChildrenOnExpand;
  }


  /**
   *
   */
  public void setProcessing() {
    if (this.epTreeCell != null) {
      this.epTreeCell.setProcessing();
    }
  }


  /**
   *
   */
  public void setEndProcessing() {
    if (this.epTreeCell != null) {
      this.epTreeCell.setEndProcessing();
    }
  }


  /**
   *
   */
  public void setCell(EpTreeCell epTreeCell) {
    this.epTreeCell = epTreeCell;
  }
}
