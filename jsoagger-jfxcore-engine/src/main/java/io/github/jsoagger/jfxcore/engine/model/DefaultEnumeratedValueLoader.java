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

package io.github.jsoagger.jfxcore.engine.model;




import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IEnumeratedValueModel;
import io.github.jsoagger.jfxcore.api.IEnumeratedValuesLoader;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import com.google.gson.JsonObject;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class DefaultEnumeratedValueLoader implements IEnumeratedValuesLoader {

  // needs ListvaluesOperation
  private IOperation listvaluesOperation;


  /**
   * Constructor
   */
  public DefaultEnumeratedValueLoader() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public List<IEnumeratedValueModel> loadValues(IJSoaggerController controller, VLViewComponentXML configuration) {

    String enumerationKey = configuration.getPropertyValue("enumerationKey");

    JsonObject query = new JsonObject();
    query.addProperty("enumerationKey", enumerationKey);
    query.addProperty("containerPath", ((RootStructureController) controller.getRootStructure()).getRootContext().getContainerPath());

    List<IEnumeratedValueModel> result = new ArrayList<>(1);
    listvaluesOperation.doOperation(query, e -> {
      MultipleResult multipleResult = (MultipleResult) e;
      result.addAll(EnumeratedValueAdapter.toEnumeratedValues(multipleResult));
    });

    return result;
  }


  /**
   * @return the listvaluesOperation
   */
  public IOperation getListvaluesOperation() {
    return listvaluesOperation;
  }


  /**
   * @param listvaluesOperation the listvaluesOperation to set
   */
  public void setListvaluesOperation(IOperation listvaluesOperation) {
    this.listvaluesOperation = listvaluesOperation;
  }
}
