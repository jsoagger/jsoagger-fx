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

package io.github.jsoagger.jfxcore.engine.components.header.comps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class ScrollableContent extends VBox implements IBuildable {

  private StackPane currentContent = new StackPane();
  private HBox pagination = new HBox();
  private List<Node> content = new ArrayList<>();
  private int currentIndex = 0;

  public ScrollableContent() {
    super();
    if(AbstractApplicationRunner.isDesktop()) {
      setStyle("-fx-spacing:64;-fx-alignment:CENTER;");
    }
    else {
      setStyle("-fx-spacing:16;-fx-alignment:CENTER;");
    }
    NodeHelper.setVgrow(this);

    currentContent.getChildren().add(NodeHelper.getProcessingIndicator());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    getChildren().add(currentContent);
    getChildren().add(pagination);

    pagination.addEventFilter(MouseEvent.MOUSE_CLICKED, ev -> scrollRight());
    currentContent.addEventFilter(MouseEvent.MOUSE_CLICKED, clickevent);
    // currentContent.addEventFilter(TouchEvent.TOUCH_MOVED, clickevent);

    currentContent.setAlignment(Pos.CENTER);
    NodeHelper.setVgrow(currentContent);
    pagination.setStyle("-fx-spacing:10;-fx-alignment:CENTER;-fx-padding:8 0 8 0;");

  }

  public void setContent(Node... nodes) {
    Platform.runLater(() -> {
      setContent(Arrays.asList(nodes));
    });
  }

  public void setContent(List<Node> nodes) {
    content.clear();
    content.addAll(nodes);
    currentIndex = 0;
    Platform.runLater(() -> {
      if (content.size() > 0 && currentIndex < content.size()) {
        display(content.get(currentIndex));
        currentIndex++;
      }
    });
  }

  private void paginate(int currentIndex) {
    pagination.getChildren().clear();
    for (int i = 0; i < content.size(); i++) {
      Label indicator = new Label();
      pagination.getChildren().add(indicator);
      if (currentIndex == i) {
        IconUtils.setFontIcon("fa-circle:14", indicator);
      } else {
        IconUtils.setFontIcon("fa-circle-o:14", indicator);
      }
    }
  }

  public void scrollLeft() {
    if (currentIndex < 0) {
      if (content.size() > 1) {
        currentIndex = content.size() - 1;
      } else {
        currentIndex = 0;
      }
    }

    if (content.size() > 0) {
      display(content.get(currentIndex));
      currentIndex--;
    }
  }

  public void scrollRight() {
    if (currentIndex >= content.size()) {
      currentIndex = 0;
    }

    if (content.size() > 0) {
      display(content.get(currentIndex));
      currentIndex++;
    }
  }

  protected void display(Node node) {
    currentContent.getChildren().clear();
    currentContent.getChildren().add(node);
    paginate(currentIndex);
    node.getStyleClass().add("hand-hover");
  }

  /**
   */
  @Override
  public Node getDisplay() {
    return this;
  }

  /**
   * Mouse click event handle on the imageview or parent pane content
   */
  private EventHandler<MouseEvent> clickevent = new EventHandler<MouseEvent>() {

    @Override
    public void handle(MouseEvent ev) {
      if (ev.getClickCount() == 2) {
        scrollRight();
      }
    }
  };
}
