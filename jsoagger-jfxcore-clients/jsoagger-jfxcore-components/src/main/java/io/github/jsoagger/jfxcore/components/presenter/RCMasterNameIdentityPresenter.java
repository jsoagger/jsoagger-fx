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
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.AbstractModelPresenter;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.components.rc.RCUtils;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Displays the name of the master of the given entity.
 * <p>
 * The iteration is the main entity.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 4 févr. 2018
 */
public class RCMasterNameIdentityPresenter extends AbstractModelPresenter implements ModelIdentityPresenter {

  /**
   * Default Constructor
   */
  public RCMasterNameIdentityPresenter() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node provideIdentityOf(IJSoaggerController controller, VLViewComponentXML component) {
    String id = identityOf(controller, component);
    Label label = new Label(id);
    label.getStyleClass().addAll("ep-rc-master-identity-label");
    return label;
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
  public Node provideIdentityOf(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    String id = identityOf(controller, configuration, forModel);
    Label label = new Label(id);
    label.getStyleClass().addAll("ep-rc-master-identity-label");
    return label;
  }
}
