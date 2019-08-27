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

package io.github.jsoagger.jfxcore.engine.components.listform;




import java.util.function.Function;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.engine.components.list.impl.AbstractListCell;
import io.github.jsoagger.jfxcore.engine.components.presenter.CellPresenterFactory;

import javafx.scene.Node;

/**
 * Factory populating the graphic displayed on each cell of the list view.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ListFormCellFactory extends AbstractListCell<OperationData> {

  private Function<Void, Void> backFunction;
  private Function<Node, Void> showFunction;


  /**
   * {@inheritDoc}
   */
  @Override
  protected void updateItem(OperationData item, boolean empty) {
    super.updateItem(item, empty);

    if (!empty && item != null) {

      loadPresenter(item);
      CellPresenterFactory p = getPresenter();
      if(p != null) {
        if (item.getAttributes().get("ep_data_selection") == null) {
          item.getAttributes().put("ep_data_selection", false);
        }

        loadPresenter(item);
        p.setCell(this);
        p.setForData(item);
        p.buildFrom(controller, configuration);
        setGraphic(p.getDisplay());
      }
      else {
        if(p == null) {
          System.out.println("[WARNING] No presenter found for " + item.getAttributes().get("fullId"));
        }
        setGraphic(null);
        super.updateItem(null, true);
      }
    }
  }


  /**
   * @param object
   */
  public void setOnBack(Function<Void, Void> backFunction) {
    this.backFunction = backFunction;
  }


  /**
   * @param object
   */
  public void setOnShow(Function<Node, Void> showFunction) {
    this.showFunction = showFunction;
  }
}
