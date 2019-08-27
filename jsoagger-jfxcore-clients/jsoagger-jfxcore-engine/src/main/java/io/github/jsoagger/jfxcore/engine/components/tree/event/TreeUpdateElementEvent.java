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

package io.github.jsoagger.jfxcore.engine.components.tree.event;



import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class TreeUpdateElementEvent {

  private AbstractViewController sourceController;
  private Object model;


  /**
   * 
   * @param sourceController
   */
  public TreeUpdateElementEvent(AbstractViewController sourceController, Object model) {
    this.sourceController = sourceController;
    this.model = model;
  }


  /**
   * @return the sourceController
   */
  public AbstractViewController getSourceController() {
    return sourceController;
  }


  /**
   * @param sourceController the sourceController to set
   */
  public void setSourceController(AbstractViewController sourceController) {
    this.sourceController = sourceController;
  }


  /**
   * @param model the model to set
   */
  public void setModel(Object model) {
    this.model = model;
  }


  /**
   * @return the model
   */
  public Object getModel() {
    return model;
  }
}
