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

package io.github.jsoagger.jfxcore.engine.components.wizard.editor.controller;



import java.util.List;

import io.github.jsoagger.jfxcore.engine.client.apiimpl.ActionResult;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.jfxcore.api.IActionResult;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IInputComponentWrapper;
import io.github.jsoagger.jfxcore.api.IPersistenceServiceDelegate;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.form.FormFieldsetRow;
import io.github.jsoagger.jfxcore.engine.components.header.event.HeaderNavbarFireBackButton;
import io.github.jsoagger.jfxcore.engine.components.header.event.HeaderNavbarSetCustomRightActions;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardViewController;
import io.github.jsoagger.jfxcore.engine.events.CoreEvent;
import io.github.jsoagger.jfxcore.engine.events.GenericEvent;
import io.github.jsoagger.jfxcore.engine.events.ModelUpdatedEvent;
import io.github.jsoagger.jfxcore.engine.utils.ComponentUtils;
import io.github.jsoagger.jfxcore.engine.utils.NotificationsUtils;
import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class CoreSimpleAttributeForwardEditionController extends StandardViewController {

  protected AbstractViewController sourceController;
  protected VLViewComponentXML inlineActionConfiguration;
  protected FormFieldsetRow row;

  protected VBox layout = new VBox();
  protected Parent previousParent;


  /**
   * Constructor
   */
  public CoreSimpleAttributeForwardEditionController() {
    super();
    registerListener(CoreEvent.HeaderNavbarBackButtonClicked);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public synchronized <T extends GenericEvent> void handle(T e) {
    super.handle(e);
    if (e.isA(CoreEvent.HeaderNavbarBackButtonClicked)) {
      Platform.runLater(() -> {
        // if the current node is editing, cancel modifications and go
        // before going back to previous page
        row.endEdition();
      });
    }
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  protected void process() {
    super.process();

    row.endEdition();
    for(IInputComponentWrapper r: row.getEntries()) {
      layout.getChildren().add(r.getDisplay());
    }

    layout.getStyleClass().addAll("white-background", "shadowed-pane", "padding-16-32-16-32");

    // shadow will not be visible without padding of this pane
    StackPane et = new StackPane();
    et.setPrefHeight(-1);
    et.setStyle("-fx-padding:4;-fx-background-color:transparent;");
    et.getChildren().add(layout);

    buildHeaderActions();
    processedView(et);
  }


  private void buildHeaderActions() {
    VLViewComponentXML root = getRootComponent();
    VLViewComponentXML headerActions = root.getComponentById("HeaderActions").orElse(null);
    if (headerActions != null) {

      // we dislay only right actions the DONE button
      VLViewComponentXML rightActions = headerActions.getComponentById("RightActions").orElse(null);
      if ((rightActions != null) && rightActions.hasSubComponent()) {
        List<IBuildable> actions = ComponentUtils.resolveAndGenerate(this, rightActions.getSubcomponents());

        // set header actions
        HeaderNavbarSetCustomRightActions ev = new HeaderNavbarSetCustomRightActions.Builder().actions(actions).build();
        dispatchEvent(ev);
      }
    }
  }


  /**
   * Commit current modifications
   */
  public IActionResult commit() {
    String persistableDelegate = inlineActionConfiguration.getPropertyValue("persistableDelegate");
    if (io.github.jsoagger.core.utils.StringUtils.isNotBlank(persistableDelegate)) {
      IPersistenceServiceDelegate delegate = (IPersistenceServiceDelegate) Services.getBean(persistableDelegate);
      delegate.setSuccessHandler(opResult -> {
        if (opResult.hasBusinessError()) {
          onCommitError(opResult);
        } else {
          onCommitSuccess(opResult);
        }
      });

      delegate.setErrorHandler(this::onCommitError);
      delegate.editCommit(sourceController, inlineActionConfiguration, row.getEntries());
    }
    return ActionResult.success();
  }


  /**
   * @param opResult
   */
  private void onCommitError(IOperationResult opResult) {
  }


  /**
   * @param opResult
   */
  private void onCommitError(Throwable e) {
    //System.out.println(e);
  }


  private void onCommitSuccess(IOperationResult result) {
    Platform.runLater(() -> {
      layout.getChildren().clear();
      row.endEdition();
    });

    // go back
    // tell the header nav bar to go back
    HeaderNavbarFireBackButton ev = new HeaderNavbarFireBackButton.Builder().build();

    // attach commit result may be some warning to display
    ev.setModel(ActionResult.success());
    dispatchEvent(ev);

    NotificationsUtils.sendObjectUpdateSuccessNotif(this);

    // update version
    Integer newVersion = (Integer) ReflectionUIUtils.invokeGetterOn(result, "data.attributes.version");
    ReflectionUIUtils.invokeSetterOn(row.getController().getModel(), "data.attributes.version", newVersion);

    // inform other object that this object have been updated
    ModelUpdatedEvent event = new ModelUpdatedEvent.Builder().model(result).build();
    dispatchEvent(event);
  }


  /**
   * Getter of sourceController
   *
   * @return the sourceController
   */
  public AbstractViewController getSourceController() {
    return sourceController;
  }


  /**
   * Setter of sourceController
   *
   * @param sourceController the sourceController to set
   */
  public void setSourceController(AbstractViewController sourceController) {
    this.sourceController = sourceController;
  }


  /**
   * Getter of inlineActionConfiguration
   *
   * @return the inlineActionConfiguration
   */
  public VLViewComponentXML getInlineActionConfiguration() {
    return inlineActionConfiguration;
  }


  /**
   * Setter of inlineActionConfiguration
   *
   * @param inlineActionConfiguration the inlineActionConfiguration to set
   */
  public void setInlineActionConfiguration(VLViewComponentXML inlineActionConfiguration) {
    this.inlineActionConfiguration = inlineActionConfiguration;
  }


  /**
   * Getter of layout
   *
   * @return the layout
   */
  public VBox getLayout() {
    return layout;
  }


  /**
   * Setter of layout
   *
   * @param layout the layout to set
   */
  public void setLayout(VBox layout) {
    this.layout = layout;
  }


  /**
   * Getter of row
   *
   * @return the row
   */
  public FormFieldsetRow getRow() {
    return row;
  }


  /**
   * Setter of row
   *
   * @param row the row to set
   */
  public void setRow(FormFieldsetRow row) {
    this.row = row;
  }
}
