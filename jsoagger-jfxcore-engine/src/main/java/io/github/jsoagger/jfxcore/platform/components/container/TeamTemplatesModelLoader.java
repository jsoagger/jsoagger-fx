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

package io.github.jsoagger.jfxcore.platform.components.container;




import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IModelProvider;

/**
 * Load team templates for combobox.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class TeamTemplatesModelLoader implements IModelProvider {

  private IOperation loadContainerTeamTemplatesOperation;// needs LoadContainerTeamTemplatesOperation
  private IOperationResult result;


  /**
   * @{inheritedDoc}
   */
  @Override
  public IOperationResult loadModel(IJSoaggerController controller, String compId) {
    JsonObject model = (JsonObject) controller.getRootStructure().getModel();
    String containerFullId = model.get("fullId").getAsString();

    JsonObject query = new JsonObject();
    query.addProperty("oid", containerFullId);

    // !! important the loaded must be transformed into combobox model
    // friendly values.
    loadContainerTeamTemplatesOperation.doOperation(query, res -> {
      result = new MultipleResult();
      List<OperationData> datas = new ArrayList<>();
      ((MultipleResult) result).setData(datas);
      ((MultipleResult) result).setMetaData(res.getMetaData());

      List<OperationData> sourceDatas = (List<OperationData>) res.rootData();
      for (OperationData sourceData : sourceDatas) {
        OperationData modelT = new OperationData.Builder().addAttribute("savedValue", sourceData.getAttributes().get("internalName"))
            .addAttribute("value", sourceData.getAttributes().get("displayName")).addAttribute("description", sourceData.getAttributes().get("description")).build();
        datas.add(modelT);
      }
    });
    return result;
  }
}
