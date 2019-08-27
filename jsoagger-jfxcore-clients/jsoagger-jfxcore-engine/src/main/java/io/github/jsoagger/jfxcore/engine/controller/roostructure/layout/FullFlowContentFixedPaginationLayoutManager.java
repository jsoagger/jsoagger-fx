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

import io.github.jsoagger.jfxcore.api.view.IViewLayoutManageable;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 11 févr. 2018
 */
public class FullFlowContentFixedPaginationLayoutManager extends FullFlowContentLayoutManager {

  @FXML
  private Pane anchorCenteredPane;


  /**
   * Constructor
   */
  public FullFlowContentFixedPaginationLayoutManager() {
    super();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void layout(IViewLayoutManageable layoutManageable) {
    super.layout(layoutManageable);

    if (topNode == null) {
      headerAreaSection.setVisible(false);
      AnchorPane.clearConstraints(anchorCenteredPane);
      AnchorPane.setLeftAnchor(anchorCenteredPane, 0.);
      AnchorPane.setRightAnchor(anchorCenteredPane, 0.);
      AnchorPane.setTopAnchor(anchorCenteredPane, 0.);
      AnchorPane.setBottomAnchor(anchorCenteredPane, 72.);
    } else {
      headerAreaSection.heightProperty().addListener((ChangeListener<Number>) (observable, oldValue, newValue) -> {
        headerHeight = newValue.doubleValue();
        AnchorPane.clearConstraints(anchorCenteredPane);
        AnchorPane.setTopAnchor(anchorCenteredPane, 0.);
        AnchorPane.setLeftAnchor(anchorCenteredPane, 0.);
        AnchorPane.setRightAnchor(anchorCenteredPane, 0.);
        AnchorPane.setBottomAnchor(anchorCenteredPane, 72.);
      });
    }

    if(bottomNode != null) {
      bottomNode.visibleProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
        updateConstraints();
      });
    }
  }

  private double headerHeight = 172.;


  private void updateConstraints() {
    AnchorPane.clearConstraints(anchorCenteredPane);
    AnchorPane.setLeftAnchor(anchorCenteredPane, 0.);
    AnchorPane.setRightAnchor(anchorCenteredPane, 0.);
    AnchorPane.setTopAnchor(anchorCenteredPane, 0.);

    if (bottomNode.isVisible()) {
      AnchorPane.setBottomAnchor(anchorCenteredPane, 72.0);
    } else {
      AnchorPane.setBottomAnchor(anchorCenteredPane, 0.0);
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public URL getFXMLLocation() {
    if (isScroll()) {
      return FullFlowContentFixedPaginationLayoutManager.class.getResource("FullFlowFixedPaginationContentLayout.fxml");
    }
    return FullFlowContentFixedPaginationLayoutManager.class.getResource("FullFlowFixedPaginationScrollLessContentLayout.fxml");
  }
}
