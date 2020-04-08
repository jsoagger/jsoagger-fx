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
import java.util.List;

import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IResponsiveAreaSize;
import io.github.jsoagger.jfxcore.api.IResponsiveAware;
import io.github.jsoagger.jfxcore.api.IResponsiveSizing;
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.api.components.annotation.GraalComponent;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManageable;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;
import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 11 févr. 2018
 */
@GraalComponent
public class FullTableStructureContentLayoutManager extends AbstractViewLayoutManager
    implements IViewLayoutManager {

  @FXML
  protected Pane leftFixedAreaSection;

  @FXML
  protected Pane centerFixedAreaSection;

  @FXML
  protected Pane rightFixedAreaSection;

  @FXML
  protected Pane mainContentAnchorContent;

  @FXML
  protected ScrollPane mainScrollPane;

  @FXML
  protected Pane rootAnchorPane;

  @FXML
  protected Pane headerAreaSection;

  @FXML
  protected Pane centerAreaSection;

  @FXML
  protected BorderPane tableStructureAreaSection;

  @FXML
  protected ButtonBase editorStructureMaximizeButton;

  @FXML
  protected Pane editorStructureMinimizedPane;

  @FXML
  protected Pane footerAreaSection;

  @FXML
  protected HBox internalWrapper;

  VBox mainScrollPaneContent;
  Node topNode = null;
  Node centerNode = null;
  Node bottomNode = null;
  StackPane externalWrapper = null;
  Label editorStructureMinimizedPaneLabel;

  @FXML
  Pane editorStructureAreaSection;
  Node pushedContent = null;


  /**
   * @{inheritedDoc}
   */
  @Override
  public void layout(IViewLayoutManageable layoutManageable) {
    super.layout(layoutManageable);

    if (getRootPane() == null) {
      rootPane = new StackPane();
      rootAnchorPane = new AnchorPane();
      editorStructureMinimizedPaneLabel = new Label();
      editorStructureMaximizeButton = new Button();
      mainContentAnchorContent = new StackPane();
      internalWrapper = new HBox();
      leftFixedAreaSection = new StackPane();
      tableStructureAreaSection = new BorderPane();
      headerAreaSection = new StackPane();
      mainScrollPane = new ScrollPane();
      mainScrollPaneContent = new VBox();
      externalWrapper = new StackPane();
      centerFixedAreaSection = new VBox();
      centerAreaSection = new StackPane();
      footerAreaSection = new StackPane();
      rightFixedAreaSection = new StackPane();
      editorStructureAreaSection = new VBox();
      editorStructureMinimizedPane = new HBox();

      rootPane.getChildren().add(rootAnchorPane);
      rootAnchorPane.getChildren().addAll(mainContentAnchorContent, editorStructureAreaSection,
          editorStructureMinimizedPane);
      AnchorPane.setTopAnchor(mainContentAnchorContent, 0.0);
      AnchorPane.setLeftAnchor(mainContentAnchorContent, 0.0);
      AnchorPane.setRightAnchor(mainContentAnchorContent, 0.0);
      AnchorPane.setBottomAnchor(mainContentAnchorContent, 0.0);

      AnchorPane.setTopAnchor(editorStructureAreaSection, 80.0);
      AnchorPane.setLeftAnchor(editorStructureAreaSection, 0.0);
      AnchorPane.setRightAnchor(editorStructureAreaSection, 0.0);
      editorStructureAreaSection.setVisible(false);

      AnchorPane.setRightAnchor(editorStructureMinimizedPane, 20.0);
      AnchorPane.setBottomAnchor(editorStructureMinimizedPane, 0.0);
      editorStructureMinimizedPane.setVisible(false);
      editorStructureMinimizedPane.setManaged(false);
      editorStructureMinimizedPane.getStyleClass().add("ep-search-result-minimized-pane");

      mainContentAnchorContent.getChildren().add(internalWrapper);
      internalWrapper.setAlignment(Pos.TOP_CENTER);
      internalWrapper.getChildren().addAll(leftFixedAreaSection, tableStructureAreaSection,
          rightFixedAreaSection);

      NodeHelper.setHVGrow(tableStructureAreaSection);
      tableStructureAreaSection.setTop(headerAreaSection);
      tableStructureAreaSection.setCenter(mainScrollPane);

      headerAreaSection.setStyle(
          "-fx-border-width:0 0 1 0;-fx-border-color:-internal-border-color;-fx-alignment:CENTER");

      mainScrollPane.setFitToHeight(true);
      mainScrollPane.setFitToWidth(true);
      mainScrollPane.setContent(mainScrollPaneContent);
      mainScrollPaneContent.getChildren().addAll(externalWrapper, footerAreaSection);
      NodeHelper.setVgrow(externalWrapper);
      externalWrapper.setAlignment(Pos.CENTER);

      externalWrapper.getChildren().addAll(centerFixedAreaSection);
      centerFixedAreaSection.getChildren().add(centerAreaSection);
      NodeHelper.setVgrow(centerFixedAreaSection, centerAreaSection);

      footerAreaSection.getStyleClass().add("ep-full-table-structure-footer");
      editorStructureMinimizedPane.getStyleClass().add("ep-search-result-minimized-pane");
      editorStructureMinimizedPaneLabel.getStyleClass()
          .add("ep-search-result-minimized-pane-label");
      editorStructureMinimizedPane.getChildren().addAll(editorStructureMinimizedPaneLabel,
          editorStructureMaximizeButton);

      postLayout();
    }

    editorStructureAreaSection.managedProperty().bind(editorStructureAreaSection.visibleProperty());
    tableStructureAreaSection.managedProperty().bind(tableStructureAreaSection.visibleProperty());

    leftFixedAreaSection.managedProperty().bind(leftFixedAreaSection.visibleProperty());
    leftFixedAreaSection.maxWidthProperty().bind(leftFixedAreaSection.prefWidthProperty());
    leftFixedAreaSection.minWidthProperty().bind(leftFixedAreaSection.prefWidthProperty());

    rightFixedAreaSection.managedProperty().bind(rightFixedAreaSection.visibleProperty());
    rightFixedAreaSection.maxWidthProperty().bind(rightFixedAreaSection.prefWidthProperty());

    centerFixedAreaSection.minWidthProperty().bind(centerFixedAreaSection.maxWidthProperty());
    centerFixedAreaSection.prefWidthProperty().bind(centerFixedAreaSection.maxWidthProperty());

    NodeHelper.setStyleClass(leftFixedAreaSection, layoutManageable.getConfiguration(),
        "leftSectionAreaStyleClass", false);
    NodeHelper.setStyleClass(centerFixedAreaSection, layoutManageable.getConfiguration(),
        "centerSectionAreaStyleClass", false);
    NodeHelper.setStyleClass(rightFixedAreaSection, layoutManageable.getConfiguration(),
        "rightSectionAreaStyleClass", false);
    NodeHelper.setStyleClass(tableStructureAreaSection, layoutManageable.getConfiguration(),
        "tableStructureAreaStyleClass", false);
    NodeHelper.setStyleClass(footerAreaSection, layoutManageable.getConfiguration(),
        "tableFooterAreaStyleClass", false);

    NodeHelper.setStyleClass(internalWrapper, layoutManageable.getConfiguration(),
        "layoutInternalWrapperStyleClass", false);

    topNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.TOP);
    centerNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.CENTER);
    bottomNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.BOTTOM);
    layoutInitialNodes();

    IconUtils.setFontIcon("fa-angle-double-up:22", editorStructureMaximizeButton);
    editorStructureMaximizeButton.addEventFilter(ActionEvent.ACTION, e -> restorePushedContent());
    editorStructureMaximizeButton.getStyleClass().add("button-transparent");
  }

  @Override
  public void setDefaultActions(List<IBuildable> comps) {
    super.setDefaultActions(comps);

  }

  private boolean isEditingNode = false;


  /**
   * @{inheritedDoc}
   */
  @Override
  public void popContent() {
    if (isEditingNode) {
      super.popContent();
      editorStructureAreaSection.setVisible(false);
      isEditingNode = false;
    }
  }


  @Override
  public void minimizePushedContent() {
    super.minimizePushedContent();
    editorStructureAreaSection.setVisible(false);
    isEditingNode = false;
    editorStructureMinimizedPane.setVisible(true);
    editorStructureMinimizedPane.setManaged(true);
  }


  @Override
  public void restorePushedContent() {
    super.restorePushedContent();
    editorStructureAreaSection.setVisible(true);
    isEditingNode = true;

    editorStructureMinimizedPane.setVisible(false);
    editorStructureMinimizedPane.setManaged(false);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void pushContent(Node node) {
    pushedContent = node;
    super.pushContent(node);

    NodeHelper.setHVGrow(node);
    editorStructureAreaSection.setVisible(true);
    editorStructureAreaSection.getChildren().clear();
    editorStructureAreaSection.getChildren().add(node);

    editorStructureMinimizedPane.setVisible(false);
    editorStructureMinimizedPane.setManaged(false);

    isEditingNode = true;
  }


  private void layoutInitialNodes() {
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
    footerAreaSection.getChildren().clear();
    footerAreaSection.getChildren().add(bottomNode);
    NodeHelper.setHgrow(bottomNode);
  }


  /**
   *
   */
  private void setCenter(Node centerNode) {
    centerAreaSection.getChildren().clear();
    centerAreaSection.getChildren().add(centerNode);
    NodeHelper.setHgrow(centerNode);
    try {
      ReflectionUIUtils.bind(centerNode, "prefWidth", centerAreaSection, "width");
    } catch (final Exception ex) {
      ex.printStackTrace();
    }
  }


  /**
   *
   */
  private void setTop(Node topNode) {
    headerAreaSection.getChildren().clear();
    headerAreaSection.getChildren().add(topNode);
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
    return FullTableStructureContentLayoutManager.class
        .getResource("FullTableStructureContent.fxml");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void applyContentMatrix(IResponsiveAreaSize areasSize) {
    popContent();

    final IResponsiveSizing leftSize = areasSize.getSizeOf(0);
    final IResponsiveSizing centerSize = areasSize.getSizeOf(1);
    final IResponsiveSizing rightSize = areasSize.getSizeOf(2);

    IResponsiveAware.doResize(leftFixedAreaSection, leftSize);
    IResponsiveAware.doResize(tableStructureAreaSection, centerSize);
    IResponsiveAware.doResize(rightFixedAreaSection, rightSize);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return rootPane;
  }
}
