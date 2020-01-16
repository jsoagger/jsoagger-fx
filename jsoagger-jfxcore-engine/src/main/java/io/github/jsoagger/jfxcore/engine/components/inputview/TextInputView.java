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

package io.github.jsoagger.jfxcore.engine.components.inputview;




import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IInputComponentWrapper;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

/**
 * Uses a {@link Text} to display a text edited in a {@link TextArea}
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class TextInputView extends AbstractViewInputComponent {

  private static final String REGEX = ",";
  private final Text label = new Text();


  /**
   * Constructor
   */
  public TextInputView() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void build(IInputComponentWrapper inputComponentWrapper) {
    super.build(inputComponentWrapper);
    NodeHelper.styleClassAddAll(label, getConfiguration(), "viewStyleClass", "form-info-value");
    String curVal = inputComponentWrapper.getCurrentInternalValue();
    label.setText(curVal);
    Bindings.bindBidirectional(label.textProperty(), inputComponentWrapper.currentInternalValueProperty(), inputComponentWrapper.getConverter());
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return label;
  }
}
