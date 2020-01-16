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

package io.github.jsoagger.jfxcore.engine.components.wizard;


import java.util.Optional;

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.wizard.IWizardStepFooter;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.security.CriteriaContext;
import io.github.jsoagger.jfxcore.engine.components.toolbar.AbstractToolbar;
import io.github.jsoagger.jfxcore.engine.components.toolbar.vtoolbar.BasicVToolbar;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;
import io.github.jsoagger.jfxcore.engine.utils.ToolbarUtils;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;

/**
 * The footer of wizard steb is an {@link AbstractToolbar}
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class WizardStepFooter extends StackPane implements IWizardStepFooter {

  //private static final Logger logR = LoggerFactory.getLogger(WizardStepFooter.class);

  private static final String ACTIONS = "Actions";
  private static final String WIZARD_STEP_FOOTER = "wizard-step-footer";

  private Optional<AbstractToolbar> toolbar = Optional.empty();
  private VLViewComponentXML configuration;
  private AbstractViewController controller;


  /**
   * Constructor
   */
  public WizardStepFooter() {
    getStyleClass().add(WIZARD_STEP_FOOTER);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.configuration = configuration;
    this.controller = (AbstractViewController) controller;

    if (!toolbar.isPresent()) {
      toolbar = ToolbarUtils.buildToolbar((AbstractViewController) controller, this);
    }

    AbstractToolbar buildable = toolbar.orElse(null);
    if(buildable != null && buildable instanceof BasicVToolbar) {
    	getChildren().add(buildable.getDisplay());
    }
    else {
    	if (buildable != null) {
    	      ScrollPane sc = NodeHelper.makeCentralScrollable(buildable.getDisplay());
    	      sc.setStyle("-fx-padding:8");
    	      getChildren().add(sc);
    	}
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public VLViewComponentXML getToolbarConfiguration() {
    final VLViewComponentXML actionsModelConf = configuration.getComponentById(ACTIONS).orElse(null);
    VLViewComponentXML result = null;

    if (actionsModelConf != null) {
      String model = actionsModelConf.getModel();
      if (model != null) {
        result = ComponentUtils.resolveDefinition(controller, model).orElse(null);
        return result;
      }
    }

    return result;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public CriteriaContext criteriaContext() {
    return null;
  }
}
