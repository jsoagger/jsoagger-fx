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

package io.github.jsoagger.jfxcore.engine.action.table;



import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.jfxcore.engine.client.apiimpl.ActionResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.FullTableStructureContentLayoutManager;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;

import javafx.scene.layout.StackPane;

/**
 * In {@link FullTableStructureContentLayoutManager} managed by {@link FullTableViewController}, the
 * table displayed in center can be be replaced by another view, example by clicking in row in the
 * table. This action build the associated view and call the controller to display it.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 27 févr. 2018
 */
public class PushViewToTableStructureAction extends AbstractAction implements IAction {

  /**
   * Default Constructor
   */
  public PushViewToTableStructureAction() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    FullTableViewController controller = (FullTableViewController) actionRequest.getController();

    OperationData model = controller.getSelectedModel();
    String viewId = (String) actionRequest.getProperty("toViewId");

    if (io.github.jsoagger.core.utils.StringUtils.isNotBlank(viewId)) {
      AbstractViewController qq = StandardViewUtils.forId(controller.getRootStructure(), controller.getStructureContent(), viewId);
      controller.pushContent(null, qq.processedView());
    } else {
      controller.pushContent(null, new StackPane());
    }

    resultProperty.set(ActionResult.success());
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getId() {
    return null;
  }
}
