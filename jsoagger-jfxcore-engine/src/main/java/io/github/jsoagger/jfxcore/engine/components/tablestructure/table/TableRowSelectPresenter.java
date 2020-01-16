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

package io.github.jsoagger.jfxcore.engine.components.tablestructure.table;



import io.github.jsoagger.jfxcore.api.ICellPresenter;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.table.cell.EpTableCell;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.AbstractTableStructure;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.CellPresenterImpl;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableViewController;

import javafx.beans.value.ChangeListener;
import javafx.css.PseudoClass;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;

public class TableRowSelectPresenter extends CellPresenterImpl implements ICellPresenter {

  private CheckBox checkBox;
  private AbstractTableStructure ts;
  private boolean isClearing = false;


  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration, Object forModel) {
    if (cell instanceof EpTableCell && controller instanceof FullTableViewController) {
      checkBox = new CheckBox();
      checkBox.setSelected(false);
      ts = (AbstractTableStructure) ((FullTableViewController) controller).processedElement();
      checkBox.visibleProperty().bind(ts.modifyingProperty());

      EventHandler ev1 = this::handleRowSelection;
      EventHandler ev2 = this::handleRowSelection;

      // because when the chackbox is clicked, a row click is fired.
      if(AbstractApplicationRunner.isDesktop()) {
        checkBox.selectedProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> selectRow(newValue));
      }
      else {
        ((EpTableCell) cell).getTableRow().addEventFilter(MouseEvent.MOUSE_CLICKED, ev1);
      }

      // ((EpTableCell) cell).getTableRow().addEventFilter(TouchEvent.TOUCH_RELEASED, ev2);

    }

    return checkBox;
  }


  public void selectCheckbox(boolean s) {
    checkBox.setSelected(s);
  }


  /**
   * One click select, 2 clicks other.
   *
   * @param event
   */
  public void handleRowSelection(Event event) {
    if (checkBox.isVisible()) {
      if (((MouseEvent) event).getClickCount() == 1 && !event.isConsumed()) {
        final boolean selected = !checkBox.isSelected();
        checkBox.setSelected(selected);
        event.consume();
      }
    }
  }


  private void selectRow(boolean selected) {
    ((EpTableCell) cell).getTableRow().pseudoClassStateChanged(PseudoClass.getPseudoClass("ep-selected"), selected);
    if (!isClearing) {
      if (selected) {
        ts.addToSelection(this);
      } else {
        ts.removeFromSelection(this);
      }
    }

    isClearing = false;
  }


  public void clearSelection() {
    isClearing = true;
    checkBox.setSelected(false);
  }
}
