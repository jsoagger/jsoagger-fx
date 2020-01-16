/**
 *
 */
package io.github.jsoagger.jfxcore.engine.action;

import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.jfxcore.engine.client.apiimpl.ActionResult;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.WizardViewController;

/**
 * When showing wizard from table view and need to refresh the after object creation.
 * @author Ramilafananana  VONJISOA
 *
 */
public class RefreshParentTableAction extends AbstractAction implements IAction {

  /**
   * Constructor
   */
  public RefreshParentTableAction() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getId() {
    return null;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    AbstractViewController controller = (AbstractViewController) actionRequest.getController();

    try {
      if(previousActionResult.get().isSuccess()) {
        if(controller instanceof WizardViewController) {
          WizardViewController c = (WizardViewController) controller;
          AbstractViewController parent = c.getParent();
          if(parent instanceof FullTableViewController) {
            ((FullTableViewController)parent).refreshDatas();
            //AbstractTableStructure ts = (AbstractTableStructure) ((FullTableStructureController)parent).processedElement();
            //ts.refreshCurrentPage();
            //ts.refreshDatas();
          }
        }
      }

      resultProperty.set(ActionResult.success());
    }
    catch (Exception e) {
      e.printStackTrace();
      resultProperty.set(ActionResult.error());
    }
  }
}
