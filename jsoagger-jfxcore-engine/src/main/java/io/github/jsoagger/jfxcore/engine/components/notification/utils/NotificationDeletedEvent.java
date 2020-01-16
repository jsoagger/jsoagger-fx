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

package io.github.jsoagger.jfxcore.engine.components.notification.utils;



import io.github.jsoagger.jfxcore.engine.events.GenericEvent;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class NotificationDeletedEvent extends GenericEvent {

  /**
   * Constructor
   */
  public NotificationDeletedEvent() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Class getFilter() {
    return NotificationDeletedEvent.class;
  }

  /**
   * @author Ramilafananana VONJISOA
   * @mailto yvonjisoa@nexitia.com
   * @date 2019
   */
  public static class Builder {

    private Object model;


    public Builder model(Object model) {
      this.model = model;
      return this;
    }


    public NotificationDeletedEvent build() {
      return new NotificationDeletedEvent(this);
    }
  }


  private NotificationDeletedEvent(Builder builder) {
    this.model = builder.model;
  }
}
