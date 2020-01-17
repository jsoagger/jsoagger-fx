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

import com.google.gson.JsonObject;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.jfxcore.engine.client.apiimpl.ActionResult;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableStructureController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.StructureContentController;
import io.github.jsoagger.jfxcore.engine.events.LinkCreatedEvent;

/**
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class CreateObjectLinkFromSearchAction extends AbstractAction {

  private IOperation createLinkOperation;// needs CreateObjectLinkOperation


  /**
   * Constructor.
   */
  public CreateObjectLinkFromSearchAction() {
    super();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    // the roleb is the object selected by the user in the table/flow
    // originator of the action
    // the search results are part master
    OperationData roleB = data;

    // the rolea is the contextual object
    StructureContentController scc = ((AbstractViewController) actionRequest.getController()).getStructureContent();
    OperationData roleA = scc.getFormModelData();

    JsonObject query = new JsonObject();
    query.addProperty("roleA", roleA.getAttributes().get("fullId").toString());
    query.addProperty("roleB", roleB.getAttributes().get("fullId").toString());

    String linkClass = (String) actionRequest.getProperty("linkClass");
    if (StringUtils.isBlank(linkClass) && ((AbstractViewController) actionRequest.getController()).getParent() != null) {
      linkClass = ((AbstractViewController) actionRequest.getController()).getParent().getRootComponent().getPropertyValue("linkClass");
    }

    String containerFullId = ((AbstractViewController)actionRequest.getController()).getModelContainerFullId();
    query.addProperty("containerFullId", containerFullId);
    query.addProperty("linkClass", linkClass);

    createLinkOperation.doOperation(query, res -> {
      resultProperty().set(ActionResult.success());
      SingleResult r = (SingleResult) res;
      if (!r.hasBusinessError()) {
        r.getData();
        ((FullTableStructureController) scc.getCurrentEditingTableStructure()).addItem(r.getData());
        fireEvent(actionRequest, r.getData());
      }
    }, ex -> {
      ex.printStackTrace();
      resultProperty().set(ActionResult.error());
    });
  }

  protected void fireEvent(IActionRequest actionRequest, OperationData item) {
    LinkCreatedEvent lke = new LinkCreatedEvent();
    lke.setModel(item);

    StructureContentController scc = ((AbstractViewController) actionRequest.getController()).getStructureContent();
    OperationData roleA = scc.getFormModelData();

    lke.setRoleA(roleA);
    ((AbstractViewController)actionRequest.getController()).dispatchEvent(lke);
  }


  /**
   * @return the createLinkOperation
   */
  public IOperation getCreateLinkOperation() {
    return createLinkOperation;
  }


  /**
   * @param createLinkOperation the createLinkOperation to set
   */
  public void setCreateLinkOperation(IOperation createLinkOperation) {
    this.createLinkOperation = createLinkOperation;
  }
}
