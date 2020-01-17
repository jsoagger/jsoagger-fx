/**
 *
 */
package io.github.jsoagger.jfxcore.platform.components.actions;

import java.util.Optional;

import com.google.gson.JsonObject;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.jfxcore.engine.components.dialog.impl.ErrorDialog;
import io.github.jsoagger.jfxcore.engine.components.dialog.impl.InformationDialog;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.AbstractTableStructure;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableStructureController;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class DeleteContentItemFromTableRowAction extends AbstractAction{

  private IOperation deleteContentItemOperation;//  needs DeleteContentItemOperation

  /**
   * Constructore
   */
  public DeleteContentItemFromTableRowAction() {
    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    FullTableStructureController controller = (FullTableStructureController) actionRequest.getController();
    OperationData data = (OperationData) actionRequest.getProperty("sourceData");
    AbstractTableStructure ts = controller.getTableStructure();

    String itemFullId = (String) data.getAttributes().get("fullId");
    String holderFullId = (String) controller.getOpData().getAttributes().get("fullId");
    String role = (String) actionRequest.getProperty("contentRole");

    JsonObject query = new JsonObject();
    query.addProperty("contentItemFullId", itemFullId);
    query.addProperty("contentHolderOid", holderFullId);
    query.addProperty("role", role);

    deleteContentItemOperation.doOperation(query, res -> {
      if(!res.hasBusinessError()) {
        ts.getSelectedElements().clear();
        controller.getTableStructure().getItems().remove(data);

        if("primary".equalsIgnoreCase(role)) {
          controller.getTableStructure().setNoContent();
        }

        new InformationDialog.Builder()
        .title("Deleted")
        .message("Item successfully deleted.")
        .buildAccent(controller).show(true);
      }
      else {
        new ErrorDialog.Builder()
        .title("Delete")
        .message("An error occurs, please try again")
        .build(controller).show(true);
      }
    });

  }

  /**
   * @return the deleteContentItemOperation
   */
  public IOperation getDeleteContentItemOperation() {
    return deleteContentItemOperation;
  }

  /**
   * @param deleteContentItemOperation the deleteContentItemOperation to set
   */
  public void setDeleteContentItemOperation(IOperation deleteContentItemOperation) {
    this.deleteContentItemOperation = deleteContentItemOperation;
  }
}
