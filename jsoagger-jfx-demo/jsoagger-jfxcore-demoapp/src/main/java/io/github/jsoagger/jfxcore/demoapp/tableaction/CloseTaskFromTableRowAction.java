/**
 *
 */
package io.github.jsoagger.jfxcore.demoapp.tableaction;

import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.AbstractTableStructure;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.table.TableContent;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableStructureController;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class CloseTaskFromTableRowAction extends AbstractAction{

  /**
   * Constructore
   */
  public CloseTaskFromTableRowAction() {
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

    IBuildable source  = actionRequest.getSource();

    data.getAttributes().put("status","Delivered");
    if(ts instanceof TableContent) {
      ((TableContent)ts).refreshTable();
    }
  }
}
