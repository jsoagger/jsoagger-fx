/**
 *
 */
package io.github.jsoagger.jfxcore.platform.components.actions;

import java.util.Optional;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.api.IFormRowEditor;
import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;

import javafx.application.Platform;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class SelectFolderInputAction extends AbstractAction implements IAction {

  /**
   * Constructore
   */
  public SelectFolderInputAction() {
    super();
  }

  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.api.IAction#execute(io.github.jsoagger.jfxcore.api.IActionRequest, java.util.Optional)
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    StandardViewController controller = (StandardViewController) actionRequest.getController();
    OperationData data  = (OperationData) actionRequest.getProperty("sourceData");
    controller.selectedElementProperty().set(data);

    IFormRowEditor editor = controller.getParent().currentForwarEditor();
    if(editor != null) {
      Platform.runLater(()->{
        editor.getInlineEditionHandler().onDone(editor);
      });
    }
  }
}
