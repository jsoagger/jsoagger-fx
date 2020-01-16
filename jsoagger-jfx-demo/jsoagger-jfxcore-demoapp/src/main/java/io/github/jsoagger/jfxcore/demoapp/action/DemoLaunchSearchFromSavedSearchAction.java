/**
 *
 */
package io.github.jsoagger.jfxcore.demoapp.action;

import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.components.search.controller.SearchController;

import javafx.application.Platform;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class DemoLaunchSearchFromSavedSearchAction extends AbstractAction {

  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.api.IAction#execute(io.github.jsoagger.jfxcore.api.IActionRequest, java.util.Optional)
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {

    AbstractViewController c = (AbstractViewController) actionRequest.getController();

    // parent of this controller must be a search controller
    SearchController sc = (SearchController) c.getParent();
    if(c != null) {
      sc.doSearch();
      Platform.runLater(()-> sc.getRootStructure().popContent());
    }
  }
}
