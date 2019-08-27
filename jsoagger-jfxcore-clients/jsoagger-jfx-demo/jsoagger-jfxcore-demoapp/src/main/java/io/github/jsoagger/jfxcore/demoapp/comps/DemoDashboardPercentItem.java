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



import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class DemoDashboardPercentItem implements IBuildable {

  private HBox root = new HBox();
  private VBox left = new VBox();
  private VBox right = new VBox();
  private Label title = new Label();
  private Label count = new Label();


  /*
   * (non-Javadoc)
   *
   * @see io.github.jsoagger.jfxcore.api.IDisplayable#getDisplay()
   */
  @Override
  public Node getDisplay() {
    return root;
  }


  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    root.getChildren().addAll(left, right);
    NodeHelper.setHgrow(left);

    root.getStyleClass().add("demo-dash-item");
    right.getStyleClass().add("demo-dash-item-right");

    left.getChildren().addAll(title, count);
    left.getStyleClass().add("demo-dash-item-left");

    title.getStyleClass().add("demo-dash-item-title");
    count.getStyleClass().add("demo-dash-item-count");

    NodeHelper.setTitle(title, configuration, (AbstractViewController) controller);
    count.setText(configuration.getPropertyValue("value"));

    Label icon = IconUtils.getFontIcon(configuration.getPropertyValue("icon"));
    right.getChildren().add(icon);
  }
}
