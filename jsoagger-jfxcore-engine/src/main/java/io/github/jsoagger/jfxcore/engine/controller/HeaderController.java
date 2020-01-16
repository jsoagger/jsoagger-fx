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




import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManageable;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.header.Toolbar;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;
import io.github.jsoagger.jfxcore.engine.events.DisplayMenuEvent;
import io.github.jsoagger.jfxcore.engine.events.GenericEvent;
import io.github.jsoagger.jfxcore.engine.events.MenuPos;
import io.github.jsoagger.jfxcore.engine.websocket.WSMessageHandler;
import io.github.jsoagger.jfxcore.engine.websocket.WebsocketClientEndpoint;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Node;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class HeaderController extends StandardViewController implements IViewLayoutManageable {

  /*-----------------------------------------------------------------------------
  | Static fields
   *=============================================================================*/
  private static final String CLIENT_ID = "client_01";
  private static final String WS_SERVER_URL = "ws://localhost:8080/jsoagger/notification/%s/%s";
  private static final String RIGHT_ACTIONS_BUTTON_PATH = "/Root/Content/%s/RightActionsButtons";
  private static final String LEFT_ACTIONS_BUTTON_PATH = "/Root/Content/%s/LeftActionsButtons";
  private static final String SEARCH_COMPONENT_PATH = "/Root/Content/%s/SearchComponent";
  private static final String PRIMARY_MENU = "primaryMenuId";
  private static final String HEADER = "Header";

  /*-----------------------------------------------------------------------------
  | Private fields
   *=============================================================================*/
  private int newMessagesCount = 0;
  private WebsocketClientEndpoint notificationsEndpoint;
  private WSMessageHandler webSocketMessageHandler;

  protected Toolbar toolbar;

  // needs platformProperties
  private Properties platformProperties;


  /*-----------------------------------------------------------------------------
  | CONSTRUCTOR
   *=============================================================================*/
  /**
   * Constructor
   */
  public HeaderController() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public synchronized <T extends GenericEvent> void handle(T e) {
    super.handle(e);
  }


  /*-----------------------------------------------------------------------------
  | PROCESS VIEW
   *=============================================================================*/
  @Override
  public void process() {
    buildToolbar();
    initWebsocketClient();

    processedView(toolbar);
    toolbar.setResponsiveMatrix(responsiveMatrix);
  }


  private void initWebsocketClient() {
    try {
      final String finalURL = String.format(WS_SERVER_URL, "TODO", CLIENT_ID);
      notificationsEndpoint = new WebsocketClientEndpoint(new URI(finalURL));
      //notificationsEndpoint.setMessageHandler(webSocketMessageHandler);
    } catch (final URISyntaxException e) {
    }
  }


  private void buildToolbar() {
    VLViewComponentXML rootComp = getRootComponent();
    String toolbarImpl = rootComp.getPropertyValue("toolbarImpl");
    if (StringUtils.isEmpty(toolbarImpl)) {
      toolbarImpl = "BasicHeaderToolbar";
    }

    toolbar = (Toolbar) Services.getBean(toolbarImpl);
    toolbar.buildFrom(this, rootComp);
  }


  /*-----------------------------------------------------------------------------
  | ACTIONS
   *=============================================================================*/
  public void expandRootMenu(Event event) {
    Platform.runLater(() -> {
      try {
        String controllerId = platformProperties.getProperty(PRIMARY_MENU);
        PrimaryMenuController primaryMenuController = (PrimaryMenuController) StandardViewUtils.forChildId(controllerId, null, this);
        Node primaryMenu = primaryMenuController.processedView();
        NodeHelper.setHVGrow(primaryMenu);

        final DisplayMenuEvent e = new DisplayMenuEvent(primaryMenu, MenuPos.PRIMARY_MENU);
        //publishEvent(e);
        dispatchEvent(e);
      } catch (Exception e) {
        // means no primary menu!
        //System.out.println("No primary menu defined!");
      }
    });
  }


  private void updateRightPopupContentTo(Node node) {
    final DisplayMenuEvent event = new DisplayMenuEvent(node, MenuPos.TERNARY_MENU);
    dispatchEvent(event);
    //publishEvent(event);
  }

  // @formatter:off
  private AbstractViewController notificationController = null;

  public void loadNotifications(Event event) {
    if (notificationController == null) {
      notificationController = StandardViewUtils.forChildId("NotificationsView", null, this);
    }
    notificationController = StandardViewUtils.forChildId("NotificationsView", null, this);
    updateRightPopupContentTo(notificationController.processedView());
  }
  // @formatter:on


  public void showTaskList(Event e) {
    //final PopOver filterPopOver = new PopOver();
    //filterPopOver.setDetachable(false);
    //filterPopOver.setArrowLocation(PopOver.ArrowLocation.TOP_RIGHT);
    //filterPopOver.setPrefHeight(150);
    //filterPopOver.setPrefWidth(200);
    //filterPopOver.show((Node) e.getSource());
    //filterPopOver.setContentNode(VLExecutorService.instance());
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void refreshDatas() {

  }


  /**
   * @param webSocketMessageHandler the webSocketMessageHandler to set
   */
  public void setWebSocketMessageHandler(WSMessageHandler webSocketMessageHandler) {
    this.webSocketMessageHandler = webSocketMessageHandler;
  }
}
