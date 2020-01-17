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


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import com.jfoenix.controls.JFXTimePicker;

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.util.StringConverter;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class InputTimePicker extends AbstractInputComponent {

  private JFXTimePicker timePicker = new JFXTimePicker();


  /**
   * Constructor
   */
  public InputTimePicker() {
    super();

    //datePicker.getStyleClass().remove("custom-text-field");
    //datePicker.getStyleClass().remove("jfx-text-field");
    timePicker.setEditable(false);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);
    final Optional<Boolean> isFocused = configuration.booleanPropertyValueOf(XMLConstants.FOCUSED);
    isFocused.filter(e -> e == Boolean.TRUE).flatMap(e -> {
    	timePicker.requestFocus();
      return Optional.empty();
    });

    // process DisplayConfig
    final Optional<VLViewComponentXML> displayConfig = configuration.getComponentById(DISPLAY_CONFIG);
    displayConfig.ifPresent(e -> {
      e.booleanPropertyValueOf(XMLConstants.READ_ONLY).ifPresent(f -> {
    	  timePicker.setDisable(f.booleanValue());
      });
    });

    processFormat();

    // format is save format so convert it into display format
    String internalVal = owner.getCurrentInternalValue();
    String saveFormat = StringUtils.isNotBlank(getSaveFormat()) ? getSaveFormat() : "dd/MM/yyyy";
    DateTimeFormatter format = DateTimeFormatter.ofPattern(saveFormat);

    if (StringUtils.isNotBlank(internalVal)) {
      try {
        LocalTime val = LocalTime.parse(internalVal, format);
        timePicker.setValue((val));
      }catch (DateTimeParseException e) {
      }
    }

    Bindings.bindBidirectional(owner.currentInternalValueProperty(), timePicker.valueProperty(), timePicker.getConverter());
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
	  timePicker.setConverter(new StringConverter<LocalTime>() {

      String displayFormat = StringUtils.isNotBlank(getDisplayFormat()) ? getDisplayFormat() : "HH:mm";
      String saveFormat = StringUtils.isNotBlank(getSaveFormat()) ? getSaveFormat() : "HH:mm";


      @Override
      public String toString(LocalTime time) {
        if (time != null) {
          DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(displayFormat) ;
          return time.format(dateTimeFormatter);
        }

        return "";
      }


      /**
       * Converts the input text from user into LocalDate
       *
       * @{inheritedDoc}
       */
      @Override
      public LocalTime fromString(String string) {
        if ((string != null) && !string.isEmpty()) {
          try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern(saveFormat);
            LocalTime val = LocalTime.parse(string, format);
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
    return timePicker;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getComponent() {
    return timePicker;
  }
}
