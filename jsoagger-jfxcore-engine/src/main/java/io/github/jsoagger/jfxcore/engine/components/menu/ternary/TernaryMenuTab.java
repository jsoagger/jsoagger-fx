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

package io.github.jsoagger.jfxcore.engine.components.menu.ternary;




import java.util.List;
import java.util.Optional;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.XMLConstants;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.beans.property.SimpleStringProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class TernaryMenuTab extends HBox implements IBuildable {

  /*-----------------------------------------------------------------------------
  | Static fields
   *=============================================================================*/
  protected static final String ACTIONS_PATH = "Actions";
  protected static PseudoClass SELECTED = PseudoClass.getPseudoClass("selected");

  /*-----------------------------------------------------------------------------
  | Private fields
   *=============================================================================*/
  protected SimpleStringProperty titleProperty = new SimpleStringProperty();
  protected Hyperlink icon = new Hyperlink();
  protected Label title = new Label();

  protected Node content;
  protected VLViewComponentXML tabDefinition;
  protected AbstractViewController controller;

  protected TernaryMenuTabPane tabPane;
  protected boolean selected = false;
  protected HBox rightActionsLayout = new HBox();


  /*-----------------------------------------------------------------------------
  | Constructors
   *=============================================================================*/
  /**
   * Constructor
   */
  public TernaryMenuTab() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    tabDefinition = configuration;
    this.controller = (AbstractViewController) controller;

    getStyleClass().add("terniary-menu-tab");

    final Optional<String> label = tabDefinition.propertyValueOf(XMLConstants.TITLE);
    final String tootlip = tabDefinition.getPropertyValue(XMLConstants.TOOLTIP);

    title.getStyleClass().add("terniary-menu-tab-label");
    title.textProperty().bind(titleProperty);
    icon.textProperty().bind(titleProperty);

    titleProperty.set(controller.getLocalised(label.get()));

    icon.setTooltip(new Tooltip(controller.getLocalised(tootlip)));
    icon.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    icon.setOnAction(e -> iconOnAction());
    IconUtils.setIcon(icon, configuration);
    getChildren().add(icon);

    //buildContent();
    buildActions();
  }


  public void buildActions() {
    NodeHelper.setHVGrow(rightActionsLayout);
    rightActionsLayout.setAlignment(Pos.BOTTOM_RIGHT);
    rightActionsLayout.setSpacing(4);

    final VLViewComponentXML comp = ComponentUtils.resolveComponent(tabDefinition, ACTIONS_PATH);
    if (comp != null) {
      if (comp.hasSubComponent()) {
        final List<VLViewComponentXML> resolvedComponents = ComponentUtils.resolveDefinitions(controller, comp.getSubcomponents());
        List<IBuildable> buildables = ComponentUtils.generate(controller, resolvedComponents);
        for (final IBuildable buildable : buildables) {
          rightActionsLayout.getChildren().add(buildable.getDisplay());
        }
      }
    }
  }


  public void buildContent() {
    String rootView = getTabDefinition().getPropertyValue("rootView");
    if (StringUtils.isNotBlank(rootView)) {
      AbstractViewController viewController = StandardViewUtils.forId(this.controller.getRootStructure(), this.controller.getStructureContent(), rootView);
      content = viewController.processedView();
      viewController.setParent(controller);
    } else {
      content = new StackPane();
    }
  }


  public Node getActions() {
    return rightActionsLayout;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }


  /*-----------------------------------------------------------------------------
  | Public methods
   *=============================================================================*/
  /**
   * When the hyperlink is clicked
   */
  public void iconOnAction() {
    icon.setOnAction(e -> {
      if(content == null) {
        buildContent();
      }
      tabPane.select(this);
    });
  }


  /**
   * Get the titleProperty
   *
   * @return the titleProperty
   */
  public SimpleStringProperty getTitleProperty() {
    return titleProperty;
  }


  /**
   * Set the titleProperty
   *
   * @param titleProperty the titleProperty to set
   */
  public void setTitleProperty(SimpleStringProperty titleProperty) {
    this.titleProperty = titleProperty;
  }


  /**
   * @param tabPane
   */
  public void setTabPane(TernaryMenuTabPane tabPane) {
    this.tabPane = tabPane;
  }


  /**
   * Get the icon
   *
   * @return the icon
   */
  public Node getIcon() {
    return icon;
  }


  /**
   * Set the icon
   *
   * @param icon the icon to set
   */
  public void setIcon(Hyperlink icon) {
    this.icon = icon;
  }


  /**
   * Get the title
   *
   * @return the title
   */
  public Node getTitle() {
    return title;
  }


  /**
   * Set the title
   *
   * @param title the title to set
   */
  public void setTitle(String title) {
    titleProperty.set(title);
  }


  /**
   * Get the content
   *
   * @return the content
   */
  public Node getContent() {
    buildContent();
    return content;
  }


  /**
   * Set the content
   *
   * @param content the content to set
   */
  public void setContent(Node content) {
    this.content = content;
  }


  /**
   * Get the tabDefinition
   *
   * @return the tabDefinition
   */
  public VLViewComponentXML getTabDefinition() {
    return tabDefinition;
  }


  /**
   * Set the tabDefinition
   *
   * @param tabDefinition the tabDefinition to set
   */
  public void setTabDefinition(VLViewComponentXML tabDefinition) {
    this.tabDefinition = tabDefinition;
  }


  /**
   * Get the controller
   *
   * @return the controller
   */
  public AbstractViewController getController() {
    return controller;
  }


  /**
   * Set the controller
   *
   * @param controller the controller to set
   */
  public void setController(AbstractViewController controller) {
    this.controller = controller;
  }


  /**
   * Change selected state
   *
   * @param selected
   */
  public void select(boolean selected) {
    this.selected = selected;
    pseudoClassStateChanged(SELECTED, selected);
  }


  /**
   * @return
   */
  public boolean isSelected() {
    return selected;
  }
}
