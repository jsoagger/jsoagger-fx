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

package io.github.jsoagger.jfxcore.engine.controller.main;



import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.ViewLayoutPosition;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.components.tab.PushTabContentEvent;
import io.github.jsoagger.jfxcore.engine.components.tab.VLTab;
import io.github.jsoagger.jfxcore.engine.components.tab.VLTabpane;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.roostructure.content.event.UpdateCurrentLocationEvent;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;
import io.github.jsoagger.jfxcore.engine.events.CoreEvent;
import io.github.jsoagger.jfxcore.engine.events.GenericEvent;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 14 févr. 2018
 */
public class StandardTabPaneController extends StandardViewController {

  private String initialSelectedTabId;
  private IBuildable buildable;
  private List<AbstractViewController> buildedTabsController = new ArrayList<>();

  public StandardTabPaneController() {
    super();
    registerListener(CoreEvent.PushTabContentEvent);
    registerListener(CoreEvent.PopTabContentEvent);
  }

  @Override
  public synchronized <T extends GenericEvent> void handle(T e) {
    super.handle(e);

    VLTabpane tabpane = (VLTabpane) buildable;

    if (e.isA(CoreEvent.PopTabContentEvent)) {
      tabpane.popCurrentContent(false);
    } else if (e.isA(CoreEvent.PushTabContentEvent)) {
      PushTabContentEvent ev = (PushTabContentEvent) e;

      if (ev.getParentController() == this || ev.getParentController() == null) {
        String viewId = ev.getProperty("viewId");
        if (StringUtils.isNotBlank(viewId)) {
          final StandardViewController view =
              StandardViewUtils.forId(rootStructure, viewId, (OperationData) ev.getModel());
          view.setParent(this);
          tabpane.pushCurrentContent(view);

          UpdateCurrentLocationEvent ucl = new UpdateCurrentLocationEvent();
          ucl.setHasPrevious(true);
          ucl.setSourceController(view);
          ucl.setTabContentNavigation(true);
          ((AbstractViewController) getController()).dispatchEvent(ucl);
        }
      }
    }
  }


  @Override
  public Node getNodeOnPosition(ViewLayoutPosition position) {
    if (position == ViewLayoutPosition.CENTER) {
      return processedView();
    }

    return super.getNodeOnPosition(position);
  }


  @Override
  protected void process() {
    String contentImpl = getRootComponent().getPropertyValue("contentImpl", "VLTabPane");
    if (StringUtils.isNotBlank(contentImpl)) {
      buildable = (IBuildable) Services.getBean(contentImpl);
      buildable.buildFrom(this, getRootComponent());
      processedView(buildable.getDisplay());

      if (StringUtils.isNotBlank(initialSelectedTabId) && buildable instanceof VLTabpane) {
        ((VLTabpane) buildable).selectTab(initialSelectedTabId);
      }
    }
  }


  public void selectTab(String tabId) {
    if (buildable != null && buildable instanceof VLTabpane) {
      ((VLTabpane) buildable).selectTab(tabId);
    }
  }


  public AbstractViewController getControllerOfTab(String tabId) {
    if (buildable != null && buildable instanceof VLTabpane) {
      List<VLTab> tabs = ((VLTabpane) buildable).getTabs();
      for (VLTab tab : tabs) {
        if (tab.getInternalId().equalsIgnoreCase(tabId)) {
          return tab.getTabContentController();
        }
      }
    }

    return null;
  }

  public String getInitialSelectedTabId() {
    return initialSelectedTabId;
  }

  /**
   * Setter of initialSelectedTabId
   *
   * @param initialSelectedTabId the initialSelectedTabId to set
   */
  public void setInitialSelectedTabId(String initialSelectedTabId) {
    this.initialSelectedTabId = initialSelectedTabId;
  }


  /**
   * Getter of buildable
   *
   * @return the buildable
   */
  public IBuildable getBuildable() {
    return buildable;
  }


  /**
   * Setter of buildable
   *
   * @param buildable the buildable to set
   */
  public void setBuildable(IBuildable buildable) {
    this.buildable = buildable;
  }


  /**
   * Getter of buildedTabsController
   *
   * @return the buildedTabsController
   */
  public void addBuildedTabsController(AbstractViewController tabController) {
    buildedTabsController.add(tabController);
    if (tabController instanceof StandardViewController) {
      ((StandardViewController) tabController).selectedElementProperty()
          .addListener((ChangeListener<OperationData>) (observable, oldValue, newValue) -> {
            this.selectedElementProperty().set(newValue);
          });
    }
  }


  @Override
  public void destroy() {
    super.destroy();
    for (AbstractViewController c : buildedTabsController) {
      c.destroy();
    }
    VLTabpane tabpane = (VLTabpane) buildable;
    tabpane.destroy();
  }


  /**
   * Getter of buildedTabsController
   *
   * @return the buildedTabsController
   */
  public List<AbstractViewController> getBuildedTabsController() {
    return buildedTabsController;
  }
}
