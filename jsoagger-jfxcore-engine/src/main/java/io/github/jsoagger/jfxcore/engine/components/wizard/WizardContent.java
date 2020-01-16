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

package io.github.jsoagger.jfxcore.engine.components.wizard;



import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IUIDataValidationResult;
import io.github.jsoagger.jfxcore.api.wizard.IWizardContent;
import io.github.jsoagger.jfxcore.api.wizard.IWizardContentLayout;
import io.github.jsoagger.jfxcore.api.wizard.IWizardStep;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.api.services.Services;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;

/**
 * Default implementation of {@link IWizardContent}
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class WizardContent implements IWizardContent {

  //private static final Logger logR = LoggerFactory.getLogger(WizardContent.class);

  /*-----------------------------------------------------------------------------
  | Private fields
   *=============================================================================*/
  protected AbstractViewController controller;
  protected VLViewComponentXML configuration;

  private IWizardContentLayout layoutManager;

  protected IntegerProperty currentStep = new SimpleIntegerProperty(1);
  protected SimpleBooleanProperty hasNextProperty = new SimpleBooleanProperty();
  protected SimpleBooleanProperty hasPreviousProperty = new SimpleBooleanProperty();


  /*-----------------------------------------------------------------------------
  | Constructor
   *=============================================================================*/
  /**
   * Constructor
   */
  public WizardContent() {

  }


  /*-----------------------------------------------------------------------------
  | Public methods
   *=============================================================================*/
  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML wizardStepsConfig) {
    this.configuration = wizardStepsConfig;
    this.controller = (AbstractViewController) controller;
    int pageIndex = 1;

    // load layout manager
    String layoutManagerName = wizardStepsConfig.getPropertyValue("layoutManagerImpl");
    if (StringUtils.isBlank(layoutManagerName)) {
      layoutManagerName = "WizardContentLayoutSelectorTop";
    }
    layoutManager = (IWizardContentLayout) Services.getBean(layoutManagerName);
    layoutManager.buildFrom(controller, wizardStepsConfig);

    // load all steps
    String stepImpl = wizardStepsConfig.getPropertyValue("stepImpl");
    if (StringUtils.isEmpty(stepImpl)) {
      stepImpl = "WizardStep";
    }

    // for Each step
    for (final VLViewComponentXML stepConfig : wizardStepsConfig.getSubcomponents()) {
      IWizardStep step = (IWizardStep) Services.getBean(stepImpl);
      step.buildFrom(controller, stepConfig);
      step.setIndex(pageIndex);
      layoutManager.addStep(step);
      pageIndex++;
    }

    // displays the first step
    layoutManager.navTo(0);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public int getStepsSize() {
    return layoutManager.stepSize();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void select(int index) {
    layoutManager.select(index);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void navTo(int index) {
    select(index);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setValid(int index) {
    layoutManager.setValid(index);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setError(int index) {
    layoutManager.setError(index);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public IWizardStep getStep(int nextStepIdx) {
    return layoutManager.getStep(nextStepIdx);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return layoutManager.getDisplay();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void next() {
    int current = currentStep.get();
    if (current < layoutManager.stepSize()) {
      navTo(current + 1);
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void back() {
    int current = currentStep.get();
    if (current > 1) {
      navTo(current - 1);
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public SimpleBooleanProperty hasNextProperty() {
    return null;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public SimpleBooleanProperty hasPreviousProperty() {
    return null;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void handleValidationResult(IActionRequest actionRequest, IUIDataValidationResult result) {
    layoutManager.handleValidationResult(actionRequest, result);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void handleValidationResult(IActionRequest actionRequest, IActionResult actionResult) {
    layoutManager.handleValidationResult(actionRequest, actionResult);
  }
}
