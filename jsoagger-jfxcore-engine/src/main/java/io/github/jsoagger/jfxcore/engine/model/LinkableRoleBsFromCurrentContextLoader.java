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

package io.github.jsoagger.jfxcore.engine.model;



import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IEnumeratedValueModel;
import io.github.jsoagger.jfxcore.api.IEnumeratedValuesLoader;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import com.google.gson.JsonObject;

/**
 * Load Linkable roleb from current context. The roleA is the context of the current controller.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 20 janv. 2018
 */
public class LinkableRoleBsFromCurrentContextLoader implements IEnumeratedValuesLoader {

  // needs GetLinkableRoleBsFromOperation 
  private IOperation getLinkableRoleBsFromOperation;
  private List<IEnumeratedValueModel> result = new ArrayList<>(1);


  /**
   * Constructor
   */
  public LinkableRoleBsFromCurrentContextLoader() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public List<IEnumeratedValueModel> loadValues(IJSoaggerController controller, VLViewComponentXML configuration) {
    JsonObject query = new JsonObject();

    // need to know from which context we want to link roleB
    query.addProperty("oid", ((RootStructureController) controller.getRootStructure()).getModelBusinessTypeFullId());

    // the type of link we want to create
    String linktype = configuration.getPropertyValue("linkTypeFullPath");
    query.addProperty("linkTypePath", linktype);

    // from roleA which is the current container of the root context!!! not
    // the model
    query.addProperty("roleAPath", ((RootStructureController) controller.getRootStructure()).getRootContext().getContainerPath());
    getLinkableRoleBsFromOperation.doOperation(query, this::onLinkableRoleBsLoaded, null);

    return result;
  }


  private void onLinkableRoleBsLoaded(IOperationResult operationResult) {
    MultipleResult multipleResult = (MultipleResult) operationResult;
    result.addAll(EnumeratedValueAdapter.toEnumeratedLinkableroleBValues(multipleResult));
  }
}
