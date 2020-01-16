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


import java.util.List;

import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;

import javafx.scene.CacheHint;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class DashboardController extends StandardViewController {

  protected StackPane pane = new StackPane();
  protected VLViewComponentXML noContentPaneConfiguration;


  /**
   * Constructor
   */
  public DashboardController() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  protected void process() {
    super.process();

    VLViewComponentXML widgetList = getRootComponent().getComponentById("WidgetList").orElse(null);
    if (widgetList != null) {
      List<IBuildable> buildables = ComponentUtils.resolveAndGenerate(this, widgetList.getSubcomponents());
      FlowPane flowPane = new FlowPane();
      flowPane.getStyleClass().add("ep-dashboard-items-wrapper");
      for(IBuildable e: buildables) {
        flowPane.getChildren().add(e.getDisplay());
      }

      // HANDLE MOBILE SCROLL ON TOUCH EVENT
      if(AbstractApplicationRunner.isMobile()) {
        flowPane.setOnScrollStarted(s -> AbstractApplicationRunner.setApplicationScrolling(true));
        flowPane.setOnScrollFinished(s-> AbstractApplicationRunner.setApplicationScrolling(false));
      }

      flowPane.setCache(true);
      flowPane.setCacheHint(CacheHint.SPEED);

      processedView(flowPane);
    } else {
      processedView(new StackPane());
    }
  }


  protected void dahsboardsLoaded(IOperationResult result) {
    if (result == null) {
      dahsboardsLoadError(new NullPointerException());
      return;
    }

    if (result.hasBusinessError()) {
      dahsboardsLoadError(new IllegalArgumentException());
      return;
    }

    if (result instanceof MultipleResult) {
      MultipleResult multipleResult = (MultipleResult) result;
      if (multipleResult.hasElements()) {

      }
    }

    dahsboardsLoadError(new IllegalArgumentException());
    return;
  }


  protected void dahsboardsLoadError(Throwable ex) {}


  protected void buildNoContentPane() {
    if (noContentPaneConfiguration == null) {
      noContentPaneConfiguration = getRootComponent().getComponentById("NoContentPane").orElse(null);
    }
  }
}
