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

package io.github.jsoagger.jfxcore.engine.controller.main;



import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.AbstractTableStructure;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.table.TableResponsiveMatrix;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.tree.LazyTreeItem;
import io.github.jsoagger.jfxcore.engine.events.CoreEvent;
import io.github.jsoagger.jfxcore.engine.events.GenericEvent;

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 30 janv. 2018
 */
public class FullTreeViewController extends StandardViewController {

  private TreeTableView treeTableView;
  private TreeView treeView;
  private TableResponsiveMatrix tableResponsiveMatrix;

  /**
   * treeview do not keep the selected node when loosing focus, thats why wee keep track no it for
   * future update for example
   */
  private LazyTreeItem lastSelectedTreeItem;


  /**
   * Default Constructor
   */
  public FullTreeViewController() {
    registerListener(CoreEvent.TreePopulatedFromTemplateEvent);
    registerListener(CoreEvent.TreeFolderCreatedEvent);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  protected void process() {
    super.process();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public synchronized <T extends GenericEvent> void handle(T e) {
    super.handle(e);
    if (e.isA(CoreEvent.TreePopulatedFromTemplateEvent)) {
      LazyTreeItem item = (LazyTreeItem) treeView.getSelectionModel().getSelectedItem();
      item.reloadChildren();
    } else if (e.isA(CoreEvent.TreeFolderCreatedEvent)) {
      LazyTreeItem item = (LazyTreeItem) treeView.getSelectionModel().getSelectedItem();
      item.reloadChildren();
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void postProcess() {
    super.postProcess();
    AbstractTableStructure table = (AbstractTableStructure) processedElement();

    if (table.getTableStructure() instanceof TreeTableView) {
      treeTableView = (TreeTableView) table.getTableStructure();
      treeTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
      treeTableView.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> {
        LazyTreeItem item = (LazyTreeItem) newValue;
        selectedElementProperty().set(item.getValue());
      });
    }

    else if (table.getTableStructure() instanceof TreeView) {
      treeView = (TreeView) table.getTableStructure();
      treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
      treeView.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> {
        lastSelectedTreeItem = (LazyTreeItem) newValue;
        selectedElementProperty().set(lastSelectedTreeItem.getValue());
      });
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getNodeOnPosition(ViewLayoutPosition position) {
    AbstractTableStructure table = (AbstractTableStructure) processedElement();

    if (position == ViewLayoutPosition.TOP) {
      if ((table.getHeader() != null) || table.getToolbar().isPresent()) {

        if ((table.getHeader() != null) && table.getToolbar().isPresent()) {
          VBox header = new VBox();
          header.getChildren().addAll(table.getHeader().getDisplay(), table.getToolbar().get());
          return header;
        }

        else if (table.getToolbar().isPresent()) {
          return table.getToolbar().get();
        }

        return table.getHeader().getDisplay();
      }
    }

    if (position == ViewLayoutPosition.BOTTOM) {
      if (table.getPagination().isPresent()) {
        return table.getPagination().get();
      }
    }

    if (position == ViewLayoutPosition.CENTER) {
      return table.getTableStructure();
    }

    return super.getNodeOnPosition(position);
  }


  /**
   * Getter of tableResponsiveMatrix
   *
   * @return the tableResponsiveMatrix
   */
  public TableResponsiveMatrix getTableResponsiveMatrix() {
    return tableResponsiveMatrix;
  }


  /**
   * Setter of tableResponsiveMatrix
   *
   * @param tableResponsiveMatrix the tableResponsiveMatrix to set
   */
  public void setTableResponsiveMatrix(TableResponsiveMatrix tableResponsiveMatrix) {
    this.tableResponsiveMatrix = tableResponsiveMatrix;
  }


  /**
   * Getter of treeView
   *
   * @return the treeView
   */
  public TreeView getTreeView() {
    return treeView;
  }


  /**
   * Getter of lastSelectedTreeItem
   *
   * @return the lastSelectedTreeItem
   */
  public LazyTreeItem getLastSelectedTreeItem() {
    return lastSelectedTreeItem;
  }
}
