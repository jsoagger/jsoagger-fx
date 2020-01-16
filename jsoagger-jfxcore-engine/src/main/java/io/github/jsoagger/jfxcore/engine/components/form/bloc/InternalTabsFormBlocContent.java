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



import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IFormFieldsetRow;
import io.github.jsoagger.jfxcore.api.form.IFormBlocContent;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class InternalTabsFormBlocContent extends StackPane implements IFormBlocContent {

  HBox flowPane = new HBox();


  /**
   * Constructor
   */
  public InternalTabsFormBlocContent() {}


  /**
   * {@inheritDoc}
   */
  @Override
  public void build(VLViewComponentXML blocConfig, IJSoaggerController controller) {
    flowPane.setStyle("-fx-padding:8;-fx-spacing:8");
    flowPane.setAlignment(Pos.CENTER);

    if (blocConfig.hasSubComponent()) {
      VLViewComponentXML subtabs = blocConfig.getComponentById("Tabs").orElse(null);
      if (subtabs != null && subtabs.hasSubComponent()) {
        List<IBuildable> buildables = ComponentUtils.resolveAndGenerate((AbstractViewController) controller, subtabs.getSubcomponents());
        for(IBuildable e: buildables) {
          flowPane.getChildren().add(e.getDisplay());
        }
      }
    }
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Node getDisplay() {
    return flowPane;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public List<IFormFieldsetRow> getRows() {
    return new ArrayList();
  }
}
