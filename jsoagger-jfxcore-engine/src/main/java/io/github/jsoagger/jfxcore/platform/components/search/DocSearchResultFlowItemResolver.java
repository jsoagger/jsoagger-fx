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

package io.github.jsoagger.jfxcore.platform.components.search;



import com.google.gson.JsonObject;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IFlowItemResolver;
import io.github.jsoagger.jfxcore.api.ITableStructure;
import io.github.jsoagger.jfxcore.components.search.controller.SearchResultController;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class DocSearchResultFlowItemResolver implements IFlowItemResolver {

  /**
   * Constructor
   */
  public DocSearchResultFlowItemResolver() {}


  /**
   * {@inheritDoc}
   */
  @Override
  public String getFlowItem(IJSoaggerController c, ITableStructure tableStructure) {
    String masterFlow = tableStructure.getContentConfig().propertyValueOf("masterFlowItemImpl").orElse(null);
    String iterationFlow = tableStructure.getContentConfig().propertyValueOf("iterationsFlowItemImpl").orElse(null);

    SearchResultController src = (SearchResultController) c;

    JsonObject pr = src.currentQuery();
    boolean mst = true;

    if (pr != null) {
      Object qms = pr.get("data.attributes.search_query.extra.queryMaster").getAsString();
      mst = Boolean.valueOf(String.valueOf(qms));
    }

    if (mst) {
      return masterFlow;
    }
    return iterationFlow;
  }
}

