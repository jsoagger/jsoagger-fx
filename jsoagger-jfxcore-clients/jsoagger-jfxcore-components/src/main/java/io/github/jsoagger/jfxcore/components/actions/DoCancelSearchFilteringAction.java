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

package io.github.jsoagger.jfxcore.components.actions;



import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.engine.controller.main.DoubleHeaderRootStructureController;
import io.github.jsoagger.jfxcore.components.search.controller.SearchFormController;

import javafx.application.Platform;

public class DoCancelSearchFilteringAction extends AbstractAction {

  /**
   * Constructor
   */
  public DoCancelSearchFilteringAction() {
    super();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    SearchFormController controller = (SearchFormController) actionRequest.getController();
    if (Platform.isFxApplicationThread()) {
      _doPop(controller);
    } else {
      Platform.runLater(() -> _doPop(controller));
    }
  }


  protected void _doPop(SearchFormController controller) {
    if (controller.getRootStructure() instanceof DoubleHeaderRootStructureController) {
      ((DoubleHeaderRootStructureController) controller.getRootStructure()).closeSecondaryRSWrapper();
    }
  }
}
