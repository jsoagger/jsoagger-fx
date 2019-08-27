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
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManageable;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManager;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class SwitchableTwoHPanesWithHeaderActionsViewLayoutManager extends SwitchableTwoHPanesViewLayoutManager implements IViewLayoutManager {

  private static final String FXML_LOCATION = "SwitchableTwoHPanesWithHeaderActionsViewLayout.fxml";

  @FXML
  private Pane headerActionsWrapper;


  /*-----------------------------------------------------------------------------
  | CONSTRUCTORS
   *=============================================================================*/
  public SwitchableTwoHPanesWithHeaderActionsViewLayoutManager() {
    super();
  }


  @Override
  public void layout(IViewLayoutManageable layoutManageable) {
    super.layout(layoutManageable);
    Node topNode = layoutManageable.getNodeOnPosition(ViewLayoutPosition.TOP);
    if (topNode != null) {
      NodeHelper.setHgrow(topNode);
      headerActionsWrapper.getChildren().add(topNode);
    }
  }


  @Override
  public URL getFXMLLocation() {
    return SwitchableTwoHPanesWithHeaderActionsViewLayoutManager.class.getResource(FXML_LOCATION);
  }
}
