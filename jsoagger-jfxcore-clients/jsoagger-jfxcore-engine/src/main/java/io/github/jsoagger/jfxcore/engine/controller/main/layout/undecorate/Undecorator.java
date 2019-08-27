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




import java.net.URL;
import java.util.Properties;

import org.kordamp.ikonli.javafx.FontIcon;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * This class, with the UndecoratorController, is the central class for the decoration of
 * Transparent Stages. The Stage Undecorator TODO: Themes, manage Quit (main stage)
 *
 * Bugs (Mac only?): Accelerators + Fullscreen crashes JVM KeyCombination does not respect
 * keyboard's locale. Multi screen: On second screen JFX returns wrong value for MinY (300)
 */
public class Undecorator extends StackPane {

  //public static final Logger LOGGER = Logger.getLogger("Undecorator");
  public static final String STAGE_FXML_LOCATION = "stageTransparentDecoration.fxml";

  public static int SHADOW_WIDTH = 15;
  public static int SAVED_SHADOW_WIDTH = 15;
  public static int RESIZE_PADDING = 7;
  public static int FEEDBACK_STROKE = 4;
  public static double ROUNDED_DELTA = 5;

  @FXML
  private Button menu;

  @FXML
  private Button close;
  @FXML
  private Button maximize;
  @FXML
  private Button restore;
  @FXML
  private Button iconify;

  @FXML
  private Button resize;
  @FXML
  private Button fullscreen;
  @FXML
  private Label title;
  @FXML
  private Pane decorationRoot;
  @FXML
  private ContextMenu contextMenu;

  private StageStyle stageStyle;
  private MenuItem maximizeMenuItem;
  private CheckMenuItem fullScreenMenuItem;
  private Region clientArea;
  private Pane stageDecoration;
  private Rectangle shadowRectangle;
  private Pane glassPane;
  private Rectangle dockFeedback;
  private FadeTransition dockFadeTransition;
  private Stage dockFeedbackPopup;
  private ParallelTransition parallelTransition;
  private Effect dsFocused;
  private Effect dsNotFocused;
  private UndecoratorController undecoratorController;
  private Stage stage;
  private Rectangle backgroundRect;
  private SimpleBooleanProperty maximizeProperty;
  private SimpleBooleanProperty closedProperty = new SimpleBooleanProperty();
  private SimpleBooleanProperty fullscreenProperty;
  private String shadowBackgroundStyleClass = "decoration-shadow";
  private String decorationBackgroundStyle = "decoration-background";
  private TranslateTransition fullscreenButtonTransition;
  final Rectangle internal = new Rectangle();
  final Rectangle external = new Rectangle();

  // needs platformProperties
  protected Properties platformProperties;


  /**
   * Constructor
   *
   * @param stage
   * @param clientArea
   * @param stageDecorationFxml
   * @param st
   */
  public Undecorator() {
    setMinHeight(500);
    setMinWidth(600);
  }


