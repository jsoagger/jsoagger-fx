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


import java.util.Optional;

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class InputText extends AbstractInputComponent {

  private TextField textField = null;


  /**
   * Constructor
   */
  public InputText() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    final String ispasswordField = configuration.getPropertyValue("passwordField");
    newInstance(StringUtils.isNotBlank(ispasswordField) && Boolean.valueOf(ispasswordField));

    super.buildFrom(controller, configuration);

    textField.setId(id);
    configure();
    textField.setText(owner.getInitialInternalValue());
    Bindings.bindBidirectional(owner.currentInternalValueProperty(), textField.textProperty(), owner.getConverter());
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void destroy() {
    super.destroy();
    textField.textProperty().unbind();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setText(String value) {
    textField.setText(value);
  }


  private void newInstance(boolean ispasswordField) {
    if (ispasswordField) {
      textField = new JFXPasswordField();
      textField.getStyleClass().remove("custom-text-field");
      textField.getStyleClass().remove("jfx-text-field");
    } else {
      textField = new JFXTextField();
      textField.getStyleClass().remove("custom-text-field");
      //textField.getStyleClass().remove("jfx-text-field");
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void addDisplayBinding(Label label) {
    super.addDisplayBinding(label);
    label.textProperty().bind(textField.textProperty());
  }


  public void configure() {
    final Optional<String> prompt = configuration.propertyValueOf(XMLConstants.PROMPT);
    final Optional<Boolean> isAttributeGenerated = configuration.booleanPropertyValueOf(XMLConstants.GENERATED);
    final Optional<Boolean> isFocused = configuration.booleanPropertyValueOf(XMLConstants.FOCUSED);

    // focus
    isFocused.filter(e -> e == Boolean.TRUE).flatMap(e -> {
      textField.requestFocus();
      return Optional.empty();
    });

    // generated value
    isAttributeGenerated.filter(e -> e == Boolean.TRUE).flatMap(e -> {
      textField.setDisable(true);
      return Optional.empty();
    });

    // prompt
    prompt.ifPresent(e -> {
      final String val = controller.getLocalised(prompt.get());
      textField.setPromptText(val);
    });

    // process DisplayConfig
    final Optional<VLViewComponentXML> displayConfig = configuration.getComponentById(DISPLAY_CONFIG);
    displayConfig.ifPresent(e -> {
      e.booleanPropertyValueOf(XMLConstants.READ_ONLY).ifPresent(f -> {
        textField.setDisable(f.booleanValue());
      });
    });

    processFormat();
  }


  /**
   * Process format section
   */
  private void processFormat() {
    final VLViewComponentXML format = configuration.getComponentById("Format").orElse(null);
    if (format != null) {
      final String upperCase = format.getPropertyValue(XMLConstants.UPPERCASE);
      final String number = format.getPropertyValue(XMLConstants.NUMBER);
      final String capitalize = format.getPropertyValue(XMLConstants.CAPITALIZE);
      final String capitalizeAll = format.getPropertyValue(XMLConstants.CAPITALIZE_ALL);
      final String maxLength = format.getPropertyValue(XMLConstants.MAX_LENGTH);

      textField.textProperty().addListener((ov, oldValue, newValue) -> {
        if (StringUtils.isNotBlank(upperCase)) {
          owner.currentInternalValueProperty().set(newValue.toUpperCase());
        }

        if (StringUtils.isNotBlank(number)) {
          if (newValue.matches("\\d*")) {
            Integer.parseInt(newValue);
          } else {
            owner.currentInternalValueProperty().set(oldValue);
          }
        }

        if (StringUtils.isNotBlank(capitalize)) {
          if (StringUtils.isNotBlank(owner.getCurrentInternalValue())) {
            owner.currentInternalValueProperty().set(StringUtils.capitalize(owner.getCurrentInternalValue()));
          }
        }

        if (StringUtils.isNotBlank(capitalizeAll)) {
          if (StringUtils.isNotBlank(owner.getCurrentInternalValue())) {
            owner.currentInternalValueProperty().set(StringUtils.capitalize(owner.getCurrentInternalValue()));
          }
        }

        if (StringUtils.isNotBlank(maxLength)) {
          try {
            final int len = Integer.parseInt(maxLength);
            if (StringUtils.isNotBlank(textField.getText())) {
              if (textField.lengthProperty().get() > len) {
                textField.setText(oldValue);
              }
            }
          } catch (final Exception e) {
          }
        }
      });
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return textField;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getComponent() {
    return textField;
  }
}
