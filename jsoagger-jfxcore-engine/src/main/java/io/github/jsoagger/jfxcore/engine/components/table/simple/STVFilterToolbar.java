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

package io.github.jsoagger.jfxcore.engine.components.table.simple;




import javafx.scene.layout.HBox;

/**
 * Simple Table View filtern toolbar.
 *
 * @author Administrator
 *
 */
public class STVFilterToolbar extends HBox {

  /**
   * Constructor
   */
  public STVFilterToolbar() {
    super();
    setStyle("-fx-background-color: white;" + "-fx-border-color: -border-color;" + "-fx-border-width: 0 0 0.2 0;" + "-fx-padding: 0 16 0 16;" + "-fx-min-height: 52;" + "-fx-alignment: CENTER_RIGHT;"
        + "-fx-spacing: 16;");
  }
}
