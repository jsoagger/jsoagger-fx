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

package io.github.jsoagger.jfxcore.engine.components.wizard.content;


import io.github.jsoagger.jfxcore.api.IActionMessage;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.api.IUIDataValidationResult;
import io.github.jsoagger.jfxcore.api.IUIValidationMessage;
import io.github.jsoagger.jfxcore.api.UIDataValidationResultStatus;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class WizardValidationMessageWrapper extends VBox {

  /**
   * Constructor
   */
  public WizardValidationMessageWrapper() {
    super();
    getStyleClass().add("wizard-validation-message-wrapper");

    // occupy space only if visible
    managedProperty().bind(visibleProperty());
    visibleProperty().set(false);
  }


  /**
   * Clear content
   */
  public void reinit() {
    Platform.runLater(() -> getChildren().clear());
  }


  /**
   * @param result
   */
  public void handle(IUIDataValidationResult result) {
    if (getChildren().size() > 0) {
      reinit();
    }

    if (result.getStatus() == UIDataValidationResultStatus.NOT_VALID) {
      setVisible(result.hasMessage());
      if (result.hasMessage()) {
        for (IUIValidationMessage message : result.getMessages()) {
          Label label = new Label(message.getMessage());
          label.getStyleClass().addAll("wizard-error-label");
          Platform.runLater(() -> getChildren().add(label));
        }
      }
    }
  }


  /**
   * @param actionResult
   */
  public void handle(IActionResult actionResult) {
    if (getChildren().size() > 0) {
      reinit();
    }

    setVisible(actionResult.hasMessage());
    if (actionResult.hasMessage()) {
      for (IActionMessage message : actionResult.getActionMessages()) {
        Label label = new Label(message.getBody());
        label.getStyleClass().addAll("wizard-error-label");
        Platform.runLater(() -> getChildren().add(label));
      }
    }
  }
}
