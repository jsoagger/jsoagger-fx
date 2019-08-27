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



import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.google.gson.JsonObject;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.FileUtils;
import io.github.jsoagger.core.utils.StringUtils;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class StubPaginatedTableDataOperation implements IOperation {

  private static List<String> datas = new ArrayList<>();

  // needs GetPreferenceValueOperation
  IOperation getPreferenceValueOperation;


  /**
   * {@inheritDoc}
   */
  @Override
  public void doOperation(JsonObject params, Consumer<IOperationResult> resultHandler, Consumer<Throwable> exHandler) {

    String dataKey = getDatasetKey();
    if (datas.isEmpty()) {
      try (InputStream is = StubPaginatedTableDataOperation.class.getResourceAsStream("/" + "military" + "-paginatedTabledatas.csv")) {
        datas.addAll(FileUtils.readAllLines("/" + "military" + "-paginatedTabledatas.csv", false));
      } catch (final Throwable e) {
        e.printStackTrace();
      }
    }

    final MultipleResult multipleResult = new MultipleResult();
    final List<OperationData> datas = new ArrayList<>();

    final int page = params.get("page").getAsInt();
    int pageSize = params.get("pageSize").getAsInt();
    String status = params.get("queryPredicate") != null ?  params.get("queryPredicate").getAsString() : "in repair";
    if(status.contains("delivered")) {
      status = "delivered";
    }
    if(status.contains("wait")) {
      status = "waiting for acceptance";
    }
    if(status.contains("repair")) {
      status = "in repair";
    }

    int begin = ((page * pageSize) + pageSize);
    int end = begin + pageSize;

    if(end > StubPaginatedTableDataOperation.datas.size()) {
      end = StubPaginatedTableDataOperation.datas.size();
    }

    for (int i = begin; i < end; i++) {
      final String line = StubPaginatedTableDataOperation.datas.get(i);
      if (!StringUtils.isEmpty(line) && !line.startsWith("#")) {
        final String[] si = line.split(",");

        // filter by status
        if(si[9].equalsIgnoreCase(status)) {
          final OperationData d = new OperationData();

          final String[] cols = "id,vin,lastname,yearmodel,company,lastModified,model,firstname,lastname,status,description".split(",");
          for (int j = 0; j < cols.length; j++) {
            d.getAttributes().put(cols[j], si[j]);
          }

          d.getAttributes().put("fullId", si[0]);
          datas.add(d);

        }
        else {
          end++;
        }
      }
    }
    multipleResult.setData(datas);
    multipleResult.addMetaData("pageSize", pageSize);
    multipleResult.addMetaData("pageNumber", page);
    multipleResult.addMetaData("totalPages", StubPaginatedTableDataOperation.datas.size() / pageSize);
    multipleResult.addMetaData("hasNextPage", (StubPaginatedTableDataOperation.datas.size()/ pageSize) > page);
    multipleResult.addMetaData("hasPreviousPage", page > 0);
    multipleResult.addMetaData("pageElements", datas.size());
    multipleResult.addMetaData("totalElements", StubPaginatedTableDataOperation.datas.size());
    resultHandler.accept(multipleResult);
  }


  private String getDatasetKey() {
    Key key = new Key("foods");
    if (getPreferenceValueOperation != null) {
      JsonObject query = new JsonObject();
      query.addProperty("key", "io.github.jsoagger.demoapp.dataset");
      getPreferenceValueOperation.doOperation(query, res -> {
        MultipleResult r = (MultipleResult) res;
        if (r.getData().size() > 0) {
          String vo = (String) r.getData().get(0).getAttributes().get("savedValue");
          key.setValue(vo);
        }
      });
    }

    return key.value;
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


  public static void main(String[] args) {
    try {
      List<String> datas = FileUtils.readAllLines("/" + "foods" + "-paginatedTabledatas.csv");
      for (String line : datas) {
        if (line.split(",").length > 11) {
          System.out.println(line.split(",")[0]);
        }
      }
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * @return the getPreferenceValueOperation
   */
  public IOperation getGetPreferenceValueOperation() {
    return getPreferenceValueOperation;
  }


  /**
   * @param getPreferenceValueOperation the getPreferenceValueOperation to set
   */
  public void setGetPreferenceValueOperation(IOperation getPreferenceValueOperation) {
    this.getPreferenceValueOperation = getPreferenceValueOperation;
  }
}
