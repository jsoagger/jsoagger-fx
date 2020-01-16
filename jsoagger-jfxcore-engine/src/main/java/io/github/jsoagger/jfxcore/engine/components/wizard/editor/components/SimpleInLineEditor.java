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
import io.github.jsoagger.jfxcore.api.IAttributeEditionHandler;
import io.github.jsoagger.jfxcore.api.IAttributeInlineEditionHandler;
import io.github.jsoagger.jfxcore.api.IFormFieldsetRow;
import io.github.jsoagger.jfxcore.api.IFormRowEditor;
import io.github.jsoagger.jfxcore.api.IInputComponentWrapper;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.form.FormFieldsetRow;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.interpolator.EasingInterpolator;
import io.github.jsoagger.jfxcore.engine.interpolator.EasingMode;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Used to edit single row without hanving to switch to new page.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class SimpleInLineEditor extends StackPane implements IFormRowEditor {

  protected Label title = new Label();
  protected Label doneOKLabel = new Label();
  protected ProgressIndicator waitingIndicator = new ProgressIndicator();
  protected final VBox allOverContainer = new VBox();
  protected final HBox actions = new HBox();

  protected final Button okButton = new Button();
  protected final Button cancelButton = new Button();

  protected final HBox rowsContainer = new HBox();
  protected VBox errorMessagesContainer = new VBox();

  protected AbstractViewController controller;
  protected VLViewComponentXML inlineActionconfiguration;
  protected FormFieldsetRow row;
  protected int callerIndex;
  protected SimpleObjectProperty<ProcessingState> status = new SimpleObjectProperty(ProcessingState.WAITING);
  protected IAttributeInlineEditionHandler inlineEditionHandler;


  /**
   * Constructor
   *
   * @param viewInputWrapper
   */
  public SimpleInLineEditor() {
    super();
    setPrefHeight(USE_COMPUTED_SIZE);
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

    row.beginInlineEdit(this);

    getStyleClass().add("form-input-inline-editor-wrapper");
    allOverContainer.getStyleClass().add("form-input-inline-editor-internal-wrapper");

    buildProcessing();
    allOverContainer.getChildren().addAll(errorMessagesContainer, rowsContainer);
    buildActions();

    NodeHelper.setHVGrow(allOverContainer);
    getChildren().addAll(allOverContainer, waitingIndicator, doneOKLabel);

    String ttl = inlineActionconfiguration.getPropertyValue("title", "UPDATE_LABEL");
    title.setText(controller.getLocalised(ttl, inlineActionconfiguration));
    title.getStyleClass().add("inline-editor-titlepane-header-title");
    allOverContainer.getChildren().add(0, title);

    // some row may present multiple line
    // But the user wanted to edit only one line
    // so we just display that line clicked by the user
    // Caller index must be in row.getEntries()
    if (row.getEntries().size() > callerIndex) {
      IInputComponentWrapper r = row.getEntries().get(callerIndex);
      rowsContainer.getChildren().add(r.getEditLayout());
    }
  }


  private void buildProcessing() {
    waitingIndicator.maxHeightProperty().bind(waitingIndicator.maxWidthProperty());
    waitingIndicator.maxWidthProperty().set(30);

    BooleanBinding isWaiting = Bindings.equal(ProcessingState.WAITING, status);
    BooleanBinding isDoneError = Bindings.equal(ProcessingState.DONE_KO, status);

    doneOKLabel.visibleProperty().bind(Bindings.equal(ProcessingState.DONE_OK, status));
    waitingIndicator.visibleProperty().bind(Bindings.equal(ProcessingState.PROCESSING, status));
    allOverContainer.visibleProperty().bind(Bindings.or(isWaiting, isDoneError));

    // ok icon
    doneOKLabel.setText("DONE");
    status.addListener((ChangeListener<ProcessingState>) (observable, oldValue, newValue) -> {
      if (newValue == ProcessingState.DONE_KO) {
        pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), true);
        pseudoClassStateChanged(PseudoClass.getPseudoClass("status"), false);
      } else if (newValue == ProcessingState.PROCESSING) {
        pseudoClassStateChanged(PseudoClass.getPseudoClass("status"), true);
        pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), false);
      }
    });
  }


  private void buildActions() {
    okButton.getStyleClass().addAll("button-transparent-border-transparent");
    okButton.setDefaultButton(true);
    okButton.setText(controller.getGLocalised("DONE_LABEL").toUpperCase());

    cancelButton.getStyleClass().addAll("button-transparent-border-transparent");
    cancelButton.setText(controller.getGLocalised("CANCEL_LABEL").toUpperCase());

    actions.getStyleClass().add("form-actions-inline-editor-wrapper");
    actions.getChildren().addAll(NodeHelper.horizontalSpacer(), cancelButton, okButton);

    allOverContainer.getChildren().add(actions);
    VBox.setMargin(actions, new Insets(16, 0, 0, 0));

    cancelButton.setOnAction(e -> onCancel());
    okButton.setOnAction(e -> onOk());
  }


  @Override
  public void onOk() {
    if (inlineEditionHandler != null) {
      boolean don = inlineEditionHandler.onDone(this);
      if (don) {
        hideEditor();
      } else {
        displayValidationErrorMessage();
      }
    }

  }


  @Override
  public void onCancel() {
    if (inlineEditionHandler != null) {
      inlineEditionHandler.onCancel();
    }
    hideEditor();
  }


  @Override
  public void onCommitSuccess(IOperationResult result) {
    status.set(ProcessingState.DONE_OK);
    pseudoClassStateChanged(PseudoClass.getPseudoClass("false"), true);
    hideEditor();
  }


  @Override
  public void onCommitError(IOperationResult result) {
    status.set(ProcessingState.DONE_KO);
    pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), true);
    animateCommitError(result);
  }


  private void animateCommitError(IOperationResult result) {
    ScaleTransition st = NodeHelper.scaleIn(row, Duration.millis(80));
    EasingInterpolator ei = new EasingInterpolator(EasingMode.IN_OUT_EXPO);
    st.setInterpolator(ei);
    st.setAutoReverse(true);
    st.setCycleCount(3);
    st.setFromX(0.9);
    st.setFromY(0.8);
    displayErrorMessages(result);
    st.play();
  }


  protected void displayValidationErrorMessage() {
    for(IInputComponentWrapper e: row.getEntries()) {
      if (e.getEditInputComponent().getFirstErrorMessage() != null) {
        Label label = new Label(e.getEditInputComponent().getFirstErrorMessage());
        label.getStyleClass().add("form-inline-error-message");
        errorMessagesContainer.getChildren().add(label);
      }
    }
  }

  protected void displayErrorMessages(IOperationResult result) {
    errorMessagesContainer.getChildren().clear();
    List<OperationMessage> messages = result.getMessages();
    for(OperationMessage message: messages) {
      String details = message.getDetail();
      Label label = new Label(details);
      label.getStyleClass().add("form-inline-error-message");
      errorMessagesContainer.getChildren().add(label);
    }
  }


  public void hideEditor() {
    row.endInlineEdit();
    FadeTransition ft = NodeHelper.fadeIn(row, Duration.millis(200));
    ft.play();
  }


  public void showEditor() {
    ScaleTransition st = NodeHelper.scaleIn(row, Duration.millis(100));
    EasingInterpolator ei = new EasingInterpolator(EasingMode.OUT_CIRC);
    st.setInterpolator(ei);
    st.setFromX(0.7);
    st.setFromY(0.7);
    st.play();
  }


  /**
   * @param width
   */
  public void setContainerWidth(double width) {
    allOverContainer.setMinWidth(width);
  }


  /**
   * @return
   */
  public Node getRootContent() {
    return allOverContainer;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Node getDisplay() {
    return this;
  }

  /**
   * @author Ramilafananana VONJISOA
   * @mailto yvonjisoa@nexitia.com
   * @date 2019
   */
  private enum ProcessingState {
    PROCESSING, DONE_OK, DONE_KO, WAITING;
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
  public void setInlineEditionHandler(IAttributeEditionHandler inlineEditionHandler) {
    this.inlineEditionHandler = (IAttributeInlineEditionHandler) inlineEditionHandler;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public FormFieldsetRow getRow() {
    return null;
  }


  @Override
  public IAttributeEditionHandler getInlineEditionHandler() {
    return inlineEditionHandler;
  }

  @Override
  public void commitModification() {
    for(IInputComponentWrapper r: row.getEntries()) {
      r.commitModification();
    }
  }
}
