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

package io.github.jsoagger.jfxcore.platform.components.components.picker;



import java.util.List;
import java.util.function.Function;

import io.github.jsoagger.jfxcore.engine.components.picker.PickerCell;
import io.github.jsoagger.jfxcore.engine.model.ObjectModel;

import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class TypePickerListCell extends PickerCell<ObjectModel> {

  private final HBox layout = new HBox();
  private final Function<ObjectModel, List<ObjectModel>> loadChildrenFuntion;


  /**
   * Constructor
   */
  public TypePickerListCell(Function<ObjectModel, List<ObjectModel>> loadChildrenFuntion) {
    super();
    this.loadChildrenFuntion = loadChildrenFuntion;
  }


  /*
   * (non-Javadoc)
   *
   * @see javafx.scene.control.Cell#updateItem(java.lang.Object, boolean)
   */
  @Override
  protected void updateItem(ObjectModel item, boolean empty) {
    super.updateItem(item, empty);

    setGraphic(null);
    setText(null);
    setStyle("-fx-padding:0");

    if (!empty) {

      layout.setStyle("-fx-pref-height: 48; " + "-fx-background-color: transparent;" + "-fx-border-color: -external-border-color; " + "-fx-border-width: 0.03;"
          + "-fx-alignment: CENTER_LEFT; -fx-padding: 0 16 0 16;");

      final Hyperlink content = new Hyperlink();
      content.setStyle("-fx-text-fill: -primary-text-color; -fx-underline: false; -fx-font-size:16px;-fx-font-family:'Roboto Regular';");
      content.setFocusTraversable(false);
      content.setText(item.getName());
      content.setOnAction(e -> {
        loadChildrenFuntion.apply(item);
      });

      layout.getChildren().clear();
      layout.getChildren().add(content);
      setGraphic(layout);
    }
  }


  @Override
  public String getLabel() {
    return null;
  }
}
