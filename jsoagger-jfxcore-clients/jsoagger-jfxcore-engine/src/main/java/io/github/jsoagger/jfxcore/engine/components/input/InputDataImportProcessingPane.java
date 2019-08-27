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

package io.github.jsoagger.jfxcore.engine.components.input;


import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class InputDataImportProcessingPane extends AbstractInputComponent {

  private final VBox pane = new VBox();


  /**
   * Constructor
   */
  public InputDataImportProcessingPane() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);
    pane.setStyle("-fx-border-width: 1 1 1 4;" + "-fx-min-height: 200;" + "-fx-spacing:16;" + "-fx-padding: 16;" + "-fx-alignment: TOP_LEFT;"
        + "-fx-border-color: -grey-color-200 -grey-color-200 -grey-color-200 -accent-color;" + "-fx-background-color: #f1f1f1;");
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Node getDisplay() {
    return pane;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Node getComponent() {
    return pane;
  }
}
