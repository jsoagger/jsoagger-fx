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
import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;
import io.github.jsoagger.jfxcore.engine.events.DisplayMenuEvent;
import io.github.jsoagger.jfxcore.engine.events.MenuPos;

import javafx.scene.layout.StackPane;

/**
 * Action for collapse/expand primary menu.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ExpandTernaryMenuAction extends AbstractAction implements IAction {

  /**
   * Constructor
   */
  public ExpandTernaryMenuAction() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {

    DisplayMenuEvent displayMenuEvent = new DisplayMenuEvent(MenuPos.TERNARY_MENU);
    AbstractViewController controller = (AbstractViewController) actionRequest.getController();

    String ternaryMenuId = (String) actionRequest.getProperty("menuId");
    if (StringUtils.isNotBlank(ternaryMenuId)) {
      AbstractViewController ternaryMenu = StandardViewUtils.forId((RootStructureController)actionRequest.getController().getRootStructure(), null, ternaryMenuId);
      displayMenuEvent.setNode(ternaryMenu.processedView());
    } else {
      StackPane tp = new StackPane();
      tp.setStyle("-fx-background-color:white;-fx-min-width:400");
      displayMenuEvent.setNode(new StackPane());
    }

    controller.dispatchEvent(displayMenuEvent);
    resultProperty.set(ActionResult.success());
  }
}
