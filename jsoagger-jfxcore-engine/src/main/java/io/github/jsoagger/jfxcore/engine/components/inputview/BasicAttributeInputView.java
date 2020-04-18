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



import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IInputComponentWrapper;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.converter.StringConverter;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class BasicAttributeInputView extends AbstractViewInputComponent {

  private final Label label = new Label();


  /**
   * Constructor
   */
  public BasicAttributeInputView() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void build(IInputComponentWrapper inputComponentWrapper) {
    super.build(inputComponentWrapper);
    NodeHelper.styleClassAddAll(label, getConfiguration(), "viewStyleClass", "form-info-value");

    String localised = getConfiguration().getPropertyValue("localised");

    if (StringUtils.isNotBlank(localised) && "true".equalsIgnoreCase(localised)) {
      label.setText(controller.getLocalised(inputComponentWrapper.getCurrentInternalValue()));
    } else {
      label.setText(((StringConverter) inputComponentWrapper.getConverter())
          .toDisplay(inputComponentWrapper.getCurrentInternalValue()));
    }

    if (AbstractApplicationRunner.isDesktop()) {
      label.setMaxWidth(400);
    }

    inputComponentWrapper.currentInternalValueProperty()
        .addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
          label.setText(
              ((StringConverter) inputComponentWrapper.getConverter()).toDisplay(newValue));
        });
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return label;
  }
}