  /**
   * @param stage
   * @param clientArea
   */
  public void buildFrom(Stage stage, Region clientArea) {
    this.stage = stage;
    this.clientArea = clientArea;
    this.stageStyle = stage.getStyle();

    URL stageDecorationFxmlAsURL = getClass().getResource(STAGE_FXML_LOCATION);
    stageDecoration = (Pane) NodeHelper.loadFXML(stageDecorationFxmlAsURL, this);

    // maximize and restore button
    // mutually exclusif, buttons
    maximizeProperty = new SimpleBooleanProperty(false);
    maximize.managedProperty().bind(maximize.visibleProperty());
    maximize.visibleProperty().bind(Bindings.not(maximizeProperty));

    restore.managedProperty().bind(restore.visibleProperty());
    restore.visibleProperty().bind(maximizeProperty);

    // iconify
    iconify.managedProperty().bind(iconify.visibleProperty());

    fullscreenProperty = new SimpleBooleanProperty(false);
    fullscreenProperty.addListener((ChangeListener<Boolean>) (ov, t, t1) -> getController().setFullScreen(!stage.isFullScreen()));

    undecoratorController = new UndecoratorController(this);
    undecoratorController.setAsStageDraggable(stage, clientArea);

    // Focus drop shadows: radius, spread, offsets
    dsFocused = new DropShadow(BlurType.THREE_PASS_BOX, Color.GRAY, SHADOW_WIDTH, 0.1, 0, 0);
    dsNotFocused = new DropShadow(BlurType.THREE_PASS_BOX, Color.DARKGREY, SHADOW_WIDTH, 0, 0, 0);

    shadowRectangle = new Rectangle();
    shadowRectangle.layoutBoundsProperty().addListener((ChangeListener<Bounds>) (observable, oldBounds, newBounds) -> {
      if (SHADOW_WIDTH != 0) {
        shadowRectangle.setVisible(true);
        setShadowClip(newBounds);
      } else {
        shadowRectangle.setVisible(false);
      }
    });

    initDecoration();

    // If not resizable (quick fix)
    if (fullscreen != null) {
      fullscreen.setVisible(stage.isResizable());
    }

    resize.setVisible(stage.isResizable());

    // Glass Pane
    glassPane = new Pane();
    glassPane.setMouseTransparent(true);
    buildDockFeedbackStage();

    title.getStyleClass().add("undecorator-label-titlebar");
    shadowRectangle.getStyleClass().add(shadowBackgroundStyleClass);
    // resizeRect.getStyleClass().add(resizeStyleClass);
    // Do not intercept mouse events on stage's shadow
    shadowRectangle.setMouseTransparent(true);

    // Is it possible to apply an effect without affecting decendent?
    super.setStyle("-fx-background-color:transparent;");
    // Or this:
    // super.setStyle("-fx-background-color:transparent;-fx-border-color:white;-fx-border-radius:30;-fx-border-width:1;-fx-border-insets:"+SHADOW_WIDTH+";");
    // super.setEffect(dsFocused);
    // super.getChildren().addAll(clientArea,stageDecoration, glassPane);

    backgroundRect = new Rectangle();
    backgroundRect.getStyleClass().add(decorationBackgroundStyle);
    backgroundRect.setMouseTransparent(true);

    // Add all layers
    super.getChildren().addAll(shadowRectangle, backgroundRect, clientArea, stageDecoration, glassPane);

    // Focused stage
    stage.focusedProperty().addListener((ChangeListener<Boolean>) (ov, t, t1) -> setShadowFocused(t1.booleanValue()));

    // Fullscreen
    // do not support this button for the moment
    if (fullscreen != null) {
      fullscreen.setVisible(false);
      // fullscreen.setManaged(false);

      fullscreen.setOnMouseEntered(t -> {
        if (stage.isFullScreen()) {
          fullscreen.setOpacity(1);
        }
      });

      fullscreen.setOnMouseExited(t -> {
        if (stage.isFullScreen()) {
          fullscreen.setOpacity(0.4);
        }
      });

      stage.fullScreenProperty().addListener((ChangeListener<Boolean>) (ov, t, fullscreenState) -> {
        setShadow(!fullscreenState.booleanValue());
        fullScreenMenuItem.setSelected(fullscreenState.booleanValue());
        maximize.setVisible(!fullscreenState.booleanValue());
        iconify.setVisible(!fullscreenState.booleanValue());
        resize.setVisible(!fullscreenState.booleanValue());
        if (fullscreenState.booleanValue()) {
          // String and icon
          fullscreen.getStyleClass().add("decoration-button-unfullscreen");
          fullscreen.setTooltip(new Tooltip("Restore"));

          undecoratorController.saveFullScreenBounds();
          if (fullscreenButtonTransition != null) {
            fullscreenButtonTransition.stop();
          }
          // Animate the fullscreen button
          fullscreenButtonTransition = new TranslateTransition();
          fullscreenButtonTransition.setDuration(Duration.millis(3000));
          fullscreenButtonTransition.setToX(66);
          fullscreenButtonTransition.setNode(fullscreen);
          fullscreenButtonTransition.setOnFinished(t1 -> fullscreenButtonTransition = null);
          fullscreenButtonTransition.play();
          fullscreen.setOpacity(0.2);
        } else {
          // String and icon
          fullscreen.getStyleClass().remove("decoration-button-unfullscreen");
          fullscreen.setTooltip(new Tooltip("FullScreen"));

          undecoratorController.restoreFullScreenSavedBounds(stage);
          fullscreen.setOpacity(1);
          if (fullscreenButtonTransition != null) {
            fullscreenButtonTransition.stop();
          }
          // Animate the change
          fullscreenButtonTransition = new TranslateTransition();
          fullscreenButtonTransition.setDuration(Duration.millis(1000));
          fullscreenButtonTransition.setToX(0);
          fullscreenButtonTransition.setNode(fullscreen);
          fullscreenButtonTransition.setOnFinished(t1 -> fullscreenButtonTransition = null);
          fullscreenButtonTransition.play();
        }
      });
    }

    computeAllSizes();
  }


