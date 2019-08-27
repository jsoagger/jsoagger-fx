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


import com.google.gson.JsonObject;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.engine.model.ObjectModel;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ContainerTeamMemberProvider implements IModelProvider {

  private IOperation loadContainerTeamMembersOperation;// needs LoadContainerTeamMembersOperation


  /**
   * Constructor
   */
  public ContainerTeamMemberProvider() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public IOperationResult loadModel(IJSoaggerController controller, String compId) {
    JsonObject model = (JsonObject) controller.getRootStructure().getModel();
    String containerFullId = model.get("oid").getAsString();

    JsonObject query = new JsonObject();
    query.addProperty("oid", containerFullId);

    loadContainerTeamMembersOperation.doOperation(query, operationResult -> {
      ObjectModel m = new ObjectModel();
      m.setEntry(operationResult);
      controller.setModel(m);
    });

    return null;
  }
}
