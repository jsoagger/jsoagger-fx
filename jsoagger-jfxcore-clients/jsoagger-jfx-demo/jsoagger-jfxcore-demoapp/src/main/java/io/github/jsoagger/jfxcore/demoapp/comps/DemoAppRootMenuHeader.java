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

import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.ResourceUtils;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class DemoAppRootMenuHeader extends VBox implements IBuildable {

  private AbstractViewController controller;
  private VLViewComponentXML configuration;

  /**
   * Constructor
   */
  public DemoAppRootMenuHeader() {}

  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.configuration = configuration;
    this.controller = (AbstractViewController) controller;

    boolean displayLogo = configuration.getBooleanProperty("displayLogo", true);
    NodeHelper.styleClassAddAll(this, configuration);

    getChildren().add(NodeHelper.verticalSpacer());
    if (displayLogo) {
      try (InputStream is = ResourceUtils.getStream("/images/logo/NEXITIAL_PARTIAL_DARK.png")) {
        final Image img = new Image(is, 180, 180, true, true);
        final ImageView imgView = new ImageView(img);
        getChildren().add(imgView);
        imgView.setOpacity(0.67);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      /*
       * BackgroundImage myBI= new BackgroundImage(new
       * Image("/images/maxresdefault.jpg",400,400,true,true), BackgroundRepeat.NO_REPEAT,
       * BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT); //then you set
       * to your node setBackground(new Background(myBI));
       */
    }

    getChildren().add(NodeHelper.verticalSpacer());
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    //setAlignment(Pos.CENTER);
    return this;
  }
}
