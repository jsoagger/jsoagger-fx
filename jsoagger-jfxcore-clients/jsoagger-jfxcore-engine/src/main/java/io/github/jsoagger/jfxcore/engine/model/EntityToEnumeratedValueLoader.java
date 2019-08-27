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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IEnumeratedValueModel;
import io.github.jsoagger.jfxcore.api.IEnumeratedValuesLoader;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.main.WizardViewController;
import com.google.gson.JsonObject;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class EntityToEnumeratedValueLoader implements IEnumeratedValuesLoader {

  // NEVER AUTOWIRE
  // MUST BE INJECTED
  private IOperation operation;

  private Map<String, Object> properties = new HashMap<>();


  /**
   * Constructor
   */
  public EntityToEnumeratedValueLoader() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public List<IEnumeratedValueModel> loadValues(IJSoaggerController controller, VLViewComponentXML configuration) {
    JsonObject query = new JsonObject();

    if(controller instanceof WizardViewController) {
      // parent must not be null otherwise technical error
      query.addProperty("containerOid", ((WizardViewController) controller).getRootStructure().getModelContainerFullId());
    }
    else {
      query.addProperty("containerOid", controller.getModelContainerFullId());
    }
    for(String key: properties.keySet()) {
      query.addProperty(key, (String) properties.get(key));
    }

    // enumerated values on the master
    boolean masterEnumeratedAttribute = configuration.getBooleanProperty("masterEnumeratedAttribute", false);

    List<IEnumeratedValueModel> result = new ArrayList<>(1);
    if (operation != null) {
      operation.doOperation(query, e -> {
        MultipleResult multipleResult = (MultipleResult) e;

        if(masterEnumeratedAttribute) {
          result.addAll(EnumeratedValueAdapter.toEnumeratedValuesByMasterName(multipleResult));
        }
        else {
          result.addAll(EnumeratedValueAdapter.toEnumeratedValuesByDisplayName(multipleResult));
        }
      });
    }

    return result;
  }


  /**
   * Getter of operation
   *
   * @return the operation
   */
  public IOperation getOperation() {
    return operation;
  }


  /**
   * Setter of operation
   *
   * @param operation the operation to set
   */
  public void setOperation(IOperation operation) {
    this.operation = operation;
  }


  /**
   * Getter of properties
   *
   * @return the properties
   */
  public Map<String, Object> getProperties() {
    return properties;
  }


  /**
   * Setter of properties
   *
   * @param properties the properties to set
   */
  public void setProperties(Map<String, Object> properties) {
    this.properties = properties;
  }
}
