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




import java.util.concurrent.CompletableFuture;

import io.github.jsoagger.jfxcore.engine.client.apiimpl.ActionRequest;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionHandler;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.layout.ViewStructure;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.PushStructureContentEvent;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.util.BuildRSContentEvent;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;
import io.github.jsoagger.jfxcore.engine.events.SetRootStructureEvent;
import io.github.jsoagger.jfxcore.engine.events.SetRootViewEvent;

import javafx.beans.value.ChangeListener;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class MenuHelper {

  /**
   * @param link
   * @return
   */
  public static void loadRootStructure(String id) {
    SetRootStructureEvent event = new SetRootStructureEvent.Builder().viewId(id).build();
    ViewStructure.instance().listenTo(event);
  }


  /**
   * @param link
   * @return
   */
  public static void loadRootView(String id, AbstractViewController controller) {
    SetRootViewEvent event = new SetRootViewEvent.Builder().viewId(id).build();

    // !! disptach the event inside the rootstructure and its children
    CompletableFuture.runAsync(() -> controller.dispatchEvent(event));
  }


  public static void loadViewContent(String location, String id, RootStructureController rootStructure) {
    BuildRSContentEvent ev = new BuildRSContentEvent.Builder().viewId(id).location(location).reinit(true).controller(rootStructure).build();

    // !! disptach the event inside the rootstructure and its children
    // !! no CompletableFuture -> IMPORTANT
    rootStructure.dispatchEvent(ev);
  }


  public static void loadViewContent(String location, String id, RootStructureController rootStructure, boolean reinit) {
    BuildRSContentEvent ev = new BuildRSContentEvent.Builder().viewId(id).location(location).reinit(reinit).controller(rootStructure).build();

    // !! disptach the event inside the rootstructure and its children
    // !! no CompletableFuture -> IMPORTANT
    rootStructure.dispatchEvent(ev);
  }


  /**
   * @param location
   * @param updateRSContentTo
   * @param controller
   */
  public static void updateRSContentTo(String location, String id, AbstractViewController controller) {
    PushStructureContentEvent ev = new PushStructureContentEvent.Builder().location(location).viewId(id).build();

    // !! disptach the event inside the rootstructure and its children
    // !! no CompletableFuture -> IMPORTANT
    CompletableFuture.runAsync(() -> controller.dispatchEvent(ev));
  }


  public static void loadChildView(String id, RootStructureController rootStructureController) {
    StandardViewUtils.forIdAndLayout(rootStructureController, id);
  }


  /**
   * @param actionName
   * @param controller
   */
  public static void doAction(String actionName, AbstractViewController controller) {
    Object bean = Services.getBean(actionName);
    IActionRequest request = new ActionRequest.Builder().controller(controller).build();

    // single action
    if (IAction.class.isAssignableFrom(bean.getClass())) {
      IAction action = (IAction) bean;
      action.execute(request, null);
      action.resultProperty().addListener((ChangeListener<IActionResult>) (observable, oldValue, newValue) -> {
        controller.handleActionResult(request, newValue);
      });

    }

    // action handler
    if (IActionHandler.class.isAssignableFrom(bean.getClass())) {
      IActionHandler actionHandler = (IActionHandler) bean;
      actionHandler.execute(request);
    }
  }


  public static void doShowWizardAction(String viewId, AbstractViewController controller) {
    Object bean = Services.getBean("ShowWizardAction");
    IActionRequest request = new ActionRequest.Builder()
        .controller(controller)
        .data("viewId", viewId)
        .build();

    // single action
    if (IAction.class.isAssignableFrom(bean.getClass())) {
      IAction action = (IAction) bean;
      action.execute(request, null);
      action.resultProperty().addListener((ChangeListener<IActionResult>) (observable, oldValue, newValue) -> {
        controller.handleActionResult(request, newValue);
      });

    }

    // action handler
    if (IActionHandler.class.isAssignableFrom(bean.getClass())) {
      IActionHandler actionHandler = (IActionHandler) bean;
      actionHandler.execute(request);
    }
  }

}
