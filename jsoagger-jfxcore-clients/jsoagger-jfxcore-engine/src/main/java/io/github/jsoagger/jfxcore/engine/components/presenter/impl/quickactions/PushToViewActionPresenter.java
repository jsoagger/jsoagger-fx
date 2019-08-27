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
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.menu.MenuHelper;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.PushStructureContentEvent;

import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;

/**
 * Displays an {@link Hyperlink} or a {@link Button}. When actioned forward to configured view. A
 * {@link PushStructureContentEvent} is built and dispatched inside the rootstructure.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 12 févr. 2018
 */
public class PushToViewActionPresenter extends ViewActionPresenter implements IBuildable {

  private String type = "button";
  private String pushToView = "";
  private String updateRSContentTo = "";


  /**
   * Default Constructor
   */
  public PushToViewActionPresenter() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    pushToView = configuration.getPropertyValue("pushToView");
    updateRSContentTo = configuration.getPropertyValue("updateRSContentTo");
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
    if (forModel == null) {
      IOperationResult or = (IOperationResult) controller.getModel();
      if (or != null) {
        formModel = (OperationData) or.rootData();
      }
    } else {
      formModel = forModel;
    }

    if (formModel != null) {
      // the view id is the one of following
      // 1. toModelDefinedLink value from model
      // or 2. toViewIdentifier defined in the XML
      String modelFullId = (String) formModel.getAttributes().get("fullId");

      if (StringUtils.isNotBlank(pushToView)) {
        PushStructureContentEvent ev = new PushStructureContentEvent.Builder().viewId(StringUtils.isNotBlank(redirectToViewKey) ? (String) formModel.getLinks().get(redirectToViewKey) : pushToView)
            .model(formModel).modelFullId(modelFullId).build();
        controller.dispatchEvent(ev);
      } else if (StringUtils.isNotBlank(updateRSContentTo)) {
        controller.getStructureContent().setFormModelData(formModel);
        MenuHelper.loadViewContent("", updateRSContentTo, controller.getRootStructure(), false);
      }
    }
  }


  /**
   * Getter of type
   *
   * @return the type
   */
  @Override
  public String getType() {
    return type;
  }


  /**
   * Setter of type
   *
   * @param type the type to set
   */
  @Override
  public void setType(String type) {
    this.type = type;
  }


  /**
   * Getter of toViewIdentifier
   *
   * @return the toViewIdentifier
   */
  public String getToViewIdentifier() {
    return pushToView;
  }


  /**
   * Setter of toViewIdentifier
   *
   * @param toViewIdentifier the toViewIdentifier to set
   */
  public void setToViewIdentifier(String toViewIdentifier) {
    pushToView = toViewIdentifier;
  }


  /**
   * Getter of forModel
   *
   * @return the forModel
   */
  @Override
  public OperationData getForModel() {
    return forModel;
  }


  /**
   * Setter of forModel
   *
   * @param forModel the forModel to set
   */
  @Override
  public void setForModel(OperationData forModel) {
    this.forModel = forModel;
  }
}
