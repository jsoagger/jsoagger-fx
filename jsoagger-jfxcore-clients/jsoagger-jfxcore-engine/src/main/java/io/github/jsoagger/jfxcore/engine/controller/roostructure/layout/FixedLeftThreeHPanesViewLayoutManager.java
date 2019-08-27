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
import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IResponsiveAreaSize;
import io.github.jsoagger.jfxcore.api.IResponsiveAware;
import io.github.jsoagger.jfxcore.api.IResponsiveSizing;
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManageable;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

/**
 * A layout where children are layed out in 3 horizontal panes. The center pane and the right pane
 * are scrolled. The left pane stays at its position all time.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class FixedLeftThreeHPanesViewLayoutManager extends AbstractViewLayoutManager implements IViewLayoutManager {

  private static final String FXML_LOCATION = "FixedLeftThreeHPanesViewLayout.fxml";

  /*-----------------------------------------------------------------------------
  | PRIVATE FIELDS
   *=============================================================================*/
  List<Node> pushedContent = new ArrayList<>();

  /*-----------------------------------------------------------------------------
  | FXML FIELDS
   *=============================================================================*/
  @FXML
  protected Pane leftFixedAreaSection;
  @FXML
  protected Pane centerFixedAreaSection;
  @FXML
  protected Pane rightFixedAreaSection;
  @FXML
  protected ScrollPane mainScrollPane;
  @FXML
  protected Pane editorStructureAreaSection;
  @FXML
  protected Pane contentWrapper;

  /*----------------------------------- ------------------------------------------
  | CONSTRUCTORS
   *=============================================================================*/
  /**
   * Constructor
   */
  public FixedLeftThreeHPanesViewLayoutManager() {
    super();
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public void layout(IViewLayoutManageable layoutManageable) {
    super.layout(layoutManageable);

    editorStructureAreaSection.managedProperty().bind(editorStructureAreaSection.visibleProperty());
    // contentWrapper.managedProperty().bind(contentWrapper.visibleProperty());

    leftFixedAreaSection.managedProperty().bind(leftFixedAreaSection.visibleProperty());
    leftFixedAreaSection.maxWidthProperty().bind(leftFixedAreaSection.prefWidthProperty());
    leftFixedAreaSection.minWidthProperty().bind(leftFixedAreaSection.prefWidthProperty());

    rightFixedAreaSection.managedProperty().bind(rightFixedAreaSection.visibleProperty());
    rightFixedAreaSection.maxWidthProperty().bind(rightFixedAreaSection.prefWidthProperty());

    centerFixedAreaSection.minWidthProperty().bind(centerFixedAreaSection.maxWidthProperty());

    NodeHelper.setStyleClass(getRootPane(), layoutManageable.getConfiguration(), "rootPaneStyleClass", true);
    NodeHelper.setStyleClass(leftFixedAreaSection, layoutManageable.getConfiguration(), "leftSectionAreaStyleClass", false);
    NodeHelper.setStyleClass(centerFixedAreaSection, layoutManageable.getConfiguration(), "centerSectionAreaStyleClass", false);
    NodeHelper.setStyleClass(rightFixedAreaSection, layoutManageable.getConfiguration(), "rightSectionAreaStyleClass", false);

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
    Platform.runLater(() -> {
      centerFixedAreaSection.getChildren().clear();
      centerFixedAreaSection.getChildren().add(node);
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
  public void pushContent(Node node) {
    pushedContent.add(node);
    super.pushContent(node);
    NodeHelper.setHVGrow(node);

    editorStructureAreaSection.setVisible(true);
    editorStructureAreaSection.getChildren().clear();
    editorStructureAreaSection.getChildren().add(node);
    contentWrapper.setVisible(false);
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public void popContent() {
    super.popContent();
    if (pushedContent.size() == 1) {
      editorStructureAreaSection.setVisible(false);
      contentWrapper.setVisible(true);
      pushedContent.clear();
    } else {
      if(pushedContent.size() > 1) {
        editorStructureAreaSection.getChildren().clear();
        pushedContent.remove(pushedContent.size() - 1);
        editorStructureAreaSection.getChildren().add(pushedContent.get(pushedContent.size() - 1));
      }
    }
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public URL getFXMLLocation() {
    return this.getClass().getResource(FXML_LOCATION);
  }

  /**
   * @return the pushedContent
   */
  public List<Node> getPushedContent() {
    return pushedContent;
  }
}
