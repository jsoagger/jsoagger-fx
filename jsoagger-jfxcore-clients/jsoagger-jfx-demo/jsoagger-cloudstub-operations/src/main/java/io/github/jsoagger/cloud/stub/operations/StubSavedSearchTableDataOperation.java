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
import io.github.jsoagger.core.utils.StringUtils;
import com.google.gson.JsonObject;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class StubSavedSearchTableDataOperation implements IOperation {

  private static List<String> datas = new ArrayList<>();

  // needs GetPreferenceValueOperation
  IOperation getPreferenceValueOperation;


  /**
   * {@inheritDoc}
   */
  @Override
  public void doOperation(JsonObject params, Consumer<IOperationResult> resultHandler, Consumer<Throwable> exHandler) {
    final MultipleResult multipleResult = new MultipleResult();
    final List<OperationData> datas = new ArrayList<>();

    final int page = params.get("page").getAsInt();
    int pageSize = params.get("pageSize").getAsInt();

    int begin = 0;
    if(page > 0) {
      begin = ((page * pageSize) + pageSize);
    }

    int end = begin + pageSize;

    if(begin > StubSavedSearchTableDataOperation.datas.size()) {
      resultHandler.accept(IOperationResult.emptyMultipleResult());
      return;
    }

    if(end > StubSavedSearchTableDataOperation.datas.size()) {
      end = StubSavedSearchTableDataOperation.datas.size();
    }

    for (int i = begin; i < end; i++) {
      final String line = StubSavedSearchTableDataOperation.datas.get(i);

      if (!StringUtils.isEmpty(line) && !line.startsWith("#")) {
        final OperationData d = new OperationData();

        final String[] cols = "id,name,description".split(",");
        final String[] si = line.split(",");
        for (int j = 0; j < cols.length; j++) {
          d.getAttributes().put(cols[j], si[j]);
        }

        d.getAttributes().put("fullId", si[0]);
        datas.add(d);
      } else {
        pageSize++;
      }
    }

    multipleResult.setData(datas);
    multipleResult.addMetaData("pageSize", pageSize);
    multipleResult.addMetaData("pageNumber", page);
    multipleResult.addMetaData("totalPages", StubSavedSearchTableDataOperation.datas.size() / pageSize);
    multipleResult.addMetaData("hasNextPage", (StubSavedSearchTableDataOperation.datas.size()/ pageSize) > page);
    multipleResult.addMetaData("hasPreviousPage", page > 0);
    multipleResult.addMetaData("pageElements", datas.size());
    multipleResult.addMetaData("totalElements", StubSavedSearchTableDataOperation.datas.size());
    resultHandler.accept(multipleResult);
  }


  public static class Key {
    String value;

    public Key(String val) {
      this.value = val;
    }

    public void setValue(String value) {
      this.value = value;
    }
  }


  public void createSavedSearch(JsonObject query, Consumer<IOperationResult> sa, Consumer<Throwable> object2) {
    String name = query.get("name").getAsString();
    Integer id = datas.size() + 1;
    datas.add(id + "," + name + "," + "No description");
    IOperationResult r = new SingleResult();
    OperationData data = new OperationData();
    data.getAttributes().put("fullId", id);
    data.getAttributes().put("id", id);
    data.getAttributes().put("name", name);
    data.getAttributes().put("description", "No description");
    sa.accept(r);
  }


  public void deleteItem(String fullId) {
    String lintodelete = null;
    for(String l: StubSavedSearchTableDataOperation.datas) {
      if(l.startsWith(fullId +","));
      lintodelete = l;
    }

    if(lintodelete != null)
      StubSavedSearchTableDataOperation.datas.remove(lintodelete);
  }
}
