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

package io.github.jsoagger.jfxcore.engine.components.menu;


import java.net.URL;

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.MenuConfigurationProvider;
import io.github.jsoagger.jfxcore.api.ResourceUtils;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

/**
 * Provider of primary menu of application.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class SecondaryMenuProvider implements MenuConfigurationProvider {

  // injected
  private String menuDefinitionLocation;


  /**
   * Constructor
   */
  public SecondaryMenuProvider() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getMenuConfigLoction() {
    return menuDefinitionLocation;
  }



  /**
   * @{inheritedDoc}
   */
  @Override
  public URL getMenuConfiguration(IJSoaggerController context) {
    return ResourceUtils.getURL(AbstractViewController.class, menuDefinitionLocation);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public URL rootContextUpdate(IJSoaggerController context) {
    return getMenuConfiguration(context);
  }


  /**
   * Getter of menuDefinitionLocation
   *
   * @return the menuDefinitionLocation
   */
  public String getMenuDefinitionLocation() {
    return menuDefinitionLocation;
  }


  /**
   * Setter of menuDefinitionLocation
   *
   * @param menuDefinitionLocation the menuDefinitionLocation to set
   */
  public void setMenuDefinitionLocation(String menuDefinitionLocation) {
    this.menuDefinitionLocation = menuDefinitionLocation;
  }

}
