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

package io.github.jsoagger.jfxcore.engine.components.toolbar.vtoolbar;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IToolbarHolder;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.toolbar.AbstractToolbar;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public abstract class AbstractVToolbar extends AbstractToolbar {

  protected static final String CENTER_ACTIONS = "centerActions";
  protected static final String RIGHT_ACTIONS = "rightActions";
  protected static final String LEFT_ACTIONS = "leftActions";

  protected VBox rootContainer = new VBox();


  /**
   * Constructor
   */
  public AbstractVToolbar() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return rootContainer;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(AbstractViewController controller, IToolbarHolder toolbarHolder) {
    super.buildFrom(controller, toolbarHolder);

    // extract css from configuration
    NodeHelper.styleClassSetAll(rootContainer, configuration);

    List<IBuildable> all = new ArrayList<>();
    if (rootMenuconfiguration != null) {

      Optional<VLViewComponentXML> leftActionsCfg = rootMenuconfiguration.getComponentById(LEFT_ACTIONS);
      leftActionsCfg.ifPresent(config -> {
        List<IBuildable> buildables = buildActionGroup(config);
        all.addAll(buildables);
      });

      Optional<VLViewComponentXML> rightActionsCfg = rootMenuconfiguration.getComponentById(RIGHT_ACTIONS);
      rightActionsCfg.ifPresent(config -> {
        List<IBuildable> buildables = buildActionGroup(config);
        all.addAll(buildables);
      });

      Optional<VLViewComponentXML> centerActionsCfg = rootMenuconfiguration.getComponentById(CENTER_ACTIONS);
      centerActionsCfg.ifPresent(config -> {
        List<IBuildable> buildables = buildActionGroup(config);
        all.addAll(buildables);
      });

      for(IBuildable buildable: all) {
        Node display = buildable.getDisplay();
        if (display instanceof Control) {
          Control node = (Control) display;
          // node.minWidthProperty().bind(rootContainer.widthProperty());
        }

        NodeHelper.setHgrow(display);
        rootContainer.getChildren().add(display);
      }
    }
  }
}
