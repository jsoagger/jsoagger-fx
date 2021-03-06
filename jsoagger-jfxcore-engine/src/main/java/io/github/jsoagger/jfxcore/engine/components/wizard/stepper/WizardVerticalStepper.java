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

package io.github.jsoagger.jfxcore.engine.components.wizard.stepper;




import io.github.jsoagger.jfxcore.api.wizard.IWizardStep;
import io.github.jsoagger.jfxcore.api.wizard.IWizardStepper;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * All steps of wizard presented in vertical way.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class WizardVerticalStepper extends VBox implements IWizardStepper {

  private static final String WIZARD_VERTICAL_STEPPER_LABEL_CSS = "wizard-vertical-stepper-index-label";
  private static final String WIZARD_VERTICAL_STEPPER_INDEX_LABEL_CSS = "wizard-vertical-stepper-index-indexLabel";
  private static final String WIZARD_VERTICAL_STEPPER_BRIDGE_CSS = "wizard-vertical-stepper-bridge";
  private static final String WIZARD_VERTICAL_STEPPER_CSS = "wizard-vertical-stepper";
  private static final String WIZARD_STEP_NUMBER_CSS = "wizard-step-number";

  private Separator bridge = new Separator();
  private Label titleLabel = new Label();

  private StackPane circleContainer = new StackPane();
  private Label indexLabel = new Label();
  private Circle circle = new Circle(15);

  private SimpleBooleanProperty hasNext = new SimpleBooleanProperty(false);
  private IWizardStep step;

  private Boolean error = null;
  private Boolean valid = null;


  /**
   * Constructor
   */
  public WizardVerticalStepper() {
    getStyleClass().add(WIZARD_VERTICAL_STEPPER_CSS);
    bridge.setOrientation(Orientation.VERTICAL);

    bridge.getStyleClass().add(WIZARD_VERTICAL_STEPPER_BRIDGE_CSS);
    bridge.managedProperty().bind(bridge.visibleProperty());
    bridge.visibleProperty().bind(hasNextProperty());
    getChildren().add(bridge);
  }


  /**
   * Constructor
   *
   * @param labelString
   */
  @Override
  public void setStep(IWizardStep step) {
    this.step = step;

    circle.setFill(Color.rgb(148, 148, 148));
    circle.setOpacity(0.57);

    indexLabel.setText(String.valueOf(step.getIndex()));
    indexLabel.getStyleClass().add(WIZARD_VERTICAL_STEPPER_INDEX_LABEL_CSS);
    circleContainer.getChildren().addAll(circle, indexLabel);

    titleLabel.getStyleClass().add(WIZARD_VERTICAL_STEPPER_LABEL_CSS);
    titleLabel.setText(step.getTitle());
    titleLabel.setWrapText(true);
    getChildren().add(0, circleContainer);
    getChildren().add(1, titleLabel);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setError() {
    circle.setFill(Color.RED);
    titleLabel.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), true);
    titleLabel.pseudoClassStateChanged(PseudoClass.getPseudoClass("valid"), false);
    bridge.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), true);
    indexLabel.setText("");

    error = true;
    valid = null;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setValid() {
    circle.setFill(Color.GREEN);
    titleLabel.pseudoClassStateChanged(PseudoClass.getPseudoClass("valid"), true);
    titleLabel.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), false);
    bridge.pseudoClassStateChanged(PseudoClass.getPseudoClass("valid"), true);
    indexLabel.setText("");

    error = null;
    valid = true;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void select(boolean value) {
    pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), value);
    if (value) {
      titleLabel.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), value);
      indexLabel.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), value);
      if (value) {
        circle.setFill(Color.rgb(33, 150, 243));
      }
    }

    // have been validated and is error
    // and selected!! should not be happen
    // only move to next step if valid
    if (error == null) {

    }

    // back or next to a valid step
    // do not change the valid state on the circle icon
    if (valid != null) {
      titleLabel.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), value);
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public IWizardStep getStep() {
    return step;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public final SimpleBooleanProperty hasNextProperty() {
    return this.hasNext;
  }
}
