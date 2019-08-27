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

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.engine.components.list.utils.AbstractListView;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.AbstractTableStructure;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.table.TableContent;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.table.TableResponsiveMatrix;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.FullFlowContentLayoutManager;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * Controller to use when the listview or flow content is displayed inside
 * {@link FullFlowContentLayoutManager}
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 30 janv. 2018
 */
public class FullListViewController extends FullTableStructureController {

  private SimpleObjectProperty<OperationData> selectedModel = new SimpleObjectProperty<>();
  private AbstractListView listView;


  /**
   * Default Constructor
   */
  public FullListViewController() {}


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
  public void postProcess() {
    super.postProcess();
    AbstractTableStructure table = (AbstractTableStructure) processedElement();
    if (table instanceof TableContent) {
      ((TableContent) table).setResponsiveColumnsMatrix(tableResponsiveMatrix);
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getNodeOnPosition(ViewLayoutPosition position) {
    AbstractTableStructure table = (AbstractTableStructure) processedElement();

    if (position == ViewLayoutPosition.TOP) {
      if ((table != null) && ((table.getHeader() != null) || table.getToolbar().isPresent())) {

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
      if ((table != null) && (table.getPagination() != null)) {
        return table.getPagination().get();
      }
    }

    if (position == ViewLayoutPosition.CENTER) {
      return table.getTableStructure();
    }

    return super.getNodeOnPosition(position);
  }


  /**
   * Getter of selectedModel
   *
   * @return the selectedModel
   */
  public SimpleObjectProperty<OperationData> getSelectedModel() {
    return selectedModel;
  }


  /**
   * Setter of selectedModel
   *
   * @param selectedModel the selectedModel to set
   */
  public void setSelectedModel(OperationData selectedModel) {
    this.selectedModel.set(selectedModel);
  }


  public void selectFirstItem() {
    if (listView != null) {
      listView.selectFirstItem();
    }
  }


  /**
   *
   */
  public void setListView(AbstractListView listView) {
    this.listView = listView;
  }


  /**
   * Getter of tableResponsiveMatrix
   *
   * @return the tableResponsiveMatrix
   */
  @Override
  public TableResponsiveMatrix getTableResponsiveMatrix() {
    return tableResponsiveMatrix;
  }


  /**
   * Setter of tableResponsiveMatrix
   *
   * @param tableResponsiveMatrix the tableResponsiveMatrix to set
   */
  @Override
  public void setTableResponsiveMatrix(TableResponsiveMatrix tableResponsiveMatrix) {
    this.tableResponsiveMatrix = tableResponsiveMatrix;
  }


  /**
   * Getter of listView
   *
   * @return the listView
   */
  public AbstractListView getListView() {
    return listView;
  }


  /**
   * Setter of selectedModel
   *
   * @param selectedModel the selectedModel to set
   */
  public void setSelectedModel(SimpleObjectProperty<OperationData> selectedModel) {
    this.selectedModel = selectedModel;
  }
}
