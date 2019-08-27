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




import java.util.Iterator;
import java.util.List;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IAccessRuleResolver;
import io.github.jsoagger.jfxcore.api.IAccessRuleResolver.UIAccessRule;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IDynamicMenuProvider;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IMinimizable;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewConfigXML;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewRootMenuGroupXML;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewRootMenuRowXML;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewRootMenuRowsXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public abstract class Menu extends StackPane implements IBuildable, IMinimizable {

  protected static final String EP_MENU_CONTAINER_CSS = "ep-menu-container";

  /**
   * The identifier of current selected item from the menu. Minimized and maximized menus should have
   * same selected row.
   */
  protected SimpleObjectProperty<String> selectedItem = new SimpleObjectProperty<>();
  protected SimpleBooleanProperty maximized = new SimpleBooleanProperty(true);

  protected MenuStructure maximizedMenu;
  protected MenuStructure minimizedMenu;

  protected AbstractViewController controller;
  protected VLViewConfigXML menuConfiguration;
  protected VLViewRootMenuGroupXML minimizedConfiguration;
  protected VLViewRootMenuGroupXML maximizedConfiguration;
  protected VLViewRootMenuRowsXML menuRows;


  /**
   * Constructor
   */
  public Menu() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.controller = (AbstractViewController) controller;

    // always grow verticaly, and pref horizontaly
    setPrefHeight(VBox.USE_COMPUTED_SIZE);

    if (maximizedConfiguration != null) {
      maximizedMenu = new MenuStructure(this, maximizedConfiguration);
      maximizedMenu.managedProperty().bind(maximizedMenu.visibleProperty());
      getChildren().add(maximizedMenu);
    }

    if (minimizedConfiguration != null) {
      minimizedMenu = new MenuStructure(this, minimizedConfiguration);
      minimizedMenu.managedProperty().bind(minimizedMenu.visibleProperty());
      getChildren().add(minimizedMenu);
    }

    if ((minimizedMenu != null) && (maximizedMenu != null)) {
      minimizedMenu.visibleProperty().bind(maximized.not());
      maximizedMenu.visibleProperty().bind(maximized);
    }

    if (maximizedMenu != null) {
      maximizedMenu.toFront();
    }
  }


  /**
   * DeselectAll rows
   */
  public void deselectAll() {
    if (minimizedMenu != null) {
      for(MenuRow r : minimizedMenu.rows) {
        r.setSelected(false);
      }
    }

    if (maximizedMenu != null) {
      for(MenuRow r : maximizedMenu.rows) {
        r.setSelected(false);
      }
    }
  }


  public void select(String id, boolean fire) {
    select(id);
    if (fire) {
      for (final MenuRow row : minimizedMenu.rows) {
        if (id.equalsIgnoreCase(row.getMenuItemConfig().getId())) {
          row.fireAction();
        }
      }
    }
  }


  /**
   * @param id
   */
  public void select(String id) {
    if (minimizedMenu != null) {
      for (final MenuRow row : minimizedMenu.rows) {
        if (id.equalsIgnoreCase(row.getMenuItemConfig().getId())) {
          row.setSelected(true);
        }
      }
    }

    if (maximizedMenu != null) {
      for (final MenuRow row : maximizedMenu.rows) {
        if (id.equalsIgnoreCase(row.getMenuItemConfig().getId())) {
          row.setSelected(true);
        }
      }
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void minimize() {
    maximized.set(false);
    minimizedMenu.toFront();
    pseudoClassStateChanged(PseudoClass.getPseudoClass("minimized"), true);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void maximize() {
    maximized.set(true);
    maximizedMenu.toFront();
    pseudoClassStateChanged(PseudoClass.getPseudoClass("minimized"), false);
  }

  /**
   * @author Ramilafananana VONJISOA
   * @mailto yvonjisoa@nexitia.com
   * @date 2019
   */
  protected class MenuStructure extends VBox {

    protected Menu menu;
    protected ObservableList<MenuRow> rows = FXCollections.observableArrayList();
    protected VLViewRootMenuGroupXML menuConfiguration;

    protected SimpleObjectProperty<ContentDisplay> contenDisplay = new SimpleObjectProperty<ContentDisplay>(ContentDisplay.GRAPHIC_ONLY);
    protected SimpleBooleanProperty collpasible = new SimpleBooleanProperty(true);

    protected String styleClass;
    protected String menuWrapperStyleClass;
    protected String menuStyleClass;
    protected String menuRowsStyleClass;
    protected String menuLabelStyleClass;

    protected String subMenuStyleClass;
    protected String subMenuRowsStyleClass;
    protected String subMenuLabelStyleClass;

    private final VBox menuRowsContainer = new VBox();


    /**
     * Constructor
     *
     * @param menu
     */
    public MenuStructure(Menu menu, VLViewRootMenuGroupXML menuConfiguration) {
      this.menu = menu;
      this.menuConfiguration = menuConfiguration;

      // if has style class defined by user
      subMenuStyleClass = menuConfiguration.getPropertyValue("subMenuStyleClass");
      subMenuRowsStyleClass = menuConfiguration.getPropertyValue("subMenuRowsStyleClass");
      subMenuLabelStyleClass = menuConfiguration.getPropertyValue("subMenuLabelStyleClass");

      styleClass = menuConfiguration.getPropertyValue("styleClass");
      menuWrapperStyleClass = menuConfiguration.getPropertyValue("menuWrapperStyleClass");
      menuStyleClass = menuConfiguration.getPropertyValue("menuStyleClass");
      menuRowsStyleClass = menuConfiguration.getPropertyValue("menuRowsStyleClass");
      menuLabelStyleClass = menuConfiguration.getPropertyValue("menuLabelStyleClass");

      if (StringUtils.isNotBlank(menuStyleClass)) {
        getStyleClass().addAll(menuStyleClass.split(","));
      }

      if (StringUtils.isNotBlank(menuWrapperStyleClass)) {
        this.menuRowsContainer.getStyleClass().addAll(menuWrapperStyleClass.split(","));
      } else {
        menuRowsContainer.getStyleClass().add("ep-menu-rows-wrapper");
      }

      if (StringUtils.isNotBlank(styleClass)) {
        this.menu.getStyleClass().addAll(styleClass.split(","));
      }

      // process content display
      String cd = menuConfiguration.getPropertyValue("contentDisplay");
      if (StringUtils.isEmpty(cd)) {
        cd = ContentDisplay.RIGHT.name();
      }
      contenDisplay.set(ContentDisplay.valueOf(cd.toUpperCase()));
      buildRows();
      getChildren().add(NodeHelper.makeCentralScrollable(menuRowsContainer));
    }


    /**
     * @param submenuItem
     */
    public void addRow(MenuRow row) {
      menuRowsContainer.getChildren().add(row);
      rows.add(row);

      if (row.getSubMenu() != null) {
        menuRowsContainer.getChildren().add(row.getSubMenu());
      }
    }


    /**
     * Build all rows of the menu
     *
     * @param menuConfiguration2
     */
    protected void buildRows() {
      final List<VLViewRootMenuRowXML> menusList = menuRows.getRows();
      for (final Iterator iterator = menusList.iterator(); iterator.hasNext();) {
        final VLViewRootMenuRowXML menuRowConfig = (VLViewRootMenuRowXML) iterator.next();
        final UIAccessRule accessRule = getAccessAttribute(menuRowConfig);
        switch (accessRule) {
          case DISABLED:
          case SHOW:
            buildRow(menuRowConfig, iterator.hasNext());
            break;

          default:
            break;
        }
      }
    }


    protected void buildRow(VLViewRootMenuRowXML menuItemConfig, boolean hasNext) {
      final MenuRow menuItem = new MenuRow(menuItemConfig, menu, this, controller);
      if (!hasNext) {
        menuItem.pseudoClassStateChanged(PseudoClass.getPseudoClass("last"), true);
      }

      menuItem.setIndex(getChildren().indexOf(menuItem));
      if (StringUtils.isNotBlank(menuRowsStyleClass)) {
        menuItem.setStyleClass(menuRowsStyleClass);
      }

      if (StringUtils.isNotBlank(menuLabelStyleClass)) {
        menuItem.setLabelStyle(menuLabelStyleClass);
      }

      // if (hasNext && menuItemConfig.addSeparatorAfter()) {
      if (menuItemConfig.addSeparatorAfter()) {
        menuRowsContainer.getChildren().add(new Separator());
      }

      if (menuItemConfig.hasSubRows()) {
        final SubMenu subMenu = new SubMenu(menu, this);
        subMenu.build(menuItemConfig.getSubMenus(), menuItem);
        menuItem.setSubmenu(subMenu);
      }

      addRow(menuItem);
      if (!hasNext && menuItemConfig.addSeparatorAfter()) {
        menuItem.pseudoClassStateChanged(PseudoClass.getPseudoClass("last"), true);
        menuRowsContainer.getChildren().add(new Separator());
      }
    }


    private UIAccessRule getAccessAttribute(VLViewRootMenuRowXML menuItemConfig) {
      if (menuItemConfig != null) {
        final String beanName = menuItemConfig.getRuleResolverName();
        if (StringUtils.isNotBlank(beanName)) {
          final IAccessRuleResolver accessRuleResolver = (IAccessRuleResolver) controller.getSpringBean(beanName);
          if(accessRuleResolver == null) {
            System.out.println("WARNING : Access rule resolver not found in context : " + accessRuleResolver);
          }
          else {
            return accessRuleResolver.isAccessible(controller, null);
          }
        }
      }

      return UIAccessRule.SHOW;
    }


    /**
     * Getter of rows
     *
     * @return the rows
     */
    public ObservableList<MenuRow> getRows() {
      return rows;
    }


    /**
     * Setter of rows
     *
     * @param rows the rows to set
     */
    public void setRows(ObservableList<MenuRow> rows) {
      this.rows = rows;
    }
  }

  /**
   * @author Ramilafananana VONJISOA
   * @mailto yvonjisoa@nexitia.com
   * @date 2019
   */
  public class SubMenu extends VBox {

    private final Menu menu;
    private final MenuStructure menuStructure;


    /**
     * Constructor
     */
    public SubMenu(Menu menu, MenuStructure menuStructure) {
      this.menu = menu;
      this.menuStructure = menuStructure;

      if (StringUtils.isNotBlank(menuStructure.subMenuStyleClass)) {
        getStyleClass().addAll(menuStructure.subMenuStyleClass.split(","));
      } else {
        getStyleClass().add("sub-menu");
      }

      NodeHelper.setHgrow(this);
    }


    public void build(List<VLViewRootMenuRowXML> submenuCfgs, MenuRow row) {
      for(VLViewRootMenuRowXML submenuCfg: submenuCfgs) {
        final MenuRow submenurow = buildSubMenu(submenuCfg);
        getChildren().add(submenurow);
      }

      // handle dynamic row
      final String userMenusProvider = row.menuItemConfig.getPropertyValueByName("userMenusProvider");
      if (StringUtils.isNotBlank(userMenusProvider)) {
        final IDynamicMenuProvider provider = (IDynamicMenuProvider) Services.getBean(userMenusProvider);
        provider.getRows(controller, rows -> {
          for(VLViewRootMenuRowXML r: rows) {
            final MenuRow submenurow = buildSubMenu(r);
            getChildren().add(submenurow);
          }
        });
      }
    }


    private MenuRow buildSubMenu(VLViewRootMenuRowXML submenuCfg) {
      final MenuRow submenuItem = new MenuRow(submenuCfg, menu, menuStructure, controller);
      submenuItem.setIndex(getChildren().indexOf(submenuItem));
      if (StringUtils.isNotBlank(menuStructure.subMenuRowsStyleClass)) {
        submenuItem.setStyleClass(menuStructure.subMenuRowsStyleClass);
      } else {
        submenuItem.setStyleClass("ep-submenu-row");
      }

      if (StringUtils.isNotBlank(menuStructure.subMenuLabelStyleClass)) {
        submenuItem.setLabelStyle(menuStructure.subMenuLabelStyleClass);
      } else {
        submenuItem.setLabelStyle("submenu-row-label");
      }

      return submenuItem;
    }
  }


  /**
   * Setter of menuConfiguration
   *
   * @param menuConfiguration the menuConfiguration to set
   */
  public void setMenuConfiguration(VLViewConfigXML menuConfiguration) {
    this.menuConfiguration = menuConfiguration;

    if (menuConfiguration.getMenus() != null) {
      for (final VLViewRootMenuGroupXML menu : menuConfiguration.getMenus()) {
        if ("minimized".equalsIgnoreCase(menu.getId())) {
          minimizedConfiguration = menu;
        }

        if ("maximized".equalsIgnoreCase(menu.getId())) {
          maximizedConfiguration = menu;
        }
      }

      menuRows = menuConfiguration.getMenusRows();
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
   * @author Ramilafananana VONJISOA
   * @mailto yvonjisoa@nexitia.com
   * @date 2019
   */
  private class Separator extends Pane {

    private final Pane intraPane = new Pane();


    /**
     * Constructor
     */
    public Separator() {
      getChildren().add(intraPane);
      getStyleClass().add("ep-menu-separator");
      NodeHelper.setHgrow(intraPane);
    }
  }
}
