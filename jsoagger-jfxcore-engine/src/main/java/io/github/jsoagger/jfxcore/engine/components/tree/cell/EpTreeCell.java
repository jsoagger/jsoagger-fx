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



import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.presenter.MultiPresenterFactory;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.tree.LazyTreeItem;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.api.services.Services;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TreeCell;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 27 janv. 2018
 */
public class EpTreeCell<OperationData> extends TreeCell {

  private MultiPresenterFactory presenter;

  private AbstractViewController controller;
  private VLViewComponentXML configuration;

  private String presenterId;
  private SimpleObjectProperty<Node> egraphic = new SimpleObjectProperty<>();


  /**
   * @{inheritedDoc}
   */
  @Override
  protected void updateItem(Object item, boolean empty) {
    super.updateItem(item, empty);
    egraphic.set(null);
    if ((item != null) && !empty) {
      if (presenter == null) {
        presenter = (MultiPresenterFactory) Services.getBean(presenterId);
      }
      presenter.setForData((io.github.jsoagger.core.bridge.result.OperationData) item);
      presenter.buildFrom((IJSoaggerController) controller, configuration);
      graphicProperty().bind(egraphic);
      egraphic.set(presenter.getDisplay());
      // setGraphic(graphic);

      if (getTreeItem() instanceof LazyTreeItem) {
        ((LazyTreeItem) getTreeItem()).setCell(this);
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
   * Default Constructor
   */
  public EpTreeCell() {
    super();
  }


  /**
   * Getter of presenter
   *
   * @return the presenter
   */
  public MultiPresenterFactory getPresenter() {
    return presenter;
  }


  /**
   * Setter of presenter
   *
   * @param presenter the presenter to set
   */
  public void setPresenter(MultiPresenterFactory presenter) {
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
  public String getPresenterId() {
    return presenterId;
  }


  /**
   * Setter of presenteId
   *
   * @param presenteId the presenteId to set
   */
  public void setPresenterId(String presenteId) {
    this.presenterId = presenteId;
  }


  public void setProcessing() {
    Platform.runLater(() -> {
      ProgressIndicator pinMain = new ProgressIndicator();
      pinMain.setPrefSize(50, 50);
      egraphic.set(pinMain);
      requestFocus();
    });

  }


  public void setEndProcessing() {
    egraphic.set(null);
  }
}
