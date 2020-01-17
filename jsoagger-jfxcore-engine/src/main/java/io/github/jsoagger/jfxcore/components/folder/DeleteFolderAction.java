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
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.ActionResultStatus;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableStructureController;
import com.google.gson.JsonObject;

/**
 * Delete form a table row not from table header!!
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 21 févr. 2018
 */
public class DeleteFolderAction extends FolderAction implements IAction {

  private IOperation deleteFolderOperation;//  needs DeleteFolderOperation
  AbstractViewController ctrl = null;

  /**
   * @{inheritedDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    ctrl = (AbstractViewController) actionRequest.getController();
    if(ctrl instanceof FullTableStructureController) {
      parentController = (FullTableStructureController) ctrl;
    }

    OperationData sourceData = (OperationData) actionRequest.getProperty("sourceData");
    String containerFullId = parentController.getModelContainerFullId();
    String folderOid = (String) sourceData.getAttributes().get("fullId");

    if (StringUtils.isNotBlank(folderOid)) {
      JsonObject query = new JsonObject();
      query.addProperty("containerOid", containerFullId);
      query.addProperty("folderOid", folderOid);
      deleteFolderOperation.doOperation(query, this::createSuccess, this::onActionGeneralError);
    }
  }


  protected void createSuccess(IOperationResult operationResult) {
    if (!operationResult.hasBusinessError()) {
      resultProperty.set(ActionResult.success());
      NodeHelper.showHeaderSuccessDeleteMessage(ctrl);
      reloadChildren(operationResult);
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
    return "DeleteFolderAction";
  }


  /**
   * @return the deleteFolderOperation
   */
  public IOperation getDeleteFolderOperation() {
    return deleteFolderOperation;
  }


  /**
   * @param deleteFolderOperation the deleteFolderOperation to set
   */
  public void setDeleteFolderOperation(IOperation deleteFolderOperation) {
    this.deleteFolderOperation = deleteFolderOperation;
  }
}
