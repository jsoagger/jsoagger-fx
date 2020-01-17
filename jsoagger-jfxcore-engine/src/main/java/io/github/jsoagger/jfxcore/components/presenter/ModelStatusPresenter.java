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
import io.github.jsoagger.jfxcore.api.presenter.IModelAttributePresenter;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.CellPresenterImpl;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;
import io.github.jsoagger.jfxcore.components.rc.RCUtils;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 15 févr. 2018
 */
public class ModelStatusPresenter extends CellPresenterImpl implements IModelAttributePresenter {




  /**
   * Default Constructor
   */
  public ModelStatusPresenter() {
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration) {
    Label p = new Label();
    p.getStyleClass().add("ep-model-current-status");

    String status = getStatus(controller, configuration);
    if (StringUtils.isNotBlank(status)) {
      p.setText(status.toUpperCase());
    }
    return p;
  }


  public String getStatus(IJSoaggerController controller, VLViewComponentXML configuration) {
    String status = RCUtils.getModelAttribute(((StandardViewController)controller).getOpData(), "attributes.lifecycleInfo.currentState");
    return controller.getLocalised(status);
  }


  public String getStatus(IJSoaggerController controller, VLViewComponentXML configuration, OperationData model) {
    String status = (String) ReflectionUIUtils.invokeGetterOn(model, "attributes.lifecycleInfo.currentState");
    return controller.getLocalised(status);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    Label p = new Label();
    p.getStyleClass().add("ep-model-current-status");

    String status = getStatus(controller, configuration, (OperationData) forModel);
    if (StringUtils.isNotBlank(status)) {
      p.setText(controller.getLocalised(status).toUpperCase());
    }
    return p;
  }
}
