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

import javafx.event.Event;
import javafx.scene.Node;

/**
 * Delete a link between two objects, use this action when the table hodling the roleA
 *  object is not recursive/navigating table.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 27 févr. 2018
 */
public class DeleteObjectLinkFromTableStructureRowAction extends AbstractAction implements IAction {

  FullTableViewController controller;


  /**
   * Default Constructor
   */
  public DeleteObjectLinkFromTableStructureRowAction() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    controller = (FullTableViewController) actionRequest.getController();
    Event event = (Event) actionRequest.getProperty("sourceEvent");
    Node source = (Node) event.getSource();
    OperationData roleB = (OperationData) actionRequest.getProperty("sourceData");

    AbstractTableStructure table = (AbstractTableStructure) controller.processedElement();

    if (data != null) {
      table.deleteItem(data);

      try {
        // the operation identifier
        String operationId = (String) actionRequest.getProperty("operation");
        IOperation deleteOperation = (IOperation) Services.getBean(operationId);

        // the link class to delete
        String linkClass = (String) actionRequest.getProperty("linkClass");

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
          table.getItems().add(roleB);
        });
      } catch (Exception e) {
        // CANCEL IT
        table.getItems().add(roleB);
      }
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getId() {
    return null;
  }
}
