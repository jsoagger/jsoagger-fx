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



import java.util.Optional;

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.wizard.IWizardFooter;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.security.CriteriaContext;
import io.github.jsoagger.jfxcore.engine.components.toolbar.AbstractToolbar;
import io.github.jsoagger.jfxcore.engine.components.wizard.WizardFooter;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ToolbarUtils;

import javafx.geometry.Pos;
import javafx.scene.Node;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class LoginWizardFooter extends WizardFooter implements IWizardFooter {

  private static final String BOTTOM_ACTIONS = "BottomActions";

  private VLViewComponentXML configuration;


  /**
   * Constructor
   */
  public LoginWizardFooter() {
    super();
    getStyleClass().setAll("login-wizard-footer");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.configuration = configuration;

    setAlignment(Pos.CENTER);

    Optional<AbstractToolbar> toolbar = ToolbarUtils.buildToolbar((AbstractViewController) controller, this);
    toolbar.ifPresent(tl -> {
      getChildren().add(tl.getDisplay());
    });
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public VLViewComponentXML getToolbarConfiguration() {
    Optional<VLViewComponentXML> bottomActions = configuration.getComponentById(BOTTOM_ACTIONS);
    VLViewComponentXML config = bottomActions.orElse(null);
    return config;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public CriteriaContext criteriaContext() {
    return null;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }
}
