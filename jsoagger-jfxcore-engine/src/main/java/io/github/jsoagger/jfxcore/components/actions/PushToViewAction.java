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

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.api.IUIDataValidationResult;
import io.github.jsoagger.jfxcore.api.UIDataValidationResultStatus;
import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.jfxcore.engine.client.apiimpl.ActionResult;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.WizardViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.PushStructureContentEvent;


/**
 * Creates a {@link PushStructureContentEvent} and dispatch it through current controller.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 24 févr. 2018
 */
public class PushToViewAction extends AbstractAction implements IAction {

  protected OperationData sourceData;
  protected String forModel;
  protected AbstractViewController controller;
  protected String viewId;


  /**
   * Default Constructor
   */
  public PushToViewAction() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    controller = (AbstractViewController) actionRequest.getController();
    if(controller instanceof WizardViewController) {
    	IUIDataValidationResult result = ((WizardViewController) controller).validateCurrentStep();
    	if(result.getStatus() != UIDataValidationResultStatus.VALID) {
    		((WizardViewController) controller).getCurrentStep().displayErrors();
    		return;
    	}
    }


    forModel = controller.getModelFullId();
    viewId = getViewId(actionRequest, previousActionResult);

    sourceData = (OperationData) actionRequest.getProperty("sourceData");
    if (sourceData == null) {
      AbstractViewController c = (AbstractViewController) actionRequest.getController();
      sourceData = c.getOpData();
    }

    boolean replace = Boolean.valueOf(String.valueOf(actionRequest.getProperty("replace")));

    buildRSContent(replace);
    resultProperty.set(ActionResult.success());
  }


  public void buildRSContent(boolean replace) {
    // @formatter:off
    PushStructureContentEvent ev = new PushStructureContentEvent
        .Builder()
        .viewId(viewId)
        .modelFullId(forModel)
        .replace(replace)
        .model(sourceData).build();
    // @formatter:on

    ev.processedContentProperty().addListener((a, b, c) -> updateRSCHeader(controller.getRootStructure(), c));
    controller.dispatchEvent(ev);
  }

  /**
   * @param actionRequest
   * @param previousActionResult
   * @return
   */
  protected String getViewId(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    return (String) actionRequest.getProperty("viewId");
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public String getId() {
    return "PushStructureContentAction";
  }


  /**
   * Getter of forModel
   *
   * @return the forModel
   */
  public String getForModel() {
    return forModel;
  }


  /**
   * Setter of forModel
   *
   * @param forModel the forModel to set
   */
  public void setForModel(String forModel) {
    this.forModel = forModel;
  }


  /**
   * Getter of controller
   *
   * @return the controller
   */
  @Override
public AbstractViewController getController() {
    return controller;
  }


  /**
   * Setter of controller
   *
   * @param controller the controller to set
   */
  @Override
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
