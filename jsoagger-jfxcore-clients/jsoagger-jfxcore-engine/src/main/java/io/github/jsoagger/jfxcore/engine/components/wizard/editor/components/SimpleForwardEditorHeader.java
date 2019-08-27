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

package io.github.jsoagger.jfxcore.engine.components.wizard.editor.components;

import io.github.jsoagger.jfxcore.api.IAttributeEditionHandler;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IFormRowEditor;
import io.github.jsoagger.jfxcore.api.IForwardEditorHeader;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.utils.IconUtils;

import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class SimpleForwardEditorHeader extends StackPane implements IForwardEditorHeader {

  protected Hyperlink backButton = new Hyperlink();
  protected Hyperlink doneDutton = new Hyperlink();
  protected HBox pane = new HBox();
  protected IAttributeEditionHandler inlineEditionHandler;
  protected IFormRowEditor formRowEditor;


  /**
   * Default Constructor
   */
  public SimpleForwardEditorHeader() {
    super();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    getChildren().add(pane);

    getStyleClass().add("simple-forward-editor-header-external-wrapper");
    pane.getStyleClass().add("simple-forward-editor-header");

    backButton.getStyleClass().addAll("rounded-primary-button-button", "transparent-focus");
    doneDutton.getStyleClass().addAll("rounded-primary-button-button", "transparent-focus");

    //backButton.setText(controller.getGLocalised("BACK_LABEL"));
    //doneDutton.setText(controller.getGLocalised("DONE_LABEL"));

    backButton.setText("DONE");
    //doneDutton.setText("OK");

    IconUtils.setFontIcon("fa-long-arrow-left:10", backButton);
    IconUtils.setFontIcon("fa-check:10", doneDutton);
    pane.getChildren().addAll(backButton, NodeHelper.horizontalSpacer(), NodeHelper.horizontalSpacer());

    //backButton.setOnAction(e -> onCancel());
    backButton.setOnAction(e -> formRowEditor.closeEditor());
    doneDutton.setOnAction(e -> onOk());
  }


  private void onOk() {
    formRowEditor.onOk();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }


  public Hyperlink getBackButton() {
    return backButton;
  }


  public void setBackButton(Hyperlink backButton) {
    this.backButton = backButton;
  }


  public Hyperlink getDoneDutton() {
    return doneDutton;
  }


  public void setDoneDutton(Hyperlink doneDutton) {
    this.doneDutton = doneDutton;
  }


  public HBox getPane() {
    return pane;
  }


  public void setPane(HBox pane) {
    this.pane = pane;
  }


  public IAttributeEditionHandler getInlineEditionHandler() {
    return inlineEditionHandler;
  }


  @Override
  public void setInlineEditionHandler(IAttributeEditionHandler inlineEditionHandler) {
    this.inlineEditionHandler = inlineEditionHandler;
  }


  public IFormRowEditor getFormRowEditor() {
    return formRowEditor;
  }


  @Override
  public void setFormRowEditor(IFormRowEditor formRowEditor) {
    this.formRowEditor = formRowEditor;
  }
}
