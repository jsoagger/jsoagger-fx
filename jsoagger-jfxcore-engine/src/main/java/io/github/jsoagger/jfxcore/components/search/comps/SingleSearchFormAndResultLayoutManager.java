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

package io.github.jsoagger.jfxcore.components.search.comps;



import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManageable;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class SingleSearchFormAndResultLayoutManager implements IViewLayoutManager {

  private IViewLayoutManageable layoutManageable;

  private BorderPane rootpane = new BorderPane();
  private StackPane headerPane = new StackPane();
  private StackPane contentPane = new StackPane();


  public SingleSearchFormAndResultLayoutManager() {
    rootpane.setTop(headerPane);
    rootpane.setCenter(contentPane);
  }


  /*
   * (non-Javadoc)
   *
   * @see io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.IViewLayoutManager#
   * layout(io.github.jsoagger.jfxcore.engine.controller.roostructure.layout. IViewLayoutManageable)
   */
  @Override
  public void layout(IViewLayoutManageable layoutManageable) {
    this.layoutManageable = layoutManageable;
    contentPane.getChildren().add(layoutManageable.getNodeOnPosition(ViewLayoutPosition.LEFT));
  }


  /*
   * (non-Javadoc)
   *
   * @see io.github.jsoagger.jfxcore.engine.controller.roostructure.layout.IViewLayoutManager# getDisplay()
   */
  @Override
  public Node getDisplay() {
    return rootpane;
  }


  @Override
  public void display(ViewLayoutPosition position) {
    IViewLayoutManager.super.display(position);
    contentPane.getChildren().clear();
    if (position == ViewLayoutPosition.LEFT) {
      contentPane.getChildren().add(layoutManageable.getNodeOnPosition(ViewLayoutPosition.LEFT));
      rootpane.setTop(layoutManageable.getNodeOnPosition(ViewLayoutPosition.TOP));
    } else if (position == ViewLayoutPosition.RIGHT) {
      contentPane.getChildren().add(layoutManageable.getNodeOnPosition(ViewLayoutPosition.RIGHT));
      rootpane.setTop(layoutManageable.getNodeOnPosition(ViewLayoutPosition.TOP));
    }
  }

}
