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

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.view.IViewLayoutManageable;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class ViewForwardLayoutManager extends FixedLeftThreeHPanesViewLayoutManager {

  @FXML
  private Pane layoutHeader;

  @FXML
  private Label layoutHeaderTitle;

  @FXML
  private ScrollPane mainScrollPane;

  /**
   * Constructor
   */
  public ViewForwardLayoutManager() {
    super();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void layout(IViewLayoutManageable layoutManageable) {
    super.layout(layoutManageable);
    NodeHelper.styleClassAddAll(layoutHeader, layoutManageable.getConfiguration(), "forwardLayoutHeaderStyleClass");
    String forwardLayoutTitle = layoutManageable.getConfiguration().getPropertyValue("forwardLayoutHeaderTitle");
    if (StringUtils.isNotBlank(forwardLayoutTitle)) {
      layoutHeaderTitle.setText(layoutManageable.getController().getLocalised(forwardLayoutTitle));
    }

    if(AbstractApplicationRunner.isDesktop()) {
      mainScrollPane.translateYProperty().set(10);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void pushContent(Node node) {
    super.pushContent(node);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void popContent() {
    super.popContent();
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public URL getFXMLLocation() {
    return this.getClass().getResource("ViewForwardViewLayout.fxml");
  }
}
