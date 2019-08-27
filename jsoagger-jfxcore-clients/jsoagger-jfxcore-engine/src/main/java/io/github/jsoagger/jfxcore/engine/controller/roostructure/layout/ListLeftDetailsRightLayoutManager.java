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

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IMinimizable;
import io.github.jsoagger.jfxcore.api.IResponsiveAreaSize;
import io.github.jsoagger.jfxcore.api.IResponsiveAware;
import io.github.jsoagger.jfxcore.api.IResponsiveSizing;
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManageable;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 9 févr. 2018
 */
public class ListLeftDetailsRightLayoutManager extends AbstractViewLayoutManager implements IViewLayoutManager {

  private static final String FXML_LOCATION = "ListLeftDetailsRightLayout.fxml";

  @FXML
  private Pane splitedLeftWrapper = null;

  @FXML
  private Pane splitedRightWrapper = null;

  @FXML
  private Pane splitedRightExternalWrapper;

  private boolean verticalScroll = false;


  /**
   * Default Constructor
   */
  public ListLeftDetailsRightLayoutManager() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void layout(IViewLayoutManageable layoutManageable) {
    super.layout(layoutManageable);

    Node left = layoutManageable.getNodeOnPosition(ViewLayoutPosition.LEFT);
    Node right = layoutManageable.getNodeOnPosition(ViewLayoutPosition.RIGHT);

    splitedLeftWrapper.getChildren().clear();
    splitedLeftWrapper.getChildren().add(left);

    splitedRightExternalWrapper.getChildren().clear();
    splitedRightExternalWrapper.getChildren().add(right);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public URL getFXMLLocation() {
    return ListLeftDetailsRightLayoutManager.class.getResource(FXML_LOCATION);
  }


  @Override
  public void applyContentMatrix(IResponsiveAreaSize areasSize) {
    IResponsiveSizing leftSize = areasSize.getSizeOf(0);
    IResponsiveSizing rightSize = areasSize.getSizeOf(1);

    IResponsiveAware.doResize(splitedLeftWrapper, leftSize);
    IResponsiveAware.doResize(splitedRightWrapper, leftSize);

    NodeHelper.setHgrow(splitedLeftWrapper);

    if (rightSize.isToHide()) {
      splitedRightExternalWrapper.setVisible(false);
      splitedRightExternalWrapper.setManaged(false);
    } else {

      // left area handling
      splitedLeftWrapper.setPrefWidth(leftSize.getWidth());
      splitedLeftWrapper.setVisible(leftSize.isToMinimize() || !leftSize.isToHide());
      if (splitedLeftWrapper.isVisible()) {
        if (leftSize.isToMinimize()) {
          try {
            // expect that have only one child and this child is
            // minimizable!!!
            // IMinimizable minimizable = (IMinimizable)
            // splitedLeftWrapper.getChildren().get(0);
            // minimizable.minimize();
            splitedLeftWrapper.pseudoClassStateChanged(PseudoClass.getPseudoClass("minimized"), true);
          } catch (IndexOutOfBoundsException | ClassCastException e) {
          }
        } else {
          try {
            // expect that have only one child and this child is
            // minimizable!!!
            IMinimizable minimizable = (IMinimizable) splitedLeftWrapper.getChildren().get(0);
            minimizable.maximize();
            splitedLeftWrapper.pseudoClassStateChanged(PseudoClass.getPseudoClass("minimized"), false);
          } catch (IndexOutOfBoundsException | ClassCastException e) {
          }
        }
      }

      if (rightSize.isToMinimize()) {
        // mean minimize the details view
        splitedRightExternalWrapper.setVisible(false);
        splitedRightExternalWrapper.setManaged(false);
      } else {
        // right area handling
        splitedRightExternalWrapper.setVisible(true);
        splitedRightExternalWrapper.setManaged(true);
        splitedRightExternalWrapper.setPrefWidth(rightSize.getWidth());
        splitedRightExternalWrapper.setVisible(rightSize.isToMinimize() || !rightSize.isToHide());
      }
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return rootPane;
  }


  /**
   * Getter of verticalScroll
   *
   * @return the verticalScroll
   */
  @Override
  public boolean isVerticalScroll() {
    return verticalScroll;
  }


  /**
   * Setter of verticalScroll
   *
   * @param verticalScroll the verticalScroll to set
   */
  @Override
  public void setVerticalScroll(boolean verticalScroll) {
    this.verticalScroll = verticalScroll;
  }
}
