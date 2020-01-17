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
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import com.google.gson.JsonObject;

/**
 * Loads the model associated to forModelId of the structure content. Works only for
 * {@link StandardViewController}
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class StructureContentModelLoader extends AbstractModelProvider implements IModelProvider {

  private IOperation loadSimpleModelOperation;// needs PersistableLoadSimpleModelOperation


  /**
   * @{inheritedDoc}
   */
  @Override
  public IOperationResult loadModel(IJSoaggerController controller, String compId) {
    this.controller = (AbstractViewController) controller;

    StandardViewController standardViewController = (StandardViewController) controller;
    String modelFullId = standardViewController.getStructureContent().__getForModelId();
    String containerOid = ((RootStructureController) controller.getRootStructure()).getRootContext().getContainerFullId();

    standardViewController.getStructureContent().formModelDataProperty().addListener((a,b,c)->{
      if(c != null) {
        JsonObject query = new JsonObject();
        query.addProperty("oid", (String) c.getAttributes().get("fullId"));
        query.addProperty("containerFullId", containerOid);

        loadSimpleModelOperation.doOperation(query, this::onModelLoadSuccess, this::onModelLoadError);
      }
    });

    if (StringUtils.isNotBlank(modelFullId)) {
      JsonObject query = new JsonObject();
      query.addProperty("oid", modelFullId);
      query.addProperty("containerFullId", containerOid);

      loadSimpleModelOperation.doOperation(query, this::onModelLoadSuccess, this::onModelLoadError);
    } else {
      System.out.println("### ## WARNING :StructureContentModelLoader  CAN NOT LOAD MODEL FROM STRUCTURE CONTENT");
    }

    return result;
  }


  /**
   * @return the loadSimpleModelOperation
   */
  public IOperation getLoadSimpleModelOperation() {
    return loadSimpleModelOperation;
  }


  /**
   * @param loadSimpleModelOperation the loadSimpleModelOperation to set
   */
  public void setLoadSimpleModelOperation(IOperation loadSimpleModelOperation) {
    this.loadSimpleModelOperation = loadSimpleModelOperation;
  }
}
