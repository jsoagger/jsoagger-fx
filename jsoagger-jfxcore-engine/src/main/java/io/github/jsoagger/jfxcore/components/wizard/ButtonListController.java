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

package io.github.jsoagger.jfxcore.components.wizard;



import java.util.List;

import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 14 mars 2018
 */
public class ButtonListController extends StandardViewController {

  private VBox layout = new VBox();


  /**
   * Default Constructor
   */
  public ButtonListController() {
    layout.setAlignment(Pos.TOP_CENTER);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  protected void process() {
    super.process();

    VLViewComponentXML root = getRootComponent();

    if ((root != null) && root.hasSubComponent()) {

      for (VLViewComponentXML group : root.getSubcomponents()) {

        FlowPane layout = new FlowPane();
        layout.setAlignment(Pos.TOP_CENTER);
        this.layout.getChildren().add(layout);
        layout.setVgap(20);
        layout.setHgap(20);
        layout.setStyle("-fx-padding:32");
        List<IBuildable> bs = ComponentUtils.resolveAndGenerate(this, group.getSubcomponents());
        for (IBuildable b : bs) {
          layout.getChildren().add(b.getDisplay());
        }
      }
    }

    processedView(layout);
  }
}
