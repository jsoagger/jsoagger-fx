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



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import io.github.jsoagger.jfxcore.viewdefinitionimpl.xml.StringUtils;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "menuGroup")
@XmlJavaTypeAdapter(value = NormalizedStringAdapter.class)
public class VLViewRootMenuGroupXML {

  @XmlAttribute(name = "id") private String						id;
  @XmlElement(name = "properties") private VLViewPropertiesXML	properties;


  public String getPropertyValue(String name) {
    if (properties == null) { return null; }
    return properties.getPropertyValueByName(name);
  }


  public int getIntPropertyValue(String name) {
    int result = Integer.MIN_VALUE;
    if (properties != null && properties.getProperties() != null) {
      for (final VLViewPropertyXML property : properties.getProperties()) {
        if (property.getName().equals(name)) {
          try {
            result = Integer.parseInt(property.getValue());
          }
          catch (final Exception e) {

          }
        }
      }
    }

    return result;
  }


  public boolean getBooleanProperty(String name) {
    boolean result = true;
    if (properties != null && properties.getProperties() != null) {
      for (final VLViewPropertyXML property : properties.getProperties()) {
        if (property.getName().equals(name)) {
          try {
            result = property.getValue() == null
                && StringUtils.isBlank(property.getValue())
                && property.getValue().equalsIgnoreCase("true");
          }
          catch (final Exception e) {

          }
        }
      }
    }

    return result;
  }


  public boolean getBooleanProperty(String name, boolean defautVal) {
    boolean result = defautVal;
    if (properties != null && properties.getProperties() != null) {
      for (final VLViewPropertyXML property : properties.getProperties()) {
        if (property.getName().equals(name)) {

          try {
            result = property.getValue() != null
                && StringUtils.isNotBlank(property.getValue())
                && property.getValue().equalsIgnoreCase("true");
          }
          catch (final Exception e) {
          }
        }
      }
    }

    return result;
  }


  public String getId() {
    return id;
  }


  public void setId(String id) {
    this.id = id;
  }


  public VLViewPropertiesXML getProperties() {
    return properties;
  }


  public void setProperties(VLViewPropertiesXML properties) {
    this.properties = properties;
  }


  @Override public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("VLViewRootMenuGroupXML [");
    if (id != null) {
      builder.append("id=");
      builder.append(id);
      builder.append(", ");
    }
    if (properties != null) {
      builder.append("properties=");
      builder.append(properties);
    }
    builder.append("]");
    return builder.toString();
  }
}
