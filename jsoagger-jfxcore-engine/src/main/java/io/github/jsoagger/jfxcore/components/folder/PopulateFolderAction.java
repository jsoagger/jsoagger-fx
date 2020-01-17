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

package io.github.jsoagger.jfxcore.components.folder;


import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.apiimpl.ActionResult;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.ActionResultStatus;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.engine.components.tree.event.TreePopulatedFromTemplateEvent;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.WizardViewController;
import com.google.gson.JsonObject;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 21 févr. 2018
 */
public class PopulateFolderAction extends FolderAction implements IAction {

  private IOperation operation;// needs PopulateFolderOperation


  /**
   * @{inheritedDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    controller = (WizardViewController) actionRequest.getController();
    parentController = (FullTableStructureController) controller.getParent();

    String containerFullId = controller.getParent().getModelContainerFullId();
    String folderOid = (String) controller.relativeToProperty().get().getAttributes().get("fullId");

    OperationData op = controller.getOpData();
    String template = (String) op.getAttributes().get("template");

    JsonObject query = new JsonObject();
    query.addProperty("templateOid", template);
    query.addProperty("containerOid", containerFullId);
    query.addProperty("folderOid", folderOid);
    operation.doOperation(query, this::populateSuccess, this::onActionGeneralError);
  }


  protected void populateSuccess(IOperationResult operationResult) {
    reloadChildren(operationResult);
    if (!operationResult.hasBusinessError()) {
      TreePopulatedFromTemplateEvent ev = new TreePopulatedFromTemplateEvent();
      controller.dispatchEvent(ev);
      resultProperty.set(ActionResult.success());
    } else {
      ActionResult ar = new ActionResult.ActionResultBuilder().operationMessage(operationResult.getMessages()).status(ActionResultStatus.ERROR).build();
      resultProperty.set(ar);
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getId() {
    return null;
  }


  /**
   * @return the operation
   */
  public IOperation getOperation() {
    return operation;
  }


  /**
   * @param operation the operation to set
   */
  public void setOperation(IOperation operation) {
    this.operation = operation;
  }
}
