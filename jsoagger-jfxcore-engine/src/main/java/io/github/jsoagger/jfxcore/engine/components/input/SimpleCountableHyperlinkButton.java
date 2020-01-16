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

package io.github.jsoagger.jfxcore.engine.components.input;



import java.text.NumberFormat;

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.NotifiableButtonController;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * Should be used along with {@link NotifiableButtonController} witch is responsible of fetching
 * datas.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class SimpleCountableHyperlinkButton extends SimpleHyperlinkButton {

  private final AnchorPane container = new AnchorPane();

  private final Label label = new Label();
  private final SimpleIntegerProperty count = new SimpleIntegerProperty(0);
  private final StackPane iconContainer = new StackPane();


  /**
   * Constructor
   */
  public SimpleCountableHyperlinkButton() {
    super();
    container.getStyleClass().add("countable-button-container");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);
    AnchorPane.setTopAnchor(iconContainer, -2.0);
    AnchorPane.setRightAnchor(iconContainer, 8.0);

    AnchorPane.setTopAnchor(link, 4.0);
    AnchorPane.setLeftAnchor(link, 0.0);
    AnchorPane.setRightAnchor(link, 0.0);

    container.getChildren().addAll(link);
    container.getChildren().add(label);

    // buildCircleIcon();
    label.getStyleClass().add("ep-countable-button-count");
    label.visibleProperty().bind(Bindings.greaterThan(count, 0));
    Bindings.bindBidirectional(label.textProperty(), count, NumberFormat.getInstance());
  }


  protected void buildCircleIcon() {
    iconContainer.setAlignment(Pos.CENTER);
    iconContainer.setAlignment(Pos.CENTER);
    iconContainer.getChildren().addAll(label);
  }


  public SimpleIntegerProperty countProperty() {
    return count;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Node getDisplay() {
    return container;
  }
}
