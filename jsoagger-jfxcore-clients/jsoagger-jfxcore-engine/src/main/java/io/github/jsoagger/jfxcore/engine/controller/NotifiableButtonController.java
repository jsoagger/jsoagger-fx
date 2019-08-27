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


import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.OperationCallback;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.INotificationsManagement;
import io.github.jsoagger.jfxcore.api.NotificationStatus;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.input.SimpleCountableHyperlinkButton;
import io.github.jsoagger.jfxcore.engine.components.notification.Notification;
import io.github.jsoagger.jfxcore.engine.components.notification.NotificationView;
import io.github.jsoagger.jfxcore.engine.components.notification.utils.NewNotificationEvent;
import io.github.jsoagger.jfxcore.engine.components.notification.utils.NotificationDeletedEvent;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.events.CoreEvent;
import io.github.jsoagger.jfxcore.engine.events.GenericEvent;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class NotifiableButtonController extends StandardViewController {

  private SimpleCountableHyperlinkButton button;
  private INotificationsManagement countDelegate;
  private SimpleIntegerProperty newElementsCount = new SimpleIntegerProperty();


  /**
   * Constructor
   */
  public NotifiableButtonController() {
    super();
    registerListener(CoreEvent.NotificationEvent);
    registerListener(CoreEvent.NotificationDeletedEvent);
    registerListener(CoreEvent.AllNotificationsClearedEvent);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public synchronized <T extends GenericEvent> void handle(T e) {
    super.handle(e);

    if (e.isA(CoreEvent.NotificationEvent)) {
      NewNotificationEvent ev = (NewNotificationEvent) e;
      if (ev.getModel() instanceof Notification) {
        OperationCallback createCallback = new OperationCallback.Builder().onSuccess(this::onCreateSuccess).build();
        countDelegate.create((Notification) ev.getModel(), createCallback);
      }
    } else if (e.isA(CoreEvent.NotificationDeletedEvent)) {
      NotificationDeletedEvent ev = (NotificationDeletedEvent) e;
      NotificationView nv = (NotificationView) ev.getModel();
      if (nv.getNotification().getStatus() == NotificationStatus.NEW) {
        updateCount(-1);
      }
    } else if (e.isA(CoreEvent.AllNotificationsClearedEvent)) {
      Platform.runLater(() -> {
        newElementsCount.set(0);
      });
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  protected void process() {
    super.process();

    VLViewComponentXML root = getRootComponent();
    String notifiableComponent = root.getPropertyValue("notifiableComponent");
    if (StringUtils.isNotBlank(notifiableComponent)) {

      VLViewComponentXML notifiableComponentCgf = ComponentUtils.resolveDefinition(this, notifiableComponent).orElse(null);

      button = new SimpleCountableHyperlinkButton();
      button.buildFrom(this, notifiableComponentCgf);
      processedView(button.getDisplay());
    }

    String delegateId = getRootComponent().getPropertyValue("countDelegate");
    button.countProperty().bind(newElementsCount);

    if (StringUtils.isNotBlank(delegateId)) {
      countDelegate = (INotificationsManagement) Services.getBean(delegateId);

      OperationCallback countCallback = new OperationCallback.Builder().onSuccess(this::onCountNewElementsSuccess).build();

      countDelegate.getLocalElementsCount(NotificationStatus.NEW.status(), countCallback);
      countDelegate.getRemoteElementsCount(NotificationStatus.NEW.status(), countCallback);
    }

  }


  private void onCreateSuccess(IOperationResult result) {
    updateCount(1);
  }


  private void onCountNewElementsSuccess(IOperationResult result) {
    int count = 0;
    try {
      count = Integer.valueOf(String.valueOf(result.getMetaData().get("totalElements")));
    }catch (Exception e) {
    }
    updateCount(count);
  }


  private void updateCount(int count) {
    if (Platform.isFxApplicationThread()) {
      int val = newElementsCount.get();

      if (val < 0) {
        newElementsCount.set(count);
      } else {
        newElementsCount.set(val + count);
      }
    } else {
      Platform.runLater(() -> {
        int val = newElementsCount.get();

        if (val < 0) {
          newElementsCount.set(count);
        } else {
          newElementsCount.set(val + count);
        }
      });
    }
  }
}