  /**
   * Compute the needed clip for stage's shadow border
   *
   * @param newBounds
   * @param shadowVisible
   */
  void setShadowClip(Bounds newBounds) {
    external.relocate(newBounds.getMinX() - SHADOW_WIDTH, newBounds.getMinY() - SHADOW_WIDTH);
    internal.setX(SHADOW_WIDTH);
    internal.setY(SHADOW_WIDTH);
    internal.setWidth(newBounds.getWidth());
    internal.setHeight(newBounds.getHeight());
    internal.setArcWidth(shadowRectangle.getArcWidth()); // shadowRectangle
    // CSS cannot be
    // applied on
    // this
    internal.setArcHeight(shadowRectangle.getArcHeight());

    external.setWidth(newBounds.getWidth() + SHADOW_WIDTH * 2);
    external.setHeight(newBounds.getHeight() + SHADOW_WIDTH * 2);
    Shape clip = Shape.subtract(external, internal);
    shadowRectangle.setClip(clip);

  }


  /**
   * Install default accelerators
   *
   * @param scene
   */
  public void installAccelerators(Scene scene) {
    // Accelerators
    if (stage.isResizable()) {
      scene.getAccelerators().put(new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN), () -> switchFullscreen());
    }
    scene.getAccelerators().put(new KeyCodeCombination(KeyCode.M, KeyCombination.SHORTCUT_DOWN), () -> iconify());
    scene.getAccelerators().put(new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN), () -> switchClose());
  }


  /**
   * Init the minimum/pref/max size in order to be reflected in the primary stage
   */
  private void computeAllSizes() {
    double minWidth = minWidth(getHeight());
    setMinWidth(minWidth);
    double minHeight = minHeight(getWidth());
    setMinHeight(minHeight);

    double prefHeight = prefHeight(getWidth());
    setPrefHeight(prefHeight);
    double prefWidth = prefWidth(getHeight());
    setPrefWidth(prefWidth);

    double maxWidth = maxWidth(getHeight());
    if (maxWidth > 0) {
      setMaxWidth(maxWidth);
    }
    double maxHeight = maxHeight(getWidth());
    if (maxHeight > 0) {
      setMaxHeight(maxHeight);
    }
  }


  @Override
  protected double computePrefWidth(double d) {
    return clientArea.getPrefWidth() + SHADOW_WIDTH * 2 + RESIZE_PADDING * 2;
  }


  @Override
  protected double computePrefHeight(double d) {
    return clientArea.getPrefHeight() + SHADOW_WIDTH * 2 + RESIZE_PADDING * 2;
  }


  @Override
  protected double computeMaxHeight(double d) {
    return clientArea.getMaxHeight() + SHADOW_WIDTH * 2 + RESIZE_PADDING * 2;
  }


  @Override
  protected double computeMinHeight(double d) {
    double d2 = super.computeMinHeight(d);
    d2 += SHADOW_WIDTH * 2 + RESIZE_PADDING * 2;
    return d2;
  }


  @Override
  protected double computeMaxWidth(double d) {
    return clientArea.getMaxWidth() + SHADOW_WIDTH * 2 + RESIZE_PADDING * 2;
  }


  @Override
  protected double computeMinWidth(double d) {
    double d2 = super.computeMinWidth(d);
    d2 += SHADOW_WIDTH * 2 + RESIZE_PADDING * 2;
    return d2;
  }


  /**
   * Activate fade in transition on showing event
   */
  public void setFadeInTransition() {
    super.setOpacity(0);
    stage.showingProperty().addListener((ChangeListener<Boolean>) (ov, t, t1) -> {
      if (t1.booleanValue()) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), Undecorator.this);
        fadeTransition.setToValue(1);
        fadeTransition.play();
      }
    });
  }


  /**
   * Launch the fade out transition. Must be invoked when the application/window is supposed to be
   * closed
   */
  public void setFadeOutTransition() {
    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), Undecorator.this);
    fadeTransition.setToValue(0);
    fadeTransition.play();
    fadeTransition.setOnFinished(t -> {
      stage.hide();
      if (dockFeedbackPopup != null && dockFeedbackPopup.isShowing()) {
        dockFeedbackPopup.hide();
      }
    });
  }


  public void removeDefaultBackgroundStyleClass() {
    shadowRectangle.getStyleClass().remove(shadowBackgroundStyleClass);
  }


  public Rectangle getBackgroundNode() {
    return shadowRectangle;
  }


  /**
   * Background opacity
   *
   * @param opacity
   */
  public void setBackgroundOpacity(double opacity) {
    shadowRectangle.setOpacity(opacity);
  }


  /**
   * Manage buttons and menu items
   */
  public void initDecoration() {
    MenuItem minimizeMenuItem = null;
    // Menu
    // final ContextMenu contextMenu = new ContextMenu();
    // contextMenu.setAutoHide(true);
    if (iconify != null) { // Utility Stage
      minimizeMenuItem = new MenuItem("Minimize");
      minimizeMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.M, KeyCombination.SHORTCUT_DOWN));
      minimizeMenuItem.setOnAction(e -> iconify());
      // contextMenu.getItems().add(minimizeMenuItem);
    }
    if (maximize != null && stage.isResizable()) { // Utility Stage type
      maximizeMenuItem = new MenuItem("Maximize");
      maximizeMenuItem.setOnAction(e -> {
        switchMaximize();
        // contextMenu.hide(); // Stay stuck on screen
      });
      // contextMenu.getItems().addAll(maximizeMenuItem, new
      // SeparatorMenuItem());
    }

    // Fullscreen
    if (stageStyle != StageStyle.UTILITY && stage.isResizable()) {
      fullScreenMenuItem = new CheckMenuItem("FullScreen");
      fullScreenMenuItem.setOnAction(e -> switchFullscreen());
      fullScreenMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN, KeyCombination.SHORTCUT_DOWN));

      // contextMenu.getItems().addAll(fullScreenMenuItem, new
      // SeparatorMenuItem());
    }

    // Close
    MenuItem closeMenuItem = new MenuItem("Close");
    closeMenuItem.setOnAction(e -> switchClose());
    closeMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN));

    // contextMenu.getItems().add(closeMenuItem);

    /*
     * menu.setOnMousePressed(t -> { if (contextMenu.isShowing()) { contextMenu.hide(); } else {
     * contextMenu.show(menu, Side.BOTTOM, 0, 0); } });
     */

    // Close button
    close.setTooltip(new Tooltip("Close"));
    close.setOnAction(t -> switchClose());
    FontIcon closeicon = new FontIcon("mdi-close:18");
    close.setGraphic(closeicon);

    // Maximize button
    // If changed via contextual menu
    if (maximize != null) {
      maximize.setTooltip(new Tooltip("Maximize"));
      maximize.setOnAction(t -> switchMaximize());
      FontIcon maximizeicon = new FontIcon("mdi-window-maximize:16");
      maximize.setGraphic(maximizeicon);
    }

    // restore button
    if (restore != null) {
      restore.setTooltip(new Tooltip("Restore"));
      restore.setOnAction(t -> switchRestore());
      FontIcon maximizeicon = new FontIcon("mdi-window-restore:16");
      restore.setGraphic(maximizeicon);
    }

    if (fullscreen != null) {
      fullscreen.setVisible(false);
      fullscreen.setTooltip(new Tooltip("FullScreen"));
      fullscreen.setOnAction(t -> switchFullscreen());
      FontIcon fullscreenicon = new FontIcon("mdi-fullscreen:16");
      fullscreen.setGraphic(fullscreenicon);
    }

    // iconify button
    if (iconify != null) {
      iconify.setTooltip(new Tooltip("Minimize"));
      iconify.setOnAction(t -> iconify());
      FontIcon minimizeicon = new FontIcon("mdi-window-minimize:16");
      iconify.setGraphic(minimizeicon);
    }

    maximizeProperty.addListener((ChangeListener<Boolean>) (ov, t, t1) -> {
      // resize.setVisible(!t1);
    });

    // Transfer stage title to undecorator tiltle label
    title.setText(this.stage.getTitle());

    // drag
    undecoratorController.setStageResizableWith(stage, decorationRoot, RESIZE_PADDING, SHADOW_WIDTH);
  }


  public void switchFullscreen() {
    // Invoke runLater even if it's on EDT: Crash apps on Mac
    Platform.runLater(() -> undecoratorController.setFullScreen(!stage.isFullScreen()));
  }


  public void iconify() {
    maximizeProperty.set(false);
    getController().iconify();
  }


  public void switchRestore() {
    maximizeProperty.set(false);
    getController().restore();
  }


  public void switchMaximize() {
    if (!maximizeProperty.get()) {
      maximizeProperty.set(true);
      getController().maximize();
    }
  }


  public SimpleBooleanProperty maximizeProperty() {
    return maximizeProperty;
  }


  public void switchClose() {
    closedProperty.set(!closedProperty.get());
    getController().close();
  }


  /**
   * Bridge to the controller to enable the specified node to drag the stage
   *
   * @param stage
   * @param node
   */
  public void setAsStageDraggable(Stage stage, Node node) {
    undecoratorController.setAsStageDraggable(stage, node);
  }


  /**
   * Switch the visibility of the window's drop shadow
   */
  protected void setShadow(boolean shadow) {
    // Already removed?
    if (!shadow && shadowRectangle.getEffect() == null) {
      return;
    }
    // From fullscreen to maximize case
    if (shadow && maximizeProperty.get()) {
      return;
    }
    if (!shadow) {
      shadowRectangle.setEffect(null);
      SAVED_SHADOW_WIDTH = SHADOW_WIDTH;
      SHADOW_WIDTH = 0;
    } else {
      shadowRectangle.setEffect(dsFocused);
      SHADOW_WIDTH = SAVED_SHADOW_WIDTH;
    }
  }


  /**
   * Set on/off the stage shadow effect
   *
   * @param b
   */
  protected void setShadowFocused(boolean b) {
    // Do not change anything while maximized (in case of dialog closing for
    // instance)
    if (stage.isFullScreen()) {
      return;
    }
    if (maximizeProperty.get()) {
      return;
    }
    if (b) {
      shadowRectangle.setEffect(dsFocused);
    } else {
      shadowRectangle.setEffect(dsNotFocused);
    }
  }


  /**
   * Set the layout of different layers of the stage
   */
  @Override
  public void layoutChildren() {
    Bounds b = super.getLayoutBounds();
    double w = b.getWidth();
    double h = b.getHeight();
    ObservableList<Node> list = super.getChildren();
    // ROUNDED_DELTA=shadowRectangle.getArcWidth()/4;
    ROUNDED_DELTA = 0;
    for (Node node : list) {
      if (node == shadowRectangle) {
        shadowRectangle.setWidth(w - SHADOW_WIDTH * 2);
        shadowRectangle.setHeight(h - SHADOW_WIDTH * 2);
        shadowRectangle.setX(SHADOW_WIDTH);
        shadowRectangle.setY(SHADOW_WIDTH);
      } else if (node == backgroundRect) {
        backgroundRect.setWidth(w - SHADOW_WIDTH * 2);
        backgroundRect.setHeight(h - SHADOW_WIDTH * 2);
        backgroundRect.setX(SHADOW_WIDTH);
        backgroundRect.setY(SHADOW_WIDTH);
      } else if (node == stageDecoration) {
        stageDecoration.resize(w - SHADOW_WIDTH * 2 - ROUNDED_DELTA * 2, h - SHADOW_WIDTH * 2 - ROUNDED_DELTA * 2);
        stageDecoration.setLayoutX(SHADOW_WIDTH + ROUNDED_DELTA);
        stageDecoration.setLayoutY(SHADOW_WIDTH + ROUNDED_DELTA);
      } // else if (node == resizeRect) {
      // resizeRect.setWidth(w - SHADOW_WIDTH * 2);
      // resizeRect.setHeight(h - SHADOW_WIDTH * 2);
      // resizeRect.setLayoutX(SHADOW_WIDTH);
      // resizeRect.setLayoutY(SHADOW_WIDTH);
      // }
      else {
        node.resize(w - SHADOW_WIDTH * 2 - ROUNDED_DELTA * 2, h - SHADOW_WIDTH * 2 - ROUNDED_DELTA * 2);
        node.setLayoutX(SHADOW_WIDTH + ROUNDED_DELTA);
        node.setLayoutY(SHADOW_WIDTH + ROUNDED_DELTA);
        // node.resize(w - SHADOW_WIDTH * 2 - RESIZE_PADDING * 2, h -
        // SHADOW_WIDTH * 2 - RESIZE_PADDING * 2);
        // node.setLayoutX(SHADOW_WIDTH + RESIZE_PADDING);
        // node.setLayoutY(SHADOW_WIDTH + RESIZE_PADDING);
      }
    }
  }


  public int getShadowBorderSize() {
    return SHADOW_WIDTH * 2 + RESIZE_PADDING * 2;
  }


  public UndecoratorController getController() {
    return undecoratorController;
  }


  public Stage getStage() {
    return stage;
  }


  protected Pane getGlassPane() {
    return glassPane;
  }


  public void addGlassPane(Node node) {
    glassPane.getChildren().add(node);
  }


  public void removeGlassPane(Node node) {
    glassPane.getChildren().remove(node);
  }


  /**
   * Returns the decoration (buttons...)
   *
   * @return
   */
  public Pane getStageDecorationNode() {
    return stageDecoration;
  }


  /**
   * Prepare Stage for dock feedback display
   */
  void buildDockFeedbackStage() {
    dockFeedbackPopup = new Stage(StageStyle.TRANSPARENT);
    dockFeedback = new Rectangle(0, 0, 100, 100);
    dockFeedback.setArcHeight(10);
    dockFeedback.setArcWidth(10);
    dockFeedback.setFill(Color.TRANSPARENT);
    dockFeedback.setStroke(Color.BLACK);
    dockFeedback.setStrokeWidth(2);
    dockFeedback.setCache(true);
    dockFeedback.setCacheHint(CacheHint.SPEED);
    dockFeedback.setEffect(new DropShadow(BlurType.TWO_PASS_BOX, Color.BLACK, 10, 0.2, 3, 3));
    dockFeedback.setMouseTransparent(true);
    BorderPane borderpane = new BorderPane();
    borderpane.setStyle("-fx-background-color:transparent"); // J8
    borderpane.setCenter(dockFeedback);
    Scene scene = new Scene(borderpane);
    scene.setFill(Color.TRANSPARENT);
    dockFeedbackPopup.setScene(scene);
    dockFeedbackPopup.sizeToScene();
  }


  /**
   * Activate dock feedback on screen's bounds
   *
   * @param x
   * @param y
   */
  public void setDockFeedbackVisible(double x, double y, double width, double height) {
    dockFeedbackPopup.setX(x);
    dockFeedbackPopup.setY(y);

    dockFeedback.setX(SHADOW_WIDTH);
    dockFeedback.setY(SHADOW_WIDTH);
    dockFeedback.setHeight(height - SHADOW_WIDTH * 2);
    dockFeedback.setWidth(width - SHADOW_WIDTH * 2);

    dockFeedbackPopup.setWidth(width);
    dockFeedbackPopup.setHeight(height);

    dockFeedback.setOpacity(1);
    dockFeedbackPopup.show();

    dockFadeTransition = new FadeTransition();
    dockFadeTransition.setDuration(Duration.millis(200));
    dockFadeTransition.setNode(dockFeedback);
    dockFadeTransition.setFromValue(0);
    dockFadeTransition.setToValue(1);
    dockFadeTransition.setAutoReverse(true);
    dockFadeTransition.setCycleCount(3);

    dockFadeTransition.play();

  }


  public void setDockFeedbackInvisible() {
    if (dockFeedbackPopup.isShowing()) {
      dockFeedbackPopup.hide();
      if (dockFadeTransition != null) {
        dockFadeTransition.stop();
      }
    }
  }


  public SimpleBooleanProperty fullscreenProperty() {
    return fullscreenProperty;
  }
}
