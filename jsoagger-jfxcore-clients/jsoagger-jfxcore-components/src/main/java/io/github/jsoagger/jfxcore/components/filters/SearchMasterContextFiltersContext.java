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

package io.github.jsoagger.jfxcore.components.filters;



import io.github.jsoagger.jfxcore.api.ICriteriaContext;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.security.IContextParamSetter;
import io.github.jsoagger.jfxcore.components.search.controller.SearchResultController;
import com.google.gson.JsonElement;

/**
 * // MUST BE FROM SEARCH RESULT // USE ANOTHER ONE IF NOT FROM THAT VIEW
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class SearchMasterContextFiltersContext implements IContextParamSetter {

  /**
   * Constructor
   */
  public SearchMasterContextFiltersContext() {}


  /**
   * {@inheritDoc}
   */
  @Override
  public void process(IJSoaggerController view, Object model, ICriteriaContext criteriaContext) {
    SearchResultController src = (SearchResultController) view;
    if (src.currentQuery() != null) {
      JsonElement qms = src.currentQuery().get("data.attributes.search_query.extra.queryMaster");
      boolean mst = qms == null ? false: qms.isJsonNull() ? false : qms.getAsBoolean();

      if (mst) {
        criteriaContext.setFilter("isMasterSearch", Boolean.TRUE.toString());
        criteriaContext.setFilter("isIterationSearch", Boolean.FALSE.toString());
      } else {
        criteriaContext.setFilter("isIterationSearch", Boolean.TRUE.toString());
        criteriaContext.setFilter("isMasterSearch", Boolean.FALSE.toString());
      }
    }
  }
}
