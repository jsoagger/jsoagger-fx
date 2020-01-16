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


import org.kordamp.ikonli.javafx.FontIcon;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.components.control9.CustomTextField;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 6 févr. 2018
 */
public class ListViewItemsListTab extends AbstractListViewTab implements ListViewTab {

  private String tabTile;
  private Node tabIcon;
  private StackPane tabHeader;

  private CustomTextField searchField = new CustomTextField();


  /**
   * Default Constructor
   */
  public ListViewItemsListTab() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML tabConfiguration) {
    super.buildFrom(controller, tabConfiguration);

    tabHeader = new StackPane();
    buildSearchFilter();
  }


  private void buildSearchFilter() {
    tabHeader.getChildren().add(searchField);
    searchField.getStyleClass().removeAll("custom-text-field");
    searchField.getStyleClass().add("list-view-header-search-field");

    // right search icon
    Label icon = new Label();
    icon.setStyle("-fx-padding:0 0 0 8");
    FontIcon searchfontIcon = new FontIcon();
    searchfontIcon.setStyle("-fx-icon-color:-accent-color-300;-fx-icon-code:mdi-magnify;-fx-icon-size:18;");
    icon.setGraphic(searchfontIcon);

    // left filters button
    FontIcon fontIcon = new FontIcon();
    fontIcon.setStyle("-fx-icon-color:-accent-color-300;-fx-icon-code:mdi-filter;-fx-icon-size:18;");

    HBox rightnode = new HBox();
    rightnode.setAlignment(Pos.CENTER);
    rightnode.getChildren().addAll(icon);
    searchField.setLeft(rightnode);

    searchField.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
      search(oldValue, newValue);
    });
  }

  public void search(String oldVal, String newVal) {
    if ((oldVal != null) && (newVal.length() < oldVal.length())) {
      listviewPaneContent.resetItems();
    }

    String value = searchField.getText().toUpperCase();
    ObservableList<OperationData> subentries = FXCollections.observableArrayList();
    for (OperationData entry : listviewPaneContent.getItems()) {
      boolean match = true;
      String entryText = entry.getMasterAttributes().get("name") == null ? "name" : (String) entry.getMasterAttributes().get("name");
      if (!entryText.toUpperCase().contains(value)) {
        match = false;
        continue;
      }

      if (match) {
        subentries.add(entry);
      }
    }

    listviewPaneContent.setItems(subentries);
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
  public void setTabHeader(Node tabHeader) {}


  /**
   * Getter of tabContent
   *
   * @return the tabContent
   */
  @Override
  public Node getTabContent() {
    return listviewPaneContent.getContent().getDisplay();
  }


  /**
   * Setter of tabContent
   *
   * @param tabContent the tabContent to set
   */
  @Override
  public void setTabContent(Node tabContent) {}
}
