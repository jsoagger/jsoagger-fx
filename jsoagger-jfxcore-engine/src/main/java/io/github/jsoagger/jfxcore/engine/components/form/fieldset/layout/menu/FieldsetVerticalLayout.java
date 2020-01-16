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


import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IFieldset;
import io.github.jsoagger.jfxcore.api.IFieldsetGroupLayout;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class FieldsetVerticalLayout extends StackPane implements IFieldsetGroupLayout {

  private ObservableList<IFieldset> fieldsets = FXCollections.observableArrayList();

  private ScrollPane scrollPane = new ScrollPane();
  private VBox content = new VBox();
  private boolean scroll = false;


  /**
   * Constructor
   */
  public FieldsetVerticalLayout() {

  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setDisplaySelectors(Boolean displayGroupSelector) {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void addFieldset(IFieldset fieldset) {
    fieldsets.add(fieldset);
    content.getChildren().add(fieldset.getDisplay());
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void displayAll() {
    if (scroll) {
      scrollPane.setFitToHeight(true);
      scrollPane.setFitToWidth(true);
      scrollPane.setContent(content);
      getChildren().add(scrollPane);
    } else {
      getChildren().add(content);
      NodeHelper.setHVGrow(content);
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
   * @{inheritedDoc}
   */
  @Override
  public ObservableList<IFieldset> getFieldsets() {
    return fieldsets;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setRootConfig(VLViewComponentXML fieldsetListConfig) {
    NodeHelper.styleClassAddAll(content, fieldsetListConfig, "contentLayoutStyleClass", "fieldset-vertical-layout-pane");
  }
}
