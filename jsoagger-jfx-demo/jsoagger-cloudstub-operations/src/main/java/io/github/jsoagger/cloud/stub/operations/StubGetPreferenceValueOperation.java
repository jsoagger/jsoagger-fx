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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.google.gson.JsonObject;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.FileUtils;

/**
 * @author Ramilafananana  VONJISOA
 */
public class StubGetPreferenceValueOperation implements IOperation {

  public static  Map<String, String> s_datas = new HashMap<>();


  /**
   * {@inheritDoc}
   */
  @Override
  public void doOperation(JsonObject params, Consumer<IOperationResult> resultHandler, Consumer<Throwable> exHandler) {
    if (s_datas.isEmpty()) {
      try  {
        List<String> lines = FileUtils.readAllLines("/preferences.csv", false);
        for (String line : lines) {
          String[] d = line.split("=");
          s_datas.put(d[0], d.length == 2 ? d[1] : "");
        }
      } catch (final Exception e) {
        e.printStackTrace();
      }
    }

    final List<OperationData> datas = new ArrayList<>();
    String value = s_datas.get(params.get("key").getAsString());
    if (value != null) {
      for (final String s : value.split(",")) {
        OperationData d = new OperationData();
        d.getAttributes().put("savedValue", s);
        d.getAttributes().put("displayedValue", s);
        d.getAttributes().put("description", s);
        datas.add(d);
      }
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
}
