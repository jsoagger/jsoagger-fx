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

package io.github.jsoagger.jfxcore.engine.components.table.cell;



import io.github.jsoagger.jfxcore.api.ICellPresenter;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.client.components.ComponentToButtonBaseHelper;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseEvent;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 27 janv. 2018
 */
public class EpTableCell<OperationData> extends TableCell {

  private ICellPresenter presenter;

  private AbstractViewController controller;
  private VLViewComponentXML configuration;
  private String presenteId;
  private VLViewComponentXML columnClickHandler;
  private Object item;
  private MouseClickService mouseClickService;


  /**
   * Default Constructor
   */
  public EpTableCell() {
    super();
  }



  /**
   * @{inheritedDoc}
   */
  @Override
  protected void updateItem(Object item, boolean empty) {
    super.updateItem(item, empty);
    setGraphic(null);
    this.item = item;
    mouseClickService = null;

    if (item != null && !empty) {

      IndexedCell cell = this;
      if (presenter == null) {
        presenter = (ICellPresenter) Services.getBean(presenteId);
        presenter.setCell(cell);
      }

      if (!getStyleClass().contains("vl-table-cell")) {
        // getStyleClass().add("vl-table-cell");
        setAlignment(Pos.CENTER_LEFT);
      }

      if (configuration.getPropertyValue("tableCellStyleClass") != null && configuration.getPropertyValue("tableCellStyleClass").contains("align-right")) {
        setAlignment(Pos.CENTER_RIGHT);
      }
      else if (configuration.getPropertyValue("tableCellStyleClass") != null && configuration.getPropertyValue("tableCellStyleClass").contains("align-center")) {
        setAlignment(Pos.CENTER);
      }
      else {
        setAlignment(Pos.CENTER_LEFT);
      }

      NodeHelper.styleClassAddAll(cell, configuration, "tableCellStyleClass");
      Node graphic = presenter.present(controller, configuration, item);
      setGraphic(graphic);

      // mouse click handle
      columnClickHandler = configuration.getComponentById("ColumnClickHandler").orElse(null);
      if (columnClickHandler != null) {
        getStyleClass().add("hand-mouse-hover");
        mouseClickService = new MouseClickService();
        addEventHandler(MouseEvent.MOUSE_CLICKED, ev -> {
          try {
            if (ev.getClickCount() == 2) {
              if (mouseClickService.isRunning()) {
                mouseClickService.restart();
              } else {
                mouseClickService.setEvent(ev);
                mouseClickService.start();
              }
            }
          } catch (Exception e) {
          }
        });
      }
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void updateSelected(boolean selected) {
    super.updateSelected(selected);
  }

  /**
   * Getter of presenter
   *
   * @return the presenter
   */
  public ICellPresenter getPresenter() {
    return presenter;
  }


  /**
   * Setter of presenter
   *
   * @param presenter the presenter to set
   */
  public void setPresenter(ICellPresenter presenter) {
    this.presenter = presenter;
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
   * Getter of configuration
   *
   * @return the configuration
   */
  public VLViewComponentXML getConfiguration() {
    return configuration;
  }


  /**
   * Setter of configuration
   *
   * @param configuration the configuration to set
   */
  public void setConfiguration(VLViewComponentXML configuration) {
    this.configuration = configuration;
  }


  /**
   * Getter of presenteId
   *
   * @return the presenteId
   */
  public String getPresenteId() {
    return presenteId;
  }


  /**
   * Setter of presenteId
   *
   * @param presenteId the presenteId to set
   */
  public void setPresenteId(String presenteId) {
    this.presenteId = presenteId;
  }

  /**
   * Handling mouse click on the cell
   *
   * @author Ramilafananana  VONJISOA
   *
   */
  private class MouseClickService extends Service<Void> {

    private Event ev;

    @Override
    protected Task<Void> createTask() {
      ComponentToButtonBaseHelper.setButtonActions(controller, columnClickHandler, null, ev, (io.github.jsoagger.core.bridge.result.OperationData) item);
      return null;
    }

    public void setEvent(Event ev) {
      this.ev = ev;
    }

  }
}
