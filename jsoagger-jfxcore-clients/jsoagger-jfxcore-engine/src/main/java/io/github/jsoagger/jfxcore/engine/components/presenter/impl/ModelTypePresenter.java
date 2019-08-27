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
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.presenter.IModelTypePresenter;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 24 févr. 2018
 */
public class ModelTypePresenter implements IModelTypePresenter {

  private Label type = new Label();


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node presentTypeOf(IJSoaggerController controller, VLViewComponentXML component) {
    SingleResult result = (SingleResult) controller.getModel();
    type.setText(getTypeOf(controller, result.getData()));
    return type;
  }


  private String getTypeOf(IJSoaggerController controller, OperationData data) {
    return (String) ReflectionUIUtils.invokeGetterOn(data, "businessType.displayName");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node presentTypeOf(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    type.setText(getTypeOf(null, null));
    return type;
  }
}
