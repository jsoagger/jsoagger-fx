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

package io.github.jsoagger.jfxcore.engine.accessrule;



import java.util.Map;
import java.util.Properties;

import io.github.jsoagger.jfxcore.api.IAccessRuleResolver;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 2 mars 2018
 */
public abstract class AbstractRuleResolver implements IAccessRuleResolver {

  protected Properties properties = new Properties();


  /**
   * Getter of properties
   *
   * @return the properties
   */
  public Properties getProperties() {
    return properties;
  }


  /**
   * Setter of properties
   *
   * @param properties the properties to set
   */
  public void setProperties(Properties properties) {
    this.properties = properties;
  }


  /**
   * @param key
   * @return
   * @see java.util.Hashtable#get(java.lang.Object)
   */
  public Object get(Object key) {
    return properties.get(key);
  }


  /**
   * @param key
   * @param value
   * @return
   * @see java.util.Hashtable#put(java.lang.Object, java.lang.Object)
   */
  public Object put(Object key, Object value) {
    return properties.put(key, value);
  }


  /**
   * @param key
   * @return
   * @see java.util.Hashtable#remove(java.lang.Object)
   */
  public Object remove(Object key) {
    return properties.remove(key);
  }


  /**
   * @param t
   * @see java.util.Hashtable#putAll(java.util.Map)
   */
  public void putAll(Map<? extends Object, ? extends Object> t) {
    properties.putAll(t);
  }


  /**
   * @param key
   * @param defaultValue
   * @return
   * @see java.util.Hashtable#getOrDefault(java.lang.Object, java.lang.Object)
   */
  public Object getOrDefault(Object key, Object defaultValue) {
    return properties.get(key) == null ? defaultValue : properties.get(key);
  }


  /**
   * @param key
   * @param value
   * @return
   * @see java.util.Hashtable#putIfAbsent(java.lang.Object, java.lang.Object)
   */
  public Object putIfAbsent(Object key, Object value) {
    if(!properties.containsKey(key)) {
      properties.put(key, value);
    }
    return null;
  }


  /**
   * @param key
   * @param value
   * @return
   * @see java.util.Hashtable#remove(java.lang.Object, java.lang.Object)
   */
  public boolean remove(Object key, Object value) {
    return properties.remove(key, value);
  }


  /**
   * @param key
   * @return
   * @see java.util.Properties#getProperty(java.lang.String)
   */
  public String getProperty(String key) {
    return properties.getProperty(key);
  }


  /**
   * @param key
   * @param defaultValue
   * @return
   * @see java.util.Properties#getProperty(java.lang.String, java.lang.String)
   */
  public String getProperty(String key, String defaultValue) {
    return properties.getProperty(key, defaultValue);
  }
}
