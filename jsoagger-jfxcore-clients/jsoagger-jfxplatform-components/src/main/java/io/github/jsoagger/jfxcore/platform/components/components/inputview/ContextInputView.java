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

package io.github.jsoagger.jfxcore.platform.components.components.inputview;


import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IInputComponentWrapper;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.inputview.AbstractViewInputComponent;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ContextInputView extends AbstractViewInputComponent {

  HBox b = new HBox();

  /**
   * @{inheritedDoc}
   */
  @Override
  public void build(IInputComponentWrapper inputComponentWrapper) {
    super.build(inputComponentWrapper);
    //    NodeHelper.styleClassAddAll(label, getConfiguration(), "viewStyleClass", "form-info-value");
    process("/Application");

    if(AbstractApplicationRunner.isDesktop()) {
      // TODO HANDLE TOOLTIP
      b.setMaxWidth(400);
      b.setAlignment(Pos.CENTER_RIGHT);
    }
    inputComponentWrapper.currentInternalValueProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
      //label.setText(((StringConverter) inputComponentWrapper.getConverter()).toDisplay(newValue));
    });
  }


  protected Node process(String location) {
    List loc = Arrays.asList(location.split("/"));
    Iterator<String> it = loc.iterator();

    b.setSpacing(2);
    b.getChildren().add(NodeHelper.getSep());
    while(it.hasNext()) {
      String next = it.next();

      if(StringUtils.isNotBlank(next)) {
        Label l = new Label(next);
        l.getStyleClass().add("form-info-value");
        b.getChildren().add(l);

        if(it.hasNext()) {
          b.getChildren().add(NodeHelper.getSep());
        }
      }
    }
    return b;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return b;
  }
}
