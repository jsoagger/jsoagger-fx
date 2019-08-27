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

package io.github.jsoagger.jfxcore.engine.components.menu;




import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IMinimizable;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class Copyright extends HBox implements IBuildable, IMinimizable {

  private final static String cp = "© ";
  private final Label copyright = new Label();


  /**
   * Constructor
   */
  public Copyright() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    getChildren().add(copyright);
    NodeHelper.setTitle(copyright, configuration, (AbstractViewController) controller);
    NodeHelper.styleClassSetAll(this, configuration);
    NodeHelper.styleClassAddAll(copyright, configuration, "labelStyleClass", "copyright-label");
    copyright.setText(cp + copyright.getText());
    minimize();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public void minimize() {}

  /**
   * @{inheritedDoc}
   */
  @Override
  public void maximize() {}
}
