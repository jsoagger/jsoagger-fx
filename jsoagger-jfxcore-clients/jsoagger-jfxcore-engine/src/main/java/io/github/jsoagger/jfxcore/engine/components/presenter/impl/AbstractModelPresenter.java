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



import java.util.HashMap;
import java.util.Map;

import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 30 janv. 2018
 */
public class AbstractModelPresenter {

  private Map<String, String> extraParameters = new HashMap<>();


  public String getModelAttribute(OperationData data, String name) {
    if (data != null) {
      Object o = ReflectionUIUtils.invokeGetterOn(data, name);
      return o != null ? o.toString() : "";
    }

    return "";
  }


  public String getModelAttribute(IJSoaggerController controller, String name) {
    IOperationResult model = (IOperationResult) controller.getModel();
    if (model != null) {
      OperationData data = (OperationData) model.rootData();
      if (data != null) {
        Object d = ReflectionUIUtils.invokeGetterOn(data, name);
        return d != null ? d.toString() : "";
      }
    }

    return null;
  }


  /**
   *
   */
  public String getDescriptionLabelStyles() {
    return null;
  }


  /**
   * Getter of extraParameters
   *
   * @return the extraParameters
   */
  public Map<String, String> getExtraParameters() {
    return extraParameters;
  }


  /**
   * Setter of extraParameters
   *
   * @param extraParameters the extraParameters to set
   */
  public void setExtraParameters(Map<String, String> extraParameters) {
    this.extraParameters = extraParameters;
  }
}
