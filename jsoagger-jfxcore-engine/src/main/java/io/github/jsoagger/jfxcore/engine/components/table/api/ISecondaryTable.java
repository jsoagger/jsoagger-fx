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

package io.github.jsoagger.jfxcore.engine.components.table.api;



import java.util.List;

import javafx.scene.control.TableColumnBase;
import javafx.scene.control.ToolBar;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public interface ISecondaryTable {

  @SuppressWarnings("rawtypes")
  public abstract void setColumns(List<TableColumnBase> columns);


  public abstract void process();


  public abstract ToolBar getToolbar();


  /**
   * Set the toolbar button actions
   *
   * @param actions
   */
  public default void setActions(Object o) {

    // if (getToolbar() != null) {
    //
    // final HBox hBox = new HBox();
    // hBox.setAlignment(Pos.CENTER_LEFT);
    // getToolbar().getItems().add(hBox);
    //
    // hBox.getChildren().add(action.getDisplay());
    //
    // if (action.getConfiguration().isSeparatorAfter()) {
    // final Separator separator = new Separator();
    // separator.setOrientation(Orientation.VERTICAL);
    // hBox.getChildren().add(separator);
    // }
    // });
    // }
  }
}
