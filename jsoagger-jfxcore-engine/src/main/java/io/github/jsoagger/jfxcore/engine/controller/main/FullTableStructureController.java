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
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.AbstractTableStructure;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.table.TableResponsiveMatrix;
import io.github.jsoagger.jfxcore.engine.events.CoreEvent;
import io.github.jsoagger.jfxcore.engine.events.GenericEvent;
import javafx.scene.Node;

/**
 * @author Ramilafananana VONJISOA
 */
public abstract class FullTableStructureController extends StandardViewController {

  protected TableResponsiveMatrix tableResponsiveMatrix;
  protected IBuildable headerPresenter; // TODO remove,
  // declared in XML


  /**
   * Constructor
   */
  public FullTableStructureController() {
    registerListener(CoreEvent.ScrollReachTopScreenEvent);
  }

  @Override
  public synchronized <T extends GenericEvent> void handle(T e) {
    super.handle(e);
    if (e.isA(CoreEvent.ScrollReachTopScreenEvent)) {
      System.out.println(">>>>>>>>>>><<<  : " + "ScrollReachTopScreenEvent");
    }
  }


  @Override
  public void beforePop() {
    super.beforePop();
    if (needRefreshBeforePop) {
      needRefreshBeforePop = false;
      getTableStructure().refreshDatas();
    }
  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected void process() {
    super.process();
    if (layoutManager != null && getTableStructure() != null) {
      if (getTableStructure().getDefaultActions() != null) {
        layoutManager.setDefaultActions(getTableStructure().getDefaultActions());
      } else {
        layoutManager.setDefaultActions(null);
      }
    }
  }


  public void forceLoadFirstPage() {
    AbstractTableStructure table = (AbstractTableStructure) processedElement();
    table.forceLoadFirstPage();
  }


  @Override
  public void refreshDatas() {
    AbstractTableStructure table = (AbstractTableStructure) processedElement();
    table.refreshDatas();
  }


  /**
   * Header/title can be change dynamicaly when displayed alongside with list.
   */
  public void setTitle(String title) {
    AbstractTableStructure table = (AbstractTableStructure) processedElement();
    if (table.getHeader() != null) {
      // table.getHeader().setTitle(title);
    }
  }


  /**
   * Header/title can be change dynamicaly when displayed alongside with list.
   */
  public void setHeader(Node header) {
    AbstractTableStructure table = (AbstractTableStructure) processedElement();
    if (table.getHeader() != null) {
      // table.getHeader().setHeader(header);
    }
  }


  public void selectedElementChangedTo(OperationData newValue) {
    selectedElementProperty().set(newValue);
  }


  public TableResponsiveMatrix getTableResponsiveMatrix() {
    return tableResponsiveMatrix;
  }


  public void setTableResponsiveMatrix(TableResponsiveMatrix tableResponsiveMatrix) {
    this.tableResponsiveMatrix = tableResponsiveMatrix;
  }


  public IBuildable getHeaderPresenter() {
    return headerPresenter;
  }


  public void setHeaderPresenter(IBuildable headerPresenter) {
    this.headerPresenter = headerPresenter;
  }


  public AbstractTableStructure getTableStructure() {
    return (AbstractTableStructure) processedElement();
  }

  /**
   * Compares by attributes fullID
   */
  public boolean containsModel(OperationData roleB) {
    AbstractTableStructure table = (AbstractTableStructure) processedElement();
    for (OperationData data : table.getItems()) {
      if (data.fullIdEquals(roleB)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Compares by master fullID
   */
  public boolean containsModelByMasterFullId(OperationData roleB) {
    AbstractTableStructure table = (AbstractTableStructure) processedElement();
    for (OperationData data : table.getItems()) {
      if (data.masterFullIdEquals(roleB)) {
        return true;
      }
    }

    return false;
  }

  public void addItem(OperationData data) {
    getTableStructure().getItems().add(data);
  }


  public void removeItem(OperationData data) {
    getTableStructure().getItems().remove(data);
  }
}
