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

package io.github.jsoagger.jfxcore.engine.controller.roostructure.util;



import io.github.jsoagger.jfxcore.engine.events.GenericEvent;

import javafx.scene.input.ScrollEvent;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class DScrollEvent extends GenericEvent {

  private ScrollEvent event;


  /**
   * Constructor
   */
  public DScrollEvent() {}


  /**
   * Getter of event
   *
   * @return the event
   */
  public ScrollEvent getEvent() {
    return event;
  }


  /**
   * Setter of event
   *
   * @param event the event to set
   */
  public void setEvent(ScrollEvent event) {
    this.event = event;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Class getFilter() {
    return DScrollEvent.class;
  }

  public static class Builder {

    private ScrollEvent event;


    public Builder event(ScrollEvent event) {
      this.event = event;
      return this;
    }


    public DScrollEvent build() {
      return new DScrollEvent(this);
    }
  }


  /**
   * Constructor
   * 
   * @param builder
   */
  private DScrollEvent(Builder builder) {
    this.event = builder.event;
  }
}
