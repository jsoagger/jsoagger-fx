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



import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import com.google.gson.JsonObject;

import javafx.concurrent.Task;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 20 janv. 2018
 */
public class DefaultLinkPaginatedDataLoader implements ILinkPaginatedDataProvider {

  protected IOperation countOperation;
  protected IOperation paginateOperation;

  // needs GetDynamicalAttributesDefinitionOperation
  private IOperation getDynamicalAttributesDefinitionOperation;

  protected String linkClass;
  protected String linkConstraintName;
  protected String linkType;

  /** indicates that data should be loaded relative to master no iteration*/
  /** Example when loading usedBy relations*/
  protected boolean masterLink = false;

  private IOperationResult linkTypeDynamicalAttributes;


  /**
   * Constructor
   */
  public DefaultLinkPaginatedDataLoader() {}


  @Override
  public void initFromConfiguration(IJSoaggerController controller, VLViewComponentXML componentConfiguration) {
    linkClass = componentConfiguration.getPropertyValue("linkClass");
    linkConstraintName = componentConfiguration.getPropertyValue("linkConstraintName");
    linkType = componentConfiguration.getPropertyValue("linkType");
    masterLink = componentConfiguration.getBooleanProperty("masterLink", false);

    if (StringUtils.isNotBlank(linkType)) {
      loadAttributesDefinition();
    }

    final String countOperation = componentConfiguration.getPropertyValue("countOperation");
    if (StringUtils.isNotBlank(countOperation)) {
      this.countOperation = (IOperation) Services.getBean(countOperation);
    }

    final String paginateOperation = componentConfiguration.getPropertyValue("paginateOperation");
    if (StringUtils.isNotBlank(paginateOperation)) {
      this.paginateOperation = (IOperation) Services.getBean(paginateOperation);
    }
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


  protected void loadAttributesDefinition() {
    final LoadAttributesDefinitionTask loadAttributesDefinitionTask = new LoadAttributesDefinitionTask(linkType);
    loadAttributesDefinitionTask.setOnSucceeded(worker -> {
      try {
        this.linkTypeDynamicalAttributes = loadAttributesDefinitionTask.get();
      } catch (InterruptedException | ExecutionException e) {
      }
    });

    final Thread t = new Thread(loadAttributesDefinitionTask);
    t.start();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void count(IJSoaggerController controller, IOperationResult currentResult, Consumer<IOperationResult> consumer) {
    final String containerFullId = controller.getModelContainerFullId();

    final JsonObject query = new JsonObject();
    query.addProperty("oid", containerFullId);
    query.addProperty("linkClass", getLinkClass());

    if (StringUtils.isNotBlank(getLinkConstraintName())) {
      query.addProperty("linkConstraintName", getLinkConstraintName());
    }

    countOperation.doOperation(query, consumer);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void navigate(IJSoaggerController controller, IOperationResult currentPage, Direction direction, Consumer<IOperationResult> consumer) {
    final String modelFullId = controller.getModelFullId();
    if (modelFullId != null) {
      final MultipleResult currentResult = (MultipleResult) currentPage;

      final JsonObject query = new JsonObject();
      if(masterLink) {
        String t = (String) ((AbstractViewController)controller).getOpData().getMasterAttributes().get("fullId");
        query.addProperty("oid", t);
      }
      else {
        query.addProperty("oid", modelFullId);
      }


      query.addProperty("linkClass", getLinkClass());
      if (StringUtils.isNotBlank(getLinkConstraintName())) {
        query.addProperty("linkConstraintName", getLinkConstraintName());
      }

      query.addProperty(IOperationResult.page, getNexPageIndex(direction, currentResult));
      query.addProperty(IOperationResult.pageSize, currentResult.getPageSize().toString());
      paginateOperation.doOperation(query, consumer);
    }
  }


  /**
   * Getter of linkClass
   *
   * @return the linkClass
   */
  @Override
  public String getLinkClass() {
    return linkClass;
  }


  /**
   * Setter of linkClass
   *
   * @param linkClass the linkClass to set
   */
  @Override
  public void setLinkClass(String linkClass) {
    this.linkClass = linkClass;
  }


  /**
   * Getter of linkConstraintName
   *
   * @return the linkConstraintName
   */
  @Override
  public String getLinkConstraintName() {
    return linkConstraintName;
  }


  /**
   * Setter of linkConstraintName
   *
   * @param linkConstraintName the linkConstraintName to set
   */
  @Override
  public void setLinkConstraintName(String linkConstraintName) {
    this.linkConstraintName = linkConstraintName;
  }


  @Override
  public String getLinkType() {
    return linkType;
  }


  public void setLinkType(String linkType) {
    this.linkType = linkType;
  }


  @Override
  public IOperationResult getTypeDynamicalAttributes() {
    return linkTypeDynamicalAttributes;
  }


  public void setLinkTypeDynamicalAttributes(IOperationResult linkTypeDynamicalAttributes) {
    this.linkTypeDynamicalAttributes = linkTypeDynamicalAttributes;
  }

  /**
   * @author Ramilafananana  VONJISOA
   */
  private class LoadAttributesDefinitionTask extends Task<IOperationResult> {

    private final String typeLogicalPath;


    public LoadAttributesDefinitionTask(String typeLogicalPath) {
      this.typeLogicalPath = typeLogicalPath;
    }


    @Override
    protected IOperationResult call() throws Exception {
      final MultipleResult m = new MultipleResult();
      final JsonObject query = new JsonObject();
      query.addProperty("typeLogicalPath", typeLogicalPath);
      getDynamicalAttributesDefinitionOperation.doOperation(query, res -> {
        final MultipleResult d = (MultipleResult) res;
        m.setData(d.getData());
        m.setMetaData(d.getMetaData());
      });

      return m;
    }
  }
}
