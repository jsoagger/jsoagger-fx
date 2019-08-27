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

package io.github.jsoagger.jfxcore.engine.websocket;



import java.net.URI;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
//@ClientEndpoint
public class WebsocketClientEndpoint {

  //private Session userSession = null;
  private WSMessageHandler messageHandler;


  /**
   * Constructor
   *
   * @param endpointURI
   */
  public WebsocketClientEndpoint(final URI endpointURI) {
    try {
      // final WebSocketContainer container =
      // ContainerProvider.getWebSocketContainer();
      // container.connectToServer(this, endpointURI);
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }


  //  //@OnOpen
  //  public void onOpen(final Session userSession) {
  //    this.userSession = userSession;
  //    System.out.println("Connection to remote websocket is OK!");
  //  }
  //
  //
  //  //@OnClose
  //  public void onClose(final Session userSession, final CloseReason reason) {
  //    this.userSession = null;
  //    System.out.println("Connection to remote websocket closed OK!");
  //  }
  //
  //
  //  //  @OnMessage
  //  public void onMessage(final String message) {
  //    if (messageHandler != null) {
  //      System.out.println("New message received, processing it ....");
  //      messageHandler.handleMessage(message);
  //    }
  //  }
  //
  //
  //  //  @OnError
  //  public void onError(Throwable t) {
  //    t.printStackTrace();
  //  }
  //
  //
  //  public void setMessageHandler(final WSMessageHandler msgHandler) {
  //    messageHandler = msgHandler;
  //  }
  //
  //
  //  public void sendMessage(final String message) {
  //    userSession.getAsyncRemote().sendText(message);
  //  }
}
