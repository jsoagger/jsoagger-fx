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
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
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
public class FullFlowContentLayoutManager extends AbstractViewLayoutManager
    implements IViewLayoutManager {

  @FXML
  protected Pane rootAnchorPane;
  @FXML
  protected BorderPane rootBorderPane;
  @FXML
  protected HBox mainScrollPaneLayout;
  @FXML
  protected Pane headerAreaExternalSection;
  @FXML
  protected StackPane externalWrapper;
  @FXML
  protected Pane leftFixedAreaSection;
  @FXML
  protected Pane internalWrapperSection;
  @FXML
  protected VBox centerFixedAreaSection;

  @FXML
  protected Pane rightFixedAreaSection;

  @FXML
  protected Pane headerAreaSection;

  @FXML
  protected Pane centerAreaSection;

  @FXML
  protected Pane footerAreaSection;

  @FXML
  protected Pane floatingDefaultActionsWrapper;

  @FXML
  protected ScrollPane mainScrollPane;

  @FXML
  protected Pane tableStructureAreaSection;

  @FXML
  protected Pane internalWrapper;

  protected Node topNode;
  protected Node bottomNode;
  private boolean scroll = true;


  /**
   * Constructor
   */
  public FullFlowContentLayoutManager() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void layout(IViewLayoutManageable layoutManageable) {
    super.layout(layoutManageable);

    if (getRootPane() == null) {
      rootPane = new StackPane();
      internalWrapper = new HBox();
      rootAnchorPane = new AnchorPane();
      rootBorderPane = new BorderPane();
      leftFixedAreaSection = new StackPane();
      headerAreaExternalSection = new StackPane();
      headerAreaSection = new StackPane();
      mainScrollPane = new ScrollPane();
      mainScrollPaneLayout = new HBox();
      centerFixedAreaSection = new VBox();
      externalWrapper = new StackPane();
      internalWrapperSection = new VBox();
      centerAreaSection = new StackPane();
      footerAreaSection = new StackPane();
      rightFixedAreaSection = new StackPane();
      floatingDefaultActionsWrapper = new StackPane();

      rootPane.getChildren().add(rootAnchorPane);
      rootAnchorPane.getChildren().addAll(internalWrapper, floatingDefaultActionsWrapper);

      AnchorPane.setTopAnchor(internalWrapper, 0.0);
      AnchorPane.setRightAnchor(internalWrapper, 0.0);
      AnchorPane.setLeftAnchor(internalWrapper, 0.0);
      AnchorPane.setBottomAnchor(internalWrapper, 0.0);
      internalWrapper.getChildren().addAll(rootBorderPane);

      rootBorderPane.setLeft(leftFixedAreaSection);
      rootBorderPane.setTop(headerAreaExternalSection);
      rootBorderPane.setCenter(mainScrollPane);

      headerAreaExternalSection.getChildren().add(headerAreaSection);
      mainScrollPane.setContent(mainScrollPaneLayout);
      mainScrollPaneLayout.getChildren().addAll(centerFixedAreaSection, rightFixedAreaSection);
      centerFixedAreaSection.getChildren().addAll(externalWrapper, footerAreaSection);
      externalWrapper.getChildren().add(internalWrapperSection);
      internalWrapperSection.getChildren().add(centerAreaSection);

      headerAreaExternalSection.setVisible(false);
      headerAreaExternalSection.setManaged(false);

      mainScrollPane.setFitToHeight(true);
      mainScrollPane.setFitToWidth(true);
      mainScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
      mainScrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

      mainScrollPaneLayout.setAlignment(Pos.CENTER);
      NodeHelper.setHgrow(centerFixedAreaSection);
      centerFixedAreaSection.setSpacing(16);
      NodeHelper.setVgrow(externalWrapper, internalWrapperSection, centerAreaSection);
      externalWrapper.setAlignment(Pos.CENTER);

      footerAreaSection.getStyleClass().add("ep-full-table-structure-footer");
      floatingDefaultActionsWrapper.setVisible(false);
      floatingDefaultActionsWrapper.setManaged(false);
      NodeHelper.setHVGrow(rootBorderPane);
    }

    leftFixedAreaSection.managedProperty().bind(leftFixedAreaSection.visibleProperty());
    leftFixedAreaSection.maxWidthProperty().bind(leftFixedAreaSection.prefWidthProperty());
    leftFixedAreaSection.minWidthProperty().bind(leftFixedAreaSection.prefWidthProperty());

    rightFixedAreaSection.managedProperty().bind(rightFixedAreaSection.visibleProperty());
    rightFixedAreaSection.minWidthProperty().bind(rightFixedAreaSection.prefWidthProperty());
    rightFixedAreaSection.maxWidthProperty().bind(rightFixedAreaSection.prefWidthProperty());

    headerAreaSection.managedProperty().bind(headerAreaSection.visibleProperty());
    centerFixedAreaSection.minWidthProperty().bind(centerFixedAreaSection.prefWidthProperty());
    centerFixedAreaSection.maxWidthProperty().bind(centerFixedAreaSection.prefWidthProperty());

    NodeHelper.setStyleClass(tableStructureAreaSection, layoutManageable.getConfiguration(),
        "tableStructureAreaStyleClass", false);
    NodeHelper.setStyleClass(leftFixedAreaSection, layoutManageable.getConfiguration(),
        "leftSectionAreaStyleClass", false);
    NodeHelper.setStyleClass(centerFixedAreaSection, layoutManageable.getConfiguration(),
        "centerSectionAreaStyleClass", false);
    NodeHelper.setStyleClass(rightFixedAreaSection, layoutManageable.getConfiguration(),
        "rightSectionAreaStyleClass", false);
    NodeHelper.setStyleClass(footerAreaSection, layoutManageable.getConfiguration(),
        "footerAreaSectionStyleClass", false);
    // NodeHelper.styleClassAddAll(headerAreaSection, layoutManageable.getConfiguration(),
    // "tableHeaderAreaStyleClass", "ep-shadowed-table-header");
    NodeHelper.styleClassAddAll(headerAreaSection, layoutManageable.getConfiguration(),
        "tableHeaderAreaStyleClass");
    NodeHelper.setStyleClass(internalWrapper, layoutManageable.getConfiguration(),
        "layoutInternalWrapperStyleClass", false);

    if (mainScrollPane != null) {
      NodeHelper.setStyleClass(mainScrollPane, layoutManageable.getConfiguration(),
          "mainScrollPaneStyleClass", false);
    }

    topNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.TOP);
    final Node centerNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.CENTER);
    bottomNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.BOTTOM);

    if (topNode != null) {
      setTop(topNode);
    }

    if (centerNode != null) {
      setCenter(centerNode);
    }

    if (bottomNode != null) {
      setBottom(bottomNode);
    } else {
      footerAreaSection.setVisible(false);
      footerAreaSection.setManaged(false);
    }

    _doLayoutDefaultActions();
  }

  private List<IBuildable> defaultActions;


  /**
   * {@inheritDoc}
   */
  @Override
  public void setDefaultActions(List<IBuildable> defaultActions) {
    this.defaultActions = defaultActions;
  }


  public void _doLayoutDefaultActions() {
    if (defaultActions != null) {
      floatingDefaultActionsWrapper.setVisible(true);
      floatingDefaultActionsWrapper.setManaged(true);
      javafx.application.Platform.runLater(() -> {
        floatingDefaultActionsWrapper.getChildren().clear();
        if (defaultActions != null && defaultActions.size() > 0) {

          final ButtonBase btn = (ButtonBase) defaultActions.get(0).getDisplay();

          floatingDefaultActionsWrapper.getChildren().add(btn);
        }
      });
    } else {
      floatingDefaultActionsWrapper.setVisible(false);
      floatingDefaultActionsWrapper.setManaged(false);
    }
  }


  private void setBottom(Node bottomNode) {
    footerAreaSection.getChildren().clear();
    footerAreaSection.getChildren().add(bottomNode);
  }


  private void setCenter(Node centerNode) {
    centerAreaSection.getChildren().clear();
    centerAreaSection.getChildren().add(centerNode);
  }


  private void setTop(Node topNode) {
    if (headerAreaExternalSection != null) {
      headerAreaExternalSection.setVisible(true);
      headerAreaExternalSection.setManaged(true);
    }

    headerAreaSection.setVisible(true);
    headerAreaSection.getChildren().clear();
    headerAreaSection.getChildren().add(topNode);
  }


  @Override
  public void pushContent(Node node) {
    super.pushContent(node);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public URL getFXMLLocation() {
    if (scroll) {
      return FullFlowContentLayoutManager.class.getResource("FullFlowContentLayout.fxml");
    }
    return FullFlowContentLayoutManager.class.getResource("FullFlowNoScrollContentLayout.fxml");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void applyContentMatrix(IResponsiveAreaSize areasSize) {
    final IResponsiveSizing leftSize = areasSize.getSizeOf(0);
    final IResponsiveSizing centerSize = areasSize.getSizeOf(1);
    final IResponsiveSizing rightSize = areasSize.getSizeOf(2);

    IResponsiveAware.doResize(leftFixedAreaSection, leftSize);
    IResponsiveAware.doResize(mainScrollPane, centerSize);
    IResponsiveAware.doResize(rightFixedAreaSection, rightSize);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return rootPane;
  }


  public boolean isScroll() {
    return scroll;
  }


  public void setScroll(boolean scroll) {
    this.scroll = scroll;
  }


  /**
   * @return the mainScrollPane
   */
  public ScrollPane getMainScrollPane() {
    return mainScrollPane;
  }
}
