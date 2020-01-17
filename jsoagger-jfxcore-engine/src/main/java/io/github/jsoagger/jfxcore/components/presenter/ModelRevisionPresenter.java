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
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;
import io.github.jsoagger.jfxcore.components.rc.RCUtils;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 15 févr. 2018
 */
public class ModelRevisionPresenter extends CellPresenterImpl implements IModelAttributePresenter {

  Label p = new Label();


  /**
   * Constructor
   */
  public ModelRevisionPresenter() {
    p.getStyleClass().add("ep-rc-revision-label");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration) {
    String revision = getRevision(controller, configuration);
    if (StringUtils.isNotBlank(revision)) {
      p.setText(revision);
    }
    return p;
  }


  public String getRevision(IJSoaggerController controller, VLViewComponentXML configuration) {
    Object version = RCUtils.getModelAttribute((AbstractViewController) controller, "attributes.versionInfo.versionId");
    Integer iteration = Double.valueOf(RCUtils.getModelAttribute((AbstractViewController) controller, "attributes.iterationInfo.iterationNumber")).intValue();
    if (version != null && StringUtils.isNotBlank(version.toString())) {
      return version.toString().toUpperCase() + "." + iteration.intValue();
    }
    return iteration.toString();
  }


  public String getRevision(IJSoaggerController controller, VLViewComponentXML configuration, OperationData model) {
    Object version = ReflectionUIUtils.invokeGetterOn(model, "attributes.versionInfo.versionId");
    Double iteration = (Double) ReflectionUIUtils.invokeGetterOn(model, "attributes.iterationInfo.iterationNumber");
    if (version != null && StringUtils.isNotBlank(version.toString())) {
      return version.toString().toUpperCase() + "." + iteration.intValue();
    }
    return "Unkown version";
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    String revision = getRevision(controller, configuration, (OperationData) forModel);
    if (StringUtils.isNotBlank(revision)) {
      p.setText(revision);
    }
    return p;
  }

}
