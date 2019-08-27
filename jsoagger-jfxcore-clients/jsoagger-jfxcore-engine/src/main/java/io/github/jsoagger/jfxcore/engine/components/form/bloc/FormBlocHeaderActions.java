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

package io.github.jsoagger.jfxcore.engine.components.form.bloc;




import java.util.List;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.form.IFormBlocHeaderActions;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class FormBlocHeaderActions extends HBox implements IFormBlocHeaderActions {

  protected AbstractViewController controller;


  /**
   * Constructor
   */
  public FormBlocHeaderActions() {}


  /**
   * Constructor
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML blocConfig) {
    VLViewComponentXML headerActions = blocConfig.getComponentById("HeaderActions").orElse(null);
    NodeHelper.styleClassSetAll(getDisplay(), headerActions, "styleClass", "ep-form-bloc-title-pane-header-actions-wrapper");
    if (headerActions != null && headerActions.hasSubComponent()) {
      List<IBuildable> buildables = ComponentUtils.resolveAndGenerate((AbstractViewController) controller, headerActions.getSubcomponents());
      for(IBuildable e: buildables) {
        getChildren().add(e.getDisplay());
      }
    }
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }
}
