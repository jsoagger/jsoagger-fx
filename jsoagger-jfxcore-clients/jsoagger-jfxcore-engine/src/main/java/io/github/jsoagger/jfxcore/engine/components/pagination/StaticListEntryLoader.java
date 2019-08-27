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



import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IPaginatedDataProvider;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;
/**
 * Transforms xml to {@link IOperationResult} for displaying into listview.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 31 janv. 2018
 */
public class StaticListEntryLoader implements IPaginatedDataProvider {

  private String dataKey;
  private VLViewComponentXML rootData;


  /**
   * Default Constructor
   */
  public StaticListEntryLoader() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void initFromConfiguration(IJSoaggerController controller, VLViewComponentXML listConfiguration) {
    dataKey = listConfiguration.getPropertyValue("dataKey");
    rootData = ComponentUtils.resolveModel((AbstractViewController) controller, dataKey);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void count(IJSoaggerController controller, IOperationResult currentPage, Consumer<IOperationResult> consumer) {
    IOperationResult operationResult = new SingleResult();

    if (rootData == null) {
      operationResult.addMetaData("totalElements", 0);
      consumer.accept(operationResult);
      return;
    }

    if (rootData.hasSubComponent()) {
      operationResult.addMetaData("totalElements", rootData.getSubcomponents().size());
      consumer.accept(operationResult);
      return;
    }

    operationResult.addMetaData("totalElements", 0);
    consumer.accept(operationResult);
    return;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void navigate(IJSoaggerController controller, IOperationResult currentResult, Direction direction, Consumer<IOperationResult> consumer) {
    MultipleResult multipleResult = new MultipleResult();
    List<OperationData> datas = new ArrayList<>();

    // admin is on context of the root strcuture
    SingleResult res = (SingleResult) controller.getRootStructure().getModel();

    for (VLViewComponentXML componentXML : rootData.getSubcomponents()) {
      OperationData data = new OperationData();
      data.getAttributes().put("fullId", ((OperationData) res.rootData()).getAttributes().get("fullId"));
      data.getAttributes().put("name", NodeHelper.getTitle(componentXML, (AbstractViewController) controller));

      for(String k: componentXML.getProperties().keySet()) {
        data.getAttributes().put(k, componentXML.getProperties().get(k));
      }

      // !hack!!
      if (componentXML.hasSubComponent()) {
        data.getMeta().put("subComponents", componentXML.getSubcomponents());
      }


      datas.add(data);
    }

    multipleResult.setData(datas);
    multipleResult.addMetaData("pageSize", rootData.getSubcomponents().size());
    multipleResult.addMetaData("pageNumber", 0);
    multipleResult.addMetaData("totalPages", 1);
    multipleResult.addMetaData("hasNextPage", false);
    multipleResult.addMetaData("hasPreviousPage", false);
    multipleResult.addMetaData("pageElements", rootData.getSubcomponents().size());
    multipleResult.addMetaData("totalElements", rootData.getSubcomponents().size());
    consumer.accept(multipleResult);
  }
}
