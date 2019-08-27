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
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.SetCurrentLocationEvent;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.UpdateCurrentLocationEvent;

/**
 * * A header toolbar where primary menu is displayed in the top right side, just on its left is
 * displayed the previous location label and below the primary button is displayed the current
 * location.
 * <p>
 * This navigation bar should be used only in the top header right of the application.
 * <p>
 * For nomwn, this toolbar is not minimizable, but should be in future.
 * <p>
 * The configuration file must declare only one button that is supposed to be the primary button.
 * This button will be layed out automatically on the primary button location.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class NoLocationPrimaryMenuWithNavigationBar extends SingleLocationPrimaryMenuWithNavigationBar {

  /**
   * Constructor
   */
  public NoLocationPrimaryMenuWithNavigationBar() {
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
  }

  @Override
  public void updateLocation(UpdateCurrentLocationEvent current) {
    // do nothing
    locationContainer.setVisible(false);

    final boolean hasPrevious = current.isHasPrevious();
    backIcon.setVisible(hasPrevious);
  }

  @Override
  public void setCurrentLocationTo(SetCurrentLocationEvent event) {
    // do nothing
    locationContainer.setVisible(false);
  }
}
