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

package io.github.jsoagger.jfxcore.engine.components.presenter.impl.descriptionprovider;



import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.presenter.ModelSecondaryLabelPresenter;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.CellPresenterImpl;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 26 janv. 2018
 */
public class UserModelDescriptionPresenter extends CellPresenterImpl implements ModelSecondaryLabelPresenter {

  /**
   * Default Constructor
   */
  public UserModelDescriptionPresenter() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node provideLabel(IJSoaggerController controller, VLViewComponentXML configuration) {
    Label description = new Label();
    IOperationResult operationResult = (IOperationResult) controller.getModel();
    OperationData operationData = (OperationData) operationResult.rootData();

    if (operationData != null) {
      String email = (String) operationData.getAttributes().get("email");
      description.setText(email);
    }

    if (StringUtils.isNotBlank(getDescriptionLabelStyles())) {
      description.getStyleClass().addAll(getDescriptionLabelStyles().split(","));
    }

    return description;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node provideLabel(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    Label description = new Label();
    OperationData data = (OperationData) forModel;

    if (data != null) {
      String email = (String) data.getAttributes().get("email");
      description.setText(email);
    }

    if (StringUtils.isNotBlank(getDescriptionLabelStyles())) {
      description.getStyleClass().addAll(getDescriptionLabelStyles().split(","));
    }

    return description;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    return present(controller, configuration, forModel);
  }
}
