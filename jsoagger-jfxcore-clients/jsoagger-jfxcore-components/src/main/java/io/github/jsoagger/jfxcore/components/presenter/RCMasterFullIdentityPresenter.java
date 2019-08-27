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
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.AbstractModelPresenter;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.components.rc.RCUtils;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

/**
 * Displays the master name and (Only if an iteration is attached) the version and status of that
 * iteration.
 * <p>
 * The master is the main entity.
 *
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 4 févr. 2018
 */
public class RCMasterFullIdentityPresenter extends AbstractModelPresenter implements ModelIdentityPresenter {

  /**
   * Default Constructor
   */
  public RCMasterFullIdentityPresenter() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node provideIdentityOf(IJSoaggerController controller, VLViewComponentXML component) {
    String id = identityOf(controller, component);
    Label label = new Label(id);
    label.getStyleClass().addAll("ep-rc-master-identity-label");

    ModelStatusPresenter statusp = (ModelStatusPresenter) Services.getBean("ModelStatusPresenter");
    Node n = statusp.present(controller, component);

    ModelRevisionPresenter revp = (ModelRevisionPresenter) Services.getBean("ModelRevisionPresenter");
    Node r = revp.present(controller, component);

    HBox identity = new HBox();
    identity.setAlignment(Pos.CENTER);
    identity.setSpacing(16);
    identity.getChildren().addAll(label, r, n);

    return identity;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String identityOf(IJSoaggerController controller, VLViewComponentXML component) {
    return RCUtils.getModelAttribute((AbstractViewController) controller, "name");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String identityOf(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    return RCUtils.getModelAttribute((OperationData) forModel, "name");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node provideIdentityOf(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    String id = identityOf(controller, configuration, forModel);
    Label label = new Label(id);
    label.getStyleClass().addAll("ep-rc-master-identity-label");

    ModelStatusPresenter statusp = (ModelStatusPresenter) Services.getBean("ModelStatusPresenter");
    Node n = statusp.present(controller, configuration, forModel);

    ModelRevisionPresenter revp = (ModelRevisionPresenter) Services.getBean("ModelRevisionPresenter");
    Node r = revp.present(controller, configuration, forModel);

    FlowPane identity = new FlowPane();
    identity.setAlignment(Pos.CENTER_LEFT);
    identity.setHgap(8);
    identity.setVgap(8);
    identity.getChildren().addAll(label, r, n);
    return identity;
  }
}
