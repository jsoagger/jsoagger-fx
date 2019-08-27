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

package io.github.jsoagger.jfxcore.demoapp.comps;



import java.io.IOException;
import java.io.InputStream;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.ResourceUtils;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * Icon associadte to the type of the businness data
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 29 janv. 2018
 */
public class DemoHeaderLogoPresenter extends VBox implements IBuildable {

  /**
   * Constructor
   */
  public DemoHeaderLogoPresenter() {
    setAlignment(Pos.CENTER);
    //setStyle("-fx-effect: dropshadow(three-pass-box, -primary-color-700, 1.0, 0.2, 0.0, 2.0);");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {

    NodeHelper.styleClassAddAll(this, configuration);

    try (InputStream is = ResourceUtils.getStream("/images/logo/JSOAGGER_FX_WHITE.png")) {
      if(is != null) {
        final Image img = new Image(is, 200, 102, true, true);
        final ImageView imgView = new ImageView(img);
        // getChildren().add(imgView);
      }
    } catch (IOException e) {
      // e.printStackTrace();
    }
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }
}
