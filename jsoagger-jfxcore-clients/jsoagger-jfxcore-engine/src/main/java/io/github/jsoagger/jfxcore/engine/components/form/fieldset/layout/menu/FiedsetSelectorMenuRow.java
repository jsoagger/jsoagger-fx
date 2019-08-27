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

package io.github.jsoagger.jfxcore.engine.components.form.fieldset.layout.menu;


import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.utils.StringUtils;
import org.kordamp.ikonli.javafx.FontIcon;

import io.github.jsoagger.jfxcore.api.ICountableElements;
import io.github.jsoagger.jfxcore.api.IFieldset;
import io.github.jsoagger.jfxcore.api.IFieldsetContent;
import io.github.jsoagger.jfxcore.api.ISelectableComponent;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class FiedsetSelectorMenuRow extends StackPane {

  /**
   * The label of the menu, and items count if displayed content has countable elements
   */
  protected Label label = new Label();
  protected Label count = new Label();

  /** The submenu if there is one */
  protected VBox subMenu = new VBox();
  protected ExpandCollapse expandCollapseIcon = new ExpandCollapse();

  /** This is the view displayed in the center of the view */
  List<ISelectableComponent> selectableComps = new ArrayList<>();
  protected FieldsetWithMenuLayout parentLayout;
  protected VBox selectableCompsLayout = new VBox();

  /** Submenu of the menu layouts */
  protected ObservableList<FieldsetSubmenuRow> submenus = FXCollections.observableArrayList();
  protected VBox rowslayout = new VBox();

  protected boolean isSelected;
  protected HBox row = new HBox();


  /**
   * Constructor
   */
  public FiedsetSelectorMenuRow() {
    getStyleClass().add("fieldset-selector-menu-row-wrapper");

    subMenu.managedProperty().bind(subMenu.visibleProperty());
    subMenu.getStyleClass().add("fieldset-selector-submenu-row-wrapper");
    subMenu.setVisible(false);
    getChildren().add(rowslayout);

    expandCollapseIcon.visibleProperty().bind(Bindings.size(submenus).greaterThan(0));
  }


  /**
   * Select the current row
   *
   * @param selected
   */
  public void select() {
    pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
    row.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
    label.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);

    selectableCompsLayout.setSpacing(8);
    if (parentLayout.getChildren().size() > 1) {
      parentLayout.getChildren().remove(1);
    }

    parentLayout.getChildren().add(selectableCompsLayout);
    NodeHelper.setHVGrow(selectableCompsLayout);
    for(Node e: selectableCompsLayout.getChildren()) {
      e.setVisible(true);
    }
  }


  /**
   * Remove all selection marker
   */
  public void clearSelection() {
    pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
    row.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
    label.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
    for(FieldsetSubmenuRow e: submenus) {
      e.clearSelection();
    }
  }

  /**
   * Select the component with this identifier.
   *
   * @param compId
   */
  public void select(String compId) {
    for(Node child: selectableCompsLayout.getChildren()) {
      if (child instanceof ISelectableComponent) {
        child.setVisible(((ISelectableComponent) child).getComponentId().equals(compId));
      }
    }
  }


  /**
   * @param fieldset
   */
  public void buildFrom(IFieldset fieldset) {
    label.getStyleClass().add("fieldset-selector-menu-row-label");
    row.getStyleClass().add("fieldset-selector-menu-row");

    FontIcon fontIcon = new FontIcon();
    fontIcon.setStyle("-fx-icon-color:-grey-color-700;-fx-icon-code:mdi-account-multiple;-fx-icon-size:16;");
    label.setGraphic(fontIcon);

    this.count.visibleProperty().bind(count.textProperty().isNotEmpty());
    count.getStyleClass().add("fieldset-selector-menu-row-items-count");

    rowslayout.getChildren().add(row);
    IFieldsetContent content = fieldset.getFieldsetContent();

    // in case of element to display is countable, we separated the menu
    // label with the items count
    // and so they can be displayed separatly
    if (ICountableElements.class.isAssignableFrom(content.getClass())) {
      if (((ICountableElements) content).elementsCountProperty() != null) {
        SimpleIntegerProperty count = ((ICountableElements) content).elementsCountProperty();
        this.count.textProperty().bindBidirectional(count, new NumberStringConverter());
        label.setText(((ICountableElements) content).labelProperty().get());
      }
    }

    // display the default label of the menu
    else {
      String title = content.getHeaderLabel();
      label.setText(StringUtils.capitalize(title));
    }

    row.getChildren().addAll(label, NodeHelper.horizontalSpacer(), count, expandCollapseIcon);
    row.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
      parentLayout.clearSelection();
      select();
    });

    // if fieldset content has selectable items
    // build submenu
    selectableComps = content.getSelectableComponents();
    if (!selectableComps.isEmpty()) {
      for (ISelectableComponent selectableComponent : selectableComps) {
        FieldsetSubmenuRow fieldsetSubmenuRow = new FieldsetSubmenuRow();
        fieldsetSubmenuRow.buildFrom(selectableComponent);
        fieldsetSubmenuRow.setParentRow(this);
        subMenu.getChildren().add(fieldsetSubmenuRow);
        submenus.add(fieldsetSubmenuRow);

        Node nodecontent = selectableComponent.content();
        nodecontent.managedProperty().bind(nodecontent.visibleProperty());
        selectableCompsLayout.getChildren().add(nodecontent);
      }
    }

    rowslayout.getChildren().add(subMenu);
    subMenu.managedProperty().bind(subMenu.visibleProperty());
  }


  /**
   * @param menuFieldsetGroupLayout
   */
  public void setParentLayout(FieldsetWithMenuLayout parentLayout) {
    this.parentLayout = parentLayout;
  }


  /**
   * Mark this row as the last one
   */
  public void setLast() {
    pseudoClassStateChanged(PseudoClass.getPseudoClass("last"), true);
  }

  /**
   * @author Ramilafananana VONJISOA
   * @mailto yvonjisoa@nexitia.com
   * @date 2019
   */
  private class ExpandCollapse extends StackPane {

    private Hyperlink plus = new Hyperlink();
    private Hyperlink minus = new Hyperlink();


    /**
     * Constructor
     */
    public ExpandCollapse() {
      plus.setOpacity(0.25);
      plus.getStyleClass().add("black-icon");
      getChildren().add(plus);

      minus.setOpacity(0.25);
      minus.getStyleClass().add("black-icon");
      getChildren().add(minus);

      plus.setVisible(true);
      minus.setVisible(false);

      plus.addEventFilter(ActionEvent.ACTION, e -> plus(e));
      minus.addEventFilter(ActionEvent.ACTION, e -> minusa(e));
    }


    /**
     *
     */
    private void plus(ActionEvent e) {
      subMenu.visibleProperty().set(true);
      plus.setVisible(false);
      minus.setVisible(true);
    }


    /**
     *
     */
    private void minusa(ActionEvent e) {
      subMenu.visibleProperty().set(false);
      plus.setVisible(true);
      minus.setVisible(false);
    }
  }


  /**
   * Getter of count
   *
   * @return the count
   */
  public Label getCount() {
    return count;
  }
}
