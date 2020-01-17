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

package io.github.jsoagger.jfxcore.components.inputView;



import io.github.jsoagger.jfxcore.api.IInputComponentWrapper;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.inputview.AbstractViewInputComponent;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Viewing all lifecycle states.
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class LifecycleInputView extends AbstractViewInputComponent {

  Label label = new Label();


  /**
   * Constructor
   */
  public LifecycleInputView() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void build(IInputComponentWrapper inputComponentWrapper) {
    super.build(inputComponentWrapper);
    String value = inputComponentWrapper.getCurrentInternalValue();
    label.setText(getController().getLocalised(value));
    NodeHelper.styleClassAddAll(label, getConfiguration(), "viewStyleClass", "current-lifecycle-form-info-value");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return label;
  }
}
