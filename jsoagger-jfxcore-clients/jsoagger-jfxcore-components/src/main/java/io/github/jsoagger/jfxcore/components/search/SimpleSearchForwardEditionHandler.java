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

package io.github.jsoagger.jfxcore.components.search;


import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.jfxcore.api.IFormRowEditor;
import io.github.jsoagger.jfxcore.api.IInputComponentWrapper;
import io.github.jsoagger.jfxcore.engine.components.wizard.editor.SimpleAttributeForwardEditionHandler;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class SimpleSearchForwardEditionHandler extends SimpleAttributeForwardEditionHandler {

  /**
   * Constructor
   */
  public SimpleSearchForwardEditionHandler() {
    super();
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public boolean onDone(IFormRowEditor editor) {
    onCommitSuccess(null);
    editor.onCommitSuccess(null);
    return true;
  }

  /**
   * @{inheritedDoc}
   */
  @Override
  public boolean commit() {
    for(IInputComponentWrapper entry: row.getEntries()) {
      entry.commitModification();
    }
    return true;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void onCancel() {
    for(IInputComponentWrapper entry: row.getEntries()) {
      entry.cancelModification();
    }

    // switch to view mode
    row.endInlineEdit();
  }


  @Override
  public void onCommitSuccess(IOperationResult result) {
    // commit all modifications
    for(IInputComponentWrapper entry: row.getEntries()) {
      entry.commitModification();
    }
    row.endInlineEdit();
  }
}
