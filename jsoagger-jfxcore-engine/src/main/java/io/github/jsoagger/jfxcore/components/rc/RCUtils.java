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

package io.github.jsoagger.jfxcore.components.rc;



import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class RCUtils {

  public static String getModelMasterAttribute(OperationData data, String name) {
    if (data != null) {
      return data.getMasterAttributes().get(name) != null ? data.getMasterAttributes().get(name).toString() : "";
    }
    return null;
  }

  public static String getModelAttribute(OperationData data, String name) {
    if (data != null) {
      Object d = ReflectionUIUtils.invokeGetterOn(data, name);
      return d != null ? d.toString() : "";
    }

    return null;
  }

  public static String getModelAttribute(AbstractViewController controller, String name) {
    IOperationResult model = (IOperationResult) controller.getModel();
    if (model != null) {
      OperationData data = (OperationData) model.rootData();
      Object d = getModelAttribute(data, name);
      return d != null ? d.toString() : "";
    }

    return null;
  }

  public static String getModelMasterAttribute(AbstractViewController controller, String name) {
    IOperationResult model = (IOperationResult) controller.getModel();
    if (model != null) {
      OperationData data = (OperationData) model.rootData();
      if (data != null) {
        return data.getMasterAttributes().get(name) != null ? data.getMasterAttributes().get(name).toString() : "";
      }
    }

    return null;
  }

  public static boolean isLocked(IOperationResult sr) {
    String locked = getModelAttribute((OperationData) sr.rootData(), "attributes.workInfo.lockedSince");
    return StringUtils.isNotBlank(locked);
  }

  public static boolean isWorkingCopy(IOperationResult sr) {
    String isWorkingCopy = getModelAttribute((OperationData) sr.rootData(), "attributes.workInfo.isWorkingCopy");
    return Boolean.TRUE == Boolean.valueOf(isWorkingCopy);
  }

  public static String getLockedBy(IOperationResult or) {
    String lockedBy = getModelAttribute((OperationData) or.rootData(), "attributes.workInfo.lockedBy");
    return lockedBy;
  }
}
