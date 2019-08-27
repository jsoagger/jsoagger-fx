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

package io.github.jsoagger.jfxcore.components.actions;



import java.util.Optional;
import java.util.function.Function;

import io.github.jsoagger.jfxcore.engine.client.apiimpl.AbstractAction;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.components.css.StyleSheetsManager;
import io.github.jsoagger.jfxcore.engine.components.dialog.impl.OkCancelDialog;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.model.EnumeratedValueModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.util.Callback;

public class DoChangeDefaultThemeAction extends AbstractAction implements IAction {

  /**
   * Constuctor
   */
  public DoChangeDefaultThemeAction() {}


  /**
   * {@inheritDoc}
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public void execute(IActionRequest actionRequest, Optional<IActionResult> previousActionResult) {
    final ComboBox<EnumeratedValueModel> combobox = new ComboBox<>();
    final ObservableList<EnumeratedValueModel> items = buildItems();
    combobox.setItems(items);
    combobox.getSelectionModel().select(0);

    combobox.setMaxWidth(400);

    final Callback cellFactory = param -> new ListCell<EnumeratedValueModel>() {

      @Override
      protected void updateItem(EnumeratedValueModel item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
          if (item != null && item.getSavedValue() == null) {
            setText(item.getValue());
          } else {
            setText(item.getValue());
          }
        }
      }
    };
    combobox.setCellFactory(cellFactory);

    final Function<Object, Object> okCallback = t -> {
      final EnumeratedValueModel m = combobox.getSelectionModel().getSelectedItem();
      if (m != null) {
        final String val = m.getSavedValue();
        final StyleSheetsManager sheetsManager = (StyleSheetsManager) Services.getBean("styleSheetManager");
        sheetsManager.setDefaultTheme(val);
        sheetsManager.reLoadSteelSheets();
      }
      return null;
    };

    final OkCancelDialog d = new OkCancelDialog.Builder().okCallBack(okCallback).content(combobox).build((AbstractViewController) actionRequest.getController());
    d.show();
  }


  protected ObservableList<EnumeratedValueModel> buildItems() {
    final ObservableList<EnumeratedValueModel> items = FXCollections.observableArrayList();
    for(THEMES t: THEMES.values()) {
      final EnumeratedValueModel m1 = new EnumeratedValueModel();
      m1.setSavedValue(t.path);
      m1.setValue(t.display);
      items.add(m1);
    }
    return items;
  }

  // @formatter:off
  public enum THEMES {

    GREEN_INDIGO("Light Green/Indigo", "/css/theme/green-indigo.css"),
    GREEN_LIME("Green/Lime", "/css/theme/green-lime.css"),
    INDIGO_BLUE("Indigo/Blue", "/css/theme/indigo-blue.css"),
    LIGHTBLUE_DEEP_ORANGE("Light Blue/Deep Orange", "/css/theme/lightblue-deepOrange.css"),
    LIGHTGREEN_INDIGO("Light Green/Indigo", "/css/theme/lightgreen-indigo.css"),
    LIME_GREEN("Lime/Green", "/css/theme/lime-green.css"),
    ORANGE_BLUE("Deep Orange/Blue","/css/theme/orange-blue.css"),
    ORANGE_LIGHGREEN("Orange/Light Green", "/css/theme/orange-lightgreen.css");

    private String display;
    private String path;


    private THEMES(String display, String path) {
      this.display = display;
      this.path = path;
    }
  }
  // @formatter:on
}
