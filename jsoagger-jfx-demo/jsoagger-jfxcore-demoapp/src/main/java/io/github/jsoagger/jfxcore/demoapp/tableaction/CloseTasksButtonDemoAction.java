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

package io.github.jsoagger.jfxcore.demoapp.tableaction;



import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.engine.components.dialog.impl.OkCancelDialog;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.AbstractTableStructure;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.table.TableContent;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableViewController;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class CloseTasksButtonDemoAction extends AbstractAction implements IAction {

  FullTableViewController controller;


  /**
   * Default Constructor
   */
  public CloseTasksButtonDemoAction() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    controller = (FullTableViewController) actionRequest.getController();
    new OkCancelDialog.Builder()
    .title("Deliver")
    .message("Deliver task(s)?")
    .okCallBack(e -> onOk(e))
    .build(controller)
    .show();
  }


  public Object onOk(Object o) {
    for(OperationData d: controller.getTableStructure().getItems()) {
      String status = (String) d.getAttributes().get("status");
      if(!"delivered".equalsIgnoreCase(status)) {
        d.getAttributes().put("status","Delivered");
      }
    }

    AbstractTableStructure t = controller.getTableStructure();
    if(t instanceof TableContent) {
      ((TableContent)t).refreshTable();
    }

    NodeHelper.showHeaderMessage(controller, "Task(s) successfully delivered", "gmi-cloud-done:32");
    return null;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getId() {
    return "CloseTasksButtonDemoAction";
  }
}
