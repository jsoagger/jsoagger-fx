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



/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class ShowListViewEvent {

  private Object eventSource;
  private String sourceViewId;

  /**
   * @param eventSource
   * @param sourceViewId
   */
  public ShowListViewEvent(Object eventSource, String sourceViewId) {
    super();
    this.eventSource = eventSource;
    this.sourceViewId = sourceViewId;
  }

  /**
   * @return the eventSource
   */
  public Object getEventSource() {
    return eventSource;
  }

  /**
   * @param eventSource the eventSource to set
   */
  public void setEventSource(Object eventSource) {
    this.eventSource = eventSource;
  }

  /**
   * @return the sourceViewId
   */
  public String getSourceViewId() {
    return sourceViewId;
  }

  /**
   * @param sourceViewId the sourceViewId to set
   */
  public void setSourceViewId(String sourceViewId) {
    this.sourceViewId = sourceViewId;
  }


}
