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

import io.github.jsoagger.jfxcore.api.IResponsiveAreaSize;
import io.github.jsoagger.jfxcore.api.IResponsiveAware;
import io.github.jsoagger.jfxcore.api.IResponsiveSizing;
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.api.components.annotation.GraalComponent;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManageable;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * A layout where children are layed out in 3 horizontal panes. The center pane and the right pane
 * are scrolled. The left pane stays at its position all time.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
@GraalComponent
public class FixedLeftRightThreeHPanesViewLayoutManager extends AbstractViewLayoutManager
    implements IViewLayoutManager {

  private static final String FXML_LOCATION = "FixedLeftRightThreeHPanesViewLayout.fxml";

  /*-----------------------------------------------------------------------------
  | PRIVATE FIELDS
   *=============================================================================*/

  /*-----------------------------------------------------------------------------
  | FXML FIELDS
   *=============================================================================*/
  @FXML
  protected Pane rootPane;
  @FXML
  protected Pane contentWrapper;
  @FXML
  protected Pane leftFixedAreaSection;
  @FXML
  protected StackPane centerFixedAreaSection;
  @FXML
  protected Pane scrolledCenterFixedAreaSection;
  @FXML
  protected Pane rightFixedAreaSection;
  @FXML
  protected ScrollPane mainScrollPane;
  @FXML
  protected Pane editorStructureAreaSection;
  @FXML
  protected Pane contentStructureAreaSection;

  private boolean verticalScroll = false;
  private boolean horizontalScroll = false;

  /*-----------------------------------------------------------------------------
  | CONSTRUCTORS
   *=============================================================================*/
  /**
   * Constructor
   */
  public FixedLeftRightThreeHPanesViewLayoutManager() {
    super();
  }

  @Override
  public String toString() {
    return "+++++===== " + rootPane + " , " + contentStructureAreaSection + " , "
        + leftFixedAreaSection + ", " + centerFixedAreaSection + ", " + mainScrollPane;
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public void layout(IViewLayoutManageable layoutManageable) {
    super.layout(layoutManageable);

    // generated from FXML
    if (getRootPane() == null) {
      rootPane = new StackPane();
      contentWrapper = new VBox();
      contentStructureAreaSection = new HBox();
      leftFixedAreaSection = new VBox();
      mainScrollPane = new ScrollPane();
      scrolledCenterFixedAreaSection = new StackPane();
      rightFixedAreaSection = new StackPane();
      centerFixedAreaSection = new StackPane();
      editorStructureAreaSection = new HBox();

      rootPane.getChildren().add(contentWrapper);
      contentWrapper.getChildren().addAll(contentStructureAreaSection, editorStructureAreaSection);
      contentStructureAreaSection.getChildren().addAll(leftFixedAreaSection, mainScrollPane,
          centerFixedAreaSection, rightFixedAreaSection);

      mainScrollPane.setContent(scrolledCenterFixedAreaSection);
      mainScrollPane.setFitToHeight(true);
      mainScrollPane.setFitToWidth(true);

      contentWrapper.setPrefHeight(-1);
      contentWrapper.setPrefWidth(-1);

      centerFixedAreaSection.setAlignment(Pos.CENTER);
      centerFixedAreaSection.setPrefHeight(-1);
      centerFixedAreaSection.setPrefWidth(-1);
      NodeHelper.setVgrow(contentStructureAreaSection);
      postLayout();

      toString();
    }

    editorStructureAreaSection.managedProperty().bind(editorStructureAreaSection.visibleProperty());
    contentStructureAreaSection.managedProperty()
        .bind(contentStructureAreaSection.visibleProperty());
    editorStructureAreaSection.setVisible(false);

    leftFixedAreaSection.managedProperty().bind(leftFixedAreaSection.visibleProperty());
    leftFixedAreaSection.maxWidthProperty().bind(leftFixedAreaSection.prefWidthProperty());
    leftFixedAreaSection.minWidthProperty().bind(leftFixedAreaSection.prefWidthProperty());

    rightFixedAreaSection.managedProperty().bind(rightFixedAreaSection.visibleProperty());
    rightFixedAreaSection.maxWidthProperty().bind(rightFixedAreaSection.prefWidthProperty());
    centerFixedAreaSection.managedProperty().bind(centerFixedAreaSection.visibleProperty());

    if (verticalScroll) {
      scrolledCenterFixedAreaSection.minWidthProperty()
          .bind(scrolledCenterFixedAreaSection.maxWidthProperty());
      NodeHelper.setStyleClass(scrolledCenterFixedAreaSection, layoutManageable.getConfiguration(),
          "centerSectionAreaStyleClass", false);
      centerFixedAreaSection.setVisible(false);
    } else {
      mainScrollPane.setVisible(false);
      mainScrollPane.setManaged(false);
      centerFixedAreaSection.minWidthProperty().bind(centerFixedAreaSection.prefWidthProperty());
      centerFixedAreaSection.maxWidthProperty().bind(centerFixedAreaSection.prefWidthProperty());
      NodeHelper.setStyleClass(centerFixedAreaSection, layoutManageable.getConfiguration(),
          "centerSectionAreaStyleClass", false);
    }

    NodeHelper.setStyleClass(getRootPane(), layoutManageable.getConfiguration(),
        "rootPaneStyleClass", true);
    NodeHelper.setStyleClass(leftFixedAreaSection, layoutManageable.getConfiguration(),
        "leftSectionAreaStyleClass", false);
    NodeHelper.setStyleClass(rightFixedAreaSection, layoutManageable.getConfiguration(),
        "rightSectionAreaStyleClass", false);
    NodeHelper.setStyleClass(mainScrollPane, layoutManageable.getConfiguration(),
        "mainScrollPaneStyleClass", false);
    NodeHelper.setStyleClass(editorStructureAreaSection, layoutManageable.getConfiguration(),
        "editorStructureAreaSectionStyleClass", false);

    Node leftNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.LEFT);
    Node centerNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.CENTER);
    Node rightNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.RIGHT);

    if (leftNode != null) {
      setLeft(leftNode);
    }

    if (centerNode != null) {
      setCenter(centerNode);
    }

    if (rightNode != null) {
      setRight(rightNode);
    }

    if (!horizontalScroll) {
      mainScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
    }
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return rootPane;
  }

  /*-----------------------------------------------------------------------------
  | PUBLIC METHODS
   *=============================================================================*/
  public void setLeft(Node node) {
    leftFixedAreaSection.getChildren().clear();
    leftFixedAreaSection.getChildren().add(node);
  }

  public void setCenter(Node node) {
    NodeHelper.setVgrow(node);
    Platform.runLater(() -> {

      if (verticalScroll) {
        scrolledCenterFixedAreaSection.getChildren().clear();
        scrolledCenterFixedAreaSection.getChildren().add(node);
        NodeHelper.setHgrow(node);
        try {
          ReflectionUIUtils.bind(node, "prefWidth", scrolledCenterFixedAreaSection, "width");
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      } else {
        centerFixedAreaSection.getChildren().clear();
        centerFixedAreaSection.getChildren().add(node);
        NodeHelper.setHgrow(node);
        try {
          ReflectionUIUtils.bind(node, "prefWidth", centerFixedAreaSection, "width");
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }

    });
  }

  public void setRight(Node node) {
    Platform.runLater(() -> {
      rightFixedAreaSection.getChildren().clear();
      rightFixedAreaSection.getChildren().add(node);
    });
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public void applyContentMatrix(IResponsiveAreaSize areasSize) {
    IResponsiveSizing leftSize = areasSize.getSizeOf(0);
    IResponsiveSizing centerSize = areasSize.getSizeOf(1);
    IResponsiveSizing rightSize = areasSize.getSizeOf(2);

    IResponsiveAware.doResize(leftFixedAreaSection, leftSize);
    if (verticalScroll) {
      IResponsiveAware.doResize(mainScrollPane, centerSize);
    } else {
      IResponsiveAware.doResize(centerFixedAreaSection, centerSize);
    }

    IResponsiveAware.doResize(rightFixedAreaSection, rightSize);
  }

  Node pushedContent;

  /**
   * @{inheritedDoc}
   */
  @Override
  public void pushContent(Node node) {
    pushedContent = node;
    super.pushContent(node);
    // NodeHelper.setVgrow(node);

    editorStructureAreaSection.setVisible(true);
    editorStructureAreaSection.getChildren().clear();
    editorStructureAreaSection.getChildren().add(node);
    contentStructureAreaSection.setVisible(false);
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public void popContent() {
    super.popContent();
    if (pushedContent != null) {
      editorStructureAreaSection.setVisible(false);
      contentStructureAreaSection.setVisible(true);
      pushedContent = null;
    }
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  protected void finalize() throws Throwable {
    super.finalize();
    centerFixedAreaSection.minWidthProperty().unbind();
    centerFixedAreaSection.maxWidthProperty().unbind();
    leftFixedAreaSection.minWidthProperty().unbind();
    leftFixedAreaSection.maxWidthProperty().unbind();
    rightFixedAreaSection.minWidthProperty().unbind();
    rightFixedAreaSection.maxWidthProperty().unbind();
    mainScrollPane = null;
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public URL getFXMLLocation() {
    return this.getClass().getResource(FXML_LOCATION);
  }

  /**
   * Getter of horizontalScroll
   *
   * @return the horizontalScroll
   */
  @Override
  public boolean isHorizontalScroll() {
    return horizontalScroll;
  }

  /**
   * Setter of horizontalScroll
   *
   * @param horizontalScroll the horizontalScroll to set
   */
  @Override
  public void setHorizontalScroll(boolean horizontalScroll) {
    this.horizontalScroll = horizontalScroll;
  }

  /**
   * Getter of verticalScroll
   *
   * @return the verticalScroll
   */
  @Override
  public boolean isVerticalScroll() {
    return verticalScroll;
  }

  /**
   * Setter of verticalScroll
   *
   * @param verticalScroll the verticalScroll to set
   */
  @Override
  public void setVerticalScroll(boolean verticalScroll) {
    this.verticalScroll = verticalScroll;
  }

  @Override
  public Pane getRootPane() {
    return rootPane;
  }

  public void setRootPane(Pane rootPane) {
    this.rootPane = rootPane;
  }
}
