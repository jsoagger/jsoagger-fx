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

package io.github.jsoagger.jfxcore.engine.components.presenter.impl.quickactions;



import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.engine.client.apiimpl.ActionRequest;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.list.impl.AbstractListCell;

/**
 * Executes an {@link IAction}
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 12 févr. 2018
 */
public class DoActionPresenter extends ViewActionPresenter implements IBuildable {

  private String action = "";
  private String args = "";

  private String viewOverride = null;


  /**
   * Default Constructor
   */
  public DoActionPresenter() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @SuppressWarnings("rawtypes")
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    VLViewComponentXML handler = configuration.getComponentById("Handler").orElse(null);
    if (handler != null) {
      action = handler.getPropertyValue("action");
      args = handler.getPropertyValue("args");
    }

    // inital configured view (viewId) can be overrided by declaring
    // toViewId in the item model.
    if (getForModel().getMeta().get("sourceNode") != null) {
      AbstractListCell cell = (AbstractListCell) getForModel().getMeta().get("sourceNode");
      OperationData data = (OperationData) cell.getItem();
      if (data != null) {
        viewOverride = (String) data.getAttributes().get("viewId");
      }
    }

    super.buildFrom(controller, configuration);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void doAction() {
    IAction action = (IAction) Services.getBean(this.action);
    IActionRequest actionRequest = new ActionRequest.Builder().args(args).controller(controller).build();
    actionRequest.setProperty("source", this);
    actionRequest.setProperty("sourceData", forModel);
    action.setData(forModel);

    if (StringUtils.isNotBlank(viewOverride)) {
      actionRequest.setProperty("viewId", viewOverride);
    }

    action.execute(actionRequest, null);
  }
}
