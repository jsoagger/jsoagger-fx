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
import io.github.jsoagger.jfxcore.engine.events.DeleteObjectFromStructureEvent;
import com.google.gson.JsonObject;

import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.scene.Node;

/**
 * Delete a link between two objects, use this action when the table holding the roleA
 *  object is a recursive table, i.e, user is navigating through structure like BOM or Folder.
 *  The roleA is not the root object but the currently navigating item.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 27 févr. 2018
 */
public class DeleteObjectLinkFromRecursiveStructureTableRowAction extends AbstractAction implements IAction {

  FullTableViewController controller;


  /**
   * Default Constructor
   */
  public DeleteObjectLinkFromRecursiveStructureTableRowAction() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    controller = (FullTableViewController) actionRequest.getController();
    OperationData roleB = (OperationData) actionRequest.getProperty("sourceData");
    AbstractTableStructure table = (AbstractTableStructure) controller.processedElement();

    if (roleB != null) {
      table.deleteItem(roleB);
      Task<Void> deleteFromTable = new Task<Void>() {

        @Override
        protected Void call() throws Exception {
          fireDeleteObjectEvent(controller, roleB);
          return null;
        }
      };

      Task<Void> deleteFromRemote = new Task<Void>() {

        @Override
        protected Void call() throws Exception {
          processDeletion(actionRequest, previousActionResult);
          return null;
        }
      };

      new Thread(deleteFromTable).start();
      new Thread(deleteFromRemote).start();
    }
  }


  protected void fireDeleteObjectEvent(AbstractViewController sourceController, OperationData data) {
    DeleteObjectFromStructureEvent e = new DeleteObjectFromStructureEvent();
    e.setModel(data);
    e.setSourceController(sourceController);
    sourceController.dispatchEvent(e);
  }

  protected void processDeletion(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    OperationData roleB = (OperationData) actionRequest.getProperty("sourceData");
    Event event = (Event) actionRequest.getProperty("sourceEvent");
    Node source = (Node) event.getSource();
    AbstractTableStructure table = (AbstractTableStructure) controller.processedElement();

    try {
      // the operation identifier
      String operationId = (String) actionRequest.getProperty("operation");
      IOperation deleteOperation = (IOperation) Services.getBean(operationId);

      // the link class to delete
      String linkClass = (String) actionRequest.getProperty("linkClass");

      StructureContentController scc = ((AbstractViewController) actionRequest.getController()).getStructureContent();
      AbstractViewController c = scc.getParent();

      OperationData roleA  = null;
      //if(c instanceof StructureManagementController) {
      //roleA = ((StructureManagementController)c).getLastChildTree();
      //}

      if(roleA == null) {
        roleA = scc.getFormModelData();
      }

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
        resultProperty().set(ActionResult.error());
      });
    } catch (Exception e) {
      // CANCEL IT
      table.getItems().add(roleB);
      resultProperty().set(ActionResult.error());
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
