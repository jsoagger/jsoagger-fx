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

package io.github.jsoagger.jfxcore.engine.components.menu;



import org.kordamp.ikonli.javafx.FontIcon;

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewRootMenuRowXML;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.menu.Menu.MenuStructure;
import io.github.jsoagger.jfxcore.engine.components.menu.Menu.SubMenu;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * A row in th e main menu.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class MenuRow extends HBox {

  private static final String MENU_ROW_LABEL = "menu-row-label";
  private static final String EP_MENU_ROW = "ep-menu-row";

  /** The root menu Configuration */
  protected VLViewRootMenuRowXML menuItemConfig = null;
  protected AbstractViewController controller = null;

  protected final SimpleBooleanProperty leaf = new SimpleBooleanProperty(true);
  protected SimpleBooleanProperty isSelected = new SimpleBooleanProperty(false);

  protected Menu menu;
  protected MenuStructure menuStructure = null;
  protected SubMenu subMenu = null;
  protected int index = -1;

  protected final Hyperlink label = new Hyperlink();
  protected FontIcon icon = null;

  protected final Hyperlink collpasedIcon = new Hyperlink();
  protected final Hyperlink expandIcon = new Hyperlink();


  /**
   * Constructor
   */
  public MenuRow(VLViewRootMenuRowXML menuItemConfig, Menu menu, MenuStructure menuStructure, AbstractViewController controller) {

    this.menu = menu;
    this.menuItemConfig = menuItemConfig;
    this.menuStructure = menuStructure;
    this.controller = controller;

    leaf.set(menuItemConfig.hasSubRows());

    buildRowItem();
    getStyleClass().add(EP_MENU_ROW);
  }


  public void setStyleClass(String rowStyleClass) {
    getStyleClass().removeAll(EP_MENU_ROW);
    getStyleClass().addAll(rowStyleClass.split(","));
  }


  public void setLabelStyle(String menuLabelStyleClass) {
    label.getStyleClass().remove(MENU_ROW_LABEL);
    label.getStyleClass().addAll(menuLabelStyleClass.split(","));
  }


  protected void buildRowItem() {
    IconUtils.setIcon(label, menuItemConfig);

    // the tooltip
    final String tooltipKey = menuItemConfig.getToolTip();
    if (StringUtils.isNotEmpty(tooltipKey)) {
      final String tooltip = controller.getLocalised(tooltipKey);
      label.setTooltip(new Tooltip(tooltip));
    }

    // the title of the row menu
    boolean uppercase = menuItemConfig.getBooleanProperty("upperCase", false);
    final String title = uppercase ? controller.getLocalised(menuItemConfig.getLabel()).toUpperCase() : controller.getLocalised(menuItemConfig.getLabel());
    label.setText(title);
    label.setWrapText(true);

    buildMenuAction();
    getChildren().addAll(label);
  }


  private void buildMenuAction() {
    EventHandler<MouseEvent> a = e -> {
      e.consume();

      if(subMenu == null) {
        HidePMTask hidePMTask = new HidePMTask();
        new Thread(hidePMTask).start();
      }

      PMDoActionTask actionTask = new PMDoActionTask();
      new Thread(actionTask).start();
    };

    addEventFilter(MouseEvent.MOUSE_CLICKED, a);
  }


  /**
   * @param subMenu
   */
  public void setSubmenu(SubMenu subm) {
    subMenu = subm;

    subm.managedProperty().bind(subm.visibleProperty());
    subm.setVisible(false);

    IconUtils.setFontIcon("fa-plus:16", collpasedIcon);
    IconUtils.setFontIcon("fa-minus:16", expandIcon);

    collpasedIcon.managedProperty().bind(collpasedIcon.visibleProperty());
    expandIcon.managedProperty().bind(expandIcon.visibleProperty());

    collpasedIcon.getGraphic().setOpacity(0.35);
    expandIcon.getGraphic().setOpacity(0.35);

    StackPane pane = new StackPane();
    pane.getChildren().addAll(collpasedIcon, expandIcon);
    getChildren().addAll(NodeHelper.horizontalSpacer(), pane);

    addEventFilter(MouseEvent.MOUSE_CLICKED, a -> collapseExpandSubmenu());
    collpasedIcon.addEventFilter(ActionEvent.ACTION, e -> collapseExpandSubmenu());
    expandIcon.addEventFilter(ActionEvent.ACTION, e -> collapseExpandSubmenu());

    pseudoClassStateChanged(PseudoClass.getPseudoClass("notleaf"), true);
    label.pseudoClassStateChanged(PseudoClass.getPseudoClass("notleaf"), true);
    menu.getChildren().add(subMenu);

    subMenu.setVisible(false);
    collpasedIcon.setVisible(true);
    expandIcon.setVisible(false);
  }

  private boolean isExpanded = false;


  private void collapseExpandSubmenu() {
    isExpanded = !isExpanded;
    subMenu.setVisible(isExpanded);
    collpasedIcon.setVisible(!isExpanded);
    expandIcon.setVisible(isExpanded);
  }


  /**
   * Pseudo class state change
   *
   * @param b
   */
  public void setSelected(boolean b) {
    pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), b);
  }


  /**
   * Return true if menu is currently selected
   *
   * @return
   */
  public BooleanProperty selectedProperty() {
    return isSelected;
  }


  public void setIndex(int index) {
    this.index = index;
  }


  public SimpleBooleanProperty leafProperty() {
    return leaf;
  }


  /**
   * Getter of menuItemConfig
   *
   * @return the menuItemConfig
   */
  public VLViewRootMenuRowXML getMenuItemConfig() {
    return menuItemConfig;
  }


  /**
   *
   */
  public void fireAction() {
    Event.fireEvent(this, new MouseEvent(MouseEvent.MOUSE_CLICKED, this.getLayoutX(), this.getLayoutY(), this.getLayoutX(), this.getLayoutY(), MouseButton.PRIMARY, 1, true, true, true, true, true,
        true, true, true, true, true, null));
  }


  /**
   * Getter of subMenu
   *
   * @return the subMenu
   */
  public SubMenu getSubMenu() {
    return subMenu;
  }


  /**
   * Task fo hidind the primary menu on action
   * @author Ramilafananana VONJISOA
   *
   */
  private class HidePMTask extends Task<Void>{

    @Override
    protected Void call() throws Exception {
      controller.getRootStructure().hidePrimaryMenu();
      return null;
    }
  }

  /**
   * Task for doing over actions
   * @author Ramilafananana VONJISOA
   *
   */
  private class PMDoActionTask extends Task<Void>{

    @Override
    protected Void call() throws Exception {
      menu.deselectAll();
      menu.select(menuItemConfig.getId());

      // and then
      // if setRootStructure(REPLACE ENTIRE VIEW BY A NEW VIEW)
      if (StringUtils.isNotBlank(menuItemConfig.getRootStructure())) {
        MenuHelper.loadRootStructure(menuItemConfig.getRootStructure());
      }

      // load a child view
      else if (StringUtils.isNotBlank(menuItemConfig.getLoadChildView())) {
        MenuHelper.loadChildView(menuItemConfig.getLink(), controller.getRootStructure());
        String location = NodeHelper.location(menuItemConfig, controller);
      }

      // setRootviewContent
      // (REPLACE CURRENT CONTENT WITH NEW CONTENT, VIA
      // PUSH, THE ROOT STRUCTURE IS NOT REPLACED)
      // FIRE AN EVENT - BuildRSContentEvent
      // -> THE VIEW MUST NOW THAT A ROOT INTERNAL MENU WAS CLICKED
      // THE HISTORY SHOULD BE REINITIALIZED
      else if (StringUtils.isNotBlank(menuItemConfig.getSetRootviewContent())) {
        String location = NodeHelper.location(menuItemConfig, controller);
        MenuHelper.loadViewContent(location, menuItemConfig.getSetRootviewContent(), controller.getRootStructure());
      }

      // simple action
      // (DO AN ACTION, NO VIEW TO LOAD)
      else if (StringUtils.isNotEmpty(menuItemConfig.getAction())) {
        MenuHelper.doAction(menuItemConfig.getAction(), controller);
      }

      // show a wizard
      else if (StringUtils.isNotEmpty(menuItemConfig.getShowWizardAction())) {
        MenuHelper.doShowWizardAction(menuItemConfig.getShowWizardAction(), controller);
      }

      // updateRSContentTo
      // FIRE AN EVENT - PushStructureContentEvent
      // THE HISTORY IS NOT REINITIALIZED,
      // ADD THIS VIEW TO HISTORY, PREVIOUS VIEW CAN BE ACCESSED BY BACK
      else if (StringUtils.isNotEmpty(menuItemConfig.updateRSContentTo())) {
        String location = NodeHelper.location(menuItemConfig, controller);
        MenuHelper.updateRSContentTo(location, menuItemConfig.updateRSContentTo(), controller);
      }
      return null;
    }
  }
}
