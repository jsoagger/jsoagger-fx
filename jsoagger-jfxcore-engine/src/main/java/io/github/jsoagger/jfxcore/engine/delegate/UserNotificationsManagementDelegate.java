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

package io.github.jsoagger.jfxcore.engine.delegate;



import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.operation.JsonUtils;
import io.github.jsoagger.core.bridge.result.OperationCallback;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.DateUtils;
import io.github.jsoagger.jfxcore.api.INotification;
import io.github.jsoagger.jfxcore.api.INotificationsManagement;
import io.github.jsoagger.jfxcore.api.NotificationStatus;
import io.github.jsoagger.jfxcore.api.NotificationType;
import io.github.jsoagger.jfxcore.engine.components.notification.Notification;
import com.google.gson.JsonObject;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class UserNotificationsManagementDelegate implements INotificationsManagement {

  IOperation cloudCountOperation;// needs  CountUserNotificationsOperation
  IOperation cloudUpdateStatusOperation;//needs  CloudUpdateNotificationsStatusOperation
  IOperation cloudDeleteOperation;// needs  DeleteNotificationOperation
  IOperation cloudLoadOperation;//GetCurrentUserNotificationsOperation
  IOperation markAllNotificationsReaden;//MarkAllNotificationsReadenOperation
  IOperation deleteAllUserNotificationsOperation;//DeleteAllUserNotificationsOperation

  IOperation offlineCountOperation;//OfflineCountNotificationsOperation
  IOperation offlineUpdateStatusOperation;// OfflineUpdateNotificationsStatusOperation
  IOperation offlineDeleteOperation; // OfflineDeleteNotificationsOperation
  IOperation offlineSaveOperation;// OfflineSaveNotificationsOperation
  IOperation offlineLoadOperation; // OfflineLoadNotificationsOperation
  IOperation offlineMarkAllReadenOperation;// OfflineMarkAllReadenNotificationsOperation
  IOperation offlineDeleteAllOperation;// OfflineDeleteAllNotificationsOperation
  IOperation getCurrentUserOperation;//GetCurrentUserOperation


  /**
   * @{inheritedDoc}
   */
  @Override
  public void getAllNotifications(NotificationType type, NotificationStatus status, OperationCallback callback) {
    if (type == NotificationType.LOCAL) {
      getAllLocalNotifications(status, callback);
    }
    getAllRemoteNotifications(status, callback);
  }


  /*-----------------------------------------------------------------------------
  | LOAD REMOTE NOTIFICATIONS
   *=============================================================================*/
  /**
   * @return
   */
  @Override
  public void getAllRemoteNotifications(NotificationStatus status, OperationCallback callback) {
    if (callback != null) {
      if (cloudLoadOperation == null) {
        JsonObject query = new JsonObject();
        query.addProperty("page", 0);
        query.addProperty("pageSize", 5);
        cloudLoadOperation.doOperation(query, res -> callback.onSuccess(res));
        return;
      }

      JsonObject query = new JsonObject();
      cloudLoadOperation.doOperation(query, callback.getOnSuccess(), callback.getOnError());
      return;
    }
  }


  private void getAllRemoteNotifications(OperationCallback callback) {
    getAllRemoteNotifications(null, callback);
  }


  /*-----------------------------------------------------------------------------
  | LOAD LOCAL NOTIFICATIONS
   *=============================================================================*/
  /**
   * @return
   */
  private void getAllLocalNotifications(OperationCallback callback) {
    getAllLocalNotifications(null, callback);
  }


  /**
   * @return
   */
  @Override
  public void getAllLocalNotifications(NotificationStatus status, OperationCallback callback) {
    if (callback != null) {
      if (cloudLoadOperation != null) {
        JsonObject query = new JsonObject();
        // query.addProperty("status", status);
        cloudLoadOperation.doOperation(query, callback.getOnSuccess(), callback.getOnError());
      }
    }
  }


  /*-----------------------------------------------------------------------------
  | DELETE NOTIFICATIONS
   *=============================================================================*/
  /**
   * @{inheritedDoc}
   */
  @Override
  public void delete(INotification notification, OperationCallback callback) {
    if (notification != null) {
      if (notification.getType() == NotificationType.LOCAL) {
        if (offlineDeleteOperation == null) {
          String message = "can.not.find.service";
          callback.onError(new NullPointerException(message));
          return;
        }

        JsonObject query = new JsonObject();
        query.addProperty("uuid", notification.getUuid().toString());
        offlineDeleteOperation.doOperation(query, callback.getOnSuccess(), callback.getOnError());
      }

      if (notification.getType() == NotificationType.REMOTE) {
        if (cloudDeleteOperation == null) {
          String message = "can.not.find.service";
          callback.onError(new NullPointerException(message));
          return;
        }

        JsonObject query = new JsonObject();
        query.addProperty("fullId", notification.getSubjectFullId().toString());
        cloudDeleteOperation.doOperation(query, callback.getOnSuccess(), callback.getOnError());
      }
    } else {
      String message = "object.to.delete.no.longer.exist";
      callback.onError(new NullPointerException(message));
    }
  }


  /*-----------------------------------------------------------------------------
  | CREATE NOTIFICATIONS
   *=============================================================================*/
  /**
   * @{inheritedDoc}
   */
  @Override
  public void create(INotification notification, OperationCallback callback) {
    // can only create local notifications
    if (notification.getType() == NotificationType.LOCAL) {
      if (offlineSaveOperation == null) {
        String message = "can.not.find.service";
        callback.onError(new NullPointerException(message));
        return;
      }

      try {
        String notif = JsonUtils.toString(notification);
        JsonObject query = new JsonObject();
        query.addProperty("notification", notif);
        query.addProperty("date", DateUtils.dateString(notification.getCreationDate(), DateUtils.FILE_SAVE_FORMAT));
        offlineSaveOperation.doOperation(query, callback.getOnSuccess(), callback.getOnError());
      } catch (Exception e) {
        e.printStackTrace();
        callback.onError(e);
      }
    } else {
      String message = "unsupported.notification.type";
      callback.onError(new IllegalArgumentException(message));
    }
  }


  /*-----------------------------------------------------------------------------
  | COUNT NOTIFICATIONS
   *=============================================================================*/
  /**
   * @{inheritedDoc}
   */
  @Override
  public void getRemoteElementsCount(int status, OperationCallback callback) {
    if (status < 0 || status > 2 || cloudCountOperation == null) {
      callback.onSuccess(IOperationResult.emptyPaginatedData());
      return;
    }

    getCurrentUserOperation.doOperation(new JsonObject(), res -> {
      OperationData data = (OperationData) res.rootData();

      JsonObject query = new JsonObject();
      String login = (String) data.getAttributes().get("login");
      query.addProperty("login", login);
      // query.addProperty("status", status);
      cloudCountOperation.doOperation(query, callback.getOnSuccess(), callback.getOnError());

    });
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void getLocalElementsCount(int status, OperationCallback callback) {
    if (status < 0 || status > 2 || offlineCountOperation == null) {
      callback.onSuccess(IOperationResult.emptyPaginatedData());
      return;
    }

    JsonObject query = new JsonObject();
    if (status < 0) {
      query.addProperty("status", status);
    } else {
      query.addProperty("status", 0);
    }
    offlineCountOperation.doOperation(query, callback.getOnSuccess(), callback.getOnError());
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setElementStatus(List<Object> elements, int status, OperationCallback callback) {
    // delete the element
    if (status == 2) {
      // not supported
      callback.onError(new NullPointerException());
    }

    List<Notification> local = new ArrayList<>();
    List<Notification> remote = new ArrayList<>();

    for (Object object : elements) {
      Notification notification = (Notification) object;
      if (notification.getType() == NotificationType.LOCAL) {
        local.add(notification);
      }

      if (notification.getType() == NotificationType.REMOTE) {
        remote.add(notification);
      }
    }

    if (!local.isEmpty()) {
      if (offlineUpdateStatusOperation == null) {
        String message = "can.not.find.service";
        callback.onError(new NullPointerException(message));
        return;
      }

      JsonObject query = new JsonObject();
      query.addProperty("notifications", local.toString());
      query.addProperty("notifications", status);
      offlineUpdateStatusOperation.doOperation(query, callback.getOnSuccess(), callback.getOnError());
    }

    if (!remote.isEmpty()) {
      if (cloudUpdateStatusOperation == null) {
        String message = "can.not.find.service";
        callback.onError(new NullPointerException(message));
        return;
      }

      List<String> ids = new ArrayList<>();
      for (Notification notification : remote) {
        // SingleResult result = (SingleResult)
        // notification.getOperationResult();
        // ids.add(result.getData().getType());
      }

      JsonObject query = new JsonObject();
      query.addProperty("ids", ids.toString());
      query.addProperty("status", status);
      cloudUpdateStatusOperation.doOperation(query, callback.getOnSuccess(), callback.getOnError());
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void markAllReaden(OperationCallback callback) {
    if (callback != null) {
      if (markAllNotificationsReaden == null) {
        callback.onSuccess(IOperationResult.emptyPaginatedData());
        return;
      }

      getCurrentUserOperation.doOperation(new JsonObject(), res -> {
        JsonObject query = new JsonObject();
        query.addProperty("login", (String) ((OperationData) res.rootData()).getAttributes().get("login"));
        markAllNotificationsReaden.doOperation(query, callback.getOnSuccess(), callback.getOnError());
      });
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void deleteAll(OperationCallback callback) {
    if (callback != null) {
      if (deleteAllUserNotificationsOperation == null) {
        callback.onSuccess(IOperationResult.emptyPaginatedData());
        return;
      }

      getCurrentUserOperation.doOperation(new JsonObject(), res -> {
        JsonObject query = new JsonObject();
        query.addProperty("login", (String) ((OperationData) res.rootData()).getAttributes().get("login"));
        deleteAllUserNotificationsOperation.doOperation(query, callback.getOnSuccess(), callback.getOnError());
      });
    }
  }
}
