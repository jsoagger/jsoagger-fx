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



import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.events.GenericEvent;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class BuildRSContentEvent extends GenericEvent {

  private String location;
  private String viewId;
  private AbstractViewController controller;
  private boolean isWizard = false;
  private boolean reinit = false;


  /**
   * Constructor
   */
  public BuildRSContentEvent() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Class getFilter() {
    return BuildRSContentEvent.class;
  }


  /**
   * Getter of location
   *
   * @return the location
   */
  public String getLocation() {
    return location;
  }


  /**
   * Setter of location
   *
   * @param location the location to set
   */
  public void setLocation(String location) {
    this.location = location;
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
   * Setter of viewId
   *
   * @param viewId the viewId to set
   */
  public void setViewId(String viewId) {
    this.viewId = viewId;
  }


  /**
   * Getter of controller
   *
   * @return the controller
   */
  public AbstractViewController getController() {
    return controller;
  }


  /**
   * Setter of controller
   *
   * @param controller the controller to set
   */
  public void setController(AbstractViewController controller) {
    this.controller = controller;
  }


  /**
   * Getter of isWizard
   *
   * @return the isWizard
   */
  public boolean isWizard() {
    return isWizard;
  }


  /**
   * Setter of isWizard
   *
   * @param isWizard the isWizard to set
   */
  public void setWizard(boolean isWizard) {
    this.isWizard = isWizard;
  }

  public static class Builder {

    private String location;
    private String viewId;
    private AbstractViewController controller;
    private boolean isWizard;
    private boolean reinit = false;


    public Builder location(String location) {
      this.location = location;
      return this;
    }


    public Builder viewId(String viewId) {
      this.viewId = viewId;
      return this;
    }


    public Builder reinit(boolean value) {
      reinit = value;
      return this;
    }


    public Builder controller(AbstractViewController controller) {
      this.controller = controller;
      return this;
    }


    public Builder isWizard(boolean isWizard) {
      this.isWizard = isWizard;
      return this;
    }


    public BuildRSContentEvent build() {
      return new BuildRSContentEvent(this);
    }
  }


  private BuildRSContentEvent(Builder builder) {
    location = builder.location;
    viewId = builder.viewId;
    controller = builder.controller;
    isWizard = builder.isWizard;
    reinit = builder.reinit;
  }


  /**
   * @return the reinit
   */
  public boolean isReinit() {
    return reinit;
  }


  /**
   * @param reinit the reinit to set
   */
  public void setReinit(boolean reinit) {
    this.reinit = reinit;
  }
}
