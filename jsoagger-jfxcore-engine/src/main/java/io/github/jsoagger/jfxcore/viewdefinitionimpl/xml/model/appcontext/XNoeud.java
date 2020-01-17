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

package io.github.jsoagger.jfxcore.viewdefinitionimpl.xml.model.appcontext;



import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.jfxcore.viewdefinitionimpl.xml.model.VLViewFilterXML;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class XNoeud {

  private boolean root;
  private boolean feuille;
  private boolean value;
  private final VLViewFilterXML filter;
  private final String operateur;
  private final List<XNoeud> fils = new ArrayList<>();

  public XNoeud(VLViewFilterXML filter, String operateur) {
    this.filter = filter;
    this.operateur = operateur;
  }

  public List<XNoeud> fils() {
    return fils;
  }

  /**
   * @return the feuille
   */
  public boolean isFeuille() {
    return feuille;
  }

  /**
   * @return the root
   */
  public boolean isRoot() {
    return root;
  }

  /**
   * @return the filter
   */
  public VLViewFilterXML filter() {
    return filter;
  }

  /**
   * @return the operateur
   */
  public String operateur() {
    return operateur;
  }

  /**
   * @param value the value to set
   */
  public void value(boolean value) {
    this.value = value;
  }

  public boolean value() {
    return value;
  }
}
