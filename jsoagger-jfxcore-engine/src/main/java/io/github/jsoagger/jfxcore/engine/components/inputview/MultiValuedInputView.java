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




import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IEnumeratedValueModel;
import io.github.jsoagger.jfxcore.api.IInputComponentWrapper;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class MultiValuedInputView extends AbstractViewInputComponent {

  protected FlowPane flow = new FlowPane();


  /**
   * Constructor
   */
  public MultiValuedInputView() {
    super();
    // flow.setTextAlignment(TextAlignment.RIGHT);
    flow.setAlignment(Pos.CENTER_LEFT);
    flow.setVgap(5);
    flow.setHgap(2);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void build(IInputComponentWrapper inputComponentWrapper) {
    super.build(inputComponentWrapper);

    // the type of value to display
    treatFlow(inputComponentWrapper.getCurrentInternalValue());
    inputComponentWrapper.currentInternalValueProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
      treatFlow(newValue);
    });
  }


  private void treatFlow(String value) {
    flow.getChildren().clear();

    if (value != null) {
      final List<String> eaches = Arrays.asList(value.split(inputComponentWrapper.getEscapedMultivaluedSeparator()));
      final Iterator<String> iterator = eaches.iterator();

      while (iterator.hasNext()) {
        final String each = iterator.next();
        final Label text = new Label();
        NodeHelper.styleClassAddAll(text, getConfiguration(), "viewStyleClass", "form-info-value");
        if (inputComponentWrapper.getEnumeratedValueModels() != null) {
          final Iterator<IEnumeratedValueModel> enumvalIterator = inputComponentWrapper.getEnumeratedValueModels().iterator();
          while (enumvalIterator.hasNext()) {
            final IEnumeratedValueModel model = enumvalIterator.next();
            if (each.equalsIgnoreCase(model.getSavedValue())) {
              text.setText(model.getValue());
              break;
            }
          }
        } else {
          text.setText(inputComponentWrapper.getConverter().toDisplay(each));
        }

        flow.getChildren().add(text);
      }
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return flow;
  }
}
