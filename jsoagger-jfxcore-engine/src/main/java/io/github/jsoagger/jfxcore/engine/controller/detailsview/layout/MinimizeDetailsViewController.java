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

package io.github.jsoagger.jfxcore.engine.controller.detailsview.layout;



import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.api.detailsview.IMinimizedDetailsView;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class MinimizeDetailsViewController extends StandardViewController {

  protected IMinimizedDetailsView detailsView;


  /**
   * Constructor
   */
  public MinimizeDetailsViewController() {
    super();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Node getNodeOnPosition(ViewLayoutPosition position) {
    if (position == ViewLayoutPosition.CENTER) {
      if(detailsView != null) {
        return detailsView.getDisplay();
      }
      else {
        return new StackPane();
      }
    }
    return null;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected void process() {
    super.process();
    if(detailsView != null) {
      detailsView.buildFrom(this, getRootComponent());
      processedView(detailsView.getDisplay());
    }
    else {
      processedView(new StackPane());
    }
  }


  public IMinimizedDetailsView getDetailsView() {
    return detailsView;
  }


  public void setDetailsView(IMinimizedDetailsView detailsView) {
    this.detailsView = detailsView;
  }
}
