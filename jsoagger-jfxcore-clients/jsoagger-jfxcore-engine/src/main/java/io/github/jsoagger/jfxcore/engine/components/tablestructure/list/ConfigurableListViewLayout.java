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

package io.github.jsoagger.jfxcore.engine.components.tablestructure.list;


import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.ICountableElements;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IFieldset;
import io.github.jsoagger.jfxcore.api.IFieldsetContent;
import io.github.jsoagger.jfxcore.api.IListviewPaneContent;
import io.github.jsoagger.jfxcore.api.ISelectableComponent;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.list.comps.ListViewTab;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.api.services.Services;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ConfigurableListViewLayout extends VBox implements ICountableElements, IFieldsetContent, IBuildable {

  protected VBox rootLayout = new VBox();

  protected HBox footerWrapper = new HBox();
  protected StackPane headerWrapper = new StackPane();
  protected Content contentWrapper = new Content();
  protected IFieldset owner;

  protected AbstractViewController controller = null;
  protected VLViewComponentXML rootConfiguration = null;

  protected IListviewPaneContent content = null;
  protected List<ListViewTab> tabs = new ArrayList<>();

  /*-----------------------------------------------------------------------------
  | Configurations
   *=============================================================================*/


  /**
   * Constructor
   */
  public ConfigurableListViewLayout() {
    rootLayout.getStyleClass().add("ep-advanced-list-view-pane");
    NodeHelper.setHVGrow(rootLayout);

    contentWrapper.getStyleClass().add("ep-advanced-list-view-pane-content-container");
    NodeHelper.setVgrow(contentWrapper);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void build(VLViewComponentXML configuration, IJSoaggerController controller) {
    buildFrom(controller, configuration.getNullableComponentById("ListContentDefinition"));
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    try {
      this.controller = (AbstractViewController) controller;
      this.rootConfiguration = configuration;

      buildListView();
      buildTabs();

      getChildren().add(headerWrapper);
      headerWrapper.getStyleClass().add("ep-advanced-list-view-pane-header-container");
      headerWrapper.managedProperty().bind(headerWrapper.visibleProperty());
      headerWrapper.setVisible(false);

      getChildren().add(rootLayout);
      rootLayout.getChildren().add(contentWrapper);

      NodeHelper.setHgrow(footerWrapper);
      footerWrapper.getStyleClass().add("ep-advanced-list-view-pane-bottom-tabs-container");
      getChildren().add(footerWrapper);
      AnchorPane.setBottomAnchor(footerWrapper, 0.0);
      AnchorPane.setLeftAnchor(footerWrapper, 0.0);
      AnchorPane.setRightAnchor(footerWrapper, 0.0);

      for (ListViewTab listViewTab : tabs) {
        NodeHelper.setHgrow(listViewTab.getDisplay());
        footerWrapper.getChildren().add(listViewTab.getDisplay());
      }

      contentWrapper.getScroll().setVbarPolicy(ScrollBarPolicy.NEVER);
      contentWrapper.getScroll().setHbarPolicy(ScrollBarPolicy.NEVER);
      select(tabs.get(0));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private boolean currentTabHaveHeader = false;


  public void select(ListViewTab tab) {
    contentWrapper.setContentItem(tab.getTabContent());
    currentTabHaveHeader = tab.getTabHeader() != null;
    if (currentTabHaveHeader) {
      headerWrapper.setVisible(true);
      headerWrapper.getChildren().clear();
      headerWrapper.getChildren().add(tab.getTabHeader());
    } else {
      headerWrapper.setVisible(false);
    }
  }


  /**
   * Build listview
   */
  protected void buildListView() {
    content = new ListViewContent();
    content.buildFrom((IJSoaggerController) controller, rootConfiguration);
  }


  /**
   * Buils all tabs
   */
  protected void buildTabs() {
    VLViewComponentXML tabsConfiguration = rootConfiguration.getComponentById("ListViewTabs").orElse(null);
    if ((tabsConfiguration != null) && tabsConfiguration.hasSubComponent()) {
      for (VLViewComponentXML tabConfig : tabsConfiguration.getSubcomponents()) {
        String contentImpl = tabConfig.getPropertyValue("contentImpl");
        if (StringUtils.isNotBlank(contentImpl)) {
          ListViewTab listViewTab = (ListViewTab) Services.getBean(contentImpl);
          listViewTab.setListviewPaneContent(this);
          listViewTab.buildFrom((IJSoaggerController) controller, tabConfig);
          tabs.add(listViewTab);
        }
      }
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public VLViewComponentXML getConfiguration() {
    return rootConfiguration;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setConfiguration(VLViewComponentXML configuration) {
    this.rootConfiguration = configuration;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public IJSoaggerController getController() {
    return (IJSoaggerController) controller;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setController(IJSoaggerController controller) {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public boolean isValid() {
    return true;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setValid(boolean isValid) {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void validate() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void displayErrors() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public List<ISelectableComponent> getSelectableComponents() {
    return null;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getHeaderLabel() {
    return null;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public SimpleIntegerProperty elementsCountProperty() {
    return content.elementsCountProperty();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setOwner(IFieldset owner) {
    this.owner = owner;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public IFieldset getOwner() {
    return owner;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public SimpleStringProperty labelProperty() {
    return null;
  }


  /**
   * Getter of content
   *
   * @return the content
   */
  public IListviewPaneContent getContent() {
    return content;
  }

  private static class Content extends StackPane {

    private ScrollPane scroll = new ScrollPane();


    /**
     * Default Constructor
     */
    public Content() {
      getChildren().add(scroll);
      scroll.setFitToHeight(true);
      scroll.setFitToWidth(true);
    }


    public void setContentItem(Node content) {
      content.setCache(true);
      content.setCacheHint(CacheHint.SPEED);
      scroll.setContent(content);
      NodeHelper.setHVGrow(content);
    }


    /**
     * Getter of scroll
     *
     * @return the scroll
     */
    public ScrollPane getScroll() {
      return scroll;
    }
  }


  /**
   * Getter of items
   *
   * @return the items
   */
  public ObservableList<OperationData> getItems() {
    return ((ListViewContent) content).getItems();
  }


  /**
   *
   */
  public void resetItems() {
    ((ListViewContent) content).resetItems();
  }


  /**
   *
   */
  public void setItems(ObservableList<OperationData> subentries) {
    ((ListViewContent) content).setItems(subentries);
  }
}
