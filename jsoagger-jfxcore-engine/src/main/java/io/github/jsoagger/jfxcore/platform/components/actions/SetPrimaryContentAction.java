/**
 *
 */
package io.github.jsoagger.jfxcore.platform.components.actions;

import java.util.Optional;

import com.google.gson.JsonObject;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.jfxcore.api.ActionResultStatus;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.jfxcore.engine.client.apiimpl.ActionResult;
import io.github.jsoagger.jfxcore.engine.components.dialog.impl.ErrorDialog;
import io.github.jsoagger.jfxcore.engine.components.dialog.impl.InformationDialog;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.utils.WizardViewUtils;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class SetPrimaryContentAction extends AbstractAction {

  private IOperation setPrimaryContentOperation;//  needs setPrimaryContentOperation
  private IJSoaggerController controller;

  /**
   * @{inheritedDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    controller = actionRequest.getController();
    SingleResult sr = (SingleResult) controller.getModel();
    AbstractViewController p = ((AbstractViewController)controller).getParent();

    JsonObject query = new JsonObject();
    WizardViewUtils.copyAllAttributesFrom(sr, query);
    query.addProperty("containerOid", ((RootStructureController)controller.getRootStructure()).getModelContainerFullId());
    query.addProperty("fullId", (String) p.getOpData().getAttributes().get("fullId"));
    setPrimaryContentOperation.doOperation(query, this::createSuccess, this::onActionGeneralError);
  }


  protected void createSuccess(IOperationResult operationResult) {
    if (!operationResult.hasBusinessError()) {
      resultProperty.set(ActionResult.success());
      new InformationDialog.Builder()
      .title("Set content")
      .message("Primary content successfully update")
      .buildAccent((AbstractViewController) controller).show(true);
    } else {
      ActionResult ar = new ActionResult.ActionResultBuilder().operationMessage(operationResult.getMessages()).status(ActionResultStatus.ERROR).build();
      resultProperty.set(ar);

      new ErrorDialog.Builder()
      .title("Set content")
      .message("An error occurs, please try again.")
      .build((AbstractViewController)controller).show(true);
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getId() {
    return "setPrimaryContentOperation";
  }


  /**
   * @return the setPrimaryContentOperation
   */
  public IOperation getSetPrimaryContentOperation() {
    return setPrimaryContentOperation;
  }


  /**
   * @param setPrimaryContentOperation the setPrimaryContentOperation to set
   */
  public void setSetPrimaryContentOperation(IOperation setPrimaryContentOperation) {
    this.setPrimaryContentOperation = setPrimaryContentOperation;
  }
}
