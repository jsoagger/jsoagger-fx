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

package io.github.jsoagger.jfxcore.engine.components.input;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import io.github.jsoagger.jfxcore.engine.client.components.ComponentToLabeledHelper;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.InjectableComponent;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.beans.value.ChangeListener;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ProcessingButton extends ActionableComp implements InjectableComponent {

  protected static final String SIMPLE_BUTTON = "simple-button";
  protected static final String REGEX = ",";
  protected static final String ICON_ONLY = "iconOnly";

  protected final Button button = new Button();


  /**
   * Constructor
   */
  public ProcessingButton() {
    super();
    button.addEventFilter(ActionEvent.ACTION, this::handle);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);

    final String title = configuration.getPropertyValue(XMLConstants.LABEL);
    NodeHelper.setLabel(button, configuration, (AbstractViewController) controller);
    if (!StringUtils.isEmpty(title)) {
      // ad fake indicator to the button to occupy the place
      // so when the task is runned, the label do not move
      final ProgressIndicator fakewaiting = new ProgressIndicator();
      fakewaiting.managedProperty().bind(fakewaiting.visibleProperty());
      fakewaiting.setVisible(false);
      // fakewaiting.getStyleClass().add(BUTTON_PROCESSING_PROGRESS);
      fakewaiting.maxHeight(8);
      fakewaiting.maxWidth(8);
      button.setGraphic(fakewaiting);
    }

    button.setDefaultButton(configuration.booleanPropertyValueOf(XMLConstants.DEFAULT).orElse(false));
    ComponentToLabeledHelper.setTooltip(configuration, button, (AbstractViewController) controller);
    NodeHelper.styleClassAddAll(button, configuration, XMLConstants.STYLE_CLASS);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void handle(ActionEvent e) {
    button.disableProperty().set(true);

    // build button task
    task = new ButtonActionTask(button, e);

    // waiting.visibleProperty().bind(task.runningProperty());
    taskProgress.progressProperty().bind(task.progressProperty());
    for(ChangeListener<Worker.State> l: listeners) {
      task.stateProperty().addListener(l);
    }

    CompletableFuture.runAsync(task, Executors.newSingleThreadExecutor()).whenComplete(this::taskCompleted);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return button;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getComponent() {
    return button;
  }


  public void toUpperCase() {
    button.setText(button.getText().toUpperCase());
  }
}
