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
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IComponentProcessor;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardTabPaneController;
import io.github.jsoagger.jfxcore.engine.controller.main.layout.IUpdatableController;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;
import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
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

  private IBadgeProvider badgeProvider;
  private Label title;
  private Label badge = new Label();
  private final StackPane contentWrapper = new StackPane();
  private Node content;

  private final VLViewComponentXML config;
  private final AbstractViewController tabPaneController;
  private AbstractViewController tabContentController;
  private final String internalId;

  private boolean builded = false;
  private boolean isSelected = false;


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

    StackPane titleAndBadge = new StackPane();
    titleAndBadge.setAlignment(Pos.BASELINE_CENTER);
    titleAndBadge.getChildren().add(title);

    String badgeProviderString = tabDefinition.getPropertyValue("badgeProvider");
    if(StringUtils.isNotBlank(badgeProviderString)) {
    	badgeProvider = (IBadgeProvider) Services.getBean(badgeProviderString);
    	badgeProvider.countProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number number1, Number number2) {
	    		Platform.runLater(() -> {
	    			badge.setText(number2.toString());
	    		});
			}
		});

    	Integer count = badgeProvider.countProperty().get();
    	badge.getStyleClass().setAll("ep-cart-items-count-badge");
    	badge.setText(count.toString());
    	titleAndBadge.getChildren().add(badge);
    	badge.visibleProperty().bind(Bindings.greaterThan(badgeProvider.countProperty(), 0));
    }

    IconUtils.setIcon(title, tabDefinition);

    getChildren().add(titleAndBadge);
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
	this.isSelected = value;
    pseudoClassStateChanged(selected, value);
    title.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), value);
  }

  public boolean isSeleted() {
	  return isSelected;
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

  public void forceUpdateTab(){
	  _doLoadTabContent();
  }


  /**
   * @return the content
   */
  public Node getContent() {
	  // force load means reload/rebuild tab content
	  // each time the controller says that i should be done
	  // for better user experience.
	  boolean needUpdate = false;
	  if(tabContentController != null) {
		  if(IUpdatableController.class.isAssignableFrom(tabContentController.getClass())) {
			  needUpdate = ((IUpdatableController)tabContentController).needUpdateContent();
		  }
	  }

	  if(needUpdate) {
		  builded = false;
		  contentWrapper.getChildren().clear();
		  contentWrapper.setAlignment(Pos.TOP_RIGHT);

		  // static header may be used on mobile fo better visual effect
		  String staticHeader = config.getPropertyValue("staticHeader");
		  if(StringUtils.isNotBlank(staticHeader)) {
			  IBuildable header = (IBuildable) Services.getBean(staticHeader);
			  if(header != null) {
				  header.buildFrom(tabContentController, config);
				  contentWrapper.getChildren().add(header.getDisplay());
			  }
		  }

		  _doLoadTabContent();
	  }
	  else {
		  if (content == null && config.hasSubComponent()) {
		    	if (!builded) {
		    		_doLoadTabContent();
		    	}
		   }
	  }

    return contentWrapper;
  }

  private void _doLoadTabContent(){
	  Task<Void> loadContentTask = new Task<Void>() {

		@Override
		protected Void call() throws Exception {
			 // setProcessing();

		     VLViewComponentXML tabCfg = config.getSubcomponents().get(0);

		    if (tabCfg.getProcessor() != null) {
		      buildFromProcessor(tabCfg);
		    } else {
		      buildFromRootView(tabCfg);
		    }

			return null;
		}
	};

	new Thread(loadContentTask).start();
  }


  private void setProcessing() {
	  Platform.runLater(()-> {
		  	contentWrapper.getChildren().clear();
		    contentWrapper.getChildren().add(NodeHelper.getProcessingIndicator());
	  });

  }


  private void setError() {
	  Platform.runLater(()-> {
		  contentWrapper.getChildren().clear();
		  contentWrapper.getChildren().add(NodeHelper.getErrorIndicator());
	  });
  }


  private void setResult(Node node) {
	  Platform.runLater(()-> {
		  contentWrapper.getChildren().clear();
		  contentWrapper.getChildren().add(node);
	  });
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
    if (!builded && StringUtils.isNotBlank(tabcontentconfig.getProcessor())
    		|| StringUtils.isNotBlank(tabcontentconfig.getRootView())) {

    	if(tabContentController != null) {
    		tabContentController.destroy();
    	}

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
