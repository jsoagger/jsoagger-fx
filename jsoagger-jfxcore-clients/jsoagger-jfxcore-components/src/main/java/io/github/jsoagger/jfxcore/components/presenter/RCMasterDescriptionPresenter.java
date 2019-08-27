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
import io.github.jsoagger.jfxcore.api.presenter.ModelSecondaryLabelPresenter;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.CellPresenterImpl;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.components.rc.RCUtils;

import javafx.scene.Node;
import javafx.scene.text.Text;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 4 févr. 2018
 */
public class RCMasterDescriptionPresenter extends CellPresenterImpl implements ModelSecondaryLabelPresenter {

  /**
   * Default Constructor
   */
  public RCMasterDescriptionPresenter() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node provideLabel(IJSoaggerController controller, VLViewComponentXML configuration) {
    Text label = new Text(RCUtils.getModelMasterAttribute((AbstractViewController) controller, "description"));
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
    Text label = new Text(RCUtils.getModelMasterAttribute((OperationData) forModel, "description"));
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
    return present(controller, configuration, forModel);
  }
}
