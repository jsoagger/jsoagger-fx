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

package io.github.jsoagger.jfxcore.engine.components.form.fieldset.layout.tabed;


import java.net.URL;
import java.util.List;

import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IDisplayable;
import io.github.jsoagger.jfxcore.api.IFieldset;
import io.github.jsoagger.jfxcore.api.IFieldsetGroupLayout;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class SimpleFieldsetTopTabedLayout extends StackPane implements IFieldsetGroupLayout {

  private static final String CSS = "fieldset-group-selector-top-tabed";

  @FXML
  private Pane fieldsetTopLayoutRootStructure;

  @FXML
  private StackPane fieldsetTopLayoutHeader;

  @FXML
  private StackPane fieldsetTopLayoutCenter;

  private SimpleTopTabedItemsSelector header = new SimpleTopTabedItemsSelector();
  private VBox contentLayout = new VBox();

  private ScrollPane scrollPane = new ScrollPane();
  private VLViewComponentXML fieldsetConfiguration;

  /** All fieldsets of this group */
  private ObservableList<IFieldset> fieldsets = FXCollections.observableArrayList();


  /**
   * Constructor
   */
  public SimpleFieldsetTopTabedLayout() {
    URL location =
        SimpleFieldsetTopTabedLayout.class.getResource("SimpleFieldsetTopTabedLayout.fxml");
    NodeHelper.loadFXML(location, this);

    fieldsetTopLayoutHeader.getChildren().add(header);
    getChildren().add(fieldsetTopLayoutRootStructure);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setRootConfig(VLViewComponentXML fieldsetListConfig) {
    this.fieldsetConfiguration = fieldsetListConfig;

    boolean scroll = fieldsetListConfig.getBooleanProperty("scroll", true);
    if (scroll) {
      fieldsetTopLayoutCenter.getChildren().add(scrollPane);
      scrollPane.setFitToHeight(true);
      scrollPane.setFitToWidth(true);
      scrollPane.setContent(contentLayout);
      scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
      NodeHelper.styleClassAddAll(contentLayout, fieldsetListConfig, "contentStyleClass",
          "fieldset-group-selector-top-tabed-content");
    } else {
      NodeHelper.styleClassAddAll(contentLayout, fieldsetListConfig, "contentStyleClass",
          "fieldset-group-selector-top-tabed-content");
      fieldsetTopLayoutCenter.getChildren().add(contentLayout);
    }

    NodeHelper.styleClassAddAll(header, fieldsetConfiguration, "headerStyleClass",
        "fieldset-group-selector-top-tabed-header");
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    // MUST RETURN THIS
    return this;
  }


  /**
   * Select the first item
   */
  public void selectFirst() {
    try {
      header.selectItem(0);
      contentLayout.getChildren().clear();
      contentLayout.getChildren().add(header.getItem(0).getContent().getDisplay());
    } catch (Exception e) {
      // array indexofboudn or null pointer means config error.
    }
  }


  /**
   * @param fieldsets
   */
  public void setFieldsets(List<IFieldset> fieldsets) {
    for (IFieldset e : fieldsets) {
      addFieldset(e);
    }
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void addFieldset(IFieldset fieldset) {
    fieldsets.add(fieldset);

    FieldsetTopTabedItem groupItem = new FieldsetTopTabedItem(fieldset);
    NodeHelper.styleClassAddAll(groupItem, fieldsetConfiguration, "tabItemStyleClass",
        "fieldset-top-tabed-item");
    header.addItem(groupItem);

    groupItem.setOnMouseClicked(e -> {
      header.unSelectAll();
      groupItem.select(true);

      Node nextNode = groupItem.getContent().getDisplay();
      contentLayout.getChildren().clear();
      contentLayout.getChildren().add(nextNode);
    });
  }

  @Override
  public void addComponents(List<IBuildable> buildables) {
    for (IBuildable buildable : buildables) {
      contentLayout.getChildren().add(buildable.getDisplay());
    }
  }

  @Override
  public void addComponent(IDisplayable displayable) {
    contentLayout.getChildren().add(displayable.getDisplay());
  }

  /**
   * @return
   */
  @Override
  public ObservableList<IFieldset> getFieldsets() {
    return fieldsets;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void displayAll() {
    // in that case we force first item selection
    selectFirst();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setDisplaySelectors(Boolean displayGroupSelector) {
    header.setVisible(displayGroupSelector);
    header.managedProperty().bind(header.visibleProperty());
  }
}
