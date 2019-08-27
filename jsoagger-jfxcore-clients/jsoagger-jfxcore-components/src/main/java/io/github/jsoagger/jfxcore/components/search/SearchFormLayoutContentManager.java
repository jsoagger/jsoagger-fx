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

package io.github.jsoagger.jfxcore.components.search;


import java.net.URL;

import io.github.jsoagger.jfxcore.api.IResponsiveAreaSize;
import io.github.jsoagger.jfxcore.api.IResponsiveAware;
import io.github.jsoagger.jfxcore.api.IResponsiveSizing;
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManageable;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.AbstractViewLayoutManager;
import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 21 mars 2018
 */
public class SearchFormLayoutContentManager extends AbstractViewLayoutManager implements IViewLayoutManager {

  @FXML
  private Pane headerActions;
  @FXML
  private Pane centerSection;
  @FXML
  private Pane footerActions;
  @FXML
  private Pane searchFormLeftSection;
  @FXML
  private Pane searchFormCenterSection;
  @FXML
  private Pane searchFormSection;
  @FXML
  private Pane searchFormRightSection;
  @FXML
  private Pane editorStructureAreaSection;
  @FXML
  private ScrollPane searchFormContentSection;

  Node topNode;
  Node centerNode;
  Node bottomNode;
  Node pushedContent = null;


  /**
   * Default Constructor
   */
  public SearchFormLayoutContentManager() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void layout(IViewLayoutManageable layoutManageable) {
    super.layout(layoutManageable);

    editorStructureAreaSection.managedProperty().bind(editorStructureAreaSection.visibleProperty());
    searchFormSection.managedProperty().bind(searchFormSection.visibleProperty());

    headerActions.managedProperty().bind(headerActions.visibleProperty());
    centerSection.managedProperty().bind(centerSection.visibleProperty());
    footerActions.managedProperty().bind(footerActions.visibleProperty());

    searchFormLeftSection.managedProperty().bind(searchFormLeftSection.visibleProperty());
    searchFormRightSection.managedProperty().bind(searchFormRightSection.visibleProperty());
    centerSection.managedProperty().bind(centerSection.visibleProperty());

    NodeHelper.setStyleClass(headerActions, layoutManageable.getConfiguration(), "headerActionsStyleClass", false);
    NodeHelper.setStyleClass(centerSection, layoutManageable.getConfiguration(), "centerSectionStyleClass", false);
    NodeHelper.setStyleClass(footerActions, layoutManageable.getConfiguration(), "footerSectionStyleClass", false);

    NodeHelper.setStyleClass(searchFormLeftSection, layoutManageable.getConfiguration(), "leftSectionAreaStyleClass", false);
    NodeHelper.setStyleClass(searchFormRightSection, layoutManageable.getConfiguration(), "rightSectionAreaStyleClass", false);

    topNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.TOP);
    centerNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.CENTER);
    bottomNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.BOTTOM);
    layoutInitialNodes();

    searchFormContentSection.setOnScrollStarted(s -> AbstractApplicationRunner.setApplicationScrolling(true));
    searchFormContentSection.setOnScrollFinished(s-> AbstractApplicationRunner.setApplicationScrolling(false));
  }


  private void layoutInitialNodes() {
    editorStructureAreaSection.setVisible(false);
    searchFormSection.setVisible(true);

    if (topNode != null) {
      setTop(topNode);
    } else {
      headerActions.setVisible(false);
    }

    if (centerNode != null) {
      setCenter(centerNode);
    }

    if (bottomNode != null) {
      setBottom(bottomNode);
    } else {
      footerActions.setVisible(false);
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void pushContent(Node node) {
    try {
      pushedContent = node;
      super.pushContent(node);
      NodeHelper.setVgrow(node);

      editorStructureAreaSection.setVisible(true);
      editorStructureAreaSection.getChildren().clear();
      editorStructureAreaSection.getChildren().add(node);

      centerSection.setVisible(false);
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void popContent() {
    if (pushedContent != null) {
      super.popContent();
      editorStructureAreaSection.setVisible(false);
      centerSection.setVisible(true);

      pushedContent = null;
    }
  }


  private void setBottom(Node bottomNode) {
    footerActions.getChildren().clear();
    footerActions.getChildren().add(bottomNode);
    NodeHelper.setHgrow(bottomNode);
  }


  private void setCenter(Node centerNode) {
    centerSection.getChildren().clear();
    centerSection.getChildren().add(centerNode);
    centerSection.setVisible(true);
    NodeHelper.setHgrow(centerNode);
    try {
      ReflectionUIUtils.bind(centerNode, "prefWidth", centerSection, "width");
    } catch (final Exception ex) {
      ex.printStackTrace();
    }
  }


  private void setTop(Node topNode) {
    headerActions.getChildren().clear();
    headerActions.getChildren().add(topNode);
    //NodeHelper.setHgrow(topNode);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return rootPane;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public URL getFXMLLocation() {
    return SearchFormLayoutContentManager.class.getResource("SearchFormLayoutContentManager.fxml");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void applyContentMatrix(IResponsiveAreaSize areasSize) {
    // why pop?
    // i commented it because when forward editing, matrix is applied
    // so the pushed content is not displayed because it is immediatly poped
    // popContent();

    final IResponsiveSizing leftSize = areasSize.getSizeOf(0);
    final IResponsiveSizing centerSize = areasSize.getSizeOf(1);
    final IResponsiveSizing rightSize = areasSize.getSizeOf(2);

    IResponsiveAware.doResize(searchFormLeftSection, leftSize);
    IResponsiveAware.doResize(searchFormCenterSection, centerSize);
    IResponsiveAware.doResize(searchFormRightSection, rightSize);
  }
}
