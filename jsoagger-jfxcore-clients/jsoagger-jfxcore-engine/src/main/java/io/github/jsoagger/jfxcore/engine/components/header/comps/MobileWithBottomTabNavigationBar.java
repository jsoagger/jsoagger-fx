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

package io.github.jsoagger.jfxcore.engine.components.header.comps;



import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.header.event.HeaderNavbarFireBackButton;
import io.github.jsoagger.jfxcore.engine.components.tab.PopTabContentEvent;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.SetCurrentLocationEvent;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.UpdateCurrentLocationEvent;

/**
 * The only navigation bar that can handle navigation inside tab pane content.
 * <p>
 * Can be used in modile mode with bottom tabpane for example.
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class MobileWithBottomTabNavigationBar extends SingleLocationPrimaryMenuWithNavigationBar {

  private boolean isNavigatingTabContent = false;

  /**
   * Constructor.
   */
  public MobileWithBottomTabNavigationBar() {
    super();
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);
    locationContainer.managedProperty().bind(locationContainer.visibleProperty());
    locationContainer.setVisible(false);

    primaryMenuButton.getDisplay().managedProperty().bind(primaryMenuButton.getDisplay().visibleProperty());
  }

  @Override
  public void updateLocation(UpdateCurrentLocationEvent current) {
    // do nothing
    locationContainer.setVisible(false);
    isNavigatingTabContent = current.isTabContentNavigation();
    final boolean hasPrevious = current.isHasPrevious();
    backIcon.setVisible(hasPrevious);
    primaryMenuButton.getDisplay().setVisible(!hasPrevious);
  }

  @Override
  public void setCurrentLocationTo(SetCurrentLocationEvent event) {
    // do nothing
    locationContainer.setVisible(false);
  }


  /**
   * @param ev
   */
  @Override
  public void goBack(HeaderNavbarFireBackButton ev) {
    if (!isNavigatingTabContent) {
      super.goBack();
      return;
    }


  }


  @Override
  public void goBack() {
    if (!isNavigatingTabContent) {
      super.goBack();
      return;
    } else {
      PopTabContentEvent ev = new PopTabContentEvent();
      controller.dispatchEvent(ev);
    }
  }

  public void reinit() {
    isNavigatingTabContent = false;
    locationContainer.setVisible(false);
    backIcon.setVisible(false);
    primaryMenuButton.getDisplay().setVisible(true);
  }
}
