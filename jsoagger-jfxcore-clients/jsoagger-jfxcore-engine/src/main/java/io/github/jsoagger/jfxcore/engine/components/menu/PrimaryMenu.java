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


import java.util.List;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;
/**
 * The application primary menu.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class PrimaryMenu extends Menu {

  /**
   * Constructor
   */
  public PrimaryMenu() {
    getStyleClass().add("ep-primary-menu");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);
    buildHeader();
    buildContent();
    // TODO correct call to jaxb in components utils
    //buildFooter();
  }


  protected void buildHeader() {
    VLViewComponentXML rootComp = controller.getRootComponent();
    VLViewComponentXML header = rootComp.getComponentById("Header").orElse(null);

    if (header != null && header.hasSubComponent()) {
      List<IBuildable> buildables = ComponentUtils.resolveAndGenerate(controller, header.getSubcomponents());
      for(IBuildable e: buildables) {
        maximizedMenu.getChildren().add(0, e.getDisplay());
      }
    }
  }


  protected void buildContent() {
    // if no custom style defined by user, use default one
    if (StringUtils.isNotBlank(maximizedMenu.menuStyleClass)) {
      maximizedMenu.getStyleClass().setAll(maximizedMenu.menuStyleClass.split(","));
    } else {
      maximizedMenu.getStyleClass().setAll(EP_MENU_CONTAINER_CSS);
    }
  }


  protected void buildFooter() {
    VLViewComponentXML rootComp = controller.getRootComponent();
    VLViewComponentXML footer = rootComp.getComponentById("Footer").orElse(null);
    maximizedMenu.getChildren().addAll(NodeHelper.verticalSpacer());

    if (footer != null && footer.hasSubComponent()) {
      List<IBuildable> buildables = ComponentUtils.resolveAndGenerate(controller, footer.getSubcomponents());
      for(IBuildable e: buildables) {
        maximizedMenu.getChildren().add(e.getDisplay());
      }
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void maximize() {
    // do nothing!!
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void minimize() {
    // do nothing!!
    maximize();
  }
}
