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

package io.github.jsoagger.jfxcore.engine.controller.roostructure.header;




import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManageable;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.header.Toolbar;
import io.github.jsoagger.jfxcore.engine.components.header.event.HeaderNavbarFireBackButton;
import io.github.jsoagger.jfxcore.engine.components.header.event.HeaderNavbarSetCustomRightActions;
import io.github.jsoagger.jfxcore.engine.components.header.event.HeaderNavbarSetStandardRightActions;
import io.github.jsoagger.jfxcore.engine.components.tab.ReinitHeaderNavigationEvent;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.SetCurrentLocationEvent;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.UpdateCurrentLocationEvent;
import io.github.jsoagger.jfxcore.engine.events.CoreEvent;
import io.github.jsoagger.jfxcore.engine.events.GenericEvent;

/**
 * Controller for handling {@link Toolbar}.
 * <p>
 * It is usualy used in header of application but can be used elsewhere.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ToolbarController extends StandardViewController implements IViewLayoutManageable {

  /*-----------------------------------------------------------------------------
  | Static fields
   *=============================================================================*/
  private static final String TOOLBAR_IMPL = "toolbarImpl";

  /*-----------------------------------------------------------------------------
  | Private fields
   *=============================================================================*/
  protected Toolbar toolbar;


  /*-----------------------------------------------------------------------------
  | CONSTRUCTOR
   *=============================================================================*/
  /**
   * Constructor
   */
  public ToolbarController() {
    super();
    registerListener(CoreEvent.UpdateCurrentLocationEvent);
    registerListener(CoreEvent.HeaderNavbarSetCustomRightActions);
    registerListener(CoreEvent.HeaderNavbarSetStandardRightActions);
    registerListener(CoreEvent.HeaderNavbarFireBackButton);
    registerListener(CoreEvent.SetCurrentLocationEvent);
    registerListener(CoreEvent.ReinitHeaderNavigationEvent);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void process() {
    VLViewComponentXML rootComp = getRootComponent();
    String toolbarImpl = rootComp.getPropertyValue(TOOLBAR_IMPL);
    if (StringUtils.isBlank(toolbarImpl)) {
      toolbarImpl = "BasicToolbar";
    }

    toolbar = (Toolbar) Services.getBean(toolbarImpl);
    toolbar.buildFrom(this, rootComp);
    toolbar.setResponsiveMatrix(responsiveMatrix);
    processedView(toolbar);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public <T extends GenericEvent> void handle(T e) {
    super.handle(e);
    if (toolbar != null) {
      if (e.isA(CoreEvent.UpdateCurrentLocationEvent)) {
        UpdateCurrentLocationEvent ev = (UpdateCurrentLocationEvent) e;
        toolbar.updateLocation(ev);
      } else if (e.isA(CoreEvent.HeaderNavbarSetCustomRightActions)) {
        HeaderNavbarSetCustomRightActions ev = (HeaderNavbarSetCustomRightActions) e;
        toolbar.handle(ev);
      } else if (e.isA(CoreEvent.HeaderNavbarSetStandardRightActions)) {
        HeaderNavbarSetStandardRightActions ev = (HeaderNavbarSetStandardRightActions) e;
        toolbar.handle(ev);
      } else if (e.isA(CoreEvent.HeaderNavbarFireBackButton)) {
        HeaderNavbarFireBackButton ev = (HeaderNavbarFireBackButton) e;
        toolbar.handle(ev);
      } else if (e.isA(CoreEvent.SetCurrentLocationEvent)) {
        SetCurrentLocationEvent ev = (SetCurrentLocationEvent) e;
        toolbar.handle(ev);
      } else if (e.isA(CoreEvent.ReinitHeaderNavigationEvent)) {
        ReinitHeaderNavigationEvent ev = (ReinitHeaderNavigationEvent) e;
        toolbar.handle(ev);
      }
    }
  }
}
