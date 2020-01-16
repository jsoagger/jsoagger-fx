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
import io.github.jsoagger.jfxcore.engine.components.tablestructure.tree.LazyTreeItem;
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
public class TreeWithContentViewController extends StandardViewController {

  FullTreeViewController leftSection;
  FullTableViewController righSection;
  String rightSectionView;

  StackPane leftPane = new StackPane();
  StackPane rightPane = new StackPane();


  /**
   * Default Constructor
   */
  public TreeWithContentViewController() {
    super();
    registerListener(CoreEvent.FolderedCreatedEvent);
    registerListener(CoreEvent.PartCreatedEvent);
    registerListener(CoreEvent.LinkDeletedEvent);
    registerListener(CoreEvent.LinkCreatedEvent);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public synchronized <T extends GenericEvent> void handle(T e) {
    super.handle(e);
    if (e.isA(CoreEvent.FolderedCreatedEvent) || e.isA(CoreEvent.PartCreatedEvent)) {
      if (righSection != null) {
        righSection.refreshDatas();
      }
    } else if (e.isA(CoreEvent.LinkDeletedEvent)) {
      final LazyTreeItem item = leftSection.getLastSelectedTreeItem();
      if (item != null) {
        item.reloadChildren();
      }
    } else if (e.isA(CoreEvent.LinkCreatedEvent)) {
      final LazyTreeItem item = leftSection.getLastSelectedTreeItem();
      if (item != null) {
        item.reloadChildren();
      }
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  protected void process() {
    super.process();

    final String leftSectionView = getRootComponent().getPropertyValue("leftSectionView");
    if (StringUtils.isNotBlank(leftSectionView)) {
      leftSection = (FullTreeViewController) StandardViewUtils.forId(getRootStructure(), structureContentController, leftSectionView);

      final String location = leftSection.getRootComponent().getPropertyValue("location");
      if (StringUtils.isNotBlank(location)) {

      }

      leftSection.selectedElementProperty().addListener((ChangeListener<OperationData>) (observable, oldValue, newValue) -> {
        final OperationData selectedModel = newValue;
        if (selectedModel != null) {
          getStructureContent().setFormModelData(selectedModel);
          getStructureContent().setForModelId((String) selectedModel.getAttributes().get("fullId"));

          final SingleResult result = new SingleResult();
          result.setData(selectedModel);
          righSection.setModel(result);
          righSection.forceLoadFirstPage();
        }
      });

      leftPane.getChildren().add(leftSection.processedView());
    }

    rightSectionView = getRootComponent().getPropertyValue("rightSectionView");
    doBuildRightPane();

    NodeHelper.styleClassSetAll(leftPane, getRootComponent(), "leftSectionAreaStyleClass", "");
    NodeHelper.styleClassSetAll(rightPane, getRootComponent(), "rightSectionAreaStyleClass", "");
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

    if (position == ViewLayoutPosition.TOP) {
      return righSection.getNodeOnPosition(ViewLayoutPosition.TOP);
    }

    return super.getNodeOnPosition(position);
  }


  public void doBuildRightPane() {
    try {
      righSection = (FullTableViewController) StandardViewUtils.forId(getRootStructure(), structureContentController, rightSectionView);
      Platform.runLater(() -> {
        final Node rightSectionNode = righSection.processedView();
        rightSectionNode.setCache(true);
        rightSectionNode.setCacheHint(CacheHint.SPEED);
        rightPane.getChildren().clear();
        rightPane.getChildren().add(rightSectionNode);
      });
    } catch (final Exception ex) {
      ex.printStackTrace();
    }
  }


  private void setProcessing() {
    Platform.runLater(() -> {
      rightPane.getChildren().clear();
      rightPane.getChildren().add(NodeHelper.getProcessingIndicator());
    });
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
