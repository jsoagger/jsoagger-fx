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

package io.github.jsoagger.jfxcore.components.presenter;



import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.presenter.ModelIdentityPresenter;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.AbstractModelPresenter;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.components.rc.RCUtils;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Displays the identity of an iteration with master fetched with it.
 * <p>
 * The iteration is the main entity.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 4 févr. 2018
 */
public class RCIteratedFullIdentityPresenter extends AbstractModelPresenter implements ModelIdentityPresenter {

  // horizontal
  // vertical
  private String orientation;
  private Pane rows = new VBox();

  /**
   * Default Constructor
   */
  public RCIteratedFullIdentityPresenter() {

  }



  /**
   * @{inheritedDoc}
   */
  @Override
  public Node provideIdentityOf(IJSoaggerController controller, VLViewComponentXML component) {

    if("horizontal".equalsIgnoreCase(orientation)) {
      rows = new HBox();
      ((HBox)rows).setAlignment(Pos.CENTER_LEFT);
      ((HBox)rows).setSpacing(8);
    }
    else {
      ((VBox)rows).setAlignment(Pos.CENTER_LEFT);
      ((VBox)rows).setSpacing(8);
    }

    String id = identityOf(controller, component);
    Label label = new Label(id);
    label.getStyleClass().addAll("ep-rc-master-identity-label");

    HBox row1 = new HBox();
    row1.setAlignment(Pos.CENTER);
    rows.getChildren().add(row1);

    ModelStatusPresenter statusp = (ModelStatusPresenter) Services.getBean("ModelStatusPresenter");
    Node n = statusp.present(controller, component);

    ModelRevisionPresenter revp = (ModelRevisionPresenter) Services.getBean("ModelRevisionPresenter");
    Node r = revp.present(controller, component);
    row1.getChildren().addAll(n, r);

    HBox identity = new HBox();
    identity.setAlignment(Pos.CENTER);
    identity.setSpacing(16);
    identity.getChildren().addAll(label);
    rows.getChildren().add(0, label);

    return rows;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String identityOf(IJSoaggerController controller, VLViewComponentXML component) {
    return RCUtils.getModelMasterAttribute((AbstractViewController) controller, "name");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String identityOf(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    return RCUtils.getModelMasterAttribute((OperationData) forModel, "name");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node provideIdentityOf(IJSoaggerController controller, VLViewComponentXML component, Object forModel) {
    if("horizontal".equalsIgnoreCase(orientation)) {
      rows = new HBox();
      rows.getStyleClass().add("ep-horizontal-rc-identity");
      ((HBox)rows).setAlignment(Pos.CENTER_LEFT);
      ((HBox)rows).setSpacing(8);
    }
    else {
      ((VBox)rows).setAlignment(Pos.CENTER_LEFT);
      ((VBox)rows).setSpacing(8);
    }

    String id = identityOf(controller, component, forModel);
    Label label = new Label(id);
    label.getStyleClass().addAll("ep-rc-master-identity-label");

    HBox row1 = new HBox();
    row1.setAlignment(Pos.CENTER_LEFT);
    rows.getChildren().add(row1);

    ModelStatusPresenter statusp = (ModelStatusPresenter) Services.getBean("ModelStatusPresenter");
    Node n = statusp.present(controller, component, forModel);

    ModelRevisionPresenter revp = (ModelRevisionPresenter) Services.getBean("ModelRevisionPresenter");
    Node r = revp.present(controller, component, forModel);
    row1.getChildren().addAll(n, r);

    HBox identity = new HBox();
    identity.setAlignment(Pos.CENTER_LEFT);
    identity.setSpacing(16);
    identity.getChildren().addAll(label);
    rows.getChildren().add(0,label);

    return rows;
  }


  /**
   * @return the orientation
   */
  public String getOrientation() {
    return orientation;
  }


  /**
   * @param orientation the orientation to set
   */
  public void setOrientation(String orientation) {
    this.orientation = orientation;
  }
}
