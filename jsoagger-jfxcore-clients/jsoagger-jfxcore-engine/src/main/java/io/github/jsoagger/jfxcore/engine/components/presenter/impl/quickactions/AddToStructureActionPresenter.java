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
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.PushStructureContentEvent;

/**
 * Add current object to usages of the current part.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 12 févr. 2018
 */
public class AddToStructureActionPresenter extends ViewActionPresenter implements IBuildable {

  private String type = "button";
  private String toViewIdentifier = "";


  /**
   * Default Constructor
   */
  public AddToStructureActionPresenter() {}


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
    // the roleA is the current model of the rootstrcuture content
    OperationData roleA = this.controller.getStructureContent().getFormModelData();
    String modelFullId = (String) roleA.getAttributes().get("fullId");

    // roleB is current selected model in the table structure
    OperationData roleB = forModel;

    // buid the objectlink creation wizard
    if ((roleA != null) && (roleB != null) && StringUtils.isNotBlank(toViewIdentifier)) {
      PushStructureContentEvent ev = new PushStructureContentEvent.Builder().viewId(toViewIdentifier).model(roleA).modelFullId(modelFullId).build();
      ev.put("roleB", roleB);
      controller.dispatchEvent(ev);
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
