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

package io.github.jsoagger.jfxcore.engine.components.dialog;




import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class DialogHeader extends StackPane implements IBuildable, IDialogHeader {

  private static final String HEADER = "Header";
  private Label title = new Label();
  VLViewComponentXML headerCfg;


  /**
   * Constructor
   */
  public DialogHeader() {
    getStyleClass().add("ep-dialog-header");
    getChildren().add(title);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    headerCfg = configuration.getComponentById(HEADER).orElse(null);
    if ((headerCfg != null) && StringUtils.isNotBlank(headerCfg.getPropertyValue(XMLConstants.TITLE))) {
      String title = headerCfg.getPropertyValue(XMLConstants.TITLE);
      this.title.setText(controller.getLocalised(title));
    }

    NodeHelper.styleClassSetAll(this, headerCfg);
    NodeHelper.styleClassAddAll(title, headerCfg, "titleStyleClass");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setTitle(String title) {
    this.title.setText(title);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }
}
