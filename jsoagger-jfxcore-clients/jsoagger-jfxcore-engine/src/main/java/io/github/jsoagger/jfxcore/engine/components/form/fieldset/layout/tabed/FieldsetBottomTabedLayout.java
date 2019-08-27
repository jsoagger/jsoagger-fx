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

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IFieldset;
import io.github.jsoagger.jfxcore.api.IFieldsetGroupLayout;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.form.fieldset.FormFieldset;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Default implementation of {@link IFieldsetGroupLayout} which displays a selector
 * {@link FieldsetTopTabedItemsSelector} on the top of the {@link VBox} and fieldsets content in the
 * center area.
 * <p>
 * {@link FieldsetTopTabedItemsSelector} can be configured to be hidden.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class FieldsetBottomTabedLayout extends StackPane implements IFieldsetGroupLayout {

  private static final String CSS = "fieldset-group-selector-top-tabed";

  @FXML
  private Pane fieldsetBottomLayoutRootStructure;

  @FXML
  private Pane fieldsetBottomLayoutCenter;

  private FieldsetTopTabedItemsSelector footer = new FieldsetTopTabedItemsSelector();
  private CenteredSingleElementLayout contentLayout = new CenteredSingleElementLayout();

  private ScrollPane scrollPane = new ScrollPane();

  /** All fieldsets of this group */
  private ObservableList<IFieldset> fieldsets = FXCollections.observableArrayList();


  /**
   * Constructor
   */
  public FieldsetBottomTabedLayout() {
    URL location = FieldsetBottomTabedLayout.class.getResource("FieldsetBottomTabedLayout.fxml");
    NodeHelper.loadFXML(location, this);
    getChildren().add(fieldsetBottomLayoutRootStructure);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setRootConfig(VLViewComponentXML fieldsetListConfig) {
    boolean scroll = fieldsetListConfig.getBooleanProperty("scroll", true);
    if (scroll) {
      fieldsetBottomLayoutCenter.getChildren().add(scrollPane);
      scrollPane.setFitToHeight(true);
      scrollPane.setFitToWidth(true);
      scrollPane.setContent(contentLayout);
    } else {
      NodeHelper.styleClassAddAll(contentLayout, fieldsetListConfig, "styleClass", "fieldset-group-selector-top-tabed-content");
      fieldsetBottomLayoutCenter.getChildren().add(contentLayout);
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
   * Select the first item
   */
  public void selectFirst() {
    try {
      footer.selectItem(0);
      contentLayout.setCenterElement(footer.getItem(0).getContent().getDisplay());
    } catch (Exception e) {
      // array indexofboudn or null pointer means config error.
    }
  }


  /**
   * @param fieldsets
   */
  public void setFieldsets(List<FormFieldset> fieldsets) {
    for(FormFieldset e: fieldsets) {
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
    footer.addItem(groupItem);

    groupItem.setOnMouseClicked(e -> {
      footer.unSelectAll();
      groupItem.select(true);

      Node nextNode = groupItem.getContent().getDisplay();
      contentLayout.setCenterElement(nextNode);
    });
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
    footer.setVisible(displayGroupSelector);
    footer.managedProperty().bind(footer.visibleProperty());
  }
}
