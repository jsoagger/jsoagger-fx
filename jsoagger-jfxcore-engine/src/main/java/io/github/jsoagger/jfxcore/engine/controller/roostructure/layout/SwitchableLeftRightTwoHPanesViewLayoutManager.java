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
import io.github.jsoagger.jfxcore.api.IResponsiveAreaSize;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class SwitchableLeftRightTwoHPanesViewLayoutManager extends AbstractViewLayoutManager implements IViewLayoutManager {

  private static final String FXML_LOCATION = "SwitchableLeftRightTwoHPanesViewLayout.fxml";

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
  protected ScrollPane popOverFloatingScrollPane;
  @FXML
  protected Pane popOverFloatingPane;

  @FXML
  private Hyperlink closeLeftFloatingPaneButton;

  protected Node leftPopoverNode = null;
  protected Node rightPopoverNode = null;
  protected Node centerNode = null;

  /**
   * When minimized, left/right node is hidden. And the left/right content is show in floating pane.
   */
  protected SimpleBooleanProperty minimized = new SimpleBooleanProperty(false);


  /*-----------------------------------------------------------------------------
  | CONSTRUCTORS
   *=============================================================================*/
  /**
   * Constructor
   */
  public SwitchableLeftRightTwoHPanesViewLayoutManager() {
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

    // rightFloatingPaneContentWrapper.setVisible(false);
    // leftFloatingPaneContentWrapper.setVisible(false);

    // left side
    buildLeft();
    buildNonFloatingLeftActionsButton();
    if (!minimized.get()) {
      leftFloatingPaneWrapper.setVisible(true);
      closeLeftPane();
    } else {
      endPopOverLeft();
    }

    // right side
    buildRight();
    buildNonFloatingRightActionsButton();
    if (!minimized.get()) {
      rightFloatingPaneWrapper.setVisible(true);
      closeRightPane();
    } else {
      endPopOverRight();
    }

    centerScrollPaneWrapper.managedProperty().bind(centerScrollPaneWrapper.visibleProperty());
    NodeHelper.styleClassAddAll(centerStackPane, layoutManageable.getConfiguration(), "centerSectionAreaStyleClass");
    //NodeHelper.styleClassAddAll(centerStackPane, layoutManageable.getConfiguration(), "rightSectionAreaStyleClass");

    leftPopoverNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.LEFT);
    rightPopoverNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.RIGHT);
    centerNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.CENTER);
    doLayout();

    final IResponsiveAreaSize areasSize = responsiveMatrix.getSizeOf(ViewStructure.primaryStage().widthProperty().get());
    applyContentMatrix(areasSize);
  }


  protected void doLayout() {
    if (leftPopoverNode != null) {
      setLeft(leftPopoverNode);
    }

    if (rightPopoverNode != null) {
      setRight(rightPopoverNode);
    }

    if (centerNode != null) {
      setCenter(centerNode);
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
  private Hyperlink showLeftPaneButton;

  @FXML
  private Hyperlink hideLeftPaneButton;

  @FXML
  private Hyperlink showLeftFloatingPaneButton;


  private void buildLeft() {
    popOverFloatingScrollPane.translateXProperty().set(-1000);
    rightFloatingPaneWrapper.managedProperty().bind(rightFloatingPaneWrapper.visibleProperty());
    leftFloatingPaneMenuWrapper.managedProperty().bind(leftFloatingPaneMenuWrapper.visibleProperty());
    leftFloatingPaneContentWrapper.managedProperty().bind(leftFloatingPaneContentWrapper.visibleProperty());
    leftFloatingPaneContentWrapper.maxWidthProperty().bind(leftFloatingPaneContentWrapper.prefWidthProperty());
    leftFloatingPaneContent.minWidthProperty().bind(leftFloatingPaneContent.prefWidthProperty());
    NodeHelper.styleClassAddAll(leftFloatingPaneContentWrapper, layoutManageable.getConfiguration(), "leftSectionAreaStyleClass");

    leftFloatingPaneContentWrapper.visibleProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
      //  if(minimized.get())
      updateLeftLayout();
    });
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
    showLeftPaneButton.getGraphic().getStyleClass().add("grey-ikonli");
    showLeftPaneButton.getStyleClass().add("transparent-focus");
    showLeftPaneButton.setOnAction(e -> {
      openLeftPane();
    });

    IconUtils.setFontIcon("fa-angle-double-left:20", hideLeftPaneButton);
    hideLeftPaneButton.getGraphic().getStyleClass().add("grey-ikonli");
    hideLeftPaneButton.getStyleClass().add("transparent-focus");
    hideLeftPaneButton.setOnAction(e -> {
      closeLeftPane();
    });
    showLeftPaneButton.visibleProperty().bind(Bindings.not(hideLeftPaneButton.visibleProperty()));
  }


  protected void openLeftPane() {
    closeRightPane();
    if (!minimized.get()) {
      popOverFloatingScrollPane.setStyle("");
      leftFloatingPaneContentWrapper.setVisible(true);

      // if the node was minimized before
      // the content is empty so rebuild it!
      if (leftFloatingPaneContent.getChildren().size() == 0) {
        leftFloatingPaneContent.getChildren().add(leftPopoverNode);
        leftPopoverNode.setVisible(true);
      }
    } else {
      popOverFloatingScrollPane.setVisible(true);
      beginPopOverLeft();
      endPopOverRight();
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


  private void beginPopOverLeft() {
    leftFloatingPaneContentWrapper.setVisible(true);
    popOverFloatingScrollPane.setVisible(true);
    popOverFloatingPane.getChildren().clear();
    popOverFloatingPane.getChildren().add(leftPopoverNode);

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
  private Hyperlink showRightPaneButton;

  @FXML
  private Hyperlink hideRightPaneButton;

  @FXML
  private Hyperlink showRightFloatingPaneButton;


  private void buildRight() {
    leftFloatingPaneWrapper.managedProperty().bind(leftFloatingPaneWrapper.visibleProperty());
    rightFloatingPaneMenuWrapper.managedProperty().bind(rightFloatingPaneMenuWrapper.visibleProperty());
    rightFloatingPaneContentWrapper.managedProperty().bind(rightFloatingPaneContentWrapper.visibleProperty());
    rightFloatingPaneContentWrapper.maxWidthProperty().bind(rightFloatingPaneContentWrapper.prefWidthProperty());
    rightFloatingPaneContent.minWidthProperty().bind(rightFloatingPaneContent.prefWidthProperty());
    NodeHelper.styleClassAddAll(rightFloatingPaneContentWrapper, layoutManageable.getConfiguration(), "rightSectionAreaStyleClass");

    rightFloatingPaneContentWrapper.visibleProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
      //  if(minimized.get())
      updateRightLayout();
    });
  }


  private void buildNonFloatingRightActionsButton() {
    popOverFloatingPane.managedProperty().bind(popOverFloatingPane.visibleProperty());
    showRightPaneButton.managedProperty().bind(showRightPaneButton.visibleProperty());
    hideRightPaneButton.managedProperty().bind(hideRightPaneButton.visibleProperty());

    showRightPaneButton.visibleProperty().bind(Bindings.not(hideRightPaneButton.visibleProperty()));

    IconUtils.setFontIcon("fa-angle-double-left:20", showRightPaneButton);
    showRightPaneButton.getGraphic().getStyleClass().add("grey-ikonli");
    showRightPaneButton.getStyleClass().add("transparent-focus");
    showRightPaneButton.setOnAction(e -> {
      openRightPane();
    });

    IconUtils.setFontIcon("fa-angle-double-right:20", hideRightPaneButton);
    hideRightPaneButton.getGraphic().getStyleClass().add("grey-ikonli");
    hideRightPaneButton.getStyleClass().add("transparent-focus");
    hideRightPaneButton.setOnAction(e -> {
      closeRightPane();
    });
  }


  protected void openRightPane() {
    closeLeftPane();
    if (!minimized.get()) {
      rightFloatingPaneContentWrapper.setVisible(true);

      // if the node was minimized before
      // the content is empty so rebuild it!
      if (rightFloatingPaneContent.getChildren().size() == 0) {
        rightFloatingPaneContent.getChildren().add(leftPopoverNode);
        leftPopoverNode.setVisible(true);
      }
    } else {
      popOverFloatingScrollPane.setVisible(true);
      beginPopOverRight();
    }

    rightFloatingPaneMenuWrapper.getStyleClass().add("ep-right-menu-shadow-effect");
    hideRightPaneButton.setVisible(true);
  }


  private void beginPopOverRight() {
    popOverFloatingPane.getChildren().clear();
    popOverFloatingPane.getChildren().add(leftPopoverNode);

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

    rightFloatingPaneMenuWrapper.getStyleClass().remove("ep-right-menu-shadow-effect");
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
    closeLeftPane();
    closeRightPane();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public URL getFXMLLocation() {
    return SwitchableLeftRightTwoHPanesViewLayoutManager.class.getResource(FXML_LOCATION);
  }


  /**
   * @param processedView
   */
  private void setLeft(Node processedView) {
    NodeHelper.setHVGrow(processedView);
    leftFloatingPaneContent.getChildren().clear();
    leftFloatingPaneContent.getChildren().add(processedView);
    leftFloatingPaneContent.setCache(true);
    leftFloatingPaneContent.setCacheHint(CacheHint.SPEED);
  }


  /**
   * @param processedView
   */
  private void setRight(Node processedView) {
    NodeHelper.setHVGrow(processedView);
    rightFloatingPaneContent.getChildren().clear();
    rightFloatingPaneContent.getChildren().add(processedView);
    rightFloatingPaneContent.setCache(true);
    rightFloatingPaneContent.setCacheHint(CacheHint.SPEED);
  }


  /**
   * @param processedView
   */
  private void setCenter(Node processedView) {
    Platform.runLater(() -> {
      processedView.setOpacity(1);
      centerStackPane.getChildren().clear();
      centerStackPane.getChildren().add(processedView);
    });
  }


  @Override
  public void handleSceneWidthChange(ObservableValue value, Number oldSceneWidth, Number newSceneWidth) {
    if (responsiveMatrix != null) {
      final IResponsiveAreaSize areasSize = responsiveMatrix.getSizeOf(newSceneWidth.doubleValue());
      applyContentMatrix(areasSize);
    }
  }


  @Override
  public void applyContentMatrix(IResponsiveAreaSize areasSize) {
    final IResponsiveSizing leftSize = areasSize.getSizeOf(0);
    final IResponsiveSizing rightSize = areasSize.getSizeOf(2);
    final IResponsiveSizing centerSize = areasSize.getSizeOf(1);

    closeLeftPane();
    closeRightPane();

    // IResponsiveAware.resize(centerPaneWrapper, centerSize);
    NodeHelper.setHgrow(centerPaneWrapper);
    updateSwitcherVisibility();
  }


  private void updateRightLayout() {
    // this means that the right pane is hidden so
    // center should fill the space left the right pane
    if (!rightFloatingPaneContentWrapper.isVisible()) {
      AnchorPane.clearConstraints(centerPaneWrapper);
      AnchorPane.setTopAnchor(centerPaneWrapper, 0.);
      AnchorPane.setBottomAnchor(centerPaneWrapper, 0.);
      AnchorPane.setRightAnchor(centerPaneWrapper, 60.);
      AnchorPane.setLeftAnchor(centerPaneWrapper, 60.);
    }

    // this means that right pane is displayed
    // so the center pane must shift left
    // min-max witdh of right pane is 500.
    else {
      AnchorPane.clearConstraints(centerPaneWrapper);
      AnchorPane.setTopAnchor(centerPaneWrapper, 0.);
      AnchorPane.setBottomAnchor(centerPaneWrapper, 0.);
      AnchorPane.setRightAnchor(centerPaneWrapper, 460.);
      AnchorPane.setLeftAnchor(centerPaneWrapper, 60.);
    }
  }


  private void updateLeftLayout() {
    // this means that the left pane is hidden so
    // center should fill the space left by the left pane
    if (!leftFloatingPaneContentWrapper.isVisible()) {
      AnchorPane.clearConstraints(centerPaneWrapper);
      AnchorPane.setTopAnchor(centerPaneWrapper, 0.);
      AnchorPane.setBottomAnchor(centerPaneWrapper, 0.);
      AnchorPane.setLeftAnchor(centerPaneWrapper, 60.);
      AnchorPane.setRightAnchor(centerPaneWrapper, 60.);
    }

    // this means that right pane is displayed
    // so the center pane must shift left
    // min-max witdh of right pane is 500.
    else {
      AnchorPane.clearConstraints(centerPaneWrapper);
      AnchorPane.setTopAnchor(centerPaneWrapper, 0.);
      AnchorPane.setBottomAnchor(centerPaneWrapper, 0.);
      AnchorPane.setLeftAnchor(centerPaneWrapper, 460.);
      AnchorPane.setRightAnchor(centerPaneWrapper, 60.);
    }
  }


  private void updateSwitcherVisibility() {
    leftFloatingPaneContent.pseudoClassStateChanged(PseudoClass.getPseudoClass("minimized"), !centerScrollPaneWrapper.isVisible());
    rightFloatingPaneContent.pseudoClassStateChanged(PseudoClass.getPseudoClass("minimized"), !centerScrollPaneWrapper.isVisible());
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
   * {@inheritDoc}
   */
  @Override
  public boolean isSwitchable() {
    return true;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void closeSwitchable(ViewLayoutPosition position) {
    super.closeSwitchable(position);
    if (position == ViewLayoutPosition.LEFT) {
      closeLeftPane();
    }

    if (position == ViewLayoutPosition.RIGHT) {
      closeRightPane();
    }
  }


  @Override
  public void displaySwitchable(ViewLayoutPosition position) {
    super.displaySwitchable(position);
    if (position == ViewLayoutPosition.LEFT) {
      beginPopOverLeft();
    }

    if (position == ViewLayoutPosition.RIGHT) {
      beginPopOverRight();
    }
  }
}
