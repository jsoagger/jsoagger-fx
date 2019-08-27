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

package io.github.jsoagger.jfxcore.engine.components.menu.ternary;




import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.events.MenuPos;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Right notification pane. Container of elements displayed on right side of the application.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class TernaryMenuTabPane extends StackPane implements IBuildable {

  private List<TernaryMenuTab> tabs = new ArrayList<>();
  private VBox tabContentContainer = new VBox();
  private TernaryMenuTabHeader tabHeaderContainer = null;
  private MenuPos position = null;
  private TernaryMenuTab currentSelectedTab = null;

  private VBox layout = new VBox();


  /**
   * Constructor
   */
  public TernaryMenuTabPane() {
    super();
    layout.getStyleClass().add("ternary-menu");
    getStyleClass().add("ternary-menu-external");
    tabHeaderContainer = new TernaryMenuTabHeader();
    getChildren().add(layout);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    tabContentContainer.getStyleClass().addAll("ternary-menu-content");
    NodeHelper.setVgrow(tabContentContainer);
    layout.getChildren().addAll(tabHeaderContainer, tabContentContainer);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }


  /**
   * Select the first tab of all tabs: tab index 0
   */
  public void selectFirstTab() {
    select(tabs.get(0));
  }


  /**
   * Select the given tab
   *
   * @param tab
   */
  public void select(TernaryMenuTab tab) {
    for(TernaryMenuTab e: tabs) {
      e.select(false);
    }

    tab.select(true);
    tabContentContainer.getChildren().clear();
    LoadTabContentTask t= new LoadTabContentTask(tab);
    new Thread(t).start();
  }


  /**
   * Add a tab
   *
   * @param tab
   */
  public void addTab(TernaryMenuTab tab) {
    tabs.add(tab);
    tab.setTabPane(this);
    tabHeaderContainer.addTab(tab);
  }


  /**
   *
   * @param node
   */
  public void setPinContentActionButton(Node node) {
    tabHeaderContainer.setPinContentActionButton(node);
  }


  /**
   * Get the position
   *
   * @return the position
   */
  public MenuPos getPosition() {
    return position;
  }


  private class LoadTabContentTask extends Task<Void>{
    TernaryMenuTab tab;
    LoadTabContentTask(TernaryMenuTab tab){
      this.tab = tab;
    }

    @Override
    protected Void call() throws Exception {
      tabHeaderContainer.setTitle(tab.getTitle());
      tabHeaderContainer.setCustomActions(tab.getActions());
      Platform.runLater(()->{
        tabContentContainer.getChildren().add(tab.getContent());
      });
      currentSelectedTab = tab;

      return null;
    }

    @Override
    protected void setException(Throwable t) {
      super.setException(t);
      t.printStackTrace();
    }
  }
}
