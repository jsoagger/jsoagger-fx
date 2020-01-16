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

package io.github.jsoagger.jfxcore.engine.components.header;




import java.net.URL;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.utils.StringUtils;
import org.kordamp.ikonli.javafx.FontIcon;

import io.github.jsoagger.jfxcore.api.ICountableElements;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IListViewPaneHeader;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.quickactions.ViewActionFactory;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextFlow;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ListViewPaneHeader implements IListViewPaneHeader, ICountableElements {

  protected static String dynMessageFormat = "(%s)";

  @FXML
  private Pane rootContainer;
  @FXML
  protected Label mainLabel;
  @FXML
  protected Label totalElementsCountLabel;
  @FXML
  protected Pane leftContainer;

  @FXML
  protected TextFlow secondaryLabelFlow;

  @FXML
  protected Pane rightActionsContainer;

  protected SimpleIntegerProperty elementCountProperty = new SimpleIntegerProperty();
  protected AbstractViewController controller;
  protected VLViewComponentXML configuration;

  private boolean collapsible;
  private boolean refreshable;

  private Label collpasedLabel = new Label();
  private Label expandedLabel = new Label();
  protected SimpleBooleanProperty collpased = new SimpleBooleanProperty(false);
  protected SimpleBooleanProperty refreshing = new SimpleBooleanProperty(false);
  protected SimpleBooleanProperty displayHeader = new SimpleBooleanProperty(true);


  /**
   * Constructor
   */
  public ListViewPaneHeader() {
    super();
    URL location = ListViewPaneHeader.class.getResource("ListViewPaneHeader.fxml");
    NodeHelper.loadFXML(location, this);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.configuration = configuration;
    this.controller = (AbstractViewController) controller;

    // visibility
    rootContainer.managedProperty().bind(rootContainer.visibleProperty());
    rootContainer.visibleProperty().bind(displayHeader);

    if (configuration != null) {
      boolean dispHeader = configuration.getBooleanProperty("displayHeader", true);
      displayHeader.set(dispHeader);

      if (dispHeader) {
        NodeHelper.setTitle(mainLabel, configuration, (AbstractViewController) controller, true);
        NodeHelper.styleClassAddAll(rootContainer, configuration, "styleClass");

        if (StringUtils.isNotBlank(configuration.getPropertyValue("description"))) {
          NodeHelper.addDescription(secondaryLabelFlow, configuration, (AbstractViewController) controller);
        } else {
          secondaryLabelFlow.setManaged(false);
        }

        // extract css from configuration
        String labelStyleClass = configuration.getPropertyValue("titleStyleClass");
        if (io.github.jsoagger.core.utils.StringUtils.isNotBlank(labelStyleClass)) {
          mainLabel.getStyleClass().addAll(labelStyleClass.split(","));
        } else {
          mainLabel.getStyleClass().addAll("ep-listview-header-title-label", "ep-listview-title-label-medium");
        }

        elementsCountProperty().addListener((ChangeListener<Number>) (observable, oldValue, newValue) -> {
          if (newValue.intValue() > 0) {
            totalElementsCountLabel.textProperty().set("");
          } else {
            totalElementsCountLabel.textProperty().set(String.format(dynMessageFormat, newValue.intValue()));
          }
        });

        if (collapsible) {
          buildCollpaseExpand();
        }

        // actions
        VLViewComponentXML conf = ComponentUtils.resolveDefinition((AbstractViewController)getController(), "HeaderActions").orElse(null);
        if (conf != null) {
          Node actions = ViewActionFactory.viewActions((AbstractViewController) controller, conf);
          if (actions != null) {
            rightActionsContainer.getChildren().add(actions);
          }
        }
      }
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setHeader(Node header) {
    leftContainer.getChildren().clear();
    leftContainer.getChildren().add(header);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return rootContainer;
  }


  /**
   * Getter of collapsible
   *
   * @return the collapsible
   */
  public boolean isCollapsible() {
    return collapsible;
  }


  /**
   * Setter of collapsible
   *
   * @param collapsible the collapsible to set
   */
  public void setCollapsible(boolean collapsible) {
    this.collapsible = collapsible;
  }


  /**
   * Getter of refreshable
   *
   * @return the refreshable
   */
  public boolean isRefreshable() {
    return refreshable;
  }


  /**
   * Setter of refreshable
   *
   * @param refreshable the refreshable to set
   */
  public void setRefreshable(boolean refreshable) {
    this.refreshable = refreshable;
  }


  private void onCollapseExpand() {
    if (collpased.get()) {
      collpased.set(false);
    } else {
      collpased.set(true);
    }
  }


  protected void buildCollpaseExpand() {
    StackPane wrapper = new StackPane();
    FontIcon collpasedLabelicon = new FontIcon("fa-arrow-right:20");
    collpasedLabel.setGraphic(collpasedLabelicon);

    FontIcon expandedLabelicon = new FontIcon("fa-arrow-left:20");
    expandedLabel.setGraphic(expandedLabelicon);

    collpasedLabel.managedProperty().bind(Bindings.not(collpased));
    collpasedLabel.visibleProperty().bind(collpasedLabel.managedProperty());

    expandedLabel.managedProperty().bind(collpased);
    expandedLabel.visibleProperty().bind(expandedLabel.managedProperty());

    collpased.set(true);
    wrapper.getChildren().addAll(collpasedLabel, expandedLabel);
    wrapper.setStyle("-fx-max-width:48;-fx-min-width:48;");

    collpasedLabel.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> onCollapseExpand());
    expandedLabel.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> onCollapseExpand());

    // !! should be las items
    // child class should handle this
    rootContainer.getChildren().add(wrapper);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public final SimpleBooleanProperty collpasedProperty() {
    return this.collpased;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public final SimpleBooleanProperty refreshingProperty() {
    return this.refreshing;
  }


  /**
   * Getter of controller
   *
   * @return the controller
   */
  @Override
  public IJSoaggerController getController() {
    return (IJSoaggerController) controller;
  }


  /**
   * Setter of controller
   *
   * @param controller the controller to set
   */
  @Override
  public void setController(IJSoaggerController controller) {
    this.controller = (AbstractViewController) controller;
  }


  /**
   * Getter of configuration
   *
   * @return the configuration
   */
  @Override
  public VLViewComponentXML getConfiguration() {
    return configuration;
  }


  /**
   * Setter of configuration
   *
   * @param configuration the configuration to set
   */
  @Override
  public void setConfiguration(VLViewComponentXML configuration) {
    this.configuration = configuration;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void displayErrors(boolean isValid) {
    // nothing to do
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getTitle() {
    return mainLabel.textProperty().get();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setTitle(String title) {
    mainLabel.textProperty().set(title);
  }


  @Override
  public SimpleBooleanProperty displayHeaderProperty() {
    return displayHeader;
  }


  public Label totalElementsCountLabel() {
    return totalElementsCountLabel;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public SimpleIntegerProperty elementsCountProperty() {
    return elementCountProperty;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public SimpleStringProperty labelProperty() {
    return (SimpleStringProperty) mainLabel.textProperty();
  }
}
