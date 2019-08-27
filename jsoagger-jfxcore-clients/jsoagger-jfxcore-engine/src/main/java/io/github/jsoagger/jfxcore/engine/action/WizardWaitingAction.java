/**
 *
 */
package io.github.jsoagger.jfxcore.engine.action;

import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.jfxcore.engine.client.apiimpl.ActionResult;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.ActionResultStatus;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.engine.controller.main.WizardViewController;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class WizardWaitingAction  extends AbstractAction {

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest,
      Optional<IActionResult> previousActionResult) {

    IJSoaggerController c = actionRequest.getController();

    if(c instanceof WizardViewController) {
      ((WizardViewController) c).hide();
      NodeHelper.showProcessingDialog((WizardViewController) c);

      ActionResult ar = new ActionResult.ActionResultBuilder().status(ActionResultStatus.SUCCESS).build();
      resultProperty.set(ar);
    }
  }
}
