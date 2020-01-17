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



import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.core.utils.StringUtils;
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
public class LoadPreviousItemChildrenAndUpdateTableAction extends AbstractAction {

  /**
   * Constructor
   */
  public LoadPreviousItemChildrenAndUpdateTableAction() {
    super();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    StandardViewController controller = (StandardViewController) actionRequest.getController();
    AbstractTableStructure ts = (AbstractTableStructure) controller.processedElement();
    OperationData data = ts.popChildrenTree();

    // means we are in top of the level so load the root one
    // if configured so, or reach the top level
    String relativeToRootModel = (String) actionRequest.getProperty("relativeToRootModel");
    if (data == null || (relativeToRootModel == null || "true".equalsIgnoreCase(relativeToRootModel))) {
      data = controller.getStructureContent().getFormModelData();
      ts.clearChildrenTree();
    }

    if (data != null) {
      String operation = (String) actionRequest.getProperty("operation");
      if (io.github.jsoagger.core.utils.StringUtils.isNotBlank(operation)) {
        IOperation op = (IOperation) Services.getBean(operation);
        JsonObject query = new JsonObject();
        query.addProperty("oid", data.getAttributes().get("fullId").toString());
        query.addProperty("fullId", data.getAttributes().get("fullId").toString());

        String linkClass = (String) actionRequest.getProperty("linkClass");
        if (StringUtils.isNotBlank(linkClass)) {
          query.addProperty("linkClass", linkClass);
        }

        op.doOperation(query, resp -> {
          ts.modelProperty().set(resp);
        }, ex -> {
          ex.printStackTrace();
        });
      }
      else {
        // set model to root model
        SingleResult res = new SingleResult();
        res.setData(data);
        controller.setModel(res);
        // and refresh data
        ts.refreshDatas();

        // and update centerpane
        controller.selectedElementProperty().set(data);
      }
    }
  }
}
