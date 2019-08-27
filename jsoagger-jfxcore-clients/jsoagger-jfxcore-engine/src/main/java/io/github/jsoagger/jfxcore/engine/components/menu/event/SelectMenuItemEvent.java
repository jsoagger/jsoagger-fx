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

package io.github.jsoagger.jfxcore.engine.components.menu.event;



import io.github.jsoagger.jfxcore.engine.events.GenericEvent;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class SelectMenuItemEvent extends GenericEvent {

  private String identifier;


  /**
   * Constructor
   */
  public SelectMenuItemEvent() {}


  /**
   * Getter of identifier
   *
   * @return the identifier
   */
  public String getIdentifier() {
    return identifier;
  }


  /**
   * Setter of identifier
   *
   * @param identifier the identifier to set
   */
  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public static class Builder {

    private String identifier;


    public Builder identifier(String identifier) {
      this.identifier = identifier;
      return this;
    }


    public SelectMenuItemEvent build() {
      return new SelectMenuItemEvent(this);
    }
  }


  private SelectMenuItemEvent(Builder builder) {
    this.identifier = builder.identifier;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Class getFilter() {
    return SelectMenuItemEvent.class;
  }
}
