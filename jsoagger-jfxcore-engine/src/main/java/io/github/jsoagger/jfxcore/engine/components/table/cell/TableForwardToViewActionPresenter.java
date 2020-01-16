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

package io.github.jsoagger.jfxcore.engine.components.table.cell;



import java.util.Optional;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.engine.client.apiimpl.ActionRequest;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.CellPresenterImpl;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.api.services.Services;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 13 févr. 2018
 */
public class TableForwardToViewActionPresenter extends CellPresenterImpl {

  private Hyperlink action = new Hyperlink();


  /**
   * Default Constructor
   */
  public TableForwardToViewActionPresenter() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration, Object item) {
    action.addEventFilter(ActionEvent.ACTION, ev -> {
      final Optional<VLViewComponentXML> actionComponent = configuration.getComponentById("Handler");
      actionComponent.ifPresent(e -> setButtonActions((AbstractViewController) controller, e, item));
    });

    return action;
  }


  private Object setButtonActions(AbstractViewController controller, VLViewComponentXML configuration, Object model) {
    String actionName = configuration.getPropertyValue("action");
    String args = configuration.getPropertyValue("args");
    if (StringUtils.isNotBlank(actionName)) {
      IActionRequest actionRequest = new ActionRequest.Builder().controller(controller).args(args).event(null).build();

      Object handler = Services.getBean(actionName);

      // single action
      if (IAction.class.isAssignableFrom(handler.getClass())) {
        IAction action = (IAction) handler;
        action.setData((OperationData) model);
        action.execute(actionRequest, null);
      }
    }

    return null;
  }
}
