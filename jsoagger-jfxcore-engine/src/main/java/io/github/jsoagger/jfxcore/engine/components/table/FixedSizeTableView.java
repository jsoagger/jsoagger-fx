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

package io.github.jsoagger.jfxcore.engine.components.table;



import io.github.jsoagger.jfxcore.components.table.FixedSizeTableViewSkin3;

import javafx.scene.control.Skin;
import javafx.scene.control.TableView;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class FixedSizeTableView<S> extends TableView<S> {

  private final boolean showTableHeader;

  /**
   * @param showTableHeader
   */
  public FixedSizeTableView(boolean showTableHeader) {
    super();
    this.showTableHeader = showTableHeader;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Skin<?> createDefaultSkin() {
    // FixedSizeTableViewSkin skin = new FixedSizeTableViewSkin(this,
    // showTableHeader);
    final FixedSizeTableViewSkin3 skin = new FixedSizeTableViewSkin3(this, showTableHeader);
    return skin;
  }
}
