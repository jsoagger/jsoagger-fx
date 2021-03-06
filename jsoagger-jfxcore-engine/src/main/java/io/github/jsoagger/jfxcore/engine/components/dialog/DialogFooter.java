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



import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class DialogFooter extends StackPane implements IDialogFooter {

  VLViewComponentXML footerCfg;

  public DialogFooter() {}


  @Override
  public Node getDisplay() {
    return this;
  }

  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    footerCfg = configuration.getComponentById("Footer").orElse(null);
    NodeHelper.styleClassSetAll(this, footerCfg);
    setAlignment(Pos.CENTER);
  }

  @Override
  public void setActions(Node actions) {
    IDialogFooter.super.setActions(actions);
    getChildren().add(actions);
  }
}
