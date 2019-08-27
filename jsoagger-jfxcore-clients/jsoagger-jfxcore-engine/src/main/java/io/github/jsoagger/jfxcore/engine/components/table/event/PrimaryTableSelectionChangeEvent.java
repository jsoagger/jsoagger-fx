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

package io.github.jsoagger.jfxcore.engine.components.table.event;



import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class PrimaryTableSelectionChangeEvent {

  private Object source;
  private AbstractViewController controller;
  private Object model;


  /**
   *
   * @param source
   */
  public PrimaryTableSelectionChangeEvent(AbstractViewController controller, Object source, Object model) {
    this.source = source;
    this.controller = controller;
    this.model = model;
  }


  /**
   * @return the source
   */
  public Object getSource() {
    return source;
  }


  /**
   * @param source the source to set
   */
  public void setSource(Object source) {
    this.source = source;
  }


  /**
   * @return the controller
   */
  public AbstractViewController getController() {
    return controller;
  }


  /**
   * @param controller the controller to set
   */
  public void setController(AbstractViewController controller) {
    this.controller = controller;
  }


  /**
   * @return the model
   */
  public Object getModel() {
    return model;
  }


  /**
   * @param model the model to set
   */
  public void setModel(Object model) {
    this.model = model;
  }
}
