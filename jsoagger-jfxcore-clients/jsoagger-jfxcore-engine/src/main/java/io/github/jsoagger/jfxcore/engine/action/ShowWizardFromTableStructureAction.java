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
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.AbstractTableStructure;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.WizardViewController;
import io.github.jsoagger.jfxcore.engine.controller.utils.WizardViewUtils;

import javafx.application.Platform;

/**
 * When showing wizard form structure, the backing may change according to user navigation. Thats
 * why the object added/removed/modified is relative to a specified bean.
 * <p>
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ShowWizardFromTableStructureAction extends AbstractAction implements IAction {

  /**
   * Constructor
   */
  public ShowWizardFromTableStructureAction() {
    super();
  }


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
    FullTableStructureController controller = (FullTableStructureController) actionRequest.getController();
    AbstractTableStructure ts = (AbstractTableStructure) controller.processedElement();

    OperationData dt = null;
    if (ts.childTree().size() > 0) {
      dt = ts.childTree().get(ts.childTree().size() - 1);
    }
    // no going into structure, so relative to root data
    else {
      SingleResult sr = (SingleResult) controller.modelProperty().get();
      dt = (OperationData) sr.rootData();
    }

    String viewId = (String) actionRequest.getProperty("viewId");
    WizardViewController wizardViewController = WizardViewUtils.forWizardId(controller, controller.getStructureContent(), viewId, null);
    wizardViewController.setParent(controller);

    wizardViewController.relativeToProperty().set(dt);
    Platform.runLater(() -> wizardViewController.show());
    resultProperty.set(ActionResult.success());
  }
}
