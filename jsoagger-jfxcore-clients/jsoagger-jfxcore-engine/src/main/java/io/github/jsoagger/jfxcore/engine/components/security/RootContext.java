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




import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.JsonUtils;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.security.ILoginSessionHolder;
import io.github.jsoagger.jfxcore.api.security.IRootContext;
import io.github.jsoagger.jfxcore.api.security.RootContextMode;
import io.github.jsoagger.jfxcore.api.services.Services;
import com.google.gson.JsonObject;

/**
 * Holds data related to current user like its account, container and session.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class RootContext implements IRootContext {

  // for update purpose
  private IOperation loadContainerOperation;
  private ILoginSessionHolder loginSessionHolder;

  private JsonObject account;
  private JsonObject user;
  private JsonObject container;

  private RootContextMode mode = RootContextMode.Connected;


  @Override
  public RootContextMode getMode() {
    return mode;
  }


  @Override
  public void setMode(RootContextMode mode) {
    this.mode = mode;
  }


  /**
   * Constructor
   */
  public RootContext() {}


  @Override
  public void init() {
    if ((loginSessionHolder != null) && (loginSessionHolder.getLoginResult() != null)) {
      final OperationData data = (OperationData) loginSessionHolder.getLoginResult().rootData();
      container = JsonUtils.toJsonObject((String)data.getLinks().get("container"));
      user = JsonUtils.toJsonObject((String)data.getLinks().get("user"));
      account = JsonUtils.toJsonObject((String)data.getLinks().get("account"));
      mode = loginSessionHolder.getMode() == null ? RootContextMode.Connected : loginSessionHolder.getMode();
    }
  }


  protected void updateModel() {
    if (loadContainerOperation == null) {
      loadContainerOperation = (IOperation) Services.getBean("LoadContainerByPathOperation");
    }
  }


  @Override
  public String getContainerPath() {
    return container.get("path").getAsString();
  }


  @Override
  public OperationData getContainer() {
    // clone curent data
    final OperationData operationData = new OperationData();
    final Map<String, Object> data = new HashMap<>();
    for (final Object key : container.keySet()) {
      data.put(new String(key.toString()), container.get(key.toString()).getAsString());
    }
    operationData.setAttributes(data);
    return operationData;
  }


  @Override
  public OperationData getSubject() {
    // clone curent data
    final OperationData operationData = new OperationData();
    final Map<String, Object> data = new HashMap<>();
    for (final Object key : user.keySet()) {
      data.put(new String(key.toString()), user.get(key.toString()).getAsString());
    }
    operationData.setAttributes(data);
    return operationData;
  }


  @Override
  public OperationData getAccount() {
    // clone curent data
    final OperationData operationData = new OperationData();
    final Map<String, Object> data = new HashMap<>();
    for (final Object key : account.keySet()) {
      data.put(new String(key.toString()), account.get(key.toString()).getAsString());
    }
    operationData.setAttributes(data);
    return operationData;
  }


  @Override
  public String getCurrentSubject() {
    return account.get("nickName").getAsString();
  }


  @Override
  public Serializable getCurrentSessionId() {
    return loginSessionHolder.getSessionId();
  }


  @Override
  public ILoginSessionHolder getLoginSessionHolder() {
    return loginSessionHolder;
  }


  @Override
  public void setLoginSessionHolder(ILoginSessionHolder loginSessionHolder) {
    this.loginSessionHolder = loginSessionHolder;
  }


  @Override
  public String getContainerFullId() {
    return container.get("fullId").getAsString();
  }
}
