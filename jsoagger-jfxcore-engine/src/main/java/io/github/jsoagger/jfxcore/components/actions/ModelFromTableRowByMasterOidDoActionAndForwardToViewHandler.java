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

package io.github.jsoagger.jfxcore.components.actions;



import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.jfxcore.engine.client.apiimpl.ActionResult;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.engine.components.dialog.impl.ErrorDialog;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.PushStructureContentEvent;
import com.google.gson.JsonObject;

/**
 * Execute referenced action form table row model.
 * <p>
 * If the action is success, forward model to given view. The current view will still in list of
 * visited view. This means that if the user clicks to back action, the current view is diplayed.
 * <p>
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class ModelFromTableRowByMasterOidDoActionAndForwardToViewHandler extends AbstractAction implements IAction {

  protected AbstractViewController controller;
  protected String viewId;
  protected boolean replacePreviousView;


  /**
   * Default Constructor
   */
  public ModelFromTableRowByMasterOidDoActionAndForwardToViewHandler() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    this.controller = (AbstractViewController) actionRequest.getController();
    this.viewId = (String) actionRequest.getProperty("viewId");

    try {
      IOperation operation = getOperation(actionRequest);
      if (operation != null) {

        OperationData d = (OperationData) actionRequest.getProperty("sourceData");

        JsonObject query = new JsonObject();
        query.addProperty("oid", d.getAttributes().get("fullId").toString());
        operation.doOperation(query, this::handleResult, this::handleException);
      }
    } catch (Exception e) {
      e.printStackTrace();
      ErrorDialog d = new ErrorDialog.Builder().message("Error").title("Error").buildAccent(controller);
      d.show();
    }
  }

  protected void handleResult(IOperationResult r) {
    if (r.hasBusinessError()) {
      resultProperty.set(ActionResult.error());
      ErrorDialog d = new ErrorDialog.Builder().message(r.getMessages().get(0).getDetail()).title("Error").buildAccent(controller);
      d.show();
    } else {
      resultProperty.set(ActionResult.success());
      SingleResult sr = (SingleResult) r;
      forwardToViewAction(sr.getData());
    }
  }

  protected void handleException(Throwable ex) {
    resultProperty.set(ActionResult.error());
    ex.printStackTrace();

    ErrorDialog d = new ErrorDialog.Builder().message(ex.getMessage()).title("Error").buildAccent(controller);
    d.show();
  }


  public void forwardToViewAction(OperationData operationData) {
    PushStructureContentEvent ev = new PushStructureContentEvent
        .Builder()
        .viewId(viewId)
        .modelFullId((String) operationData.getAttributes().get("fullId"))
        .model(operationData)
        .replace(replacePreviousView)
        .build();
    controller.dispatchEvent(ev);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getId() {
    return "ModelByOidDoActionAndForwardToViewHandler";
  }


  /**
   * Getter of controller
   *
   * @return the controller
   */
  public AbstractViewController getController() {
    return controller;
  }


  /**
   * Setter of controller
   *
   * @param controller the controller to set
   */
  public void setController(AbstractViewController controller) {
    this.controller = controller;
  }


  /**
   * Getter of viewId
   *
   * @return the viewId
   */
  public String getRedirectToView() {
    return viewId;
  }


  /**
   * Setter of viewId
   *
   * @param viewId the viewId to set
   */
  public void setRedirectToView(String viewId) {
    this.viewId = viewId;
  }
}
