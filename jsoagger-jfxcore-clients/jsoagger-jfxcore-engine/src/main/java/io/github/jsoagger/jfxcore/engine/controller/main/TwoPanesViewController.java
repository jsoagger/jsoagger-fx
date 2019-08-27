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
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;

import javafx.application.Platform;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 4 févr. 2018
 */
public class TwoPanesViewController extends StandardViewController {

  protected StandardViewController leftController;
  protected StandardViewController rightController;

  protected StackPane leftPane = new StackPane();
  protected StackPane rightPane = new StackPane();


  /**
   * Default Constructor
   */
  public TwoPanesViewController() {
    super();
  }


  @Override
  protected void process() {
    super.process();
    doBuildLeftPane();
    doBuildRightPane();
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


  public void doBuildLeftPane() {
    final String leftSectionView = getRootComponent().getPropertyValue("leftSectionView");
    if (StringUtils.isNotBlank(leftSectionView)) {
      leftController = StandardViewUtils.forId(getRootStructure(), structureContentController, leftSectionView);
      leftController.setParent(this);
      NodeHelper.styleClassSetAll(leftPane, getRootComponent(), "leftSectionAreaStyleClass", "ep-two-hpanes-with-left-menu-wrapper");
      leftPane.getChildren().add(leftController.processedView());
    }
  }


  public void doBuildRightPane() {
    try {
      final String rightSectionView = getRootComponent().getPropertyValue("rightSectionView");
      if (StringUtils.isNotBlank(rightSectionView)) {

        rightController = StandardViewUtils.forId(getRootStructure(), structureContentController, rightSectionView);
        NodeHelper.styleClassSetAll(rightPane, getRootComponent(), "rightSectionAreaStyleClass", "");
        rightController.setParent(this);

        Platform.runLater(() -> {
          final Node rightSectionNode = rightController.processedView();
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
