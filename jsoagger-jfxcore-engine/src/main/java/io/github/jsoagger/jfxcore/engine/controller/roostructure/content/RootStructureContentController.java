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

package io.github.jsoagger.jfxcore.engine.controller.roostructure.content;




import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IRSHeaderHolder;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.SecondaryMenuController;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import io.github.jsoagger.jfxcore.engine.controller.main.RootStructureController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.WizardViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.PushStructureContentEvent;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.UpdateCurrentLocationEvent;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;
import io.github.jsoagger.jfxcore.engine.controller.utils.StructureContentUtils;
import io.github.jsoagger.jfxcore.engine.controller.utils.WizardViewUtils;
import io.github.jsoagger.jfxcore.engine.events.CoreEvent;
import io.github.jsoagger.jfxcore.engine.events.GenericEvent;
import io.github.jsoagger.jfxcore.engine.events.RSBeginProcessing;
import io.github.jsoagger.jfxcore.engine.interpolator.EasingInterpolator;
import io.github.jsoagger.jfxcore.engine.interpolator.EasingMode;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewConfigXML;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * The center content of a view.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class RootStructureContentController extends AbstractViewController {

  protected List<PushStructureContentEvent> views = Collections.synchronizedList(new ArrayList<>());
  protected SimpleBooleanProperty displayNavigationBar = new SimpleBooleanProperty(true);
  protected StackPane layout = new StackPane();

  protected RootStructureController rootStructure;
  protected Node leftMenu;

  /**
   * Constructor
   */
  public RootStructureContentController() {
    super();

    layout.setId("RootStructureContentController");
    registerListener(CoreEvent.PopStructureContentEvent);
    registerListener(CoreEvent.PushStructureContentEvent);
    registerListener(CoreEvent.RSBeginProcessing);
    registerListener(CoreEvent.RSEndProcessing);
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  protected void process() {
    super.process();

    // build the InitialStructureContent when first time displayed
    final VLViewComponentXML rootComp = getRootComponent();
    if (rootComp != null) {
      displayNavigationBar.set(rootComp.booleanPropertyValueOf("displayNavigationBar").orElse(true));
      final String initialView = initialView();
      if (StringUtils.isNotBlank(initialView)) {
        final PushStructureContentEvent ev = new PushStructureContentEvent.Builder().viewId(initialView).build();
        buildStructureContent(ev);
      }
    }

    // load left menu if have provided one
    final String leftMenuView = rootComp.getPropertyValue("leftMenuView");
    if (StringUtils.isNotBlank(leftMenuView)) {
      final StandardViewController view = StandardViewUtils.forId(rootStructure, leftMenuView);
      rootStructure.setLeftMenuContent(view.processedView());

      final String selectedLeftMenuViewId = rootComp.getPropertyValue("selectedLeftMenuViewId");
      if (StringUtils.isNotBlank(selectedLeftMenuViewId)) {
        ((SecondaryMenuController) view).selectMenu(selectedLeftMenuViewId, false);
      }
    } else {
      rootStructure.setLeftMenuContent(null);
    }

    // do no change this color, please set a
    // -default-background color in your .root css
    layout.setStyle("-fx-background-color:-default-background");
    rootStructure.setAnchors();
    processedView(layout);
  }

  protected String initialView() {
    final VLViewComponentXML rootComp = getRootComponent();
    if (rootComp != null) {
      final String initialView = rootComp.getPropertyValue("initialView");
      return initialView;
    }

    return null;
  }

  /**
   * Build the new {@link StandardViewController} in the same context as. The resulted view is
   * displayed inside the current {@link RootStructureController} current controller.
   */
  protected void buildStructureContent(PushStructureContentEvent event) {
    final StructureContentController structureContent = (StructureContentController) Services.getBean("StructureContentController");
    if (event != null) {
      structureContent.setSourceEvent(event);
      structureContent.setFormModelData((OperationData) event.getModel());
      structureContent.setForModelId(event.getModelFullId());
    }

    structureContent.setRootStructure(rootStructure);
    structureContent.initViewContext(new VLViewConfigXML(), rootStructure.getRootContext());
    structureContent.setInitialized(true);
    structureContent.build();

    if (event.getViewId().endsWith("Wizard")) {
      final WizardViewController view = WizardViewUtils.forWizardId(this, structureContent, event.getViewId(), null);
      structureContent.processedView(view.processedView());
      structureContent.setCurrentContent(view);
      event.setProcessedContent(structureContent);

      if (view.isDialog()) {
        Platform.runLater(() -> view.show());
      } else {
        if (view.getRootComponent() != null) {
          final String location = view.getRootComponent().getPropertyValue("location");
          if (StringUtils.isNotBlank(location)) {
            structureContent.contentLocationProperty().set(view.getLocalised(location));
          }
        }

        Platform.runLater(() -> display(event));
      }
    } else {

      final StandardViewController view = StandardViewUtils.forId(rootStructure, structureContent, event.getViewId());
      structureContent.setCurrentContent(view);
      structureContent.processedView(view.processedView());
      event.setProcessedContent(structureContent);

      Platform.runLater(() -> {
        if (view.getRootComponent() != null) {
          final String rootStructureContentStyleClass = view.getRootComponent().getPropertyValue("rootStructureContentStyleClass");
          if (StringUtils.isNotBlank(rootStructureContentStyleClass)) {
            rootStructure.getContentRootPane().getStyleClass().setAll(rootStructureContentStyleClass.split(","));
          } else {
            rootStructure.getContentRootPane().getStyleClass().clear();
          }
        }

        display(event);
      });
    }

    if (event.isReplace()) {
      views.remove(views.size() - 1);
    }

    views.add(event);
    sendUpdateCurrentLocation();
  }

  private void dobuildStructureContent(PushStructureContentEvent event, VLViewComponentXML c) {
    final Object obj = this;
    setProcessing(null);

    final boolean showBottomHeader = c.getBooleanProperty("showRootStructureHeader", true);
    event.setShowRootStructureHeader(showBottomHeader);

    final StructureContentController sc = StructureContentUtils.forId((RootStructureContentController) obj, getRootStructure(), event, c);

    sc.setScIdentifier(event.getViewId());
    event.setProcessedContent(sc);
    views.add(event);

    sendUpdateCurrentLocation();
    Platform.runLater(() -> display(event));
  }

  /**
   * Update header location
   */
  public void sendUpdateCurrentLocation() {
    UpdateLocationTask t = new UpdateLocationTask();
    new Thread(t).start();
  }

  /**
   * @param identifier
   * @return
   */
  protected Optional<VLViewComponentXML> getStructureContentConfig(String identifier) {
    final VLViewComponentXML rootComp = getRootComponent();
    if (rootComp != null) {
      final String structureContentConfig = rootComp.getPropertyValue(identifier);
      if (StringUtils.isNotBlank(structureContentConfig)) {
        return ComponentUtils.resolveDefinition(this, structureContentConfig);
      }
    }

    return Optional.empty();
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public synchronized <T extends GenericEvent> void handle(T e) {
    super.handle(e);
    if(e.isA(CoreEvent.RSBeginProcessing)) {
    	if(Platform.isFxApplicationThread()) {
    		setProcessing((RSBeginProcessing) e);
    	}
    	else {
        	Platform.runLater(() -> {
        		setProcessing((RSBeginProcessing) e);
        	});
    	}
    }
    else if(e.isA(CoreEvent.RSEndProcessing)) {
    	Platform.runLater(() -> {
    		endProcessing();
    	});
    }
    else if (e.isA(CoreEvent.PushStructureContentEvent)) {
      final PushStructureContentEvent event = (PushStructureContentEvent) e;
      final boolean redispatch = event.wasProcessed();

      // build new one
      if (!redispatch) {
        Task<Void> pt=  new Task<Void>() {

          @Override
          protected Void call() throws Exception {
            buildStructureContent(event);
            return null;
          }
        };
        new Thread(pt).start();
      }

      // display processed
      else {
        if (Platform.isFxApplicationThread()) {
          display(event);
        } else {
          Platform.runLater(() -> {
            display(event);
          });
        }
      }
    }

    // back
    else if (e.isA(CoreEvent.PopStructureContentEvent)) {
      Task<Void> pt=  new Task<Void>() {

        @Override
        protected Void call() throws Exception {
          pop();
          return null;
        }
      };
      new Thread(pt).start();
    }
  }

  public void display(PushStructureContentEvent event) {
    final StructureContentController sc = event.getProcessedContent();
    final Node nextNode = sc.processedView();

    if (layout.getChildren().size() > 0) {
      final EasingInterpolator cme = new EasingInterpolator(EasingMode.OUT_CIRC);
      final Transition tte = NodeHelper.fadeOut(layout.getChildren().get(0), Duration.millis(50));
      tte.setInterpolator(cme);
      tte.setOnFinished(e -> {
        layout.getChildren().remove(0);
        layout.getChildren().add(nextNode);
        nextNode.setOpacity(1);

        if(AbstractApplicationRunner.isMobile() || AbstractApplicationRunner.isSimulMobile()) {
          nextNode.translateXProperty().set(100);
          final Transition tte1 = NodeHelper.translateTo(100, 0, nextNode, Duration.millis(50));
          tte1.setInterpolator(cme);
          tte1.play();
        }
      });
      tte.play();

    } else {
      nextNode.setOpacity(0);
      layout.getChildren().add(nextNode);
      final EasingInterpolator cme = new EasingInterpolator(EasingMode.IN_EXPO);
      final Transition tte = NodeHelper.fadeIn(nextNode, Duration.millis(50));
      tte.setInterpolator(cme);
      tte.play();
    }

    rootStructure.displayMainView();
  }

  Node beforeProcessedNode = null;
  public void setProcessing(RSBeginProcessing e) {
    Node node = processingNode(e);
    layout.getChildren().add(node);
    e.processingProperty().set(true);
  }

  protected Node processingNode(RSBeginProcessing e) {
	  StackPane processing = new StackPane();
	  processing.getStyleClass().add("ep-root-structure-processing-pane");

	  if(e != null && e.getContentNode() != null) {
		  processing.getChildren().add(e.getContentNode());
	  }
	  else {
		  Label op = new javafx.scene.control.Label("Processing...");
		  processing.getChildren().add(op);
		  op.getStyleClass().add("ep-root-structure-processing-pane-label");
	  }

	  e.processingProperty().set(true);
	  return processing;
  }

  public void setError() {
    if (layout.getChildren().size() > 0) {
      layout.getChildren().remove(0);
    }
    layout.getChildren().add(NodeHelper.getErrorIndicator());
  }


  public void endProcessing() {
	  if (layout.getChildren().size() > 0) {
		  layout.getChildren().remove(layout.getChildren().size() - 1);
	  }
	  if(beforeProcessedNode != null) {
		  layout.getChildren().add(beforeProcessedNode);
	  }
  }

  /**
   * Back button was clicked, hide and destroy current diplayed view. Display previous view.
   */
  private void pop() {
    if (views.size() == 1) {
      // buggy!!, do nothing
      // no pop this is the last one
      return;
    }

    final PushStructureContentEvent todisp = views.get(views.size() - 2);
    Node todispNode = todisp.getProcessedContent().processedView();
    todispNode.setOpacity(1);

    final PushStructureContentEvent torem = views.get(views.size() - 1);
    views.remove(views.size() - 1);

    // this must be done after removing view form inside views.
    sendUpdateCurrentLocation();

    // this is the current displayed node,
    // animate hide and at the end display new one
    final EasingInterpolator cme = new EasingInterpolator(EasingMode.OUT_CIRC);
    final Transition tte = NodeHelper.fadeOut(layout.getChildren().get(0), Duration.millis(50));
    tte.setInterpolator(cme);
    tte.setOnFinished(e -> {
      layout.getChildren().remove(0);
      layout.getChildren().add(todisp.getProcessedContent().processedView());
      if(AbstractApplicationRunner.isMobile() || AbstractApplicationRunner.isSimulMobile()) {
        final Transition tte1 = NodeHelper.translateYTo(100, 0, todisp.getProcessedContent().processedView(), Duration.millis(400));
        tte1.setInterpolator(cme);
        tte1.setOnFinished(q -> clean(torem));
        tte1.play();
      }
    });
    tte.play();
  }

  /**
   * Destroy a controller.
   *
   * @param view
   */
  private void clean(PushStructureContentEvent view) {
    final Task<Void> t = new Task<Void>() {

      @Override
      protected Void call() throws Exception {
        view.getSourceController().destroy();
        return null;
      }
    };
    final Thread thread = new Thread(t);
    thread.start();
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public <T extends GenericEvent> void dispatchEvent(T event) {
    synchronized (views) {
      final List<StructureContentController> aware = new ArrayList<>();
      for (final PushStructureContentEvent c : views) {
        if (c.getProcessedContent().isAwareOf(event)) {
          aware.add(c.getProcessedContent());
        }
      }

      for(StructureContentController c : aware) {
        c.handle(event);
      }

      if (this.isAwareOf(event)) {
        handle(event);
      }
    }
  }

  /**
   * @return
   */
  public final SimpleBooleanProperty displayNavigationBarProperty() {
    return displayNavigationBar;
  }

  /**
   * @return
   */
  public final boolean isDisplayNavigationBar() {
    return this.displayNavigationBarProperty().get();
  }

  /**
   * @param displayNavigationBar
   */
  public final void setDisplayNavigationBar(final boolean displayNavigationBar) {
    this.displayNavigationBarProperty().set(displayNavigationBar);
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public RootStructureController getRootStructure() {
    return rootStructure;
  }

  /**
   *
   */
  public void setRootStructure(RootStructureController rootStructure) {
    this.rootStructure = rootStructure;
  }

  /**
   * @author Ramilafananana  VONJISOA
   */
  private class UpdateLocationTask extends Task<Void> {

    @Override
    protected Void call() throws Exception {
      final StructureContentController current = views.get(views.size() - 1).getProcessedContent();
      final UpdateCurrentLocationEvent eve = new UpdateCurrentLocationEvent();

      if(current.getCurrentContent() instanceof IRSHeaderHolder) {
        IRSHeaderHolder h = (IRSHeaderHolder) current.getCurrentContent();
        Node header = h.getDisplayIdentity();
        if(header != null) {
          eve.setLocationNode(header);
        }
      }

      eve.setCurrentView(current);
      eve.setHasPrevious(views.size() > 1);
      eve.setShowHeader(views.get(views.size() - 1).isShowRootStructureHeader());

      getRootStructure().dispatchEvent(eve);
      return null;
    }
  }
}
