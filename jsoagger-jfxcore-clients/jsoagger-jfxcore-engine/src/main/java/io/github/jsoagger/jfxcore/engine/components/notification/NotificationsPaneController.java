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

package io.github.jsoagger.jfxcore.engine.components.notification;


import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.OperationCallback;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.INotificationsManagement;
import io.github.jsoagger.jfxcore.engine.components.notification.action.AllNotificationsClearedEvent;
import io.github.jsoagger.jfxcore.engine.components.notification.action.DeleteAllNotificationsEvent;
import io.github.jsoagger.jfxcore.engine.components.notification.action.MarkAllNotificationsReadenEvent;
import io.github.jsoagger.jfxcore.engine.components.notification.utils.NewNotificationEvent;
import io.github.jsoagger.jfxcore.engine.components.notification.utils.NotificationDeletedEvent;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.events.CoreEvent;
import io.github.jsoagger.jfxcore.engine.events.GenericEvent;
import io.github.jsoagger.jfxcore.api.services.Services;

import javafx.concurrent.Task;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class NotificationsPaneController extends StandardViewController {

  private NotificationsPane notificationsPane = null;
  private INotificationsManagement notificationsManagement;


  /**
   * Constructor
   */
  public NotificationsPaneController() {
    super();
    registerListener(CoreEvent.NotificationEvent);
    registerListener(CoreEvent.NotificationDeletedEvent);
    registerListener(CoreEvent.MarkAllNotificationsReadenEvent);
    registerListener(CoreEvent.DeleteAllNotificationsEvent);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public synchronized <T extends GenericEvent> void handle(T e) {
    super.handle(e);
    if (e.isA(CoreEvent.NotificationEvent)) {
      NewNotificationEvent ev = (NewNotificationEvent) e;
      notificationsPane.handleNotification(ev);
    } else if (e.isA(CoreEvent.NotificationDeletedEvent)) {
      NotificationDeletedEvent ev = (NotificationDeletedEvent) e;
      notificationsPane.handleNotification(ev);
    } else if (e.isA(CoreEvent.MarkAllNotificationsReadenEvent)) {
      MarkAllNotificationsReadenEvent ev = (MarkAllNotificationsReadenEvent) e;
      handEvent(ev);
    } else if (e.isA(CoreEvent.DeleteAllNotificationsEvent)) {
      DeleteAllNotificationsEvent ev = (DeleteAllNotificationsEvent) e;
      handEvent(ev);
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  protected void process() {
    super.process();

    notificationsPane = new NotificationsPane();
    notificationsPane.buildFrom((IJSoaggerController) this, getRootComponent());
    processedView(notificationsPane.getDisplay());

    notificationsManagement = (INotificationsManagement) Services.getBean("UserNotificationsManagementDelegate");
    loaAllNotificationsAsync();
  }


  private void loaAllNotificationsAsync() {
    Task<Void> t = new Task<Void>() {

      @Override
      protected Void call() throws Exception {
        OperationCallback callback = new OperationCallback.Builder().onSuccess(o -> onNewNotificationsLoaded(o)).build();
        notificationsManagement.getAllLocalNotifications(null, callback);
        return null;
      }
    };
    Thread r = new Thread(t);
    r.setDaemon(true);
    r.start();
  }


  private void handEvent(DeleteAllNotificationsEvent ev) {
    OperationCallback callback = new OperationCallback.Builder().onSuccess(this::onAllNotificationsDeleted).build();
    notificationsManagement.deleteAll(callback);
  }


  private void handEvent(MarkAllNotificationsReadenEvent ev) {
    OperationCallback callback = new OperationCallback.Builder().onSuccess(this::onAllNotificationsMarkedAsReaden).build();
    notificationsManagement.markAllReaden(callback);
  }


  private void onAllNotificationsMarkedAsReaden(IOperationResult operationResult) {
    if (!operationResult.hasBusinessError()) {
      notificationsPane.markAllReaden();
      AllNotificationsClearedEvent ev = new AllNotificationsClearedEvent();
      dispatchEvent(ev);
    }
  }


  private void onAllNotificationsDeleted(IOperationResult operationResult) {
    if (!operationResult.hasBusinessError()) {
      notificationsPane.deleteAll();
      AllNotificationsClearedEvent ev = new AllNotificationsClearedEvent();
      dispatchEvent(ev);
    }
  }


  private void onNewNotificationsLoaded(IOperationResult result) {
    if (result != null) {
      notificationsPane.onLocalNewNotificationsLoaded(result);
    }
  }
}
