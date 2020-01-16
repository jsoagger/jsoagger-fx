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
import io.github.jsoagger.jfxcore.api.IResponsiveAware;
import io.github.jsoagger.jfxcore.api.IResponsiveSizing;
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManageable;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;
import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Pane;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class FixedLeftRightFourHPanesViewLayoutManager extends AbstractViewLayoutManager implements IViewLayoutManager {

  private static final String FXML_LOCATION = "FixedLeftRightFourHPanesViewLayout.fxml";

  /*-----------------------------------------------------------------------------
  | PRIVATE FIELDS
   *=============================================================================*/

  /*-----------------------------------------------------------------------------
  | FXML FIELDS
   *=============================================================================*/
  @FXML
  protected Pane leftLeftFixedAreaSection;
  @FXML
  protected Pane leftFixedAreaSection;
  @FXML
  protected Pane centerFixedAreaSection;
  @FXML
  protected Pane rightFixedAreaSection;
  @FXML
  protected Pane rightRightFixedAreaSection;
  @FXML
  protected ScrollPane mainScrollPane;

  private boolean verticalScroll = true;
  private boolean horizontalScroll = false;


  /*-----------------------------------------------------------------------------
  | CONSTRUCTORS
   *=============================================================================*/
  /**
   * Constructor
   */
  public FixedLeftRightFourHPanesViewLayoutManager() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void layout(IViewLayoutManageable layoutManageable) {
    super.layout(layoutManageable);

    leftLeftFixedAreaSection.managedProperty().bind(leftLeftFixedAreaSection.visibleProperty());
    leftLeftFixedAreaSection.maxWidthProperty().bind(leftLeftFixedAreaSection.prefWidthProperty());
    leftLeftFixedAreaSection.minWidthProperty().bind(leftLeftFixedAreaSection.prefWidthProperty());

    rightRightFixedAreaSection.managedProperty().bind(rightRightFixedAreaSection.visibleProperty());
    rightRightFixedAreaSection.maxWidthProperty().bind(rightRightFixedAreaSection.prefWidthProperty());
    rightRightFixedAreaSection.minWidthProperty().bind(rightRightFixedAreaSection.prefWidthProperty());

    leftFixedAreaSection.managedProperty().bind(leftFixedAreaSection.visibleProperty());
    leftFixedAreaSection.maxWidthProperty().bind(leftFixedAreaSection.prefWidthProperty());
    leftFixedAreaSection.minWidthProperty().bind(leftFixedAreaSection.prefWidthProperty());

    rightFixedAreaSection.managedProperty().bind(rightFixedAreaSection.visibleProperty());
    rightFixedAreaSection.maxWidthProperty().bind(rightFixedAreaSection.prefWidthProperty());
    rightFixedAreaSection.minWidthProperty().bind(rightFixedAreaSection.prefWidthProperty());

    centerFixedAreaSection.minWidthProperty().bind(centerFixedAreaSection.maxWidthProperty());

    NodeHelper.setStyleClass(leftLeftFixedAreaSection, layoutManageable.getConfiguration(), "leftLeftSectionAreaStyleClass", false);
    NodeHelper.setStyleClass(leftFixedAreaSection, layoutManageable.getConfiguration(), "leftSectionAreaStyleClass", false);
    NodeHelper.setStyleClass(centerFixedAreaSection, layoutManageable.getConfiguration(), "centerSectionAreaStyleClass", false);
    NodeHelper.setStyleClass(rightFixedAreaSection, layoutManageable.getConfiguration(), "rightSectionAreaStyleClass", false);
    NodeHelper.setStyleClass(rightRightFixedAreaSection, layoutManageable.getConfiguration(), "rightRightSectionAreaStyleClass", false);

    NodeHelper.setStyleClass(mainScrollPane, layoutManageable.getConfiguration(), "mainScrollPaneStyleClass", false);

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

    if (!verticalScroll) {
      mainScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
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
      centerFixedAreaSection.getChildren().clear();
      centerFixedAreaSection.getChildren().add(node);
      NodeHelper.setHgrow(node);
      try {
        ReflectionUIUtils.bind(node, "prefWidth", centerFixedAreaSection, "width");
      } catch (Exception ex) {
        ex.printStackTrace();
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
    IResponsiveSizing leftLeftSize = areasSize.getSizeOf(0);
    IResponsiveSizing leftSize = areasSize.getSizeOf(1);
    IResponsiveSizing centerSize = areasSize.getSizeOf(2);
    IResponsiveSizing rightSize = areasSize.getSizeOf(3);
    IResponsiveSizing rightRightSize = areasSize.getSizeOf(4);

    IResponsiveAware.doResize(leftLeftFixedAreaSection, leftLeftSize);
    IResponsiveAware.doResize(leftFixedAreaSection, leftSize);
    IResponsiveAware.doResize(centerFixedAreaSection, centerSize);
    IResponsiveAware.doResize(rightFixedAreaSection, rightSize);
    IResponsiveAware.doResize(rightRightFixedAreaSection, rightRightSize);
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
}
