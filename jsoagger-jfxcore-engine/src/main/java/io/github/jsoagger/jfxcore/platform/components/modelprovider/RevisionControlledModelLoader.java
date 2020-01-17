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

package io.github.jsoagger.jfxcore.platform.components.modelprovider;



import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

/**
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class RevisionControlledModelLoader implements IModelProvider {


  /**
   *
   */
  public RevisionControlledModelLoader() {}


  /**
   * {@inheritDoc}
   */
  @Override
  public IOperationResult loadModel(IJSoaggerController controller, String componentId) {
    OperationData operationData = ((AbstractViewController) controller).getStructureContent().getFormModelData();
    List<IOperationResult> ress = new ArrayList<>();

    if (operationData != null) {
      String fullId = (String) operationData.getAttributes().get("fullId");

      JsonObject jo = new JsonObject();
      jo.addProperty("fullId", fullId);

      IOperation op = (IOperation) Services.getBean("PersistableLoadBasicRCModelOperation");
      op.doOperation(jo, res -> {
        ress.add(res);
        controller.setModel(res);
      }, ex -> {
        ex.printStackTrace();
      });
    }

    return ress.get(0);
  }
}
