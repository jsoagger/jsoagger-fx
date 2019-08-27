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

package io.github.jsoagger.jfxcore.engine.action;




import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.engine.components.dialog.impl.ErrorDialog;
import io.github.jsoagger.jfxcore.engine.components.dialog.impl.InformationDialog;
import io.github.jsoagger.jfxcore.engine.components.dialog.impl.OkCancelDialog;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.AbstractTableStructure;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableStructureController;
import com.google.gson.JsonObject;

/**
 * When showing wizard form structure, the backing may change according to user navigation. Thats
 * why the object added/removed/modified is relative to a specified bean.
 * <p>
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class DeleteIterationFromTableStructureAction extends AbstractAction implements IAction {

  FullTableStructureController controller;
  AbstractTableStructure ts;
  IOperation deleteOperation;

  /**
   * Constructor
   */
  public DeleteIterationFromTableStructureAction() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getId() {
    return null;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    controller = (FullTableStructureController) actionRequest.getController();
    ts = (AbstractTableStructure) controller.processedElement();

    if(!ts.getSelectedElements().isEmpty()) {
      new OkCancelDialog.Builder()
      .title("Delete")
      .message("Definitly delete this items?")
      .okCallBack(this::onOk)
      .buildPrimary(controller)
      .show();
    }
    else {
      new InformationDialog.Builder()
      .title("Delete")
      .message("No item selected.")
      .buildAccent(controller)
      .show(true);
    }
  }

  /**
   *
   * @param d
   * @return
   */
  protected Object onOk(Object d) {
    OperationData opd = ts.getSelectedElements().get(0);
    String fullId = (String) opd.getAttributes().get("fullId");

    JsonObject query = new JsonObject();
    query.addProperty("fullId", fullId);
    query.addProperty("oid", fullId);

    deleteOperation.doOperation(query, res -> {
      if(!res.hasBusinessError()) {
        ts.getSelectedElements().clear();
        controller.getTableStructure().getItems().remove(opd);

        new InformationDialog.Builder()
        .title("Deleted")
        .message("Item successfully deleted.")
        .buildAccent(controller).show(true);
      }
      else {
        new ErrorDialog.Builder()
        .title("Delete")
        .message("An error occurs, please try again")
        .build(controller).show(true);
      }
    });

    return null;
  }


  /**
   * @return the deleteOperation
   */
  public IOperation getDeleteOperation() {
    return deleteOperation;
  }


  /**
   * @param deleteOperation the deleteOperation to set
   */
  public void setDeleteOperation(IOperation deleteOperation) {
    this.deleteOperation = deleteOperation;
  }
}
