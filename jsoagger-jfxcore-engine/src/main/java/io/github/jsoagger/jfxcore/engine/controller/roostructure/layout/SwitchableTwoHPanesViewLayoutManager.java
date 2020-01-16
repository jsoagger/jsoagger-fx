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

package io.github.jsoagger.jfxcore.engine.controller.roostructure.layout;




import java.net.URL;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IResponsiveAreaSize;
import io.github.jsoagger.jfxcore.api.IResponsiveAware;
import io.github.jsoagger.jfxcore.api.IResponsiveSizing;
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManageable;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;
import io.github.jsoagger.jfxcore.engine.controller.main.layout.ViewStructure;
import io.github.jsoagger.jfxcore.engine.interpolator.EasingInterpolator;
import io.github.jsoagger.jfxcore.engine.interpolator.EasingMode;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class SwitchableTwoHPanesViewLayoutManager extends AbstractViewLayoutManager implements IViewLayoutManager {

  private static final String FXML_LOCATION = "SwitchableTwoHPanesViewLayout.fxml";

  /*-----------------------------------------------------------------------------
  | PRIVATE FIELDS
   *=============================================================================*/

  /*-----------------------------------------------------------------------------
  | FXML FIELDS
   *=============================================================================*/
  @FXML
  private Pane leftFloatingPaneActionsWrapper;
  @FXML
  protected Pane leftFloatingPaneContentWrapper;
  @FXML
  protected ScrollPane leftScrollPane;
  @FXML
  protected Pane leftFloatingPaneContent;
  @FXML
  protected Pane leftFloatingPane;
  @FXML
  private Pane leftFloatingPaneMenuWrapper;
  @FXML
  private Pane leftMenuContentPane;
  @FXML
  private Pane leftMenuFloatingPane;
  @FXML
  private Pane leftFloatingPaneWrapper;

  @FXML
  private Pane rightFloatingPaneActionsWrapper;
  @FXML
  protected Pane rightFloatingPaneContentWrapper;
  @FXML
  protected ScrollPane rightScrollPane;
  @FXML
  protected Pane rightFloatingPaneContent;
  @FXML
  protected Pane rightFloatingPane;
  @FXML
  private Pane rightFloatingPaneMenuWrapper;
  @FXML
  private Pane rightMenuContentPane;
  @FXML
  private Pane rightMenuFloatingPane;
  @FXML
  private Pane rightFloatingPaneWrapper;

  @FXML
  private Pane centerPaneWrapper;
  @FXML
  protected ScrollPane centerScrollPaneWrapper;
  @FXML
  protected Pane centerStackPane;

  @FXML
  protected Pane popOverFloatingScrollPane;

  @FXML
  protected Pane popOverFloatingPane;

  protected Node leftNode = null;
  protected Node rightNode = null;

  /**
   * When minimized, left/right node is hidden. And the left/right content is show in floating pane.
   */
  protected boolean leftPanewasclosedForResizing = false;
  protected SimpleBooleanProperty minimized = new SimpleBooleanProperty(false);
  private final SimpleObjectProperty<SwitchableTwoHPanesOrientation> orientation = new SimpleObjectProperty<>(SwitchableTwoHPanesOrientation.LEFT);

  // if leftswitchable, mode is permanent
  private boolean permanentLeftPane = true;
  private boolean  permanentRightPane = true;

  /*-----------------------------------------------------------------------------
  | CONSTRUCTORS
   *=============================================================================*/
  /**
   * Constructor
   */
  public SwitchableTwoHPanesViewLayoutManager() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void layout(IViewLayoutManageable layoutManageable) {
    super.layout(layoutManageable);

    rightFloatingPaneWrapper.setVisible(false);
    leftFloatingPaneWrapper.setVisible(false);

    rightFloatingPaneContentWrapper.setVisible(false);
    leftFloatingPaneContentWrapper.setVisible(false);

    permanentLeftPane = layoutManageable.getConfiguration().getBooleanProperty("permanentLeftPane", true);
    permanentRightPane = layoutManageable.getConfiguration().getBooleanProperty("permanentRightPane", true);

    // perfs speed up for animation
    popOverFloatingScrollPane.setCache(true);
    popOverFloatingScrollPane.setCacheHint(CacheHint.SPEED);
    popOverFloatingScrollPane.translateXProperty().set(-1000);

    if (orientation.get() == SwitchableTwoHPanesOrientation.LEFT) {
      buildLeft();
      buildNonFloatingLeftActionsButton();
      if (!minimized.get()) {
        leftFloatingPaneWrapper.setVisible(true);
        closeLeftPane();
      } else {
        endPopOverLeft();
      }
    } else {
      buildRight();
      buildNonFloatingRightActionsButton();
      if (!minimized.get()) {
        rightFloatingPaneWrapper.setVisible(true);
        closeRightPane();
      } else {
        endPopOverRight();
      }
    }

    centerScrollPaneWrapper.managedProperty().bind(centerScrollPaneWrapper.visibleProperty());
    NodeHelper.styleClassAddAll(centerStackPane, layoutManageable.getConfiguration(), "rightSectionAreaStyleClass");

    leftNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.LEFT);
    rightNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.RIGHT);
    doLayout();

    final IResponsiveAreaSize areasSize = responsiveMatrix.getSizeOf(ViewStructure.primaryStage().widthProperty().get());
    applyContentMatrix(areasSize);

    if(leftNode != null) {
      openLeftPane();
    }

    if(rightNode != null) {
      openRightPane();
    }

  }

  protected void doLayout() {
    if (leftNode != null) {
      setLeft(leftNode);
    }

    if (rightNode != null) {
      setRight(rightNode);

    }
  };

  /*-----------------------------------------------------------------------------
   *
   *
  | ORIENTATION LEFT HANDLING
   *
   *
   *=============================================================================*/

  @FXML
  private ButtonBase showLeftPaneButton;

  @FXML
  private ButtonBase hideLeftPaneButton;


  private void buildLeft() {
    rightFloatingPaneWrapper.managedProperty().bind(rightFloatingPaneWrapper.visibleProperty());
    leftFloatingPaneMenuWrapper.managedProperty().bind(leftFloatingPaneMenuWrapper.visibleProperty());
    leftFloatingPaneContentWrapper.managedProperty().bind(leftFloatingPaneContentWrapper.visibleProperty());
    leftFloatingPaneContentWrapper.maxWidthProperty().bind(leftFloatingPaneContentWrapper.prefWidthProperty());
    leftFloatingPaneContent.minWidthProperty().bind(leftFloatingPaneContent.prefWidthProperty());
    NodeHelper.styleClassAddAll(leftFloatingPaneContentWrapper, layoutManageable.getConfiguration(), "leftSectionAreaStyleClass");

    leftFloatingPaneContentWrapper.visibleProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
      updateLeftLayout();
    });

    leftFloatingPaneMenuWrapper.setVisible(!permanentLeftPane);
  }


  /**
   * These buttons are showLeftPaneButton and hideLeftPaneButton. These actions button should be shown
   * only when the view is minimized.
   * <p>
   * When the view is minimized, the left pane is hidden and when clicking on the show button, the
   * content is poped up over the right pane.
   * <p>
   */
  private void buildNonFloatingLeftActionsButton() {
    popOverFloatingPane.managedProperty().bind(popOverFloatingPane.visibleProperty());
    showLeftPaneButton.managedProperty().bind(showLeftPaneButton.visibleProperty());
    hideLeftPaneButton.managedProperty().bind(hideLeftPaneButton.visibleProperty());

    IconUtils.setFontIcon("fa-angle-double-right:20", showLeftPaneButton);
    showLeftPaneButton.getStyleClass().add("ep-button");
    showLeftPaneButton.setOnAction(e -> {
      openLeftPane();
    });

    IconUtils.setFontIcon("fa-angle-double-left:20", hideLeftPaneButton);
    hideLeftPaneButton.getStyleClass().add("ep-button");
    hideLeftPaneButton.setOnAction(e -> {
      closeLeftPane();
    });
    showLeftPaneButton.visibleProperty().bind(Bindings.not(hideLeftPaneButton.visibleProperty()));
  }


  /**
   * Return true if left is currently opened.
   *
   * @return
   */
  protected boolean isLeftPaneOpen() {
    return leftFloatingPaneContentWrapper.isVisible() || popOverFloatingScrollPane.isVisible();
  }


  protected void openLeftPane() {
    if (!minimized.get()) {
      leftFloatingPaneContentWrapper.setVisible(true);

      // if the node was minimized before
      // the content is empty so rebuild it!
      if (leftFloatingPaneContent.getChildren().size() == 0) {
        leftFloatingPaneContent.getChildren().add(leftNode);
        leftNode.setVisible(true);
      }
    } else {
      popOverFloatingScrollPane.setVisible(true);
      beginPopOverLeft();
    }

    hideLeftPaneButton.setVisible(true);
  }


  protected void closeLeftPane() {
    if (!minimized.get()) {
      leftFloatingPaneContentWrapper.setVisible(false);
    } else {
      if (popOverFloatingScrollPane.isVisible()) {
        endPopOverLeft();
      }
    }

    hideLeftPaneButton.setVisible(false);
  }

  protected void closeLeftPaneImmediat() {
    leftFloatingPaneContentWrapper.setVisible(false);
    popOverFloatingScrollPane.setVisible(false);
    popOverFloatingScrollPane.translateXProperty().set(-1000);
    hideLeftPaneButton.setVisible(false);
  }


  private void beginPopOverLeft() {
    popOverFloatingScrollPane.setVisible(true);
    popOverFloatingPane.getChildren().clear();
    popOverFloatingPane.getChildren().add(leftNode);

    popOverFloatingScrollPane.setStyle("-fx-effect:-ep-pane-left-pane-shadow-effect");
    AnchorPane.clearConstraints(popOverFloatingScrollPane);
    AnchorPane.setTopAnchor(popOverFloatingScrollPane, 0.);
    AnchorPane.setBottomAnchor(popOverFloatingScrollPane, 0.);
    AnchorPane.setLeftAnchor(popOverFloatingScrollPane, 60.);

    final EasingInterpolator ei = new EasingInterpolator(EasingMode.IN_EXPO);
    TranslateTransition tt;

    popOverFloatingScrollPane.layout();

    tt = NodeHelper.translateTo(-1000, 0, popOverFloatingScrollPane);
    tt.setDuration(Duration.millis(100));
    tt.setInterpolator(ei);
    tt.play();
  }


  private void endPopOverLeft() {
    final EasingInterpolator ei = new EasingInterpolator(EasingMode.IN_EXPO);
    TranslateTransition tt;

    tt = NodeHelper.translateTo(0, -1000, popOverFloatingScrollPane);
    tt.setDuration(Duration.millis(100));
    tt.setInterpolator(ei);
    tt.setOnFinished(e -> popOverFloatingScrollPane.setVisible(false));
    tt.play();
  }

  /*-----------------------------------------------------------------------------
   *
   *
  | ORIENTATION RIGHT HANDLING
   *
   *
   *=============================================================================*/

  @FXML
  private ButtonBase showRightPaneButton;

  @FXML
  private ButtonBase hideRightPaneButton;


  private void buildRight() {
    leftFloatingPaneWrapper.managedProperty().bind(leftFloatingPaneWrapper.visibleProperty());
    rightFloatingPaneMenuWrapper.managedProperty().bind(rightFloatingPaneMenuWrapper.visibleProperty());
    rightFloatingPaneContentWrapper.managedProperty().bind(rightFloatingPaneContentWrapper.visibleProperty());
    rightFloatingPaneContentWrapper.maxWidthProperty().bind(rightFloatingPaneContentWrapper.prefWidthProperty());
    rightFloatingPaneContent.minWidthProperty().bind(rightFloatingPaneContent.prefWidthProperty());
    NodeHelper.styleClassAddAll(rightFloatingPaneContentWrapper, layoutManageable.getConfiguration(), "leftSectionAreaStyleClass");

    rightFloatingPaneContentWrapper.visibleProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
      updateRightLayout();
    });
    rightFloatingPaneMenuWrapper.setVisible(permanentRightPane);
  }


  private void buildNonFloatingRightActionsButton() {
    popOverFloatingPane.managedProperty().bind(popOverFloatingPane.visibleProperty());
    showRightPaneButton.managedProperty().bind(showRightPaneButton.visibleProperty());
    hideRightPaneButton.managedProperty().bind(hideRightPaneButton.visibleProperty());

    showRightPaneButton.visibleProperty().bind(Bindings.not(hideRightPaneButton.visibleProperty()));

    IconUtils.setFontIcon("fa-angle-double-left:20", showRightPaneButton);
    showRightPaneButton.setOnAction(e -> {
      openRightPane();
    });

    IconUtils.setFontIcon("fa-angle-double-right:20", hideRightPaneButton);
    hideRightPaneButton.setOnAction(e -> {
      closeRightPane();
    });
  }


  protected void openRightPane() {
    if (!minimized.get()) {
      rightFloatingPaneContentWrapper.setVisible(true);

      // if the node was minimized before
      // the content is empty so rebuild it!
      if (rightFloatingPaneContent.getChildren().size() == 0) {
        rightFloatingPaneContent.getChildren().add(leftNode);
        leftNode.setVisible(true);
      }
    } else {
      popOverFloatingScrollPane.setVisible(true);
      beginPopOverRight();
    }

    hideRightPaneButton.setVisible(true);
  }


  private void beginPopOverRight() {
    popOverFloatingPane.getChildren().clear();
    popOverFloatingPane.getChildren().add(leftNode);

    // -fx-effect:-ep-pane-left-pane-shadow-effect;
    popOverFloatingScrollPane.setStyle("-fx-effect:-ep-pane-right-pane-shadow-effect");

    AnchorPane.clearConstraints(popOverFloatingScrollPane);
    AnchorPane.setTopAnchor(popOverFloatingScrollPane, 0.);
    AnchorPane.setBottomAnchor(popOverFloatingScrollPane, 0.);
    AnchorPane.setRightAnchor(popOverFloatingScrollPane, 60.);

    final EasingInterpolator ei = new EasingInterpolator(EasingMode.IN_EXPO);
    TranslateTransition tt;

    tt = NodeHelper.translateTo(2000, 0, popOverFloatingScrollPane);
    tt.setDuration(Duration.millis(100));
    tt.setInterpolator(ei);
    tt.setOnFinished(e -> {
      popOverFloatingScrollPane.layout();
      popOverFloatingPane.layout();
    });

    tt.play();
  }


  protected void closeRightPane() {
    if (!minimized.get()) {
      rightFloatingPaneContentWrapper.setVisible(false);
    } else {
      if (popOverFloatingScrollPane.isVisible()) {
        endPopOverRight();
      }
    }
    hideRightPaneButton.setVisible(false);
  }

  protected void closeRightPaneImmediat() {
    rightFloatingPaneContentWrapper.setVisible(false);
    popOverFloatingScrollPane.setVisible(false);
    popOverFloatingScrollPane.translateXProperty().set(2000);
    hideRightPaneButton.setVisible(false);
  }


  private void endPopOverRight() {
    final EasingInterpolator ei = new EasingInterpolator(EasingMode.IN_EXPO);
    TranslateTransition tt;

    tt = NodeHelper.translateTo(0, 2000, popOverFloatingScrollPane);
    tt.setDuration(Duration.millis(100));
    tt.setInterpolator(ei);
    tt.setOnFinished(e -> {
      popOverFloatingScrollPane.setVisible(false);
    });
    tt.play();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void popContent() {
    super.popContent();
    if (orientation.get() == SwitchableTwoHPanesOrientation.LEFT) {
      closeLeftPane();
    } else {
      closeRightPane();
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public URL getFXMLLocation() {
    return SwitchableTwoHPanesViewLayoutManager.class.getResource(FXML_LOCATION);
  }

  protected Node leftPane = null;


  /**
   * @param processedView
   */
  private void setLeft(Node processedView) {
    leftPane = processedView;

    if (orientation.get() == SwitchableTwoHPanesOrientation.LEFT) {
      NodeHelper.setHVGrow(processedView);
      leftFloatingPaneContent.getChildren().clear();
      leftFloatingPaneContent.getChildren().add(leftPane);
      leftFloatingPaneContent.setCache(true);
      leftFloatingPaneContent.setCacheHint(CacheHint.SPEED);
      leftScrollPane.setStyle("-fx-effect: dropshadow(one-pass-box, -grey-color-400, 14.0, 0.2, 2.0, 1.0);");
    } else {
      NodeHelper.setHVGrow(processedView);
      rightFloatingPaneContent.getChildren().clear();
      rightFloatingPaneContent.getChildren().add(leftPane);
      rightFloatingPaneContent.setCache(true);
      rightFloatingPaneContent.setCacheHint(CacheHint.SPEED);
    }
  }


  /**
   * @param processedView
   */
  private void setRight(Node processedView) {
    Platform.runLater(() -> {
      processedView.setOpacity(1);
      centerStackPane.getChildren().clear();
      centerStackPane.getChildren().add(processedView);
    });
  }


  @SuppressWarnings("rawtypes")
  @Override
  public void handleSceneWidthChange(ObservableValue value, Number oldSceneWidth, Number newSceneWidth) {
    if (responsiveMatrix != null) {
      final IResponsiveAreaSize areasSize = responsiveMatrix.getSizeOf(newSceneWidth.doubleValue());
      applyContentMatrix(areasSize);
    }
  }


  @Override
  public void applyContentMatrix(IResponsiveAreaSize areasSize) {
    if(isLeftPaneOpen()) {
      leftPanewasclosedForResizing = true;
    }

    resizingTimeline.stop();
    resizingTimeline.playFromStart();

    final IResponsiveSizing leftSize = areasSize.getSizeOf(0);
    final IResponsiveSizing rightSize = areasSize.getSizeOf(1);

    minimized.set(leftSize.isToMinimize() || leftSize.isToHide());

    if (orientation.get() == SwitchableTwoHPanesOrientation.LEFT) {
      //final boolean washidden = !leftFloatingPaneContentWrapper.isVisible();
      IResponsiveAware.resize(leftFloatingPaneContentWrapper, leftSize);
      leftFloatingPaneContent.pseudoClassStateChanged(PseudoClass.getPseudoClass("minimized"), leftSize.isToMinimize() || leftSize.isToHide());
      //if (washidden) {
      //leftFloatingPaneContentWrapper.setVisible(false);
      //}
      //closeLeftPaneImmediat();
    } else {
      final boolean washidden = !rightFloatingPaneContentWrapper.isVisible();
      IResponsiveAware.resize(rightFloatingPaneContentWrapper, leftSize);
      rightFloatingPaneContent.pseudoClassStateChanged(PseudoClass.getPseudoClass("minimized"), leftSize.isToMinimize() || leftSize.isToHide());
      if (washidden) {
        rightFloatingPaneContentWrapper.setVisible(false);
      }
      closeRightPaneImmediat();
    }

    IResponsiveAware.resize(centerScrollPaneWrapper, rightSize);
    updateSwitcherVisibility();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleSceneWidthChangeEnd() {
    super.handleSceneWidthChangeEnd();
    if(leftPanewasclosedForResizing) {
      leftPanewasclosedForResizing = false;
      showLeftPaneButton.fire();
    }
  }


  private void updateRightLayout() {
    if (orientation.get() == SwitchableTwoHPanesOrientation.RIGHT) {
      // this means that the right pane is hidden so
      // center should fill the space left the right pane
      if (!rightFloatingPaneContentWrapper.isVisible()) {
        AnchorPane.clearConstraints(centerPaneWrapper);
        AnchorPane.setTopAnchor(centerPaneWrapper, 0.);
        AnchorPane.setBottomAnchor(centerPaneWrapper, 0.);
        AnchorPane.setRightAnchor(centerPaneWrapper, 60.);
        AnchorPane.setLeftAnchor(centerPaneWrapper, 0.);
      }

      // this means that right pane is displayed
      // so the center pane must shift left
      // min-max witdh of right pane is 500.
      else {
        AnchorPane.clearConstraints(centerPaneWrapper);
        AnchorPane.setTopAnchor(centerPaneWrapper, 0.);
        AnchorPane.setBottomAnchor(centerPaneWrapper, 0.);
        AnchorPane.setRightAnchor(centerPaneWrapper, 410.);
        AnchorPane.setLeftAnchor(centerPaneWrapper, 0.);
      }
    }
  }


  private void updateLeftLayout() {
    if (orientation.get() == SwitchableTwoHPanesOrientation.LEFT) {
      // this means that the left pane is hidden so
      // center should fill the space left by the left pane
      if (!leftFloatingPaneContentWrapper.isVisible()) {
        AnchorPane.clearConstraints(centerPaneWrapper);
        AnchorPane.setTopAnchor(centerPaneWrapper, 0.);
        AnchorPane.setBottomAnchor(centerPaneWrapper, 0.);
        AnchorPane.setLeftAnchor(centerPaneWrapper, 60.);
        AnchorPane.setRightAnchor(centerPaneWrapper, 0.);
      }

      // this means that right pane is displayed
      // so the center pane must shift left
      // min-max witdh of right pane is 460(size of the floating pane).
      else {
        AnchorPane.clearConstraints(centerPaneWrapper);
        AnchorPane.setTopAnchor(centerPaneWrapper, 0.);
        AnchorPane.setBottomAnchor(centerPaneWrapper, 0.);

        if(permanentLeftPane) {
          AnchorPane.setLeftAnchor(centerPaneWrapper, 400.);
        }
        else {
          AnchorPane.setLeftAnchor(centerPaneWrapper, 460.);
        }
        AnchorPane.setRightAnchor(centerPaneWrapper, 0.);
      }
    }
  }


  private void updateSwitcherVisibility() {
    if (orientation.get() == SwitchableTwoHPanesOrientation.LEFT) {
      leftFloatingPaneContent.pseudoClassStateChanged(PseudoClass.getPseudoClass("minimized"), !centerScrollPaneWrapper.isVisible());
    } else {
      rightFloatingPaneContent.pseudoClassStateChanged(PseudoClass.getPseudoClass("minimized"), !centerScrollPaneWrapper.isVisible());
    }

    centerStackPane.pseudoClassStateChanged(PseudoClass.getPseudoClass("switched"), leftFloatingPaneMenuWrapper.isVisible());
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return rootPane;
  }


  /**
   * @return
   */
  public SimpleObjectProperty<SwitchableTwoHPanesOrientation> orientationProperty() {
    return orientation;
  }


  public void setOrientation(String orientation) {
    if (StringUtils.isEmpty(orientation)) {
      orientationProperty().set(SwitchableTwoHPanesOrientation.LEFT);
    }

    if ("LEFT".equalsIgnoreCase(orientation)) {
      orientationProperty().set(SwitchableTwoHPanesOrientation.LEFT);
    } else {
      orientationProperty().set(SwitchableTwoHPanesOrientation.RIGHT);
    }
  }

  public enum SwitchableTwoHPanesOrientation {
    LEFT, RIGHT;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSwitchable() {
    return true;
  }


  @Override
  public void closeSwitchable(ViewLayoutPosition position) {
    super.closeSwitchable(position);
    if (position == ViewLayoutPosition.LEFT) {
      if(minimized.get()) {
        closeLeftPane();
      }
    }

    if (position == ViewLayoutPosition.RIGHT) {
      closeRightPane();
    }
  }


  @Override
  public void displaySwitchable(ViewLayoutPosition position) {
    super.displaySwitchable(position);
    if (position == ViewLayoutPosition.LEFT) {
      openLeftPane();
    }

    if (position == ViewLayoutPosition.RIGHT) {
      openRightPane();
    }
  }
}
