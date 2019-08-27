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



import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Layout the content of the {@link AbstractTableStructure} inside {@link BorderPane}. Displays only
 * the header and the content. No pagination and toolbar displayed.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 13 févr. 2018
 */
public class SimpleTableStructureLayoutManager {

  private BorderPane layout = new BorderPane();


  /**
   * Default Constructor
   */
  public SimpleTableStructureLayoutManager() {}


  public void layout(AbstractTableStructure structure) {
    if (structure.getHeader() != null || structure.getToolbar().isPresent()) {
      if (structure.getHeader() != null && structure.getToolbar().isPresent()) {
        VBox header = new VBox();
        header.getChildren().addAll(structure.getHeader().getDisplay(), structure.getToolbar().get());
        layout.setTop(header);
      } else if (structure.getToolbar().isPresent()) {
        layout.setTop(structure.getToolbar().get());
      } else {
        layout.setTop(structure.getHeader().getDisplay());
      }
    }

    if (structure.getTableStructure() != null) {
      layout.setCenter(structure.getTableStructure());
    }
  }


  public Node getDisplay() {
    return layout;
  }
}
