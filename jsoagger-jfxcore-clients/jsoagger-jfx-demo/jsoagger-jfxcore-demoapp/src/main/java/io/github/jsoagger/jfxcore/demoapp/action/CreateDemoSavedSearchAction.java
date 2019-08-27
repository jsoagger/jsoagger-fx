/**
 *
 */
package io.github.jsoagger.jfxcore.demoapp.action;

import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.apiimpl.WizardAction;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.cloud.stub.operations.StubSavedSearchTableDataOperation;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.controller.main.WizardViewController;
import com.google.gson.JsonObject;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class CreateDemoSavedSearchAction extends WizardAction implements IAction {


  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    NodeHelper.showHeaderSuccessCreateMessage(actionRequest.getController());

    StubSavedSearchTableDataOperation op = (StubSavedSearchTableDataOperation) Services.getBean("StubSavedSearchTableDataOperation");
    JsonObject query = populateBeanFromWizard((WizardViewController) actionRequest.getController());
    op.createSavedSearch(query, this::createSuccess, this::onActionGeneralError);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {
    return "CreateDemoSavedSearchAction";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setData(OperationData data) {
    this.data = data;
  }
}
