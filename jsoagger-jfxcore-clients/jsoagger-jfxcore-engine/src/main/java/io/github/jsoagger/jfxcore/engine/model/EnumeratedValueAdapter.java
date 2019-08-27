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
import java.util.Map;

import io.github.jsoagger.core.bridge.operation.JsonUtils;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;
import com.google.gson.JsonObject;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class EnumeratedValueAdapter {

  /**
   * Constructor
   */
  public EnumeratedValueAdapter() {}


  public static List<EnumeratedValueModel> toEnumeratedValues(MultipleResult result) {
    List<EnumeratedValueModel> vals = new ArrayList<>();
    if (result != null) {
      List<OperationData> datas = result.getData();
      if (datas != null) {
        for(OperationData data: datas) {
          EnumeratedValueModel model = new EnumeratedValueModel();
          model.setValue((String) ReflectionUIUtils.invokeGetterOn(data, "attributes.value"));
          model.setSavedValue((String) ReflectionUIUtils.invokeGetterOn(data, "attributes.savedValue"));
          model.setDescription((String) ReflectionUIUtils.invokeGetterOn(data, "attributes.description"));
          model.setSourceModel(data);
          vals.add(model);
        };
      }
    }
    return vals;
  }


  public static List<EnumeratedValueModel> toEnumeratedValuesByName(MultipleResult result) {
    List<EnumeratedValueModel> vals = new ArrayList<>();
    if (result != null) {
      List<OperationData> datas = result.getData();
      if (datas != null) {
        for(OperationData data: datas) {
          EnumeratedValueModel model = new EnumeratedValueModel();
          model.setValue((String) ReflectionUIUtils.invokeGetterOn(data, "attributes.name"));
          model.setSavedValue((String) ReflectionUIUtils.invokeGetterOn(data, "attributes.fullId"));
          model.setDescription((String) ReflectionUIUtils.invokeGetterOn(data, "attributes.description"));
          model.setSourceModel(data);
          vals.add(model);
        };
      }
    }
    return vals;
  }


  public static List<EnumeratedValueModel> toEnumeratedValuesByMasterName(MultipleResult result) {
    List<EnumeratedValueModel> vals = new ArrayList<>();
    if (result != null) {
      List<OperationData> datas = result.getData();
      if (datas != null) {
        for(OperationData data: datas) {
          EnumeratedValueModel model = new EnumeratedValueModel();
          model.setValue((String) data.getMasterAttributes().get("name"));
          model.setDescription((String) data.getMasterAttributes().get("description"));
          model.setSavedValue((String) data.getMasterAttributes().get("fullId"));
          model.setSourceModel(data);
          vals.add(model);
        };
      }
    }
    return vals;
  }


  public static List<EnumeratedValueModel> toEnumeratedValuesByDisplayName(MultipleResult result) {
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


  public static List<EnumeratedValueModel> toEnumeratedTypeValues(MultipleResult result) {
    List<EnumeratedValueModel> vals = new ArrayList<>();
    if (result != null) {
      List<OperationData> datas = result.getData();
      if (datas != null) {
        for(OperationData data: datas) {
          EnumeratedValueModel model = new EnumeratedValueModel();
          model.setValue((String) ReflectionUIUtils.invokeGetterOn(data, "attributes.linkDisplayName"));
          model.setSavedValue((String) ReflectionUIUtils.invokeGetterOn(data, "type"));
          model.setDescription((String) ReflectionUIUtils.invokeGetterOn(data, "attributes.linkDescription"));
          model.setSourceModel(data);
          vals.add(model);
        };
      }
    }
    return vals;
  }


  /**
   * Each linkable type is in the relashionship section. Saved Value of each model is formatted as :
   * rolebid|rolebLogicalPath|linkInternalName
   */
  public static List<EnumeratedValueModel> toEnumeratedLinkableroleBValues(MultipleResult result) {
    List<EnumeratedValueModel> vals = new ArrayList<>();
    if (result != null) {
      List<OperationData> datas = result.getData();
      if (datas != null) {
        for(OperationData data: datas) {
          Map<String, Object> relashionship = data.getRelationships();

          for (String key : relashionship.keySet()) {
            String savedValue = key;

            String jsonString = (String) relashionship.get(savedValue);
            JsonObject jsonObject = JsonUtils.toJsonObject(jsonString);

            savedValue += "|" + jsonObject.get("logicalPath").getAsString() + "|" + data.getAttributes().get("linkInternalName");

            EnumeratedValueModel model = new EnumeratedValueModel();
            model.setValue(jsonObject.get("displayName").getAsString());
            model.setSavedValue(savedValue);
            model.setDescription((String) ReflectionUIUtils.invokeGetterOn(data, "attributes.linkDescription"));
            model.setSourceModel(data);
            vals.add(model);
          }
        };
      }
    }
    return vals;
  }
}
