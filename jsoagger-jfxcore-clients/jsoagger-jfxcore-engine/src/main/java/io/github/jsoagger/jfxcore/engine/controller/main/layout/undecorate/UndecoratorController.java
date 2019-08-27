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

package io.github.jsoagger.jfxcore.engine.controller.main.layout.undecorate;




import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class UndecoratorController {

  private static boolean isMacOS = false;
  private static final int MAXIMIZE_BORDER = 20;
  private static final int DOCK_NONE = 0x0;
  private static final int DOCK_LEFT = 0x1;
  private static final int DOCK_RIGHT = 0x2;
  private static final int DOCK_TOP = 0x4;
  private static double initX = -1;
  private static double initY = -1;
  private static double newX;
  private static double newY;
  private static int RESIZE_PADDING;
  private static int SHADOW_WIDTH;

  private int lastDocked = DOCK_NONE;
  private Undecorator undecorator;
  private static boolean maximized = false;
  private BoundingBox savedBounds = null;
  private BoundingBox savedFullScreenBounds = null;


  /**
   * Constructor
   *
   * @param ud
   */
  public UndecoratorController(Undecorator ud) {
    undecorator = ud;
  }


  /**
   * Somme controls needs to know if screen is actualy maximized.
   *
   * @return
   */
  public static boolean isScreenMaximized() {
    return maximized;
  }


  /**
   * Restore the window to the previous saved bounds.
   */
  public void restore() {
    if (savedBounds != null) {
      Stage stage = undecorator.getStage();
      restoreSavedBounds(stage, false);
      undecorator.setShadow(true);
      savedBounds = null;
      maximized = false;
    }
  }


  /**
   * Maximize and ajust the window to screen size.
   */
  public void maximize() {
    Stage stage = undecorator.getStage();
    ObservableList<Screen> screensForRectangle = Screen.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
    Screen screen = screensForRectangle.get(0);
    Rectangle2D visualBounds = screen.getVisualBounds();

    // save bounds before maximizing in order to restore later
    savedBounds = new BoundingBox(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
    undecorator.setShadow(false);

    // effectivly maximize
    stage.setX(visualBounds.getMinX());
    stage.setY(visualBounds.getMinY());
    stage.setWidth(visualBounds.getWidth());
    stage.setHeight(visualBounds.getHeight());
    maximized = true;
  }


  public void saveBounds() {
    Stage stage = undecorator.getStage();
    savedBounds = new BoundingBox(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
  }


  public void saveFullScreenBounds() {
    Stage stage = undecorator.getStage();
    savedFullScreenBounds = new BoundingBox(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
  }


  public void restoreSavedBounds(Stage stage, boolean fullscreen) {
    if (savedBounds != null) {
      stage.setX(savedBounds.getMinX());
      stage.setY(savedBounds.getMinY());
      stage.setWidth(savedBounds.getWidth());
      stage.setHeight(savedBounds.getHeight());
      savedBounds = null;
    }
  }


  public void restoreFullScreenSavedBounds(Stage stage) {
    if (savedFullScreenBounds != null) {
      stage.setX(savedFullScreenBounds.getMinX());
      stage.setY(savedFullScreenBounds.getMinY());
      stage.setWidth(savedFullScreenBounds.getWidth());
      stage.setHeight(savedFullScreenBounds.getHeight());
      savedFullScreenBounds = null;
    }
  }


  protected void setFullScreen(boolean value) {
    Stage stage = undecorator.getStage();
    stage.setFullScreen(value);
  }


  public void close() {
    final Stage stage = undecorator.getStage();
    Platform.runLater(() -> stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST)));

  }


  public void iconify() {
    if (!Platform.isFxApplicationThread()) {
      Platform.runLater(() -> _iconify());
    } else {
      _iconify();
    }
  }


  private void _iconify() {
    Stage stage = undecorator.getStage();
    if (!stage.isIconified()) {
      stage.setIconified(true);
    }
  }


  /**
   * Stage resize management
   */
  public void setStageResizableWith(final Stage stage, final Node node, int PADDING, int SHADOW) {
    RESIZE_PADDING = PADDING;
    SHADOW_WIDTH = SHADOW;
    node.addEventFilter(MouseEvent.MOUSE_CLICKED, this::handleHeaderClicked);
    node.setOnMousePressed(mouseEvent -> {
      if (mouseEvent.isPrimaryButtonDown()) {
        initX = mouseEvent.getScreenX();
        initY = mouseEvent.getScreenY();
        mouseEvent.consume();
      }
    });
    node.setOnMouseDragged(mouseEvent -> {
      if (!mouseEvent.isPrimaryButtonDown() || (initX == -1 && initY == -1)) {
        return;
      }
      if (stage.isFullScreen()) {
        return;
      }
      /*
       * Long press generates drag event!
       */
      if (mouseEvent.isStillSincePress()) {
        return;
      }
      if (maximized) {
        // Remove maximized state
        undecorator.switchRestore();
        return;
      } // Docked then moved, so restore state
      else if (savedBounds != null) {
        undecorator.setShadow(true);
      }

      newX = mouseEvent.getScreenX();
      newY = mouseEvent.getScreenY();
      double deltax = newX - initX;
      double deltay = newY - initY;

      Cursor cursor = node.getCursor();
      if (Cursor.E_RESIZE.equals(cursor)) {
        setStageWidth(stage, stage.getWidth() + deltax);
        mouseEvent.consume();
      } else if (Cursor.NE_RESIZE.equals(cursor)) {
        if (setStageHeight(stage, stage.getHeight() - deltay)) {
          setStageY(stage, stage.getY() + deltay);
        }
        setStageWidth(stage, stage.getWidth() + deltax);
        mouseEvent.consume();
      } else if (Cursor.SE_RESIZE.equals(cursor)) {
        setStageWidth(stage, stage.getWidth() + deltax);
        setStageHeight(stage, stage.getHeight() + deltay);
        mouseEvent.consume();
      } else if (Cursor.S_RESIZE.equals(cursor)) {
        setStageHeight(stage, stage.getHeight() + deltay);
        mouseEvent.consume();
      } else if (Cursor.W_RESIZE.equals(cursor)) {
        if (setStageWidth(stage, stage.getWidth() - deltax)) {
          stage.setX(stage.getX() + deltax);
        }
        mouseEvent.consume();
      } else if (Cursor.SW_RESIZE.equals(cursor)) {
        if (setStageWidth(stage, stage.getWidth() - deltax)) {
          stage.setX(stage.getX() + deltax);
        }
        setStageHeight(stage, stage.getHeight() + deltay);
        mouseEvent.consume();
      } else if (Cursor.NW_RESIZE.equals(cursor)) {
        if (setStageWidth(stage, stage.getWidth() - deltax)) {
          stage.setX(stage.getX() + deltax);
        }
        if (setStageHeight(stage, stage.getHeight() - deltay)) {
          setStageY(stage, stage.getY() + deltay);
        }
        mouseEvent.consume();
      } else if (Cursor.N_RESIZE.equals(cursor)) {
        if (setStageHeight(stage, stage.getHeight() - deltay)) {
          setStageY(stage, stage.getY() + deltay);
        }
        mouseEvent.consume();
      }

    });
    node.setOnMouseMoved(mouseEvent -> {
      if (maximized) {
        setCursor(node, Cursor.DEFAULT);
        return; // maximized mode does not support resize
      }
      if (stage.isFullScreen()) {
        return;
      }
      if (!stage.isResizable()) {
        return;
      }
      double x = mouseEvent.getX();
      double y = mouseEvent.getY();
      Bounds boundsInParent = node.getBoundsInParent();
      if (isRightEdge(x, y, boundsInParent)) {
        if (y < RESIZE_PADDING + SHADOW_WIDTH) {
          setCursor(node, Cursor.NE_RESIZE);
        } else if (y > boundsInParent.getHeight() - (RESIZE_PADDING + SHADOW_WIDTH)) {
          setCursor(node, Cursor.SE_RESIZE);
        } else {
          setCursor(node, Cursor.E_RESIZE);
        }

      } else if (isLeftEdge(x, y, boundsInParent)) {
        if (y < RESIZE_PADDING + SHADOW_WIDTH) {
          setCursor(node, Cursor.NW_RESIZE);
        } else if (y > boundsInParent.getHeight() - (RESIZE_PADDING + SHADOW_WIDTH)) {
          setCursor(node, Cursor.SW_RESIZE);
        } else {
          setCursor(node, Cursor.W_RESIZE);
        }
      } else if (isTopEdge(x, y, boundsInParent)) {
        setCursor(node, Cursor.N_RESIZE);
      } else if (isBottomEdge(x, y, boundsInParent)) {
        setCursor(node, Cursor.S_RESIZE);
      } else {
        setCursor(node, Cursor.DEFAULT);
      }
    });
  }


  private void handleHeaderClicked(MouseEvent mouseEvent) {
    // to do decomment when reactive full screen
    // if (stage.getStyle() != StageStyle.UTILITY &&
    // !stage.isFullScreen() && mouseEvent.getClickCount() > 1) {
    if (undecorator.getStage().getStyle() != StageStyle.UTILITY && mouseEvent.getClickCount() > 1) {
      if (mouseEvent.getSceneY() - SHADOW_WIDTH < MAXIMIZE_BORDER) {
        if (!undecorator.maximizeProperty().get()) {
          undecorator.switchMaximize();
          mouseEvent.consume();
        } else {
          undecorator.switchRestore();
          mouseEvent.consume();
        }
      }
    }
  }


  /**
   * Under Windows, the undecorator Stage could be been dragged below the Task bar and then no way to
   * grab it again... On Mac, do not drag above the menu bar
   *
   * @param y
   */
  void setStageY(Stage stage, double y) {
    try {
      ObservableList<Screen> screensForRectangle = Screen.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
      if (screensForRectangle.size() > 0) {
        Screen screen = screensForRectangle.get(0);
        Rectangle2D visualBounds = screen.getVisualBounds();
        if (y < visualBounds.getHeight() - 30 && y + SHADOW_WIDTH >= visualBounds.getMinY()) {
          stage.setY(y);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  boolean setStageWidth(Stage stage, double width) {
    if (width >= stage.getMinWidth()) {
      stage.setWidth(width);
      initX = newX;
      return true;
    }
    return false;
  }


  boolean setStageHeight(Stage stage, double height) {
    if (height >= stage.getMinHeight()) {
      stage.setHeight(height);
      initY = newY;
      return true;
    }
    return false;
  }


  /**
   * Allow this node to drag the Stage
   *
   * @param stage
   * @param node
   */
  public void setAsStageDraggable(final Stage stage, final Node node) {
    node.setOnMouseClicked(mouseEvent -> {
      if (stage.getStyle() != StageStyle.UTILITY && !stage.isFullScreen() && mouseEvent.getClickCount() > 1) {
        if (mouseEvent.getSceneY() - SHADOW_WIDTH < MAXIMIZE_BORDER) {
          if (!undecorator.maximizeProperty().get()) {
            undecorator.switchMaximize();
            mouseEvent.consume();
          } else {
            undecorator.switchRestore();
            mouseEvent.consume();
          }
        }
      }
    });
    node.setOnMousePressed(mouseEvent -> {
      if (mouseEvent.isPrimaryButtonDown()) {
        initX = mouseEvent.getScreenX();
        initY = mouseEvent.getScreenY();
        mouseEvent.consume();
      } else {
        initX = -1;
        initY = -1;
      }
    });
    node.setOnMouseDragged(mouseEvent -> {
      if (!mouseEvent.isPrimaryButtonDown() || initX == -1) {
        return;
      }
      if (stage.isFullScreen()) {
        return;
      }
      /*
       * Long press generates drag event!
       */
      if (mouseEvent.isStillSincePress()) {
        return;
      }
      if (maximized) {
        // Remove Maximized state
        undecorator.switchRestore();
        // Center
        stage.setX(mouseEvent.getScreenX() - stage.getWidth() / 2);
        stage.setY(mouseEvent.getScreenY() - SHADOW_WIDTH);
      } // Docked then moved, so restore state
      else if (savedBounds != null) {
        restoreSavedBounds(stage, false);
        undecorator.setShadow(true);
        // Center
        stage.setX(mouseEvent.getScreenX() - stage.getWidth() / 2);
        stage.setY(mouseEvent.getScreenY() - SHADOW_WIDTH);
      }
      double newX = mouseEvent.getScreenX();
      double newY = mouseEvent.getScreenY();
      double deltax = newX - initX;
      double deltay = newY - initY;
      initX = newX;
      initY = newY;
      setCursor(node, Cursor.HAND);
      stage.setX(stage.getX() + deltax);
      setStageY(stage, stage.getY() + deltay);

      testDock(stage, mouseEvent);
      mouseEvent.consume();
    });
    node.setOnMouseReleased(t -> {
      if (stage.isResizable()) {
        undecorator.setDockFeedbackInvisible();
        setCursor(node, Cursor.DEFAULT);
        initX = -1;
        initY = -1;
        dockActions(stage, t);
      }
    });

    node.setOnMouseExited(mouseEvent -> {
      // setCursor(node, Cursor.DEFAULT);
    });

  }


  /**
   * (Humble) Simulation of Windows behavior on screen's edges Feedbacks
   */
  void testDock(Stage stage, MouseEvent mouseEvent) {

    if (!stage.isResizable()) {
      return;
    }

    int dockSide = getDockSide(mouseEvent);
    // Dock Left
    if (dockSide == DOCK_LEFT) {
      if (lastDocked == DOCK_LEFT) {
        return;
      }
      ObservableList<Screen> screensForRectangle = Screen.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
      Screen screen = screensForRectangle.get(0);
      Rectangle2D visualBounds = screen.getVisualBounds();
      // Dock Left
      double x = visualBounds.getMinX();
      double y = visualBounds.getMinY();
      double width = visualBounds.getWidth() / 2;
      double height = visualBounds.getHeight();

      undecorator.setDockFeedbackVisible(x, y, width, height);
      lastDocked = DOCK_LEFT;
    } // Dock Right
    else if (dockSide == DOCK_RIGHT) {
      if (lastDocked == DOCK_RIGHT) {
        return;
      }
      ObservableList<Screen> screensForRectangle = Screen.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
      Screen screen = screensForRectangle.get(0);
      Rectangle2D visualBounds = screen.getVisualBounds();
      // Dock Right (visualBounds = (javafx.geometry.Rectangle2D)
      // Rectangle2D [minX = 1440.0, minY=300.0, maxX=3360.0, maxY=1500.0,
      // width=1920.0, height=1200.0])
      double x = visualBounds.getMinX() + visualBounds.getWidth() / 2;
      double y = visualBounds.getMinY();
      double width = visualBounds.getWidth() / 2;
      double height = visualBounds.getHeight();

      undecorator.setDockFeedbackVisible(x, y, width, height);
      lastDocked = DOCK_RIGHT;
    } // Dock top
    else if (dockSide == DOCK_TOP) {
      if (lastDocked == DOCK_TOP) {
        return;
      }
      ObservableList<Screen> screensForRectangle = Screen.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
      Screen screen = screensForRectangle.get(0);
      Rectangle2D visualBounds = screen.getVisualBounds();
      // Dock Left
      double x = visualBounds.getMinX();
      double y = visualBounds.getMinY();
      double width = visualBounds.getWidth();
      double height = visualBounds.getHeight();
      undecorator.setDockFeedbackVisible(x, y, width, height);
      lastDocked = DOCK_TOP;
    } else {
      undecorator.setDockFeedbackInvisible();
      lastDocked = DOCK_NONE;
    }
  }


  /**
   * Based on mouse position returns dock side
   *
   * @param mouseEvent
   * @return DOCK_LEFT,DOCK_RIGHT,DOCK_TOP
   */
  int getDockSide(MouseEvent mouseEvent) {
    double minX = Double.POSITIVE_INFINITY;
    double minY = Double.POSITIVE_INFINITY;
    double maxX = 0;
    double maxY = 0;

    // Get "big" screen bounds
    ObservableList<Screen> screens = Screen.getScreens();
    for (Screen screen : screens) {
      Rectangle2D visualBounds = screen.getVisualBounds();
      minX = Math.min(minX, visualBounds.getMinX());
      minY = Math.min(minY, visualBounds.getMinY());
      maxX = Math.max(maxX, visualBounds.getMaxX());
      maxY = Math.max(maxY, visualBounds.getMaxY());
    }
    // Dock Left
    if (mouseEvent.getScreenX() == minX) {
      return DOCK_LEFT;
    } else if (mouseEvent.getScreenX() >= maxX - 1) { // MaxX returns the
                                                      // width? Not width -1
                                                      // ?!
      return DOCK_RIGHT;
    } else if (mouseEvent.getScreenY() <= minY) { // Mac menu bar
      return DOCK_TOP;
    }
    return 0;
  }


  /**
   * (Humble) Simulation of Windows behavior on screen's edges Actions
   */
  void dockActions(Stage stage, MouseEvent mouseEvent) {
    ObservableList<Screen> screensForRectangle = Screen.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
    Screen screen = screensForRectangle.get(0);
    Rectangle2D visualBounds = screen.getVisualBounds();
    // Dock Left
    if (mouseEvent.getScreenX() == visualBounds.getMinX()) {
      savedBounds = new BoundingBox(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());

      stage.setX(visualBounds.getMinX());
      stage.setY(visualBounds.getMinY());
      // Respect Stage Max size
      double width = visualBounds.getWidth() / 2;
      if (stage.getMaxWidth() < width) {
        width = stage.getMaxWidth();
      }

      stage.setWidth(width);

      double height = visualBounds.getHeight();
      if (stage.getMaxHeight() < height) {
        height = stage.getMaxHeight();
      }

      stage.setHeight(height);
      undecorator.setShadow(false);
    } // Dock Right (visualBounds = [minX = 1440.0, minY=300.0, maxX=3360.0,
      // maxY=1500.0, width=1920.0, height=1200.0])
    else if (mouseEvent.getScreenX() >= visualBounds.getMaxX() - 1) { // MaxX
                                                                      // returns
                                                                      // the
                                                                      // width?
                                                                      // Not
                                                                      // width
                                                                      // -1
                                                                      // ?!
      savedBounds = new BoundingBox(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());

      stage.setX(visualBounds.getWidth() / 2 + visualBounds.getMinX());
      stage.setY(visualBounds.getMinY());
      // Respect Stage Max size
      double width = visualBounds.getWidth() / 2;
      if (stage.getMaxWidth() < width) {
        width = stage.getMaxWidth();
      }

      stage.setWidth(width);

      double height = visualBounds.getHeight();
      if (stage.getMaxHeight() < height) {
        height = stage.getMaxHeight();
      }

      stage.setHeight(height);
      undecorator.setShadow(false);
    }
    // mac menu bar
    else if (mouseEvent.getScreenY() <= visualBounds.getMinY()) {
      undecorator.switchMaximize();
    }
  }


  public boolean isRightEdge(double x, double y, Bounds boundsInParent) {
    if (x < boundsInParent.getWidth() && x > boundsInParent.getWidth() - RESIZE_PADDING - SHADOW_WIDTH) {
      return true;
    }
    return false;
  }


  public boolean isTopEdge(double x, double y, Bounds boundsInParent) {
    if (y >= 0 && y < RESIZE_PADDING + SHADOW_WIDTH) {
      return true;
    }
    return false;
  }


  public boolean isBottomEdge(double x, double y, Bounds boundsInParent) {
    if (y < boundsInParent.getHeight() && y > boundsInParent.getHeight() - RESIZE_PADDING - SHADOW_WIDTH) {
      return true;
    }
    return false;
  }


  public boolean isLeftEdge(double x, double y, Bounds boundsInParent) {
    if (x >= 0 && x < RESIZE_PADDING + SHADOW_WIDTH) {
      return true;
    }
    return false;
  }


  public void setCursor(Node n, Cursor c) {
    n.setCursor(c);
  }
}
