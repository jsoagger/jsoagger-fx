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

package io.github.jsoagger.jfxcore.engine.controller.login.action;

import java.util.Optional;

import com.google.gson.JsonObject;

import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.jfxcore.api.ActionResultStatus;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.api.security.ILoginSessionHolder;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.jfxcore.engine.client.apiimpl.ActionResult;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.layout.ViewStructure;
import io.github.jsoagger.jfxcore.engine.controller.utils.WizardViewUtils;
import io.github.jsoagger.jfxcore.engine.events.LoginSuccessEvent;

/**
 * Login action needs spring bean LoginOperation to complete.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class LoginAction extends AbstractAction implements IAction {

  // needs LoginOperation
  private IOperation loginOperation;

  /**
   * @{inheritedDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    // no bean declared, runtime error!!
    if (loginOperation == null) {
      resultProperty.set(ActionResult.error());
    }

    this.controller = (AbstractViewController) actionRequest.getController();

    SingleResult sr = (SingleResult) actionRequest.getController().getModel();
    JsonObject model = new JsonObject();
    WizardViewUtils.copyAllAttributesFrom(sr, model);
    loginOperation.doOperation(model.toString(), this::handleResult);
  }

  /**
   * @param result
   */
  public synchronized void handleResult(IOperationResult result) {
    if (result.hasBusinessError()) {
      ActionResult actionresult = new ActionResult.ActionResultBuilder()
          .operationMessage(result.getMessages()).status(ActionResultStatus.ERROR).build();
      resultProperty.set(actionresult);
      NodeHelper.showHeaderErrorMessage(controller, "Invalid username or password");
    } else {

      // set root context of the view to
      // client must handle and manage session holder like LocalStorage
      // and manage session data inside it after success login
      ILoginSessionHolder loginContext =
          (ILoginSessionHolder) Services.getBean("LoginSessionHolder");
      loginContext.setSessionId((String) result.getMetaData().get("session_id"));

      // redirect to welcome view
      LoginSuccessEvent loginSuccessEvent = new LoginSuccessEvent();
      Services.dispatchEvent(loginSuccessEvent);
      ViewStructure.instance().listenTo(loginSuccessEvent);
      ActionResult actionresult = new ActionResult.ActionResultBuilder()
          .operationMessage(result.getMessages()).status(ActionResultStatus.SUCCESS).build();
      resultProperty.set(actionresult);
    }
  }

  /**
   * @return the loginOperation
   */
  public IOperation getLoginOperation() {
    return loginOperation;
  }

  /**
   * @param loginOperation the loginOperation to set
   */
  public void setLoginOperation(IOperation loginOperation) {
    this.loginOperation = loginOperation;
  }
}
