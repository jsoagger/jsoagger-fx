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

package io.github.jsoagger.jfxcore.engine.controller.login.action;




import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.WizardViewController;
import io.github.jsoagger.jfxcore.engine.controller.utils.WizardViewUtils;
import com.google.gson.JsonObject;

import javafx.event.ActionEvent;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class TroubleSignAction {

  private static final String CORE_PLATFORM_LOGIN_RECOVERY_SUMMARY_VIEW = "corePlatformLoginRecoverySummaryView";


  /**
   * {@inheritDoc}
   */
  private void doAction(ActionEvent event, AbstractViewController controller) {
    // reuse form controller if login form
    JsonObject form = null;
    if (controller != null && controller.getModel() != null) {
      form = (JsonObject) controller.getModel();
    }

    RootStructureController structureController = controller.getRootStructure();
    // load recovery view
    WizardViewController abstractViewController = WizardViewUtils.forWizardId(structureController, controller.getStructureContent(), CORE_PLATFORM_LOGIN_RECOVERY_SUMMARY_VIEW, form);
    // structureController.layout(abstractViewController);
  }
}
