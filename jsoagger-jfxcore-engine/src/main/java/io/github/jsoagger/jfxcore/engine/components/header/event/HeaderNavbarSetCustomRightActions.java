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

package io.github.jsoagger.jfxcore.engine.components.header.event;



import java.util.List;

import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.engine.components.header.comps.HeaderRightToolbar;
import io.github.jsoagger.jfxcore.engine.events.GenericEvent;

/**
 * Content can modify {@link HeaderRightToolbar} content with this event.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class HeaderNavbarSetCustomRightActions extends GenericEvent {

  private List<IBuildable> actions;


  /**
   * Constructor
   */
  public HeaderNavbarSetCustomRightActions() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public Class getFilter() {
    return HeaderNavbarSetCustomRightActions.class;
  }


  /**
   * Getter of actions
   *
   * @return the actions
   */
  public List<IBuildable> getActions() {
    return actions;
  }


  /**
   * Setter of actions
   *
   * @param actions the actions to set
   */
  public void setActions(List<IBuildable> actions) {
    this.actions = actions;
  }

  public static class Builder {

    private List<IBuildable> actions;


    public Builder actions(List<IBuildable> actions) {
      this.actions = actions;
      return this;
    }


    public HeaderNavbarSetCustomRightActions build() {
      return new HeaderNavbarSetCustomRightActions(this);
    }
  }


  private HeaderNavbarSetCustomRightActions(Builder builder) {
    this.actions = builder.actions;
  }
}
