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
@XmlRootElement(name = "column")
@XmlJavaTypeAdapter(value = NormalizedStringAdapter.class)
public class VLViewTableSettingColumnXML {

  @XmlAttribute(name = "id")
  private String id;

  @XmlAttribute(name = "width")
  private Double width;

  @XmlAttribute(name = "displayed")
  private boolean displayed;

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
   * @return the width
   */
  public Double getWidth() {
    return width;
  }

  /**
   * @param width the width to set
   */
  public void setWidth(Double width) {
    this.width = width;
  }

  /**
   * @return the displayed
   */
  public boolean isDisplayed() {
    return displayed;
  }

  /**
   * @param displayed the displayed to set
   */
  public void setDisplayed(boolean displayed) {
    this.displayed = displayed;
  }
}
