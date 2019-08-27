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
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;

import javafx.application.Platform;
import javafx.scene.Node;

public class PushViewToRSContentAction extends AbstractAction {

  /**
   * Constructor
   */
  public PushViewToRSContentAction() {
    super();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    StandardViewController controller = (StandardViewController) actionRequest.getController();
    AbstractViewController rscc = controller.getStructureContent().getCurrentContent();
    String viewId = (String) actionRequest.getProperty("viewId");

    String needRefreshBeforePop = (String) actionRequest.getProperty("needRefreshBeforePop");
    controller.setNeedRefreshBeforePop(Boolean.valueOf(needRefreshBeforePop));

    AbstractViewController qq = StandardViewUtils.forId(controller.getRootStructure(), controller.getStructureContent(), viewId, data);
    if (qq instanceof FullTableStructureController) {
      controller.getStructureContent().setCurrentEditingTableStructure(qq);
    }

    if (StringUtils.isNotBlank(viewId) && rscc != null) {
      if (Platform.isFxApplicationThread()) {
        _doPush(qq.processedView(), controller);
      } else {
        Platform.runLater(() -> {
          _doPush(qq.processedView(), controller);
        });
      }
    }
  }


  protected void _doPush(Node node, StandardViewController controller) {
    controller.getRootStructure().pushContent(controller, node);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {
    return "PushViewToRSContentAction";
  }
}
