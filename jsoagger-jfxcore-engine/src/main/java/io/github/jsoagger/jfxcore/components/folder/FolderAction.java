/**
 *
 */
package io.github.jsoagger.jfxcore.components.folder;

import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.AbstractTableStructure;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.WizardViewController;
import com.google.gson.JsonObject;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public abstract class FolderAction extends AbstractAction {

  protected WizardViewController controller;
  protected FullTableStructureController parentController;

  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.api.IAction#execute(io.github.jsoagger.jfxcore.api.IActionRequest, java.util.Optional)
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {

  }

  protected void reloadChildren(IOperationResult operationResult) {
    // action from wizard relative to table
    if(parentController != null) {
      IOperation op = (IOperation) Services.getBean("GetFolderChildrenOperation");

      OperationData data = null;
      // action form wizard
      if(controller != null) {
        data = controller.relativeToProperty().get();
      }
      // action form table row
      else {
        AbstractTableStructure ts = (AbstractTableStructure) parentController.processedElement();
        if(ts.childTree().size() > 0) {
          // last element in tree
          data = ts.childTree().get(ts.childTree().size() - 1);
        }
        else {
          // root element
          data = parentController.getOpData();
        }
      }

      final OperationData rootdata = data;

      // refresh folder
      JsonObject query = new JsonObject();
      query.addProperty("oid", data.getAttributes().get("fullId").toString());
      query.addProperty("fullId", data.getAttributes().get("fullId").toString());

      op.doOperation(query, resp -> {
        AbstractTableStructure ts = (AbstractTableStructure) parentController.processedElement();
        //ts.pushChildrenTree(data);
        ts.onModelUpdated(resp);
        // refresh content
        parentController.getStructureContent().setFormModelData(null);
        parentController.getStructureContent().setFormModelData(rootdata);
      }, ex -> {
        ex.printStackTrace();
      });
    }
  }
}
