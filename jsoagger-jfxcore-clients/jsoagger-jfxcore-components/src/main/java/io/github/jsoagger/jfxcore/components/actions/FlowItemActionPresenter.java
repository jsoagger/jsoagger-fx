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



import io.github.jsoagger.jfxcore.engine.client.apiimpl.ActionRequest;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionHandler;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.quickactions.ViewActionPresenter;

import javafx.beans.value.ChangeListener;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 12 févr. 2018
 */
public class FlowItemActionPresenter extends ViewActionPresenter implements IBuildable {

  VLViewComponentXML actionConfig;


  /**
   * Default Constructor
   */
  public FlowItemActionPresenter() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    actionConfig = configuration.getComponentById("Handler").orElse(null);
    super.buildFrom(controller, configuration);
  }


  /**
   * The associated model should be not null in order to process the action. It is the responsability of the builder to initiate the model. In no model, the {@link OperationData} of current controller will be used.
   */
  @Override
  public void doAction() {
    if (actionConfig != null) {
      OperationData formModel = null;
      if (forModel == null) {
        IOperationResult or = (IOperationResult) controller.getModel();
        if (or != null) {
          formModel = (OperationData) or.rootData();
        }
      } else {
        formModel = forModel;
      }

      String actionName = actionConfig.getPropertyValue("action");
      String args = actionConfig.getPropertyValue("args");

      if (StringUtils.isNotBlank(actionName)) {
        IActionRequest actionRequest = new ActionRequest.Builder().controller(controller).args(args).event(null).build();
        actionRequest.setProperty("sourceEvent", null);
        if (forModel != null) {
          actionRequest.setProperty("sourceData", forModel);
        }

        Object handler = Services.getBean(actionName);

        // single action
        if (IAction.class.isAssignableFrom(handler.getClass())) {
          IAction action = (IAction) handler;
          action.setData(forModel);
          action.resultProperty().addListener((ChangeListener<IActionResult>) (observable, oldValue, newValue) -> {
            controller.handleActionResult(actionRequest, newValue);
          });
          action.execute(actionRequest, null);
        }

        // action handler
        else if (IActionHandler.class.isAssignableFrom(handler.getClass())) {
          IActionHandler actionHandler = (IActionHandler) handler;
          actionHandler.execute(actionRequest);
        }
      }
    }
  }
}
