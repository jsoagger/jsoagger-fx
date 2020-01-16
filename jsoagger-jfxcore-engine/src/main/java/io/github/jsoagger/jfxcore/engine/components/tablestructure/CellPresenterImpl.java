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

package io.github.jsoagger.jfxcore.engine.components.tablestructure;



import io.github.jsoagger.jfxcore.api.ICellPresenter;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.AbstractModelPresenter;

import javafx.scene.control.IndexedCell;
import javafx.scene.control.TableCell;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 13 févr. 2018
 */
public abstract class CellPresenterImpl extends AbstractModelPresenter implements ICellPresenter {

  protected IndexedCell cell;


  /**
   * Default Constructor
   */
  public CellPresenterImpl() {}


  public void setCell(IndexedCell cell) {
    this.cell = cell;
  }


  /**
   * Getter of cell
   *
   * @return the cell
   */
  public IndexedCell getCell() {
    return cell;
  }


  /**
   * Setter of cell
   *
   * @param cell the cell to set
   */
  public void setCell(TableCell cell) {
    this.cell = cell;
  }

}
