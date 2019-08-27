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




import java.text.SimpleDateFormat;
import java.util.UUID;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.OperationCallback;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.INotificationsManagement;
import io.github.jsoagger.jfxcore.api.NotificationLevel;
import io.github.jsoagger.jfxcore.api.NotificationStatus;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.notification.utils.NotificationDeletedEvent;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class NotificationView extends BorderPane implements IBuildable {

  protected static final String DATE_FORMAT = "dd MMM yyyy hh:mm";
  protected static final SimpleDateFormat SDF = new SimpleDateFormat(DATE_FORMAT);

  private String uuid = UUID.randomUUID().toString();
  private Notification notification;

  protected final Label icon = new Label();
  protected final Label title = new Label();
  protected final Label date = new Label();
  protected final Text message = new Text();

  protected final VBox centerContainer = new VBox();
  protected final HBox actionsContainer = new HBox();
  protected final VBox iconContainer = new VBox();

  protected Hyperlink deleteAction = new Hyperlink();
  protected INotificationsManagement notificationsManagement;

  protected AbstractViewController controller;


  /**
   * Constructor
   */
  public NotificationView() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.controller = (AbstractViewController) controller;
    notificationsManagement = (INotificationsManagement) Services.getBean("UserNotificationsManagementDelegate");

    getStyleClass().add("notification-view");
    if (notification.getStatus() == NotificationStatus.READEN) {
      pseudoClassStateChanged(PseudoClass.getPseudoClass("readen"), true);
    }

    // setLeft(iconContainer);
    setCenter(centerContainer);

    buildCenterArea();
    buildActionsArea();
    buildIconArea();

    title.textProperty().set(notification.getTitle());
    date.setText(SDF.format(notification.getCreationDate()));

    iconify(icon, notification.getLevel());
    managedProperty().bind(visibleProperty());
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }


  /**
   * @param label
   * @param type
   */
  public static void iconify(Label label, NotificationLevel type) {
    switch (type) {
      case ERROR:
        IconUtils.setFontIcon("mdi-error-outline:22", label);
        label.getGraphic().getStyleClass().add("grey-ikonli");
        break;

      case OK:
        IconUtils.setFontIcon("fa-check-circle-o:22", label);
        label.getGraphic().getStyleClass().add("grey-ikonli");
        break;

      case INFO:
        IconUtils.setFontIcon("mdi-info-outline:22", label);
        label.getGraphic().getStyleClass().add("grey-ikonli");
        break;

      case WARNING:
        IconUtils.setFontIcon("mdi-warning:22", label);
        label.getGraphic().getStyleClass().add("yellow-ikonli");
        break;
      default:
        break;
    }
  }


  protected void buildIconArea() {
    iconContainer.getChildren().add(icon);
    iconContainer.getStyleClass().add("notification-icon-container");
  }


  protected void buildActionsArea() {
    actionsContainer.getStyleClass().add("notification-view-actions-container");
    actionsContainer.getChildren().addAll(deleteAction);
    deleteAction.setOnAction(e -> delete());

    deleteAction.setOpacity(0.54);
    deleteAction.setFocusTraversable(false);
    deleteAction.getStyleClass().addAll("button-transparent-border-primary", "scale-down-on-click");
    deleteAction.setText(controller.getGLocalised("REMOVE_LABEL").toUpperCase());
    // IconUtils.setFontIcon("mdi-close:14", "grey-ikonli", deleteAction);
  }


  private void delete() {
    OperationCallback callback = new OperationCallback.Builder().onSuccess(this::onDeleteSuccess).build();
    notificationsManagement.delete(notification, callback);
  }


  private void onDeleteSuccess(IOperationResult operationResult) {
    NotificationDeletedEvent event = new NotificationDeletedEvent.Builder().model(this).build();
    controller.dispatchEvent(event);
  }


  public void markReaden() {
    pseudoClassStateChanged(PseudoClass.getPseudoClass("readen"), true);
  }


  protected void buildCenterArea() {
    HBox firtsRow = new HBox();
    firtsRow.getChildren().addAll(date, NodeHelper.horizontalSpacer());
    centerContainer.getStyleClass().add("notification-view-center-container");
    centerContainer.getChildren().addAll(firtsRow, title, actionsContainer);

    title.getStyleClass().add("notification-title");
    date.getStyleClass().add("notification-date");
    deleteAction.setVisible(true);

    if (notification.getContentType().equals("text/plain")) {
      message.getStyleClass().add("notification-message");
      // centerContainer.getChildren().add(message);
    } else {

      Platform.runLater(() -> {
        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        webEngine.loadContent(
            "<body style=\"background-color:#757575\">\r\n" + "\r\n" + "<p>\r\n" + "	<span\r\n" + "		style=\"color:white;font-family: 'Open sans', 'Arial', sans-serif; font-size: 10px\">\r\n"
                + "		Le job a &eacute;t&eacute; execut&eacute; avec succ&egrave;s</span>\r\n" + "</p>\r\n" + "</body>\r\n" + "");

        // Add the WebView to the VBox
        // centerContainer.getChildren().add(browser);
      });
    }
  }


  public boolean isUnreaden() {
    return notification.getStatus() == NotificationStatus.NEW;
  }


  public void setReaden(boolean readen) {
    pseudoClassStateChanged(PseudoClass.getPseudoClass("readen"), readen);
  }


  /**
   * Getter of notification
   *
   * @return the notification
   */
  public Notification getNotification() {
    return notification;
  }


  /**
   * Setter of notification
   *
   * @param notification the notification to set
   */
  public void setNotification(Notification notification) {
    this.notification = notification;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (uuid == null ? 0 : uuid.hashCode());
    return result;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    NotificationView other = (NotificationView) obj;
    if (uuid == null) {
      if (other.uuid != null) {
        return false;
      }
    } else if (!uuid.equals(other.uuid)) {
      return false;
    }
    return true;
  }
}
