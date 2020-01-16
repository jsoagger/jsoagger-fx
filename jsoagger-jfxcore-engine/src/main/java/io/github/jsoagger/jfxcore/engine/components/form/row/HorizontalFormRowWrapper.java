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



import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.RowLayout;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class HorizontalFormRowWrapper extends HBox implements RowLayout {

  /**
   * Constructor
   */
  public HorizontalFormRowWrapper() {
    managedProperty().bind(visibleProperty());
    prefWidthProperty().bind(maxWidthProperty());
    prefHeightProperty().bind(minHeightProperty());
    // maxWidthProperty().bind(widthProperty());

    getStyleClass().add("form-view-input-component-wrapper");
    getStyleClass().add("horizontal-form-row-container");
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void addLabel(Node label) {
    // !! IMPORTAANT, horizontalSpacer WAS ADDED FOR SINGLE ROW
    // CHECKBOX, TO KEEP IT ON THE RIGHT SIDE
    // IF YOU CHANGE THIS TEST IT
    getChildren().add(0, label);
    getChildren().add(NodeHelper.horizontalSpacer());
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void addValue(Node value) {
    getChildren().addAll(value);
    NodeHelper.setHgrow(value);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }
}
