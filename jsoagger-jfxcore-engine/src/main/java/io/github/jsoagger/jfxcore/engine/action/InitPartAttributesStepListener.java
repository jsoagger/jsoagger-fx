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

package io.github.jsoagger.jfxcore.engine.action;



import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.InjectableComponent;
import io.github.jsoagger.jfxcore.engine.components.input.InputText;
import io.github.jsoagger.jfxcore.engine.components.wizard.actionhandler.WizardInitializator;
import io.github.jsoagger.jfxcore.engine.controller.main.WizardViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.StructureContentController;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 26 févr. 2018
 */
public class InitPartAttributesStepListener implements WizardInitializator {

  /**
   * @{inheritedDoc}
   */
  @Override
  public void initialize(WizardViewController wizardViewController) {
    StructureContentController contentController = wizardViewController.getStructureContent();
    OperationData data = contentController.getFormModelData();
    String folder = (String) data.getAttributes().get("path");
    InjectableComponent comp = wizardViewController.getComponent("PartFolderAttribute");
    if (comp instanceof InputText) {
      ((InputText) comp).setText(folder);
    }
  }
}
