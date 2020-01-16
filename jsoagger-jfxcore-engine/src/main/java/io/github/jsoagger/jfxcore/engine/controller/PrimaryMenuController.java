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
import java.net.URL;

import com.google.gson.Gson;
import io.github.jsoagger.jfxcore.api.MenuConfigurationProvider;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewConfigXML;
import io.github.jsoagger.jfxcore.engine.components.menu.PrimaryMenu;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;

import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class PrimaryMenuController extends StandardViewController {

  private MenuConfigurationProvider menuProvider;

  /**
   * Constructor
   */
  public PrimaryMenuController() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void process() {
    final VLViewConfigXML primaryMenuConfig = getContextRootMenuConfig();

    try {
      final PrimaryMenu menu = new PrimaryMenu();
      menu.setMenuConfiguration(primaryMenuConfig);
      menu.buildFrom(this, null);
      processedView(menu);
    } catch (final Exception e) {
      e.printStackTrace();
      processedView(new StackPane());
    }
  }


  /**
   * Get the primary menu wizardConfiguration
   *
   * @return
   */
  private VLViewConfigXML getContextRootMenuConfig() {
    final URL primaryMenu = menuProvider.getMenuConfiguration(this);
    if(primaryMenu.toExternalForm().endsWith(".json")) {
      Gson gson = new Gson();
      InputStream io = PrimaryMenuController.class.getResourceAsStream(menuProvider.getMenuConfigLoction());
      VLViewConfigXML  finalResult = gson.fromJson(new InputStreamReader(io), VLViewConfigXML.class);
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
