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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 8 févr. 2018
 */
public abstract class AbstractComponentResponsiveAware {

  /*-----------------------------------------------------------------------------
  | PRIVATE FIELDS
   *=============================================================================*/
  protected IParentResponsiveMatrix responsiveMatrix;
  protected IResponsiveAreaSize previousSize;
  protected ChangeListener<Number> rootPaneWidthChangeListener;


  /**
   * Default Constructor
   */
  public AbstractComponentResponsiveAware() {}


  protected void init() {
    rootPaneWidthChangeListener = this::handleSceneWidthChange;
    getRootPane().widthProperty().addListener(rootPaneWidthChangeListener);
  }


  public void handleSceneWidthChange(ObservableValue value, Number oldSceneWidth, Number newSceneWidth) {
    if (responsiveMatrix != null) {
      IResponsiveAreaSize areasSize = responsiveMatrix.getSizeOf(newSceneWidth.doubleValue());

      // only recompute the sizing if the matrix has switched to another
      // min/max plage
      if ((previousSize == null) || (!previousSize.equals(areasSize))) {
        applyContentMatrix(areasSize);
      }
    }
  }


  /**
   * @{inheritedDoc}
   */
  public void applyCurrentMatrix() {
    if (getRootPane().widthProperty().get() > 0) {
      IResponsiveAreaSize areasSize = responsiveMatrix.getSizeOf(getRootPane().widthProperty().get());
      applyContentMatrix(areasSize);
    }
  }


  /**
   * Getter of responsiveMatrix
   *
   * @return the responsiveMatrix
   */
  public IParentResponsiveMatrix getResponsiveMatrix() {
    return responsiveMatrix;
  }


  /**
   * Setter of responsiveMatrix
   *
   * @param responsiveMatrix the responsiveMatrix to set
   */
  public void setResponsiveMatrix(IParentResponsiveMatrix responsiveMatrix) {
    this.responsiveMatrix = responsiveMatrix;
  }


  public abstract Pane getRootPane();


  public abstract URL getFXMLLocation();


  public abstract void applyContentMatrix(IResponsiveAreaSize areasSize);


  /**
   * @{inheritedDoc}
   */
  @Override
  protected void finalize() throws Throwable {
    super.finalize();
    this.rootPaneWidthChangeListener = null;
    responsiveMatrix = null;
    previousSize = null;
  }
}
