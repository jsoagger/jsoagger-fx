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

package io.github.jsoagger.jfxcore.engine.controller.main;




import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.InjectableComponent;
import io.github.jsoagger.jfxcore.api.ResourceUtils;
import io.github.jsoagger.jfxcore.api.security.IRootContext;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.dialog.HideDialogEvent;
import io.github.jsoagger.jfxcore.engine.components.dialog.ShowDialogEvent;
import io.github.jsoagger.jfxcore.engine.components.security.RootContext;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.layout.ViewStructure;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.RootStructureContentController;
import io.github.jsoagger.jfxcore.engine.controller.utils.RootStructureContentUtils;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;
import io.github.jsoagger.jfxcore.engine.events.CloseMenuEvent;
import io.github.jsoagger.jfxcore.engine.events.CoreEvent;
import io.github.jsoagger.jfxcore.engine.events.DisplayMenuEvent;
import io.github.jsoagger.jfxcore.engine.events.GenericEvent;
import io.github.jsoagger.jfxcore.engine.events.HeaderSearchFocusedEvent;
import io.github.jsoagger.jfxcore.engine.events.HeaderSearchLostFocusedEvent;
import io.github.jsoagger.jfxcore.engine.events.MenuPos;
import io.github.jsoagger.jfxcore.engine.events.PinSecondaryMenuRightEvent;
import io.github.jsoagger.jfxcore.engine.events.PopRootViewEvent;
import io.github.jsoagger.jfxcore.engine.events.UnpinSecondaryMenuRightEvent;
import io.github.jsoagger.jfxcore.engine.interpolator.EasingInterpolator;
import io.github.jsoagger.jfxcore.engine.interpolator.EasingMode;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class RootStructureController extends AbstractViewController {

  private static final String PRIMARY_MENU_VIEW = "primaryMenuView";
  private static final String HEADER_VIEW = "headerView";

  protected SimpleObjectProperty<IRootContext> rootContext = new SimpleObjectProperty<>();
  protected RootStructureContentController rootStructureContentController;
  protected List<StandardViewController> children = Collections.synchronizedList(new ArrayList<>());
  protected Node materialNode;
  private final SimpleDoubleProperty rootMenuWidth = new SimpleDoubleProperty();

  /** Sometimes whe need to construct view relative to a data */
  SimpleObjectProperty<OperationData> relativeTo = new SimpleObjectProperty<>();


  public SimpleObjectProperty<OperationData> relativeToProperty() {
    return relativeTo;
  }

  /*-----------------------------------------------------------------------------
  | FXML CONTENT OF THE STRUCTURE
   *=============================================================================*/
  @FXML
  protected Pane secondaryRSWrapperScrollpane;
  @FXML
  protected Pane secondaryRSActionsWrapper;
  @FXML
  protected Pane secondaryRootStructureWrapper;
  @FXML
  protected Pane headerStack;
  @FXML
  protected Pane contentStack;
  @FXML
  protected Pane rootStructure;
  @FXML
  protected Pane rootStructurePrimaryPane;
  @FXML
  protected Pane rootStructureTernaryPane;
  @FXML
  protected Pane pushedContentWrapper;
  @FXML
  protected ScrollPane pushedContentScrollpane;
  @FXML
  protected Pane rootStructureLeftMenuPane;
  @FXML
  protected Pane rootStructureWrapper;
  @FXML
  protected ButtonBase closePushedContentButton;

  private final StackPane notificationsStack = new StackPane();

  /*-----------------------------------------------------------------------------
  | STATIC
   *=============================================================================*/
  /** Default Anchors configuration file */
  protected static Properties APPLICATION_PROP = null;
  static {
    try (InputStream is = ResourceUtils.getStream(AbstractViewController.class, "/anchors.properties")) {
      APPLICATION_PROP = new Properties();
      APPLICATION_PROP.load(is);
    } catch (final Exception e) {
    }
  }


  /*-----------------------------------------------------------------------------
  | CONSTRUCTOR
   *=============================================================================*/

  /**
   * Constructor
   */
  public RootStructureController() {
    super();

    registerListener(CoreEvent.HeaderSearchFocusedEvent);
    registerListener(CoreEvent.HeaderSearchLostFocusedEvent);
    registerListener(CoreEvent.ShowDialogEvent);
    registerListener(CoreEvent.HideDialogEvent);
    registerListener(CoreEvent.DisplayMenuEvent);
    registerListener(CoreEvent.CloseMenuEvent);
  }


  /*-----------------------------------------------------------------------------
  | BUILDING THE VIEW
   *=============================================================================*/
  /**
   * @{inheritedDoc}
   */
  @Override
  protected void process() {
    super.process();
    notificationsStack.setId("root-structure-notifications-stack");
    notificationsStack.setStyle("-fx-background-color:rgb(0,0,10,0.10);");
    NodeHelper.setHVGrow(notificationsStack);

    pushedContentWrapper.managedProperty().bind(pushedContentWrapper.visibleProperty());
    closePushedContentButton.setOnAction(e-> popContent());
    IconUtils.setFontIcon("gmi-close:22", closePushedContentButton);
    closePushedContentButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    // rootStructure.visibleProperty().bind(Bindings.not(pushedContentWrapper.visibleProperty()));

    //final CompletableFuture buildHeader = CompletableFuture.runAsync(() -> buildHeader(), DesktopApplicationRunner.pool);
    //final CompletableFuture buildContent = CompletableFuture.runAsync(() -> buildContent(), DesktopApplicationRunner.pool);
    //CompletableFuture.allOf(buildHeader, buildContent).join();
    //CompletableFuture.runAsync(() -> buildPrimaryMenu(), DesktopApplicationRunner.pool);
    buildHeader();
    buildContent();
    buildPrimaryMenu();

    secondaryRootStructureWrapper.managedProperty().bind(secondaryRootStructureWrapper.visibleProperty());
    secondaryRootStructureWrapper.setVisible(false);
    if(AbstractApplicationRunner.isSimulMobile() || AbstractApplicationRunner.isMobile()) {
    }

    if (!headerLess) {
      // AnchorPane.setTopAnchor(rootStructureWrapper, 88.);
    }

    initAnimations();
  }

  /*-----------------------------------------------------------------------------
  | THE HEADER
   *=============================================================================*/
  private boolean headerLess = false;


  private void buildHeader() {
    try {
      final VLViewComponentXML rootComp = getRootComponent();
      if (rootComp != null) {
        final String headerId = rootComp.getPropertyValue(HEADER_VIEW);
        if (StringUtils.isNotBlank(headerId)) {
          final StandardViewController header = StandardViewUtils.forId(this, headerId);
          Platform.runLater(() -> {
            headerStack.getChildren().add(header.processedView());
            headerStack.managedProperty().bind(headerStack.visibleProperty());
            headerStack.setVisible(headerStack.getChildren().size() > 0);
          });
        } else {
          headerLess = true;
          headerStack.setVisible(false);
          headerStack.setManaged(false);
          AnchorPane.setTopAnchor(rootStructureWrapper, 0.);
        }

        NodeHelper.styleClassAddAll(headerStack, rootComp, "headerStyleClass");
      }
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }


  public void buildPrimaryMenu() {
    final VLViewComponentXML rootComp = getRootComponent();
    if (rootComp != null) {
      final String primaryMenuId = rootComp.getPropertyValue(PRIMARY_MENU_VIEW);
      if (StringUtils.isNotBlank(primaryMenuId)) {
        final AbstractViewController controller = StandardViewUtils.forId(this, primaryMenuId);
        final Node primaryMenu = controller.processedView();
        primaryMenu.setCache(true);
        primaryMenu.setCacheHint(CacheHint.SPEED);

        Platform.runLater(() -> {
          AnchorPane.setTopAnchor(primaryMenu, 0.);
          AnchorPane.setBottomAnchor(primaryMenu, 0.);
          AnchorPane.setRightAnchor(primaryMenu, 0.);
          AnchorPane.setLeftAnchor(primaryMenu, 0.);
          rootStructurePrimaryPane.getChildren().add(primaryMenu);
        });
      }

      // add key listener to scene
      final EventHandler<javafx.scene.input.KeyEvent> kr = event -> {
        switch (event.getCode()) {
          case P:
            if (event.isControlDown()) {
              displayPrimary(null);
            }
            break;
          case ESCAPE:
            if (primaryMenuIsShowing) {
              event.consume();
              hidePrimaryMenu();
            }
            break;
          default:
            break;
        }
      };

      // CTRL + L: SHOW
      // ESC: HIDE
      if(ViewStructure.primaryStage() != null && ViewStructure.primaryStage().getScene() != null) {
        ViewStructure.primaryStage().getScene().addEventFilter(KeyEvent.KEY_RELEASED, kr);
      }
    }
  }


  /*-----------------------------------------------------------------------------
  | THE CONTENT
   *=============================================================================*/
  private void buildContent() {
    try {
      final VLViewComponentXML rootComp = getRootComponent();
      if (rootComp != null) {
        final String contentRootStructure = rootComp.getPropertyValue("contentRootStructure");
        if (StringUtils.isNotBlank(contentRootStructure)) {
          rootStructureContentController = RootStructureContentUtils.forId(contentRootStructure, this);
          contentStack.getChildren().add(rootStructureContentController.processedView());
        }
      }
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }

  public RootStructureContentController getRootStructureContent() {
	  return rootStructureContentController;
  }

  /*-----------------------------------------------------------------------------
  | EVENT MANAGEMENT
   *=============================================================================*/
  /**
   * @{inheritedDoc}
   */
  @Override
  public <T extends GenericEvent> void handle(T event) {
    super.handle(event);
    handleEvent(event);
  }


  /**
   * Dispatch this event to all known children which {@link #isAwareOf(GenericEvent)} returns true.
   *
   * @param e
   */
  @Override
  public <T extends GenericEvent> void dispatchEvent(T event) {
    synchronized (children) {
      final List<StandardViewController> aware = new ArrayList<>();
      for (final StandardViewController c : children) {
        if (c.isAwareOf(event)) {
          aware.add(c);
        }
      }

      // root structure will first handle the event
      if (isAwareOf(event) && !aware.contains(this)) {
        handleEvent(event);
      }

      // and then other child
      for(StandardViewController c : aware) {
        c.handle(event);
      }
    }

    // and after dispatch it to rootStructureContentController
    if (rootStructureContentController != null) {
      rootStructureContentController.dispatchEvent(event);
    }
  }


  /**
   * @param event
   */
  private void handleEvent(GenericEvent event) {
    if (event.isA(CoreEvent.DisplayMenuEvent)) {
      handleEvent((DisplayMenuEvent) event);
    } else if (event.isA(CoreEvent.ShowDialogEvent)) {
      handleEvent((ShowDialogEvent) event);
    } else if (event.isA(CoreEvent.HideDialogEvent)) {
      handleEvent((HideDialogEvent) event);
    } else if (event.isA(CoreEvent.CloseMenuEvent)) {
      handleEvent((CloseMenuEvent) event);
    } else if (event.isA(CoreEvent.HeaderSearchFocusedEvent)) {
      handleEvent((HeaderSearchFocusedEvent)event);
    } else if (event.isA(CoreEvent.HeaderSearchLostFocusedEvent)) {
      handleEvent((HeaderSearchLostFocusedEvent) event);
    }
  }


  public void clearMaterialNode() {
    Platform.runLater(() -> {
      if (materialNode != null) {
        ((Pane) processedView()).getChildren().remove(materialNode);
      }
    });
  }


  public void setMaterialNode(Node materialNode) {
    Platform.runLater(() -> {
      if (this.materialNode != null) {
        ((Pane) processedView()).getChildren().remove(this.materialNode);
      }

      ((Pane) processedView()).getChildren().add(materialNode);
      this.materialNode = materialNode;

      AnchorPane.setTopAnchor(materialNode, 350.);
      AnchorPane.setRightAnchor(materialNode, 40.);
      initMaterialNodeDrag();
    });
  }


  private void handleEvent(ShowDialogEvent e) {
    final Stage stage = e.getStage();
    makeCentralPaneDarker();
    if(stage != null) stage.show();
  }


  private void handleEvent(HideDialogEvent e) {
    final Stage stage = e.getStage();
    makeCentralPaneLighter();
    if(stage != null)
    	Platform.runLater(() -> stage.hide());
  }


  /**
   * Removes and clean children
   */
  public void clear() {
    for(StandardViewController s: children) {
      s.destroy();
    }
    children.clear();
  }


  public IRootContext getRootContext() {
    return rootContext.get();
  }


  public void setRootContext(RootContext context) {
    rootContext.set(context);
  }


  /**
   * @param controller
   */
  public boolean addChild(StandardViewController controller) {
    if (!children.contains(controller)) {
      children.add(controller);
      return true;
    }

    return false;
  }


  /**
   * @param controller
   */
  public void removeChild(StandardViewController controller) {
    children.remove(controller);
  }


  public void setLeftMenuContent(Node node) {
    if (node != null) {
      rootStructureLeftMenuPane.getChildren().clear();
      rootStructureLeftMenuPane.getChildren().add(node);
    } else {
      rootStructureLeftMenuPane.setVisible(false);
      rootStructureLeftMenuPane.setManaged(false);
      // AnchorPane.setLeftAnchor(rootStructure, 0.);
    }
  }


  /**
   * Set anchors of root structure. Anchors are defined in file application.properties or in Root
   * structure configuration, inside component with identifier 'RootStructureComponentAnchors'.
   * <p>
   * RootStructureComponentAnchors has priority over property file.
   */
  public void setAnchors() {
    String rootStructureWrapperAnchors = null;
    String rootStructureLeftMenuPanAnchors = null;
    String secondaryRootStructureWrapperAnchors = null;
    String rootStructureTernaryPaneAnchors = null;
    String rootStructurePrimaryPaneAnchors = null;
    String pushedContentWrapperAnchors = null;

    final Optional<VLViewComponentXML> anchorsConfig = getRootComponent().getComponentById("RootStructureComponentAnchors");
    if (anchorsConfig != null && anchorsConfig.isPresent()) {
      final VLViewComponentXML anchorsConf = anchorsConfig.get();
      rootStructureWrapperAnchors = anchorsConf.getPropertyValue("rootStructureView.rootStructureWrapper.anchor");
      rootStructureLeftMenuPanAnchors = anchorsConf.getPropertyValue("rootStructureView.rootStructureLeftMenuPane.anchor");
      secondaryRootStructureWrapperAnchors = anchorsConf.getPropertyValue("rootStructureView.secondaryRootStructureWrapper.anchor");
      pushedContentWrapperAnchors = anchorsConf.getPropertyValue("rootStructureView.pushedContentWrapper.anchor");
      rootStructurePrimaryPaneAnchors = anchorsConf.getPropertyValue("rootStructureView.rootStructurePrimaryPane.anchor");
      rootStructureTernaryPaneAnchors = anchorsConf.getPropertyValue("rootStructureView.rootStructureTernaryPane.anchor");
    }

    // rootStructureWrapper.default.anchor
    if (StringUtils.isEmpty(rootStructureWrapperAnchors))
      rootStructureWrapperAnchors = APPLICATION_PROP.getProperty("rootStructureView.rootStructureWrapper.default.anchor");
    setAnchors(rootStructureWrapper, rootStructureWrapperAnchors);

    // rootStructureLeftMenuPane.default.anchor
    if (StringUtils.isEmpty(rootStructureLeftMenuPanAnchors))
      rootStructureLeftMenuPanAnchors = APPLICATION_PROP.getProperty("rootStructureView.rootStructureLeftMenuPane.default.anchor");
    setAnchors(rootStructureLeftMenuPane, rootStructureLeftMenuPanAnchors);

    // secondaryRootStructureWrapper.default.anchor
    if (StringUtils.isEmpty(secondaryRootStructureWrapperAnchors))
      secondaryRootStructureWrapperAnchors = APPLICATION_PROP.getProperty("rootStructureView.secondaryRootStructureWrapper.default.anchor");
    setAnchors(secondaryRootStructureWrapper, secondaryRootStructureWrapperAnchors);

    // pushedContentWrapper.default.anchor
    if (StringUtils.isEmpty(pushedContentWrapperAnchors))
      pushedContentWrapperAnchors = APPLICATION_PROP.getProperty("rootStructureView.pushedContentWrapper.default.anchor");
    setAnchors(pushedContentWrapper, pushedContentWrapperAnchors);

    // rootStructurePrimaryPane.default.anchor
    if (StringUtils.isEmpty(rootStructurePrimaryPaneAnchors))
      rootStructurePrimaryPaneAnchors = APPLICATION_PROP.getProperty("rootStructureView.rootStructurePrimaryPane.default.anchor");
    setAnchors(rootStructurePrimaryPane, rootStructurePrimaryPaneAnchors);

    // rootStructureTernaryPane.default.anchor
    if (StringUtils.isEmpty(rootStructureTernaryPaneAnchors))
      rootStructureTernaryPaneAnchors = APPLICATION_PROP.getProperty("rootStructureView.rootStructureTernaryPane.default.anchor");
    setAnchors(rootStructureTernaryPane, rootStructureTernaryPaneAnchors);
  }


  protected void setAnchors(Node node, String anchors) {
    final String[] an = anchors.split(",");
    final double top = Long.valueOf(an[0]);
    final double right = Long.valueOf(an[1]);
    final double bottom = Long.valueOf(an[2]);
    final double left = Long.valueOf(an[3]);

    AnchorPane.clearConstraints(node);
    AnchorPane.setTopAnchor(node, top > -1 ? top : null);
    AnchorPane.setRightAnchor(node, right > -1 ? right : null);
    AnchorPane.setBottomAnchor(node, bottom > -1 ? bottom : null);
    AnchorPane.setLeftAnchor(node, left > -1 ? left : null);
  }


  public void clearLeftMenu() {
    rootStructureLeftMenuPane.getChildren().clear();
  }


  public void addLeftMenuItem(Node node) {
    rootStructureLeftMenuPane.getChildren().add(node);
  }


  public void removeLeftMenuItem(Node node) {
    rootStructureLeftMenuPane.getChildren().remove(node);
  }


  /**
   * Back button on the listViewPaneHeader was actionned.
   *
   * @param e
   */
  public void handleEvent(PopRootViewEvent e) {
    // currentRootPage.displayChildView(e.getView());
  }


  public void handleEvent(HeaderSearchFocusedEvent ev) {
    pushedContentWrapper.setVisible(true);
    pushedContentWrapper.setManaged(true);
    final EasingInterpolator ei = new EasingInterpolator(EasingMode.IN_OUT_SINE);
    final FadeTransition ft = NodeHelper.fadeOut(pushedContentWrapper, Duration.millis(100));
    ft.setInterpolator(ei);
    ft.play();
  }


  public void handleEvent(final HeaderSearchLostFocusedEvent event) {
    final EasingInterpolator ei = new EasingInterpolator(EasingMode.IN_OUT_SINE);
    final FadeTransition ft = NodeHelper.fadeOut(pushedContentWrapper, Duration.millis(100));
    ft.setInterpolator(ei);
    ft.setOnFinished(e -> {
      pushedContentWrapper.translateXProperty().set(400);
      pushedContentWrapper.setVisible(false);
      pushedContentWrapper.setManaged(false);
    });

    ft.play();
  }


  /**
   * Fired when the root menu button is clicked
   *
   * @param e
   */
  public void handleEvent(final DisplayMenuEvent e) {
    // primary menu from left to right
    if (e.getSide() == MenuPos.PRIMARY_MENU) {
      displayPrimary(e);
    }

    // tertiary menu from right to left
    else if (e.getSide() == MenuPos.TERNARY_MENU) {
      final Node ternaryMenu = e.getNode();

      AnchorPane.setTopAnchor(ternaryMenu, 0.);
      AnchorPane.setBottomAnchor(ternaryMenu, 0.);
      AnchorPane.setRightAnchor(ternaryMenu, 0.);
      AnchorPane.setLeftAnchor(ternaryMenu, 0.);

      if (Platform.isFxApplicationThread()) {
        rootStructureTernaryPane.getChildren().clear();
        rootStructureTernaryPane.getChildren().add(ternaryMenu);
        animateShowTernaryMenu();
      } else {
        Platform.runLater(() -> {
          rootStructureTernaryPane.getChildren().clear();
          rootStructureTernaryPane.getChildren().add(ternaryMenu);
          animateShowTernaryMenu();
        });
      }
    }

    else if (e.getSide() == MenuPos.CENTER) {
    }
  }


  public void handleEvent(CloseMenuEvent e) {
    if (e.getSide() == MenuPos.PRIMARY_MENU) {
      animateHidePrimaryMenu();
    } else if (e.getSide() == MenuPos.TERNARY_MENU) {
      animateHideTernaryMenu();
    }
  }


  public void handleEvent(PinSecondaryMenuRightEvent e) {

  }


  public void handleEvent(UnpinSecondaryMenuRightEvent e) {

  }

  private boolean ternaryMenuExpanded = false;


  /**
   * Displays a popup or top right corner of the display.
   *
   * @param e
   */
  protected void animateShowTernaryMenu() {
    if (ternaryMenuExpanded) {
      animateHideTernaryMenu();
    }

    final EasingInterpolator ei = new EasingInterpolator(EasingMode.IN_OUT_SINE);
    TranslateTransition tt;

    // if screen is not maximized, no animation when showing.
    // because transalation will start out of the screen!!
    if (ViewStructure.primaryStage().isFullScreen()) {
      tt = NodeHelper.translateTo(600, 0, rootStructureTernaryPane);
    } else {
      tt = NodeHelper.translateTo(-1, 0, rootStructureTernaryPane);
      rootStructureTernaryPane.setVisible(true);
    }

    rootStructureTernaryPane.setCache(true);
    rootStructureTernaryPane.setCacheHint(CacheHint.SPEED);

    ternaryMenuExpanded = true;
    tt.setDuration(Duration.millis(300));
    tt.setInterpolator(ei);
    tt.play();
  }


  private void animateHideTernaryMenu() {
    ternaryMenuExpanded = false;
    // if screen is not maximized, no animation when closing.
    // because transalation will go out of the screen!!
    final EasingInterpolator cme = new EasingInterpolator(EasingMode.IN_EXPO);
    final TranslateTransition tte = NodeHelper.translateTo(0, 800, rootStructureTernaryPane);
    tte.setDuration(Duration.millis(50));
    tte.setInterpolator(cme);
    if (ViewStructure.primaryStage().isFullScreen()) {
      tte.play();
    } else {
      rootStructureTernaryPane.setVisible(false);
      tte.play();
    }
  }

  /*-----------------------------------------------------------------------------
  | PRIMARY HANDLING
   *=============================================================================*/
  private boolean primaryMenuIsShowing = false;


  protected void displayPrimary(DisplayMenuEvent e) {
    if (Platform.isFxApplicationThread()) {
      animateShowPrimaryMenu();
    } else {
      Platform.runLater(() -> {
        animateShowPrimaryMenu();
      });
    }
  }


  private void animateShowPrimaryMenu() {
    if (!primaryMenuIsShowing) {

      TranslateTransition tt;
      rootStructurePrimaryPane.setVisible(true);

      // if screen is not maximized, no animation when showing.
      // because translation will start out of the screen!!
      if(AbstractApplicationRunner.isDesktop()) {
        tt = NodeHelper.translateTo(50, 0, rootStructurePrimaryPane);
        tt.setDuration(Duration.millis(50));
        final EasingInterpolator ei = new EasingInterpolator(EasingMode.IN_EXPO);
        tt.setInterpolator(ei);
      } else {
        tt = NodeHelper.translateTo(0, rootStructurePrimaryPane);
        tt.setDuration(Duration.millis(200));
        final EasingInterpolator ei = new EasingInterpolator(EasingMode.IN_OUT_CIRC);
        tt.setInterpolator(ei);
      }

      EventHandler<MouseEvent> m = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
          if(!event.isConsumed() && primaryMenuIsShowing) {
            //animateHidePrimaryMenu();
          }
        }
      };

      tt.setOnFinished(e -> {
        notificationsStack.setOnMouseClicked(m);
        primaryMenuIsShowing = true;
      });


      makeCentralPaneDarker();
      tt.play();
    }
  }

  public void hidePrimaryMenu() {
    if (primaryMenuIsShowing) {
      animateHidePrimaryMenu();
    }

    primaryMenuIsShowing = false;
    notificationsStack.setOnMouseClicked(null);
  }

  private void animateHidePrimaryMenu() {
    if (primaryMenuIsShowing) {
      // if screen is not maximized, no animation when closing.
      // because translation will go out of the screen!!
      if(!AbstractApplicationRunner.isDesktop()) {
        final EasingInterpolator cme = new EasingInterpolator(EasingMode.IN_OUT_CIRC);
        final TranslateTransition tte = NodeHelper.translateTo(-800, rootStructurePrimaryPane);
        tte.setDuration(Duration.millis(1000));
        tte.setInterpolator(cme);
        tte.play();
      }
      else {
        final FadeTransition tte = NodeHelper.fadeOut(rootStructurePrimaryPane, Duration.millis(100));
        tte.setOnFinished(e -> {
          rootStructurePrimaryPane.setOpacity(1);
          rootStructurePrimaryPane.translateXProperty().set(-800);
        });

        tte.play();
      }

      makeCentralPaneLighter();
      primaryMenuIsShowing = false;
      notificationsStack.setOnMouseClicked(null);
    }
  }


  /*-----------------------------------------------------------------------------
  | DIVERS
   *=============================================================================*/
  public void makeCentralPaneLighter() {
    if (Platform.isFxApplicationThread()) {
      rootStructure.getChildren().remove(notificationsStack);
    } else {
      Platform.runLater(() -> rootStructure.getChildren().remove(notificationsStack));
    }

  }


  public void makeCentralPaneDarker() {
    notificationsStack.toFront();
    notificationsStack.getChildren().clear();
    if (Platform.isFxApplicationThread()) {
      if (!rootStructure.getChildren().contains(notificationsStack)) {
        rootStructure.getChildren().add(notificationsStack);
      }
    } else {
      Platform.runLater(() -> {
        if (!rootStructure.getChildren().contains(notificationsStack)) {
          rootStructure.getChildren().add(notificationsStack);
        }
      });
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public RootStructureController getRootStructure() {
    return this;
  }


  protected void initMaterialNodeDrag() {
    final Delta dragDelta = new Delta();
    materialNode.setOnMousePressed(mouseEvent -> {
      AnchorPane.setTopAnchor(materialNode, null);
      AnchorPane.setBottomAnchor(materialNode, null);
      AnchorPane.setRightAnchor(materialNode, null);

      // record a delta distance for the drag and drop operation.
      dragDelta.x = materialNode.getLayoutX() - mouseEvent.getSceneX();
      dragDelta.y = materialNode.getLayoutY() - mouseEvent.getSceneY();
      materialNode.setCursor(Cursor.MOVE);
    });
    materialNode.setOnMouseReleased(mouseEvent -> materialNode.setCursor(Cursor.HAND));
    materialNode.setOnMouseDragged(mouseEvent -> {
      materialNode.setLayoutX(mouseEvent.getSceneX() + dragDelta.x);
      materialNode.setLayoutY(mouseEvent.getSceneY() + dragDelta.y);
    });
    materialNode.setOnMouseEntered(mouseEvent -> materialNode.setCursor(Cursor.HAND));
  }


  public Pane getContentRootPane() {
    return rootStructure;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  protected URL getFXMLFileName() {
    return RootStructureController.class.getResource("RootStructureView.fxml");
  }

  // records relative x and y co-ordinates.
  class Delta {

    double x, y;
  }


  /**
   * Getter of contentStack
   *
   * @return the contentStack
   */
  public Pane getContentStack() {
    return contentStack;
  }


  public void displayMainView() {
    pushedContentWrapper.setVisible(false);
    rootStructureWrapper.setVisible(true);
  }

  /*-------------------------------------------------------------------------------------------------------------
  |
  |  SECONDARY ROOT STRUCTURE DISPLAYS VIEW COVERING ALL THE PAGE
  |
   *=============================================================================================================*/
  /**
   * Reference to the RootStructureController is kept because the close action is relative to it.
   * sourceRootStructure Should be set when using
   * {@link #setSecondaryRootStructureContent(Node, List)}
   */
  SimpleObjectProperty<RootStructureController> sourceRootStructure = new SimpleObjectProperty<>();


  public void setSecondaryRootStructureContent(Node node, List<Node> actions) {
    secondaryRootStructureWrapper.setCache(true);
    if (!secondaryRootStructureWrapper.isVisible()) {
      secondaryRootStructureWrapper.setVisible(true);
      animateShowSecondaryRSContent(node, actions);
    }
  }


  public RootStructureController sourceRootStructure() {
    return sourceRootStructure.get();
  }


  public void sourceRootStructure(RootStructureController sourceRootStructure) {
    this.sourceRootStructure.set(sourceRootStructure);
  }


  public void setSecondaryRootStructureContent(Node view) {
    setSecondaryRootStructureContent(view, null);
  }


  public void closeSecondaryRSWrapper() {
    animateHideSecondaryRSContent();
    //ViewStructure.instance().hideSecondaryRS();
  }


  TranslateTransition showSecondaryStrWrapper = null;
  TranslateTransition hideSecondaryStrWrapper = null;

  private void initAnimations() {
    secondaryRootStructureWrapper.addEventFilter(KeyEvent.KEY_RELEASED, ev -> {
      if (secondaryRootStructureWrapper.isVisible() && ev.getCode() == KeyCode.ESCAPE) {
        animateHideSecondaryRSContent();
      }
    });

    secondaryRootStructureWrapper.setCache(true);
    secondaryRootStructureWrapper.setCacheHint(CacheHint.SPEED);

    final EasingInterpolator cme = new EasingInterpolator(EasingMode.OUT_CIRC);
    if(AbstractApplicationRunner.isDesktop()) {
      showSecondaryStrWrapper = NodeHelper.translateYTo(0, 0, secondaryRootStructureWrapper);
      showSecondaryStrWrapper.setDuration(Duration.millis(50));
    }
    else {
      showSecondaryStrWrapper = NodeHelper.translateYTo(-100, 0, secondaryRootStructureWrapper);
      showSecondaryStrWrapper.setDuration(Duration.millis(200));
    }

    showSecondaryStrWrapper.setInterpolator(cme);

    final EasingInterpolator cme1 = new EasingInterpolator(EasingMode.OUT_CIRC);
    if(AbstractApplicationRunner.isMobile() || AbstractApplicationRunner.isSimulMobile()) {
      hideSecondaryStrWrapper = NodeHelper.translateYTo(-400, -1000, secondaryRootStructureWrapper);
      hideSecondaryStrWrapper.setDuration(Duration.millis(500));
    }
    else {
      hideSecondaryStrWrapper = NodeHelper.translateYTo(-500, -2000, secondaryRootStructureWrapper);
      hideSecondaryStrWrapper.setDuration(Duration.millis(100));
    }

    hideSecondaryStrWrapper.setInterpolator(cme1);
    hideSecondaryStrWrapper.setOnFinished(e -> {
      secondaryRSActionsWrapper.getChildren().clear();
      secondaryRSWrapperScrollpane.getChildren().clear();
      if (secondaryRootStructureWrapper.isVisible()) {
        secondaryRootStructureWrapper.setVisible(false);
      }
    });
  }

  private void animateShowSecondaryRSContent(Node node, List<Node> actions) {
    if(!AbstractApplicationRunner.isDesktop()) {
      ((Pane) node).maxWidthProperty().unbind();
      ((Pane) node).minWidthProperty().unbind();
      ((Pane) node).maxWidthProperty().bind(((Pane) node).prefWidthProperty());
      ((Pane) node).minWidthProperty().bind(((Pane) node).prefWidthProperty());
      ((Pane) node).setPrefWidth(ViewStructure.instance().platformSceneWidth().get());
    }

    Platform.runLater(()->{
      secondaryRootStructureWrapper.toFront();
      showSecondaryStrWrapper.setOnFinished(e -> {
        if (actions != null && actions.size() > 0) {
          secondaryRSActionsWrapper.getChildren().addAll(actions);
        }
        secondaryRSWrapperScrollpane.getChildren().add(node);
      });
      showSecondaryStrWrapper.play();
    });
  }

  private void animateHideSecondaryRSContent() {
    hideSecondaryStrWrapper.play();
  }


  /*------------------------------------------------------------------------------------------------------------
  |
  | pushedContentWrapper IS COVERING 3/4 OF THE ROOTSTRUCTURE. THE HEADER IS NOT HIDDEN
  |
   *=============================================================================================================*/
  protected void displayPushedView() {
    pushedContentWrapper.translateXProperty().set(0);
    pushedContentWrapper.setOpacity(1);
    pushedContentWrapper.setVisible(true);

    pushedContentWrapper.setCache(true);
    pushedContentWrapper.setCacheHint(CacheHint.SPEED);

    ternaryMenuExpanded = true;
    rootStructureWrapper.setVisible(false);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void popContent() {
    super.popContent();
    pushedContentScrollpane.setContent(null);
    pushedContentWrapper.setOpacity(0);
    pushedContentWrapper.translateXProperty().set(-2400);
    pushedContentWrapper.setVisible(false);
    rootStructureWrapper.setVisible(true);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void pushContent(StandardViewController controller, Node node) {
    super.pushContent(controller, node);
    pushedContentScrollpane.setContent(node);
    if (!pushedContentWrapper.isVisible()) {
      displayPushedView();
    }
  }


  /**
   * Get a component from this view
   *
   * @param id
   * @return VLComponent ro null
   */
  public io.github.jsoagger.jfxcore.api.InjectableComponent getComponent(String id, boolean inChildren) {
    if (children != null && children.size() > 0) {
      for (final AbstractViewController avc : children) {
        final InjectableComponent ic = avc.getComponent(id);
        if (ic != null) {
          return ic;
        }
      }
    }

    return null;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void destroy() {
    /*
     * super.destroy(); final Task<Void> t = new Task<Void>() {
     *
     * @Override protected Void call() throws Exception { rootContext = null;
     * null; return null; } }; final Thread thread = new Thread(t); thread.start();
     */
  }

  /*-----------------------------------------------------------------------------
  | Handling messages displayed on header
   *=============================================================================*/
  protected Timeline headeMessageTimeline = new Timeline();

  @FXML
  protected Pane headerMessagePane;

  public void showMessage(Node message) {
    if(Platform.isFxApplicationThread()) {
      _showMessage(message);
    }
    else {
      Platform.runLater(()->{
        _showMessage(message);
      });
    }
  }

  private void _showMessage(Node message) {
    headeMessageTimeline.setOnFinished(e -> {
      final EasingInterpolator ei = new EasingInterpolator(EasingMode.IN_OUT_SINE);
      FadeTransition fo = NodeHelper.fadeOut(headerMessagePane, Duration.millis(600));
      fo.setInterpolator(ei);
      fo.setOnFinished(a -> {
        headerMessagePane.setVisible(false);
        headerMessagePane.setManaged(false);
      });
      fo.play();
    });

    headeMessageTimeline.stop();

    headeMessageTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(2500)));
    headerMessagePane.setVisible(true);
    headerMessagePane.setManaged(true);
    headerMessagePane.setOpacity(1);

    headerMessagePane.getChildren().clear();
    headerMessagePane.getChildren().add(message);

    final EasingInterpolator ei = new EasingInterpolator(EasingMode.IN_OUT_SINE);
    TranslateTransition tt = NodeHelper.translateYTo(-70, 0, headerMessagePane);
    tt.setInterpolator(ei);
    tt.play();


    headeMessageTimeline.playFromStart();
  }
}
