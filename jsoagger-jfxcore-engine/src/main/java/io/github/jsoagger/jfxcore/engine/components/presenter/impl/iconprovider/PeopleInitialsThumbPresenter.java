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

package io.github.jsoagger.jfxcore.engine.components.presenter.impl.iconprovider;



import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.presenter.ModelIconPresenter;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.CellPresenterImpl;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * Icon associadte to the type of the businness data
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 29 janv. 2018
 */
public class PeopleInitialsThumbPresenter extends CellPresenterImpl implements ModelIconPresenter {

  private final StackPane content = new StackPane();


  /**
   *
   */
  public PeopleInitialsThumbPresenter() {

  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public Node provideIcon(IJSoaggerController controller, VLViewComponentXML configuration) {
    return provideIcon(controller, configuration, null);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node provideIcon(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {

    OperationData model = (OperationData) forModel;
    if(model == null) {
      AbstractViewController c = (AbstractViewController) controller;
      model = c.getOpData();
    }

    String firstName = (String) model.getAttributes().get("firstName");

    Label initial = new Label(firstName.substring(0, 1).toUpperCase());
    Platform.runLater(() -> {
      content.getChildren().add(initial);
    });

    initial.getStyleClass().add("ep-people-thumb-initial-label");
    content.getStyleClass().add("ep-people-small-initials-container");
    return content;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    return provideIcon(controller, configuration, forModel);
  }
}
