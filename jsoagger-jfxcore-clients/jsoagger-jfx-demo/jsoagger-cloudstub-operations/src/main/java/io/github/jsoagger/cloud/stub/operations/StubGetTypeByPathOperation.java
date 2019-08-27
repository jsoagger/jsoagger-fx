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
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.core.utils.FileUtils;
import com.google.gson.JsonObject;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class StubGetTypeByPathOperation implements IOperation {

  /**
   * {@inheritDoc}
   */
  @Override
  public void doOperation(JsonObject params, Consumer<IOperationResult> resultHandler, Consumer<Throwable> exHandler) {
    final SingleResult result = new SingleResult();
    final OperationData d = new OperationData();
    result.setData(d);

    final String path = params.get("path").getAsString();

    final List<String> lines = new ArrayList<>();
    try {
      lines.addAll(FileUtils.readAllLines("/typesmanaged.csv", false));
    } catch (final Exception e) {
      e.printStackTrace();
    }

    for (final String line : lines) {
      if (line != null) {
        final String[] l = line.split(",");
        if (l[3].equals(path)) {
          final String[] cols = "fullId,logicalName,displayName,logicalPath".split(",");
          final String[] si = line.split(",");
          for (int j = 0; j < 4; j++) {
            d.getAttributes().put(cols[j], si[j]);
          }

          d.getAttributes().put("fullId", si[0]);
        }
      }
    }
    resultHandler.accept(result);
  }

}
