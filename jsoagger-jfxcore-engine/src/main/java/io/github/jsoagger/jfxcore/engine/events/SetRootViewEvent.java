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



/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class SetRootViewEvent extends GenericEvent {

  private String viewId;
  private boolean wrapped;


  /**
   * Constructor
   */
  public SetRootViewEvent() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public Class getFilter() {
    return SetRootViewEvent.class;
  }


  /**
   * Getter of viewId
   *
   * @return the viewId
   */
  public String getViewId() {
    return viewId;
  }


  /**
   * Getter of wrapped
   *
   * @return the wrapped
   */
  public boolean isWrapped() {
    return wrapped;
  }


  /**
   * Setter of wrapped
   *
   * @param wrapped the wrapped to set
   */
  public void setWrapped(boolean wrapped) {
    this.wrapped = wrapped;
  }


  /**
   * Setter of viewId
   *
   * @param viewId the viewId to set
   */
  public void setViewId(String viewId) {
    this.viewId = viewId;
  }

  public static class Builder {

    private String viewId;
    private boolean wrapped;


    public Builder viewId(String viewId) {
      this.viewId = viewId;
      return this;
    }


    public Builder wrapped(boolean wrapped) {
      this.wrapped = wrapped;
      return this;
    }


    public SetRootViewEvent build() {
      return new SetRootViewEvent(this);
    }
  }


  private SetRootViewEvent(Builder builder) {
    this.viewId = builder.viewId;
    this.wrapped = builder.wrapped;
  }
}
