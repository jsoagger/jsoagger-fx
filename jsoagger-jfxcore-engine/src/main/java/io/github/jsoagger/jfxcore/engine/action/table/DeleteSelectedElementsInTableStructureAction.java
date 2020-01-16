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

package io.github.jsoagger.jfxcore.engine.action.table;



import java.util.List;
import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.jfxcore.engine.client.apiimpl.ActionResult;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.AbstractTableStructure;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.StructureContentController;
import com.google.gson.JsonObject;

import javafx.application.Platform;

/**
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 27 févr. 2018
 */
public class DeleteSelectedElementsInTableStructureAction extends AbstractAction implements IAction {

  FullTableViewController controller;


  /**
   * Default Constructor
   */
  public DeleteSelectedElementsInTableStructureAction() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    controller = (FullTableViewController) actionRequest.getController();
    AbstractTableStructure table = (AbstractTableStructure) controller.processedElement();

    List<OperationData> rows = table.getSelectedElements();

    // the link class to delete
    String linkClass = (String) actionRequest.getProperty("linkClass");
    for (OperationData roleB : rows) {
      try {
        // the operati identifier
        String operationId = (String) actionRequest.getProperty("operation");
        IOperation deleteOperation = (IOperation) Services.getBean(operationId);

        StructureContentController scc = ((AbstractViewController) actionRequest.getController()).getStructureContent();
        OperationData roleA = scc.getFormModelData();

        JsonObject query = new JsonObject();
        query.addProperty("roleA", roleA.getAttributes().get("fullId").toString());
        query.addProperty("roleB", roleB.getAttributes().get("fullId").toString());
        query.addProperty("linkClass", linkClass);

        deleteOperation.doOperation(query, res -> {
          resultProperty().set(ActionResult.success());
        }, ex -> {
          // CANCEL IT
          ex.printStackTrace();
        });
      } catch (Exception e) {
      }
    }

    Platform.runLater(() -> {
      table.deleteSelectedRows();
    });
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getId() {
    return null;
  }
}
