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

package io.github.jsoagger.jfxcore.demoapp.comps;




import java.util.List;

import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class DemoDialogController extends StandardViewController {

  protected VBox pane = new VBox();


  /**
   * Constructor
   */
  public DemoDialogController() {
    super();
    pane.setStyle("-fx-alignment:TOP_CENTER;-fx-padding:64;-fx-spacing:64");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  protected void process() {
    super.process();
    processedView(pane);

    final VLViewComponentXML root = getRootComponent();
    if (root.hasSubComponent()) {
      for (final VLViewComponentXML group : root.getSubcomponents()) {

        if (group.hasSubComponent()) {
          final List<IBuildable> ts = ComponentUtils.resolveAndGenerate(this, group.getSubcomponents());
          final FlowPane p = new FlowPane();
          p.setVgap(20);
          p.setHgap(20);

          for(IBuildable e: ts) {
            p.getChildren().add(e.getDisplay());
          }
          pane.getChildren().add(p);
        }
      }
    }
  }
}
