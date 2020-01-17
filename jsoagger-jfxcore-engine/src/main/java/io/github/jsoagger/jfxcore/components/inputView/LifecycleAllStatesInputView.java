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



import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IInputComponentWrapper;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.inputview.AbstractViewInputComponent;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

/**
 * Viewing all lifecycle states.
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class LifecycleAllStatesInputView extends AbstractViewInputComponent {

  private static final String REGEX = ",";
  FlowPane fp = new FlowPane();


  /**
   * Constructor
   */
  public LifecycleAllStatesInputView() {
    super();
    fp.setHgap(5);
    fp.setVgap(5);
    fp.setAlignment(Pos.CENTER_RIGHT);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void build(IInputComponentWrapper inputComponentWrapper) {
    super.build(inputComponentWrapper);

    // separated by string
    String values = inputComponentWrapper.getCurrentInternalValue();
    List<String> converted = new ArrayList<>();
    if (StringUtils.isNotBlank(values)) {
      for(String s : values.split(REGEX)) {
        converted.add(getController().getGLocalised(s));
      }
    }

    for(String e: converted) {
      Label label = new Label(e);
      NodeHelper.styleClassAddAll(label, getConfiguration(), "viewStyleClass", "ep-chip-view");
      fp.getChildren().add(label);
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return fp;
  }
}
