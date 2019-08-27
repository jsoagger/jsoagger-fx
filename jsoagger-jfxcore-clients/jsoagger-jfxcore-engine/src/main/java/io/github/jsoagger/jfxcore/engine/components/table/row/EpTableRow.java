/*-
 * ========================LICENSE_START=================================
 * JSoagger 
 * %%
 * Copyright (C) 2019 JSOAGGER
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */

package io.github.jsoagger.jfxcore.engine.components.table.row;



import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.security.IContextParamSetter;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewFilterXML;
import io.github.jsoagger.jfxcore.engine.components.security.CriteriaContext;
import io.github.jsoagger.jfxcore.engine.components.security.CriteriaEvaluatorImpl;

import javafx.css.PseudoClass;
import javafx.scene.control.TableRow;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class EpTableRow extends TableRow {

  PseudoClass lastRow = PseudoClass.getPseudoClass("last-row");
  IJSoaggerController controller;
  CriteriaContext rowCriteriaContext;
  VLViewComponentXML tableConfiguration;

  /**
   * Constructor
   */
  public EpTableRow() {
    super();
  }

  @Override
  protected void updateItem(Object model, boolean empty) {
    super.updateItem(model, empty);
    if(!empty && model != null) {
      rowCriteriaContext = new CriteriaContext();
      String rowCriteriaSetter = tableConfiguration.getPropertyValue("rowCriteriaContextSetter");

      if(StringUtils.isNotBlank(rowCriteriaSetter)) {
        final IContextParamSetter contextParamSetter = (IContextParamSetter) Services.getBean(rowCriteriaSetter);
        contextParamSetter.process(controller, model, rowCriteriaContext);
      }
    }
  }


  /**
   * Evaluate this criteria in the context of this row.
   *
   * @param rowCriteria
   * @return
   */
  public boolean evaluateCriteria(String rowCriteria) {
    if(rowCriteriaContext == null) return false;
    if(rowCriteria == null) return false;

    if(rowCriteriaContext.containsFilter(rowCriteria)){
      return "true".equals(rowCriteriaContext.filterValue(rowCriteria));
    }

    final CriteriaEvaluatorImpl evaluatorImpl = new CriteriaEvaluatorImpl();
    final VLViewFilterXML noeud = Services.resolveFilter(controller, rowCriteria);

    if (noeud != null) {
      return evaluatorImpl.evaluate(controller, noeud, rowCriteriaContext);
    }

    // a criteria have been declared
    // but developper has not declared an evaluator for it
    // so hide it
    return false;
  }


  @Override
  public void updateIndex(int index) {
    super.updateIndex(index);
    pseudoClassStateChanged(lastRow, index >= 0 && index == getTableView().getItems().size() - 1);
  }

  /**
   * @return the controller
   */
  public IJSoaggerController getController() {
    return controller;
  }

  /**
   * @param controller the controller to set
   */
  public void setController(IJSoaggerController controller) {
    this.controller = controller;
  }

  /**
   * @return the tableConfiguration
   */
  public VLViewComponentXML getTableConfiguration() {
    return tableConfiguration;
  }

  /**
   * @param tableConfiguration the tableConfiguration to set
   */
  public void setTableConfiguration(VLViewComponentXML tableConfiguration) {
    this.tableConfiguration = tableConfiguration;
  }

}
