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

package io.github.jsoagger.jfxcore.engine.controller.login.component;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;

import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class LoginWizardFooterToolbar extends FlowPane implements IBuildable {

  private static final String CENTER_ACTIONS = "centerActions";
  private static final String RIGHT_ACTIONS = "rightActions";
  private static final String LEFT_ACTIONS = "leftActions";


  /**
   * Constructor
   */
  public LoginWizardFooterToolbar() {
    super();
    getStyleClass().add("login-wizard-footer-toolbar");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML actionsModelConfig) {
    List<IBuildable> all = new ArrayList<>();

    Optional<VLViewComponentXML> leftActionsCfg = actionsModelConfig.getComponentById(LEFT_ACTIONS);
    leftActionsCfg.ifPresent(config -> {
      List<IBuildable> buttons = ComponentUtils.resolveAndGenerate((AbstractViewController) controller, config.getSubcomponents());
      all.addAll(buttons);
    });

    Optional<VLViewComponentXML> rightActionsCfg = actionsModelConfig.getComponentById(RIGHT_ACTIONS);
    rightActionsCfg.ifPresent(config -> {
      List<IBuildable> buttons = ComponentUtils.resolveAndGenerate((AbstractViewController) controller, config.getSubcomponents());
      all.addAll(buttons);
    });

    Optional<VLViewComponentXML> centerActionsCfg = actionsModelConfig.getComponentById(CENTER_ACTIONS);
    centerActionsCfg.ifPresent(config -> {
      List<IBuildable> buttons = ComponentUtils.resolveAndGenerate((AbstractViewController) controller, config.getSubcomponents());
      all.addAll(buttons);
    });

    for(IBuildable b: all) {
      getChildren().add(b.getDisplay());
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }
}
