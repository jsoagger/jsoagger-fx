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

package io.github.jsoagger.jfxcore.engine.components.pagination;



import java.util.function.Consumer;

import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IPaginatedDataProvider;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import com.google.gson.JsonObject;

/**
 * For content holder, primary content have been loaded by main data provider. We just need to
 * display them.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 20 janv. 2018
 */
public class ModelRelatedPrimaryContentDataLoader implements IPaginatedDataProvider {

  @Override
  public void initFromConfiguration(IJSoaggerController controller, VLViewComponentXML componentConfiguration) {}


  @Override
  public int getNexPageIndex(Direction direction, MultipleResult currentPage) {
    // no pagination
    return -1;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void count(IJSoaggerController controller, IOperationResult currentResult, Consumer<IOperationResult> consumer) {
    final SingleResult result = new SingleResult();
    result.addMetaData("count", "0");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void navigate(IJSoaggerController controller, IOperationResult currentPage, Direction direction, Consumer<IOperationResult> consumer) {
    final SingleResult model = (SingleResult) controller.getModel();
    final String fullId = (String) model.getData().getAttributes().get("fullId");

    final JsonObject query = new JsonObject();
    query.addProperty("fullId", fullId);
    query.addProperty("role", "primary");

    final IOperation getPrimaryContentOp = (IOperation) Services.getBean("GetContentInfoOperation");
    getPrimaryContentOp.doOperation(query, res -> consumer.accept(res));
  }
}
