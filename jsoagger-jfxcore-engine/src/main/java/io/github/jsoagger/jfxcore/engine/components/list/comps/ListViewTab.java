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



import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.list.ConfigurableListViewLayout;

import javafx.scene.Node;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 6 févr. 2018
 */
public interface ListViewTab extends IBuildable {

  /**
   *
   */
  public void setListviewPaneContent(ConfigurableListViewLayout content);


  /**
   *
   */
  public ConfigurableListViewLayout getListviewPaneContent();


  /**
   * Getter of tabTile
   *
   * @return the tabTile
   */
  public String getTabTile();


  /**
   * Setter of tabTile
   *
   * @param tabTile the tabTile to set
   */
  public void setTabTile(String tabTile);


  /**
   * Getter of tabIcon
   *
   * @return the tabIcon
   */
  public Node getTabIcon();


  /**
   * Setter of tabIcon
   *
   * @param tabIcon the tabIcon to set
   */
  public void setTabIcon(Node tabIcon);


  /**
   * Getter of tabHeader
   *
   * @return the tabHeader
   */
  public Node getTabHeader();


  /**
   * Setter of tabHeader
   *
   * @param tabHeader the tabHeader to set
   */
  public void setTabHeader(Node tabHeader);


  /**
   * Getter of tabContent
   *
   * @return the tabContent
   */
  public Node getTabContent();


  /**
   * Setter of tabContent
   *
   * @param tabContent the tabContent to set
   */
  public void setTabContent(Node tabContent);
}
