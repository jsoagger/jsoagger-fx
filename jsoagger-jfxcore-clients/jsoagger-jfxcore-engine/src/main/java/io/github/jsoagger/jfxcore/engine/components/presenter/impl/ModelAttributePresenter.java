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

package io.github.jsoagger.jfxcore.engine.components.presenter.impl;



import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.presenter.IModelAttributePresenter;
import io.github.jsoagger.jfxcore.api.presenter.ModelEnumeratedValueTranslater;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.CellPresenterImpl;
import io.github.jsoagger.jfxcore.api.services.Services;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 15 févr. 2018
 */
public class ModelAttributePresenter extends CellPresenterImpl implements IModelAttributePresenter {

  private Label label = new Label();
  private ModelEnumeratedValueTranslater enumeratedTypeTranslater;
  private String attributePath;


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration) {
    if (StringUtils.isEmpty(attributePath)) {
      attributePath = configuration.getPropertyValue("attributePath");
    }

    String dataValue = getModelAttribute(controller, attributePath);
    initEnumeratedTypeTranslater(configuration);

    if (dataValue != null) {
      if (enumeratedTypeTranslater != null) {
        // TO DO Asunch if long running
        String realVal = enumeratedTypeTranslater.translate(controller, configuration, dataValue.toString());
        label.setText(realVal);
      } else {
        label.setText(dataValue.toString());
      }
    }

    return label;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    if (StringUtils.isEmpty(attributePath)) {
      attributePath = configuration.getPropertyValue("attributePath");
    }

    String dataValue = getModelAttribute((OperationData) forModel, attributePath);
    initEnumeratedTypeTranslater(configuration);

    if (dataValue != null) {
      if (enumeratedTypeTranslater != null) {
        // TO DO Asunch if long running
        String realVal = enumeratedTypeTranslater.translate(controller, configuration, dataValue.toString());
        label.setText(realVal);
      } else {
        label.setText(dataValue.toString());
      }
    }

    return label;
  }


  private void initEnumeratedTypeTranslater(VLViewComponentXML config) {
    String enumeratedTypeTranslater = config.getPropertyValue("enumeratedTypeTranslater");
    if (StringUtils.isNotBlank(enumeratedTypeTranslater)) {
      this.enumeratedTypeTranslater = (ModelEnumeratedValueTranslater) Services.getBean(enumeratedTypeTranslater);
    }
  }


  /**
   * @return the attributePath
   */
  public String getAttributePath() {
    return attributePath;
  }


  /**
   * @param attributePath the attributePath to set
   */
  public void setAttributePath(String attributePath) {
    this.attributePath = attributePath;
  }

}
