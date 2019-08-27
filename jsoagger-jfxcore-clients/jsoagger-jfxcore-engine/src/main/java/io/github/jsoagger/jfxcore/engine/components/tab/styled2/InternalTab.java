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

package io.github.jsoagger.jfxcore.engine.components.tab.styled2;




import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class InternalTab extends StackPane {

  PseudoClass selected = PseudoClass.getPseudoClass("selected");
  /**
   * The title of the tab
   */
  private SimpleStringProperty title = new SimpleStringProperty();
  private final SimpleStringProperty counter = new SimpleStringProperty();
  /**
   * Toolbar of tab, if wizardConfiguration is set to true
   */
  private final HBox toolbar = new HBox();
  /**
   * The content of the tab
   */
  private Node content;


  public InternalTab(final String title) {
    this.title.set(title);
    getStyleClass().setAll("InternalTab-primary");

    toolbar.setStyle("-fx-background-color: white; " + "-fx-border-color: #e5e5e5; " + "-fx-border-width: 0 0 1 0;" + "-fx-padding: 15 5 8 5;");
    toolbar.setSpacing(10);
    toolbar.setPrefHeight(25);

    toolbar.setVisible(false);
    toolbar.setManaged(false);

    setStyle("-fx-min-width: 100;" + "-fx-padding: 0 10 0 10");
  }


  public InternalTab(final String title, final Node content) {
    this(title);
    this.content = content;
  }


  public void setActions(List<Node> actions) {
    if (actions != null) {

      toolbar.setVisible(!actions.isEmpty());
      toolbar.setManaged(!actions.isEmpty());

      for (final Node node : actions) {
        if (node instanceof Button) {
          node.setStyle("-fx-text-fill: #3b5998;" + "-fx-font-family: helvetica, arial, sans-serif;" + "-fx-font-size:12px;-fx-font-weight:bold;"
              + "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.3), 2,0.0,0,0);");
        }

        toolbar.getChildren().add(node);
      }
    }

    if (!toolbar.isManaged()) {

    }
  }


  public void hideToolbar() {
    toolbar.setVisible(false);
    toolbar.setManaged(false);
  }


  public void setContent(Node content) {
    this.content = content;
  }


  public void select(boolean select) {
    pseudoClassStateChanged(selected, select);
  }


  public Node getTitle() {
    final HBox tabTileContainer = new HBox();
    tabTileContainer.setAlignment(Pos.CENTER);
    tabTileContainer.setSpacing(5);

    final Label titleLabel = new Label();
    titleLabel.textProperty().bind(title);
    tabTileContainer.getChildren().add(titleLabel);
    titleLabel.setStyle("-fx-font-size: 13px;-fx-font-weight: bold;" + "-fx-text-fill: #3b5998;" + "-fx-font-family: helvetica, arial, sans-serif");

    final Label counterLabel = new Label();
    counterLabel.textProperty().set(" ");
    tabTileContainer.getChildren().add(counterLabel);

    setAlignment(Pos.CENTER);
    getChildren().addAll(tabTileContainer);
    return this;
  }


  public void setTitle(final SimpleStringProperty title) {
    this.title = title;
  }


  public Node getContent() {
    final VBox box = new VBox();
    VBox.setVgrow(box, Priority.ALWAYS);
    box.setSpacing(25);

    if (content == null) {
      box.getChildren().addAll(toolbar, new HBox());
    } else {
      box.getChildren().addAll(toolbar, content);
      VBox.setVgrow(content, Priority.ALWAYS);
    }

    return box;
  }

}
