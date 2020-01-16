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

package io.github.jsoagger.jfxcore.engine.components.menu;



import java.util.concurrent.ExecutionException;

import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.input.SimpleButton;
import com.google.gson.JsonObject;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Node;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class PrimaryMenuUserProfile extends SimpleButton {

  // needs GetCurrentUserOperation
  private IOperation getCurrentUserOperation;


  /**
   * Constructor
   */
  public PrimaryMenuUserProfile() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);

    if (getCurrentUserOperation != null) {
      button.setText("--");
      final LoadTask task = new LoadTask();
      task.setOnSucceeded(e -> {
        Platform.runLater(() -> {
          try {
            button.setText((String) ((OperationData) task.get().rootData()).getAttributes().get("nickName"));
          } catch (InterruptedException | ExecutionException e1) {
            e1.printStackTrace();
          }
        });
      });

      new Thread(task).start();
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return button;
  }

  /**
   * @author Ramilafananana  VONJISOA
   *
   */
  private class LoadTask extends Task<IOperationResult> {

    @Override
    protected IOperationResult call() throws Exception {
      final IOperationResult r = new SingleResult();
      getCurrentUserOperation.doOperation(new JsonObject(), res -> {
        r.setData(res.rootData());
      });

      return r;
    }

  }
}
