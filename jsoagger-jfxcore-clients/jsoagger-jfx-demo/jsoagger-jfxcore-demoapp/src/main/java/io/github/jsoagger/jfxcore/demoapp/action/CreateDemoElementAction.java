/**
 *
 */
package io.github.jsoagger.jfxcore.demoapp.action;

import java.util.Optional;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.PushStructureContentEvent;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class CreateDemoElementAction extends AbstractAction implements IAction {


  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    NodeHelper.showHeaderSuccessCreateMessage(actionRequest.getController());
    PushStructureContentEvent ev = new PushStructureContentEvent
        .Builder()
        .viewId("AnotherWizardView")
        .replace(true)
        .build();
    ((AbstractViewController) actionRequest.getController()).dispatchEvent(ev);
  }

  @Override
  public String getId() {
    return "CreateDemoElementAction";
  }

  @Override
  public void setData(OperationData data) {
    this.data = data;
  }
}
