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



import java.util.List;

import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.OperationMessage;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IAttributeEditionHandler;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IFieldset;
import io.github.jsoagger.jfxcore.api.IFormFieldsetRow;
import io.github.jsoagger.jfxcore.api.IFormRowEditor;
import io.github.jsoagger.jfxcore.api.IForwardEditorFooter;
import io.github.jsoagger.jfxcore.api.IForwardEditorHeader;
import io.github.jsoagger.jfxcore.api.IInputComponentWrapper;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.form.FormFieldsetRow;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 15 janv. 2018
 */
public class SimpleForwardEditor extends StackPane implements IFormRowEditor {

  protected AbstractViewController controller;
  protected VLViewComponentXML inlineActionconfiguration;
  protected FormFieldsetRow row;
  protected IAttributeEditionHandler inlineEditionHandler;

  protected IForwardEditorHeader header;
  protected IForwardEditorFooter footer;
  protected SimpleForwardErrorPane errorPane;

  protected BorderPane rootLayout = new BorderPane();
  protected ScrollPane scrollPane = new ScrollPane();
  protected VBox content = new VBox();

  protected int callerIndex;


  /**
   * Default Constructor
   */
  public SimpleForwardEditor() {
    super();
    getStyleClass().add("ep-simple-attribute-forward-editor");
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public FormFieldsetRow getRow() {
    return row;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void build(IJSoaggerController controller, VLViewComponentXML inlineActionconfiguration, IFormFieldsetRow row, int callerIndex) {
    this.controller = (AbstractViewController) controller;
    this.inlineActionconfiguration = inlineActionconfiguration;
    this.row = (FormFieldsetRow) row;
    this.callerIndex = callerIndex;

    getChildren().add(rootLayout);
    NodeHelper.styleClassSetAll(rootLayout, inlineActionconfiguration, "forwardEditorStyleClass", "simple-forward-editor");

    // header pane
    String headerImpl = inlineActionconfiguration.getPropertyValue("headerImpl");
    if (StringUtils.isEmpty(headerImpl)) {
      header = new SimpleForwardEditorHeader();
      header.setFormRowEditor(this);
      header.buildFrom(controller, inlineActionconfiguration);
      header.getDisplay().managedProperty().bind(header.getDisplay().visibleProperty());
      rootLayout.setTop(header.getDisplay());
    }

    // content pane
    scrollPane.setFitToHeight(true);
    scrollPane.setFitToWidth(true);
    scrollPane.setContent(content);
    rootLayout.setCenter(scrollPane);
    NodeHelper.styleClassSetAll(content, inlineActionconfiguration, "forwardEditorContentStyleClass", "simple-forward-editor-content");

    // error pane
    errorPane = new SimpleForwardErrorPane();
    content.getChildren().add(errorPane);

    // footer
    String footerImpl = inlineActionconfiguration.getPropertyValue("footerImpl");
    if (StringUtils.isNotBlank(footerImpl)) {
      footer = (IForwardEditorFooter) Services.getBean(footerImpl);
      footer.setForwardEditor(this);
      footer.buildFrom(controller, inlineActionconfiguration);
      rootLayout.setBottom(footer.getDisplay());
    }

    // finalisation
    row.beginEdition();

    // some row may present multiple line
    // But the user wanted to edit only one line
    // so we just display that line clicked by the user
    // Caller index must be in row.getEntries()
    if (row.getEntries().size() > callerIndex) {
      IInputComponentWrapper r = row.getEntries().get(callerIndex);
      content.getChildren().add(r.getEditLayout());
    }

    // ASK FIRST CONTROLLER IF IT WANTS TO DISPLAY THIS NODE
    // IF NOT SEND IT TO FIELDSET
    if (!((AbstractViewController) controller).beginForwardEdition(this)) {
      IFieldset fieldset = row.getFieldset();
      if (fieldset != null) {
        fieldset.beginForwardEdition(this);
      }
    }
  }

  protected void displayValidationErrorMessage() {
    errorPane.getChildren().clear();
    for(IInputComponentWrapper e: row.getEntries()) {
      if (e.getEditInputComponent().getFirstErrorMessage() != null) {
        Label label = new Label(e.getEditInputComponent().getFirstErrorMessage());
        label.getStyleClass().add("form-inline-error-message");
        errorPane.getChildren().add(0, label);
      }
    }
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public void onCommitSuccess(IOperationResult result) {
    IFieldset fieldset = row.getFieldset();
    fieldset.endForwardEdition();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void onCommitError(IOperationResult result) {
    errorPane.displayErrorMessages(result);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void closeEditor() {
    IFormRowEditor.super.closeEditor();
    onCancel();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void onOk() {
    if (inlineEditionHandler != null) {
      boolean don = inlineEditionHandler.onDone(this);
      if (don) {
        row.endEdition();
      } else {
        displayValidationErrorMessage();
      }
    }
  }


  @Override
  public void commitModification() {
    for(IInputComponentWrapper r: row.getEntries()) {
      r.commitModification();
    }
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void onCancel() {
    if (inlineEditionHandler != null) {
      inlineEditionHandler.onCancel();
      IFieldset fieldset = getRow().getFieldset();
      if (fieldset != null) {
        fieldset.endForwardEdition();
      }
    }
    row.endEdition();
    // controller.endForwardEdition();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setInlineEditionHandler(IAttributeEditionHandler inlineEditionHandler) {
    this.inlineEditionHandler = inlineEditionHandler;
    if (header != null) {
      header.setInlineEditionHandler(inlineEditionHandler);
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public VLViewComponentXML getInlineActionConfiguration() {
    return inlineActionconfiguration;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }

  private class SimpleForwardErrorPane extends VBox {

    /**
     * Default Constructor
     */
    public SimpleForwardErrorPane() {
      getStyleClass().add("simple-forward-editor-error-pane");
      managedProperty().bind(visibleProperty());
      visibleProperty().bind(Bindings.size(getChildren()).greaterThan(0));
    }


    protected void displayErrorMessages(IOperationResult result) {
      getChildren().clear();
      List<OperationMessage> messages = result.getMessages();
      for(OperationMessage message: messages) {
        String details = message.getDetail();
        Label label = new Label(details);
        label.getStyleClass().add("form-inline-error-message");
        getChildren().add(label);
      }

      if (messages.size() > 0) {
        setVisible(true);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IAttributeEditionHandler getInlineEditionHandler() {
    return inlineEditionHandler;
  }
}
