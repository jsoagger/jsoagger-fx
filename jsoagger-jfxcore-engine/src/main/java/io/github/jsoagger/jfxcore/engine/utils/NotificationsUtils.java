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

package io.github.jsoagger.jfxcore.engine.utils;



import io.github.jsoagger.jfxcore.api.NotificationLevel;
import io.github.jsoagger.jfxcore.api.NotificationStatus;
import io.github.jsoagger.jfxcore.api.NotificationType;
import io.github.jsoagger.jfxcore.engine.components.notification.Notification;
import io.github.jsoagger.jfxcore.engine.components.notification.utils.NewNotificationEvent;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class NotificationsUtils {

  /**
   * @param controller
   */
  public static void sendObjectUpdateSuccessNotif(AbstractViewController controller) {

    // creates new local notification
    final Notification notification =
        new Notification.Builder().titleKey("OBJECT_UPDATED_MESSAGE_TITLE").messageKey("OBJECT_UPDATED_MESSAGE").title(controller.getGLocalised("OBJECT_UPDATED_MESSAGE_TITLE"))
        .message(controller.getGLocalised("OBJECT_UPDATED_MESSAGE")).status(NotificationStatus.NEW).type(NotificationType.LOCAL).level(NotificationLevel.OK).build();

    // dispatch it
    final NewNotificationEvent e = new NewNotificationEvent.Builder().model(notification).build();
    controller.dispatchEvent(e);
  }


  /**
   * @param controller
   */
  public static void sendObjectCreatedSuccessNotif(AbstractViewController controller) {

    // creates new local notification
    final Notification notification =
        new Notification.Builder().titleKey("OBJECT_CREATED_MESSAGE_TITLE").messageKey("OBJECT_CREATED_MESSAGE").title(controller.getGLocalised("OBJECT_CREATED_MESSAGE_TITLE"))
        .message(controller.getGLocalised("OBJECT_CREATED_MESSAGE")).status(NotificationStatus.NEW).type(NotificationType.LOCAL).level(NotificationLevel.OK).build();

    // dispatch it
    final NewNotificationEvent e = new NewNotificationEvent.Builder().model(notification).build();
    controller.dispatchEvent(e);
  }
}
