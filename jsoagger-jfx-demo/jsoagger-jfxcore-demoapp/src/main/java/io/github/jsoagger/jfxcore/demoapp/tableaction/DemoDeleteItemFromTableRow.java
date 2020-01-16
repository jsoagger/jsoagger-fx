/**
 *
 */
package io.github.jsoagger.jfxcore.demoapp.tableaction;

import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.cloud.stub.operations.StubSavedSearchTableDataOperation;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.AbstractTableStructure;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class DemoDeleteItemFromTableRow extends AbstractAction implements IAction {

  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    OperationData d = (OperationData) actionRequest.getProperty("sourceData");
    AbstractTableStructure ts = (AbstractTableStructure) ((StandardViewController)actionRequest.getController()).processedElement();
    ts.getItems().remove(d);
    if(ts.getItems().size() < 1) {
      ts.setNoContent();
    }

    StubSavedSearchTableDataOperation op = (StubSavedSearchTableDataOperation) Services.getBean("StubSavedSearchTableDataOperation");
    op.deleteItem((String) d.getAttributes().get("fullId"));
  }

}
