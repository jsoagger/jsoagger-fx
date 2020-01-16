/*-
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


import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import io.github.jsoagger.jfxcore.engine.client.apiimpl.ActionRequest;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IAction;
import io.github.jsoagger.jfxcore.api.IActionHandler;
import io.github.jsoagger.jfxcore.api.IActionRequest;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;
import io.github.jsoagger.jfxcore.engine.controller.utils.WizardViewUtils;
import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;

import javafx.beans.value.ChangeListener;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuItem;

/**
 * Helper for generating {@link Button} or {@link Hyperlink} from {@link VLViewComponentXML}
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public abstract class ComponentToButtonBaseHelper extends ComponentToLabeledHelper {

  public static final String HANDLER_ID = "Handler";

  /**
   * Set the action of the button if and only if the {@link VLViewComponentXML} have a subcomponent named Handler.
   *
   * @param component
   * @param buttonbase
   * @param controller
   */
  public static void setOnAction(final VLViewComponentXML component, final ButtonBase buttonbase, final AbstractViewController controller) {
    buttonbase.setOnAction(ev->{
      final Optional<VLViewComponentXML> actionComponent = component.getComponentById(HANDLER_ID);
      actionComponent.ifPresent(e -> setButtonActions(controller, e, buttonbase, ev));
    });
  }


  public static void setOnAction(VLViewComponentXML component, ButtonBase buttonbase, AbstractViewController controller, OperationData item) {
    buttonbase.setOnAction(ev->{
      final Optional<VLViewComponentXML> actionComponent = component.getComponentById(HANDLER_ID);
      actionComponent.ifPresent(e -> setButtonActions(controller, e, buttonbase, ev, item));
    });
  }

  public static void setOnAction(VLViewComponentXML component, ButtonBase buttonbase, AbstractViewController controller, OperationData item, Function<Object, Object> callback) {
    buttonbase.setOnAction(ev -> {
      final Optional<VLViewComponentXML> actionComponent = component.getComponentById(HANDLER_ID);
      actionComponent.ifPresent(e -> setButtonActions(controller, e, buttonbase, ev, item, callback));
    });
  }

  public static void setButtonActions(AbstractViewController controller, VLViewComponentXML actionComponent, Object source, Event ev, OperationData item, Function<Object, Object> callback) {

    Task<Void> task = new Task<Void>() {

      @Override
      protected Void call() throws Exception {
        boolean consumed = ev.isConsumed();

        if(!consumed) {
          ev.consume();
          String actionName = actionComponent.getPropertyValue("action");
          String args = actionComponent.getPropertyValue("args");

          if (StringUtils.isNotBlank(actionName)) {
            IActionRequest actionRequest = new ActionRequest.Builder().controller(controller).args(args).event(null).build();
            actionRequest.setProperty("sourceEvent", ev);
            actionRequest.setProperty("sourceControl", source);

            if (source != null && source instanceof ButtonBase) {
              actionRequest.setProperty("sourceActionableComp", ((ButtonBase) source).getUserData());
            }

            if (item != null) {
              actionRequest.setProperty("sourceData", item);
            }

            Object handler = Services.getBean(actionName);

            // single action
            if (IAction.class.isAssignableFrom(handler.getClass())) {
              IAction action = (IAction) handler;
              action.setData(item);
              actionRequest.setProperty("actionObject", action);
              action.resultProperty().addListener((ChangeListener<IActionResult>) (observable, oldValue, newValue) -> {
                controller.handleActionResult(actionRequest, newValue);
                if (callback != null)
                  callback.apply(newValue);
              });

              action.execute(actionRequest, null);
            }

            // action handler
            else if (IActionHandler.class.isAssignableFrom(handler.getClass())) {
              IActionHandler actionHandler = (IActionHandler) handler;
              actionHandler.execute(actionRequest);
              if (callback != null)
                callback.apply(null);
            }
          }

          else {

            if (source instanceof ButtonBase) {
              ButtonBase buttonbase = (ButtonBase) source;

              final String procedureList = actionComponent.getPropertyValue(XMLConstants.PROCEDURE);
              if (StringUtils.isNotBlank(procedureList)) {
                buttonbase.addEventHandler(ActionEvent.ACTION, event -> {
                  final List<String> methodNames = Arrays.asList(procedureList.split(";"));
                  for (final String methodName : methodNames) {
                    CompletableFuture.runAsync(() -> {
                      ReflectionUIUtils.callControllerMethod(controller, methodName, event);
                    }, AbstractApplicationRunner.TH_POOL);
                  }
                });
              } else {
                final String loadview = actionComponent.getPropertyValue(XMLConstants.LOAD_CHILD_VIEW);
                final String loadRootView = actionComponent.getPropertyValue(XMLConstants.LOAD_ROOT_VIEW);
                final String loadWizardView = actionComponent.getPropertyValue(XMLConstants.LOAD_WIZARD_VIEW);

                if (StringUtils.isNotBlank(loadview)) {
                  buttonbase.addEventHandler(ActionEvent.ACTION, event -> {
                    // loadChildView((StandardViewController) controller,
                    // loadview);
                  });
                } else if (StringUtils.isNotBlank(loadRootView)) {
                  buttonbase.addEventHandler(ActionEvent.ACTION, event -> {
                    loadRootView(controller, loadRootView);
                  });
                } else if (StringUtils.isNotBlank(loadWizardView)) {
                  buttonbase.addEventHandler(ActionEvent.ACTION, event -> {
                    // loadWizardView(controller, loadWizardView);
                  });
                }
                // view section
                else {
                  String viewName = actionComponent.getPropertyValue("view");
                  if (StringUtils.isNotBlank(viewName)) {
                    WizardViewUtils.forWizardId(controller.getRootStructure(), controller.getStructureContent(), viewName);
                  }
                }
              }
            }
          }
        }

        return null;
      }
    };

    new Thread(task).start();
  }

  /**
   * Execution action without callback a the end.
   *
   * @param controller
   * @param actionComponent
   * @param source
   * @param ev
   * @param item
   */
  public static void setButtonActions(AbstractViewController controller, VLViewComponentXML actionComponent, Object source, Event ev, OperationData item) {
    setButtonActions(controller, actionComponent, source, ev, item, null);
  }


  public static void setButtonActions(AbstractViewController controller, VLViewComponentXML actionComponent, ButtonBase buttonbase, Event ev) {
    setButtonActions(controller, actionComponent, buttonbase, ev, null);
  }


  /**
   * Loads and display the given root view.
   *
   * @param controller
   * @param viewId
   */
  private static void loadRootView(final AbstractViewController controller, String viewId) {
    CompletableFuture.runAsync(() -> {
      if (controller instanceof RootStructureController) {
        StandardViewUtils.forIdAndLayout(controller, viewId);
      } else {
        RootStructureController rootStructureController = controller.getRootStructure();
        StandardViewUtils.forIdAndLayout(rootStructureController, viewId);
      }

    }, AbstractApplicationRunner.TH_POOL);
  }


  /**
   * Set the action of the button
   *
   * @param component
   * @param menuItem
   * @param view
   */
  public static void setOnAction(final VLViewComponentXML component, final MenuItem menuItem, final AbstractViewController view) {}


  /**
   * Set the action of the button
   *
   * @param component
   * @param buttonbase
   * @param controller
   */
  public static void setOnContextualAction(final VLViewComponentXML component, final ButtonBase buttonbase, final AbstractViewController controller, Object parameter) {}

  private static class ButtonBaseService extends Service<Void> {

    Task<Void> runnable;
    String id;

    public ButtonBaseService(String id, Task<Void> task) {
      this.id = id;
      this.runnable = runnable;
    }

    @Override
    protected Task<Void> createTask() {
      return runnable;
    }
  }
}
