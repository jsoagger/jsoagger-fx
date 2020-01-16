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
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManageable;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

/**
 * Layout a {@link IViewLayoutManageable} inside a single view. The center section of the
 * {@link IViewLayoutManageable} is layed out inside a scrollpane.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ScrolledSinglePaneViewLayoutManager extends AbstractViewLayoutManager implements IViewLayoutManager {

  /*-----------------------------------------------------------------------------
  | PRIVATE FIELDS
   *=============================================================================*/

  /*-----------------------------------------------------------------------------
  | FXML FIELDS
   *=============================================================================*/
  @FXML
  protected Pane centerFixedAreaSection;

  @FXML
  protected ScrollPane mainScrollPane;


  /*-----------------------------------------------------------------------------
  | CONSTRUCTORS
   *=============================================================================*/
  /**
   * Constructor
   */
  public ScrolledSinglePaneViewLayoutManager() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void layout(IViewLayoutManageable layoutManageable) {
    super.layout(layoutManageable);

    centerFixedAreaSection.minWidthProperty().bind(centerFixedAreaSection.prefWidthProperty());
    centerFixedAreaSection.maxWidthProperty().bind(centerFixedAreaSection.prefWidthProperty());

    NodeHelper.setStyleClass(centerFixedAreaSection, layoutManageable.getConfiguration(), "centerSectionAreaStyleClass", false);

    Node centerSection = layoutManageable.getNodeOnPosition(ViewLayoutPosition.CENTER);
    if (centerSection != null) {
      setCenter(centerSection);
    }

    if (layoutManageable.getResponsiveMatrix() != null) {
      responsiveMatrix = layoutManageable.getResponsiveMatrix();
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public URL getFXMLLocation() {
    return ScrolledSinglePaneViewLayoutManager.class.getResource("ScrolledSinglePaneViewLayout.fxml");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return rootPane;
  }


  public void setCenter(Node node) {
    Platform.runLater(() -> {
      node.setCache(true);
      node.setCacheHint(CacheHint.SPEED);
      centerFixedAreaSection.getChildren().clear();
      centerFixedAreaSection.getChildren().add(node);
    });
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  protected void finalize() throws Throwable {
    super.finalize();
    centerFixedAreaSection.minWidthProperty().unbind();
    centerFixedAreaSection.maxWidthProperty().unbind();
    mainScrollPane = null;
    rootPane = null;
    responsiveMatrix = null;
    layoutManageable = null;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void applyContentMatrix(IResponsiveAreaSize areasSize) {}
}
