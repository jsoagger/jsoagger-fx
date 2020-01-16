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


import java.util.List;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.NotificationStatus;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.notification.utils.NewNotificationEvent;
import io.github.jsoagger.jfxcore.engine.components.notification.utils.NotificationDeletedEvent;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class NotificationsPane implements IBuildable {

  private AbstractViewController controller;
  private VLViewComponentXML configuration;

  private ObservableList<NotificationView> notifications = FXCollections.observableArrayList();
  private NoNotificationsPane noNotificationsPane;

  private NotifsPane notifsPane;
  private ObservableList<NotificationStatus> filter = FXCollections.observableArrayList(NotificationStatus.NEW);

  private StackPane externalPane = new StackPane();
  private StackPane internalPane = new StackPane();


  /**
   * Constructor
   */
  public NotificationsPane() {
    internalPane.getStyleClass().add("ternary-menu-notifications-wrapper");
    externalPane.getStyleClass().add("ternary-menu-notifications-external-wrapper");
    externalPane.getChildren().add(internalPane);
    NodeHelper.setVgrow(internalPane, externalPane);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return externalPane;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.configuration = configuration;
    this.controller = (AbstractViewController) controller;

    // TODO Handle internationalication
    // CoreGeneralMessageSource
    // ms = (ResourceBundleMessageSource) Services.getBean("CoreGeneralMessageSource");

    noNotificationsPane = new NoNotificationsPane();
    internalPane.getChildren().add(noNotificationsPane);
    noNotificationsPane.visibleProperty().bind(Bindings.size(notifications).lessThan(1));

    notifsPane = new NotifsPane();
    internalPane.getChildren().add(notifsPane);
    notifsPane.visibleProperty().bind(Bindings.size(notifications).greaterThan(0));

    filter.addListener((ListChangeListener<NotificationStatus>) c -> {
      onFilterChange(c);
    });
  }


  /**
   * @param c
   */
  private void onFilterChange(Change<? extends NotificationStatus> c) {
    ObservableList statuses = c.getList();
    for (NotificationView notificationView : notifications) {
      notificationView.setVisible(statuses.contains(notificationView.getNotification().getStatus()));
    }
  }


  /**
   * @param ev
   */
  public void handleNotification(NewNotificationEvent ev) {
    Notification notification = (Notification) ev.getModel();
    addNotification(notification);
  }


  /**
   * @param ev
   */
  public void handleNotification(NotificationDeletedEvent ev) {
    NotificationView nv = (NotificationView) ev.getModel();
    nv.setCache(true);
    nv.setCacheHint(CacheHint.DEFAULT);

    ScaleTransition st = NodeHelper.scaleOut(nv, Duration.millis(200));
    st.setOnFinished(e -> {
      notifsPane.removeNotif(nv);
      notifications.remove(nv);
    });
    st.play();
  }


  public void addNotification(Notification notification) {
    NotificationView nv = new NotificationView();
    nv.setNotification(notification);
    nv.buildFrom((IJSoaggerController) controller, configuration);

    nv.setCache(true);
    nv.setCacheHint(CacheHint.DEFAULT);

    if (Platform.isFxApplicationThread()) {
      notifsPane.addNotif(nv);
    } else {
      Platform.runLater(() -> {
        notifsPane.addNotif(nv);
      });
    }
    notifications.add(nv);

    ScaleTransition st = NodeHelper.scaleIn(nv, Duration.millis(200));
    st.play();
  }


  /**
   * @param result
   */
  public void onLocalNewNotificationsLoaded(IOperationResult result) {
    MultipleResult multipleResult = (MultipleResult) result;
    List<OperationData> datas = multipleResult.getData();

    for (OperationData data : datas) {
      Notification notification = new Notification.Builder().operationData(data).build();
      addNotification(notification);
    }
  }

  /**
   * @author Ramilafananana VONJISOA
   * @mailto yvonjisoa@nexitia.com
   * @date 2019
   */
  private class NotifsPane extends VBox {

    NotifsFilter notifsFilter = new NotifsFilter();
    VBox notifsContainer = new VBox();
    ScrollPane scrollPane = new ScrollPane();


    /**
     * Constructor
     */
    public NotifsPane() {
      setAlignment(Pos.TOP_CENTER);
      notifsContainer.getStyleClass().add("notifications-pane");
      NodeHelper.setVgrow(notifsContainer);
      // getChildren().addAll(notifsContainer, notifsFilter);
      scrollPane.setFitToHeight(true);
      scrollPane.setFitToWidth(true);
      getChildren().addAll(scrollPane);
      scrollPane.setContent(notifsContainer);
    }


    /**
     * @param nv
     */
    public void removeNotif(NotificationView nv) {
      notifsContainer.getChildren().remove(nv);
    }


    public void addNotif(NotificationView view) {
      notifsContainer.getChildren().add(0, view);
      VBox.setVgrow(view, Priority.NEVER);
    }


    public void clearAll() {
      notifsContainer.getChildren().clear();
    }
  }

  /**
   * @author Ramilafananana VONJISOA
   * @mailto yvonjisoa@nexitia.com
   * @date 2019
   */
  private class NotifsFilter extends HBox {

    private Hyperlink allNotifs = new Hyperlink();
    private Hyperlink newNotifs = new Hyperlink();


    /**
     * Constructor
     */
    public NotifsFilter() {
      //allNotifs.setText(ms.getMessage("ALL_NOTIFICATIONS_LABEL", null, null));
      //newNotifs.setText(ms.getMessage("NEW_NOTIFICATIONS_LABEL", null, null));

      allNotifs.setText("ALL_NOTIFICATIONS_LABEL");
      newNotifs.setText("NEW_NOTIFICATIONS_LABEL");

      NodeHelper.setHgrow(allNotifs, newNotifs);
      allNotifs.getStyleClass().addAll("notifications-filter-button", "transparent-focus", "scale-down-on-click");
      newNotifs.getStyleClass().addAll("notifications-filter-button", "transparent-focus", "scale-down-on-click");

      allNotifs.prefHeightProperty().bind(heightProperty());
      newNotifs.prefHeightProperty().bind(heightProperty());

      allNotifs.prefWidthProperty().bind(widthProperty().divide(2));
      newNotifs.prefWidthProperty().bind(widthProperty().divide(2));

      getChildren().addAll(allNotifs, newNotifs);
      getStyleClass().add("notifications-filters-container");

      allNotifs.setOnAction(e -> filter.setAll(NotificationStatus.NEW, NotificationStatus.READEN));
      newNotifs.setOnAction(e -> filter.setAll(NotificationStatus.NEW));
    }
  }

  /**
   * @author Ramilafananana VONJISOA
   * @mailto yvonjisoa@nexitia.com
   * @date 2019
   */
  private class NoNotificationsPane extends StackPane {

    private Label noNotofications = new Label();


    /**
     * Constructor
     */
    public NoNotificationsPane() {

      setAlignment(Pos.CENTER);

      String noNotoficationsLabel = controller.getLocalised("NO_NOTIFICATION_LABEL", configuration);

      noNotofications.getStyleClass().add("ternary-menu-no-notifications-label");
      noNotofications.setText(noNotoficationsLabel.toUpperCase());
      getChildren().add(noNotofications);
    }
  }


  /**
   * Mark all visible notifications as readen
   */
  public void markAllReaden() {
    for (NotificationView notificationView : notifications) {
      notificationView.markReaden();
    }
  }


  /**
   * Delete all notifications
   */
  public void deleteAll() {
    Platform.runLater(() -> {
      notifications.clear();
      notifsPane.clearAll();
    });
  }
}
