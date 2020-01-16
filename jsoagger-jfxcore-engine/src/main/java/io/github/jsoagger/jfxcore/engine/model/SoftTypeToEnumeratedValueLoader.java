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
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IEnumeratedValueModel;
import io.github.jsoagger.jfxcore.api.IEnumeratedValuesLoader;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;
import com.google.gson.JsonObject;

/**
 * Loads list of type declared in properties from server side and transform it to enumerated value.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class SoftTypeToEnumeratedValueLoader implements IEnumeratedValuesLoader {

  // if root type is searchable/instanciable
  private boolean includeRootTypeInResult = false;
  private IOperation getTypeByPathOperation;

  private IOperation operation; // GetInstanciableSoftTypesOperation
  private Map<String, Object> properties = new HashMap<>();


  /**
   * Constructor
   */
  public SoftTypeToEnumeratedValueLoader() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public List<IEnumeratedValueModel> loadValues(IJSoaggerController controller, VLViewComponentXML configuration) {
    JsonObject query = new JsonObject();
    query.addProperty("containerOid", controller.getModelContainerFullId());
    for(String key: properties.keySet()) {
      query.addProperty(key, (String) properties.get(key));
    }

    List<IEnumeratedValueModel> result = new ArrayList<>(1);
    if (operation != null) {
      operation.doOperation(query, e -> {
        MultipleResult multipleResult = (MultipleResult) e;
        result.addAll(toEnumeratedValues(multipleResult));
      });
    }else {
      System.out.println("[ERROR] operation is null " + this);
    }

    if (includeRootTypeInResult && getTypeByPathOperation != null) {
      query.addProperty("path", (String) properties.get("rootType"));
      getTypeByPathOperation.doOperation(query, e -> addToResult(result, e));
    }

    return result;
  }


  private void addToResult(List<IEnumeratedValueModel> result, IOperationResult e) {
    if (e != null) {
      OperationData data = (OperationData) e.rootData();

      if (data != null) {
        EnumeratedValueModel model = new EnumeratedValueModel();
        model.setValue((String) ReflectionUIUtils.invokeGetterOn(data, "attributes.displayName"));
        model.setSavedValue((String) ReflectionUIUtils.invokeGetterOn(data, "attributes.fullId"));
        model.setDescription((String) ReflectionUIUtils.invokeGetterOn(data, "attributes.description"));
        model.setSourceModel(data);
        result.add(model);
      }
    }
  }


  public static List<EnumeratedValueModel> toEnumeratedValues(MultipleResult result) {
    List<EnumeratedValueModel> vals = new ArrayList<>();
    if (result != null) {
      List<OperationData> datas = result.getData();
      if (datas != null) {
        for(OperationData data: datas) {
          EnumeratedValueModel model = new EnumeratedValueModel();
          model.setValue((String) ReflectionUIUtils.invokeGetterOn(data, "attributes.displayName"));
          model.setSavedValue((String) ReflectionUIUtils.invokeGetterOn(data, "attributes.fullId"));
          model.setDescription((String) ReflectionUIUtils.invokeGetterOn(data, "attributes.description"));
          model.setSourceModel(data);
          vals.add(model);
        }
      }
    }
    return vals;
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


  /**
   * @return the includeRootTypeInResult
   */
  public boolean isIncludeRootTypeInResult() {
    return includeRootTypeInResult;
  }


  /**
   * @param includeRootTypeInResult the includeRootTypeInResult to set
   */
  public void setIncludeRootTypeInResult(boolean includeRootTypeInResult) {
    this.includeRootTypeInResult = includeRootTypeInResult;
  }


  /**
   * @return the getTypeByPathOperation
   */
  public IOperation getGetTypeByPathOperation() {
    return getTypeByPathOperation;
  }


  /**
   * @param getTypeByPathOperation the getTypeByPathOperation to set
   */
  public void setGetTypeByPathOperation(IOperation getTypeByPathOperation) {
    this.getTypeByPathOperation = getTypeByPathOperation;
  }
}
