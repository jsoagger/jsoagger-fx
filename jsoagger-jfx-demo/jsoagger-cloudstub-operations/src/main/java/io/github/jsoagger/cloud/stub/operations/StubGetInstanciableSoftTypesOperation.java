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
import java.util.List;
import java.util.function.Consumer;

import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.core.utils.FileUtils;
import com.google.gson.JsonObject;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class StubGetInstanciableSoftTypesOperation implements IOperation {

  private static List<String> datas = new ArrayList<>();

  // needs GetTypeByPathOperation
  private IOperation operation;


  /**
   * {@inheritDoc}
   */
  @Override
  public void doOperation(JsonObject params, Consumer<IOperationResult> resultHandler, Consumer<Throwable> exHandler) {
    if (datas.isEmpty()) {
      try {
        datas.addAll(FileUtils.readAllLines("/subtypesmanaged.csv", false));
      } catch (final Exception e) {
        e.printStackTrace();
      }
    }

    final List<String> subtypes = new ArrayList<>();
    for (final Object key : params.keySet()) {
      final String path = params.get((String) key).getAsString();
      for (final String line : datas) {
        if (line.startsWith(path)) {
          subtypes.add(line.split(",")[1]);
        }
      }
    }

    final List<OperationData> datas = new ArrayList<>();
    for (final String subtype : subtypes) {
      final JsonObject query = new JsonObject();
      query.addProperty("path", subtype);
      operation.doOperation(query, res -> {
        final SingleResult sr = (SingleResult) res;
        datas.add(sr.getData());
      });
    }

    final MultipleResult multipleResult = new MultipleResult();
    multipleResult.setData(datas);
    multipleResult.addMetaData("pageSize", datas.size());
    multipleResult.addMetaData("pageNumber", 0);
    multipleResult.addMetaData("totalPages", 1);
    multipleResult.addMetaData("hasNextPage", false);
    multipleResult.addMetaData("hasPreviousPage", false);
    multipleResult.addMetaData("pageElements", datas.size());
    multipleResult.addMetaData("totalElements", datas.size());
    resultHandler.accept(multipleResult);
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
