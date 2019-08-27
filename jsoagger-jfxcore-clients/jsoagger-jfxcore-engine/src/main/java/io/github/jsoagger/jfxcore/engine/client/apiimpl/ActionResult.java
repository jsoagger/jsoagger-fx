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

import io.github.jsoagger.core.bridge.result.OperationMessage;
import io.github.jsoagger.jfxcore.api.ActionResultStatus;
import io.github.jsoagger.jfxcore.api.IActionMessage;
import io.github.jsoagger.jfxcore.api.IActionResult;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ActionResult implements IActionResult {

  private ActionResultStatus status = ActionResultStatus.SUCCESS;
  private List<IActionMessage> actionMessages = new ArrayList<>();

  /**
   * Constructor
   */
  public ActionResult() {}


  /**
   * ActionResultStatus with {@link ActionResultStatus} success
   *
   * @return
   */
  public static IActionResult success() {
    IActionResult actionResult = new ActionResult();
    return actionResult;
  }


  /**
   * ActionResultStatus with {@link ActionResultStatus} success
   *
   * @return
   */
  public static IActionResult error() {
    IActionResult actionResult = new ActionResult();
    actionResult.setStatus(ActionResultStatus.ERROR);
    return actionResult;
  }


  /**
   * ActionResultStatus with {@link ActionResultStatus} WIZARD_GO_NEXT
   *
   * @return
   */
  public  static IActionResult wizardNext() {
    IActionResult actionResult = new ActionResult();
    actionResult.setStatus(ActionResultStatus.WIZARD_GO_NEXT);
    return actionResult;
  }


  /**
   * ActionResultStatus with {@link ActionResultStatus} WIZARD_GO_PREVIOUS
   *
   * @return
   */
  public static IActionResult wizardPrevious() {
    IActionResult actionResult = new ActionResult();
    actionResult.setStatus(ActionResultStatus.WIZARD_GO_PREVIOUS);
    return actionResult;
  }


  /**
   * Getter of status
   *
   * @return the status
   */
  @Override
  public ActionResultStatus getStatus() {
    return status;
  }


  /**
   * Setter of status
   *
   * @param status the status to set
   */
  @Override
  public void setStatus(ActionResultStatus status) {
    this.status = status;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public boolean isSuccess() {
    return status != ActionResultStatus.ERROR;
  }

  /**
   * @author Ramilafananana VONJISOA
   * @mailto yvonjisoa@nexitia.com
   * @date 2019
   */
  public static class ActionResultBuilder {

    private ActionResultStatus status;
    private List<IActionMessage> actionMessages = new ArrayList<>();


    public ActionResultBuilder status(ActionResultStatus status) {
      this.status = status;
      return this;
    }


    public ActionResultBuilder message(IActionMessage actionMessage) {
      this.actionMessages.add(actionMessage);
      return this;
    }


    public ActionResultBuilder message(String message) {
      ActionMessage msg = new ActionMessage.ActionMessageBuilder().body(message).build();
      this.actionMessages.add(msg);
      return this;
    }


    public ActionResultBuilder message(List<IActionMessage> actionMessages) {
      this.actionMessages.addAll(actionMessages);
      return this;
    }


    public ActionResultBuilder operationMessage(List<OperationMessage> opMessages) {
      try {
        if ((opMessages != null) && !opMessages.isEmpty()) {
          for(OperationMessage ms: opMessages) {
            ActionMessage am = new ActionMessage.ActionMessageBuilder().from(ms).build();
            this.actionMessages.add(am);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

      return this;
    }


    public ActionResult build() {
      return new ActionResult(this);
    }
  }


  private ActionResult(ActionResultBuilder builder) {
    this.status = builder.status;
    this.actionMessages = builder.actionMessages;
  }


  /**
   * Getter of actionMessages
   *
   * @return the actionMessages
   */
  @Override
  public List<IActionMessage> getActionMessages() {
    return actionMessages;
  }


  /**
   * Setter of actionMessages
   *
   * @param actionMessages the actionMessages to set
   */
  public void setActionMessages(List<IActionMessage> actionMessages) {
    this.actionMessages = actionMessages;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public boolean hasMessage() {
    return !actionMessages.isEmpty();
  }
}
