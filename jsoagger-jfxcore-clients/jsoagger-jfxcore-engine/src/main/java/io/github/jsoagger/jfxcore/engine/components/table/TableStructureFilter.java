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

package io.github.jsoagger.jfxcore.engine.components.table;



import io.github.jsoagger.jfxcore.engine.components.table.api.ITableStructureFilter;

/**
 * Filter a table according to attribute defined as master attribute.
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class TableStructureFilter implements ITableStructureFilter {

  private String masterAttributePath;


  /**
   */
  public TableStructureFilter() {}


  /**
   * {@inheritDoc}
   */
  @Override
  public String getMasterAttributePath() {
    return masterAttributePath;
  }


  /**
   * @param masterAttributePath
   */
  public void setMasterAttributePath(String masterAttributePath) {
    this.masterAttributePath = masterAttributePath;
  }
}
