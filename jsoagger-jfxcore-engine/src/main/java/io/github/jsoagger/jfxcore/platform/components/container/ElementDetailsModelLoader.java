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
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.components.modelprovider.AbstractModelProvider;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.StructureContentController;

/**
 * Loads the part with its master for details view. The identifier of the part to load is supposed
 * to be found in the {@link StructureContentController}
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ElementDetailsModelLoader extends AbstractModelProvider implements IModelProvider {

  private IOperation operation;// needs PersistableLoadBasicRCModelOperation


  /**
   * @{inheritedDoc}
   */
  @Override
  public IOperationResult loadModel(IJSoaggerController controller, String compId) {
    this.controller = (AbstractViewController) controller;

    final StandardViewController standardViewController = (StandardViewController) controller;
    final String modelFullId = standardViewController.getStructureContent().__getForModelId();
    final String containerOid = ((RootStructureController) controller.getRootStructure()).getRootContext().getContainerFullId();

    final JsonObject query = new JsonObject();
    query.addProperty("oid", modelFullId);
    query.addProperty("containerFullId", containerOid);

    operation.doOperation(query, this::onModelLoadSuccess, this::onModelLoadError);

    // update the model of structure content to more fresh one
    final SingleResult sr = (SingleResult) result;
    standardViewController.getStructureContent().setFormModelData(sr.getData());
    return result;
  }
}
