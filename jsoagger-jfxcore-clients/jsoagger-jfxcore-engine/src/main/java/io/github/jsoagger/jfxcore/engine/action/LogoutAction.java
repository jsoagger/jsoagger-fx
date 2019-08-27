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

package io.github.jsoagger.jfxcore.engine.action;


import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.jfxcore.engine.client.apiimpl.ActionResult;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.api.security.ILoginSessionHolder;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.controller.main.layout.ViewStructure;
import io.github.jsoagger.jfxcore.engine.events.LogoutSuccessEvent;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class LogoutAction extends AbstractAction implements IAction {

  // needs LogoutOperation
  private IOperation logoutOperation;


  /**
   * Constructor
   */
  public LogoutAction() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {

    // if there is remote logoutOperation, do logout
    // in fact it is not important in client view
    if (logoutOperation != null) {
      try {
        logoutOperation.doOperation("", null, null);
      } catch (Exception e) {
      }
    }

    // Clean session data
    ILoginSessionHolder rootContext = (ILoginSessionHolder) Services.getBean("LoginSessionHolder");
    rootContext.logout();

    // redirect to login view after cleaning session
    LogoutSuccessEvent logoutSuccessEvent = new LogoutSuccessEvent();
    Services.dispatchEvent(logoutSuccessEvent);
    ViewStructure.instance().lougoutSuccess(logoutSuccessEvent);

    resultProperty.set(ActionResult.success());
  }


  /**
   * @return the logoutOperation
   */
  public IOperation getLogoutOperation() {
    return logoutOperation;
  }


  /**
   * @param logoutOperation the logoutOperation to set
   */
  public void setLogoutOperation(IOperation logoutOperation) {
    this.logoutOperation = logoutOperation;
  }
}
