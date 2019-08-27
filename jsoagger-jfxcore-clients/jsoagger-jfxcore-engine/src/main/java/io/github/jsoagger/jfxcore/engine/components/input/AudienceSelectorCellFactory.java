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


import io.github.jsoagger.jfxcore.api.IEnumeratedValueModel;
import io.github.jsoagger.jfxcore.engine.model.EnumeratedValueModel;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class AudienceSelectorCellFactory implements Callback<ListView<EnumeratedValueModel>, ListCell<EnumeratedValueModel>> {

  /**
   * Constructor
   */
  public AudienceSelectorCellFactory() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public ListCell<EnumeratedValueModel> call(ListView<EnumeratedValueModel> param) {
    return cell(true);
  }


  /**
   * Buttoncell do not displays description, indeividual cell yes.
   *
   * @param withDescription
   * @return ListCell
   */
  public static ListCell<EnumeratedValueModel> cell(boolean withDescription) {
    return new ListCell<EnumeratedValueModel>() {

      @Override
      protected void updateItem(EnumeratedValueModel item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
          HBox box = new HBox();
          box.getStyleClass().add("audience-selector-cell-wrapper");

          Label icon = new Label();
          icon.getStyleClass().add("grey-500-icon");
          // GlyphsDude.setIcon(icon,
          // FontAwesomeIcon.valueOf(audience.getIcon()), "16");

          Label label = new Label();
          label.getStyleClass().add("audience-selector-cell-label");
          label.setText(item.getValue());

          box.getChildren().addAll(icon, label);
          setGraphic(box);
        }
      }
    };
  }


  public static ListCell<IEnumeratedValueModel> buttonCell(boolean withDescription) {
    return new ListCell<IEnumeratedValueModel>() {

      @Override
      protected void updateItem(IEnumeratedValueModel item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
          HBox box = new HBox();
          box.getStyleClass().add("audience-selector-cell-wrapper");

          Label icon = new Label();
          icon.getStyleClass().add("grey-500-icon");
          /// GlyphsDude.setIcon(icon,
          /// FontAwesomeIcon.valueOf(audience.getIcon()), "16");

          Label label = new Label();
          label.getStyleClass().add("audience-selector-button-cell-label");
          label.setText(item.getValue());

          box.getChildren().addAll(icon, label);
          setGraphic(box);
        }
      }
    };
  }
}
