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
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class CardViewStructure extends ViewStructure implements IViewStructure {

  /**
   * Constructor
   */
  public CardViewStructure() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void selectTab(int tabIndex) {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void removeTab(String tabId) {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void removeTab(int tabIndex) {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void selectTab(String tabId) {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void add(RootStructureController rootStructure) {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void remove(RootStructureController rootStructure) {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void closeAllTabs() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public ObjectProperty<ViewStructureStatus> statusProperty() {
    return null;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public ViewStructureStatus getStatus() {
    return null;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setStatus(ViewStructureStatus status) {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public Pane getRootViewStructure() {
    return null;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Pane getRootViewStructureHeaderArea() {
    return null;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Pane getRootViewStructureContentArea() {
    return null;
  }
}
