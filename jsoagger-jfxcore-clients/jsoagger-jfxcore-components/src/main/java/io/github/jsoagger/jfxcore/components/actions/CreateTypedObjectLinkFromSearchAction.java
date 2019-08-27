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
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableStructureController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.StructureContentController;
import io.github.jsoagger.jfxcore.engine.events.LinkCreatedEvent;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class CreateTypedObjectLinkFromSearchAction extends AbstractAction {

  private IOperation createLinkOperation;// needs CreateTypedObjectLinkOperation


  /**
   * Constructor
   */
  public CreateTypedObjectLinkFromSearchAction() {
    super();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    JsonObject query = new JsonObject();
    query.addProperty("roleA", getRoleA(actionRequest, previousActionResult));
    query.addProperty("roleB", getRoleB(actionRequest, previousActionResult));

    String linkClass = (String) actionRequest.getProperty("linkClass");
    if (StringUtils.isBlank(linkClass) && ((AbstractViewController) actionRequest.getController()).getParent() != null) {
      linkClass = ((AbstractViewController) actionRequest.getController()).getParent().getRootComponent().getPropertyValue("linkClass");
    }

    String linkConstraintName = (String) actionRequest.getProperty("linkConstraintName");
    if (StringUtils.isBlank(linkConstraintName) && ((AbstractViewController) actionRequest.getController()).getParent() != null) {
      linkConstraintName = ((AbstractViewController) actionRequest.getController()).getParent().getRootComponent().getPropertyValue("linkConstraintName");
    }

    String linkType = (String) actionRequest.getProperty("linkType");
    if (StringUtils.isBlank(linkType) && ((AbstractViewController) actionRequest.getController()).getParent() != null) {
      linkType = ((AbstractViewController) actionRequest.getController()).getParent().getRootComponent().getPropertyValue("linkType");
    }

    query.addProperty("linkType", linkType);
    query.addProperty("linkClass", linkClass);
    query.addProperty("linkConstraintName", linkConstraintName);

    StructureContentController scc = ((AbstractViewController) actionRequest.getController()).getStructureContent();

    createLinkOperation.doOperation(query, res -> {
      SingleResult r = (SingleResult) res;
      if (!r.hasBusinessError()) {
        ((FullTableStructureController) scc.getCurrentEditingTableStructure()).refreshDatas();
        resultProperty().set(ActionResult.success());
        //((FullTableStructureController) scc.getCurrentEditingTableStructure()).addItem(r.getData());
        fireEvent(actionRequest, r.getData());
      }
      else {
        String message = r._getFirstErrorMessage();
        if(StringUtils.isNotBlank(message)) {
          NodeHelper.showHeaderErrorMessage(actionRequest.getController(), message);
        }
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
