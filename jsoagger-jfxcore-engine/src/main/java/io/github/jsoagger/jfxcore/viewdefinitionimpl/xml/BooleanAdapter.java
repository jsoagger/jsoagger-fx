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

package io.github.jsoagger.jfxcore.viewdefinitionimpl.xml;



import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class BooleanAdapter extends XmlAdapter<String, Boolean> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Boolean unmarshal(String v) throws Exception {
    if (StringUtils.isBlank(v)) {
      return Boolean.FALSE;
    }

    return "1".equals(v) || "true".equalsIgnoreCase(v);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public String marshal(Boolean v) throws Exception {
    if (v == null) {
      return null;
    }
    return v.toString();
  }
}
