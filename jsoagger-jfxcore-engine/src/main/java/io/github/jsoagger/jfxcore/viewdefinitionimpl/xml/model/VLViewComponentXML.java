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


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import io.github.jsoagger.jfxcore.viewdefinitionimpl.xml.StringUtils;
import io.github.jsoagger.jfxcore.viewdefinitionimpl.xml.XMLConstants;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "component")
public class VLViewComponentXML implements Cloneable, Serializable {

  private static final long	serialVersionUID	= 8225109214272813121L;
  private static final String	PATH_SEPARATOR		= "/";

  @XmlAttribute
  private String						id;
  @XmlAttribute(name = "ref")
  private String						reference;
  @XmlAttribute(name = "separator-after")
  private String						separatorAfter;
  @XmlAttribute(name = "controller")
  private String						controller;
  @XmlAttribute(name = "processor")
  private String						processor;
  @XmlAttribute(name = "model")
  private String						model;
  @XmlElement(name = "component")
  private List<VLViewComponentXML>	subcomponents;
  @XmlAttribute(name = "visible-if")
  private String						visibleIf;
  @XmlAttribute(name = "rootview")
  private String						rootView;
  @XmlAttribute(name = "layoutProcessor")
  private String						layoutProcessor;
  @XmlAttribute(name = "criteria")
  private String						criteria;
  @XmlAttribute(name = "responsiveOrder")
  private String						responsiveOrder;
  @XmlAttribute(name = "masterColumn")
  private String						masterColumn;
  @XmlAttribute(name = "defaultButton")
  private String						defaultButton;

  @XmlElement(name = "accessRules")
  private VLAccessRulesXML accessRules;

  @XmlJavaTypeAdapter(PropertiesToMapAdapter.class)
  public Map<String, String> properties = new HashMap<>();


  public boolean isFiltered() {
    return StringUtils.isNotBlank(criteria);
  }


  public String getRootView() {
    return rootView;
  }


  public void setRootView(String rootView) {
    this.rootView = rootView;
  }


  public String getSeparatorAfter() {
    return separatorAfter;
  }

  public void addProperty(String name, String value) {
    getProperties().put(name, value);
  }



  /**
   * Get first subcomponent given identifier.
   *
   * @param id
   * @return
   */
  public Optional<VLViewComponentXML> getComponentById(String id) {
    if (hasSubComponent()) {
      for (final VLViewComponentXML comp : getSubcomponents()) {
        if (id.equalsIgnoreCase(comp.getId())) {
          return Optional.of(comp);
        }
      }
    }
    return Optional.empty();
  }

  /**
   * Get first subcomponent given identifier.
   *
   * @param id
   * @return
   */
  public List<VLViewComponentXML> getComponentsById(String id) {
    List<VLViewComponentXML> comps = new ArrayList<>();
    if (hasSubComponent()) {
      for (final VLViewComponentXML comp : getSubcomponents()) {
        if (id.equalsIgnoreCase(comp.getId())) {
          comps.add(comp);
        }
      }
    }
    return comps;
  }


  /**
   * @param id
   * @return VLViewComponentXML
   */
  public VLViewComponentXML getNullableComponentById(String id) {
    return getComponentById(id).orElse(null);
  }


  /**
   * Get property as a boolean. In this case empty value is considered a true.
   *
   * @param name
   * @return Optional
   */
  public Optional<Boolean> booleanPropertyValueOf(String name) {

    if (properties.get(name) == null) {
      return Optional.of(false);
    }

    Boolean val = Boolean.valueOf(properties.get(name));
    return Optional.of(val);
  }


  /**
   * Get property as a string
   *
   * @param name
   * @return Optional
   */
  public Optional<String> propertyValueOf(String name) {
    return propertyValueOf(name, "");
  }


  /**
   * Get property as a string
   *
   * @param name
   * @return Optional
   */
  public Optional<String> propertyValueOf(String name, String defaultval) {
    String val = properties.get(name);
    if (StringUtils.isEmpty(val)) {
      return Optional.of(defaultval);
    }
    return Optional.of(val);
  }


