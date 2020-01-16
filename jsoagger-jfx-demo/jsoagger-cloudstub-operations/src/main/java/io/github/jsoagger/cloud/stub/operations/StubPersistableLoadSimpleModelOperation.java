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



import java.util.function.Consumer;

import com.google.gson.JsonObject;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;

/**
 * Need by multiform search controller to load the type.
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class StubPersistableLoadSimpleModelOperation implements IOperation {

  // needs GetTypeByOidOperation
  private IOperation operation;


  @Override
  public void doOperation(JsonObject params, Consumer<IOperationResult> resultHandler, Consumer<Throwable> exHandler) {
    operation.doOperation(params, resultHandler);
  }


  /**
   * @return the operation
   */
  public IOperation getOperation() {
    return operation;
  }


  /**
   * @param operation the operation to set
   */
  public void setOperation(IOperation operation) {
    this.operation = operation;
  }

}
