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



import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

public class FromDetailsViewRootElementStructureModelLoader implements IModelProvider {

  /**
   * Constructor
   */
  public FromDetailsViewRootElementStructureModelLoader() {}


  /**
   * {@inheritDoc}
   */
  @Override
  public IOperationResult loadModel(IJSoaggerController controller, String componentId) {
    // this is the current part displayed in the details view
    final OperationData data = ((AbstractViewController) controller).getStructureContent().getFormModelData();

    // set it as model of current controller
    // the controller knows how to load the structure
    final SingleResult result = new SingleResult();
    result.setData(data);
    controller.setModel(result);
    return result;
  }
}
