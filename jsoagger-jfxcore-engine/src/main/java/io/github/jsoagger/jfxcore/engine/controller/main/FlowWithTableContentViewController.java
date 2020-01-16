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


import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;
import io.github.jsoagger.jfxcore.engine.events.CoreEvent;
import io.github.jsoagger.jfxcore.engine.events.GenericEvent;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 4 févr. 2018
 */
public class FlowWithTableContentViewController extends StandardViewController {

  protected StandardViewController leftPaneController;
  protected StandardViewController tableViewController;
  protected StandardViewController centerViewController;

  protected String rightSectionView;
  protected String centerSectionView;

  protected StackPane leftPane = new StackPane();
  protected StackPane rightPane = new StackPane();
  protected StackPane centerPane = new StackPane();


  /**
   * Default Constructor
   */
  public FlowWithTableContentViewController() {
    super();
    registerListener(CoreEvent.ElementRemovedFromTableEvent);
    registerListener(CoreEvent.LinkCreatedEvent);
    registerListener(CoreEvent.LinkDeletedEvent);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public synchronized <T extends GenericEvent> void handle(T e) {
    super.handle(e);
    if (e.isA(CoreEvent.LinkDeletedEvent)) {
      // tableViewController.refreshDatas();
    } else if (e.isA(CoreEvent.LinkCreatedEvent)) {
      //      tableViewController.refreshDatas();
    } else if (e.isA(CoreEvent.ElementRemovedFromTableEvent) && e.getSourceController().equals(tableViewController)) {
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
    buildPanes();
  }


  protected void buildPanes() {
    doBuildLeftPane();
    doBuildRightPane();
    doBuildCenterPane();
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
      try {
        leftPaneController = StandardViewUtils.forId(getRootStructure(), structureContentController, leftSectionView);
        leftPaneController.setParent(this);
        //NodeHelper.styleClassSetAll(leftPane, getRootComponent(), "leftSectionAreaStyleClass", "");
        final String location = leftPaneController.getRootComponent().getPropertyValue("location");
        if (StringUtils.isNotBlank(location)) {

        }

        if(leftPaneController instanceof FullTableStructureController) {
          leftPaneController.getStructureContent().setCurrentEditingTableStructure(leftPaneController);
        }

        final ChangeListener<OperationData> cl = (ChangeListener<OperationData>) (observable, oldValue, newValue) -> {
          final OperationData selectedModel = newValue;
          leftPaneSelectedElementChange(selectedModel);
        };

        // update model of right pane when left pane
        // selection is modified.
        // do not change the structure content model reference!!
        leftPaneController.selectedElementProperty().addListener(cl);
        Platform.runLater(() -> {
          leftPane.getChildren().add(leftPaneController.processedView());
        });
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  protected  void leftPaneSelectedElementChange(OperationData selectedModel) {
    if (selectedModel != null) {
      final SingleResult result = new SingleResult();
      result.setData(selectedModel);
      tableViewController.setModel(result);
    }
  }


  public void doBuildRightPane() {
    try {
      rightSectionView = getRootComponent().getPropertyValue("rightSectionView");
      if (StringUtils.isNotBlank(rightSectionView)) {

        tableViewController = StandardViewUtils.forId(getRootStructure(), structureContentController, rightSectionView);
        NodeHelper.styleClassSetAll(rightPane, getRootComponent(), "rightSectionAreaStyleClass", "");

        if(tableViewController instanceof FullTableStructureController) {
          tableViewController.getStructureContent().setCurrentEditingTableStructure(tableViewController);
        }

        tableViewController.setParent(this);

        // tableViewController.forceLoadFirstPage();
        Platform.runLater(() -> {
          final Node rightSectionNode = tableViewController.processedView();
          rightSectionNode.setCache(true);
          rightSectionNode.setCacheHint(CacheHint.SPEED);
          rightPane.getChildren().clear();
          rightPane.getChildren().add(rightSectionNode);
        });
      }
    } catch (final Exception ex) {
      ex.printStackTrace();
    }
  }


  public void doBuildCenterPane() {
    try {
      centerSectionView = getRootComponent().getPropertyValue("centerSectionView");
      if (StringUtils.isNotBlank(centerSectionView)) {

        centerViewController = StandardViewUtils.forId(getRootStructure(), structureContentController, centerSectionView);
        NodeHelper.styleClassSetAll(rightPane, getRootComponent(), "centerSectionAreaStyleClass", "");

        centerViewController.setParent(this);

        Platform.runLater(() -> {
          final Node centerSectionNode = centerViewController.processedView();
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


  /**
   * @{inheritedDoc}
   */
  @Override
  public IJSoaggerController getController() {
    return this;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public VLViewComponentXML getConfiguration() {
    return getRootComponent();
  }
}
