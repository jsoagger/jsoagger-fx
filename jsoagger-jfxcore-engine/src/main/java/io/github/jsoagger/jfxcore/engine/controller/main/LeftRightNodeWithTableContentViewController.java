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

package io.github.jsoagger.jfxcore.engine.controller.main;



import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;
import io.github.jsoagger.jfxcore.engine.events.ElementRemovedFromTableEvent;
import io.github.jsoagger.jfxcore.engine.events.GenericEvent;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * Left pane is master, when left model is updated, center and right should be updated to.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 4 févr. 2018
 */
public class LeftRightNodeWithTableContentViewController extends StandardViewController {

  StandardViewController leftPaneController;
  StandardViewController rightPaneController;
  FullTableViewController tableViewController;

  String centerSectionView;
  String rightSectionView;

  StackPane leftPane = new StackPane();
  StackPane centerPane = new StackPane();
  StackPane rightPane = new StackPane();


  /**
   * Default Constructor
   */
  public LeftRightNodeWithTableContentViewController() {
    super();

    registerListener(new ElementRemovedFromTableEvent());
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public synchronized <T extends GenericEvent> void handle(T e) {
    super.handle(e);
    if (e instanceof ElementRemovedFromTableEvent && e.getSourceController().equals(tableViewController)) {
      leftPaneController.refreshDatas();
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  protected void process() {
    super.process();

    if (getRootStructure().relativeToProperty().get() != null) {
      if (getStructureContentController().getFormModelData() == null) {
        getStructureContentController().setFormModelData(getRootStructure().relativeToProperty().get());
      }
    }

    doBuildLeftPane();
    doBuildCenterPane();
    doBuildRightPane();

    // TO UPDATE RIGHT PANE, LEFT MUST SET THE NEW MODEL TO
    // STRUCTURE CONTENT FORM MODEL DATA,
    // THE RIGHT PANE WILL LISTEN TO CHANGE ON THIS OBJECT TO UPDATE ITSELF
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getNodeOnPosition(ViewLayoutPosition position) {
    if (position == ViewLayoutPosition.LEFT) {
      return leftPane;
    }

    if (position == ViewLayoutPosition.RIGHT) {
      return rightPane;
    }

    if (position == ViewLayoutPosition.CENTER) {
      return centerPane;
    }

    if (position == ViewLayoutPosition.TOP) {
      return tableViewController.getNodeOnPosition(ViewLayoutPosition.TOP);
    }

    return super.getNodeOnPosition(position);
  }


  public void doBuildLeftPane() {
    final String leftSectionView = getRootComponent().getPropertyValue("leftSectionView");
    if (StringUtils.isNotBlank(leftSectionView)) {
      leftPaneController = StandardViewUtils.forId(getRootStructure(), structureContentController, leftSectionView);
      leftPaneController.setParent(this);
      NodeHelper.styleClassSetAll(leftPane, getRootComponent(), "leftSectionAreaStyleClass", "");
      final String location = leftPaneController.getRootComponent().getPropertyValue("location");
      if (StringUtils.isNotBlank(location)) {

      }

      final ChangeListener<OperationData> cl = (ChangeListener<OperationData>) (observable, oldValue, newValue) -> {
        final OperationData selectedModel = newValue;
        if (selectedModel != null) {
          getStructureContent().setFormModelData(selectedModel);
          getStructureContent().setForModelId((String) selectedModel.getAttributes().get("fullId"));

          final SingleResult result = new SingleResult();
          result.setData(selectedModel);
          tableViewController.setModel(result);
        }
      };

      leftPaneController.selectedElementProperty().addListener(cl);
      Platform.runLater(() -> {
        leftPane.getChildren().add(leftPaneController.processedView());
      });
    }
  }


  public void doBuildCenterPane() {
    try {
      centerSectionView = getRootComponent().getPropertyValue("centerSectionView");
      if (StringUtils.isNotBlank(centerSectionView)) {

        tableViewController = (FullTableViewController) StandardViewUtils.forId(getRootStructure(), structureContentController, centerSectionView);
        NodeHelper.styleClassSetAll(centerPane, getRootComponent(), "centerSectionAreaStyleClass", "");
        tableViewController.getStructureContent().setCurrentEditingTableStructure(tableViewController);
        tableViewController.setParent(this);

        tableViewController.forceLoadFirstPage();
        Platform.runLater(() -> {
          final Node centerSectionNode = tableViewController.processedView();
          centerSectionNode.setCache(true);
          centerSectionNode.setCacheHint(CacheHint.SPEED);
          centerPane.getChildren().clear();
          centerPane.getChildren().add(centerSectionNode);
        });
      }
    } catch (final Exception ex) {
      ex.printStackTrace();
    }
  }


  public void doBuildRightPane() {
    final String rightSectionView = getRootComponent().getPropertyValue("rightSectionView");
    if (StringUtils.isNotBlank(rightSectionView)) {
      rightPaneController = StandardViewUtils.forId(getRootStructure(), structureContentController, rightSectionView);
      rightPaneController.setParent(this);
      NodeHelper.styleClassSetAll(rightPane, getRootComponent(), "rightSectionAreaStyleClass", "");
      final String location = rightPaneController.getRootComponent().getPropertyValue("location");
      if (StringUtils.isNotBlank(location)) {

      }

      Platform.runLater(() -> {
        rightPane.getChildren().add(rightPaneController.processedView());
      });
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public IJSoaggerController getController() {
    return (IJSoaggerController) this;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public VLViewComponentXML getConfiguration() {
    return getRootComponent();
  }
}
