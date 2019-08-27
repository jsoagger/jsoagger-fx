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

package io.github.jsoagger.jfxcore.engine.components.form.fieldset;


import java.text.MessageFormat;

import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IFieldsetHeader;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Header of a fieldset
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class FormFieldsetHeader extends HBox implements IFieldsetHeader {

  private static final String FIELDSET_HEADER = "form-fieldset-listViewPaneHeader";
  private static final String REGEX = ",";
  private static final String TITLE_STYLE_CLASS = "titleStyleClass";

  private VLViewComponentXML configuration = null;
  private AbstractViewController controller = null;
  private String title;


  /**
   * Constructor
   */
  public FormFieldsetHeader() {
    getStyleClass().add(FIELDSET_HEADER);
  }


  /**
   * Constructor
   */
  @Override
  public void build(VLViewComponentXML configuration, IJSoaggerController controller) {
    this.configuration = configuration;
    this.controller = (AbstractViewController) controller;

    buildLabel();
  }


  /**
   * Build the title
   *
   * @param blocConfig
   * @return
   */
  protected void buildLabel() {
    setId(MessageFormat.format("fieldset-title{0}", configuration.getId()));
    final Label label = new Label();
    NodeHelper.setTitle(label, configuration, controller);
    NodeHelper.styleClassSetAll(label, configuration, "titleStyleClass", "form-fieldset-title");
    NodeHelper.styleClassSetAll(this, configuration, "headerStyleClass", "form-fieldset-header");

    this.title = label.getText();
    getChildren().addAll(label);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public VLViewComponentXML getConfiguration() {
    return configuration;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setConfiguration(VLViewComponentXML configuration) {
    this.configuration = configuration;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public IJSoaggerController getController() {
    return (IJSoaggerController) controller;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setController(IJSoaggerController controller) {
    this.controller = (AbstractViewController) controller;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getTitle() {
    return title;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setTitle(String title) {
    this.title = title;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void displayErrors(boolean isValid) {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }
}
