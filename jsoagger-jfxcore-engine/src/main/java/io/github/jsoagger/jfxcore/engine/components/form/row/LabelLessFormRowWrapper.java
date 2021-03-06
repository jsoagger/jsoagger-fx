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

import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 21 mars 2018
 */
public class LabelLessFormRowWrapper extends HBox implements RowLayout {

  /**
   * Constructor
   */
  public LabelLessFormRowWrapper() {
    managedProperty().bind(visibleProperty());
    prefWidthProperty().bind(maxWidthProperty());

    getStyleClass().add("form-view-input-component-wrapper");
    getStyleClass().add("label-less-form-row-container");
  }


  @Override
  public void addLabel(Node label) {}


  @Override
  public void addValue(Node value) {
    //getChildren().addAll(value, NodeHelper.horizontalSpacer());
    getChildren().addAll(value);
  }


  @Override
  public Node getDisplay() {
    return this;
  }
}
