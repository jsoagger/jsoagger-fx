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

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IPaginatedDataProvider;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.quickactions.PushToViewActionPresenter;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.StructureContentController;
import com.google.gson.JsonObject;

/**
 * Used when want to load data related to a row of a table structure for example. Used along with
 * {@link PushToViewActionPresenter} which will setthe selected model into the
 * {@link StructureContentController}.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 20 janv. 2018
 */
public class StructureContentRelatedModelPaginatedDataLoader implements IPaginatedDataProvider {

  protected IOperation countOperation;
  protected IOperation paginateOperation;


  @Override
  public void initFromConfiguration(IJSoaggerController controller, VLViewComponentXML componentConfiguration) {
    String countOperation = componentConfiguration.getPropertyValue("countOperation");
    if (StringUtils.isNotBlank(countOperation)) {
      this.countOperation = (IOperation) Services.getBean(countOperation);
    }

    String paginateOperation = componentConfiguration.getPropertyValue("paginateOperation");
    if (StringUtils.isNotBlank(paginateOperation)) {
      this.paginateOperation = (IOperation) Services.getBean(paginateOperation);
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void count(IJSoaggerController controller, IOperationResult currentResult, Consumer<IOperationResult> consumer) {
    String containerFullId = controller.getModelContainerFullId();
    String modelFullId = controller.getModelFullId();
    JsonObject query = new JsonObject();
    query.addProperty("oid", modelFullId);
    query.addProperty("containerFullId", containerFullId);
    countOperation.doOperation(query, consumer);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void navigate(IJSoaggerController controller, IOperationResult currentPage, Direction direction, Consumer<IOperationResult> consumer) {
    String containerFullId = controller.getModelContainerFullId();

    OperationData data = ((AbstractViewController) controller).getStructureContent().getFormModelData();
    String modelFullId = (String) data.getAttributes().get("fullId");
    MultipleResult currentResult = (MultipleResult) currentPage;

    JsonObject query = new JsonObject();
    query.addProperty("oid", modelFullId);
    query.addProperty("containerFullId", containerFullId);
    query.addProperty(IOperationResult.page, getNexPageIndex(direction, currentResult));
    query.addProperty(IOperationResult.pageSize, currentResult.getPageSize().toString());
    paginateOperation.doOperation(query, consumer);
  }
}
