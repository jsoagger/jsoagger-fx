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
import io.github.jsoagger.jfxcore.components.modelprovider.AbstractModelProvider;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 2 févr. 2018
 */
public class SoftAttributeDetailsViewModelProvider extends AbstractModelProvider implements IModelProvider {

  protected IOperation getTypeByOidOperation;// needs GetGlobalAttributesByOidOperation


  /**
   * Default Constructor
   */
  public SoftAttributeDetailsViewModelProvider() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public IOperationResult loadModel(IJSoaggerController controller, String componentId) {
    this.controller = (AbstractViewController) controller;
    final String fullid = ((AbstractViewController) controller).getStructureContent().__getForModelId();

    final JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("oid", fullid);
    getTypeByOidOperation.doOperation(jsonObject, this::onModelLoadSuccess, this::onModelLoadError);
    return result;
  }
}
