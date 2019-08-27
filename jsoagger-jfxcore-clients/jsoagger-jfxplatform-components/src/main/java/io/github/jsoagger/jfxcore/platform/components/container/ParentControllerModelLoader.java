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




import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.components.modelprovider.AbstractModelProvider;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

/**
 * Set the model of the given controller from the parent controller.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ParentControllerModelLoader extends AbstractModelProvider implements IModelProvider {

  /**
   * Default Constructor
   */
  public ParentControllerModelLoader() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public IOperationResult loadModel(IJSoaggerController controller, String compId) {
    this.controller = (AbstractViewController) controller;
    if (((AbstractViewController) controller).getParent() != null) {
      this.controller.setModel(((AbstractViewController) controller).getParent().getModel());
    }

    return result;
  }
}
