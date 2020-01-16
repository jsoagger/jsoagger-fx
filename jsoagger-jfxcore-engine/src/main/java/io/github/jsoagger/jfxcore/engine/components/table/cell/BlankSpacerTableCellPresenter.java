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



import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.CellPresenterImpl;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 13 févr. 2018
 */
public class BlankSpacerTableCellPresenter extends CellPresenterImpl {

  IJSoaggerController controller;
  VLViewComponentXML configuration;

  /**
   * Default Constructor
   */
  public BlankSpacerTableCellPresenter() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node present(IJSoaggerController controller, VLViewComponentXML configuration, Object item) {
    this.configuration = configuration;
    this.controller = controller;
    StackPane pane = new StackPane();
    pane.getStyleClass().add("ep-table-blank-spacer");
    return pane;
  }


  @Override
  public void setConfiguration(VLViewComponentXML contentConfiguration) {
    this.configuration = contentConfiguration;
  }


  @Override
  public void setController(IJSoaggerController controller) {
    this.controller = controller;
  }
}
