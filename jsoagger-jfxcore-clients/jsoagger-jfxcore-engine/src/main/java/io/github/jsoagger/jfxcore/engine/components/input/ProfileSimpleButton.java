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

package io.github.jsoagger.jfxcore.engine.components.input;



import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import com.google.gson.JsonObject;

import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class ProfileSimpleButton extends SimpleButton {

  // needs GetCurrentUserOperation
  private IOperation getCurrentUserOperation;


  /**
   * Constructor
   */
  public ProfileSimpleButton() {
    super();
  }


  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);
    button.setText("");
    if (getCurrentUserOperation != null) {
      Task<Void> loadCurrentUser = new Task<Void>() {

        @Override
        protected Void call() throws Exception {
          getCurrentUserOperation.doOperation(new JsonObject(), res -> {
            Platform.runLater(() -> {
              button.setText((String) ((OperationData) res.rootData()).getAttributes().get("nickName"));
            });
          });

          return null;
        }


        @Override
        protected void failed() {
          super.failed();
          Platform.runLater(() -> {
            button.setText("--");
          });
        }


        @Override
        protected void setException(Throwable t) {
          super.setException(t);
          Platform.runLater(() -> {
            button.setText("--");
          });
        }
      };

      Thread t = new Thread(loadCurrentUser);
      t.setDaemon(true);
      // t.start();
    }
  }
}
