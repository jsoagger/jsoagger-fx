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

package io.github.jsoagger.jfxcore.engine.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IEnumeratedValueModel;
import io.github.jsoagger.jfxcore.api.IEnumeratedValuesLoader;
import io.github.jsoagger.jfxcore.api.IMultipleEnumeratedValuesLoader;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class MultipleSoftTypeToEnumeratedValueLoader implements IMultipleEnumeratedValuesLoader {

  private Map<String, String> loaders = new HashMap<>();
  private Map<String, List<IEnumeratedValueModel>> values = new HashMap<>();


  /**
   * Constructor
   */
  public MultipleSoftTypeToEnumeratedValueLoader() {}


  @Override
  public List<IEnumeratedValueModel> loadValues(IJSoaggerController controller, VLViewComponentXML configuration) {
    loadMultipleValues(controller, configuration);
    List<IEnumeratedValueModel> allmodels = new ArrayList<>();
    for(String key: values.keySet()) {
      allmodels.addAll(values.get(key));
    }
    return allmodels;
  }


  protected Map<String, List<IEnumeratedValueModel>> loadMultipleValues(IJSoaggerController controller, VLViewComponentXML configuration) {
    for(String key: loaders.keySet()) {
      IEnumeratedValuesLoader load = (IEnumeratedValuesLoader) Services.getBean(loaders.get(key));
      List<IEnumeratedValueModel> vals = load.loadValues(controller, configuration);
      values.put(key, vals);
    }
    return values;
  }


  public Map<String, String> getLoaders() {
    return loaders;
  }


  @Override
  public Map<String, List<IEnumeratedValueModel>> getValues() {
    return values;
  }


  public void setLoaders(Map<String, String> loaders) {
    this.loaders = loaders;
  }
}
