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

package io.github.jsoagger.jfxcore.engine.components.list.comps;



import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 6 févr. 2018
 */
public class ListViewConfigurationTab extends AbstractListViewTab implements ListViewTab {

  private String tabTile;
  private Node tabIcon;
  private Node tabHeader;
  private Node tabContent;


  /**
   * Default Constructor
   */
  public ListViewConfigurationTab() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML tabConfiguration) {
    super.buildFrom(controller, tabConfiguration);
    tabContent = new StackPane();
    NodeHelper.setVgrow(tabContent);
  }


  /**
   * Getter of tabTile
   *
   * @return the tabTile
   */
  @Override
  public String getTabTile() {
    return tabTile;
  }


  /**
   * Setter of tabTile
   *
   * @param tabTile the tabTile to set
   */
  @Override
  public void setTabTile(String tabTile) {
    this.tabTile = tabTile;
  }


  /**
   * Getter of tabIcon
   *
   * @return the tabIcon
   */
  @Override
  public Node getTabIcon() {
    return tabIcon;
  }


  /**
   * Setter of tabIcon
   *
   * @param tabIcon the tabIcon to set
   */
  @Override
  public void setTabIcon(Node tabIcon) {
    this.tabIcon = tabIcon;
  }


  /**
   * Getter of tabHeader
   *
   * @return the tabHeader
   */
  @Override
  public Node getTabHeader() {
    return tabHeader;
  }


  /**
   * Setter of tabHeader
   *
   * @param tabHeader the tabHeader to set
   */
  @Override
  public void setTabHeader(Node tabHeader) {
    this.tabHeader = tabHeader;
  }


  /**
   * Getter of tabContent
   *
   * @return the tabContent
   */
  @Override
  public Node getTabContent() {
    return tabContent;
  }


  /**
   * Setter of tabContent
   *
   * @param tabContent the tabContent to set
   */
  @Override
  public void setTabContent(Node tabContent) {
    this.tabContent = tabContent;
  }
}
