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
import io.github.jsoagger.jfxcore.api.IAttributeForwardEditionHandler;
import io.github.jsoagger.jfxcore.api.IFieldset;
import io.github.jsoagger.jfxcore.engine.components.wizard.editor.components.SimpleForwardEditor;

/**
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class SearchAttributeForwardEditor extends SimpleForwardEditor {

  /**
   * Constructor
   */
  public SearchAttributeForwardEditor() {
    super();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void onCancel() {
    super.onCancel();
    controller.endForwardEdition();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void closeEditor() {
    if (inlineEditionHandler != null) {
      IFieldset fieldset = getRow().getFieldset();
      if (fieldset != null) {
        fieldset.endForwardEdition();
      }
    }
    row.endEdition();
    controller.endForwardEdition();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void onOk() {
    super.onOk();
    controller.endForwardEdition();
  }

  @Override
  public void commitModification() {
    ((IAttributeForwardEditionHandler)inlineEditionHandler).commit();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void onCommitError(IOperationResult result) {
    controller.endForwardEdition();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void onCommitSuccess(IOperationResult result) {
    controller.endForwardEdition();
  }
}
