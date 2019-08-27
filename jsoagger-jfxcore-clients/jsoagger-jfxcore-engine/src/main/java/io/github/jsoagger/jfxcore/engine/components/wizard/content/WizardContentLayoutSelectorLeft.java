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


import java.util.Iterator;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.wizard.IWizardContentLayout;
import io.github.jsoagger.jfxcore.api.wizard.IWizardStep;
import io.github.jsoagger.jfxcore.api.wizard.IWizardStepper;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.api.services.Services;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class WizardContentLayoutSelectorLeft extends WizardContentLayoutSelector implements IWizardContentLayout {

  protected VBox menu = new VBox();
  protected HBox layout = new HBox();


  /**
   * Constructor
   */
  public WizardContentLayoutSelectorLeft() {
    super();
    stepLeftWrapper.getChildren().add(menu);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);

    layout.getStyleClass().add("wizard-content-layout-left-wrapper");
    layout.getChildren().add(stepLeftWrapper);
    layout.getChildren().add(stepContentWrapper);
    NodeHelper.setHVGrow(stepContentWrapper);
    NodeHelper.styleClassAddAll(layout, configuration, "stepsWrapperStyleClass");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return layout;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void addStep(IWizardStep step) {
    IWizardStepper stepper = (IWizardStepper) Services.getBean("WizardVerticalStepper");
    stepper.setStep(step);
    steppers.add(stepper);
    menu.getChildren().add(stepper.getDisplay());

    Iterator<IWizardStepper> iterator = steppers.iterator();
    while (iterator.hasNext()) {
      IWizardStepper st = iterator.next();
      if (iterator.hasNext()) {
        st.hasNextProperty().set(true);
      }
    }
  }
}
