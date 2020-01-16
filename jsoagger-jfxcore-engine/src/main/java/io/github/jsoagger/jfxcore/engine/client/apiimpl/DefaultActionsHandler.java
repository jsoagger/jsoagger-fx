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

package io.github.jsoagger.jfxcore.engine.client.apiimpl;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.github.jsoagger.core.utils.Assert;
import io.github.jsoagger.jfxcore.api.ActionRequestStatus;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionHandler;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;

/**
 * Process list of {@link IAction}. Next action is called only and only if current action result is
 * success.
 * <p>
 * At The end, local event is dispatched in the local controller. If there is some message to
 * display to user, the controller will do it.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class DefaultActionsHandler implements IActionHandler {

  protected List<IAction> actions = new ArrayList<>();
  protected IActionResult result = ActionResult.success();
  protected int currentExecutionIndex = 0;
  protected IActionRequest actionRequest;

  protected SimpleObjectProperty<ActionRequestStatus> status = new SimpleObjectProperty<>(ActionRequestStatus.PROCESSING);


  /**
   * Constructor
   */
  public DefaultActionsHandler() {
    status.addListener((ChangeListener<ActionRequestStatus>) (observable, oldValue, newValue) -> {
      //  logR.info("Action processing status: " + newValue);
      if (newValue == ActionRequestStatus.DONE) {
        if (actionRequest != null) {
          actionRequest.getController().handleActionResult(actionRequest, result);
        }
      }
    });
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest) {
    Assert.notNull(actionRequest);
    Assert.notNull(actionRequest.getController());
    this.actionRequest = actionRequest;

    // logR.info("Begin processing actions : " + actions.size());
    processActions();
    //logR.info("End processing actions : " + actions.size());
  }


  protected void processActions() {
	  if(actions.size() > currentExecutionIndex) {
		  try {
			  IAction action = actions.get(currentExecutionIndex);
			    currentExecutionIndex++;

			    if(action.resultProperty() != null) {
			      action.resultProperty().addListener((ChangeListener<IActionResult>) (observable, oldValue, newValue) -> {
			        result = newValue;
			        if ((result != null) && result.isSuccess()) {
			          if (currentExecutionIndex < actions.size()) {
			            processActions();
			          }
			        }

			        if (result == null) {
			          result = ActionResult.error();
			        }

			        status.set(ActionRequestStatus.DONE);
			      });
			    }

			    action.execute(actionRequest, Optional.of(result));
			    //logR.debug("Processed action (OK): " + action.getId());
		  }
		  catch (Exception e) {
			  e.printStackTrace();
		  }
	  }
  }


  /**
   * Getter of actions
   *
   * @return the actions
   */
  @Override
  public List<IAction> getActions() {
    return actions;
  }


  /**
   * Setter of actions
   *
   * @param actions the actions to set
   */
  @Override
  public void setActions(List<IAction> actions) {
    this.actions = actions;
  }


  /**
   * Getter of result
   *
   * @return the result
   */
  public IActionResult getResult() {
    return result;
  }


  /**
   * Setter of result
   *
   * @param result the result to set
   */
  public void setResult(IActionResult result) {
    this.result = result;
  }

  /**
   * Getter of status
   *
   * @return the status
   */
  public ActionRequestStatus getStatus() {
    return status.get();
  }


  /**
   * Setter of status
   *
   * @param status the status to set
   */
  public void setStatus(ActionRequestStatus status) {
    this.status.set(status);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public SimpleObjectProperty<ActionRequestStatus> statusProperty() {
    return this.status;
  }
}
