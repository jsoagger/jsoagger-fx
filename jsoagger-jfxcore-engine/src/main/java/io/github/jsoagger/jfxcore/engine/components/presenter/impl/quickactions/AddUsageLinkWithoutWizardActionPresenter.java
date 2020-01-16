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



import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.events.LinkCreatedEvent;
import io.github.jsoagger.jfxcore.engine.events.RefreshTableCurrentPageEvent;
import com.google.gson.JsonObject;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 12 févr. 2018
 */
public class AddUsageLinkWithoutWizardActionPresenter extends ViewActionPresenter implements IBuildable {

  // needs CreateTypedObjectLinkOperation
  private IOperation operation;


  /**
   * Default Constructor
   */
  public AddUsageLinkWithoutWizardActionPresenter() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);
  }


  /**
   * The associated model should be not null in order to process the action. It is the responsability
   * of the builder to initiate the model. In no model, the {@link OperationData} of current
   * controller will be used.
   */
  @Override
  public void doAction() {
    OperationData roleA = controller.getStructureContent().getFormModelData();
    OperationData roleB = this.forModel;

    JsonObject model = new JsonObject();
    model.addProperty("roleA", roleA.getAttributes().get("fullId").toString());
    model.addProperty("roleB", roleB.getAttributes().get("fullId").toString());

    model.addProperty("linkConstraintName", "partUsageLinkConstraint");
    model.addProperty("linkClass", "io.github.jsoagger.core.model.part.PartUsageLink");
    model.addProperty("linkType", "io.github.jsoagger.objectLink.PartLink/PartUsageLink");

    operation.doOperation(model, this::createSuccess, this::onActionGeneralError);
  }


  protected void createSuccess(IOperationResult operationResult) {
    if (!operationResult.hasBusinessError()) {
      RefreshTableCurrentPageEvent e = new RefreshTableCurrentPageEvent();
      controller.dispatchEvent(e);

      LinkCreatedEvent event = new LinkCreatedEvent();
      controller.dispatchEvent(event);
    }
  }


  public void onActionGeneralError(Throwable ex) {}
}
