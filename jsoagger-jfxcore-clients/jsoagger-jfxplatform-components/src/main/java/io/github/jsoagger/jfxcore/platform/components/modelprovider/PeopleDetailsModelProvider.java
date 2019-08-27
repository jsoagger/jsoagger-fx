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
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

/**
 * Provide model for details view when the details view is invoked from a list/table/component where
 * a person was root data.
 * <p>
 * The model is fetched from login of current model.
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class PeopleDetailsModelProvider implements IModelProvider {

  private IOperation getUserDetailsOperation;// needs GetUserDetailsOperation


  public PeopleDetailsModelProvider() {}


  @Override
  public IOperationResult loadModel(IJSoaggerController controller, String componentId) {
    final OperationData data = ((AbstractViewController) controller).getStructureContent().getFormModelData();
    final String login = (String) data.getAttributes().get("email");

    final JsonObject query = new JsonObject();
    query.addProperty("login", login);

    getUserDetailsOperation.doOperation(query, res -> {
      controller.setModel(res);
      ((AbstractViewController) controller).getStructureContent().setFormModelData((OperationData) res.rootData());
    });

    return null;
  }


  /**
   * @return the getUserDetailsOperation
   */
  public IOperation getGetUserDetailsOperation() {
    return getUserDetailsOperation;
  }


  /**
   * @param getUserDetailsOperation the getUserDetailsOperation to set
   */
  public void setGetUserDetailsOperation(IOperation getUserDetailsOperation) {
    this.getUserDetailsOperation = getUserDetailsOperation;
  }

}
