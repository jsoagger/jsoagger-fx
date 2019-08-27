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


import io.github.jsoagger.jfxcore.engine.client.apiimpl.DefaultActionsHandler;
import io.github.jsoagger.jfxcore.engine.client.apiimpl.UIDataValidationResult;
import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IIStepListenerHandler;
import io.github.jsoagger.jfxcore.api.IUIDataValidationResult;
import io.github.jsoagger.jfxcore.api.IUIDataValidatorHandler;
import io.github.jsoagger.jfxcore.api.UIDataValidationResultStatus;
import io.github.jsoagger.jfxcore.api.wizard.IWizardStep;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.main.WizardViewController;
import io.github.jsoagger.jfxcore.api.services.Services;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public abstract class WizardStepActionHandler extends DefaultActionsHandler {

  //private static final Logger //logR = LoggerFactory.getLogger(WizardStepActionHandler.class);

  private static final String AFTER_STEP_LISTENER = "afterStepListener";
  private static final String BEFORE_STEP_LISTENER = "beforeStepListener";
  private static final String DATA_VALIDATOR = "dataValidator";
  private static final String STEP_HANDLERS = "StepHandlers";


  /**
   * Get the component with identifier StepHandlers within this step wizardConfiguration.
   *
   * @return
   */
  protected VLViewComponentXML getStepHandlers(IActionRequest actionRequest) {
    WizardViewController controller = (WizardViewController) actionRequest.getController();
    IWizardStep step = controller.getCurrentStep();

    VLViewComponentXML stepConfig = step.getConfiguration();
    VLViewComponentXML stepHandlers = stepConfig.getComponentById(STEP_HANDLERS).orElse(null);
    return stepHandlers;
  }


  /**
   * Validates the wizard current step. If current is valid, call external validators.
   * <p>
   * All external validators are called (If current step is valid) whatever the status of previous
   * validation.
   *
   * @return
   */
  protected IUIDataValidationResult validate(IActionRequest actionRequest) {
    WizardViewController controller = (WizardViewController) actionRequest.getController();
    IUIDataValidationResult result = controller.validateCurrentStep();

    if ((result == null) || (result.getStatus() == UIDataValidationResultStatus.NOT_VALID)) {
      return result;
    }

    //logR.info("Begin execution of external validation");

    VLViewComponentXML stepHandlers = getStepHandlers(actionRequest);
    if (stepHandlers != null) {
      String dataValidatorId = stepHandlers.getPropertyValue(DATA_VALIDATOR);
      if (StringUtils.isNotBlank(dataValidatorId)) {

        //logR.debug("Data validator identifier is : " + dataValidatorId);
        //logR.debug("Begin of data validation");

        IUIDataValidatorHandler dataValidator = (IUIDataValidatorHandler) Services.getBean(dataValidatorId);
        result = dataValidator.validate(actionRequest);
        if ((result == null) || (result.getStatus() == UIDataValidationResultStatus.NOT_VALID)) {
          ((WizardViewController) actionRequest.getController()).handleValidationResult(actionRequest, result);
          return result;
        }

        //logR.debug("End of data validation");
      }

      else {
        //logR.info("No external data validation to execute");
      }
    }

    return UIDataValidationResult.success();
  }


  /**
   * Call and execute all step listener before.
   */
  protected void beforeStep(IActionRequest actionRequest) {
    //logR.info("Begin execution of beforeStep");

    VLViewComponentXML stepHandlers = getStepHandlers(actionRequest);
    if (stepHandlers != null) {
      String beforeStepListenerId = stepHandlers.getPropertyValue(BEFORE_STEP_LISTENER);
      if (StringUtils.isNotBlank(beforeStepListenerId)) {

        //logR.debug("Before step identifier : " + beforeStepListenerId);

        IIStepListenerHandler stepListenerHandler = (IIStepListenerHandler) Services.getBean(beforeStepListenerId);
        stepListenerHandler.execute(actionRequest);
      }
    }

    //logR.info("End execution of beforeStep");
  }


  /**
   * Call and execute all step listener after.
   */
  protected void afterStep(IActionRequest actionRequest) {
    //logR.info("Begin execution of afterStep");

    VLViewComponentXML stepHandlers = getStepHandlers(actionRequest);
    if (stepHandlers != null) {
      String afterStepListenerId = stepHandlers.getPropertyValue(AFTER_STEP_LISTENER);
      if (StringUtils.isNotBlank(afterStepListenerId)) {

        //logR.debug("After step identifier : " + afterStepListenerId);

        IIStepListenerHandler stepListenerHandler = (IIStepListenerHandler) Services.getBean(afterStepListenerId);
        stepListenerHandler.execute(actionRequest);
      }
    }
    //logR.info("End execution of afterStep");
  }
}
