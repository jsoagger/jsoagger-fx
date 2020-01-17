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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "subview")
@XmlJavaTypeAdapter(value = NormalizedStringAdapter.class)
public class VLViewSubViewXML {

  @XmlAttribute(required = true, name = "id")
  private String id;

  @XmlAttribute(name = "filter")
  private String filter;

  @XmlAttribute(name = "root")
  private boolean root;

  @XmlAttribute(name = "handler")
  private String handler;

  @XmlAttribute(name = "parent")
  private String parent;


  /**
   * @return the id
   */
  public String getId() {
    return id;
  }


  /**
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }


  /**
   * @return the handler
   */
  public String getHandler() {
    return handler;
  }


  /**
   * @param handler the handler to set
   */
  public void setHandler(String handler) {
    this.handler = handler;
  }


  /**
   * @return the root
   */
  public boolean isRoot() {
    return root;
  }


  /**
   * @param root the root to set
   */
  public void setRoot(boolean root) {
    this.root = root;
  }


  /**
   * @param filter the filter to set
   */
  public void setFilter(String filter) {
    this.filter = filter;
  }


  /**
   * @return the filter
   */
  public String getFilter() {
    return filter;
  }


  /**
   * @return the parent
   */
  public String getParent() {
    return parent;
  }


  /**
   * @param parent the parent to set
   */
  public void setParent(String parent) {
    this.parent = parent;
  }
}
