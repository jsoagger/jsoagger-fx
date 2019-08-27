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

package io.github.jsoagger.jfxcore.engine.components.table.api;



import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.engine.utils.ReflectionUIUtils;

/**
 * Provides Client side filtering capability to table structure.
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public interface ITableStructureFilter {

  /**
   *
   * @param data
   * @param filter
   * @return
   */
  public default boolean isDisplayable(OperationData data, String filter) {
    if (data == null) {
      return true;
    }

    if (filter == null) {
      return true;
    }

    final String attr = getMasterAttributePath();
    final String d = (String) ReflectionUIUtils.invokeGetterOn(data, attr);

    if (d == null) {
      return true;
    }

    return d.toLowerCase().contains(filter.toLowerCase());
  }


  public String getMasterAttributePath();
}
