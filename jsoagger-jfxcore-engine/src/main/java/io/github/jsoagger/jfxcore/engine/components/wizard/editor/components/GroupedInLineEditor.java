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



import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.core.utils.StringUtils;
import org.kordamp.ikonli.javafx.FontIcon;

import io.github.jsoagger.jfxcore.api.IAttributeEditionHandler;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IEditInputComponent;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IFieldsetContent;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.form.fieldset.FormFieldsetContent;
import io.github.jsoagger.jfxcore.engine.components.form.row.InputComponentWrapper;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.interpolator.EasingInterpolator;
import io.github.jsoagger.jfxcore.engine.interpolator.EasingMode;
import io.github.jsoagger.jfxcore.api.services.Services;

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
 * Used to edit group of row in a form.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class GroupedInLineEditor extends StackPane implements IBuildable {

  protected Label errorMessage = new Label();
  protected Label doneOKLabel = new Label();
  protected ProgressIndicator waitingIndicator = new ProgressIndicator();
  protected final VBox allOverContainer = new VBox();
  protected final HBox actions = new HBox();

  protected final Button okButton = new Button();
  protected final Button cancelButton = new Button();

  protected AbstractViewController controller;
  protected VLViewComponentXML inlineActionconfiguration;

  protected IEditInputComponent editInputComponent;
  protected SimpleObjectProperty<ProcessingState> status = new SimpleObjectProperty(ProcessingState.WAITING);

  protected IFieldsetContent content = null;


  /**
   * Constructor
   *
   * @param viewInputWrapper
   */
  public GroupedInLineEditor() {
    super();
    setPrefHeight(USE_COMPUTED_SIZE);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML inlineActionconfiguration) {
    this.controller = (AbstractViewController) controller;
    this.inlineActionconfiguration = inlineActionconfiguration;

    getStyleClass().add("form-input-inline-editor-wrapper");
    allOverContainer.getStyleClass().add("form-input-inline-editor-internal-wrapper");

    buildProcessing();
    buildActions();

    content = new FormFieldsetContent();
    content.build(inlineActionconfiguration, controller);
    allOverContainer.getChildren().add(0, content.getDisplay());

    NodeHelper.setHVGrow(allOverContainer);
    getChildren().addAll(allOverContainer, waitingIndicator, doneOKLabel);
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
    FontIcon fontIcon = new FontIcon();
    fontIcon.setStyle("-fx-icon-color:-accent-color-500;-fx-icon-code:mdi-check;-fx-icon-size:30;");
    doneOKLabel.setGraphic(fontIcon);

    status.addListener((ChangeListener<ProcessingState>) (observable, oldValue, newValue) -> {
      if (newValue == ProcessingState.DONE_KO) {
        pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), true);
        pseudoClassStateChanged(PseudoClass.getPseudoClass("status"), false);
      }

      else if (newValue == ProcessingState.PROCESSING) {
        pseudoClassStateChanged(PseudoClass.getPseudoClass("status"), true);
        pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), false);
      }
    });
  }


  public void buildInput() {
    allOverContainer.getChildren().add(0, errorMessage);
    errorMessage.getStyleClass().add("form-input-inline-editor-errorMessage");
    errorMessage.visibleProperty().bind(errorMessage.textProperty().isNotEmpty());
    allOverContainer.getChildren().add(editInputComponent.getDisplay());
  }


  private void buildActions() {
    okButton.getStyleClass().addAll("flat-button", "transparent-focus");
    okButton.setFocusTraversable(false);
    okButton.setDefaultButton(true);
    okButton.setText(controller.getGLocalised("DONE_LABEL").toUpperCase());

    cancelButton.getStyleClass().addAll("flat-button", "transparent-focus");
    cancelButton.setText(controller.getGLocalised("CANCEL_LABEL").toUpperCase());
    cancelButton.setFocusTraversable(false);

    actions.getStyleClass().add("form-actions-inline-editor-wrapper");
    actions.getChildren().addAll(NodeHelper.horizontalSpacer(), cancelButton, okButton);
    cancelButton.setOnAction(e -> {
      hideEditor();
    });

    okButton.setOnAction(e -> {
      try {
        String callBackHandler = inlineActionconfiguration.getPropertyValue("actionHandler");
        IAttributeEditionHandler handler = null;
        if (StringUtils.isNotBlank(callBackHandler)) {
          handler = (IAttributeEditionHandler) Services.getBean(callBackHandler);
          // handler.done(controller, inlineActionconfiguration,
          // content);
          status.set(ProcessingState.DONE_OK);
          hideEditor();
        }
      } catch (Exception e1) {
        status.set(ProcessingState.DONE_KO);
        e1.printStackTrace();
      }
    });

    allOverContainer.getChildren().add(actions);
    VBox.setMargin(actions, new Insets(16, 0, 0, 0));
  }


  public void hideEditor() {
    ScaleTransition st = NodeHelper.scaleOut(this.getParent(), Duration.millis(300));
    EasingInterpolator ei = new EasingInterpolator(EasingMode.IN_CIRC);
    st.setInterpolator(ei);
    st.setOnFinished(c -> {
      // owner.endInlineEdition();
    });
    st.play();
  }


  public void showEditor() {
    // owner.beginInlineEdition(this);
    ScaleTransition st = NodeHelper.scaleIn(this.getParent(), Duration.millis(300));
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

  protected InputComponentWrapper owner;


  /**
   * @param componentWrapper
   */
  public void setOwner(InputComponentWrapper componentWrapper) {
    this.owner = componentWrapper;
  }

  /**
   * @author Ramilafananana VONJISOA
   * @mailto yvonjisoa@nexitia.com
   * @date 2019
   */
  private static enum ProcessingState {
    PROCESSING, DONE_OK, DONE_KO, WAITING;
  }
}
