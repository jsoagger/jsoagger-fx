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

package io.github.jsoagger.jfxcore.engine.components.security;



import java.util.HashMap;

import io.github.jsoagger.jfxcore.api.ICriteriaContext;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class CriteriaContext implements ICriteriaContext {

  /** All filters loaded for this view */
  public HashMap<String, String> filters = new HashMap<>();


  public boolean containsFilter(String key) {
    return filters.containsKey(key);
  }


  public void setFilter(String key, String value) {
    filters.put(key, value);
  }


  public String filterValue(String key) {
    return filters.get(key);
  }


  public boolean isTrue(String key) {
    return "true".equalsIgnoreCase(filters.get(key));
  }


  public HashMap<String, String> getAllFilters() {
    return (HashMap<String, String>) filters.clone();
  }
}
