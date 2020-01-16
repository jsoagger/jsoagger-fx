/**
 *
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
public class StubGetFolderContentOperation implements IOperation {

  @Override
  public void doOperation(JsonObject params, Consumer<IOperationResult> resultHandler, Consumer<Throwable> exHandler) {
    MultipleResult multipleResult = new MultipleResult();
    multipleResult.setData(new ArrayList<>());
    multipleResult.addMetaData("pageSize", 0);
    multipleResult.addMetaData("pageNumber", 0);
    multipleResult.addMetaData("totalPages", 0);
    multipleResult.addMetaData("hasNextPage", false);
    multipleResult.addMetaData("hasPreviousPage", false);
    multipleResult.addMetaData("pageElements", 0);
    multipleResult.addMetaData("totalElements", 0);
    resultHandler.accept(multipleResult);
  }
}
