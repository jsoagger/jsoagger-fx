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

package io.github.jsoagger.jfxcore.engine.components.form.row;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IEditInputComponent;
import io.github.jsoagger.jfxcore.api.IInputComponentWrapper;
import io.github.jsoagger.jfxcore.api.IVLConstraint;
import io.github.jsoagger.jfxcore.api.VLConstraintState;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.AbstractComponent;
import io.github.jsoagger.jfxcore.engine.components.input.AbstractInputComponent;
import io.github.jsoagger.jfxcore.engine.components.input.InputCheckbox;
import io.github.jsoagger.jfxcore.engine.components.validation.VLConstraintMaxLength;
import io.github.jsoagger.jfxcore.engine.components.validation.VLConstraintMinLength;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Order of the constraints influences the order of the error message displayed when form is
 * validated. Only the first error message from constraints list is displayed when the form is
 * validated.
 * <p>
 * If component content display is TOP, the label is layed out here. If component content display is
 * LEFT, the label is not layed out.
 *
 * @author Ramilafananana  VONJISOAsoa
 *
 */
public class EditInputComponent implements IEditInputComponent {

  private static final String DISPLAY_CONFIG_RELATIVE_PATH = "DisplayConfig";
  private static final String CONSTRAINTS_CONFIG_RELATIVE_PATH = "ValidationConfig";

  /*-----------------------------------------------------------------------------
  | PRIVATE ATTRIBUTES
   *=============================================================================*/
  protected AbstractInputComponent input;
  protected int counterMax = -1;
  protected Label counterText = new Label("0/" + counterMax);

  protected Label errorText = new Label();
  protected final VBox inputContainer = new VBox();

  protected SimpleBooleanProperty visible = new SimpleBooleanProperty();
  protected InputComponentWrapper inputComponentWrapper;

  protected List<IVLConstraint> constraints = new ArrayList<>();
  protected VLConstraintState state = VLConstraintState.UNVALIDATED;


  /*-----------------------------------------------------------------------------
  | Public methods
   *=============================================================================*/
  public EditInputComponent() {}


