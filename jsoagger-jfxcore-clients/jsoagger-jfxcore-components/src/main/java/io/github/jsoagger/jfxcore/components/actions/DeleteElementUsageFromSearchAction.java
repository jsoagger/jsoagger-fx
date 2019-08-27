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
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableStructureController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.StructureContentController;
import com.google.gson.JsonObject;

public class DeleteElementUsageFromSearchAction extends AbstractAction {

  private IOperation deleteLinkOperation;// needs DeleteLinkOperation


  /**
   *
   */
  public DeleteElementUsageFromSearchAction() {}


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
    query.addProperty("linkClass", "io.github.jsoagger.core.model.part.ElementToElementMasterLink");

    deleteLinkOperation.doOperation(query, res -> {
      resultProperty().set(ActionResult.success());
      SingleResult r = (SingleResult) res;
      if (!r.hasBusinessError()) {
        ((FullTableStructureController) scc.getCurrentEditingTableStructure()).removeItem(roleB);
      }
    }, ex -> {
      ex.printStackTrace();
      resultProperty().set(ActionResult.error());
    });
  }
}
