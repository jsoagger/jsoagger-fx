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
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.PushStructureContentEvent;

/**
 * This action is used to forward current view to parametrized view.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 24 févr. 2018
 */
public class ForwardToRSViewAction extends AbstractAction implements IAction {

  protected String forModel;
  protected AbstractViewController controller;
  protected String redirectToView;


  /**
   * Default Constructor
   */
  public ForwardToRSViewAction() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    this.controller = (AbstractViewController) actionRequest.getController();
    this.forModel = this.controller.getModelFullId();
    this.redirectToView = (String) actionRequest.getProperty("toView");
    doAction();
    resultProperty.set(ActionResult.success());
  }


  public void doAction() {
    PushStructureContentEvent ev = new PushStructureContentEvent.Builder().viewId(redirectToView).modelFullId(forModel).build();
    controller.dispatchEvent(ev);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getId() {
    return "ForwardToViewAction";
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
   * Getter of redirectToView
   *
   * @return the redirectToView
   */
  public String getRedirectToView() {
    return redirectToView;
  }


  /**
   * Setter of redirectToView
   *
   * @param redirectToView the redirectToView to set
   */
  public void setRedirectToView(String redirectToView) {
    this.redirectToView = redirectToView;
  }
}
