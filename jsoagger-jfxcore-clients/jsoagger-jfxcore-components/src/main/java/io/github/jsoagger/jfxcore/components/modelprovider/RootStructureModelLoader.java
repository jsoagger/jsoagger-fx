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

package io.github.jsoagger.jfxcore.components.modelprovider;


import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import com.google.gson.JsonObject;

/**
 * Loads the container associated to the root structure and set it as the model of the current
 * controller.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class RootStructureModelLoader extends AbstractModelProvider implements IModelProvider {

  private IOperation loadContainerByOidOperation;


  /**
   * @{inheritedDoc}
   */
  @Override
  public IOperationResult loadModel(IJSoaggerController controller, String compId) {
    final String containerOid = ((RootStructureController) controller.getRootStructure()).getRootContext().getContainerFullId();
    this.controller = (AbstractViewController) controller;

    if(loadContainerByOidOperation == null) {
      throw new RuntimeException("Configuration error, please set loadContainerByOidOperation on RootStructureModelLoader");
    }

    final JsonObject query = new JsonObject();
    query.addProperty("oid", containerOid);

    loadContainerByOidOperation.doOperation(query, res -> {
      onModelLoadSuccess(res);
    }, this::onModelLoadError);

    return result;
  }

  public IOperation getLoadContainerByOidOperation() {
    return loadContainerByOidOperation;
  }


  public void setLoadContainerByOidOperation(IOperation loadContainerByOidOperation) {
    this.loadContainerByOidOperation = loadContainerByOidOperation;
  }
}
