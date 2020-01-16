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



import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.client.components.ComponentToButtonBaseHelper;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class DemoDashboardItem implements IBuildable {

  private HBox layout = new HBox();
  private VBox root = new VBox();
  private Label title = new Label();
  private Label count = new Label();
  private boolean isMobile = false;

  private boolean isLoadingChild = false;


  /*
   * (non-Javadoc)
   *
   * @see io.github.jsoagger.jfxcore.api.IDisplayable#getDisplay()
   */
  @Override
  public Node getDisplay() {
    return layout;
  }


  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    root.getChildren().addAll(title, count);

    title.getStyleClass().add("demo-dash-item-title");
    count.getStyleClass().add("demo-dash-item-count");

    NodeHelper.setTitle(title, configuration, (AbstractViewController) controller);
    count.setText(configuration.getPropertyValue("value"));

    layout.getStyleClass().addAll("demo-dash-item", "hand-hover");
    layout.getChildren().add(root);
    NodeHelper.setHgrow(root);

    Optional<VLViewComponentXML> handler = configuration.getComponentById("Handler");
    handler.ifPresent(h -> {
      if (StringUtils.isNotBlank(configuration.getPropertyValue("icon"))) {
        Button action = new JFXButton();
        action.getStyleClass().remove("jfx-button");
        action.getStyleClass().remove("button");
        action.getStyleClass().add("button-transparent-border-transparent");
        IconUtils.setFontIcon(configuration.getPropertyValue("icon"), action);
        layout.getChildren().add(action);

        // Do not trigger an action event when button is clicked because this event will
        // be forwarded to the layout and its event filter
        // so the view will be builded twice.
        // ComponentToButtonBaseHelper.setOnAction(configuration, action, controller);
      }

      isMobile = AbstractApplicationRunner.isMobile();
      if(isMobile) {
        // avoid multiple touch loading same view multiple times!!
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1500), ae -> isLoadingChild = false));

        layout.addEventFilter(TouchEvent.TOUCH_RELEASED, ev -> {
          if(!AbstractApplicationRunner.isApplicationScrolling() && !isLoadingChild && ev.getTouchCount() == 1) {
            isLoadingChild = true;
            timeline.play();
            ComponentToButtonBaseHelper.setButtonActions((AbstractViewController) controller, h, layout, ev, null);
          }
        });
      }
      else {
        layout.addEventFilter(MouseEvent.MOUSE_CLICKED, ev -> {
          if (ev.getClickCount() == 2) {
            ComponentToButtonBaseHelper.setButtonActions((AbstractViewController) controller, h, layout, ev, null);
          }
        });
      }
    });
  }
}
