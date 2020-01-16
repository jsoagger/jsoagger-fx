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

package io.github.jsoagger.jfxcore.engine.components.table.cell;



import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.table.simple.SimpleTableView;
import io.github.jsoagger.jfxcore.engine.model.ObjectModel;

import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseEvent;

/**
 * Use only this cell for marking the first columns of the table.
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class CheckBoxTableCell extends TableCell<ObjectModel, Boolean> {

  private final CheckBox checkBox = new CheckBox();
  private SimpleTableView tableView;


  /**
   * Constructor
   */
  public CheckBoxTableCell() {
    super();
  }


  public void setTableView(SimpleTableView tableView) {
    this.tableView = tableView;
  }


  @Override
  protected void updateItem(Boolean item, boolean empty) {
    super.updateItem(item, empty);
    setGraphic(null);
    if (!empty) {

      checkBox.setSelected(item == null ? false : item);
      final Node node = NodeHelper.wrapInGrowingHbox(checkBox);
      node.setStyle("-fx-padding:0 0 0 10;-fx-opacity:0.87;");
      setGraphic(node);

      getTableRow().addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
        // toOpposite();
      });

      // bindBidirectional checkbox and model value
      try {

        checkBox.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
          tableView.setHasSelected(checkBox.isSelected());
        });

        final BooleanProperty selected = ((ObjectModel) getTableRow().getItem()).selectedProperty();
        selected.bindBidirectional(checkBox.selectedProperty());
      } catch (final NullPointerException e) {
      }
    }
  }


  /**
   * All internal event on table will be fired!!.
   *
   * @param selected
   */
  public void toOpposite() {
    final boolean opposite = !checkBox.isSelected();
    checkBox.setSelected(opposite);
  }
}
