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

package io.github.jsoagger.jfxcore.engine.delegate;



import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.operation.JsonUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IFieldsetContent;
import io.github.jsoagger.jfxcore.api.IInputComponentWrapper;
import io.github.jsoagger.jfxcore.api.IPersistenceServiceDelegate;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class PersistenceServiceDelegate implements IPersistenceServiceDelegate {

  private static final String DATA_ATTRIBUTES_VERSION = "data.attributes.version";
  private static final String DATA_TYPE = "data.type";
  private static final String PERSISTENCE_VERSION = "persistence.version";
  private static final String PERSISTENCE_FULL_ID = "persistence.fullId";
  private static final String ACTION_NAME = "actionName";
  private static final String HIDDEN_PREFIX = "hidden.";

  // needs PersistableUpdateOperation
  private IOperation persistableUpdateOperation;
  private Consumer<IOperationResult> successHandler;
  private Consumer<Throwable> errorHandler;


  /**
   * Constructor
   */
  public PersistenceServiceDelegate() {}


  /**
   * @{inheritedDoc}
   */
  @Override
  public void editCommit(IJSoaggerController sourceController, VLViewComponentXML inlineActionconfiguration, IFieldsetContent form) {
    IPersistenceServiceDelegate.super.editCommit(sourceController, inlineActionconfiguration, form);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void editCommit(IJSoaggerController sourceController, VLViewComponentXML inlineActionConfiguration, List<IInputComponentWrapper> rows) {
    IPersistenceServiceDelegate.super.editCommit(sourceController, inlineActionConfiguration, rows);
    String action = inlineActionConfiguration.getPropertyValue(ACTION_NAME);

    Map<String, Object> vals = extracValue(rows);
    vals.put(HIDDEN_PREFIX + ACTION_NAME, action);

    // commit all mofications in same controller
    IInputComponentWrapper row0 = rows.get(0);
    Object model = row0.getController().getModel();

    String rootModelOid = (String) ReflectionUIUtils.invokeGetterOn(model, DATA_TYPE);
    int version = (Integer) ReflectionUIUtils.invokeGetterOn(model, DATA_ATTRIBUTES_VERSION);

    vals.put(HIDDEN_PREFIX + PERSISTENCE_FULL_ID, rootModelOid);
    vals.put(HIDDEN_PREFIX + PERSISTENCE_VERSION, version);

    //persistableUpdateOperation.doOperation(JsonObject.fromObject(vals), this::successHandler, getErrorHandler());
    persistableUpdateOperation.doOperation(JsonUtils.toJsonObject(vals), this::successHandler, getErrorHandler());
  }


  /**
   * Getter of successHandler
   *
   * @return the successHandler
   */
  @Override
  public Consumer<IOperationResult> getSuccessHandler() {
    return successHandler;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void setErrorHandler(Consumer<Throwable> errorHandler) {
    IPersistenceServiceDelegate.super.setErrorHandler(errorHandler);
    this.errorHandler = errorHandler;
  }


  /**
   * Setter of successHandler
   *
   * @param successHandler the successHandler to set
   */
  @Override
  public void setSuccessHandler(Consumer<IOperationResult> successHandler) {
    this.successHandler = successHandler;
  }
}
