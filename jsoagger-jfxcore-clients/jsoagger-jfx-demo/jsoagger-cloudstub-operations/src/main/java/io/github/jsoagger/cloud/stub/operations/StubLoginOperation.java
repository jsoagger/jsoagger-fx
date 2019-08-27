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

package io.github.jsoagger.cloud.stub.operations;



import java.io.Serializable;
import java.util.UUID;
import java.util.function.Consumer;

import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.operation.JsonUtils;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import com.google.gson.JsonObject;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class StubLoginOperation implements IOperation {

  @Override
  public void doOperation(JsonObject params, Consumer<IOperationResult> resultHandler, Consumer<Throwable> exHandler) {
    final SingleResult sr = new SingleResult();
    final Serializable sessionId = UUID.randomUUID().toString();
    sr.addMetaData("session_id", sessionId);

    final OperationData operationData = new OperationData();
    sr.setData(operationData);

    JsonObject account = new JsonObject();
    account.addProperty("login", "lorenzo@nexitia.com");
    account.addProperty("nickName", "lorenzo");

    final JsonObject user = new JsonObject();
    user.addProperty("nickName", "lorenzo");
    account.addProperty("mail", "lorenzo@nexitia.com");

    final JsonObject container = new JsonObject();
    container.addProperty("fullId", "1:people");
    container.addProperty("path", "/");

    operationData.getLinks().put("container", JsonUtils.toString(container));
    operationData.getLinks().put("account", JsonUtils.toString(account));
    operationData.getLinks().put("user", JsonUtils.toString(user));
    resultHandler.accept(sr);
  }
}
