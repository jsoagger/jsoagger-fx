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



import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import io.github.jsoagger.jfxcore.engine.utils.IconUtils;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class ComponentToMenuItemHelper extends ComponentToButtonBaseHelper {

  /**
   * Generates a links from a component
   *
   * @param controller
   * @param component
   * @return List
   */
  public static List<MenuItem> menuItemsFrom(final AbstractViewController controller, final List<VLViewComponentXML> actionsDefinition) {
    final List<MenuItem> result = new ArrayList<>();

    for (final VLViewComponentXML componentXML : actionsDefinition) {
      final MenuItem link = menuItemFrom(controller, componentXML);
      result.add(link);
    }

    return result;
  }


  /**
   * Generates an hyperlink from a component
   *
   * @param controller
   * @param actionDefinition
   * @return Button
   */
  public static MenuItem menuItemFrom(final AbstractViewController controller, final VLViewComponentXML actionDefinition) {

    final MenuItem menuItem = new MenuItem();
    menuItem.setId(actionDefinition.getId());

    final String title = actionDefinition.getPropertyValue(XMLConstants.LABEL);
    if (!StringUtils.isEmpty(title)) {
      menuItem.setText(controller.getLocalised(title));
    }

    Label label = new Label();
    IconUtils.setIcon(label, actionDefinition);
    menuItem.setGraphic(label);

    setOnAction(actionDefinition, menuItem, controller);
    return menuItem;
  }
}
