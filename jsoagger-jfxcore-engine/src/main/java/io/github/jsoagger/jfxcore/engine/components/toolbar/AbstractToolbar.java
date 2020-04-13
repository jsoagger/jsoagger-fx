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

package io.github.jsoagger.jfxcore.engine.components.toolbar;


import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IDisplayable;
import io.github.jsoagger.jfxcore.api.IToolbarHolder;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;
import io.github.jsoagger.jfxcore.engine.utils.ToolbarUtils;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.scene.Node;

/**
 * Example of Basic horizontal toolbar:
 * <p>
 *
 * @formatter:off <component id="BasicToolbarActions"> <properties>
 *                <property name="styleClass" value="transparent-background,empty-border"/>
 *                <property name="toolbarImpl" value="BasicHToolbar"/> </properties>
 *
 *                <component id="EllypsisMenuActions"/> <component id="RootMenuActions">
 *                <component id="LeftActions"/> <component id="CenterActions"/>
 *                <component id="RightActions"/> </component> </component>
 * @formatter:on
 *               <p>
 *
 *               Example of Modifiable horizontal toolbar:
 *               <p>
 * @formatter:off <component id="ModifiableToolbarActions"> <properties>
 *                <property name="styleClass" value="transparent-background,empty-border"/>
 *                <property name="toolbarImpl" value="ModifiableHToolbar"/> </properties>
 *
 *                <component id="EllypsisMenuActions"/> <component id="RootMenuActions">
 *                <component id="LeftActions"/> <component id="CenterActions"/>
 *                <component id="RightActions"/> </component>
 *
 *                <component id="ModifyMenuActions"> <component id="LeftActions"/>
 *                <component id="CenterActions"/> <component id="RightActions"/> </component>
 *                </component>
 * @formatter:on
 *               <p>
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public abstract class AbstractToolbar implements IDisplayable {

  public VLViewComponentXML getConfiguration() {
    return configuration;
  }


  /*-----------------------------------------------------------------------------
  | STATIC ATTRIBUTES
   *=============================================================================*/
  public static String ELLYPSISMENUACTIONS = "EllipsisMenuActions";
  public static String MODIFYMENUACTIONS = "ModifyMenuActions";
  public static String ROOTMENUACTIONS = "RootMenuActions";

  /*-----------------------------------------------------------------------------
  | PRIVATE ATTRIBUTES
   *=============================================================================*/
  protected VLViewComponentXML configuration;
  protected AbstractViewController controller;
  protected IToolbarHolder toolbarHolder;

  protected VLViewComponentXML ellypsisMenuConfiguration;
  protected VLViewComponentXML modifyMenuConfiguration;
  protected VLViewComponentXML rootMenuconfiguration;

  protected Node ellypisMenu = null;


  /**
   * Constructor
   */
  public AbstractToolbar() {}


  /**
   * @param controller
   * @param modifiableToolbarHolder
   */
  public void buildFrom(AbstractViewController controller, IToolbarHolder toolbarHolder) {
    this.controller = controller;
    this.toolbarHolder = toolbarHolder;
    if (toolbarHolder != null) {
      this.configuration = toolbarHolder.getToolbarConfiguration();
      this.ellypsisMenuConfiguration =
          configuration.getComponentById(ELLYPSISMENUACTIONS).orElse(null);
      this.modifyMenuConfiguration = configuration.getComponentById(MODIFYMENUACTIONS).orElse(null);
      this.rootMenuconfiguration = configuration.getComponentById(ROOTMENUACTIONS).orElse(null);
    }
  }


  protected void buildEllypsisMenu() {
    if ((this.ellypsisMenuConfiguration != null) && ellypsisMenuConfiguration.hasSubComponent()) {
      ellypisMenu =
          ToolbarUtils.ellipsisVActionsButton(ellypsisMenuConfiguration, controller, null);
    }
  }


  /**
   * Generates the components from the given configuration.
   *
   * @param groupDefinition
   * @return List
   */
  public List<IBuildable> buildActionGroup(VLViewComponentXML groupDefinition) {
    if (groupDefinition == null) {
      return new ArrayList<>();
    }

    List<IBuildable> buildables =
        ComponentUtils.resolveAndGenerate(controller, groupDefinition.getSubcomponents());
    if (buildables == null) {
      return new ArrayList<>();
    }
    return buildables;
  }

  public void setConfiguration(VLViewComponentXML configuration) {
    this.configuration = configuration;
    this.ellypsisMenuConfiguration =
        configuration.getComponentById(ELLYPSISMENUACTIONS).orElse(null);
    this.modifyMenuConfiguration = configuration.getComponentById(MODIFYMENUACTIONS).orElse(null);
    this.rootMenuconfiguration = configuration.getComponentById(ROOTMENUACTIONS).orElse(null);
  }
}
