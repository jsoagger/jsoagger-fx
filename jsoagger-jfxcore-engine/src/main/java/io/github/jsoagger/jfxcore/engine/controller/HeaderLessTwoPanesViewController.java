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

package io.github.jsoagger.jfxcore.engine.controller;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IRSHeaderHolder;
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.header.comps.HeaderLessSingleLocationNavigationBar;
import io.github.jsoagger.jfxcore.engine.components.header.comps.NavigableToolbar;
import io.github.jsoagger.jfxcore.engine.components.menu.Copyright;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.TwoPanesViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.WizardViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.UpdateCurrentLocationEvent;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.util.BuildRSContentEvent;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;
import io.github.jsoagger.jfxcore.engine.events.CoreEvent;
import io.github.jsoagger.jfxcore.engine.events.GenericEvent;
import io.github.jsoagger.jfxcore.engine.interpolator.EasingInterpolator;
import io.github.jsoagger.jfxcore.engine.interpolator.EasingMode;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;

import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * This Controller is the only one who have builded left menu handling. When an entry in left menu
 * is clicked, it fires a {@link BuildRSContentEvent}. The corresponding view is built and displayed
 * in the right side of the two panes layout.
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class HeaderLessTwoPanesViewController extends TwoPanesViewController {

  private Copyright copyright;
  private VBox layout = new VBox();
  private IBuildable topSection = null;

  private List<StandardViewController> rightHistory = new ArrayList<>();
  private SimpleObjectProperty<StandardViewController> holder = new SimpleObjectProperty();

  /**
   * Constructor
   */
  public HeaderLessTwoPanesViewController() {
    super();

    // TODO HANDLE ONLY IF SOURCE IS THE LEFT PANE ABOVE!!!!
    registerListener(CoreEvent.BuildRSContentEvent);
  }

  @Override
  protected void process() {
    super.process();
    doBuildCopyRight();
    doBuildTop();

    if (topSection != null) {
      layout.getChildren().addAll(topSection.getDisplay(),leftPane, NodeHelper.verticalSpacer());
    }
    else {
      layout.getChildren().addAll(leftPane, NodeHelper.verticalSpacer());
    }
    layout.getChildren().add(copyright);
    layout.setStyle("-fx-background-color:-primary-color;-fx-border-width:0 1 0 0;"
        + "-fx-border-color:derive(-primary-color,10%);-fx-padding:32 0 0 0");
    NodeHelper.setVgrow(layout);

    holder.addListener((a, b, c) -> {
      if (c != null) {
        rightHistory.add(c);
        display(c);
      } else {
        hideCurrentView();
      }
    });
  }

  private void reinit() {
    rightHistory.clear();
    sendUpdateCurrentLocation();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doBuildRightPane() {
    super.doBuildRightPane();
  }

  protected void doBuildCopyRight() {
    Optional<VLViewComponentXML> config = ComponentUtils.getFromGlobalConfig("CoreCopyright");
    if (config.isPresent()) {
      copyright = (Copyright) Services.getBean("Copyright");
      copyright.buildFrom(this, config.get());
    }
  }

  protected void doBuildTop() {
    try {
      String topSectionView = getRootComponent().getPropertyValue("topSectionView");
      if (StringUtils.isNotBlank(topSectionView)) {
        topSection = (IBuildable) Services.getBean(topSectionView);
        topSection.buildFrom(this, getRootComponent());

        if (topSection instanceof HeaderLessSingleLocationNavigationBar) {
          ((HeaderLessSingleLocationNavigationBar) topSection).setTwoPanesViewController(this);
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getNodeOnPosition(ViewLayoutPosition position) {
    if (position == ViewLayoutPosition.LEFT) {
      return layout;
    }

    if (position == ViewLayoutPosition.RIGHT) {
      return rightPane;
    }

    return super.getNodeOnPosition(position);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized <T extends GenericEvent> void handle(T e) {
    super.handle(e);
    if (e.isA(CoreEvent.BuildRSContentEvent)) {
      BuildRSContentEvent ev = (BuildRSContentEvent) e;
      if (ev.isReinit()) {
        reinit();
      }

      if (Platform.isFxApplicationThread()) {
        hideCurrentView();
      } else {
        Platform.runLater(() -> hideCurrentView());
      }
      String id = ev.getViewId();
      BuildStandardViewControllerTask task = new BuildStandardViewControllerTask(id);
      new Thread(task).start();
    }
  }

  /**
   * @author Ramilafananana  VONJISOA
   *
   */
  private class BuildStandardViewControllerTask extends Task<Void> {
    String id;

    /**
     * @param id
     * @param rsc
     * @param rscc
     */
    public BuildStandardViewControllerTask(String id) {
      this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Void call() throws Exception {
      StandardViewController c = StandardViewUtils.forId(getRootStructure(), getStructureContentController(), id);
      if(c instanceof WizardViewController) {
        ((WizardViewController)c).navTo(0);
      }
      Platform.runLater(() -> holder.set(c));
      return null;
    }
  }

  public void sendUpdateCurrentLocation() {
    UpdateCurrentLocationEvent eve = new UpdateCurrentLocationEvent();
    eve.setHasPrevious(rightHistory.size() > 1);
    eve.setShowHeader(false);

    if (topSection instanceof NavigableToolbar) {
      ((NavigableToolbar) topSection).updateLocation(eve);
    }
  }

  public void goBack() {
    if (rightHistory.size() > 1) {
      rightHistory.remove(rightHistory.get(rightHistory.size() - 1));
      display(rightHistory.get(rightHistory.size() - 1));
    }
  }

  protected void hideCurrentView() {
    if(rightPane.getChildren().size() > 0) {
      rightPane.getChildren().remove(0);
    }
  }

  /**
   * @param c
   */
  private void display(StandardViewController c) {
    Node toDisplay = c.processedView();
    toDisplay.setOpacity(1);
    toDisplay.setCache(true);
    toDisplay.setCacheHint(CacheHint.SPEED);

    Node di = ((IRSHeaderHolder) c).getDisplayIdentity();
    if (di == null) {
      Platform.runLater(() -> {
        rightPane.getChildren().clear();
        rightPane.getChildren().add(toDisplay);

        final EasingInterpolator cme = new EasingInterpolator(EasingMode.OUT_BACK);
        final Transition tte = NodeHelper.fadeIn(toDisplay, Duration.millis(300));
        tte.setInterpolator(cme);
        tte.play();
      });
    } else {
      VBox layout = new VBox();
      StackPane head = new StackPane();
      if (di != null) {
        head.getChildren().add(di);
        head.setAlignment(Pos.CENTER_LEFT);
        head.setStyle("-fx-background-color:black;-fx-min-height:72;-fx-padding:0 0 0 8");
      }

      Node dox = c.processedView();
      NodeHelper.setVgrow(dox);
      layout.getChildren().addAll(head, dox);
      rightPane.getChildren().add(layout);
    }

    sendUpdateCurrentLocation();
  }
}