  /**
   * Get property as a string
   *
   * @param name
   * @return Optional
   */
  public OptionalInt intPropertyValueOf(String name) {
    if (properties.get(name) != null && StringUtils.isNotBlank(properties.get(name))) {
      Integer val = Integer.valueOf(properties.get(name));
      return OptionalInt.of(val);
    }
    return OptionalInt.empty();
  }


  /**
   * @param maxLength
   * @return
   */
  public int getIntPropertyValue(String name) {
    try {
      Integer val = Integer.valueOf(properties.get(name));
      return val;
    }
    catch (NumberFormatException e) {
      return Integer.MIN_VALUE;
    }
  }


  /**
   * No property, empty and true means true
   *
   * @param name
   * @return
   */
  public boolean getBooleanProperty(String name) {
    Boolean val = Boolean.valueOf(properties.get(name));
    return val;
  }


  /**
   * No property, empty and true means true
   *
   * @param name
   * @return
   */
  public boolean getBooleanProperty(String name, Boolean defaultVal) {
    if (StringUtils.isEmpty(properties.get(name))) {
      return defaultVal;
    }
    Boolean val = Boolean.valueOf(properties.get(name));
    return val;
  }


  /**
   * Get property as a string
   *
   * @param name
   * @return
   */
  public String getPropertyValue(String name) {
    return StringUtils.isEmpty(properties.get(name)) ? "" : properties.get(name);
  }


  /**
   * Get property as a string
   *
   * @param name
   * @return
   */
  public String getPropertyValue(String name, String defaultVal) {
    return StringUtils.isEmpty(properties.get(name)) ? defaultVal : properties.get(name);
  }


  /**
   * @return the id
   */
  public String getId() {
    return id;
  }


  /**
   * @param id
   *            the id to set
   */
  public void setId(String id) {
    this.id = id;
  }


  /**
   * @return the subcomponents
   */
  public List<VLViewComponentXML> getSubcomponents() {
    if (subcomponents == null) {
      subcomponents = new ArrayList<>();
    }
    return subcomponents;
  }


  /**
   * @param subcomponents
   *            the subcomponents to set
   */
  public void setSubcomponents(List<VLViewComponentXML> subcomponents) {
    this.subcomponents = subcomponents;
  }


  /**
   * @return the reference
   */
  public String getReference() {
    return reference;
  }


  /**
   * @param reference
   *            the reference to set
   */
  public void setReference(String reference) {
    this.reference = reference;
  }


  /**
   * @return the controller
   */
  public String getController() {
    return controller;
  }


  /**
   * @param controller
   *            the controller to set
   */
  public void setController(String controller) {
    this.controller = controller;
  }


  /**
   * @return the processor
   */
  public String getProcessor() {
    return processor;
  }


  /**
   * @param processor
   *            the processor to set
   */
  public void setProcessor(String processor) {
    this.processor = processor;
  }


  /**
   * @return the separatorAfter
   */
  public Boolean isSeparatorAfter() {
    return separatorAfter != null && ("1".equals(separatorAfter) || "true".equalsIgnoreCase(separatorAfter));
  }


  /**
   * @param separatorAfter
   *            the separatorAfter to set
   */
  public void setSeparatorAfter(String separatorAfter) {
    this.separatorAfter = separatorAfter;
  }


  /**
   *
   * @return
   */
  public boolean hasSubComponent() {
    return subcomponents != null && !subcomponents.isEmpty();
  }


  /**
   * Get the visibleIf
   *
   * @return the visibleIf
   */
  public String getVisibleIf() {
    return visibleIf;
  }


  /**
   *
   * @param visibleIf
   *            the visibleIf to set
   */
  public void setVisibleIf(String visibleIf) {
    this.visibleIf = visibleIf;
  }


  /**
   * Read property mandatory from config of attribute
   *
   * @param attrConfig
   * @return
   */
  public boolean isAttributeMandatory() {
    final VLViewComponentXML displayConfig = getComponentById("ValidationConfig").orElse(null);
    if (displayConfig != null) {
      return displayConfig.getBooleanProperty(XMLConstants.MANDATORY, false);
    }

    return false;
  }


