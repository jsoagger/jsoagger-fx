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



import java.util.Iterator;
import java.util.List;

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IMinimizable;
import io.github.jsoagger.jfxcore.api.IResponsiveAware;
import io.github.jsoagger.jfxcore.api.IResponsiveSizing;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.contextmenu.EllipsisActionButton;
import io.github.jsoagger.jfxcore.engine.components.header.event.HeaderNavbarSetCustomRightActions;
import io.github.jsoagger.jfxcore.engine.components.header.event.HeaderNavbarSetStandardRightActions;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;
import io.github.jsoagger.jfxcore.engine.interpolator.EasingInterpolator;
import io.github.jsoagger.jfxcore.engine.interpolator.EasingMode;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;
import io.github.jsoagger.jfxcore.engine.utils.ToolbarUtils;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class HeaderRightToolbar extends VBox implements IMinimizable, IBuildable, IResponsiveAware, IHeaderToolbar {

  private static final String HEADER_RIGHT_TOOLBAR = "header-right-toolbar";

  private final StackPane topToolbar = new StackPane();
  private final StackPane bottomToolbar = new StackPane();

  /**
   * When maximized all nodes are displayed in this wrapper -> standard actions
   */
  private final HBox maximizedWrapper = new HBox();

  /**
   * When navigating throught pages, custom actions are displayed inside this box
   */
  private final HBox customWrapper = new HBox();

  /** When minimized, displayed in context menu -> standard actions */
  private EllipsisActionButton ellispsys = null;
  private final SimpleBooleanProperty minimized = new SimpleBooleanProperty(false);

  /** When child structure content declares custom -> custom actions */
  private final SimpleBooleanProperty wasCustomized = new SimpleBooleanProperty(false);

  private AbstractViewController controller;
  private VLViewComponentXML configuration;


  /**
   * Constructor
   */
  public HeaderRightToolbar() {
    super();

    NodeHelper.setVgrow(topToolbar);
    getChildren().add(topToolbar);
    getChildren().add(bottomToolbar);

    setAlignment(Pos.CENTER_RIGHT);
    maximizedWrapper.setAlignment(Pos.CENTER_RIGHT);
    customWrapper. setAlignment(Pos.CENTER_RIGHT);

  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.configuration = configuration;
    this.controller = (AbstractViewController) controller;
    build();
  }


  private void build() {
    maximizedWrapper.getChildren().clear();
    topToolbar.getChildren().clear();

    //NodeHelper.styleClassSetAll(maximizedWrapper, configuration, HEADER_RIGHT_TOOLBAR);
    //NodeHelper.styleClassSetAll(customWrapper, configuration, HEADER_RIGHT_TOOLBAR);
    getStyleClass().add(HEADER_RIGHT_TOOLBAR);

    final Iterator<VLViewComponentXML> it = configuration.getSubcomponents().iterator();
    while (it.hasNext()) {
      final VLViewComponentXML conf = it.next();

      // can be reference or controller
      if (StringUtils.isNotBlank(conf.getController())) {
        final String controller = conf.getController();
        final AbstractViewController viewController = StandardViewUtils.forId(this.controller.getRootStructure(), controller);
        maximizedWrapper.getChildren().add(viewController.processedView());
      } else {
        final VLViewComponentXML resolved = ComponentUtils.resolveDefinition(controller, conf).orElse(null);

        if (resolved != null) {

          final IBuildable buildable = ComponentUtils.generate(controller, resolved);
          NodeHelper.setTransparentFocus(buildable.getDisplay());
          maximizedWrapper.getChildren().addAll(buildable.getDisplay());
        }
      }

      if (conf.isSeparatorAfter() && it.hasNext()) {
        maximizedWrapper.getChildren().addAll(NodeHelper.headerVerticalSeparator());
      }
    }

    bottomToolbar.setVisible(false);
    ellispsys = ToolbarUtils.ellipsisVActionsButton(configuration, controller, null);
    animateAddMaximizedWrapper();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void minimize() {
    if (wasCustomized.not().get()) {
      topToolbar.getChildren().clear();
      if (ellispsys != null) {
        topToolbar.getChildren().addAll(ellispsys);
      }
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void maximize() {
    if (wasCustomized.not().get()) {
      topToolbar.getChildren().clear();
      topToolbar.getChildren().addAll(maximizedWrapper);
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
  public void doResize(IResponsiveSizing responsiveSizing) {
    IResponsiveAware.doResize(this, responsiveSizing);
  }


  /**
   * @param ev
   */
  public void handle(HeaderNavbarSetCustomRightActions ev) {
    Platform.runLater(() -> {
      animateRemoveMaximizedWrapper();
      wasCustomized.set(true);
      if (ev.getActions() != null) {
        animateAddCustomAction(ev.getActions());
      }
    });
  }


  /**
   * @param ev
   */
  public void handle(HeaderNavbarSetStandardRightActions ev) {
    Platform.runLater(() -> {
      topToolbar.getChildren().clear();
      wasCustomized.set(false);
      animateAddMaximizedWrapper();
    });
  }


  private void animateRemoveMaximizedWrapper() {
    final FadeTransition ft = NodeHelper.fadeOut(maximizedWrapper, Duration.millis(100));
    ft.setOnFinished(e -> topToolbar.getChildren().remove(maximizedWrapper));
    ft.play();
  }


  private void animateAddMaximizedWrapper() {
    topToolbar.getChildren().clear();
    topToolbar.getChildren().add(maximizedWrapper);

    final FadeTransition ft = NodeHelper.fadeIn(maximizedWrapper, Duration.millis(400));
    final EasingInterpolator ei = new EasingInterpolator(EasingMode.IN_OUT_CIRC);
    ft.setInterpolator(ei);
    ft.play();
  }


  private void animateAddCustomAction(List<IBuildable> actions) {
    customWrapper.getChildren().clear();
    topToolbar.getChildren().clear();
    topToolbar.getChildren().add(customWrapper);
    for(IBuildable e: actions) {
      customWrapper.getChildren().add(e.getDisplay());
    }
    customWrapper.setOpacity(1);

    final FadeTransition ft = NodeHelper.fadeIn(customWrapper, Duration.millis(400));
    final EasingInterpolator ei = new EasingInterpolator(EasingMode.IN_OUT_CIRC);
    ft.setInterpolator(ei);
    ft.play();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void hideBottomToolbar() {
    bottomToolbar.visibleProperty().set(false);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void showBottomToolbar() {
    bottomToolbar.visibleProperty().set(true);
    //bottomToolbar.setVisible(false);
  }
}
