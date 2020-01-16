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

package io.github.jsoagger.jfxcore.engine.components.tree.cell;


import io.github.jsoagger.jfxcore.api.ICellPresenter;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.api.services.Services;

import javafx.scene.Node;
import javafx.scene.control.TreeTableCell;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 27 janv. 2018
 */
public class EpTreeTableCell<OperationData> extends TreeTableCell {

  private ICellPresenter presenter;

  private AbstractViewController controller;
  private VLViewComponentXML configuration;

  private String presenteId;
  private Node graphic;


  /**
   * @{inheritedDoc}
   */
  @Override
  protected void updateItem(Object item, boolean empty) {
    super.updateItem(item, empty);
    setGraphic(null);

    if ((item != null) && !empty) {
      if (presenter == null) {
        presenter = (ICellPresenter) Services.getBean(presenteId);
        presenter.setCell(this);
        graphic = presenter.present((IJSoaggerController) controller, configuration, item);
      }

      setGraphic(graphic);
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
   * Default Constructor
   */
  public EpTreeTableCell() {
    super();
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
}
