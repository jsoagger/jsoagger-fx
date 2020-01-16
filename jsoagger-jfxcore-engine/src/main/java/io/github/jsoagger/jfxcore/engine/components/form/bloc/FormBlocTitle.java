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

package io.github.jsoagger.jfxcore.engine.components.form.bloc;




import org.kordamp.ikonli.javafx.FontIcon;

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.form.IFormBlocTitle;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ToolbarUtils;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class FormBlocTitle extends VBox implements IFormBlocTitle {

  private static final String FORM_BLOC_TITLE_LABEL = "form-bloc-title-label";
  private static final String FORM_BLOC_TITLE_LABEL_COLLAPSE_ICON = "form-bloc-title-label-collapse-icon";

  protected VLViewComponentXML blocConfig;
  protected AbstractViewController controller;

  protected Label collpasedLabel = new Label();
  protected HBox headerActionsPane = new HBox();
  protected Label expandedLabel = new Label();
  protected BooleanProperty expanded = new SimpleBooleanProperty(true);
  protected Label label;

  private final HBox internalLayout = new HBox();


  /**
   * Constructor
   */
  public FormBlocTitle() {
    internalLayout.setAlignment(Pos.CENTER_LEFT);
  }


  /**
   * Constructor
   */
  @Override
  public void build(VLViewComponentXML blocConfig, IJSoaggerController controller) {
    this.blocConfig = blocConfig;
    this.controller = (AbstractViewController) controller;

    label = new Label();
    NodeHelper.setTitle(label, blocConfig, (AbstractViewController) controller);
    NodeHelper.styleClassSetAll(label, blocConfig, "headerTitleStyleClass", FORM_BLOC_TITLE_LABEL);
    NodeHelper.styleClassSetAll(this, blocConfig, "titleStyleClass", "form-bloc-title");

    internalLayout.getChildren().addAll(label);
    buildCollpaseExpandIndicator();
    buildHeaderEllipsysActions();
    getChildren().add(internalLayout);
  }


  protected void buildHeaderEllipsysActions() {
    final VLViewComponentXML headerActions = blocConfig.getComponentById("HeaderEllipsysActions").orElse(null);
    if (headerActions != null) {
      final Node button = ToolbarUtils.ellipsisVActionsButton(headerActions, controller, null);
      internalLayout.getChildren().add(button);
    }
  }


  public void onCollapseExpand() {
    if (expanded.get()) {
      expanded.set(false);
      headerActionsPane.setVisible(false);
    } else {
      expanded.set(true);
      headerActionsPane.setVisible(true);
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void collapse() {
    if (expanded.get()) {
      expanded.set(false);
    }
  }


  private void buildCollpaseExpandIndicator() {
    final boolean collapsible = blocConfig.booleanPropertyValueOf("collapsible").orElse(true);
    if (collapsible) {
      // action on the mouse event on the hbox only
      addEventFilter(MouseEvent.MOUSE_CLICKED, e -> onCollapseExpand());

      buildCollapsedIcon();
      buildExpandedcon();

      final StackPane wrapper = new StackPane();
      collpasedLabel.managedProperty().bind(Bindings.not(expanded));
      collpasedLabel.getStyleClass().addAll(FORM_BLOC_TITLE_LABEL_COLLAPSE_ICON, "hand-hover");
      collpasedLabel.visibleProperty().bind(collpasedLabel.managedProperty());

      expandedLabel.managedProperty().bind(expanded);
      expandedLabel.getStyleClass().addAll(FORM_BLOC_TITLE_LABEL_COLLAPSE_ICON, "hand-hover");
      expandedLabel.visibleProperty().bind(expandedLabel.managedProperty());

      expanded.set(true);
      wrapper.getChildren().add(0, collpasedLabel);
      wrapper.getChildren().add(0, expandedLabel);
      wrapper.setStyle("-fx-max-width:28;-fx-min-width:28;");
      internalLayout.getChildren().addAll( NodeHelper.horizontalSpacer(),wrapper);
    }
  }


  protected void buildCollapsedIcon() {
    final FontIcon icon = new FontIcon("fa-plus:20");
    icon.getStyleClass().add("expand-collapse-icon");
    collpasedLabel.setGraphic(icon);
  }


  protected void buildExpandedcon() {
    final FontIcon expandedIcon = new FontIcon("fa-minus:10");
    expandedIcon.getStyleClass().add("expand-collapse-icon");
    expandedLabel.setGraphic(expandedIcon);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public BooleanProperty expandedProperty() {
    return expanded;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }


  /**
   * Getter of label
   *
   * @return the label
   */
  @Override
  public Label getLabel() {
    return label;
  }
}
