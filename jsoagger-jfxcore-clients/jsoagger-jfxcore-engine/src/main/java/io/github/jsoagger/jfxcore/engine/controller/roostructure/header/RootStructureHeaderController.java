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

package io.github.jsoagger.jfxcore.engine.controller.roostructure.header;




import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class RootStructureHeaderController extends StandardViewController {

  @FXML
  protected HBox headerTopSectionHbox;
  @FXML
  protected HBox headerBottomSectionHbox;


  /**
   * Constructor
   */
  public RootStructureHeaderController() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  protected void process() {
    super.process();

    headerBottomSectionHbox.managedProperty().bind(headerBottomSectionHbox.visibleProperty());
    headerTopSectionHbox.managedProperty().bind(headerTopSectionHbox.visibleProperty());

    VLViewComponentXML rootComp = getRootComponent();
    if (rootComp != null) {

      String headerTopSection = rootComp.getPropertyValue("headerTopSection");
      if (StringUtils.isNotBlank(headerTopSection)) {
        StandardViewController top = StandardViewUtils.forId(getRootStructure(), null, headerTopSection);
        headerTopSectionHbox.getChildren().add(top.processedView());
      }

      String headerBottomSection = rootComp.getPropertyValue("headerBottomSection");
      if (StringUtils.isNotBlank(headerTopSection)) {
        StandardViewController bottom = StandardViewUtils.forId(getRootStructure(), null, headerBottomSection);
        headerBottomSectionHbox.getChildren().add(bottom.processedView());
      }
    }

    if (headerTopSectionHbox.getChildren().size() == 0) {
      headerTopSectionHbox.setVisible(false);
    }

    if (headerBottomSectionHbox.getChildren().size() == 0) {
      headerBottomSectionHbox.setVisible(false);
    }
  }
}
