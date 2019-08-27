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

package io.github.jsoagger.jfxcore.engine.controller.detailsview.layout;



import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.tab.VLTab;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 16 févr. 2018
 */
public abstract class DetailsView extends StackPane {

  protected AbstractViewController controller;
  protected List<VLTab> tabs = new ArrayList<>();


  public void buildTabs(VLViewComponentXML rootComponent) {
    VLViewComponentXML detailsViewTabs = rootComponent.getComponentById("DetailsViewTabs").orElse(null);
    if (detailsViewTabs != null && detailsViewTabs.hasSubComponent()) {
      for (VLViewComponentXML tabGroupConfig : detailsViewTabs.getSubcomponents()) {
        buildTabGroup(tabGroupConfig);
      }
    }

    // select first tab
    selectTab(0);
  }


  protected void buildTabGroup(VLViewComponentXML tabGroupConfig) {
    for (VLViewComponentXML tabConfig : tabGroupConfig.getSubcomponents()) {
      VLTab tab = new VLTab(controller, tabConfig);
      tab.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> selectTab(tab));
      tabs.add(tab);
    }
  }


  public void selectTab(final int i) {
    if (i < tabs.size()) {
      selectTab(tabs.get(i));
    }
  }


  /**
   * Select a tab
   *
   * @param tab
   */
  public void selectTab(final VLTab tab) {
    tab.select(true);
    if (tab.getConfig().hasSubComponent()) {
      String getRootView = tab.getConfig().getSubcomponents().get(0).getRootView();
      if (StringUtils.isNotBlank(getRootView)) {
        AbstractViewController controller = tab.getContent(getRootView);
        Platform.runLater(() -> {
          getTabContentArea().getChildren().clear();

          Node processed = controller.processedView();
          NodeHelper.setHVGrow(getTabContentArea(), processed);
          getTabContentArea().getChildren().addAll(processed);
        });
      } else {
        setEmptyContent();
      }
    } else {
      setEmptyContent();
    }

    for (final VLTab styledTab : tabs) {
      if (!styledTab.getTitle().getText().equals(tab.getTitle().getText())) {
        styledTab.select(false);
      }
    }
  }


  private void setEmptyContent() {
    Platform.runLater(() -> {
      getTabContentArea().getChildren().clear();
      getTabContentArea().getChildren().addAll(new StackPane());
    });
  }


  public abstract Pane getTabContentArea();
}
