/**
 *
 */
package io.github.jsoagger.jfxcore.demoapp;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.ICriteriaContext;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.security.IContextParamSetter;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class DemoTablesRowCriteriaSetter implements IContextParamSetter {

  /* (non-Javadoc)
   * @see io.github.jsoagger.jfxcore.api.security.IContextParamSetter#process(io.github.jsoagger.jfxcore.api.IJSoaggerController, java.lang.Object, io.github.jsoagger.jfxcore.api.ICriteriaContext)
   */
  @Override
  public void process(IJSoaggerController controller, Object model, ICriteriaContext criteriaContext) {

    OperationData data = (OperationData) model;

    String status = (String) data.getAttributes().get("status");
    if("in repair".equalsIgnoreCase(status)) {
      criteriaContext.setFilter("isDeliverable", "true");
    }

    if("delivered".equalsIgnoreCase(status)) {
      criteriaContext.setFilter("isOpenable", "true");
    }

    if("waiting for acceptance".equalsIgnoreCase(status)) {
      criteriaContext.setFilter("isRepairable", "true");
    }
  }
}
