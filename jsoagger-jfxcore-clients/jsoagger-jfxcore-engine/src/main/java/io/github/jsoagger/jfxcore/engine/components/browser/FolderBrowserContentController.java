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

package io.github.jsoagger.jfxcore.engine.components.browser;




import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;

import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class FolderBrowserContentController extends StandardViewController {

  private StackPane stackPane = new StackPane();


  /**
   * Constructor
   */
  public FolderBrowserContentController() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  protected void process() {
    super.process();

    VLViewComponentXML configuration = getRootComponent();
    IBuildable tableview = new FolderContentBrowserTableview();
    tableview.buildFrom((IJSoaggerController) this, configuration);

    stackPane.setStyle("-fx-background-color:-background-color;" + "-fx-padding:16 16 0 16;");

    StackPane shadowedStack = new StackPane();
    stackPane.getChildren().add(shadowedStack);
    shadowedStack.getChildren().add(tableview.getDisplay());
    shadowedStack.setStyle("-fx-effect:dropshadow(three-pass-box, -grey-color-500, 2.0, 0.2, 0.0, 0.0);");

    processedView(stackPane);
  }
}
