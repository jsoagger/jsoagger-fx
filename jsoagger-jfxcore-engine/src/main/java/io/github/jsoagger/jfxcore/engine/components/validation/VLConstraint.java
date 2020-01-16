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

package io.github.jsoagger.jfxcore.engine.components.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.api.IComponent;
import io.github.jsoagger.jfxcore.api.IVLConstraint;
import io.github.jsoagger.jfxcore.api.VLConstraintState;
import io.github.jsoagger.jfxcore.engine.components.input.AbstractInputComponent;
import io.github.jsoagger.jfxcore.engine.utils.LocaleResolver;

import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputControl;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public abstract class VLConstraint implements IVLConstraint {

  protected VLConstraintState state = VLConstraintState.UNVALIDATED;

  protected String errorMessageKey;
  protected String errorMessage;

  protected AbstractInputComponent input;
  protected List<AbstractInputComponent> dependantNode = new ArrayList<>();
  protected boolean hasDependantNodes;

  // do not forgte afterPropertiesSet
  // needs CoreValidationMessageSource
  //protected MessageSource ms;


  /**
   * Constructor
   */
  public VLConstraint() {}


  public void afterPropertiesSet() throws Exception {
    Locale locale = LocaleResolver.getLocale();
    if (StringUtils.isNotBlank(errorMessageKey)) {
      //this.errorMessage = ms.getMessage(errorMessageKey, null, locale);
    }
  }


  /**
   *
   * @return
   */
  @Override
  public VLConstraintState getState() {
    return state;
  }


  /**
   *
   * @param input
   */
  public void addDependance(AbstractInputComponent input) {
    dependantNode.add(input);
  }


  /**
   *
   * @param input
   */
  public void addDependance(AbstractInputComponent... input) {
    this.hasDependantNodes = true;
    dependantNode.addAll(Arrays.asList(input));
  }


  /**
   *
   */
  @Override
  public void validate() {
    boolean atLeastOneDependantNodeHasText = false;
    if (hasDependantNodes) {
      // if one of dependant has value this field must have value
      for (final AbstractInputComponent control : dependantNode) {
        if (control.getComponent() instanceof TextInputControl) {
          if (!StringUtils.isEmpty(((TextInputControl) control.getComponent()).getText())) {
            atLeastOneDependantNodeHasText = true;
          }
        } else if (control.getComponent() instanceof ChoiceBox) {
          if (_getContent(control.getComponent()) != null) {
            atLeastOneDependantNodeHasText = true;
          }
        }
      }
    }

    if (hasDependantNodes) {
      if (atLeastOneDependantNodeHasText) {
        state = _hasContent(input.getComponent()) ? VLConstraintState.VALID : VLConstraintState.NOT_VALID;
      } else {
        state = VLConstraintState.VALID;
      }
      return;
    }

    Object toVAlidate = input.getValueToValidate();
    final boolean result = isValidInputFor((String) toVAlidate);
    if (result) {
      state = VLConstraintState.VALID;
    } else {
      state = VLConstraintState.NOT_VALID;
    }
  }


  /**
   *
   * @return
   */
  @Override
  public boolean isValid() {
    return state == VLConstraintState.VALID;
  }


  /**
   *
   * @return
   */
  @Override
  public boolean isNotValid() {
    return (state == VLConstraintState.NOT_VALID) || (state == VLConstraintState.UNVALIDATED);
  }


  /**
   * @return the errorMessage
   */
  @Override
  public String getErrorMessage() {
    return errorMessage;
  }


  /**
   * @param input the input to set
   */
  @Override
  public void setInput(IComponent input) {
    this.input = (AbstractInputComponent) input;
  }


  @SuppressWarnings("rawtypes")
  private boolean _hasContent(Node input) {

    if (input instanceof TextInputControl) {
      return !StringUtils.isEmpty(((TextInputControl) input).getText());
    } else if (input instanceof ChoiceBox) {
      return ((ChoiceBox) input).getSelectionModel().getSelectedItem() != null;
    } else if (input instanceof ComboBox) {
      return ((ComboBox) input).getSelectionModel().getSelectedItem() != null;
    }
    return false;
  }


  @SuppressWarnings("rawtypes")
  private Object _getContent(Node control) {

    if (control instanceof TextInputControl) {
      return ((TextInputControl) control).getText();
    } else if (control instanceof ChoiceBox) {
      return ((ChoiceBox) control).getSelectionModel().getSelectedItem();
    } else if (control instanceof ComboBox) {
      return ((ComboBox) control).getSelectionModel().getSelectedItem();
    }
    return null;
  }


  /**
   * Getter of dependantNode
   *
   * @return the dependantNode
   */
  public List<AbstractInputComponent> getDependantNode() {
    return dependantNode;
  }


  /**
   * Setter of dependantNode
   *
   * @param dependantNode the dependantNode to set
   */
  public void setDependantNode(List<AbstractInputComponent> dependantNode) {
    this.dependantNode = dependantNode;
  }

  /**
   * Getter of input
   *
   * @return the input
   */
  public AbstractInputComponent getInput() {
    return input;
  }


  /**
   * Setter of state
   *
   * @param state the state to set
   */
  public void setState(VLConstraintState state) {
    this.state = state;
  }


  /**
   * Setter of errorMessage
   *
   * @param errorMessage the errorMessage to set
   */
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }


  /**
   * Getter of errorMessageKey
   *
   * @return the errorMessageKey
   */
  public String getErrorMessageKey() {
    return errorMessageKey;
  }


  /**
   * Setter of errorMessageKey
   *
   * @param errorMessageKey the errorMessageKey to set
   */
  public void setErrorMessageKey(String errorMessageKey) {
    this.errorMessageKey = errorMessageKey;
  }


  /**
   * @param text
   * @return
   */
  public boolean isValidInputFor(String text) {
    return false;
  }
}
