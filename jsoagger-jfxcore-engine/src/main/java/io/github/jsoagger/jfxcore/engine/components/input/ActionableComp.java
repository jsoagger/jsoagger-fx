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
import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.components.ComponentToButtonBaseHelper;
import io.github.jsoagger.jfxcore.api.IActionable;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.AbstractComponent;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ProgressBar;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public abstract class ActionableComp extends AbstractComponent implements IActionable, EventHandler<ActionEvent> {

  private static final String ACTION = "action";

  protected ProgressBar taskProgress = new ProgressBar();
  protected ButtonActionTask task = null;
  protected List<ChangeListener<Worker.State>> listeners = new ArrayList<>();


  /**
   * @{inheritedDoc}
   */
  @Override
  public void addStateListener(ChangeListener<Worker.State> listener) {
    listeners.add(listener);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public DoubleProperty progressProperty() {
    return getTaskProgress().progressProperty();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public ProgressBar getTaskProgress() {
    return taskProgress;
  }


  /**
   * @param arg
   * @param ex
   */
  public void taskCompleted(Object arg, Throwable ex) {}

  /**
   * Anonymous class for button external parameters passing.
   *
   * @author Ramilafananana VONJISOA
   * @mailto yvonjisoa@nexitia.com
   * @date 2019
   */
  protected class ButtonActionTask extends Task<Void> {

    ButtonBase button;
    ActionEvent e;
    ActionableComp actionableComp;


    public ButtonActionTask(ButtonBase button, ActionEvent e) {
      super();
      this.button = button;
      this.e = e;
    }


    public ButtonActionTask(ButtonBase button, ActionEvent e, ActionableComp comp) {
      this(button, e);
      actionableComp = comp;
      taskProgress.progressProperty().bind(progressProperty());
      for(ChangeListener<Worker.State> l: listeners) {
        stateProperty().addListener(l);
      }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected Void call() {
      try {
        button.setUserData(actionableComp);
        final Optional<VLViewComponentXML> actionComponent = configuration.getComponentById("Handler");
        actionComponent.ifPresent(handler -> ComponentToButtonBaseHelper.setButtonActions(controller, handler, button, e));
      } catch (final Exception e) {
        e.printStackTrace();
      } finally {
        // reenable the button whatever the issue
        getDisplay().disableProperty().set(false);
      }

      return null;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected void failed() {
      super.failed();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected void succeeded() {
      super.succeeded();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected void running() {
      super.running();
    }


    public ActionableComp getActionableComp() {
      return actionableComp;
    }


    public void setActionableComp(ActionableComp actionableComp) {
      this.actionableComp = actionableComp;
    }
  };
}
