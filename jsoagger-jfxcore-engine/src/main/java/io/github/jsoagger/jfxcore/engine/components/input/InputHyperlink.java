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




import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.beans.value.ChangeListener;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class InputHyperlink extends ActionableComp {

  private Hyperlink hyperlink = new Hyperlink();
  protected ProgressBar taskProgress = new ProgressBar();
  protected ButtonActionTask task = null;
  protected List<ChangeListener<Worker.State>> listeners = new ArrayList<>();


  /**
   * Constructor
   */
  public InputHyperlink() {
    super();
    hyperlink.getStyleClass().add("input-hyperlink");
    hyperlink.addEventFilter(ActionEvent.ACTION, this::handle);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);
    hyperlink.setId(id);
    NodeHelper.setTitle(hyperlink, configuration, (AbstractViewController) controller);
    NodeHelper.setStyleClass(hyperlink, configuration, "styleClass", true);
    IconUtils.setIcon(hyperlink, configuration);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void handle(ActionEvent e) {
    task = new ButtonActionTask(hyperlink, e, this);
    taskProgress.progressProperty().bind(task.progressProperty());
    for(ChangeListener<Worker.State> l: listeners) {
      task.stateProperty().addListener(l);
      new Thread(task).start();
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void addDisplayBinding(Label label) {
    super.addDisplayBinding(label);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return hyperlink;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getComponent() {
    return hyperlink;
  }
}
