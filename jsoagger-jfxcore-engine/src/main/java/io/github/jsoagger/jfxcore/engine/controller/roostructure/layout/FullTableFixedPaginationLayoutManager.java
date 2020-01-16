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
public class FullTableFixedPaginationLayoutManager extends FullTableStructureContentLayoutManager {

  @FXML
  private Pane centerAreaSection;


  /**
   *
   */
  public FullTableFixedPaginationLayoutManager() {
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
      AnchorPane.setTopAnchor(centerAreaSection, 0.);
      AnchorPane.setLeftAnchor(centerAreaSection, 0.);
      AnchorPane.setRightAnchor(centerAreaSection, 0.);

      if(bottomNode != null) {
        AnchorPane.setBottomAnchor(centerAreaSection, 72.);
      }
      else {
        AnchorPane.setBottomAnchor(centerAreaSection, 0.);
      }
    } else {
      headerAreaSection.heightProperty().addListener((ChangeListener<Number>) (observable, oldValue, newValue) -> {
        double height = newValue.doubleValue();
        AnchorPane.clearConstraints(centerAreaSection);
        AnchorPane.setTopAnchor(centerAreaSection, height);
        AnchorPane.setLeftAnchor(centerAreaSection, 0.);
        AnchorPane.setRightAnchor(centerAreaSection, 0.);
        if(bottomNode != null) {
          AnchorPane.setBottomAnchor(centerAreaSection, 72.);
        }
        else {
          AnchorPane.setBottomAnchor(centerAreaSection, 0.);
        }
        centerAreaSection.requestLayout();
      });
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public URL getFXMLLocation() {
    return FullTableFixedPaginationLayoutManager.class.getResource("FullTableFixedPaginationStructureContent.fxml");
  }
}
