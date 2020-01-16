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

package io.github.jsoagger.jfxcore.engine.components.table.simple;


import java.lang.reflect.Method;
import java.util.List;

import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.components.control9.CustomTextField;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;

import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Table View Top toolbar
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class STVTopToolbar extends VBox {

  private static String Format = "%s item(s) selected";

  private final HBox titleToolbar = new HBox();
  private final HBox modifyingToolbar = new HBox();
  private final HBox defaultToolbar = new HBox();
  private final HBox searchToolbar = new HBox();
  private final HBox filtersToolbar = new HBox();

  private Label title = new Label();
  private SimpleTableView<?> tableView = null;
  private AbstractViewController controller = null;
  private Hyperlink doneButton = new Hyperlink();
  private Hyperlink modifyButton = new Hyperlink();
  private Node ellispsis = null;
  private VLViewComponentXML tableConfiguration = null;
  private Label numberOfSelectedElementsCount = new Label();


  /**
   * Constructor
   */
  public STVTopToolbar(AbstractViewController controller, VLViewComponentXML tableConfiguration) {
    super();

    this.controller = controller;
    this.tableConfiguration = tableConfiguration;

    modifyingToolbar.managedProperty().bind(modifyingToolbar.visibleProperty());
    defaultToolbar.managedProperty().bind(defaultToolbar.visibleProperty());

    NodeHelper.setHgrow(modifyingToolbar);
    NodeHelper.setHgrow(defaultToolbar);

    defaultToolbar.setVisible(true);
    modifyingToolbar.setVisible(false);
    defaultToolbar.setStyle("-fx-background-color: white;" + "-fx-alignment:CENTER_LEFT;" + "-fx-padding: 8 16 16 16;" + "-fx-spacing: 16;" + "-fx-border-color: -grey-color-300;"
        + "-fx-border-width: 0 0 1 0;" + "-fx-pref-height: 64;");

    modifyingToolbar.setStyle("-fx-background-color: white;" + "-fx-alignment: CENTER;" + "-fx-padding: 16;" + "-fx-spacing: 16;" + "-fx-border-color: -border-color;" + "-fx-border-width: 0.25 0 0 0;"
        + "-fx-pref-height: 64;");
  }


  public void build() {
    buildTitleToolbar();
    buildDefaultToolbar();
    buildModifyActions();
  }


  // @formatter:off
  /**
   * <component id="TitleToolBarActions"> <component id="ActionGroup"> <component ref="ExportButton"/>
   * </component>
   *
   * <component id="ActionGroup"> <component ref="EllipsisButton"/> </component> </component>
   */
  // @formatter:on
  private void buildTitleToolbar() {
    titleToolbar.getChildren().addAll(title, NodeHelper.horizontalSpacer());
    titleToolbar.setStyle("-fx-background-color: white;" + "-fx-alignment:CENTER_LEFT;" + "-fx-padding: 16 16 16 16;" + "-fx-spacing: 16;" + "-fx-border-color: -grey-color-300;"
        + "-fx-border-width: 0 0 0 0;" + "-fx-pref-height: 64;");

    title.setStyle("-fx-font-family: 'Roboto Regular';" + "-fx-font-size: 2.3em;" + "-fx-opacity: 0.87;");

    getChildren().add(titleToolbar);
    buildEllipsisMenu();
    titleToolbar.getChildren().addAll(ellispsis != null ? ellispsis : new HBox());
  }


  // @formatter:off
  /**
   * <component id="ToolbarActions"> <component id="ActionGroup">
   * <component ref="DeleteSelectedRows"/> </component>
   *
   * <component id="ActionGroup"> <component ref="AddToFavorisRows"/>
   * <component ref="AddToSelectedRows"/> </component> </component>
   */
  // @formatter:on
  public void buildDefaultToolbar() {
    getChildren().add(defaultToolbar);

    final SplitMenuButton addNew = new SplitMenuButton();
    addNew.setText("Create new item");
    final MenuItem addNewa = new MenuItem("Create new element");
    addNew.getItems().add(addNewa);
    defaultToolbar.getChildren().add(addNew);
    defaultToolbar.getChildren().add(NodeHelper.horizontalSpacer());

    modifyButton.setOnAction(e -> modifyClicked());
    modifyButton.setFocusTraversable(false);
    modifyButton.visibleProperty().bind(Bindings.size(tableView.getTableView().getItems()).greaterThan(0));

    // and modify button
    defaultToolbar.getChildren().addAll(modifyButton);

    // Rfresh button
    final Hyperlink refresh = new Hyperlink();
    defaultToolbar.getChildren().addAll(refresh);

    // displayfilter
    final SplitMenuButton button = new SplitMenuButton();
    button.setText("Display all items");
    final MenuItem all = new MenuItem("Display all items");
    button.getItems().add(all);

    buildFilterToolbar();
    buildSearchFilter();
    defaultToolbar.getChildren().addAll(button);
  }


  // @formatter:off
  /**
   * <component id="ModifyMenuActions"> <component id="ActionGroup">
   * <component ref="DeleteSelectedRows"/> </component>
   *
   * <component id="ActionGroup"> <component ref="AddToFavorisRows"/>
   * <component ref="AddToSelectedRows"/> </component> </component>
   */
  // @formatter:on
  public void buildModifyActions() {

    // add label for selected item count
    modifyingToolbar.getChildren().addAll(numberOfSelectedElementsCount, NodeHelper.horizontalSpacer());
    numberOfSelectedElementsCount.setStyle("-fx-font-size:1.5em;");
    setSelectedItems(0);

    // process buttons
    final VLViewComponentXML actionsConfig = ComponentUtils.resolveComponent(tableConfiguration, "ModifyMenuActions");

    if (actionsConfig != null && actionsConfig.hasSubComponent()) {

      // for each group generate buttons
      for (final VLViewComponentXML actionsGroup : actionsConfig.getSubcomponents()) {

        final List<IBuildable> buttons = ComponentUtils.resolveAndGenerate(controller, actionsGroup.getSubcomponents());

        final HBox group = new HBox();
        modifyingToolbar.getChildren().add(group);

        for(IBuildable e: buttons) {
          ((Button) e.getDisplay()).setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
          ((Button) e.getDisplay()).disableProperty().bind(Bindings.not(tableView.hasSelectedRowProperty()));
          e.getDisplay().getStyleClass().add("flat-action-button");
          group.getChildren().add(e.getDisplay());
        }
      }
    }

    doneButton.setText(controller.getLocalised("DONE_LABEL").toUpperCase());
    doneButton.getStyleClass().add("listview-toolbar-action-label");
    doneButton.setOnAction(e -> doneClicked());
    doneButton.setFocusTraversable(false);
    modifyingToolbar.getChildren().addAll(doneButton);
    getChildren().add(modifyingToolbar);
  }


  // @formatter:off
  /**
   * <component id="EllipsisMenuActions"> <component ref="DeleteSelectedRows"/>
   * <component ref="AddToFavorisRows"/> <component ref="AddToSelectedRows"/> </component>
   */
  // @formatter:on
  private void buildEllipsisMenu() {

    final VLViewComponentXML moreActionsConfig = ComponentUtils.resolveComponent(tableConfiguration, "EllipsisMenuActions");

    if (moreActionsConfig != null && moreActionsConfig.hasSubComponent()) {
      // ellispsis = ComponentToContextMenuHelper
      // .ellipsisVActionsButton(moreActionsConfig.getSubcomponents(),
      // controller,
      // tableView.criteriaContext());
      // ellispsis.managedProperty().bind(ellispsis.visibleProperty());
      // ellispsis.visibleProperty().bind(Bindings.size(tableView.getTableView().getItems()).greaterThan(0));
    }
  }


  private void buildSearchFilter() {
    searchToolbar.setStyle("-fx-background-color: white;" + "-fx-alignment:CENTER_LEFT;" + "-fx-padding: 8 16 8 64;" + "-fx-spacing:16;" + "-fx-border-color: -grey-color-300;"
        + "-fx-border-width: 0 0 1 0;" + "-fx-pref-height: 48;");
    getChildren().addAll(searchToolbar);

    NodeHelper.setHgrow(searchToolbar);
    searchToolbar.managedProperty().bind(searchToolbar.visibleProperty());

    final CustomTextField customTextField = new CustomTextField();
    customTextField.setPrefWidth(350);

    final Button searchIconRight = new Button("Go");
    searchIconRight.setFocusTraversable(false);
    searchIconRight.setOnAction(e -> {
      Method method = null;

      try {
        method = controller.getClass().getMethod("runSearch", Event.class);
        method.invoke(controller, e);
      } catch (final Exception e1) {
        e1.printStackTrace();
      }
    });

    final Hyperlink searchToolBarButton = new Hyperlink();
    defaultToolbar.getChildren().add(searchToolBarButton);
    searchToolBarButton.setOnAction(e -> {
      searchToolbar.visibleProperty().set(!searchToolbar.visibleProperty().get());
    });

    searchToolbar.getChildren().addAll(NodeHelper.horizontalSpacer(), customTextField, searchIconRight, NodeHelper.horizontalSpacer());
    searchToolbar.setVisible(false);
  }


  private void buildFilterToolbar() {
    filtersToolbar.setStyle("-fx-background-color: white;" + "-fx-alignment:CENTER_LEFT;" + "-fx-padding: 16 16 16 64;" + "-fx-spacing:16;" + "-fx-border-color: -grey-color-300;"
        + "-fx-border-width: 0 0 1 0;" + "-fx-pref-height: 48;");
    getChildren().add(filtersToolbar);
    NodeHelper.setHgrow(filtersToolbar);
    filtersToolbar.managedProperty().bind(filtersToolbar.visibleProperty());

    final Hyperlink filterToolBarButton = new Hyperlink();
    defaultToolbar.getChildren().add(filterToolBarButton);
    filterToolBarButton.setOnAction(e -> {
      filtersToolbar.visibleProperty().set(!filtersToolbar.visibleProperty().get());
    });

    filtersToolbar.setVisible(false);
  }


  private void modifyClicked() {
    // hide more actions buttons
    defaultToolbar.setVisible(false);
    modifyingToolbar.setVisible(true);

    // show all selectors
    tableView.modify();
  }


  public void done() {
    defaultToolbar.setVisible(true);
    modifyingToolbar.setVisible(false);
  }


  private void doneClicked() {
    // hide all selectors
    tableView.cancelModify();
    done();
  }


  /**
   * @param size
   */
  public void setSelectedItems(int size) {
    numberOfSelectedElementsCount.setText(String.format(Format, size));
  }


  public void setTableView(SimpleTableView tableView) {
    this.tableView = tableView;
  }


  public void setTitle(String title) {
    this.title.textProperty().set(title);
  }

  /**
   * Keep track of current title in order to be able to remove it if displayed.
   */
  private Node currentTitle = null;


  public void setTitle(Node title) {
    if (this.currentTitle != null) {
      titleToolbar.getChildren().remove(currentTitle);
    }

    this.currentTitle = title;
    this.title.textProperty().set("");
    titleToolbar.getChildren().add(0, currentTitle);
  }
}
