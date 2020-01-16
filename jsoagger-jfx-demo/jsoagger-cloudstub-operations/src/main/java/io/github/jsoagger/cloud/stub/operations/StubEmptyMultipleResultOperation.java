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



import java.util.ArrayList;
import java.util.function.Consumer;

import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import com.google.gson.JsonObject;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class StubEmptyMultipleResultOperation implements IOperation {

  /**
   * {@inheritDoc}
   */
  @Override
  public void doOperation(JsonObject params, Consumer<IOperationResult> resultHandler, Consumer<Throwable> exHandler) {
    final MultipleResult multipleResult = new MultipleResult();
    multipleResult.setData(new ArrayList<>());
    multipleResult.addMetaData("pageSize", 10);
    multipleResult.addMetaData("pageNumber", 0);
    multipleResult.addMetaData("totalPages", 0);
    multipleResult.addMetaData("hasNextPage", false);
    multipleResult.addMetaData("hasPreviousPage", false);
    multipleResult.addMetaData("pageElements", 0);
    multipleResult.addMetaData("totalElements", 0);
    resultHandler.accept(multipleResult);
  }
}