  /**
   * Get the criteria
   *
   * @return the criteria
   */
  public String getCriteria() {
    return criteria;
  }


  /**
   * Set the criteria
   *
   * @param criteria
   *            the criteria to set
   */
  public void setCriteria(String criteria) {
    this.criteria = criteria;
  }


  /**
   * Getter of model
   *
   * @return the model
   */
  public String getModel() {
    return model;
  }


  /**
   * Setter of model
   *
   * @param model
   *            the model to set
   */
  public void setModel(String model) {
    this.model = model;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("VLViewComponentXML [");
    if (id != null) {
      builder.append("id=");
      builder.append(id);
      builder.append(", ");
    }
    if (reference != null) {
      builder.append("reference=");
      builder.append(reference);
      builder.append(", ");
    }
    if (separatorAfter != null) {
      builder.append("separatorAfter=");
      builder.append(separatorAfter);
      builder.append(", ");
    }
    if (controller != null) {
      builder.append("controller=");
      builder.append(controller);
      builder.append(", ");
    }
    if (processor != null) {
      builder.append("processor=");
      builder.append(processor);
      builder.append(", ");
    }
    if (model != null) {
      builder.append("model=");
      builder.append(model);
      builder.append(", ");
    }
    if (properties != null) {
      builder.append("properties=");
      builder.append(properties);
      builder.append(", ");
    }
    if (subcomponents != null) {
      builder.append("subcomponents=");
      builder.append(subcomponents);
      builder.append(", ");
    }
    if (visibleIf != null) {
      builder.append("visibleIf=");
      builder.append(visibleIf);
      builder.append(", ");
    }
    if (rootView != null) {
      builder.append("rootView=");
      builder.append(rootView);
      builder.append(", ");
    }
    if (layoutProcessor != null) {
      builder.append("layoutProcessor=");
      builder.append(layoutProcessor);
      builder.append(", ");
    }
    if (criteria != null) {
      builder.append("criteria=");
      builder.append(criteria);
    }
    builder.append("]");
    return builder.toString();
  }


  /**
   * Getter of properties
   *
   * @return the properties
   */
  public Map<String, String> getProperties() {
    if (properties == null) {
      properties = new HashMap<>();
    }
    return properties;
  }


  /**
   * Setter of properties
   *
   * @param properties
   *            the properties to set
   */
  public void setProperties(Map<String, String> properties) {
    this.properties = properties;
  }


  /**
   * Getter of responsiveOrder
   *
   * @return the responsiveOrder
   */
  public String getResponsiveOrder() {
    return responsiveOrder;
  }


  /**
   * Setter of responsiveOrder
   *
   * @param responsiveOrder
   *            the responsiveOrder to set
   */
  public void setResponsiveOrder(String responsiveOrder) {
    this.responsiveOrder = responsiveOrder;
  }


  /**
   * Getter of masterColumn
   *
   * @return the masterColumn
   */
  public String getMasterColumn() {
    return masterColumn;
  }


  /**
   * Setter of masterColumn
   *
   * @param masterColumn
   *            the masterColumn to set
   */
  public void setMasterColumn(String masterColumn) {
    this.masterColumn = masterColumn;
  }


  /**
   *
   */
  public void addSubconfg(VLViewComponentXML config) {
    if (getSubcomponents() == null) {
      subcomponents = new ArrayList<>();
    }
    subcomponents.add(config);
  }


  /**
   * Getter of defaultButton
   *
   * @return the defaultButton
   */
  public String getDefaultButton() {
    return defaultButton;
  }


  /**
   * Setter of defaultButton
   *
   * @param defaultButton
   *            the defaultButton to set
   */
  public void setDefaultButton(String defaultButton) {
    this.defaultButton = defaultButton;
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
   * {@inheritDoc}
   */
  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (id == null ? 0 : id.hashCode());
    return result;
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    VLViewComponentXML other = (VLViewComponentXML) obj;
    if (id == null) {
      if (other.id != null) return false;
    }
    else if (!id.equals(other.id)) return false;
    return true;
  }

}
