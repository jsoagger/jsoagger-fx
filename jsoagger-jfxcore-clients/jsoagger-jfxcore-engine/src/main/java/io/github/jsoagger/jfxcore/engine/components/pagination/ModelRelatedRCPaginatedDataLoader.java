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
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IPaginatedDataProvider;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import com.google.gson.JsonObject;

/**
 *
 * Navigation is done via modelFullId of model of current controller.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 20 janv. 2018
 */
public class ModelRelatedRCPaginatedDataLoader implements IPaginatedDataProvider {

  protected IOperation countOperation;
  protected IOperation paginateOperation;
  protected String domainClass;
  protected String queryPredicate;


  @Override
  public void initFromConfiguration(IJSoaggerController controller, VLViewComponentXML componentConfiguration) {
    String paginateOperation = componentConfiguration.getPropertyValue("paginateOperation");
    if (StringUtils.isNotBlank(paginateOperation)) {
      this.paginateOperation = (IOperation) Services.getBean(paginateOperation);
    }

    queryPredicate = componentConfiguration.getPropertyValue("queryPredicate");
  }


  @Override
  public int getNexPageIndex(Direction direction, MultipleResult currentPage) {
    int nextPageIndex = -1;

    switch (direction) {
      case FIRST:
        nextPageIndex = currentPage.firstPage();
        break;

      case LAST:
        nextPageIndex = currentPage.lastPage();
        break;

      case NEXT:
        nextPageIndex = currentPage.getNextPageIndex();
        break;

      case PREVIOUS:
        nextPageIndex = currentPage.getPreviousPageIndex();
        break;

      default:
        break;
    }

    return nextPageIndex;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void count(IJSoaggerController controller, IOperationResult currentResult, Consumer<IOperationResult> consumer) {
    String containerFullId = controller.getModelContainerFullId();
    String fullId = controller.getModelFullId();
    JsonObject query = new JsonObject();
    query.addProperty("fullId", fullId);
    query.addProperty("oid", fullId);

    if (StringUtils.isEmpty(containerFullId)) {
      return;
    }

    if (StringUtils.isNotBlank(queryPredicate)) {
      query.addProperty("queryPredicate", queryPredicate);
    }

    String masterFullId = (String) ((AbstractViewController)controller).getOpData().getMasterAttributes().get("fullId");
    if (StringUtils.isNotEmpty(masterFullId)) {
      query.addProperty("masterFullId", masterFullId);
    }

    String version = (String) ((AbstractViewController)controller).getOpData().getAttributes().get("version.versionId");
    if (StringUtils.isNotEmpty(version)) {
      query.addProperty("versionNumber", version);
    }

    query.addProperty("containerOid", containerFullId);

    if (countOperation != null) {
      countOperation.doOperation(query, consumer);
    } else {
      consumer.accept(new SingleResult());
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void navigate(IJSoaggerController controller, IOperationResult currentPage, Direction direction, Consumer<IOperationResult> consumer) {
    String containerFullId = controller.getModelContainerFullId();
    String fullId = controller.getModelFullId();
    String masterFullId = (String) ((AbstractViewController)controller).getOpData().getMasterAttributes().get("fullId");

    JsonObject query = new JsonObject();
    query.addProperty("fullId", fullId);
    query.addProperty("oid", fullId);

    if (StringUtils.isEmpty(containerFullId)) {
      return;
    }

    if (StringUtils.isNotEmpty(masterFullId)) {
      query.addProperty("masterFullId", masterFullId);
    }

    String versionNumber =  (String) ((AbstractViewController)controller).getOpData().getAttributes().get("versionInfo.versionId");
    query.addProperty("versionNumber", versionNumber);

    if (StringUtils.isNotBlank(queryPredicate)) {
      query.addProperty("queryPredicate", queryPredicate);
    }

    query.addProperty("containerOid", containerFullId);
    query.addProperty(IOperationResult.pageSize, -1);
    if (paginateOperation != null) {
      paginateOperation.doOperation(query, consumer);
    } else {
      consumer.accept(new MultipleResult());
    }
  }
}
