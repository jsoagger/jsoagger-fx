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

package io.github.jsoagger.jfxcore.engine.components.header.comps;




import java.util.List;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IMinimizable;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Header left toolbar implementation. This toolbar can be minimized.
 * <p>
 * This toolbar is made by top and bottom toolbar.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class HeaderLeftToolBar extends VBox implements IBuildable, IMinimizable, IHeaderToolbar {

  private static final String EP_HEADER_LEFT_TOOLBAR_TOP = "ep-header-left-toolbar-top";
  private static final String EP_HEADER_LEFT_TOOLBAR_BOTTOM = "ep-header-left-toolbar-bottom";
  private static final String EP_HEADER_LEFT_TOOLBAR = "ep-header-left-toolbar";

  private HBox topToolbar = new HBox();
  private HBox bottomToolbar = new HBox();
  private Node ellipsys;


  /**
   * Constructor
   */
  public HeaderLeftToolBar() {
    super();
    minWidthProperty().bind(prefWidthProperty());
    maxWidthProperty().bind(prefWidthProperty());
    getStyleClass().add(EP_HEADER_LEFT_TOOLBAR);

    getChildren().addAll(topToolbar, bottomToolbar);
    NodeHelper.setVgrow(topToolbar, bottomToolbar);

    topToolbar.minWidthProperty().bind(topToolbar.prefWidthProperty());
    topToolbar.maxWidthProperty().bind(topToolbar.prefWidthProperty());
    topToolbar.minHeightProperty().bind(topToolbar.prefHeightProperty());
    topToolbar.maxHeightProperty().bind(topToolbar.prefHeightProperty());

    bottomToolbar.minWidthProperty().bind(bottomToolbar.prefWidthProperty());
    bottomToolbar.maxWidthProperty().bind(bottomToolbar.prefWidthProperty());
    bottomToolbar.minHeightProperty().bind(bottomToolbar.prefHeightProperty());
    bottomToolbar.maxHeightProperty().bind(bottomToolbar.prefHeightProperty());
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    buildTopToolbar((AbstractViewController) controller, configuration);
    buildBottomToolbar((AbstractViewController) controller, configuration);
  }


  protected void buildBottomToolbar(AbstractViewController controller, VLViewComponentXML configuration) {
    NodeHelper.styleClassSetAll(bottomToolbar, configuration, EP_HEADER_LEFT_TOOLBAR_BOTTOM);
  }


  protected void buildTopToolbar(AbstractViewController controller, VLViewComponentXML configuration) {
    List<IBuildable> links = ComponentUtils.resolveAndGenerate(controller, configuration.getSubcomponents());
    NodeHelper.styleClassSetAll(topToolbar, configuration, EP_HEADER_LEFT_TOOLBAR_TOP);
    for (final IBuildable action : links) {
      topToolbar.getChildren().add(action.getDisplay());
    }
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


  /**
   * @{inheritedDoc}
   */
  @Override
  public void hideBottomToolbar() {
    bottomToolbar.setVisible(false);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void showBottomToolbar() {
    bottomToolbar.setVisible(true);
    bottomToolbar.setVisible(false);
  }
}
