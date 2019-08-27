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


import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IEnumeratedValueModel;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.api.services.Services;

import javafx.beans.value.ChangeListener;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class AudienceSelector extends AbstractInputComponent {

  /*** Combobox */
  private ComboBox<IEnumeratedValueModel> comboBox = new ComboBox<>();


  /**
   * Constructor
   */
  public AudienceSelector() {
    // comboBox.getStyleClass().add("audience-selector-combobox");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);
    comboBox.getStyleClass().removeAll("jsoagger-control");

    String audiences = configuration.getPropertyValue("audiences", "0,1,2,3");
    String[] audiencesT = audiences.split(",");
    for (String aud : audiencesT) {
      IEnumeratedValueModel model = owner.getEnumeratedValue(aud);
      if (model != null) {
        comboBox.getItems().add(model);
      }
    }

    String cellFactory = configuration.getPropertyValue("cellFactory", "AudienceSelectorCellFactory");
    Callback cellfactory = (Callback) Services.getBean(cellFactory);

    comboBox.setButtonCell(AudienceSelectorCellFactory.buttonCell(false));
    comboBox.setCellFactory(cellfactory);

    // if not value has been setted, set to the default value
    if (comboBox.getSelectionModel().getSelectedItem() == null) {
      // todo add blank item if needed
      String internalVal = (String) owner.getInitialInternalValue();
      IEnumeratedValueModel val = owner.getEnumeratedValue(internalVal);
      comboBox.getSelectionModel().select(val);
    }

    // update wizardConfiguration value when the selection has changed
    final ChangeListener valueChangeListener = (arg0, arg1, arg2) -> {
      final IEnumeratedValueModel selected = comboBox.getSelectionModel().getSelectedItem();
      owner.currentInternalValueProperty().set(selected.savedValueProperty().get());
    };
    comboBox.valueProperty().addListener(valueChangeListener);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    if (combodisplay == null) {
      combodisplay = new StackPane();
      combodisplay.setAlignment(Pos.CENTER_LEFT);
      combodisplay.getStyleClass().add("jsoagger-control");
      combodisplay.pseudoClassStateChanged(PseudoClass.getPseudoClass("borderless"), true);
      combodisplay.getChildren().add(comboBox);
    }

    return combodisplay;
  }

  StackPane combodisplay = null;


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getComponent() {
    return comboBox;
  }
}
