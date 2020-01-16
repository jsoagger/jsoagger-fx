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

package io.github.jsoagger.jfxcore.engine.components.tablestructure.table.header;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IListViewPaneHeader;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.quickactions.ViewActionFactory;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class BasicTableHeaderImpl implements IListViewPaneHeader {

  private static String dynMessageFormat = "(%s)";

  @FXML
  private Pane tableHeaderRootPane;

  @FXML
  private Pane iconContainer;

  @FXML
  private Pane actionsContainer;

  @FXML
  private Label title;

  @FXML
  private Label itemsCount;

  private AbstractViewController controller;
  private VLViewComponentXML configuration;
  private SimpleIntegerProperty elementCount = new SimpleIntegerProperty();


  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.configuration = configuration;
    this.controller = (AbstractViewController) controller;

    NodeHelper.loadFXML(BasicTableHeaderImpl.class.getResource("BasicTableHeader.fxml"), this);

    if (configuration != null) {
      NodeHelper.setTitle(title, configuration, (AbstractViewController) controller, true);
      NodeHelper.styleClassAddAll(tableHeaderRootPane, configuration, "styleClass");

      // extract css from configuration
      String labelStyleClass = configuration.getPropertyValue("titleStyleClass");
      if (io.github.jsoagger.core.utils.StringUtils.isNotBlank(labelStyleClass)) {
        title.getStyleClass().addAll(labelStyleClass.split(","));
      } else {
        title.getStyleClass().addAll("ep-listview-header-title-label", "ep-listview-title-label-medium");
      }

      elementsCountProperty().addListener((ChangeListener<Number>) (observable, oldValue, newValue) -> {
        if (newValue.intValue() > 0) {
          itemsCount.textProperty().set("");
        } else {
          itemsCount.textProperty().set(String.format(dynMessageFormat, newValue.intValue()));
        }
      });

      // actions
      VLViewComponentXML conf = ComponentUtils.resolveDefinition((AbstractViewController)getController(), "HeaderActions").orElse(null);
      if (conf != null) {
        Node actions = ViewActionFactory.viewActions((AbstractViewController) controller, conf);
        if (actions != null) {
          actionsContainer.getChildren().add(actions);
        }
      }
    }
  }


  @Override
  public Node getDisplay() {
    return tableHeaderRootPane;
  }


  @Override
  public VLViewComponentXML getConfiguration() {
    return configuration;
  }


  @Override
  public void setConfiguration(VLViewComponentXML configuration) {

  }


  @Override
  public IJSoaggerController getController() {
    return (IJSoaggerController) controller;
  }


  @Override
  public void setController(IJSoaggerController controller) {

  }


  @Override
  public String getTitle() {
    return title.textProperty().get();
  }


  @Override
  public void setTitle(String title) {
    this.title.setText(title);
  }


  @Override
  public void displayErrors(boolean isValid) {

  }


  @Override
  public SimpleIntegerProperty elementsCountProperty() {
    return elementCount;
  }


  @Override
  public SimpleStringProperty labelProperty() {
    return (SimpleStringProperty) title.textProperty();
  }


  @Override
  public SimpleBooleanProperty refreshingProperty() {
    return null;
  }


  @Override
  public SimpleBooleanProperty collpasedProperty() {
    return null;
  }


  @Override
  public SimpleBooleanProperty displayHeaderProperty() {
    return null;
  }


  @Override
  public void setHeader(Node header) {
    // TODO
  }
}
