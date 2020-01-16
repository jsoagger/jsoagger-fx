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

package io.github.jsoagger.jfxcore.engine.components.wizard.actionhandler;



import io.github.jsoagger.jfxcore.engine.client.apiimpl.ActionResult;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.ActionRequestStatus;
import io.github.jsoagger.jfxcore.api.IActionHandler;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IUIDataValidationResult;
import io.github.jsoagger.jfxcore.api.UIDataValidationResultStatus;

import javafx.beans.value.ChangeListener;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class WizardStepFinishActionHandler extends WizardStepActionHandler implements IActionHandler {

  //private static final Logger logR = LoggerFactory.getLogger(WizardStepFinishActionHandler.class);


  /**
   * Constructor
   */
  public WizardStepFinishActionHandler() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest) {
    this.actionRequest = actionRequest;
    // call all validators of the step validator
    IUIDataValidationResult dataValidationResult = validate(actionRequest);
    if (dataValidationResult.getStatus().equals(UIDataValidationResultStatus.VALID)) {
      status.addListener((ChangeListener<ActionRequestStatus>) (observable, oldValue, newValue) -> {
        if (getResult().isSuccess()) {
          // WizardViewController controller = (WizardViewController)
          // actionRequest.getController();
          // controller.hide();
          afterStep(actionRequest);
        }
      });

      try {
        super.execute(actionRequest);
      }catch (Exception e) {
        e.printStackTrace();
      }
    }

    // ERROR
    else {
      result = ActionResult.error();
      status.set(ActionRequestStatus.DONE);
      NodeHelper.showHeaderMessage(actionRequest.getController(), "Validation error", "gmi-error-outline:32");
    }
  }
}
