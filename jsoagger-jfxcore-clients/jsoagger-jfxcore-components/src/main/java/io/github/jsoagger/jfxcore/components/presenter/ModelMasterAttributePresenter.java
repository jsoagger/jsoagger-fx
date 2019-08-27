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
import io.github.jsoagger.jfxcore.api.presenter.IModelAttributePresenter;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.CellPresenterImpl;
import io.github.jsoagger.jfxcore.components.rc.RCUtils;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 15 févr. 2018
 */
public class ModelMasterAttributePresenter extends CellPresenterImpl implements IModelAttributePresenter {

  private Label label = new Label();


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration) {
    String attributePath = configuration.getPropertyValue("attributePath");
    String dataValue = RCUtils.getModelMasterAttribute((OperationData) controller, attributePath);
    if (dataValue != null) {
      label.setText(dataValue.toString());
    }

    return label;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    String attributePath = configuration.getPropertyValue("attributePath");
    String dataValue = RCUtils.getModelMasterAttribute((OperationData) forModel, attributePath);
    if (dataValue != null) {
      label.setText(dataValue.toString());
    }

    return label;
  }

}
