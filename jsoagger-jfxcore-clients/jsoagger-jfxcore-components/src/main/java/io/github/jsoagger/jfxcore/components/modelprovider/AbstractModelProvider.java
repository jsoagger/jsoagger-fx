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

package io.github.jsoagger.jfxcore.components.modelprovider;



import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.jfxcore.api.IModelProvider;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 29 janv. 2018
 */
public abstract class AbstractModelProvider implements IModelProvider {

  protected IOperationResult result;
  protected AbstractViewController controller;


  /**
   * Default Constructor
   */
  public AbstractModelProvider() {}


  protected void onModelLoadSuccess(IOperationResult operationResult) {
    this.result = operationResult;
    if (controller != null) {
      controller.setModel(operationResult);
    }
  }


  protected void onModelLoadError(Throwable ex) {
    ex.printStackTrace();
  }
}
