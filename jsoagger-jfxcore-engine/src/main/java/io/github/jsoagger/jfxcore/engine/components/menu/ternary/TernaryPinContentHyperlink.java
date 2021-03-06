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

package io.github.jsoagger.jfxcore.engine.components.menu.ternary;




import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.events.PinSecondaryMenuRightEvent;
import io.github.jsoagger.jfxcore.engine.events.UnpinSecondaryMenuRightEvent;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Tooltip;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class TernaryPinContentHyperlink extends Hyperlink {

  private static final String SECONDARY_MENU_PIN_CONTENT_ICON = "secondary-menu-pin-content-icon";
  private final AbstractViewController controller;
  private boolean isPined = false;


  /**
   *
   * Constructor
   *
   * @param pinDef
   */
  public TernaryPinContentHyperlink(AbstractViewController controller) {
    super();
    this.controller = controller;

    setText(controller.getLocalised("PIN_LABEL"));
    setTooltip(new Tooltip(controller.getLocalised("PIN_TOOLTIP")));
    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    getStyleClass().add(SECONDARY_MENU_PIN_CONTENT_ICON);

    // first time, the content is no pin. That's why this rotation
    rotateProperty().set(90);
    setFocusTraversable(false);

    // set clicked action
    setOnAction(e -> pinContent());
  }


  /**
   * Pin or unpin the secondary menus
   */
  public void pinContent() {
    if (!isPined) {
      isPined = true;
      controller.publishEvent(new PinSecondaryMenuRightEvent());
      rotateProperty().set(0);
    } else {
      isPined = false;
      controller.publishEvent(new UnpinSecondaryMenuRightEvent());
      rotateProperty().set(90);
    }
  }


  /**
   * Get the controller
   *
   * @return the controller
   */
  public AbstractViewController getController() {
    return controller;
  }
}
