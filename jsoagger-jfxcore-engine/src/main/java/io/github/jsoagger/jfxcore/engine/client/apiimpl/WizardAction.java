/**
 *
 */
package io.github.jsoagger.jfxcore.engine.client.apiimpl;

import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.jfxcore.api.ActionResultStatus;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.engine.controller.main.WizardViewController;
import io.github.jsoagger.jfxcore.engine.controller.utils.WizardViewUtils;
import com.google.gson.JsonObject;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public abstract class WizardAction extends AbstractAction {

  protected WizardViewController sourceController;


  /**
   * {@inheritDoc}
   */
  @Override
  public void onActionGeneralError(Throwable ex) {
    sourceController.hide();
    sourceController.hideProcessingPane();
    super.onActionGeneralError(ex);
  }


  protected void createSuccess(IOperationResult operationResult) {
    if (!operationResult.hasBusinessError()) {
      sourceController.hide();
      sourceController.hideProcessingPane();

      NodeHelper.showHeaderSuccessCreateMessage(sourceController);
    }

    // error
    else {
      NodeHelper.showHeaderErrorCreateMessage(sourceController);
      sourceController.hideProcessingPane();
      sourceController.show();

      ActionResult ar = new ActionResult.ActionResultBuilder().operationMessage(operationResult.getMessages()).status(ActionResultStatus.ERROR).build();
      resultProperty.set(ar);
    }
  }

  public JsonObject populateBeanFromWizard(WizardViewController viewController) {
    this.sourceController = viewController;
    SingleResult sr = (SingleResult) sourceController.getModel();

    JsonObject query = new JsonObject();
    WizardViewUtils.copyAllAttributesFrom(sr, query);
    query.addProperty("containerOid", sourceController.getRootStructure().getModelContainerFullId());
    return query;
  }

  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.api.IAction#execute(io.github.jsoagger.jfxcore.api.IActionRequest, java.util.Optional)
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {

  }


  /**
   * @return the sourceController
   */
  public WizardViewController getSourceController() {
    return sourceController;
  }


  /**
   * @param sourceController the sourceController to set
   */
  public void setSourceController(WizardViewController sourceController) {
    this.sourceController = sourceController;
  }
}
