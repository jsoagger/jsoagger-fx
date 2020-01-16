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

package io.github.jsoagger.jfxcore.engine.client.utils;



import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 15 mars 2018
 */
public class ResizeHelper {

  public static void addResizeListener(Stage stage) {
    ResizeListener resizeListener = new ResizeListener(stage, null);
    stage.getScene().addEventHandler(MouseEvent.MOUSE_MOVED, resizeListener);
    stage.getScene().addEventHandler(MouseEvent.MOUSE_PRESSED, resizeListener);
    stage.getScene().addEventHandler(MouseEvent.MOUSE_DRAGGED, resizeListener);
    stage.getScene().addEventHandler(MouseEvent.MOUSE_EXITED, resizeListener);
    stage.getScene().addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, resizeListener);
    ObservableList<Node> children = stage.getScene().getRoot().getChildrenUnmodifiable();
    for (Node child : children) {
      // addListenerDeeply(child, resizeListener);
    }
  }


  public static void addResizeListener(Stage stage, Pane node) {
    ResizeListener resizeListener = new ResizeListener(stage, node);
    node.getScene().addEventHandler(MouseEvent.MOUSE_MOVED, resizeListener);
    node.getScene().addEventHandler(MouseEvent.MOUSE_PRESSED, resizeListener);
    node.getScene().addEventHandler(MouseEvent.MOUSE_DRAGGED, resizeListener);
    node.getScene().addEventHandler(MouseEvent.MOUSE_EXITED, resizeListener);
    node.getScene().addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, resizeListener);
  }


  public static void addListenerDeeply(Node node, EventHandler<MouseEvent> listener) {
    node.addEventHandler(MouseEvent.MOUSE_MOVED, listener);
    node.addEventHandler(MouseEvent.MOUSE_PRESSED, listener);
    node.addEventHandler(MouseEvent.MOUSE_DRAGGED, listener);
    node.addEventHandler(MouseEvent.MOUSE_EXITED, listener);
    node.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, listener);
    if (node instanceof Parent) {
      Parent parent = (Parent) node;
      ObservableList<Node> children = parent.getChildrenUnmodifiable();
      for (Node child : children) {
        addListenerDeeply(child, listener);
      }
    }
  }

  static class ResizeListener implements EventHandler<MouseEvent> {

    private Stage stage;
    private Pane forNode;

    private Cursor cursorEvent = Cursor.DEFAULT;
    private int border = 0;
    private double startX = 0;
    private double startY = 0;


    public ResizeListener(Stage stage, Pane forNode) {
      this.stage = stage;
      this.forNode = forNode;
    }


    @Override
    public void handle(MouseEvent mouseEvent) {
      EventType<? extends MouseEvent> mouseEventType = mouseEvent.getEventType();
      Scene scene = stage.getScene();

      double mouseEventX = mouseEvent.getSceneX(), mouseEventY = mouseEvent.getSceneY(), sceneWidth = forNode == null ? scene.getWidth() : forNode.getWidth(),
          sceneHeight = forNode == null ? scene.getHeight() : forNode.getWidth();

      if (MouseEvent.MOUSE_MOVED.equals(mouseEventType) == true) {
        if ((mouseEventX < border) && (mouseEventY < border)) {
          cursorEvent = Cursor.NW_RESIZE;
        } else if ((mouseEventX < border) && (mouseEventY > (sceneHeight - border))) {
          cursorEvent = Cursor.SW_RESIZE;
        } else if ((mouseEventX > (sceneWidth - border)) && (mouseEventY < border)) {
          cursorEvent = Cursor.NE_RESIZE;
        } else if ((mouseEventX > (sceneWidth - border)) && (mouseEventY > (sceneHeight - border))) {
          cursorEvent = Cursor.SE_RESIZE;
        } else if (mouseEventX < border) {
          cursorEvent = Cursor.W_RESIZE;
        } else if (mouseEventX > (sceneWidth - border)) {
          cursorEvent = Cursor.E_RESIZE;
        } else if (mouseEventY < border) {
          cursorEvent = Cursor.N_RESIZE;
        } else if (mouseEventY > (sceneHeight - border)) {
          cursorEvent = Cursor.S_RESIZE;
        } else {
          cursorEvent = Cursor.DEFAULT;
        }
        scene.setCursor(cursorEvent);
      } else if (MouseEvent.MOUSE_EXITED.equals(mouseEventType) || MouseEvent.MOUSE_EXITED_TARGET.equals(mouseEventType)) {
        scene.setCursor(Cursor.DEFAULT);
      } else if (MouseEvent.MOUSE_PRESSED.equals(mouseEventType) == true) {
        startX = stage.getWidth() - mouseEventX;
        startY = stage.getHeight() - mouseEventY;
      } else if (MouseEvent.MOUSE_DRAGGED.equals(mouseEventType) == true) {
        if (Cursor.DEFAULT.equals(cursorEvent) == false) {
          if ((Cursor.W_RESIZE.equals(cursorEvent) == false) && (Cursor.E_RESIZE.equals(cursorEvent) == false)) {
            double minHeight = stage.getMinHeight() > (border * 2) ? stage.getMinHeight() : (border * 2);
            if ((Cursor.NW_RESIZE.equals(cursorEvent) == true) || (Cursor.N_RESIZE.equals(cursorEvent) == true) || (Cursor.NE_RESIZE.equals(cursorEvent) == true)) {
              if ((stage.getHeight() > minHeight) || (mouseEventY < 0)) {
                stage.setHeight((stage.getY() - mouseEvent.getScreenY()) + stage.getHeight());
                stage.setY(mouseEvent.getScreenY());
              }
            } else {
              if ((stage.getHeight() > minHeight) || (((mouseEventY + startY) - stage.getHeight()) > 0)) {
                stage.setHeight(mouseEventY + startY);
              }
            }
          }

          if ((Cursor.N_RESIZE.equals(cursorEvent) == false) && (Cursor.S_RESIZE.equals(cursorEvent) == false)) {
            double minWidth = stage.getMinWidth() > (border * 2) ? stage.getMinWidth() : (border * 2);
            if ((Cursor.NW_RESIZE.equals(cursorEvent) == true) || (Cursor.W_RESIZE.equals(cursorEvent) == true) || (Cursor.SW_RESIZE.equals(cursorEvent) == true)) {
              if ((stage.getWidth() > minWidth) || (mouseEventX < 0)) {
                stage.setWidth((stage.getX() - mouseEvent.getScreenX()) + stage.getWidth());
                stage.setX(mouseEvent.getScreenX());
              }
            } else {
              if ((stage.getWidth() > minWidth) || (((mouseEventX + startX) - stage.getWidth()) > 0)) {
                stage.setWidth(mouseEventX + startX);
              }
            }
          }
        }

      }
    }
  }
}
