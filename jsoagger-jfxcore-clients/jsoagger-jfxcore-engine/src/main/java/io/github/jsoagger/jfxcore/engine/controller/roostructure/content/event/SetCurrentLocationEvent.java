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

package io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event;



import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.events.GenericEvent;

import javafx.scene.Node;

/**
 * Use to set/update the header displayed in the root structure after the view is builded.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 1 févr. 2018
 */
public class SetCurrentLocationEvent extends GenericEvent {

  private Node location = null;
  private AbstractViewController controller;

  public AbstractViewController getController() {
    return controller;
  }


  public void setController(AbstractViewController controller) {
    this.controller = controller;
  }


  /**
   * Default Constructor
   */
  public SetCurrentLocationEvent() {
    super();
  }


  /**
   * Default Constructor
   */
  public SetCurrentLocationEvent(Node location) {
    this();
    this.location = location;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Class getFilter() {
    return SetCurrentLocationEvent.class;
  }


  public Node getLocation() {
    return location;
  }


  public void setLocation(Node location) {
    this.location = location;
  }
}
