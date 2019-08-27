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



import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.StructureContentController;
import io.github.jsoagger.jfxcore.engine.events.GenericEvent;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Display a view inside current root structure by building a {@link StructureContentController}.
 * Previous view can be redisplayed via
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class PushStructureContentEvent extends GenericEvent {

  /** The identifier of the view */
  private String viewId;

  /** the full identifier of the model to load by the view */
  private String modelFullId;

  private SimpleObjectProperty<StructureContentController> processedContent = new SimpleObjectProperty<>();
  private SimpleBooleanProperty showRootStructureHeader = new SimpleBooleanProperty(true);
  private String location;

  /**
   * Indicates whether the current view should be replaced or not
   */
  private boolean replace = false;


  /**
   * Constructor
   */
  public PushStructureContentEvent() {
    super();
  }


  public boolean wasProcessed() {
    return processedContent.get() != null;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Class getFilter() {
    return PushStructureContentEvent.class;
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
   * Getter of processedContent
   *
   * @return the processedContent
   */
  public StructureContentController getProcessedContent() {
    return processedContent.get();
  }


  /**
   * Setter of processedContent
   *
   * @param processedContent the processedContent to set
   */
  public void setProcessedContent(StructureContentController processedContent) {
    this.processedContent.set(processedContent);
  }


  public final SimpleBooleanProperty showRootStructureHeaderProperty() {
    return showRootStructureHeader;
  }


  public final boolean isShowRootStructureHeader() {
    return this.showRootStructureHeaderProperty().get();
  }


  public final void setShowRootStructureHeader(final boolean showRootStructureHeader) {
    this.showRootStructureHeaderProperty().set(showRootStructureHeader);
  }

  /**
   * @author Ramilafananana VONJISOA
   * @mailto yvonjisoa@nexitia.com
   * @date 2019
   */
  public static class Builder {

    /** The identifier of the view */
    private String viewId;

    /** the full identifier of the model to load by the view */
    private String modelFullId;

    private StructureContentController processedContent;
    private SimpleBooleanProperty showRootStructureHeader;
    private boolean pop = false;
    private boolean editing = false;
    private String location;
    private Object model;
    private boolean replace = false;


    public Builder model(Object model) {
      this.model = model;
      return this;
    }


    public Builder replace(boolean replace) {
      this.replace = replace;
      return this;
    }


    public Builder location(String location) {
      this.location = location;
      return this;
    }


    public Builder edit(boolean editing) {
      this.editing = editing;
      return this;
    }


    public Builder modelFullId(String modelFullId) {
      this.modelFullId = modelFullId;
      return this;
    }


    public Builder pop(boolean pop) {
      this.pop = pop;
      return this;
    }


    public Builder viewId(String viewId) {
      this.viewId = viewId;
      return this;
    }


    public Builder processedContent(StructureContentController processedContent) {
      this.processedContent = processedContent;
      return this;
    }


    public Builder showRootStructureHeader(SimpleBooleanProperty showRootStructureHeader) {
      this.showRootStructureHeader = showRootStructureHeader;
      return this;
    }


    public PushStructureContentEvent build() {
      return new PushStructureContentEvent(this);
    }
  }


  private PushStructureContentEvent(Builder builder) {
    viewId = builder.viewId;
    processedContent.set(builder.processedContent);
    modelFullId = builder.modelFullId;
    location = builder.location;
    model = builder.model;
    replace = builder.replace;

    if (builder.showRootStructureHeader == null) {
      showRootStructureHeader = new SimpleBooleanProperty(true);
    } else {
      showRootStructureHeader = builder.showRootStructureHeader;
    }
  }


  /**
   * Getter of showRootStructureHeader
   *
   * @return the showRootStructureHeader
   */
  public SimpleBooleanProperty getShowRootStructureHeader() {
    return showRootStructureHeader;
  }


  /**
   * Setter of showRootStructureHeader
   *
   * @param showRootStructureHeader the showRootStructureHeader to set
   */
  public void setShowRootStructureHeader(SimpleBooleanProperty showRootStructureHeader) {
    this.showRootStructureHeader = showRootStructureHeader;
  }


  /**
   * Getter of modelFullId
   *
   * @return the modelFullId
   */
  public String getModelFullId() {
    return modelFullId;
  }


  /**
   * Setter of modelFullId
   *
   * @param modelFullId the modelFullId to set
   */
  public void setModelFullId(String modelFullId) {
    this.modelFullId = modelFullId;
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


  public boolean isReplace() {
    return replace;
  }


  public void setReplace(boolean replace) {
    this.replace = replace;
  }


  public SimpleObjectProperty<StructureContentController> processedContentProperty() {
    return processedContent;
  }
}
