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

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.api.detailsview.IMaximizedDetailsView;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;

import javafx.scene.Node;

/**
 * Controller of {@link IMaximizedDetailsView}, thiw view can be displayed either with full details
 * view or with a header via maximized details view layout.
 * <p>
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class MaximizedDetailsViewController extends StandardViewController {

  protected IMaximizedDetailsView maximizedDetailsView;


  /**
   * Constructor
   */
  public MaximizedDetailsViewController() {
    super();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Node getNodeOnPosition(ViewLayoutPosition position) {
    return maximizedDetailsView.getNodeOnPosition(position);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected void process() {
    super.process();
    maximizedDetailsView.buildFrom((IJSoaggerController) this, getRootComponent());
  }


  public IMaximizedDetailsView getDetailsView() {
    return maximizedDetailsView;
  }


  public void setDetailsView(IMaximizedDetailsView maximizedDetailsView) {
    this.maximizedDetailsView = maximizedDetailsView;
  }
}
