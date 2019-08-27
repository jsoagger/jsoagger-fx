/**
 *
 */
package io.github.jsoagger.jfxcore.platform.components.actions;

import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.PushStructureContentEvent;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class ForwardToCreateSuccessAction extends AbstractAction {

  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.api.IAction#execute(io.github.jsoagger.jfxcore.api.IActionRequest, java.util.Optional)
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {

    // @formatter:off
    PushStructureContentEvent ev = new PushStructureContentEvent
        .Builder()
        .viewId("CreateSuccessView")
        .replace(true)
        .model(data)
        .build();
    // @formatter:on

    ((AbstractViewController) actionRequest.getController()).dispatchEvent(ev);


  }
}
