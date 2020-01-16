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
 * A layout where children are layed out in 3 horizontal panes. The center pane and the right pane
 * are scrolled. The left pane stays at its position all time.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class MaximizedDetailsViewLayoutManager extends AbstractViewLayoutManager implements IViewLayoutManager {

  private static final String FXML_LOCATION = "MaximizedDetailsViewLayout.fxml";

  /*-----------------------------------------------------------------------------
  | PRIVATE FIELDS
   *=============================================================================*/

  /*-----------------------------------------------------------------------------
  | FXML FIELDS
   *=============================================================================*/
  @FXML
  protected Pane leftSpacer;
  @FXML
  protected Pane headerAreaSection;

  @FXML
  protected Pane leftFixedAreaSection;
  @FXML
  protected Pane centerFixedAreaSection;
  @FXML
  protected Pane rightFixedAreaSection;
  @FXML
  protected ScrollPane mainScrollPane;
  @FXML
  protected Pane topAnchored;
  @FXML
  protected Pane maximizedDetailsViewPaneVbox;
  @FXML
  protected Pane rootAnchor;

  private boolean verticalScroll = false;
  private boolean horizontalScroll = false;


  /*-----------------------------------------------------------------------------
  | CONSTRUCTORS
   *=============================================================================*/
  /**
   * Constructor
   */
  public MaximizedDetailsViewLayoutManager() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void layout(IViewLayoutManageable layoutManageable) {
    super.layout(layoutManageable);

    leftSpacer.managedProperty().bind(leftSpacer.visibleProperty());
    leftSpacer.minWidthProperty().bind(leftSpacer.prefWidthProperty());

    leftFixedAreaSection.managedProperty().bind(leftFixedAreaSection.visibleProperty());
    leftFixedAreaSection.maxWidthProperty().bind(leftFixedAreaSection.prefWidthProperty());
    leftFixedAreaSection.minWidthProperty().bind(leftFixedAreaSection.prefWidthProperty());

    rightFixedAreaSection.managedProperty().bind(rightFixedAreaSection.visibleProperty());
    rightFixedAreaSection.maxWidthProperty().bind(rightFixedAreaSection.prefWidthProperty());
    rightFixedAreaSection.minWidthProperty().bind(rightFixedAreaSection.prefWidthProperty());

    centerFixedAreaSection.minWidthProperty().bind(centerFixedAreaSection.prefWidthProperty());
    rightFixedAreaSection.maxWidthProperty().bind(rightFixedAreaSection.prefWidthProperty());

    headerAreaSection.managedProperty().bind(headerAreaSection.visibleProperty());

    NodeHelper.setStyleClass(leftFixedAreaSection, layoutManageable.getConfiguration(), "leftSectionAreaStyleClass", false);
    NodeHelper.setStyleClass(centerFixedAreaSection, layoutManageable.getConfiguration(), "centerSectionAreaStyleClass", false);
    NodeHelper.setStyleClass(rightFixedAreaSection, layoutManageable.getConfiguration(), "rightSectionAreaStyleClass", false);
    NodeHelper.setStyleClass(rightFixedAreaSection, layoutManageable.getConfiguration(), "topSectionAreaStyleClass", false);

    final Node leftNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.LEFT);
    final Node centerNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.CENTER);
    final Node rightNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.RIGHT);
    final Node topNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.TOP);

    if (leftNode != null) {
      setLeft(leftNode);
    }

    if (centerNode != null) {
      setCenter(centerNode);
    }

    if (rightNode != null) {
      setRight(rightNode);
    }

    if (topNode != null) {
      setTop(topNode);
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


  private void setTop(Node topNode) {
    headerAreaSection.getChildren().clear();
    headerAreaSection.getChildren().add(topNode);
  }


  public void setCenter(Node node) {
    Platform.runLater(() -> {
      centerFixedAreaSection.getChildren().clear();
      centerFixedAreaSection.getChildren().add(node);
      NodeHelper.setHgrow(node);
      try {
        ReflectionUIUtils.bind(node, "prefWidth", centerFixedAreaSection, "width");
      } catch (final Exception ex) {
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
    final IResponsiveSizing leftSpacerSize = areasSize.getSizeOf(0);
    final IResponsiveSizing leftSize = areasSize.getSizeOf(1);
    final IResponsiveSizing centerSize = areasSize.getSizeOf(2);
    final IResponsiveSizing rightSize = areasSize.getSizeOf(3);

    IResponsiveAware.doResize(leftSpacer, leftSpacerSize);
    IResponsiveAware.doResize(leftFixedAreaSection, leftSize);
    IResponsiveAware.doResize(centerFixedAreaSection, centerSize);
    IResponsiveAware.doResize(rightFixedAreaSection, rightSize);
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
