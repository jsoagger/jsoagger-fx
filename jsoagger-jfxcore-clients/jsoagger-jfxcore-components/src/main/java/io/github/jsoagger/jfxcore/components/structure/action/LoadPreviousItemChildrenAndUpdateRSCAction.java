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

package io.github.jsoagger.jfxcore.components.structure.action;



import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.api.InjectableComponent;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.components.input.SimpleButton;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.AbstractTableStructure;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import com.google.gson.JsonObject;

import javafx.scene.control.ButtonBase;

/**
 * Loads the children of current item and update the table structure.
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class LoadPreviousItemChildrenAndUpdateRSCAction extends AbstractAction {

  /**
   * Constructor
   */
  public LoadPreviousItemChildrenAndUpdateRSCAction() {
    super();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    StandardViewController controller = (StandardViewController) actionRequest.getController();
    AbstractTableStructure ts = (AbstractTableStructure) controller.processedElement();
    OperationData data = ts.popChildrenTree();

    // means we are in top of the level so load the root one
    // if configured so, or reach the top level
    String relativeToRootModel = (String) actionRequest.getProperty("relativeToRootModel");
    if (data == null || (relativeToRootModel == null || "true".equalsIgnoreCase(relativeToRootModel))) {
      loadRootFolder(controller);
    }

    if (data != null) {
      String operation = (String) actionRequest.getProperty("operation");
      if (io.github.jsoagger.core.utils.StringUtils.isNotBlank(operation)) {
        IOperation op = (IOperation) Services.getBean(operation);
        JsonObject query = new JsonObject();
        query.addProperty("oid", data.getAttributes().get("fullId").toString());
        query.addProperty("fullId", data.getAttributes().get("fullId").toString());

        String linkClass = (String) actionRequest.getProperty("linkClass");
        if (StringUtils.isNotBlank(linkClass)) {
          query.addProperty("linkClass", linkClass);
        }

        OperationData finalData = data;

        op.doOperation(query, resp -> {
          ts.modelProperty().set(resp);
          controller.getStructureContentController().setFormModelData(finalData);
        }, ex -> {
          ex.printStackTrace();
        });
      }
    }
  }

  /**
   *
   */
  private void loadRootFolder(AbstractViewController controller) {
    InjectableComponent comp = controller.getComponent("GoHomeButton");
    if(comp != null && comp instanceof SimpleButton) {
      ButtonBase bb = (ButtonBase) ((SimpleButton)comp).getComponent();
      bb.fire();
    }
  }
}
