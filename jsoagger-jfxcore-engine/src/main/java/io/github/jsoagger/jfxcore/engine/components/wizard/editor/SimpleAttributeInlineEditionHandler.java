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

package io.github.jsoagger.jfxcore.engine.components.wizard.editor;


import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.jfxcore.api.IAttributeInlineEditionHandler;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IFormFieldsetRow;
import io.github.jsoagger.jfxcore.api.IFormRowEditor;
import io.github.jsoagger.jfxcore.api.IInputComponentWrapper;
import io.github.jsoagger.jfxcore.api.IPersistenceServiceDelegate;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.form.FormFieldsetRow;
import io.github.jsoagger.jfxcore.engine.components.wizard.editor.components.SimpleInLineEditor;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.events.ModelUpdatedEvent;
import io.github.jsoagger.jfxcore.engine.utils.NotificationsUtils;
import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;

/**
 * Handler when editing a single row. It switches from view mode to edit mode. The edit mode of the
 * compoenent is wrapped inside a pane with OK/Cancel button.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class SimpleAttributeInlineEditionHandler implements IAttributeInlineEditionHandler {

  private AbstractViewController controller;
  private VLViewComponentXML configuration;
  private FormFieldsetRow row;


  /**
   * Constructor
   */
  public SimpleAttributeInlineEditionHandler() {}


  @Override
  public void onInlineAction(IJSoaggerController controller, VLViewComponentXML configuration, IFormFieldsetRow row, int callerIndex) {
    this.configuration = configuration;
    this.controller = (AbstractViewController) controller;
    this.row = (FormFieldsetRow) row;

    IFormRowEditor editor = new SimpleInLineEditor();
    editor.setInlineEditionHandler(this);
    editor.build(controller, configuration, row, callerIndex);
    ((SimpleInLineEditor) editor).showEditor();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void onCancel() {
    // rollback current modifications
    for(IInputComponentWrapper entry: row.getEntries()) {
      entry.cancelModification();
    }
    // switch to view mode
    row.endInlineEdit();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public boolean onDone(IFormRowEditor editor) {
    for(IInputComponentWrapper entry: row.getEntries()) {
      entry.validate();
    }

    boolean foundAtLeastOne = row.getEntries().stream().filter(r -> r.isNotValid()).findAny().isPresent();
    if (Boolean.TRUE == Boolean.valueOf(foundAtLeastOne)) {
      return false;
    } else {
      String persistableDelegate = configuration.getPropertyValue("persistableDelegate");
      if (io.github.jsoagger.core.utils.StringUtils.isNotBlank(persistableDelegate)) {
        IPersistenceServiceDelegate delegate = (IPersistenceServiceDelegate) Services.getBean(persistableDelegate);
        delegate.setSuccessHandler(opResult -> {
          if (opResult.hasBusinessError()) {
            editor.onCommitError(opResult);
          } else {
            onCommitSuccess(opResult);
            editor.onCommitSuccess(opResult);
          }
        });

        delegate.editCommit(controller, configuration, row.getEntries());
      }
      // no dlegate configured, commit a local modification
      else {
        for(IInputComponentWrapper entry: row.getEntries()) {
          entry.commitModification();
        }
        editor.onCommitSuccess(null);
      }
    }
    return true;
  }


  @Override
  public void onCommitSuccess(IOperationResult result) {
    // commit all modifications
    for(IInputComponentWrapper entry: row.getEntries()) {
      entry.commitModification();
    }
    row.endInlineEdit();
    NotificationsUtils.sendObjectUpdateSuccessNotif(controller);

    // update version
    Integer newVersion = (Integer) ReflectionUIUtils.invokeGetterOn(result, "data.attributes.version");
    ReflectionUIUtils.invokeSetterOn(row.getController().getModel(), "data.attributes.version", newVersion);

    // inform other object that this object have been updated
    ModelUpdatedEvent event = new ModelUpdatedEvent.Builder().model(result).build();
    controller.dispatchEvent(event);
  }
}
