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

package io.github.jsoagger.jfxcore.engine.components.tab;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import io.github.jsoagger.jfxcore.engine.interpolator.EasingInterpolator;
import io.github.jsoagger.jfxcore.engine.interpolator.EasingMode;

import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Tab pane implementation
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class VLTabpane extends VBox implements IBuildable {

  private static final String TAB_PANE_WRAPPER_CSS = "tab-pane-wrapper";
  private static final String TAB_CONTENT_WRAPPER_CSS = "tab-content-wrapper";

  private AbstractViewController controller;
  private VLViewComponentXML configuration;

  final HBox tabItemsContainer = new HBox();

  private ScrollPane contentSroll = new ScrollPane();
  StackPane tabContentContainer = new StackPane();

  final HBox leftTabItemsContainer = new HBox();
  final HBox centerTabItemsContainer = new HBox();
  final HBox rightTabItemsContainer = new HBox();

  private final List<VLTab> tabs = new ArrayList<>();
  private VLSimpleTabPos tabsPos = VLSimpleTabPos.TOP;
  private String tabsAlignment;

  private List<Node> pushedContents = new ArrayList<>();


  /**
   * Constructor
   */
  public VLTabpane() {
    getStyleClass().add(TAB_PANE_WRAPPER_CSS);
    tabItemsContainer.getStyleClass().add("tab-items-wrapper");
    leftTabItemsContainer.getStyleClass().add("left-tab-items-wrapper");
    rightTabItemsContainer.getStyleClass().add("right-tab-items-wrapper");
    centerTabItemsContainer.getStyleClass().add("center-tab-items-wrapper");
  }


  public void pushCurrentContent(AbstractViewController controller) {
    Platform.runLater(() -> {

      if (tabContentContainer.getChildren().size() > 0) {
        pushedContents.add(tabContentContainer.getChildren().get(0));
      }

      tabContentContainer.getChildren().clear();
      tabContentContainer.getChildren().add(controller.processedView());
    });
  }


  public void popCurrentContent(boolean update) {
    if (pushedContents.size() > 0) {
      final Node newNode = pushedContents.get(pushedContents.size() - 1);
      pushedContents.remove(pushedContents.size() - 1);

      if (pushedContents.isEmpty()) {
        controller.dispatchEvent(new ReinitHeaderNavigationEvent());
      }

      Platform.runLater(() -> {
        if (tabContentContainer.getChildren().size() > 0) {
          tabContentContainer.getChildren().clear();
        }
        tabContentContainer.getChildren().add(newNode);
      });
    } else {
      controller.dispatchEvent(new ReinitHeaderNavigationEvent());
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.configuration = configuration;
    this.controller = (AbstractViewController) controller;

    final String style = configuration.getPropertyValue(XMLConstants.STYLE_CLASS);
    if (StringUtils.isNotBlank(style)) {
      getStyleClass().setAll(style.split(","));
    }

    final String wrapperStyleClass = configuration.getPropertyValue("wrapperStyleClass");
    if (StringUtils.isNotBlank(wrapperStyleClass)) {
      getStyleClass().setAll(wrapperStyleClass.split(","));
    }

    final String headerStyleClass = configuration.getPropertyValue("headerStyleClass");
    if (StringUtils.isNotBlank(headerStyleClass)) {
      tabItemsContainer.getStyleClass().setAll(headerStyleClass.split(","));
    }

    NodeHelper.setHVGrow(tabContentContainer);
    final String contentStyleClass = configuration.getPropertyValue("contentStyleClass");
    if (StringUtils.isNotBlank(contentStyleClass)) {
      tabContentContainer.getStyleClass().setAll(contentStyleClass.split(","));
    }

    // tab position is top by default
    final String tabPos = configuration.getPropertyValue(XMLConstants.TAB_POS);
    if (StringUtils.isNotBlank(tabPos)) {
      try {
        tabsPos = VLSimpleTabPos.valueOf(tabPos.toUpperCase());
      } catch (final Exception e) {
        tabsPos = VLSimpleTabPos.TOP;
      }
    }

    contentSroll.setFitToHeight(true);
    contentSroll.setFitToWidth(true);
    contentSroll.setHbarPolicy(ScrollBarPolicy.NEVER);

    if (tabsPos == VLSimpleTabPos.TOP) {
      getChildren().add(tabItemsContainer);
      contentSroll.setContent(tabContentContainer);
      getChildren().add(contentSroll);
      NodeHelper.setVgrow(contentSroll);
      tabItemsContainer.pseudoClassStateChanged(PseudoClass.getPseudoClass("top"), true);
    } else if (tabsPos == VLSimpleTabPos.LEFT) {
      getChildren().add(tabItemsContainer);
      getChildren().add(tabContentContainer);
      tabItemsContainer.pseudoClassStateChanged(PseudoClass.getPseudoClass("left"), true);
    } else {
      contentSroll.setContent(tabContentContainer);
      getChildren().add(contentSroll);
      getChildren().add(tabItemsContainer);
      NodeHelper.setHVGrow(contentSroll, tabContentContainer);
      tabItemsContainer.pseudoClassStateChanged(PseudoClass.getPseudoClass("bottom"), true);
    }

    tabsAlignment = configuration.getPropertyValue("tabsAlignment");
    boolean withFiller = "true".equalsIgnoreCase(configuration.getPropertyValue("withFiller"));
    if ("center".equalsIgnoreCase(tabsAlignment)) {
      if (withFiller) {
        Node hs2 = NodeHelper.horizontalSpacer();
        Node hs1 = NodeHelper.horizontalSpacer();
        NodeHelper.styleClassSetAll(hs1, configuration, "fillerStyleClass", "vl-tab-pane-filler");
        NodeHelper.styleClassSetAll(hs2, configuration, "fillerStyleClass", "vl-tab-pane-filler");

        tabItemsContainer.getChildren().addAll(hs1, centerTabItemsContainer, hs2);
      } else {
        // NodeHelper.setHgrow(centerTabItemsContainer);
        tabItemsContainer.getChildren().addAll(centerTabItemsContainer);
      }
    } else if ("right".equalsIgnoreCase(tabsAlignment)) {
      Node hs1 = NodeHelper.horizontalSpacer();
      NodeHelper.styleClassAddAll(hs1, configuration, "fillerStyleClass", "vl-tab-pane-filler");
      tabItemsContainer.getChildren().addAll(hs1, centerTabItemsContainer);
    } else {
      Node hs1 = NodeHelper.horizontalSpacer();
      NodeHelper.styleClassAddAll(hs1, configuration, "fillerStyleClass", "vl-tab-pane-filler");
      tabItemsContainer.getChildren().addAll(centerTabItemsContainer, hs1);
    }

    tabContentContainer.setFocusTraversable(false);
    tabContentContainer.getStyleClass().add(TAB_CONTENT_WRAPPER_CSS);

    buildTabs();
    selectTab(0);

    VLTabPaneHeader header = new VLTabPaneHeader();
    //header.buildFrom(controller, configuration);
    getChildren().add(0, header);
  }


  protected void buildTabs() {
    // leftTabs
    VLViewComponentXML leftTabsCfg = configuration.getNullableComponentById("LeftTabs");
    if (leftTabsCfg != null && leftTabsCfg.hasSubComponent()) {
      buildTabsGroup(leftTabsCfg, TabSide.LEFT);
    }

    // right tabs
    VLViewComponentXML rightTabsCfg = configuration.getNullableComponentById("RightTabs");
    if (rightTabsCfg != null && rightTabsCfg.hasSubComponent()) {
      buildTabsGroup(rightTabsCfg, TabSide.RIGHT);
    }

    // center tabs
    VLViewComponentXML centerTabsCfg = configuration.getNullableComponentById("CenterTabs");
    if (centerTabsCfg != null && centerTabsCfg.hasSubComponent()) {
      buildTabsGroup(centerTabsCfg, TabSide.CENTER);
    }
  }


  protected void buildTabsGroup(VLViewComponentXML groupConfiguration, TabSide side) {
    for (final VLViewComponentXML tabConfig : groupConfiguration.getSubcomponents()) {
      String styleClass = groupConfiguration.getPropertyValue("styleClass");
      if (StringUtils.isEmpty(styleClass)) {
        final VLTab tab = new VLTab(controller, tabConfig);
        addTab(tab, side);
      } else {
        final VLTab tab = new VLTab(controller, tabConfig, styleClass);
        addTab(tab, side);
      }
    }
  }


  protected void addTab(final VLTab tab, TabSide side) {
    tabs.add(tab);
    tab.setPos(tabsPos);
    tab.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> selectTab(tab));
    // NodeHelper.setHgrow(tab);

    if (side == TabSide.LEFT) {
      centerTabItemsContainer.getChildren().add(tab);
    } else if (side == TabSide.CENTER) {
      centerTabItemsContainer.getChildren().add(tab);
    } else if (side == TabSide.RIGHT) {
      centerTabItemsContainer.getChildren().add(tab);
    }
  }


  /**
   * Select a tab
   *
   * @param tab
   */
  public void selectTab(final VLTab tab) {
    tab.select(true);
    tabContentContainer.getChildren().clear();
    pushedContents.clear();

    if (tab.getContent() != null) {
      Node content = tab.getContent();
      NodeHelper.setHVGrow(content);
      tabContentContainer.getChildren().add(content);

      if(AbstractApplicationRunner.isMobile() || AbstractApplicationRunner.isSimulMobile()) {
        final EasingInterpolator cme = new EasingInterpolator(EasingMode.OUT_CIRC);
        final Transition tte1 = NodeHelper.translateYTo(100, 0, content, Duration.millis(500));
        tte1.setInterpolator(cme);
        tte1.play();
      }

      String location = tab.getConfig().getPropertyValue("location");
      if (StringUtils.isNotBlank(location)) {
        tab.getController().getStructureContent().contentLocationProperty().set(tab.getController().getLocalised(location));
      }
    }

    for (final VLTab styledTab : tabs) {
      if (!styledTab.equals(tab)) {
        styledTab.select(false);
      }
    }

    // reinit header each time we change tab
    controller.dispatchEvent(new ReinitHeaderNavigationEvent());
  }


  public void selectTab(String id) {
    for (final VLTab styledTab : tabs) {
      if (styledTab.getInternalId().equals(id)) {
        selectTab(styledTab);
        break;
      }
    }
  }


  public List<VLTab> getTabs() {
    return tabs;
  }


  /**
   * Set tabs content
   *
   * @param contents
   */
  public void setTabContent(final Map<String, Node> contents) {
    if (contents != null) {
      for (final String tabId : contents.keySet()) {
        for (final VLTab tab : tabs) {
          if (tabId.equals(tab.getInternalId())) {
            tab.setContent(contents.get(tabId));
          }
        }
      }
    }
  }


  public void setBottomToolbar(Node toolbar) {
    toolbar.getStyleClass().add("dv-fieldset-footer");
    getChildren().add(toolbar);
  }


  public void selectTab(final int i) {
    if (i < tabs.size()) {
      selectTab(tabs.get(i));
    }
  }

  /**
   * @author Ramilafananana VONJISOA
   * @mailto yvonjisoa@nexitia.com
   * @date 2019
   */
  static enum TabSide {
    LEFT, RIGHT, CENTER, MORE;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }


  public void destroy() {
    tabs.clear();
    controller = null;
    configuration = null;
  }
}
