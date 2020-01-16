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



import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

/**
 * Send this event when force a {@link RootViewController} to update the currently displayed ui
 * inside it to the view to the given view. Currently raised when back button is pushed.
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class PopRootViewEvent extends GenericEvent {

  private AbstractViewController view;


  /**
   *
   * Constructor
   *
   * @param view
   */
  public PopRootViewEvent(AbstractViewController view) {
    this.view = view;
  }


  /**
   * @return the view
   */
  public AbstractViewController getView() {
    return view;
  }


  /**
   * @param view the view to set
   */
  public void setView(AbstractViewController view) {
    this.view = view;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Class getFilter() {
    return PopRootViewEvent.class;
  }
}
