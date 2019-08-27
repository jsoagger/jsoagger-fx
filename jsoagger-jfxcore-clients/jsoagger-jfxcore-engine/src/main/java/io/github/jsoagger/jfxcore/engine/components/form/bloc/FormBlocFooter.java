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
import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.ICriteriaContext;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IToolbarHolder;
import io.github.jsoagger.jfxcore.api.form.IFormBlocFooter;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * Footer of {@link FormBlocTitlePane} impleted by a toolbar with actions.
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class FormBlocFooter implements IFormBlocFooter, IToolbarHolder {

  private HBox display;
  private VLViewComponentXML componentXML;
  private AbstractViewController controller;


  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.controller = (AbstractViewController) controller;
    if (configuration != null) {
      Optional<VLViewComponentXML> footer = configuration.getComponentById("Footer");
      if (footer.isPresent()) {
        componentXML = footer.get();
        List<IBuildable> buildables = ComponentUtils.resolveAndGenerate(this.controller, componentXML.getSubcomponents());
        if (buildables.size() > 0) {
          display = new HBox();
          display.managedProperty().bind(display.visibleProperty());

          for(IBuildable e: buildables) {
            display.getChildren().add(e.getDisplay());
          }
          NodeHelper.styleClassAddAll(display, componentXML, "styleClass", "ep-bloc-footer");
        }
      }
    }
  }


  @Override
  public Node getDisplay() {
    return display;
  }


  @Override
  public VLViewComponentXML getToolbarConfiguration() {
    return componentXML;
  }


  @Override
  public ICriteriaContext criteriaContext() {
    return controller.viewContext().getCriterias();
  }
}
