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

package io.github.jsoagger.jfxcore.engine.events;

import io.github.jsoagger.core.bridge.result.OperationData;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 26 févr. 2018
 */
public class LinkCreatedEvent extends GenericEvent {

  private OperationData roleA;

  /**
   * Default Constructor
   */
  public LinkCreatedEvent() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public Class getFilter() {
    return LinkCreatedEvent.class;
  }


  /**
   * @return the roleA
   */
  public OperationData getRoleA() {
    return roleA;
  }


  /**
   * @param roleA the roleA to set
   */
  public void setRoleA(OperationData roleA) {
    this.roleA = roleA;
  }
}
