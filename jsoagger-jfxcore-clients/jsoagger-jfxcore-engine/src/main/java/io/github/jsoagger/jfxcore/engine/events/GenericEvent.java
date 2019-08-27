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

package io.github.jsoagger.jfxcore.engine.events;



import java.util.Properties;

import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public abstract class GenericEvent {

  protected Object model;
  protected Node source;
  protected AbstractViewController sourceController;
  protected Properties properties = new Properties();
  protected SimpleBooleanProperty wasConsumed = new SimpleBooleanProperty(false);


  /**
   * Constructor
   */
  public GenericEvent() {}


  /**
   * Returns true is {@link CoreEvent} is equivalent to this filter
   */
  public boolean isA(CoreEvent eq) {
    return getFilter() != null && getFilter().getCanonicalName().equals(eq.filter().getCanonicalName());
  }


  public Object getModel() {
    return model;
  }


  public void setModel(Object model) {
    this.model = model;
  }


  /**
   * Get the source
   *
   * @return the source
   */
  public Node getSource() {
    return source;
  }


  /**
   * Set the source
   *
   * @param source the source to set
   */
  public void setSource(Node source) {
    this.source = source;
  }


  public abstract Class getFilter();


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
   * @param value
   * @return
   * @see java.util.Properties#setProperty(java.lang.String, java.lang.String)
   */
  public Object setProperty(String key, String value) {
    return properties.setProperty(key, value);
  }


  /**
   * @return
   * @see java.util.Hashtable#isEmpty()
   */
  public boolean isEmpty() {
    return properties.isEmpty();
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
    return properties.put(key, value);
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


  public final SimpleBooleanProperty wasConsumedProperty() {
    return wasConsumed;
  }


  public final boolean isWasConsumed() {
    return this.wasConsumedProperty().get();
  }


  public final void setWasConsumed(final boolean wasConsumed) {
    this.wasConsumedProperty().set(wasConsumed);
  }


  /**
   * Getter of sourceController
   *
   * @return the sourceController
   */
  public AbstractViewController getSourceController() {
    return sourceController;
  }


  /**
   * Setter of sourceController
   *
   * @param sourceController the sourceController to set
   */
  public void setSourceController(AbstractViewController sourceController) {
    this.sourceController = sourceController;
  }
}
