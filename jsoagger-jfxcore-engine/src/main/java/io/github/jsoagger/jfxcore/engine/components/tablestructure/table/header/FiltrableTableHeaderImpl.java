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



import java.net.URL;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IListViewPaneHeader;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.table.api.ITableStructureFilter;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.AbstractTableStructure;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableStructureController;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;
import com.jfoenix.controls.JFXButton;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 * Default impletation of table tool bar. Displays the filter input text and action in same hbox.
 *
 * The filter is hidden when the table is in modify mode.
 *
 * @authisplor vonji
 *
 */
public class FiltrableTableHeaderImpl implements IListViewPaneHeader {

  protected static String dynMessageFormat = "(%s)";

  @FXML
  private Pane titleContainer;

  @FXML
  protected Pane tableHeaderRootPane;

  @FXML
  protected Pane actionsAndFiltersContainer;

  @FXML
  protected Pane iconContainer;

  @FXML
  protected Label title;

  @FXML
  protected Label itemsCount;

  @FXML
  protected TextField filterTextField;

  protected AbstractViewController controller;
  protected VLViewComponentXML configuration;
  protected SimpleIntegerProperty elementCount = new SimpleIntegerProperty();

  protected Button filterButton = new JFXButton();
  protected Button closeFilterButton = new JFXButton();
  protected Node toolbar = null;


  protected URL getFxmlLocation() {
    return FiltrableTableHeaderImpl.class.getResource("FiltrableTableHeader.fxml");
  }


  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.configuration = configuration;
    this.controller = (AbstractViewController) controller;
    NodeHelper.loadFXML(getFxmlLocation(), this);

    filterTextField.managedProperty().bind(filterTextField.visibleProperty());
    filterTextField.setVisible(false);
    filterTextField.setPromptText("Find in table");

    if (configuration != null) {
      NodeHelper.setTitle(title, configuration, (AbstractViewController) controller, true);
      NodeHelper.styleClassSetAll(title, configuration, "titleStyleClass", "ep-edit-structure-title-label");
      NodeHelper.styleClassAddAll(tableHeaderRootPane, configuration, "styleClass");
      title.visibleProperty().bind(Bindings.isNotEmpty(title.textProperty()));

      elementsCountProperty().addListener((ChangeListener<Number>) (observable, oldValue, newValue) -> {
        if (newValue.intValue() > 0) {
          itemsCount.textProperty().set("");
        } else {
          itemsCount.textProperty().set(String.format(dynMessageFormat, newValue.intValue()));
        }
      });

      ((FullTableStructureController) controller).processedElementProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> {
        final AbstractTableStructure ts = (AbstractTableStructure) ((FullTableStructureController) controller).processedElementProperty().get();
        doLayout(ts);
        buildFiltering(ts);
      });
    }
  }


  protected void beginFiltering() {
    filterButton.setVisible(false);
    filterTextField.setVisible(true);
    closeFilterButton.setVisible(true);
    if(toolbar != null) {
      toolbar.setVisible(false);
    }
  }

  protected void endFiltering() {
    filterButton.setVisible(true);
    filterTextField.setVisible(false);
    closeFilterButton.setVisible(false);
    if(toolbar != null) {
      toolbar.setVisible(true);
    }
  }


  protected void doLayout(AbstractTableStructure ts) {
    if (ts.getToolbar().isPresent()) {
      toolbar = ts.getToolbar().get();
      actionsAndFiltersContainer.getChildren().add(toolbar);
      HBox.setHgrow(toolbar, Priority.NEVER);

      if(!toolbar.managedProperty().isBound()) {
        toolbar.managedProperty().bind(toolbar.visibleProperty());
      }
    }
  }

  protected void buildFiltering(AbstractTableStructure ts) {
    final String filter = configuration.getPropertyValue("filterImpl");
    if (io.github.jsoagger.core.utils.StringUtils.isNotBlank(filter)) {
      final ITableStructureFilter pr = (ITableStructureFilter) Services.getBean(filter);

      final FilteredList<OperationData> tableDatas = ts.getFilteredDatas();
      filterTextField.textProperty().addListener((ChangeListener<String>) (i, j, k) -> {
        if (k == null || k.length() == 0 || k.trim().length() == 0) {
          tableDatas.setPredicate(s -> true);
        }
        tableDatas.setPredicate(s -> pr.isDisplayable(s, k));
      });
    }


    if(toolbar == null || ts.filteringAlwaysShown()) {
      filterButton.setVisible(false);
      filterTextField.setVisible(true);
    }
    else {
      closeFilterButton.managedProperty().bind(closeFilterButton.visibleProperty());
      closeFilterButton.setVisible(false);
      closeFilterButton.setOnAction(e -> endFiltering());
      actionsAndFiltersContainer.getChildren().add(0, closeFilterButton);
      closeFilterButton.getStyleClass().addAll("table-toolbar-action", "hand-hover");
      closeFilterButton.getStyleClass().removeAll("button", "jfx-button");
      IconUtils.setFontIcon("fa-close:20", closeFilterButton);

      filterButton.managedProperty().bind(filterButton.visibleProperty());
      IconUtils.setFontIcon("fa-filter:20", filterButton);
      actionsAndFiltersContainer.getChildren().add(0, filterButton);
      filterButton.getStyleClass().addAll("table-toolbar-action", "hand-hover");
      filterButton.getStyleClass().removeAll("button", "jfx-button");
      filterButton.setOnAction(e -> {
        beginFiltering();
      });
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Node getDisplay() {
    return tableHeaderRootPane;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public VLViewComponentXML getConfiguration() {
    return configuration;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void setConfiguration(VLViewComponentXML configuration) {

  }


  /**
   * {@inheritDoc}
   */
  @Override
  public IJSoaggerController getController() {
    return controller;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void setController(IJSoaggerController controller) {

  }


  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {
    return title.textProperty().get();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitle(String title) {
    this.title.setText(title);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void displayErrors(boolean isValid) {

  }


  /**
   * {@inheritDoc}
   */
  @Override
  public SimpleIntegerProperty elementsCountProperty() {
    return elementCount;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public SimpleStringProperty labelProperty() {
    return (SimpleStringProperty) title.textProperty();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public SimpleBooleanProperty refreshingProperty() {
    return null;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public SimpleBooleanProperty collpasedProperty() {
    return null;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public SimpleBooleanProperty displayHeaderProperty() {
    return null;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeader(Node header) {}
}
