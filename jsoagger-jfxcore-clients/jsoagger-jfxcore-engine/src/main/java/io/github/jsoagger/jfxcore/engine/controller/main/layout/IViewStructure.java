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

package io.github.jsoagger.jfxcore.engine.controller.main.layout;




import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;

import javafx.beans.property.ObjectProperty;
import javafx.scene.layout.Pane;

/**
 * Root view structure. In desktop, view structure is organized in tab pane in top of the view. In
 * mobile, the view is organized in card like we can see in web browser on mobile application.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public interface IViewStructure {

  public void buildStructure();


  /**
   * This will be used as root pane of the scene.
   *
   * @return
   */
  public Pane getRootViewStructure();


  /**
   * The header of the layout(tabed or card)
   * 
   * @return
   */
  public Pane getRootViewStructureHeaderArea();


  /**
   * Each {@link RootStructureController} is layed out in this view.
   *
   * @return
   */
  public Pane getRootViewStructureContentArea();


  /**
   * @param tabIndex
   */
  public void selectTab(int tabIndex);


  /**
   * @param tabId
   */
  public void removeTab(String tabId);


  /**
   * @param tabIndex
   */
  public void removeTab(int tabIndex);


  /**
   * @param tabId
   */
  public void selectTab(String tabId);


  /**
   * @param rootStructure
   */
  public void add(RootStructureController rootStructure);


  /**
   * @param rootStructure
   */
  public void remove(RootStructureController rootStructure);


  /**
   * Close all tabs except the main tab
   */
  public void closeAllTabs();


  /**
   * @return
   */
  public ObjectProperty<ViewStructureStatus> statusProperty();


  /**
   * @return
   */
  public ViewStructureStatus getStatus();


  /**
   * @param status
   */
  public void setStatus(final ViewStructureStatus status);
}
