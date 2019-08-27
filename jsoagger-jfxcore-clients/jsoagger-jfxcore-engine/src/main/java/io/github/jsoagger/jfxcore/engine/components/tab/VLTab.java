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


import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IComponentProcessor;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardTabPaneController;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * A tab inside a {@link VLTabpane}
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class VLTab extends VBox {

  private final static String TAB_ITEM_CSS = "tab-item";
  private final static String TAB_ITEM_TITLE_CSS = "tab-item-title";

  private final PseudoClass selected = PseudoClass.getPseudoClass("selected");
  private final PseudoClass top = PseudoClass.getPseudoClass("top");
  private final PseudoClass bottom = PseudoClass.getPseudoClass("bottom");

  private Label title;
  private final StackPane contentWrapper = new StackPane();
  private Node content;

  private final VLViewComponentXML config;
  private final AbstractViewController tabPaneController;
  private AbstractViewController tabContentController;
  private final String internalId;

  private boolean builded = false;


  /**
   * @param tabsid
   * @param config
   * @param text
   */
  public VLTab(AbstractViewController controller, VLViewComponentXML tabDefinition) {
    this.config = tabDefinition;
    this.tabPaneController = controller;
    this.internalId = tabDefinition.getId();

    String styleClass = tabDefinition.getPropertyValue("styleClass");
    if (StringUtils.isNotBlank(styleClass)) {
      getStyleClass().addAll(styleClass.split(","));
    } else {
      getStyleClass().add(TAB_ITEM_CSS);
    }

    this.title = new Label();
    NodeHelper.setTitle(title, tabDefinition, controller);
    // title.setContentDisplay(ContentDisplay.LEFT);

    String titleStyleClass = tabDefinition.getPropertyValue("titleStyleClass");
    if (StringUtils.isNotBlank(titleStyleClass)) {
      title.getStyleClass().addAll(titleStyleClass.split(","));
    } else {
      title.getStyleClass().add(TAB_ITEM_TITLE_CSS);
    }

    IconUtils.setIcon(title, tabDefinition);
    getChildren().add(title);
    NodeHelper.setHVGrow(contentWrapper);
  }


  /**
   * Constructor
   *
   * @param cssStyle
   * @param config
   * @param text
   */
  public VLTab(AbstractViewController tabPaneController, VLViewComponentXML tabDefinition, String cssStyle) {
    this.config = tabDefinition;
    this.tabPaneController = tabPaneController;
    this.internalId = tabDefinition.getId();

    // Process tab and its title
    final String text = tabDefinition.getPropertyValue(XMLConstants.TITLE);
    this.title = new Label();
    this.title.setText(tabPaneController.getLocalised(text));
    this.title.getStyleClass().add(TAB_ITEM_TITLE_CSS);
    IconUtils.setIcon(title, tabDefinition);
    getStyleClass().add(cssStyle);
    getChildren().add(title);

    NodeHelper.setHgrow(this, contentWrapper);
  }


  /**
   * Set the position of the tab: TOP, BOTTOM, LEFT
   *
   * @param pos
   */
  public void setPos(VLSimpleTabPos pos) {
    if (pos == VLSimpleTabPos.BOTTOM) {
      pseudoClassStateChanged(bottom, true);
    } else {
      pseudoClassStateChanged(top, true);
    }
  }


  /**
   * Set seletion value of the tab
   *
   * @param value
   */
  public void select(boolean value) {
    pseudoClassStateChanged(selected, value);
  }


  /**
   * @return the title
   */
  public Label getTitle() {
    return title;
  }


  /**
   * @param title the title to set
   */
  public void setTitle(Label title) {
    this.title = title;
  }


  /**
   * @return the content
   */
  public Node getContent() {
    if (content == null && config.hasSubComponent()) {
      setProcessing();
      VLViewComponentXML tabCfg = config.getSubcomponents().get(0);

      if (!builded) {
        if (tabCfg.getProcessor() != null) {
          buildFromProcessor(tabCfg);
        } else {
          buildFromRootView(tabCfg);
        }
      }
    }

    return contentWrapper;
  }


  private void setProcessing() {
    contentWrapper.getChildren().clear();
    contentWrapper.getChildren().add(NodeHelper.getProcessingIndicator());
  }


  private void setError() {
    contentWrapper.getChildren().clear();
    contentWrapper.getChildren().add(NodeHelper.getErrorIndicator());
  }


  private void setResult(Node node) {
    contentWrapper.getChildren().clear();
    contentWrapper.getChildren().add(node);
  }


  /**
   * Display center content in animated mode
   *
   * @return
   */
  private void fadeInCenterAnimation() {
    FadeTransition ft = new FadeTransition(Duration.millis(700), content);
    ft.setFromValue(0.4);
    ft.setToValue(1);
    ft.setAutoReverse(false);
    ft.play();
  }


  /**
   * @param tabcontentconfig
   * @return
   */
  protected void buildFromProcessor(VLViewComponentXML tabcontentconfig) {
    Task<Void> buildTask = new Task<Void>() {

      @Override
      protected Void call() throws Exception {
        if (!builded && tabcontentconfig.getProcessor() != null) {
          final IComponentProcessor componentProcessor = (IComponentProcessor) Services.getBean(tabcontentconfig.getProcessor());
          content = componentProcessor.process(tabPaneController, config);

          builded = true;
          Platform.runLater(() -> {
            NodeHelper.setHVGrow(content);
            setResult(content);
          });
        }
        return null;
      }
    };

    new Thread(buildTask).start();

  }


  protected void buildFromRootView(VLViewComponentXML tabcontentconfig) {
    if (!builded && StringUtils.isNotBlank(tabcontentconfig.getProcessor()) || StringUtils.isNotBlank(tabcontentconfig.getRootView())) {
      tabContentController = getContent(tabcontentconfig.getRootView());

      if(tabContentController != null) {
        content = tabContentController.processedView();
        builded = true;
        Platform.runLater(() -> {
          NodeHelper.setHVGrow(content);
          setResult(content);
        });
      }
      else {
        Platform.runLater(() -> {
          setResult(new StackPane());
        });
      }
    }
  }


  public void loadContent(Pane centerViewContent, String rootViewId) {
    try {
      final AbstractViewController controller = StandardViewUtils.forId(getController().getRootStructure(), getController().getStructureContent(), rootViewId);

      if (tabPaneController instanceof StandardTabPaneController) {
        ((StandardTabPaneController) tabPaneController).addBuildedTabsController(controller);
      }

      content = controller.processedView();
      Platform.runLater(() -> {
        centerViewContent.getChildren().clear();
        NodeHelper.setHVGrow(centerViewContent);
        centerViewContent.getChildren().add(content);
      });
    }catch (Exception e) {
      e.printStackTrace();
    }
  }


  public AbstractViewController getContent(String rootViewId) {
    try {
      final AbstractViewController controller = StandardViewUtils.forId(getController().getRootStructure(),
          getController().getStructureContent(),
          rootViewId);

      controller.setParent(getController());
      if (tabPaneController instanceof StandardTabPaneController) {
        ((StandardTabPaneController) tabPaneController).addBuildedTabsController(controller);
      }

      return controller;
    }catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }


  /**
   * @param content the content to set
   */
  public void setContent(Node content) {
    this.content = content;
  }


  /**
   * @return the internalId
   */
  public String getInternalId() {
    return internalId;
  }


  /**
   * @return the config
   */
  public VLViewComponentXML getConfig() {
    return config;
  }


  /**
   * Getter of tabPaneController
   *
   * @return the tabPaneController
   */
  public AbstractViewController getController() {
    return tabPaneController;
  }


  /**
   * Getter of tabContentController
   *
   * @return the tabContentController
   */
  public AbstractViewController getTabContentController() {
    return tabContentController;
  }
}
