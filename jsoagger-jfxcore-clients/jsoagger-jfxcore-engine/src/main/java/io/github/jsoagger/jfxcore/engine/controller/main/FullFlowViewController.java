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


import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.ITableStructureHeaderPresenter;
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.AbstractTableStructure;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.SingleTableStructure;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.flow.FlowContent;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.table.TableResponsiveMatrix;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.FullFlowContentLayoutManager;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

/**
 * Controller to use when flow content is displayed inside {@link FullFlowContentLayoutManager}
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 30 janv. 2018
 */
public class FullFlowViewController extends FullTableStructureController {

  private IOperationResult rootModel;


  /**
   * Default Constructor
   */
  public FullFlowViewController() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  protected void process() {
    super.process();

    // listen on model change
    ChangeListener<Object> cl2 = this::onModelChange;
    modelProperty().addListener(cl2);
  }


  private void onModelChange(ObservableValue<?> observable, Object oldValue, Object newValue) {
    if (rootModel == null) {
      rootModel = (IOperationResult) oldValue;
    }

    forceLoadFirstPage();
    modelChanged();
  }


  private void modelChanged() {
    if (headerPresenter != null) {
      // Node node = headerPresenter.present(this);
      // setHeader(node);
    }
  }


  public void navigate(OperationData operationData) {
    SingleResult newmodel = new SingleResult();
    newmodel.setData(operationData);
    setModel(newmodel);
  }


  /**
   * Needed when displayed along side with table to display datas related to this table.
   */
  public OperationData getFirstElement() {
    SingleTableStructure table = (SingleTableStructure) processedElement();
    if (table.elementsCountProperty().get() > 0) {
      return table.getFirstItem();
    }

    return null;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void postProcess() {
    super.postProcess();
    AbstractTableStructure table = (AbstractTableStructure) processedElement();
    if (table == null) {
      // processedElement(getTableStructure());
    }

    if (table instanceof FlowContent) {
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getNodeOnPosition(ViewLayoutPosition position) {
    AbstractTableStructure table = (AbstractTableStructure) processedElement();

    if (position == ViewLayoutPosition.TOP) {
      if (table != null && table.getHeader() != null) {
        return table.getHeader().getDisplay();
      }
    }

    if (position == ViewLayoutPosition.BOTTOM) {
      if (table != null && table.getPagination().isPresent()) {
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


  @Override
  public ITableStructureHeaderPresenter getHeaderPresenter() {
    return (ITableStructureHeaderPresenter) headerPresenter;
  }


  @Override
  public void setHeaderPresenter(IBuildable headerPresenter) {
    this.headerPresenter = headerPresenter;
  }


  public void selectFirstItem() {

  }
}
