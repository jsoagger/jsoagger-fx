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

package io.github.jsoagger.jfxcore.demoapp.comps;



import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.presenter.ModelIdentityPresenter;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.AbstractModelPresenter;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class DemoFlowItemIdentityPresenter extends AbstractModelPresenter implements ModelIdentityPresenter {

  /**
   * Default Constructor
   */
  public DemoFlowItemIdentityPresenter() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node provideIdentityOf(IJSoaggerController controller, VLViewComponentXML component) {
    final String id = identityOf(controller, component);
    final Label label = new Label(id);
    label.getStyleClass().addAll("ep-rc-master-identity-label");

    final VBox rows = new VBox();
    rows.setAlignment(Pos.CENTER_LEFT);
    rows.setSpacing(8);

    final HBox row1 = new HBox();
    row1.setAlignment(Pos.CENTER_LEFT);
    rows.getChildren().add(row1);

    // final Node n = getFakeStatus((OperationData) controller.getModel());
    // row1.getChildren().addAll(n);

    final HBox identity = new HBox();
    identity.setAlignment(Pos.CENTER_LEFT);
    identity.setSpacing(16);
    identity.getChildren().addAll(label);
    rows.getChildren().add(label);

    return rows;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String identityOf(IJSoaggerController controller, VLViewComponentXML component) {
    String model = getModelAttribute(controller, "attributes.vin");
    String company = getModelAttribute(controller, "attributes.model");
    return model + ", " + company;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String identityOf(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    String model = getModelAttribute((OperationData) forModel, "attributes.lastname");
    String company = getModelAttribute((OperationData) forModel, "attributes.model");
    return model + ", " + company;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node provideIdentityOf(IJSoaggerController controller, VLViewComponentXML component, Object forModel) {
    final String id = identityOf(controller, component, forModel);
    final Label label = new Label(id);
    label.getStyleClass().addAll("ep-rc-master-identity-label");

    final VBox rows = new VBox();
    rows.setAlignment(Pos.TOP_LEFT);
    rows.setSpacing(8);

    final HBox row1 = new HBox();
    row1.setAlignment(Pos.TOP_LEFT);
    rows.getChildren().add(row1);

    final Node n = getFakeStatus((OperationData) forModel);
    row1.getChildren().addAll(n);

    final HBox identity = new HBox();
    identity.setAlignment(Pos.TOP_LEFT);
    identity.setSpacing(16);
    identity.getChildren().addAll(label);
    rows.getChildren().add(label);

    return rows;
  }


  private Node getFakeStatus(OperationData model) {
    String status = (String) model.getAttributes().get("status");
    final Label label = new Label(status == null ? "" : status);
    label.getStyleClass().addAll("ep-rc-master-identity-caption");
    return label;
  }

}
