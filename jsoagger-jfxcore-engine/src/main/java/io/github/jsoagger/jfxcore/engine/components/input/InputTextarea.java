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

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import com.jfoenix.controls.JFXTextArea;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class InputTextarea extends AbstractInputComponent {

  private TextArea textArea;
  public int maxLength = -1;


  /**
   * Constructor
   */
  public InputTextarea() {
    super();
    textArea = new JFXTextArea();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);
    textArea.setPrefRowCount(2);

    // prompt
    prompt.ifPresent(e -> {
      final String val = controller.getLocalised(prompt.get());
      textArea.setPromptText(val);
    });

    // process wizardConfiguration
    final Optional<VLViewComponentXML> displayConfig = configuration.getComponentById(DISPLAY_CONFIG);
    displayConfig.ifPresent(d -> {
      d.booleanPropertyValueOf(XMLConstants.READ_ONLY).ifPresent(e -> {
        textArea.setDisable(e);
      });

      d.intPropertyValueOf(XMLConstants.COUNTER).ifPresent(e -> {
        if (e > 0) {
          // !! not for validation, for counter
          // textArea.setMaxLength(e);
        }
      });
    });

    textArea.setText(owner.getCurrentInternalValue());
    Bindings.bindBidirectional(textArea.textProperty(), owner.currentInternalValueProperty(), owner.getConverter());
  }


  /**
   *
   * @return
   */
  public int getMaxLength() {
    return maxLength;
  }


  /**
   *
   * @param maxLength
   */
  public void setMaxLength(int maxLength) {
    this.maxLength = maxLength;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return textArea;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getComponent() {
    return textArea;
  }
}
