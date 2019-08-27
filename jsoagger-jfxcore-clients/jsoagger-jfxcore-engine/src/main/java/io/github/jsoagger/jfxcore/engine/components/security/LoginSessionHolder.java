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

package io.github.jsoagger.jfxcore.engine.components.security;



import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.jfxcore.api.security.ILoginSessionHolder;
import io.github.jsoagger.jfxcore.api.security.RootContextMode;

/**
 * When user has logged in, a session id is sent to the server. This session id is unique along the
 * application. Singleton in spring context.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 23 janv. 2018
 */
public class LoginSessionHolder implements ILoginSessionHolder {

  private String sessionId;
  private IOperationResult loginResult;
  private RootContextMode mode = RootContextMode.Connected;


  /**
   * Default Constructor
   */
  public LoginSessionHolder() {}


  @Override
  public String getSessionId() {
    return sessionId;
  }


  @Override
  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }


  @Override
  public IOperationResult getLoginResult() {
    return loginResult;
  }


  @Override
  public void setLoginResult(IOperationResult loginResult) {
    this.loginResult = loginResult;
  }


  @Override
  public void logout() {
    this.sessionId = null;
  }

  @Override
  public RootContextMode getMode() {
    return mode;
  }


  @Override
  public void setMode(RootContextMode mode) {
    this.mode = mode;
  }
}
