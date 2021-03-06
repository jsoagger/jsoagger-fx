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



import com.google.gson.JsonObject;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

/**
 * The demo structure management needs this loader because the view is displayed directly, not
 * redirecgted from details or from a list.
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class RootDocForStructureModelLoader implements IModelProvider {

  /**
   * Constructor
   */
  public RootDocForStructureModelLoader() {
    super();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public IOperationResult loadModel(IJSoaggerController controller, String componentId) {
    String fullId = "1436:io.github.jsoagger.core.model.document.Document";

    IOperation operation = (IOperation) Services.getBean("PersistableLoadBasicRCModelOperation");
    JsonObject query = new JsonObject();
    query.addProperty("fullId", fullId);

    operation.doOperation(query, res -> {
      SingleResult sr = (SingleResult) res;
      ((AbstractViewController) controller).getStructureContent().setFormModelData((OperationData) sr.rootData());
      controller.setModel(res);
    }, ex -> ex.printStackTrace());

    return null;
  }
}
