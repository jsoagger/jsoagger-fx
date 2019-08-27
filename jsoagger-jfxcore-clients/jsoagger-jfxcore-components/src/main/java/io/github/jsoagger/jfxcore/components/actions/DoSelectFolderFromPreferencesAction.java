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



import java.io.File;
import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.jfxcore.engine.client.apiimpl.ActionResult;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.engine.controller.main.layout.ViewStructure;
import com.google.gson.JsonObject;

import javafx.application.Platform;
import javafx.stage.DirectoryChooser;

public class DoSelectFolderFromPreferencesAction extends AbstractAction implements IAction {

  IOperation setPreferenceValueOperation;//  needs SetPreferenceValueOperation

  /**
   * Constuctor
   */
  public DoSelectFolderFromPreferencesAction() {}


  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {

    Platform.runLater(() -> {
      DirectoryChooser directoryChooser = new DirectoryChooser();
      File selectedDirectory = directoryChooser.showDialog(ViewStructure.primaryStage());

      if (selectedDirectory == null) {
      } else {


        // If no thing happens in IHM,
        // make sure developper has setted this operation
        // on its configuration
        if (setPreferenceValueOperation != null) {
          String prefKey = (String) actionRequest.getProperty("prefKey");
          String value = selectedDirectory.getAbsolutePath();

          JsonObject query = new JsonObject();
          query.addProperty("key", prefKey);
          query.addProperty("value", value);

          IAction action = (IAction) actionRequest.getProperty("actionObject");
          setPreferenceValueOperation.doOperation(query, res -> {
            action.resultProperty().set(ActionResult.success());
          });
        }
      }
    });
  }


  /**
   * @return the setPreferenceValueOperation
   */
  public IOperation getSetPreferenceValueOperation() {
    return setPreferenceValueOperation;
  }


  /**
   * @param setPreferenceValueOperation the setPreferenceValueOperation to set
   */
  public void setSetPreferenceValueOperation(IOperation setPreferenceValueOperation) {
    this.setPreferenceValueOperation = setPreferenceValueOperation;
  }
}
