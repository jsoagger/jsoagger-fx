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


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Optional;

import io.github.jsoagger.core.utils.DateUtils;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import com.jfoenix.controls.JFXDatePicker;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class InputDatePicker extends AbstractInputComponent {

  private DatePicker datePicker = new JFXDatePicker();


  /**
   * Constructor
   */
  public InputDatePicker() {
    super();

    //datePicker.getStyleClass().remove("custom-text-field");
    //datePicker.getStyleClass().remove("jfx-text-field");
    datePicker.setEditable(false);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);
    final Optional<Boolean> isFocused = configuration.booleanPropertyValueOf(XMLConstants.FOCUSED);
    isFocused.filter(e -> e == Boolean.TRUE).flatMap(e -> {
      datePicker.requestFocus();
      return Optional.empty();
    });

    // process DisplayConfig
    final Optional<VLViewComponentXML> displayConfig = configuration.getComponentById(DISPLAY_CONFIG);
    displayConfig.ifPresent(e -> {
      e.booleanPropertyValueOf(XMLConstants.READ_ONLY).ifPresent(f -> {
        datePicker.setDisable(f.booleanValue());
      });
    });

    processFormat();

    // format is save format so convert it into display format
    String internalVal = owner.getCurrentInternalValue();
    String saveFormat = StringUtils.isNotBlank(getSaveFormat()) ? getSaveFormat() : "dd/MM/yyyy";
    DateTimeFormatter format = DateTimeFormatter.ofPattern(saveFormat);

    if (StringUtils.isNotBlank(internalVal)) {
      try {
        LocalDate val = LocalDate.parse(internalVal, format);
        datePicker.setValue((val));
      }catch (DateTimeParseException e) {
      }
    }

    Bindings.bindBidirectional(owner.currentInternalValueProperty(), datePicker.valueProperty(), datePicker.getConverter());
  }


  /**
   * Format for converting from displayed value to saved value
   */
  private void processFormat() {
    // VLViewComponentXML format =
    // getConfiguration().getComponentById("Format").orElse(null);

    /**
     * Converts the date from component to text
     */
    datePicker.setConverter(new StringConverter<LocalDate>() {

      String displayFormat = StringUtils.isNotBlank(getDisplayFormat()) ? getDisplayFormat() : "dd/MM/yyyy";
      String saveFormat = StringUtils.isNotBlank(getSaveFormat()) ? getSaveFormat() : "dd/MM/yyyy";


      @Override
      public String toString(LocalDate object) {
        if (object != null) {
          Date datem = DateUtils.asDate(object);
          return DateUtils.dateString(datem, displayFormat);
        }

        return "";
      }


      /**
       * Converts the input text from user into LocalDate
       *
       * @{inheritedDoc}
       */
      @Override
      public LocalDate fromString(String string) {
        if ((string != null) && !string.isEmpty()) {
          try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern(saveFormat);
            LocalDate val = LocalDate.parse(string, format);
            return val;
          } catch (Exception e) {
            return null;
          }
        }

        return null;
      }
    });
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return datePicker;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getComponent() {
    return datePicker;
  }
}
