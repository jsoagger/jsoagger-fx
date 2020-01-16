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

package io.github.jsoagger.jfxcore.engine.client.components;



import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.scene.control.ButtonBase;
import javafx.scene.control.Labeled;
import javafx.scene.control.Tooltip;

/**
 * Set title, icon and tooltip of a labeled
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class ComponentToLabeledHelper {

  /**
   * Set the text of the {@link Labeled}. The text is localised and UPPER CASE
   *
   * @param component
   * @param controller
   * @param buttonbase return Boolean
   */
  private static boolean setText(final VLViewComponentXML component, final Labeled labeled, final AbstractViewController controller) {

    boolean added = false;
    final String title = component.getPropertyValue(XMLConstants.LABEL);
    if (!StringUtils.isEmpty(title)) {
      labeled.setText(controller.getLocalised(title, component).toUpperCase());
      added = true;
    }

    return added;
  }


  /**
   * Set the title of the {@link ButtonBase}
   *
   * @param component
   * @param buttonbase return Boolean
   */
  public static boolean setText(final VLViewComponentXML component, final Labeled labeled, boolean uppercase, AbstractViewController controller) {

    boolean added = false;

    if (uppercase) {
      return setText(component, labeled, controller);
    }

    final String title = component.getPropertyValue(XMLConstants.LABEL);
    if (!StringUtils.isEmpty(title)) {
      labeled.setText(controller.getLocalised(title, component));
      added = true;
    }

    return added;
  }


  /**
   * Set the tooltip of {@link ButtonBase}
   *
   * @param component
   * @param buttonbase
   */
  public static void setTooltip(final VLViewComponentXML component, final Labeled labeled, AbstractViewController controller) {
    final String tooltip = component.getPropertyValue(XMLConstants.TOOLTIP);
    if (!StringUtils.isEmpty(tooltip)) {
      labeled.setTooltip(new Tooltip(controller.getLocalised(tooltip, component)));
    }
  }
}
