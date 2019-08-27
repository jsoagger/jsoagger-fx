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



import java.util.Iterator;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IListViewPaneHeader;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.tablestructure.AbstractTableStructure;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.FullTableStructureController;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class StructureNavigatorTableHeaderImpl implements IListViewPaneHeader {

  @FXML
  protected Pane tableHeaderRootPane;

  @FXML
  protected Pane actionsContainer;

  @FXML
  protected Pane filterContainer;

  @FXML
  protected Pane titleContainer;

  @FXML
  protected Label headerTitle;

  @FXML
  protected TextField filterTextField;

  protected AbstractViewController controller;
  protected VLViewComponentXML configuration;

  protected OperationData rootModel = null;


  /**
   * {@inheritDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    this.configuration = configuration;
    this.controller = (AbstractViewController) controller;

    loadFXML();
    NodeHelper.styleClassAddAll(tableHeaderRootPane, configuration, "styleClass");
    NodeHelper.setTitle(headerTitle, configuration, (AbstractViewController) controller, true);
    NodeHelper.styleClassSetAll(headerTitle, configuration, "titleStyleClass", "ep-table-title-label");
    NodeHelper.styleClassAddAll(titleContainer, configuration, "titleContainerStyleClass");

    filterTextField.managedProperty().bind(filterTextField.visibleProperty());
    filterTextField.setPromptText("Find in table");
    boolean filtrable = configuration.getBooleanProperty("filtrable", true);
    filterTextField.setVisible(filtrable);

    ((FullTableStructureController) controller).processedElementProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> {
      AbstractTableStructure ts = (AbstractTableStructure) ((FullTableStructureController) controller).processedElementProperty().get();
      if (ts.getToolbar().isPresent()) {
        Node tb = ts.getToolbar().get();
        HBox.setHgrow(tb, Priority.NEVER);
        actionsContainer.getChildren().add(tb);
      }
    });

    setRootModel();

    headerTitle.managedProperty().bind(headerTitle.visibleProperty());
    headerTitle.visibleProperty().bind(Bindings.size(titleContainer.getChildren()).isEqualTo(0));
    titleContainer.visibleProperty().bind(Bindings.size(titleContainer.getChildren()).greaterThan(0));
    titleContainer.managedProperty().bind(titleContainer.visibleProperty());
  }


  protected void loadFXML() {
    NodeHelper.loadFXML(StructureNavigatorTableHeaderImpl.class.getResource("FolderNavigatorTableHeader.fxml"), this);
  }


  protected void setRootModel() {
    SingleResult sr = (SingleResult) controller.getModel();
    rootModel = sr.getData();

    ((FullTableStructureController) controller).processedElementProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> {
      AbstractTableStructure ts = (AbstractTableStructure) ((FullTableStructureController) controller).processedElementProperty().get();
      ts.childTree().addListener((ListChangeListener<OperationData>) c -> {
        structureModelUpdated();
      });

      structureModelUpdated();
    });
  }


  protected void structureModelUpdated() {
    Platform.runLater(() -> {
      titleContainer.getChildren().clear();

      AbstractTableStructure ts = (AbstractTableStructure) ((FullTableStructureController) controller).processedElementProperty().get();
      Iterator<OperationData> it = ts.childTree().iterator();

      // we are on the root model
      // by configuration, we can configure to display or not
      // the root model name on the header of the table
      String displayRootModelNameOnHeader = ts.headerConfiguration().getPropertyValue("displayRootModelNameOnHeader");

      if(!it.hasNext() && "true".equalsIgnoreCase(displayRootModelNameOnHeader)) {
        Text l = new Text();
        l.setText(NodeHelper.getDisplayName(rootModel));
        l.getStyleClass().add("ep-structure-location-item-label");
        titleContainer.getChildren().add(l);
      }

      // ALL or LAST
      String structureTitleMode = ts.headerConfiguration().getPropertyValue("structureTitleMode");
      boolean hasProcessedItems = false;

      if(StringUtils.isEmpty(structureTitleMode) || "all".equalsIgnoreCase(structureTitleMode)) {
        if (it.hasNext()) {
          hasProcessedItems = true;

          // the root model
          Text r = new Text();
          r.setText(NodeHelper.getDisplayName(rootModel));
          r.getStyleClass().add("ep-tab-pane-header-structure-navigation-each");
          // titleContainer.getChildren().add(r);
          //titleContainer.getChildren().add(NodeHelper.getSep());

          while (it.hasNext()) {
            Text l = new Text();

            OperationData opd = it.next();

            l.setText(NodeHelper.getDisplayName(opd));
            l.getStyleClass().add("ep-tab-pane-header-structure-navigation-each");
            titleContainer.getChildren().add(l);

            if (it.hasNext()) {
              titleContainer.getChildren().add(NodeHelper.getSep());
            }
          }
        }
      }
      else if("last".equalsIgnoreCase(structureTitleMode)){
        if (it.hasNext()) {
          hasProcessedItems = true;

          Text l = new Text();
          l.getStyleClass().add("ep-structure-location-item-label");
          titleContainer.getChildren().add(l);
          while (it.hasNext()) {
            OperationData opd = it.next();
            if (!it.hasNext()) {
              l.setText(NodeHelper.getDisplayName(opd));
            }
          }
        }
      }

      if(!hasProcessedItems) {
        String defaultLocation = configuration.getPropertyValue("defaultLocation");
        if (StringUtils.isNotBlank(defaultLocation)) {
          Text l = new Text();
          l.setText(controller.getLocalised(defaultLocation));
          l.getStyleClass().add("ep-structure-location-item-label");
          titleContainer.getChildren().add(l);
        }
      }
    });
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
    return null;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitle(String title) {}


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
    return null;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public SimpleStringProperty labelProperty() {
    return null;
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
