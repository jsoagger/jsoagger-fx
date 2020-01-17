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
import io.github.jsoagger.jfxcore.engine.client.apiimpl.ActionResult;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableStructureController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.StructureContentController;
import io.github.jsoagger.jfxcore.engine.events.LinkDeletedEvent;
import com.google.gson.JsonObject;

public class DeleteLinkFromSearchAction extends AbstractAction {

  private IOperation deleteLinkOperation;// needs DeleteLinkOperation


  /**
   * Constructor
   */
  public DeleteLinkFromSearchAction() {}


  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    StructureContentController scc = ((AbstractViewController) actionRequest.getController()).getStructureContent();

    JsonObject query = new JsonObject();
    query.addProperty("roleA", getRoleA(actionRequest, previousActionResult));
    query.addProperty("roleB", getRoleB(actionRequest, previousActionResult));

    String linkClass = (String) actionRequest.getProperty("linkClass");
    if (StringUtils.isBlank(linkClass)) {
      linkClass = ((AbstractViewController) actionRequest.getController()).getRootComponent().getPropertyValue("linkClass");
    }

    query.addProperty("linkClass", (String) actionRequest.getProperty("linkClass"));

    deleteLinkOperation.doOperation(query, res -> {
      resultProperty().set(ActionResult.success());
      SingleResult r = (SingleResult) res;
      if (!r.hasBusinessError()) {
        ((FullTableStructureController) scc.getCurrentEditingTableStructure()).removeItem(data);
        fireEvent(actionRequest, data   );
      }
    }, ex -> {
      ex.printStackTrace();
      resultProperty().set(ActionResult.error());
    });
  }


  /**
   * Get identifier of object which play roleA. By default it is the contextual object but in some other cases, it may
   * refer to another object like in master link or table structure navigation.
   *
   * @param actionRequest
   * @param previousActionResult
   * @return
   */
  protected String getRoleA(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    // the rolea is the contextual object
    StructureContentController scc = ((AbstractViewController) actionRequest.getController()).getStructureContent();
    OperationData roleA = scc.getFormModelData();

    return roleA.getAttributes().get("fullId").toString();
  }

  protected String getRoleB(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    return data.getAttributes().get("fullId").toString();
  }



  protected void fireEvent(IActionRequest actionRequest, OperationData item) {
    LinkDeletedEvent lke = new LinkDeletedEvent();
    lke.setModel(item);

    StructureContentController scc = ((AbstractViewController) actionRequest.getController()).getStructureContent();
    OperationData roleA = scc.getFormModelData();

    lke.setRoleA(roleA);
    ((AbstractViewController)actionRequest.getController()).dispatchEvent(lke);
  }


  /**
   * @return the deleteLinkOperation
   */
  public IOperation getDeleteLinkOperation() {
    return deleteLinkOperation;
  }


  /**
   * @param deleteLinkOperation the deleteLinkOperation to set
   */
  public void setDeleteLinkOperation(IOperation deleteLinkOperation) {
    this.deleteLinkOperation = deleteLinkOperation;
  }
}
