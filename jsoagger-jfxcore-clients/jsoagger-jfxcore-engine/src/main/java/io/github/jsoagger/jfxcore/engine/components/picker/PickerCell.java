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

package io.github.jsoagger.jfxcore.engine.components.picker;




import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * @author Administrator
 *
 */
public abstract class PickerCell<T extends Object> extends ListCell<T> {

  private final HBox layout = new HBox();

  private final CheckBox checkBox = new CheckBox();
  private final SimpleBooleanProperty selected = new SimpleBooleanProperty(false);


  /**
   * Constructor
   */
  public PickerCell() {
    super();
  }


  /*
   * (non-Javadoc)
   *
   * @see javafx.scene.control.Cell#updateItem(java.lang.Object, boolean)
   */
  @Override
  protected void updateItem(T item, boolean empty) {
    super.updateItem(item, empty);

    setGraphic(null);
    setText(null);
    setStyle("-fx-padding:0");

    if (!empty) {
      layout.setStyle("-fx-background-color: transparent;" + "-fx-border-color: -external-border-color; " + "-fx-border-width: 0.03;" + "-fx-alignment: CENTER_LEFT; " + "-fx-padding: 4 16 4 16; "
          + "-fx-spacing: 22;");

      // the account label
      final Label userLabel = new Label();
      userLabel.getStyleClass().add("-primary-label");
      userLabel.setFocusTraversable(false);
      userLabel.setText(getLabel());

      checkBox.setOnAction(e -> {
        if (selected.get()) {
          selected.set(false);
        } else {
          selected.set(true);
        }
      });

      layout.getChildren().clear();
      setGraphic(layout);

      // circle icon
      final StackPane circleStack = new StackPane();

      final Circle circle = new Circle(15);
      circle.setFill(Color.color(0.52, 0.80, 0.92));

      final Label label = new Label();
      label.setText(getLabel().substring(0, 1).toUpperCase());
      label.setStyle("-fx-text-fill: white;" + " -fx-underline: false; " + "-fx-font-family:'Roboto Bold';");

      circleStack.getChildren().addAll(circle, label);
      layout.getChildren().addAll(checkBox, circleStack, userLabel);
    }
  }


  public SimpleBooleanProperty isSelectedProperty() {
    return selected;
  }


  public abstract String getLabel();
}
