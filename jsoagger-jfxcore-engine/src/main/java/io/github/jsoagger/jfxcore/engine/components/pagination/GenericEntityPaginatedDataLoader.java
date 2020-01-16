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
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IPaginatedDataProvider;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import com.google.gson.JsonObject;

/**
 *
 * Navigation is done via modelFullId of model of current controller.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 20 janv. 2018
 */
public class GenericEntityPaginatedDataLoader implements IPaginatedDataProvider {

  protected IOperation countOperation;
  protected IOperation paginateOperation;
  protected String domainClass;
  protected String queryPredicate;


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

    domainClass = componentConfiguration.getPropertyValue("domainClass");
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
    JsonObject query = new JsonObject();
    query.addProperty("oid", ":" + domainClass);

    if (StringUtils.isEmpty(domainClass)) {
      return;
    }

    if (StringUtils.isEmpty(containerFullId)) {
      return;
    }

    if (StringUtils.isNotBlank(queryPredicate)) {
      query.addProperty("queryPredicate", queryPredicate);
    }

    query.addProperty("containerOid", containerFullId);
    countOperation.doOperation(query, consumer);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void navigate(IJSoaggerController controller, IOperationResult currentPage, Direction direction, Consumer<IOperationResult> consumer) {
    String containerFullId = controller.getModelContainerFullId();
    MultipleResult currentResult = (MultipleResult) currentPage;
    if (StringUtils.isEmpty(domainClass)) {
      return;
    }

    if (StringUtils.isEmpty(containerFullId)) {
      return;
    }

    JsonObject query = new JsonObject();
    query.addProperty("oid", ":" + domainClass);
    query.addProperty("containerOid", containerFullId);

    if (StringUtils.isNotBlank(queryPredicate)) {
      query.addProperty("queryPredicate", queryPredicate);
    }

    query.addProperty(IOperationResult.page, getNexPageIndex(direction, currentResult));
    query.addProperty(IOperationResult.pageSize, currentResult.getPageSize().toString());
    paginateOperation.doOperation(query, consumer);
  }
}
