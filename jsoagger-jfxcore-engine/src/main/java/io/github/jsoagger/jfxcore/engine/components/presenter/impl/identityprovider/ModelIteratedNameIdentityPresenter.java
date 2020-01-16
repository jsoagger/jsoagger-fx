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

package io.github.jsoagger.jfxcore.engine.components.presenter.impl.identityprovider;



import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.presenter.ModelIdentityPresenter;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.AbstractModelPresenter;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 30 janv. 2018
 */
public class ModelIteratedNameIdentityPresenter extends AbstractModelPresenter implements ModelIdentityPresenter {

  /**
   * Default Constructor
   */
  public ModelIteratedNameIdentityPresenter() {
    super();
  }


  /**
   *
   * @{inheritedDoc}
   */
  @Override
  public Node provideIdentityOf(IJSoaggerController controller, VLViewComponentXML configuration) {
    String id = identityOf(controller, configuration);
    Label label = new Label(id);

    setStyleClass(label, configuration);
    return label;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String identityOf(IJSoaggerController controller, VLViewComponentXML component) {
    return getModelAttribute(controller, "masterAttributes.name");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String identityOf(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    return getModelAttribute((OperationData) forModel, "masterAttributes.name");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node provideIdentityOf(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    String id = identityOf(controller, configuration, forModel);
    Label label = new Label(id);
    setStyleClass(label, configuration);
    return label;
  }


  private void setStyleClass(Label label, VLViewComponentXML configuration) {
    NodeHelper.setStyleClass(label, configuration, "mainLabelStyleClass", true);
  }
}
