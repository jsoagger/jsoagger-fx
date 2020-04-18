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

import com.google.gson.JsonObject;

import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.PushStructureContentEvent;
import io.github.jsoagger.jfxcore.engine.controller.utils.RootStructureUtils;
import javafx.application.Platform;
import javafx.concurrent.Task;

public class PushViewToSecondaryRSContentAction extends AbstractAction {

  public PushViewToSecondaryRSContentAction() {
    super();
  }

  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    try {
      StandardViewController controller = (StandardViewController) actionRequest.getController();
      String viewId = (String) actionRequest.getProperty("viewId");
      OperationData data = (OperationData) actionRequest.getProperty("sourceData");

      IOperation op = getOperation(actionRequest);
      if (op != null) {
        JsonObject query = new JsonObject();
        query.addProperty("oid", data.getAttributes().get("fullId").toString());
        op.doOperation(query, r -> {
          setData(((SingleResult) r).getData());

          Object rd = controller.getModel();
          OperationData rootdata = null;
          if (rd instanceof OperationData) {
            rootdata = (OperationData) rd;
          }

          RootStructureController rsc = RootStructureUtils.forId(viewId, rootdata);
          Platform.runLater(() -> {
            rsc.sourceRootStructure(controller.getRootStructure());
            controller.getRootStructure().setSecondaryRootStructureContent(rsc.processedView());
          });

          BuildRSContentTask task = new BuildRSContentTask(rsc, actionRequest);
          new Thread(task).start();
        }, ex -> {
          ex.printStackTrace();
        });
      } else {

        Object rd = controller.getModel();
        OperationData rootdata = null;
        if (rd instanceof OperationData) {
          rootdata = (OperationData) rd;
        }

        RootStructureController rsc = RootStructureUtils.forId(viewId, rootdata);
        Platform.runLater(() -> {
          rsc.sourceRootStructure(controller.getRootStructure());
          controller.getRootStructure().setSecondaryRootStructureContent(rsc.processedView());
        });

        BuildRSContentTask task = new BuildRSContentTask(rsc, actionRequest);
        new Thread(task).start();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void buildRSContent(RootStructureController rsc, IActionRequest actionRequest) {
    // Building the content of the RootStructureController
    String contentId = (String) actionRequest.getProperty("contentId");

    // @formatter:off
    PushStructureContentEvent ev =
        new PushStructureContentEvent.Builder().model(getData()).viewId(contentId).build();
    // @formatter:on

    ev.processedContentProperty().addListener((a, b, c) -> updateRSCHeader(rsc, c));
    rsc.dispatchEvent(ev);
  }

  /**
   * @author Ramilafananana VONJISOA
   */
  private class BuildRSContentTask extends Task<Void> {

    RootStructureController rsc;
    IActionRequest actionRequest;

    BuildRSContentTask(String rscuid, OperationData data, IActionRequest actionRequest) {

    }

    /**
     * @param rsc
     * @param actionRequest
     */
    BuildRSContentTask(RootStructureController rsc, IActionRequest actionRequest) {
      this.rsc = rsc;
      this.actionRequest = actionRequest;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Void call() throws Exception {
      buildRSContent(rsc, actionRequest);
      return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void failed() {
      super.failed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void running() {
      super.running();
    }
  }
}
