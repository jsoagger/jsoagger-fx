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
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.components.modelprovider.AbstractModelProvider;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;

/**
 * Loads the latest version associated to the given master object. Can be used when displayed is a
 * list of masters and want to do action on latest iteration.
 * <p>
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class LatestVersionFromStructureContentModelReference extends AbstractModelProvider implements IModelProvider {

  /**
   * @{inheritedDoc}
   */
  @Override
  public IOperationResult loadModel(IJSoaggerController controller, String compId) {
    this.controller = (AbstractViewController) controller;
    final StandardViewController standardViewController = (StandardViewController) controller;

    // this is the master
    final OperationData data = standardViewController.getStructureContent().getFormModelData();

    final IOperation op = (IOperation) Services.getBean("DGetLatestVersionByMasterOidOperation");
    final JsonObject query = new JsonObject();

    // this is the master oid
    query.addProperty("oid", data.getAttributes().get("fullId").toString());
    op.doOperation(query, r -> {

      // Because all actions to child controller should be done relative
      // to this!
      standardViewController.getStructureContent().setFormModelData((OperationData) r.rootData());

      // this is the lastest version
      result = r;
      onModelLoadSuccess(r);
    });

    return result;
  }
}
