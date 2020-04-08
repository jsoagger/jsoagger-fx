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

import io.github.jsoagger.jfxcore.api.IParentResponsiveMatrix;
import io.github.jsoagger.jfxcore.api.IResponsiveAreaSize;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManageable;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 8 févr. 2018
 */
public abstract class AbstractViewLayoutManager implements IViewLayoutManager {

  /*-----------------------------------------------------------------------------
  | PRIVATE FIELDS
   *=============================================================================*/
  protected IParentResponsiveMatrix responsiveMatrix;
  protected IViewLayoutManageable layoutManageable;
  protected ChangeListener<Number> rootPaneWidthChangeListener;

  /*-----------------------------------------------------------------------------
  | FXML FIELDS
   *=============================================================================*/
  @FXML
  protected Pane rootPane;

  private boolean verticalScroll = false;
  private boolean horizontalScroll = false;

  protected Timeline resizingTimeline = new Timeline();


  /**
   * Default Constructor
   */
  public AbstractViewLayoutManager() {
    KeyFrame kf = new KeyFrame(Duration.millis(600));
    resizingTimeline = new Timeline(kf);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void layout(IViewLayoutManageable layoutManageable) {
    this.layoutManageable = layoutManageable;

    try {
      if (getFXMLLocation() != null)
        NodeHelper.loadFXML(getFXMLLocation(), this);
    } catch (Exception e) {
    }

    if (getRootPane() != null) {
      postLayout();
    }
  }

  protected void postLayout() {
    if (layoutManageable != null && layoutManageable.getResponsiveMatrix() != null) {
      responsiveMatrix = layoutManageable.getResponsiveMatrix();
    }

    rootPaneWidthChangeListener = (ChangeListener<Number>) this::handleSceneWidthChange;
    getRootPane().widthProperty().addListener(rootPaneWidthChangeListener);
    NodeHelper.styleClassAddAll(getRootPane(), layoutManageable.getConfiguration(), "styleClass");

    resizingTimeline.setOnFinished(e -> {
      handleSceneWidthChangeEnd();
    });
  }

  /**
   * At the end of the resize time will call this method after 1 seconds.
   */
  public void handleSceneWidthChangeEnd() {}

  public void handleSceneWidthChange(ObservableValue value, Number oldSceneWidth,
      Number newSceneWidth) {
    if (responsiveMatrix != null) {
      final IResponsiveAreaSize areasSize = responsiveMatrix.getSizeOf(newSceneWidth.doubleValue());
      applyContentMatrix(areasSize);
    }
  }


  public void applyCurrentMatrix() {
    if (getRootPane().widthProperty().get() > 0) {
      final IResponsiveAreaSize areasSize =
          responsiveMatrix.getSizeOf(getRootPane().widthProperty().get());
      applyContentMatrix(areasSize);
    }
  }


  /**
   * Getter of responsiveMatrix
   *
   * @return the responsiveMatrix
   */
  @Override
  public IParentResponsiveMatrix getResponsiveMatrix() {
    return responsiveMatrix;
  }


  /**
   * Setter of responsiveMatrix
   *
   * @param responsiveMatrix the responsiveMatrix to set
   */
  @Override
  public void setResponsiveMatrix(IParentResponsiveMatrix responsiveMatrix) {
    this.responsiveMatrix = responsiveMatrix;
  }


  /**
   * Getter of layoutManageable
   *
   * @return the layoutManageable
   */
  public IViewLayoutManageable getLayoutManageable() {
    return layoutManageable;
  }


  /**
   * Setter of layoutManageable
   *
   * @param layoutManageable the layoutManageable to set
   */
  public void setLayoutManageable(IViewLayoutManageable layoutManageable) {
    this.layoutManageable = layoutManageable;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  protected void finalize() throws Throwable {
    super.finalize();
    getRootPane().widthProperty().removeListener(rootPaneWidthChangeListener);
    rootPaneWidthChangeListener = null;
    layoutManageable = null;
    responsiveMatrix = null;
    rootPane = null;
    getRootPane().getChildren().clear();
  }


  public Pane getRootPane() {
    return rootPane;
  }


  public abstract URL getFXMLLocation();


  public abstract void applyContentMatrix(IResponsiveAreaSize areasSize);


  /**
   * Getter of verticalScroll
   *
   * @return the verticalScroll
   */
  public boolean isVerticalScroll() {
    return verticalScroll;
  }


  /**
   * Setter of verticalScroll
   *
   * @param verticalScroll the verticalScroll to set
   */
  public void setVerticalScroll(boolean verticalScroll) {
    this.verticalScroll = verticalScroll;
  }


  /**
   * Getter of horizontalScroll
   *
   * @return the horizontalScroll
   */
  public boolean isHorizontalScroll() {
    return horizontalScroll;
  }


  /**
   * Setter of horizontalScroll
   *
   * @param horizontalScroll the horizontalScroll to set
   */
  public void setHorizontalScroll(boolean horizontalScroll) {
    this.horizontalScroll = horizontalScroll;
  }
}
