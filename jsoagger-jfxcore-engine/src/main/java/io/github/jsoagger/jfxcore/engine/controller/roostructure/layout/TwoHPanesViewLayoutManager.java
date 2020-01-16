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
import io.github.jsoagger.jfxcore.api.IMinimizable;
import io.github.jsoagger.jfxcore.api.IResponsiveAreaSize;
import io.github.jsoagger.jfxcore.api.IResponsiveAware;
import io.github.jsoagger.jfxcore.api.IResponsiveSizing;
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManageable;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class TwoHPanesViewLayoutManager extends AbstractViewLayoutManager implements IViewLayoutManager {

  private static final String FXML_LOCATION = "TwoHPanesViewLayout.fxml";

  /*-----------------------------------------------------------------------------
  | PRIVATE FIELDS
   *=============================================================================*/

  /*-----------------------------------------------------------------------------
  | FXML FIELDS
   *=============================================================================*/
  @FXML
  protected Pane leftStackPaneWrapper;
  @FXML
  protected ScrollPane leftScrollPane;
  @FXML
  protected Pane leftStackPane;

  @FXML
  protected Pane rightStackPaneWrapper;
  @FXML
  protected ScrollPane rightScrollPane;
  @FXML
  protected Pane rightStackPane;


  /*-----------------------------------------------------------------------------
  | CONSTRUCTORS
   *=============================================================================*/
  /**
   * Constructor
   */
  public TwoHPanesViewLayoutManager() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void layout(IViewLayoutManageable layoutManageable) {
    super.layout(layoutManageable);

    rightStackPaneWrapper.managedProperty().bind(rightStackPaneWrapper.visibleProperty());
    rightScrollPane.managedProperty().bind(rightScrollPane.visibleProperty());
    rightStackPaneWrapper.minWidthProperty().bind(rightStackPaneWrapper.prefWidthProperty());
    rightStackPaneWrapper.maxWidthProperty().bind(rightStackPaneWrapper.prefWidthProperty());

    leftStackPaneWrapper.maxWidthProperty().bind(leftStackPaneWrapper.prefWidthProperty());
    leftStackPane.minWidthProperty().bind(leftStackPane.prefWidthProperty());
    leftStackPane.maxWidthProperty().bind(leftStackPane.prefWidthProperty());

    NodeHelper.setStyleClass(leftStackPane, layoutManageable.getConfiguration(), "leftSectionAreaStyleClass", false);
    NodeHelper.setStyleClass(rightStackPaneWrapper, layoutManageable.getConfiguration(), "rightSectionAreaStyleClass", false);

    final Node leftNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.LEFT);
    final Node rightNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.RIGHT);

    if (leftNode != null) {
      setLeft(leftNode);
    }

    if (rightNode != null) {
      setRight(rightNode);
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public URL getFXMLLocation() {
    return TwoHPanesViewLayoutManager.class.getResource(FXML_LOCATION);
  }

  /**
   * Keep track because impossible minimize them inside layout!
   */
  private final List<IMinimizable> leftMinimizables = new ArrayList<IMinimizable>();


  /**
   * @param processedView
   */
  private void setLeft(Node processedView) {
    _collectLeftMinimizable(processedView);
    Platform.runLater(() -> {
      leftStackPane.getChildren().clear();
      leftStackPane.getChildren().add(processedView);
    });
  }


  private void _collectLeftMinimizable(Node processedView) {
    if (processedView instanceof Pane) {
      for (final Node node : ((Pane) processedView).getChildren()) {
        if (node instanceof IMinimizable) {
          leftMinimizables.add((IMinimizable) node);
        }

        if (node instanceof Pane) {
          _collectLeftMinimizable(node);
        }
      }
    }
  }


  /**
   * @param processedView
   */
  private void setRight(Node processedView) {
    Platform.runLater(() -> {
      processedView.setOpacity(1);
      rightStackPane.getChildren().clear();
      rightStackPane.getChildren().add(processedView);
    });
  }


  /*-----------------------------------------------------------------------------
  | PUBLIC METHODS
   *=============================================================================*/
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
    final IResponsiveSizing rightSize = areasSize.getSizeOf(1);

    IResponsiveAware.resize(leftStackPaneWrapper, leftSize);
    IResponsiveAware.resize(rightStackPaneWrapper, rightSize);

    // handle nested minimizable
    if (!leftMinimizables.isEmpty()) {
      for (final IMinimizable minimizable : leftMinimizables) {
        if (leftSize.isToMinimize())
          minimizable.minimize();
        else
          minimizable.maximize();
      }
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return rootPane;
  }
}
