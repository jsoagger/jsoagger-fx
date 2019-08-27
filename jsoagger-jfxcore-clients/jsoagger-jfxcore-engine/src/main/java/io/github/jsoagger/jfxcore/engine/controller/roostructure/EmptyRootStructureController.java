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

package io.github.jsoagger.jfxcore.engine.controller.roostructure;




import java.util.Optional;

import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;
import io.github.jsoagger.jfxcore.engine.controller.utils.WizardViewUtils;

import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class EmptyRootStructureController extends RootStructureController {

  private static final String ROOT_VIEW = "RootView";
  private StackPane container = new StackPane();


  /**
   * Constructor
   */
  public EmptyRootStructureController() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  protected void process() {
    super.process();
    processedView(container);
    buildRootView();
  }


  /**
   * Build the root view declared in the XML wizardConfiguration file
   */
  public void buildRootView() {
    Optional<VLViewComponentXML> rootviewCfg = getRootComponent().getComponentById(ROOT_VIEW);
    rootviewCfg.ifPresent(config -> {
      String rootviewid = config.getPropertyValue("id");
      if (StringUtils.isNotBlank(rootviewid)) {
        if (rootviewid.startsWith("wizard#")) {
          WizardViewUtils.forWizardId(this, null, StringUtils.substringAfter(rootviewid, "wizard#"));
        } else {
          StandardViewUtils.forIdAndLayout(this, rootviewid);
        }
      }
    });
  }
}