  /**
   * Build the help text and the error label
   */
  protected void buildHelpTextAndErrorText() {
    errorText.setVisible(false);
    errorText.setStyle("-fx-text-fill:red;" + "-fx-font-size:0.9em;" +
        "-fx-padding:4 0 8 0;-fx-font-weight:bold");
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void build(IInputComponentWrapper inputComponentWrapper) {
    this.inputComponentWrapper = (InputComponentWrapper) inputComponentWrapper;

    buildInput();

    // add the counter
    final HBox counterBox = new HBox();
    counterBox.setAlignment(Pos.BASELINE_RIGHT);
    counterBox.getChildren().add(counterText);
    counterText.setVisible(false);
    counterText.getStyleClass().add("counter-label");

    // @formatter:off
    final Optional<VLViewComponentXML> displayConfig = getConfiguration().getComponentById("DisplayConfig");
    displayConfig.ifPresent(d -> {
      d.intPropertyValueOf(XMLConstants.COUNTER).ifPresent(e -> {

        if (e > 0) {
          counterMax = e;

          // when the input is focused, the counter is displayed
          counterText.visibleProperty().bind(input.getDisplay().focusedProperty());

          // Counter is updated when the text value change
          ((TextInputControl) input.getComponent()).textProperty().addListener((observable, oldValue, newValue) -> {
            updateCounterValue(oldValue, newValue);
          });

          // as input text value may has been setted before, explicity
          // call updateCounterValue at initialisation
          final Optional<String> curText = Optional.of(((TextInputControl) input.getComponent()).getText());

          curText.ifPresent(text -> updateCounterValue(text, text));
        }
      });
    });
    // @formatter:on
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public IInputComponentWrapper getInputComponentWrapper() {
    return inputComponentWrapper;
  }


  @Override
  public VLViewComponentXML getConfiguration() {
    return inputComponentWrapper.getConfiguration();
  }


  public AbstractViewController getController() {
    return (AbstractViewController) inputComponentWrapper.getController();
  }


  /**
   * Build the input
   */
  protected void buildInput() {
    final String editUtility = getConfiguration().getPropertyValue(XMLConstants.EDIT_UTILITY);

    if (StringUtils.isNotBlank(editUtility)) {
      input = (AbstractInputComponent) Services.getBean(editUtility);
      input.setOwner(getInputComponentWrapper());
      input.buildFrom(getController(), getConfiguration());
      inputContainer.getChildren().addAll(input.getDisplay());

      try {
        // ReflectionUIUtils.bind(input.getDisplay(), "prefWidth",
        // inputContainer, "width");
      } catch (Exception e) {
        e.printStackTrace();
      }

      if (input instanceof InputCheckbox) {

      }

      generateConstraintsForAttribute(getConfiguration());
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getContent() {
    return inputContainer;
  }


  private void updateCounterValue(String oldValue, String newValue) {
    if (StringUtils.isNotBlank(newValue) && (newValue.length() > counterMax)) {
      ((TextInputControl) input.getComponent()).setText(oldValue);
      counterText.setText(((TextInputControl) input.getComponent()).getText().length() + "/" + counterMax);
    } else {
      counterText.setText(((TextInputControl) input.getComponent()).getText().length() + "/" + counterMax);
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public AbstractComponent getComponent() {
    return input;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    // allOverInputContainer.getChildren().clear();
    // allOverInputContainer.getChildren().add(inputContainer);
    // buildHelpTextAndErrorText();
    NodeHelper.setHgrow(inputContainer);
    return inputContainer;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void cancelModification() {
    input.cancelModification();
  }


  /**
   * Generates all constraints for this attribute
   *
   * @param attrConfig
   * @param entry
   */
  protected void generateConstraintsForAttribute(final VLViewComponentXML attrConfig) {
    final VLViewComponentXML constraintsConfig = attrConfig.getComponentById(CONSTRAINTS_CONFIG_RELATIVE_PATH).orElse(null);
    if (constraintsConfig != null) {
      final boolean notBlank = constraintsConfig.getBooleanProperty(XMLConstants.NOT_BLANK);
      final boolean mandatory = constraintsConfig.getBooleanProperty(XMLConstants.MANDATORY);
      final int maxlength = constraintsConfig.getIntPropertyValue(XMLConstants.MAX_LENGTH);
      final int minlength = constraintsConfig.getIntPropertyValue(XMLConstants.MIN_LENGTH);

      // not blank constraints
      if (notBlank) {
        addConstraint((IVLConstraint) Services.getBean("VLConstraintNotBlank"));
      }

      if (mandatory) {
        addConstraint((IVLConstraint) Services.getBean("VLConstraintRequired"));
      }

      if (maxlength > 0) {
        IVLConstraint constraintMaxLength = (IVLConstraint) Services.getBean("VLConstraintMaxLength");
        ((VLConstraintMaxLength)constraintMaxLength).setLength(maxlength);
        addConstraint(constraintMaxLength);
      }

      if (minlength > 0) {
        VLConstraintMinLength constraintMinLength = (VLConstraintMinLength) Services.getBean("VLConstraintMaxLength");
        constraintMinLength.setLength(minlength);
        addConstraint((IVLConstraint) Services.getBean("VLConstraintMinLength"));
      }
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public final SimpleBooleanProperty visibleProperty() {
    return this.visible;
  }


  /**
   * @return
   */
  @Override
  public boolean isNotValid() {
    boolean isNotValid = false;
    // validate the entry only if displayed
    if (getDisplay().isVisible()) {
      for (final IVLConstraint constraint : constraints) {
        if ((constraint.getState() == VLConstraintState.UNVALIDATED) || (constraint.getState() == VLConstraintState.NOT_VALID)) {
          isNotValid = true;
          break;
        }
      }
    }

    return isNotValid;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void displayError() {
    if(isNotValid()) {
      String errorMessage = getFirstErrorMessage();
      if (errorMessage == null) {
        errorMessage = "Invalid Field";
      }

      buildHelpTextAndErrorText();
      errorText.setText(errorMessage);
      errorText.getStyleClass().add("error-text");
      errorText.setTooltip(new Tooltip(errorMessage));
      errorText.setVisible(true);
      state = VLConstraintState.NOT_VALID;
      input.setInErrorState();
      if (!inputContainer.getChildren().contains(errorText)) {
        inputContainer.getChildren().add(errorText);
      }
    }
    else {
      errorText.setVisible(false);
    }
  }


  /**
   * Get the error message of the first error if exist
   *
   * @return
   */
  @Override
  public String getFirstErrorMessage() {
    final IVLConstraint constraint = constraints.stream().filter(c -> !c.isValid()).findFirst().orElse(null);
    if (constraint != null) {
      return constraint.getErrorMessage();
    }

    return null;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void validate() {
    Platform.runLater(() -> {
      inputContainer.getChildren().remove(errorText);
      input.setInValidState();
    });

    // validate the entry only if displayed
    if (getDisplay().isVisible()) {
      for (final IVLConstraint constraint : constraints) {
        constraint.validate();
      }

      state = VLConstraintState.VALID;
      for (IVLConstraint constraint : constraints) {
        if (constraint.isNotValid()) {
          state = VLConstraintState.NOT_VALID;
        }
      }
    }
  }


  /**
   * Order of the constraints influences the order of the error message displayed when form is
   * validated
   *
   */
  @Override
  public void addConstraint(IVLConstraint cons) {
    constraints.addAll(Arrays.asList(cons));
    cons.setInput(input);
  }
}
