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

package io.github.jsoagger.jfxcore.engine.components.wizard.content;


import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IUIDataValidationResult;
import io.github.jsoagger.jfxcore.api.wizard.IWizardContentLayout;
import io.github.jsoagger.jfxcore.api.wizard.IWizardStep;
import io.github.jsoagger.jfxcore.api.wizard.IWizardStepHeader;
import io.github.jsoagger.jfxcore.api.wizard.IWizardStepper;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public abstract class WizardContentLayoutSelector extends VBox implements IWizardContentLayout {

  protected List<IWizardStepper> steppers = new ArrayList<>();

  protected VBox stepContentWrapper = new VBox();
  protected StackPane stepTopWrapper = new StackPane();
  protected StackPane stepLeftWrapper = new StackPane();
  protected SimpleBooleanProperty showSteps = new SimpleBooleanProperty(true);

  protected WizardValidationMessageWrapper messageWrapper = new WizardValidationMessageWrapper();


  /**
   * Constructor
   */
  public WizardContentLayoutSelector() {
    getStyleClass().add("wizard-content-layout-selector");
    stepContentWrapper.getStyleClass().add("wizard-content-layout-content-wrapper");
    stepTopWrapper.getStyleClass().add("wizard-content-layout-top-menu-wrapper");
    stepLeftWrapper.getStyleClass().add("wizard-content-layout-left-menu-wrapper");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    stepTopWrapper.managedProperty().bind(stepTopWrapper.visibleProperty());
    stepLeftWrapper.managedProperty().bind(stepLeftWrapper.visibleProperty());
    showSteps.set(configuration.booleanPropertyValueOf("showSteps").orElse(true));

    stepLeftWrapper.visibleProperty().bind(showSteps);
    stepTopWrapper.visibleProperty().bind(showSteps);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void navTo(int i) {
    select(i);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void select(int index) {
    final IWizardStepper stepper = steppers.get(index);
    for(IWizardStepper node: steppers) {
      node.select(false);
    }

    IWizardStep step = stepper.getStep();
    IWizardStepHeader header = step.getStepHeader();
    if (header != null) {
      header.getTitle().setText(step.getTitle());
    }

    step.select();

    if (stepper != null) {
      stepper.select(true);
      Platform.runLater(() -> {
        stepContentWrapper.getChildren().clear();
        messageWrapper = new WizardValidationMessageWrapper();
        NodeHelper.setHVGrow(stepper.getStep().getDisplay());
        stepContentWrapper.getChildren().addAll(messageWrapper, stepper.getStep().getDisplay());
        NodeHelper.setHVGrow(stepper.getStep().getDisplay());
      });
    }
  }


  /**
   * Getter of steppers
   *
   * @return the steppers
   */
  @Override
  public List<IWizardStepper> getSteppers() {
    return steppers;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void handleValidationResult(IActionRequest actionRequest, IUIDataValidationResult result) {
    messageWrapper.handle(result);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void handleValidationResult(IActionRequest actionRequest, IActionResult actionResult) {
    messageWrapper.handle(actionResult);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public int stepSize() {
    return steppers.size();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public IWizardStep getStep(int index) {
    return steppers.get(index).getStep();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }
}
