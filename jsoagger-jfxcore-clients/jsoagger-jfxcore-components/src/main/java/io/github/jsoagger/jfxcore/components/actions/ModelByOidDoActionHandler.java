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
import com.google.gson.JsonObject;

/**
 * Do an action on the controller model and update the model.
 * <p>
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class ModelByOidDoActionHandler extends AbstractAction implements IAction {

  protected AbstractViewController controller;


  /**
   * Default Constructor
   */
  public ModelByOidDoActionHandler() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    this.controller = (AbstractViewController) actionRequest.getController();

    try {
      IOperation operation = (IOperation) actionRequest.getProperty("operation");
      JsonObject query = new JsonObject();
      query.addProperty("fullId", this.controller.getModelFullId());
      operation.doOperation(query, this::handleResult, this::handleException);
    } catch (Exception e) {
      e.printStackTrace();

      ErrorDialog d = new ErrorDialog.Builder().message(e.getMessage()).title("Error").buildAccent(controller);
      d.show();
    }
  }

  protected void handleResult(IOperationResult r) {
    if (r.hasBusinessError()) {
      resultProperty.set(ActionResult.error());
    } else {
      resultProperty.set(ActionResult.success());
      SingleResult sr = (SingleResult) r;
      reprocessCurrentView(sr.getData());
    }
  }

  protected void handleException(Throwable ex) {
    resultProperty.set(ActionResult.error());
    ex.printStackTrace();

    ErrorDialog d = new ErrorDialog.Builder().message(ex.getMessage()).title("Error").buildAccent(controller);
    d.show();
  }


  public void reprocessCurrentView(OperationData operationData) {
    SingleResult sr = (SingleResult) this.controller.getModel();
    sr.setData(operationData);
    // this.controller.build();
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
}
