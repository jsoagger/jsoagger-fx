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
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.engine.components.dialog.impl.InformationDialog;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableViewController;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class PrintButtonDemoAction extends AbstractAction implements IAction {

  FullTableViewController controller;


  /**
   * Default Constructor
   */
  public PrintButtonDemoAction() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    controller = (FullTableViewController) actionRequest.getController();
    new InformationDialog.Builder().title("Print").message("Print function is not implemented").buildAccent(controller).show();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getId() {
    return "ExportButtonDemoAction";
  }
}
