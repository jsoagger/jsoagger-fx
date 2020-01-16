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

package io.github.jsoagger.jfxcore.engine.controller;



import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.MenuConfigurationProvider;
import io.github.jsoagger.jfxcore.api.ResourceUtils;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewConfigXML;
import io.github.jsoagger.jfxcore.engine.components.menu.SecondaryMenu;
import io.github.jsoagger.jfxcore.engine.components.menu.event.SelectMenuItemEvent;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.events.CoreEvent;
import io.github.jsoagger.jfxcore.engine.events.GenericEvent;

import javafx.scene.layout.StackPane;

public class SecondaryMenuController extends StandardViewController {

  private static final String PLATFORM_PROPERTIES = "platformProperties";
  private static final String LOGIN_VIEW_ID = "loginViewId";

  private MenuConfigurationProvider menuProvider;
  private SecondaryMenu secondaryMenu;


  /**
   * Constructor
   */
  public SecondaryMenuController() {
    super();
    registerListener(CoreEvent.SelectMenuItemEvent);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void handle(GenericEvent e) {
    if (e.isA(CoreEvent.SelectMenuItemEvent)) {
      SelectMenuItemEvent event = (SelectMenuItemEvent) e;
      String menuId = event.getIdentifier();

      if (StringUtils.isNotBlank(menuId)) {
        secondaryMenu.select(menuId, true);
      }
    }
  }


  public void selectMenu(String id, boolean fire) {
    secondaryMenu.select(id, fire);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void process() {
    VLViewConfigXML secondaryMenuConfig = getMenuDefinition();

    try {
      secondaryMenu = new SecondaryMenu();
      secondaryMenu.setMenuConfiguration(secondaryMenuConfig);
      secondaryMenu.buildFrom(this, null);
      processedView(secondaryMenu);
    } catch (Exception e) {
      e.printStackTrace();
      processedView(new StackPane());
    }
  }


  /**
   * @return
   */
  private VLViewConfigXML getMenuDefinition() {
    String primaryMenu = menuProvider.getMenuConfigLoction();
    if (primaryMenu != null) {
      Gson gson = new Gson();
      InputStream io = ResourceUtils.getStream(VLViewConfigXML.class, primaryMenu.toString());
      VLViewConfigXML finalResult = gson.fromJson(new InputStreamReader(io), VLViewConfigXML.class);
      return finalResult;
    }

    return null;
  }


  /**
   * @param menuProvider the menuProvider to set
   */
  public void setMenuProvider(MenuConfigurationProvider menuProvider) {
    this.menuProvider = menuProvider;
  }
}
