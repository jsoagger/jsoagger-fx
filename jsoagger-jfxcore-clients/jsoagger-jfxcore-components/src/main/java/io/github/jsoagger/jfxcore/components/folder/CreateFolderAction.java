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
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.ActionResultStatus;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.WizardViewController;
import com.google.gson.JsonObject;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 21 févr. 2018
 */
public class CreateFolderAction extends FolderAction implements IAction {

  private IOperation createFolderOperation;//  needs CreateFolderOperation


  /**
   * @{inheritedDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    controller = (WizardViewController) actionRequest.getController();
    parentController = (FullTableStructureController) controller.getParent();
    String containerFullId = (String) controller.relativeToProperty().get().getContainer().get("fullId");
    String folderOid = (String) controller.relativeToProperty().get().getAttributes().get("fullId");

    if (StringUtils.isNotBlank(folderOid)) {
      SingleResult model = (SingleResult) controller.getModel();
      String name = (String) model.getData().getAttributes().get("name");

      JsonObject query = new JsonObject();
      query.addProperty("name", name);
      query.addProperty("containerOid", containerFullId);
      query.addProperty("folderOid", folderOid);
      createFolderOperation.doOperation(query, this::createSuccess, this::onActionGeneralError);
    }
  }


  protected void createSuccess(IOperationResult operationResult) {
    reloadChildren(operationResult);
    if (!operationResult.hasBusinessError()) {
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
   * @return the createFolderOperation
   */
  public IOperation getCreateFolderOperation() {
    return createFolderOperation;
  }


  /**
   * @param createFolderOperation the createFolderOperation to set
   */
  public void setCreateFolderOperation(IOperation operation) {
    this.createFolderOperation = operation;
  }
}
