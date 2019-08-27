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
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class DialogContent extends StackPane implements IBuildable, IDialogContent {

  VLViewComponentXML contentCfg;
  Label text = new Label();


  /**
   * Constructor
   */
  public DialogContent() {}


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
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    contentCfg = configuration.getComponentById("Content").orElse(null);
    getChildren().add(text);

    NodeHelper.styleClassSetAll(this, contentCfg);
    String messageStyleClass = contentCfg.getPropertyValue("messageStyleClass");
    if (StringUtils.isNotBlank(messageStyleClass)) {
      text.getStyleClass().addAll(messageStyleClass.split(","));
    }

    IconUtils.setIcon(text, contentCfg);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setMessage(String message) {
    text.setText(message);
  }
}
