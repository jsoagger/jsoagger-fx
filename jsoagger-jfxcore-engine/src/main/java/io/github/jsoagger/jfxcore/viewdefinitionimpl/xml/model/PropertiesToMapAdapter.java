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

package io.github.jsoagger.jfxcore.viewdefinitionimpl.xml.model;




import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class PropertiesToMapAdapter extends XmlAdapter<VLViewPropertiesXML, Map<String, String>> {

  /**
   * Constructor
   */
  public PropertiesToMapAdapter() {
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public Map<String, String> unmarshal(VLViewPropertiesXML v) throws Exception {
    Map<String, String> values = new HashMap<>();
    if (v != null) {
      if (v.getProperties() != null && !v.getProperties().isEmpty()) {
        List<VLViewPropertyXML> d = v.getProperties();
        for (VLViewPropertyXML p : d) {
          values.put(p.getName(), p.getValue());
        }
      }
    }

    return values;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public VLViewPropertiesXML marshal(Map<String, String> v) throws Exception {
    VLViewPropertiesXML propertiesXML = new VLViewPropertiesXML();
    for(String p: v.keySet()) {
      VLViewPropertyXML q = new VLViewPropertyXML();
      q.setName(p);
      q.setValue(v.get(p));
      propertiesXML.getProperties().add(q);
    }
    return propertiesXML;
  }
}
