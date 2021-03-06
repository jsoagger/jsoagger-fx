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

package io.github.jsoagger.jfxcore.engine.components.tab;



import io.github.jsoagger.jfxcore.engine.controller.main.StandardTabPaneController;
import io.github.jsoagger.jfxcore.engine.events.GenericEvent;

/**
 * Should replace current content of the tab pane by the content of processed view form this event.
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class PushTabContentEvent extends GenericEvent {

  StandardTabPaneController parentController;

  /**
   * Constructor
   */
  public PushTabContentEvent() {
    super();
  }

  @Override
  public Class getFilter() {
    return PushTabContentEvent.class;
  }

  public StandardTabPaneController getParentController() {
    return parentController;
  }

  public void setParentController(StandardTabPaneController parentController) {
    this.parentController = parentController;
  }
}
