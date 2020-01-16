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



import io.github.jsoagger.jfxcore.api.RowLayout;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 5 mars 2018
 */
public class VerticalFormRowWrapper extends VBox implements RowLayout {

  /**
   * Constructor
   */
  public VerticalFormRowWrapper() {
    minHeightProperty().bind(prefHeightProperty());
    managedProperty().bind(visibleProperty());
    getStyleClass().add("vertical-form-row-container");
    setAlignment(Pos.CENTER_LEFT);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void addLabel(Node label) {
    getChildren().add(0, label);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void addValue(Node value) {
    getChildren().addAll(value);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }
}
