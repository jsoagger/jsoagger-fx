/**
 *
 */
package io.github.jsoagger.jfxcore.platform.components.modelprovider;

import com.google.gson.JsonObject;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.api.services.Services;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class UserRootFolderModelProvider  implements IModelProvider {

  @Override
  public IOperationResult loadModel(IJSoaggerController controller, String componentId) {
    IOperation op = (IOperation) Services.getBean("GetUserRootFolderOperation");
    IOperation getCurrentUserOperation = (IOperation) Services.getBean("GetCurrentUserOperation");

    try {

      getCurrentUserOperation.doOperation(new JsonObject(), res -> {
        JsonObject params = new JsonObject();
        SingleResult sr = (SingleResult) res;
        String fullId = (String) sr.getData().getAttributes().get("fullId");
        params.addProperty("accountOid", fullId);

        op.doOperation(params, r -> {
          controller.setModel(r);
        });
      });
    }
    catch (Exception e) {
    }

    return null;
  }

}
