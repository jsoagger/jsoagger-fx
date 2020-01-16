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
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.list.ConfigurableListViewLayout;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import io.github.jsoagger.jfxcore.engine.utils.IconUtils;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 6 févr. 2018
 */
public abstract class AbstractListViewTab extends StackPane implements ListViewTab {

  protected Hyperlink iconNode = new Hyperlink();
  protected ConfigurableListViewLayout listviewPaneContent;


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML tabConfiguration) {
    IconUtils.setIcon(iconNode, tabConfiguration);
    setTabIcon(iconNode);

    String title = NodeHelper.getTitle(tabConfiguration, (AbstractViewController) controller);
    setTabTile(title);
    iconNode.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    iconNode.setTooltip(new Tooltip(title));

    iconNode.setOnAction(e -> listviewPaneContent.select(this));
    getChildren().add(iconNode);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }


  /**
   * Getter of iconNode
   *
   * @return the iconNode
   */
  public Hyperlink getIconNode() {
    return iconNode;
  }


  /**
   * Setter of iconNode
   *
   * @param iconNode the iconNode to set
   */
  public void setIconNode(Hyperlink iconNode) {
    this.iconNode = iconNode;
  }


  /**
   * Getter of listviewPaneContent
   *
   * @return the listviewPaneContent
   */
  @Override
  public ConfigurableListViewLayout getListviewPaneContent() {
    return listviewPaneContent;
  }


  /**
   * Setter of listviewPaneContent
   *
   * @param listviewPaneContent the listviewPaneContent to set
   */
  @Override
  public void setListviewPaneContent(ConfigurableListViewLayout listviewPaneContent) {
    this.listviewPaneContent = listviewPaneContent;
  }
}
