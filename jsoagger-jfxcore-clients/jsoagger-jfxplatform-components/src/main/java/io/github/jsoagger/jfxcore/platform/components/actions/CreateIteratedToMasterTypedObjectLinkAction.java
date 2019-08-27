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

package io.github.jsoagger.jfxcore.platform.components.actions;



import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.google.gson.JsonObject;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.components.actions.CreateTypedObjectLinkFromSearchAction;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.events.LinkCreatedEvent;
import io.github.jsoagger.jfxcore.platform.components.controller.StructureManagementController;

/**
 * Create an object link where the roleA is the current element holded by
 * forModelData of structure controller of controller.
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class CreateIteratedToMasterTypedObjectLinkAction extends CreateTypedObjectLinkFromSearchAction {



  /**
   * Constructor
   */
  public CreateIteratedToMasterTypedObjectLinkAction() {
    super();
  }


  @Override
  protected void fireEvent(IActionRequest actionRequest, OperationData item) {
    LinkCreatedEvent lke = new LinkCreatedEvent();
    lke.setModel(item);

    final StandardViewController scc = (StandardViewController) actionRequest.getController();
    AbstractViewController c = scc.getParent();

    OperationData roleA  = null;
    if(c instanceof StructureManagementController) {
      roleA = ((StructureManagementController)c).getLastChildTree();
    }

    lke.setRoleA(roleA);
    ((AbstractViewController)actionRequest.getController()).dispatchEvent(lke);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected String getRoleA(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    final StandardViewController scc = (StandardViewController) actionRequest.getController();
    AbstractViewController c = scc.getParent();

    OperationData roleA  = null;
    if(c instanceof StructureManagementController) {
      roleA = ((StructureManagementController)c).getLastChildTree();

      // in this case the user is navigating
      if(roleA != null) {
        Map result = new HashMap<>();

        JsonObject glatest = new JsonObject();
        glatest.addProperty("fullId", roleA.getAttributes().get("fullId").toString());
        glatest.addProperty("oid", roleA.getAttributes().get("fullId").toString());
        IOperation getLastest = (IOperation) Services.getBean("GetLatestVersionByMasterOidOperation");
        getLastest.doOperation(glatest, res -> {
          result.put("latest", ((SingleResult)res).getData().getAttributes().get("fullId"));
        });

        return (String) result.get("latest");
      }
    }

    // this is an iteration, the current backing model
    roleA = scc.getStructureContent().getFormModelData();
    return (String) roleA.getAttributes().get("fullId");
  }
}
