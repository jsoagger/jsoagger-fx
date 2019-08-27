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

import javafx.scene.Node;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 1 févr. 2018
 */
public class UpdateCurrentLocationEvent extends GenericEvent {

  /**
   * Location string or location node
   */
  private Node locationNode = null;
  private boolean showHeader = false;
  private StructureContentController currentView = null;
  private boolean hasPrevious = false;

  /**
   * Indicating we are nagivating in tab content.
   * <p>
   * This navigation is special one because we dont replace the root structure content but the current view in the tabpane.
   */
  private boolean tabContentNavigation = false;


  /**
   * Default Constructor
   */
  public UpdateCurrentLocationEvent() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public Class getFilter() {
    return UpdateCurrentLocationEvent.class;
  }


  /**
   * Getter of showHeader
   *
   * @return the showHeader
   */
  public boolean getShowHeader() {
    return showHeader;
  }


  /**
   * Setter of showHeader
   *
   * @param showHeader the showHeader to set
   */
  public void setShowHeader(boolean showHeader) {
    this.showHeader = showHeader;
  }


  /**
   * Getter of currentView
   *
   * @return the currentView
   */
  public StructureContentController getCurrentView() {
    return currentView;
  }


  /**
   * Setter of currentView
   *
   * @param currentView the currentView to set
   */
  public void setCurrentView(StructureContentController currentView) {
    this.currentView = currentView;
  }


  public Node getLocationNode() {
    return locationNode;
  }


  public void setLocationNode(Node locationNode) {
    this.locationNode = locationNode;
  }


  public boolean isHasPrevious() {
    return hasPrevious;
  }


  public void setHasPrevious(boolean hasPrevious) {
    this.hasPrevious = hasPrevious;
  }


  public boolean isTabContentNavigation() {
    return tabContentNavigation;
  }


  public void setTabContentNavigation(boolean tabContentNavigation) {
    this.tabContentNavigation = tabContentNavigation;
  }
}
