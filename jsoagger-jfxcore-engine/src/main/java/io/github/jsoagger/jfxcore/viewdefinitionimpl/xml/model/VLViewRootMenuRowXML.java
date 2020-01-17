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



import java.util.List;

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
@XmlRootElement(name = "menuRow")
@XmlJavaTypeAdapter(value = NormalizedStringAdapter.class)
public class VLViewRootMenuRowXML {

  @XmlAttribute(name = "id") private String							id;
  @XmlElement(name = "properties") private VLViewPropertiesXML		properties;
  @XmlElement(name = "accessRules") private VLAccessRulesXML			accessRules;
  @XmlElement(name = "menuRow") private List<VLViewRootMenuRowXML>	subMenus;


  /**
   * Constructor
   */
  public VLViewRootMenuRowXML() {
  }


  /**
   * Getter of id
   *
   * @return the id
   */
  public String getId() {
    return id;
  }


  /**
   * Setter of id
   *
   * @param id
   *            the id to set
   */
  public void setId(String id) {
    this.id = id;
  }


  /**
   * Getter of properties
   *
   * @return the properties
   */
  public VLViewPropertiesXML getProperties() {
    return properties;
  }


  /**
   * Setter of properties
   *
   * @param properties
   *            the properties to set
   */
  public void setProperties(VLViewPropertiesXML properties) {
    this.properties = properties;
  }


  /**
   * Getter of accessRules
   *
   * @return the accessRules
   */
  public VLAccessRulesXML getAccessRules() {
    return accessRules;
  }


  /**
   * Setter of accessRules
   *
   * @param accessRules
   *            the accessRules to set
   */
  public void setAccessRules(VLAccessRulesXML accessRules) {
    this.accessRules = accessRules;
  }


  /**
   * Getter of subMenus
   *
   * @return the subMenus
   */
  public List<VLViewRootMenuRowXML> getSubMenus() {
    return subMenus;
  }


  /**
   * Setter of subMenus
   *
   * @param subMenus
   *            the subMenus to set
   */
  public void setSubMenus(List<VLViewRootMenuRowXML> subMenus) {
    this.subMenus = subMenus;
  }


  /**
   * @return
   */
  public boolean addSeparatorAfter() {
    String addString = properties.getPropertyValueByName("separator-after");
    return StringUtils.isNotBlank(addString) && Boolean.valueOf(addString);
  }


  /**
   * @return
   */
  public String getRuleResolverName() {
    if (accessRules != null) { return accessRules.getRuleResolverName(); }
    return null;
  }


  /**
   */
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


  /**
   * @param name
   * @return
   */
  public VLViewPropertyXML getPropertyByName(String name) {
    return properties.getPropertyByName(name);
  }


  /**
   * @param name
   * @return
   */
  public String getPropertyValueByName(String name) {
    return properties.getPropertyValueByName(name);
  }


  /**
   * @return
   */
  public String getIcon() {
    return getPropertyValueByName("icon");
  }


  /**
   * @return
   */
  public String getIconStyleClass() {
    return getPropertyValueByName("iconStyleClass");
  }


  /**
   * @return
   */
  public String getToolTip() {
    return getPropertyValueByName("tooltip");
  }


  /**
   * @return
   */
  public String getLabel() {
    return getPropertyValueByName("label");
  }


  /**
   * @return
   */
  public String getRootStructure() {
    return getPropertyValueByName("setRootStructure");
  }


  /**
   * @return
   */
  public String getLink() {
    return getPropertyValueByName("link");
  }


  /**
   * @return
   */
  public String updateRSContentTo() {
    return getPropertyValueByName("updateRSContentTo");
  }


  /**
   * @return
   */
  public String getLoadChildView() {
    return getPropertyValueByName("loadChildView");
  }


  /**
   * @return
   */
  public String getSetRootviewContent() {
    return getPropertyValueByName("setRootviewContent");
  }


  /**
   * @return
   */
  public String getLoadRootStructure() {
    return getPropertyValueByName("loadRootStructure");
  }


  /**
   * @return
   */
  public String getAction() {
    return getPropertyValueByName("action");
  }


  public String getShowWizardAction() {
    return getPropertyValueByName("showWizardAction");
  }

  /**
   * @return
   */
  public boolean hasSubRows() {
    return subMenus != null && !subMenus.isEmpty();
  }


  /**
   * @{inheritedDoc}
   */
  @Override public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("VLViewRootMenuRowXML [");
    if (id != null) {
      builder.append("id=");
      builder.append(id);
      builder.append(", ");
    }
    if (properties != null) {
      builder.append("properties=");
      builder.append(properties);
      builder.append(", ");
    }
    if (accessRules != null) {
      builder.append("accessRules=");
      builder.append(accessRules);
      builder.append(", ");
    }
    if (subMenus != null) {
      builder.append("subMenus=");
      builder.append(subMenus);
    }
    builder.append("]");
    return builder.toString();
  }

  public static class Builder {

    private String				id;
    private VLViewPropertiesXML	properties;


    public Builder id(String id) {
      this.id = id;
      return this;
    }


    public Builder properties(VLViewPropertiesXML properties) {
      this.properties = properties;
      return this;
    }


    public VLViewRootMenuRowXML build() {
      return new VLViewRootMenuRowXML(this);
    }
  }


  private VLViewRootMenuRowXML(Builder builder) {
    this.id = builder.id;
    this.properties = builder.properties;
  }
}
