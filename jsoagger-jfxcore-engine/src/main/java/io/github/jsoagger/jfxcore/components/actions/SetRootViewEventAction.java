/**
 *
 */
package io.github.jsoagger.jfxcore.components.actions;

import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.engine.controller.main.layout.ViewStructure;
import io.github.jsoagger.jfxcore.engine.events.SetRootStructureEvent;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class SetRootViewEventAction extends AbstractAction {

  /**
   *
   */
  public SetRootViewEventAction() {
    super();
  }

  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.api.IAction#execute(io.github.jsoagger.jfxcore.api.IActionRequest, java.util.Optional)
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    String viewid = (String) actionRequest.getProperty("viewId");
    SetRootStructureEvent event = new SetRootStructureEvent();
    event.setViewId(viewid);
    ViewStructure.instance().listenTo(event);
  }
}
