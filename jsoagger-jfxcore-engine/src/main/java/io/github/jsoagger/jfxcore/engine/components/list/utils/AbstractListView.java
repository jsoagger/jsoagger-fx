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

package io.github.jsoagger.jfxcore.engine.components.list.utils;


import io.github.jsoagger.core.utils.StringUtils;

import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

/**
 * A list view.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public abstract class AbstractListView<T> extends ListView {

  protected String id;
  protected String styleClass;
  protected String cellFactory;

  protected AbstractViewController controller;
  protected VLViewComponentXML configuration;


  public AbstractListView() {
    getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
  }


  /**
   * !!important, do not select 0 automatically. Views that need to select first items must call it
   * explicitly because in some case (multiple listview view displayed in on page for example), it
   * leads to unconsistent behaviour.
   */
  public void selectFirstItem() {
    getSelectionModel().select(0);
  }


  // @formatter:off
  /**
   * <component id="ListView"> <properties> <!-- nullable --> <property name="id" value="id" />
   * <property name="styleClass" value="styleClass" />
   *
   * <!-- mandatory --> <property name="cellFactory" value="cellFactoryBean" /> </properties>
   * </component>
   */
  // @formatter:on
  public void loadConfiguration() {
    id = configuration.propertyValueOf(XMLConstants.ID).orElse(null);
    styleClass = configuration.propertyValueOf(XMLConstants.STYLE_CLASS).orElse(null);
    cellFactory = configuration.propertyValueOf(XMLConstants.CELL_FACTORY).orElseThrow(IllegalArgumentException::new);

    // set the ID
    if (StringUtils.isNotBlank(id)) {
      setId(id);
    }

    // set style class
    if (StringUtils.isNotBlank(styleClass)) {
      getStyleClass().addAll(styleClass.split(","));
    }

    // process cell factory
    setCellFactory(p -> {
      return buildCellFactory();
    });
  }


  public ListCell<T> buildCellFactory() {
    // final AbstractListCell<T> cell = (AbstractListCell<T>)
    // Services.getBean(cellFactory);
    // return cell;
    return null;
  }


  public VLViewComponentXML getContextMenuConfiguration() {
    if ((configuration != null) && configuration.hasSubComponent()) {
      for (VLViewComponentXML subComp : configuration.getSubcomponents()) {
        if (subComp.getId().equalsIgnoreCase("ContextMenu")) {
          return subComp;
        }
      }
    }

    return null;
  }


  public VLViewComponentXML getEllipsisMenuConfiguration() {
    if ((configuration != null) && configuration.hasSubComponent()) {
      for (VLViewComponentXML subComp : configuration.getSubcomponents()) {
        if (subComp.getId().equalsIgnoreCase("EllipsisMenu")) {
          return subComp;
        }
      }
    }

    return null;
  }


  public void doRefreshDatas() {

  }


  public void copyListViewSelectedRows() {

  }


  public void copyListViewFromContextMenu() {

  }


  public void processDeletionOfSelectedElementsFromCheckBox() {

  }


  public void processDeletionOfSelectedElementsFromContextMenu() {

  }


  public void removeCurrentSelectedEntry() {

  }
}
