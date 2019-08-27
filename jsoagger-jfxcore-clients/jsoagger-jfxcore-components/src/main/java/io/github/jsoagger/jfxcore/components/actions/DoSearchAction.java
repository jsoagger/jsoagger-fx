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
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.StructureContentController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.PushStructureContentEvent;
import io.github.jsoagger.jfxcore.components.search.controller.SearchFormController;
import io.github.jsoagger.jfxcore.components.search.controller.SearchRootFormController;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 27 févr. 2018
 */
public class DoSearchAction extends AbstractAction implements IAction {

  /**
   * Constructor
   */
  public DoSearchAction() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    AbstractViewController c = (AbstractViewController) actionRequest.getController();

    if(c instanceof SearchFormController) {
      SearchFormController controller = (SearchFormController) actionRequest.getController();

      // COMMIT FORM
      /* controller.commitForm();
      JsonObject searchQuery = new JsonObject();
      JsonObject form = controller.getForm(true);

      for (Object att : form.keySet()) {
        if (att.toString().startsWith("data.attributes.search_")) {
          searchQuery.addProperty(StringUtils.substringAfter(att.toString(), "data.attributes.search_"), form.get(att.toString()).getAsString());
        } else {
          searchQuery.addProperty(att.toString(), form.get(att.toString()).getAsString());
        }
      }

      searchQuery.addProperty("modelSourceFullId", controller.getModelFullId());
      searchQuery.addProperty("containerFullId", controller.getModelContainerFullId());*/
      controller.getSearchRootFormController().getSearchController().doSearch();
    }
    else {
      if(c instanceof SearchRootFormController) {
        ((SearchRootFormController)c).getSearchController().doSearch();

        StructureContentController scc = (StructureContentController) Services.getBean("StructureContentController");
        scc.processedView(((SearchRootFormController)c).getSearchController().searchResult());
        PushStructureContentEvent ev = new PushStructureContentEvent.Builder().processedContent(scc).build();
        c.dispatchEvent(ev);
      }
    }



    resultProperty.set(ActionResult.success());
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getId() {
    return "DoSearchAction";
  }
}
