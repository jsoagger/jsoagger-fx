/**
 *
 */
package io.github.jsoagger.jfxcore.platform.components.actions;

import java.util.Optional;

import com.google.gson.JsonObject;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.engine.client.apiimpl.WizardAction;
import io.github.jsoagger.jfxcore.engine.controller.main.WizardViewController;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class CreateElementAction extends WizardAction implements IAction {

  private IOperation createElementOperation;//  needs createElementOperation

  /**
   * @{inheritedDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    JsonObject query = populateBeanFromWizard((WizardViewController) actionRequest.getController());
    createElementOperation.doOperation(query, this::createSuccess, this::onActionGeneralError);
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public String getId() {
    return "createElementOperation";
  }


  /**
   * @return the createElementOperation
   */
  public IOperation getCreateElementOperation() {
    return createElementOperation;
  }


  /**
   * @param createElementOperation the createElementOperation to set
   */
  public void setCreateElementOperation(IOperation createElementOperation) {
    this.createElementOperation = createElementOperation;
  }

}
