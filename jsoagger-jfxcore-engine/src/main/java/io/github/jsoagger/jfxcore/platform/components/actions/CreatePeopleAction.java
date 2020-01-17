/**
 *
 */
package io.github.jsoagger.jfxcore.platform.components.actions;

import java.util.Optional;

import com.google.gson.JsonObject;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.utils.WizardViewUtils;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class CreatePeopleAction  extends AbstractAction implements IAction {

  private IOperation createPeopleOperation;


  /**
   * @{inheritedDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    controller = (AbstractViewController) actionRequest.getController();
    SingleResult sr = (SingleResult) controller.getModel();

    JsonObject query = new JsonObject();
    WizardViewUtils.copyAllAttributesFrom(sr, query);
    query.addProperty("containerOid", controller.getRootStructure().getModelContainerFullId());
    query.addProperty("container", controller.getRootStructure().getModelContainerFullId());
    createPeopleOperation.doOperation(query, this::createSuccess, this::onActionGeneralError);
  }



  /**
   * @return the createPeopleOperation
   */
  public IOperation getCreatePeopleOperation() {
    return createPeopleOperation;
  }


  /**
   * @param createPeopleOperation the createPeopleOperation to set
   */
  public void setCreatePeopleOperation(IOperation createPeopleOperation) {
    this.createPeopleOperation = createPeopleOperation;
  }
}
