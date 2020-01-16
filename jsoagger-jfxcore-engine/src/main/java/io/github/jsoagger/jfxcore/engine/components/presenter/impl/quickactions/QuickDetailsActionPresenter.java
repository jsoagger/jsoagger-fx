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



import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.PushStructureContentEvent;


/**
 * Do an action from a flow item.
 * <p>
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 12 févr. 2018
 */
public class QuickDetailsActionPresenter extends ViewActionPresenter implements IBuildable {

  private String toViewIdentifier = "";


  /**
   * Default Constructor
   */
  public QuickDetailsActionPresenter() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.toViewIdentifier = configuration.getPropertyValue("pushToView");
    super.buildFrom(controller, configuration);
  }


  /**
   * The associated model should be not null in order to process the action. It is the responsability
   * of the builder to initiate the model. In no model, the {@link OperationData} of current
   * controller will be used.
   */
  @Override
  public void doAction() {
    OperationData formModel = null;
    if (this.forModel == null) {
      IOperationResult or = (IOperationResult) this.controller.getModel();
      if (or != null) {
        formModel = (OperationData) or.rootData();
      }
    } else {
      formModel = this.forModel;
    }

    if ((formModel != null) && StringUtils.isNotBlank(toViewIdentifier)) {
      String modelFullId = (String) formModel.getAttributes().get("fullId");
      PushStructureContentEvent ev = new PushStructureContentEvent.Builder().viewId(toViewIdentifier).model(formModel).modelFullId(modelFullId).build();
      controller.dispatchEvent(ev);
    }
  }


  /**
   * Getter of toViewIdentifier
   *
   * @return the toViewIdentifier
   */
  public String getToViewIdentifier() {
    return toViewIdentifier;
  }


  /**
   * Setter of toViewIdentifier
   *
   * @param toViewIdentifier the toViewIdentifier to set
   */
  public void setToViewIdentifier(String toViewIdentifier) {
    this.toViewIdentifier = toViewIdentifier;
  }
}
