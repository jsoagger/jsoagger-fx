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
import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 11 févr. 2018
 */
public class FullTreeContentLayoutManager extends AbstractViewLayoutManager implements IViewLayoutManager {

  @FXML
  private Pane fullTreeLeftFixedAreaSection;

  @FXML
  private Pane fullTreeRightFixedAreaSection;

  @FXML
  private Pane centerFixedAreaSection;

  @FXML
  private Pane headerAreaSection;

  @FXML
  private Pane centerAreaSection;

  @FXML
  private Pane footerAreaSection;


  /**
   * @{inheritedDoc}
   */
  @Override
  public void layout(IViewLayoutManageable layoutManageable) {
    super.layout(layoutManageable);

    fullTreeLeftFixedAreaSection.managedProperty().bind(fullTreeLeftFixedAreaSection.visibleProperty());
    fullTreeLeftFixedAreaSection.maxWidthProperty().bind(fullTreeLeftFixedAreaSection.prefWidthProperty());
    fullTreeLeftFixedAreaSection.minWidthProperty().bind(fullTreeLeftFixedAreaSection.prefWidthProperty());

    fullTreeRightFixedAreaSection.managedProperty().bind(fullTreeRightFixedAreaSection.visibleProperty());
    fullTreeRightFixedAreaSection.maxWidthProperty().bind(fullTreeRightFixedAreaSection.prefWidthProperty());

    centerFixedAreaSection.minWidthProperty().bind(centerFixedAreaSection.maxWidthProperty());

    NodeHelper.setStyleClass(fullTreeLeftFixedAreaSection, layoutManageable.getConfiguration(), "leftSectionAreaStyleClass", false);
    NodeHelper.setStyleClass(centerFixedAreaSection, layoutManageable.getConfiguration(), "centerSectionAreaStyleClass", false);
    NodeHelper.setStyleClass(fullTreeRightFixedAreaSection, layoutManageable.getConfiguration(), "rightSectionAreaStyleClass", false);

    Node topNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.TOP);
    Node centerNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.CENTER);
    Node bottomNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.BOTTOM);

    if (topNode != null) {
      setTop(topNode);
    }

    if (centerNode != null) {
      setCenter(centerNode);
    }

    if (bottomNode != null) {
      setBottom(bottomNode);
    }
  }


  /**
   *
   */
  private void setBottom(Node bottomNode) {
    this.footerAreaSection.getChildren().clear();
    this.footerAreaSection.getChildren().add(bottomNode);
    NodeHelper.setHgrow(bottomNode);
  }


  /**
   *
   */
  private void setCenter(Node centerNode) {
    this.centerAreaSection.getChildren().clear();
    this.centerAreaSection.getChildren().add(centerNode);
    NodeHelper.setHgrow(centerNode);
    try {
      ReflectionUIUtils.bind(centerNode, "prefWidth", centerAreaSection, "width");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }


  /**
   *
   */
  private void setTop(Node topNode) {
    this.headerAreaSection.getChildren().clear();
    this.headerAreaSection.getChildren().add(topNode);
    NodeHelper.setHgrow(topNode);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Pane getRootPane() {
    return super.getRootPane();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public URL getFXMLLocation() {
    return FullTreeContentLayoutManager.class.getResource("FullTreeContent.fxml");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void applyContentMatrix(IResponsiveAreaSize areasSize) {
    IResponsiveSizing leftSize = areasSize.getSizeOf(0);
    IResponsiveSizing centerSize = areasSize.getSizeOf(1);
    IResponsiveSizing rightSize = areasSize.getSizeOf(2);

    IResponsiveAware.doResize(fullTreeLeftFixedAreaSection, leftSize);
    IResponsiveAware.doResize(centerFixedAreaSection, centerSize);
    IResponsiveAware.doResize(fullTreeRightFixedAreaSection, rightSize);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return rootPane;
  }
}
