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

package io.github.jsoagger.jfxcore.engine.controller.detailsview;



import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 4 févr. 2018
 */
public class TableWithDetailsviewController extends StandardViewController {

  FullTableViewController leftSection;
  DetailsViewController righSection;

  StackPane leftPane = new StackPane();
  StackPane rightPane = new StackPane();

  /** this is the current selected element on the list view */
  private OperationData selectedModel;

  /** the right section vieww identifier, build every selected model change */
  String detailsView = null;


  /**
   * Default Constructor
   */
  public TableWithDetailsviewController() {
    super();
  }


  public OperationData getSelectedModel() {
    return selectedModel;
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

    return super.getNodeOnPosition(position);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  protected void process() {
    super.process();

    final String leftSectionView = getRootComponent().getPropertyValue("leftSectionView");
    if (StringUtils.isNotBlank(leftSectionView)) {
      leftSection = (FullTableViewController) StandardViewUtils.forId(getRootStructure(), structureContentController, leftSectionView);

      final String location = leftSection.getRootComponent().getPropertyValue("location");
      if (StringUtils.isNotBlank(location)) {
        final String loc = leftSection.getLocalised(location);

      }

      leftSection.selectedElementProperty().addListener((ChangeListener<OperationData>) (observable, oldValue, newValue) -> {
        selectedModel = newValue;
        if (selectedModel != null) {
          getStructureContent().setFormModelData(selectedModel);
          getStructureContent().setForModelId((String) selectedModel.getAttributes().get("fullId"));
          loadDetailsTask();
        }
      });

      leftPane.getChildren().add(leftSection.processedView());
      leftSection.selectFirstItem();
    }

    detailsView = getRootComponent().getPropertyValue("rightSectionView");

    NodeHelper.styleClassSetAll(leftPane, getRootComponent(), "leftSectionAreaStyleClass", "");
    NodeHelper.styleClassSetAll(rightPane, getRootComponent(), "rightSectionAreaStyleClass", "");
  }


  private void loadDetailsTask() {
    final Task<Void> task = new Task<Void>() {

      @Override
      protected Void call() throws Exception {
        loadDetailsView();
        return null;
      }


      /**
       * @{inheritedDoc}
       */
      @Override
      protected void running() {
        super.running();
        setProcessing();
      }
    };

    final Thread thread = new Thread(task);
    thread.setDaemon(true);
    thread.start();
  }


  public void loadDetailsView() {

    try {
      final StandardViewController righSection = StandardViewUtils.forId(getRootStructure(), structureContentController, detailsView);
      final String location = righSection.getRootComponent().getPropertyValue("location");
      if (StringUtils.isNotBlank(location)) {
        final String loc = righSection.getLocalised(location);
        // contentLocation.set(loc);
      }

      Platform.runLater(() -> {
        rightPane.setCache(true);
        rightPane.setCacheHint(CacheHint.SPEED);
        rightPane.getChildren().clear();
        rightPane.getChildren().add(righSection.processedView());
      });
    } catch (final Exception ex) {
      // TODO: handle exception
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
