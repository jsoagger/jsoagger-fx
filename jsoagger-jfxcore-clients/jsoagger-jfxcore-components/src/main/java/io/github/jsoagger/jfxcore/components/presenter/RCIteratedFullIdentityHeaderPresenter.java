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
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.presenter.ModelHeaderIdentityPresenter;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.AbstractModelPresenter;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.components.rc.RCUtils;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Displays the identity of an iteration with master fetched with it.
 * <p>
 * The iteration is the main entity.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 4 févr. 2018
 */
public class RCIteratedFullIdentityHeaderPresenter extends AbstractModelPresenter implements ModelHeaderIdentityPresenter {

  HBox identity = new HBox();
  boolean wasProcessed = false;

  /**
   * Default Constructor
   */
  public RCIteratedFullIdentityHeaderPresenter() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node provideIdentityOf(IJSoaggerController controller, VLViewComponentXML component) {
    if(wasProcessed) {
      return identity;
    }

    wasProcessed = true;
    identity.setId("ep-rc-header-identity");
    identity.setStyle("-fx-spacing:8;-fx-alignment:CENTER");
    identity.setAlignment(Pos.CENTER);

    // master name
    String id = identityOf(controller, component);
    Label label = new Label(id);
    label.getStyleClass().addAll("ep-rc-master-identity-label-for-header");
    identity.getChildren().addAll(label);

    // status
    String status = RCUtils.getModelAttribute((AbstractViewController) controller, "attributes.lifecycleInfo.currentState");
    Label pstatus = new Label(controller.getLocalised(status));
    pstatus.getStyleClass().add("ep-model-current-status-for-header");
    identity.getChildren().addAll(pstatus);

    // version
    Object version = RCUtils.getModelAttribute((AbstractViewController) controller, "attributes.versionInfo.versionId");
    Double iteration = Double.valueOf(RCUtils.getModelAttribute((AbstractViewController) controller, "attributes.iterationInfo.iterationNumber"));
    if (version != null && StringUtils.isNotBlank(version.toString())) {
      String ver = version.toString().toUpperCase() + "." + iteration.intValue();
      Label pver = new Label(ver);
      pver.getStyleClass().add("ep-model-current-version-for-header");
      identity.getChildren().addAll(pver);
    }

    return identity;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String identityOf(IJSoaggerController controller, VLViewComponentXML component) {
    return RCUtils.getModelMasterAttribute(((StandardViewController)controller).getOpData(), "name");
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
  public Node provideIdentityOf(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    HBox identity = new HBox();
    identity.setStyle("-fx-spacing:8");
    identity.setAlignment(Pos.CENTER);

    // master name
    String id = identityOf(controller, configuration, forModel);
    Label label = new Label(id);
    label.getStyleClass().addAll("ep-rc-master-identity-label-for-header");
    identity.getChildren().addAll(label);

    // status
    String status = RCUtils.getModelAttribute((AbstractViewController) controller, "attributes.lifecycleInfo.currentState");
    Label pstatus = new Label(status);
    pstatus.getStyleClass().add("ep-model-current-status-for-header");
    identity.getChildren().addAll(pstatus);

    // version
    Object version = RCUtils.getModelAttribute((AbstractViewController) controller, "attributes.versionInfo.versionId");
    Object iteration = RCUtils.getModelAttribute((AbstractViewController) controller, "attributes.iterationInfo.iterationNumber");
    if (version != null && StringUtils.isNotBlank(version.toString())) {
      String ver = version.toString().toUpperCase() + "." + iteration;
      Label pver = new Label(ver);
      pver.getStyleClass().add("ep-model-current-version-for-header");
      identity.getChildren().addAll(pver);
    }

    return identity;
  }
}
