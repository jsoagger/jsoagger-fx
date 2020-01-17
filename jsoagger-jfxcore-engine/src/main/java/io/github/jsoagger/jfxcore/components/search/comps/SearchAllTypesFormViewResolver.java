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

package io.github.jsoagger.jfxcore.components.search.comps;



import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import io.github.jsoagger.jfxcore.api.IViewResolver;
import io.github.jsoagger.jfxcore.api.ResourceUtils;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;

/**
 * Return a search form view identifier according to a type and a context.
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class SearchAllTypesFormViewResolver implements IViewResolver {

  private String definitionFile;
  private Properties properties = new Properties();
  private boolean initialized = false;


  /**
   * Constructor
   */
  public SearchAllTypesFormViewResolver() {}


  /**
   * Initialization and load the properties.
   */
  public void init() {
    try (InputStream inStream = ResourceUtils.getStream(definitionFile)) {
      if(inStream == null) throw new RuntimeException("File not found : " + definitionFile);
      properties.load(inStream);
      initialized = true;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public String getViewName(VLViewComponentXML configuration, Map<String, Object> params) {
    if(!initialized) init();

    String action = (String) params.get("action");
    String viewContext = (String) params.get("viewContext");
    String type = (String) params.get("type");
    return properties.getProperty(viewContext + ";" + action + ";" + type);
  }


  public String getDefinitionFile() {
    return definitionFile;
  }


  public void setDefinitionFile(String definitionFile) {
    this.definitionFile = definitionFile;
  }
}
