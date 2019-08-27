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

package io.github.jsoagger.jfxcore.components.control9;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.control.TextField;

/**
 */
public class CustomTextField extends TextField {

  /**************************************************************************
   *
   * Private fields
   *
   **************************************************************************/




  /**************************************************************************
   *
   * Constructors
   *
   **************************************************************************/

  /**
   * Instantiates a default CustomTextField.
   */
  public CustomTextField() {
    getStyleClass().add("custom-text-field"); //$NON-NLS-1$
  }



  /**************************************************************************
   *
   * Properties
   *
   **************************************************************************/

  // --- left
  private ObjectProperty<Node> left = new SimpleObjectProperty<>(this, "left"); //$NON-NLS-1$

  /**
   *
   * @return An ObjectProperty wrapping the {@link Node} that is placed
   * on the left ofthe text field.
   */
  public final ObjectProperty<Node> leftProperty() {
    return left;
  }

  /**
   *
   * @return the {@link Node} that is placed on the left of
   * the text field.
   */
  public final Node getLeft() {
    return left.get();
  }

  /**
   * Sets the {@link Node} that is placed on the left of
   * the text field.
   * @param value
   */
  public final void setLeft(Node value) {
    left.set(value);
  }


  // --- right
  private ObjectProperty<Node> right = new SimpleObjectProperty<>(this, "right"); //$NON-NLS-1$

  /**
   * Property representing the {@link Node} that is placed on the right of
   * the text field.
   * @return An ObjectProperty.
   */
  public final ObjectProperty<Node> rightProperty() {
    return right;
  }

  /**
   *
   * @return The {@link Node} that is placed on the right of
   * the text field.
   */
  public final Node getRight() {
    return right.get();
  }

  /**
   * Sets the {@link Node} that is placed on the right of
   * the text field.
   * @param value
   */
  public final void setRight(Node value) {
    right.set(value);
  }



  /**************************************************************************
   *
   * Public API
   *
   **************************************************************************/

  /**
   * {@inheritDoc}
   */
  @Override protected Skin<?> createDefaultSkin() {
    return new CustomTextFieldSkin(this) {
      @Override public ObjectProperty<Node> leftProperty() {
        return CustomTextField.this.leftProperty();
      }

      @Override public ObjectProperty<Node> rightProperty() {
        return CustomTextField.this.rightProperty();
      }
    };
  }

  /**
   * {@inheritDoc}
   */
  @Override public String getUserAgentStylesheet() {
    return CustomTextField.class.getResource("customtextfield.css").toExternalForm(); //$NON-NLS-1$
  }
}
