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




import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.jfxcore.engine.client.apiimpl.ActionResult;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.WizardViewController;

/**
 * Close a wizard.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class CloseWizardAction extends AbstractAction implements IAction {

  /**
   * Constructor
   */
  public CloseWizardAction() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getId() {
    return null;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    AbstractViewController controller = (AbstractViewController) actionRequest.getController();

    if(previousActionResult.get().isSuccess()) {
      if(controller instanceof WizardViewController) {
        WizardViewController c = (WizardViewController) controller;
        c.hide();
      }
    }

    resultProperty.set(ActionResult.success());
  }
}
