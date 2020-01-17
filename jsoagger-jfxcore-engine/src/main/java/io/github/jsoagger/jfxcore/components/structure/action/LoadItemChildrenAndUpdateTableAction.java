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

package io.github.jsoagger.jfxcore.components.structure.action;



import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.AbstractTableStructure;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import com.google.gson.JsonObject;

/**
 * Loads the children of current item and update the table structure.
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class LoadItemChildrenAndUpdateTableAction extends AbstractAction {

  /**
   * Constructor
   */
  public LoadItemChildrenAndUpdateTableAction() {
    super();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    StandardViewController controller = (StandardViewController) actionRequest.getController();
    OperationData data = getRoleA(actionRequest, previousActionResult);

    String operation = (String) actionRequest.getProperty("operation");
    if (io.github.jsoagger.core.utils.StringUtils.isNotBlank(operation)) {
      IOperation op = (IOperation) Services.getBean(operation);
      JsonObject query = new JsonObject();
      query.addProperty("oid", data.getAttributes().get("fullId").toString());
      query.addProperty("fullId", data.getAttributes().get("fullId").toString());

      if(actionRequest.getProperty("linkClass") != null)
        query.addProperty("linkClass", actionRequest.getProperty("linkClass").toString());

      op.doOperation(query, resp -> {
        AbstractTableStructure ts = (AbstractTableStructure) controller.processedElement();
        ts.pushChildrenTree(data);
        ts.modelProperty().set(resp);
        //controller.selectedElementProperty().set(data);
      }, ex -> {
        ex.printStackTrace();
      });
    }
    else {
      // table should load data relative to
      // selected element or last item in the children tree data
      AbstractTableStructure ts = (AbstractTableStructure) controller.processedElement();
      ts.pushChildrenTree(data);
      ts.refreshDatas();
      controller.selectedElementProperty().set(data);
    }
  }

  /**
   * Role A may be another object that the one displayed in view.
   * When navigating structure or filtered data for example, we can query data
   * relative to latest master version or latest released version or something else.
   * Default is latest
   *
   * @param actionRequest
   * @param previousActionResult
   * @return
   */
  protected OperationData getRoleADef(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    OperationData data = (OperationData) actionRequest.getProperty("sourceData");
    return data;
  }

  /**
   * Role A may be another object that the one displayed in view.
   * When navigating structure or filtered data for example, we can query data
   * relative to latest master version or latest released version or something else.
   * Default is latest
   *
   * @param actionRequest
   * @param previousActionResult
   * @return
   */
  protected OperationData getRoleA(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    StandardViewController controller = (StandardViewController) actionRequest.getController();
    OperationData data = (OperationData) actionRequest.getProperty("sourceData");

    Map result = new HashMap<>();

    JsonObject glatest = new JsonObject();
    glatest.addProperty("fullId", data.getAttributes().get("fullId").toString());
    glatest.addProperty("oid", data.getAttributes().get("fullId").toString());
    IOperation getLastest = (IOperation) Services.getBean("GetLatestVersionByMasterOidOperation");
    getLastest.doOperation(glatest, res -> {
      result.put("latest", ((SingleResult)res).getData());
    });

    return (OperationData) result.get("latest");
  }
}
