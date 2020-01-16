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

package io.github.jsoagger.jfxcore.engine.client.apiimpl;


import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.ActionResultStatus;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.api.IRSHeaderHolder;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.StructureContentController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.SetCurrentLocationEvent;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public abstract class AbstractAction implements IAction {

  protected SimpleObjectProperty<IActionResult> resultProperty = new SimpleObjectProperty<IActionResult>();

  protected String id;
  protected OperationData data;
  protected  AbstractViewController controller;


  /**
   * @{inheritedDoc}
   */
  @Override
  public SimpleObjectProperty<IActionResult> resultProperty() {
    return resultProperty;
  }


  /**
   * Constructor
   */
  public AbstractAction() {}


  /**
   * Getter of id
   *
   * @return the id
   */
  @Override
  public String getId() {
    return id;
  }


  /**
   * Setter of id
   *
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }


  /**
   * @param actionRequest
   * @return
   */
  public IOperation getOperation(IActionRequest actionRequest) {
    IOperation operation = null;
    final String operationId = (String) actionRequest.getProperty("operation");
    if (StringUtils.hasText(operationId)) {
      operation = (IOperation) Services.getBean(operationId);
    }

    return operation;
  }


  /**
   * @param actionRequest
   * @return
   */
  public boolean isForwardAndReplaceView(IActionRequest actionRequest) {
    boolean isForwardAndReplaceView = false;
    final String isForwardAndReplaceViewString = (String) actionRequest.getProperty("replace");
    isForwardAndReplaceView = StringUtils.hasText(isForwardAndReplaceViewString) && "true".equalsIgnoreCase(isForwardAndReplaceViewString);
    return isForwardAndReplaceView;
  }


  /**
   * Show general error message
   */
  public void onActionGeneralError(Throwable ex) {
    final ActionResult ar = new ActionResult.ActionResultBuilder()
        .message("Oups, your request can not be processed due to internal error.")
        .status(ActionResultStatus.ERROR).build();
    resultProperty.set(ar);
    NodeHelper.showHeaderErrorMessage(controller, "Oups, your request can not be processed due to internal error.");
  }


  /**
   * Getter of data
   *
   * @return the data
   */
  public OperationData getData() {
    return data;
  }


  /**
   * Setter of data
   *
   * @param data the data to set
   */
  @Override
  public void setData(OperationData data) {
    this.data = data;
  }

  /**
   * Default create success message handler.
   *
   * @param operationResult
   * @param controller
   */
  protected void createSuccess(IOperationResult operationResult) {
    if (operationResult != null && !operationResult.hasBusinessError()) {
      resultProperty.set(ActionResult.success());
      NodeHelper.showHeaderSuccessCreateMessage(controller);
    } else {
      ActionResult ar = new ActionResult
          .ActionResultBuilder()
          .operationMessage(operationResult != null ? operationResult.getMessages(): null)
          .status(ActionResultStatus.ERROR).build();
      resultProperty.set(ar);
      NodeHelper.showHeaderErrorCreateMessage(controller);
    }
  }

  /**
   * Update the header of the Root strcuture.
   *
   * @param rsc
   * @param currentContent
   */
  protected void updateRSCHeader(RootStructureController rsc, StructureContentController currentContent) {
    AbstractViewController c = currentContent.getCurrentContent();
    if (c instanceof IRSHeaderHolder) {
      Node ident = ((IRSHeaderHolder) c).getDisplayIdentity();
      if (ident != null) {
        ((IRSHeaderHolder) c).hideIdentity();
        Platform.runLater(() -> {
          SetCurrentLocationEvent scl = new SetCurrentLocationEvent(ident);
          scl.setController(c);
          rsc.dispatchEvent(scl);
        });
      }
    }
  }


  /**
   * @return the controller
   */
  public AbstractViewController getController() {
    return controller;
  }


  /**
   * @param controller the controller to set
   */
  public void setController(AbstractViewController controller) {
    this.controller = controller;
  }
}
