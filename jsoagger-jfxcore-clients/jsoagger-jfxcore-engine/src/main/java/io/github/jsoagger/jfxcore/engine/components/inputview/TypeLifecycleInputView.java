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

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 30 janv. 2018
 */
public class TypeLifecycleInputView extends AbstractViewInputComponent {

  private Label label = new Label();


  /**
   * Default Constructor
   */
  public TypeLifecycleInputView() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void build(IInputComponentWrapper inputComponentWrapper) {
    super.build(inputComponentWrapper);

    label.textProperty().unbind();
    label.setWrapText(true);
    NodeHelper.styleClassAddAll(label, getConfiguration(), "viewStyleClass", "form-info-value");

    // convert saved date to displayed date
    String currval = (String) inputComponentWrapper.getConverter().fromString(inputComponentWrapper.getCurrentInternalValue());
    label.setText(currval);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return label;
  }
}
