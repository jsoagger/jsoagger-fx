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



import java.time.LocalDate;
import java.util.Date;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.utils.DateUtils;
import io.github.jsoagger.jfxcore.api.IInputComponentWrapper;

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class DateInputView extends AbstractViewInputComponent {

  private final Label label = new Label();


  /**
   * Constructor
   */
  public DateInputView() {
    super();
  }


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
    LocalDate currval = (LocalDate) inputComponentWrapper.getConverter().fromString(inputComponentWrapper.getCurrentInternalValue());
    label.setText(toDisplayFormat(currval));

    inputComponentWrapper.currentInternalValueProperty().addListener((ChangeListener) (observable, oldValue, newValue) -> {
      LocalDate val = (LocalDate) inputComponentWrapper.getConverter().fromString((String) newValue);
      label.setText(toDisplayFormat(val));
    });
  }


  public String toDisplayFormat(LocalDate currval) {
    Date datem = DateUtils.asDate(currval);
    return DateUtils.dateString(datem, getDisplayFormat());
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return label;
  }
}
