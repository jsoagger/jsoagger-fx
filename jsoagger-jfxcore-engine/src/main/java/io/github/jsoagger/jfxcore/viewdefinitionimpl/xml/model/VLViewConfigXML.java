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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "view")
@XmlJavaTypeAdapter(value = NormalizedStringAdapter.class)
public class VLViewConfigXML implements Serializable {

  private static final long serialVersionUID = 1L;

  private static final String CONTENT = "Content";

  @XmlAttribute(name = "id")
  private String id;

  @XmlAttribute(name = "view")
  private String view;

  @XmlAttribute(name = "icon")
  private String icon;

  @XmlAttribute(name = "layoutManager")
  private String layoutManager;

  @XmlElement(name = "context-filter-group")
  private VLViewContextFilterGroupXML filtersGroup;

  @XmlElement(name = "view-filter")
  private List<VLViewFilterXML> viewFilters = null;

  @XmlElement(name = "component")
  private List<VLViewComponentXML> components = new ArrayList<>();

  @XmlElement(name = "menuGroup")
  private List<VLViewRootMenuGroupXML> menus = new ArrayList<>();

  @XmlElement(name = "menuRows")
  private VLViewRootMenuRowsXML menusRows;

  @XmlJavaTypeAdapter(PropertiesToMapAdapter.class)
  public Map<String, String> properties = new HashMap<>();


  /**
   * Get property as a string
   *
   * @param name
   * @return Optional
   */
  public String propertyValueOf(String key) {
    return properties.get(key) == null ? "" : properties.get(key);
  }


  /**
   * Returns the first {@link VLViewComponentXML} with given identifier in
   * this view wizardConfiguration.
   *
   * @param id
   * @return Optional
   */
  public Optional<VLViewComponentXML> getComponentById(String id) {
    if (getComponents() == null || getComponents().isEmpty()) {
      return Optional.empty();
    }

    for(VLViewComponentXML comp: getComponents()) {
      if(id.equalsIgnoreCase(comp.getId())) {
        return Optional.of(comp);
      }
    }

    return Optional.empty();
  }


  /**
   * Root component is the one with id = Content
   *
   * @return
   */
  public VLViewComponentXML getRootComponent() {
    if (components == null) {
      return null;
    }
    for (final VLViewComponentXML componentXML : components) {
      if (CONTENT.equals(componentXML.getId())) {
        return componentXML;
      }
    }

    return null;
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
   * @return the layoutManager
   */
  public String getLayoutManager() {
    return layoutManager;
  }


  /**
   * @param layoutManager
   *            the layoutManager to set
   */
  public void setLayoutManager(String layoutManager) {
    this.layoutManager = layoutManager;
  }


  /**
   * @return the filtersGroup
   */
  public VLViewContextFilterGroupXML getFiltersGroup() {
    return filtersGroup;
  }


  /**
   * @param filtersGroup
   *            the filtersGroup to set
   */
  public void setFiltersGroup(VLViewContextFilterGroupXML filtersGroup) {
    this.filtersGroup = filtersGroup;
  }


  /**
   * @return the components
   */
  public List<VLViewComponentXML> getComponents() {
    return components;
  }


  /**
   * @param components
   *            the components to set
   */
  public void setComponents(List<VLViewComponentXML> components) {
    this.components = components;
  }


  /**
   * @return the view
   */
  public String getView() {
    return view;
  }


  /**
   * @param view
   *            the view to set
   */
  public void setView(String view) {
    this.view = view;
  }


  /**
   * Get the menus
   *
   * @return the menus
   */
  public List<VLViewRootMenuGroupXML> getMenus() {
    return menus;
  }


  /**
   * Set the menus
   *
   * @param menus
   *            the menus to set
   */
  public void setMenus(List<VLViewRootMenuGroupXML> menus) {
    this.menus = menus;
  }


  /**
   * Get the icon
   *
   * @return the icon
   */
  public String getIcon() {
    return icon;
  }


  /**
   * Set the icon
   *
   * @param icon
   *            the icon to set
   */
  public void setIcon(String icon) {
    this.icon = icon;
  }


  /**
   * Getter of menusRows
   *
   * @return the menusRows
   */
  public VLViewRootMenuRowsXML getMenusRows() {
    return menusRows;
  }


  /**
   * Setter of menusRows
   *
   * @param menusRows
   *            the menusRows to set
   */
  public void setMenusRows(VLViewRootMenuRowsXML menusRows) {
    this.menusRows = menusRows;
  }


  public void addEmptyRootContent() {
    VLViewComponentXML componentXML = new VLViewComponentXML();
    componentXML.setId("Content");
    if (components == null) {
      components = new ArrayList<>();
    }

    components.add(componentXML);
  }


  /**
   * Getter of properties
   *
   * @return the properties
   */
  public Map<String, String> getProperties() {
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
   *
   */
  public void addComponentDefinition(VLViewComponentXML attributeConfig) {
    if (components == null) {
      components = new ArrayList<>();
    }

    components.add(attributeConfig);
  }


  public List<VLViewFilterXML> getViewFilters() {
    return viewFilters;
  }


  public void setViewFilters(List<VLViewFilterXML> viewFilters) {
    this.viewFilters = viewFilters;
  }


  @Override
  public String toString() {
    return "VLViewConfigXML [id=" + id + ", view=" + view + ", icon=" + icon + ", layoutManager=" + layoutManager + ", filtersGroup=" + filtersGroup + ", viewFilters=" + viewFilters + ", components=" + components
        + ", menus=" + menus + ", menusRows=" + menusRows + ", properties=" + properties + "]";
  }
}
