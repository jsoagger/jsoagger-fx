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


import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.presenter.ModelSecondaryLabelPresenter;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.CellPresenterImpl;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 30 janv. 2018
 */
public class ModelDescriptionPresenter extends CellPresenterImpl implements ModelSecondaryLabelPresenter {

  /**
   * Default Constructor
   */
  public ModelDescriptionPresenter() {
    super();
  }


  protected String getAttributeName(IJSoaggerController controller, VLViewComponentXML configuration) {
    return "attributes.description";
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node provideLabel(IJSoaggerController controller, VLViewComponentXML configuration) {
    Label label = new Label(getModelAttribute(controller, getAttributeName(controller, configuration)));
    if (StringUtils.isNotBlank(getDescriptionLabelStyles())) {
      label.getStyleClass().addAll(getDescriptionLabelStyles().split(","));
    }

    return label;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node provideLabel(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    String desc = getModelAttribute((OperationData) forModel, getAttributeName(controller, configuration));
    Label label = new Label();
    if (StringUtils.isNotBlank(desc) && desc.length() > 60) {
      label.setText(desc.substring(0, 60));
      if (desc.length() > 80) {
        label.setText(label.getText() + ".....");
      }
    } else {
      label.setText(desc);
      return label;
    }

    label.setWrapText(false);
    if (StringUtils.isNotBlank(getDescriptionLabelStyles())) {
      label.getStyleClass().addAll(getDescriptionLabelStyles().split(","));
    }
    return label;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    return provideLabel(controller, configuration, forModel);
  }
}
