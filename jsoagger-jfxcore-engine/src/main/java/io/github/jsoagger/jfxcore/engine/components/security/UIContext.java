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

package io.github.jsoagger.jfxcore.engine.components.security;



import java.util.HashMap;
import java.util.Locale;

import io.github.jsoagger.jfxcore.api.ICriteriaContext;
import io.github.jsoagger.jfxcore.api.security.IRootContext;
import io.github.jsoagger.jfxcore.api.security.IUserContext;
import io.github.jsoagger.jfxcore.api.security.IViewContext;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewConfigXML;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class UIContext implements IViewContext {

  protected ICriteriaContext criterias = new CriteriaContext();
  protected VLViewConfigXML viewConfig;
  protected HashMap<String, String> parameters = new HashMap<>();
  protected IUserContext userContext;
  protected IRootContext rootContext;


  /**
   * New view context
   *
   */
  public UIContext() {}

  /**
   * {@inheritDoc}
   */
  @Override
  public void addCriterias(HashMap<String, String> filters) {
    if (filters != null) {
      for(String e: filters.keySet()) {
        criterias.setFilter(e, filters.get(e));
      }
    }
  }


  public boolean evaluate(String filterName) {
    return "true".equals(criterias.filterValue(filterName));
  }


  /**
   * New view context
   *
   * @param viewConfig
   */
  public UIContext(VLViewConfigXML viewConfig) {
    this.viewConfig = viewConfig;
  }


  /**
   * @return
   */
  @Override
  public Locale getUserLocale() {
    return userContext.getUserLocale();
  }


  /**
   *
   * @param viewConfig
   */
  public void controllerCfg(VLViewConfigXML viewConfig) {
    this.viewConfig = viewConfig;
  }


  /**
   *
   * @return
   */
  public VLViewConfigXML controllerCfg() {
    return viewConfig;
  }


  /**
   * @return the criterias
   */
  @Override
  public ICriteriaContext getCriterias() {
    return criterias;
  }


  /**
   * @param criterias the criterias to set
   */
  @Override
  public void setCriterias(ICriteriaContext criterias) {
    this.criterias = criterias;
  }


  /**
   * @return the viewConfig
   */
  @Override
  public VLViewConfigXML getViewConfig() {
    return viewConfig;
  }


  /**
   * @param viewConfig the viewConfig to set
   */
  @Override
  public void setViewConfig(VLViewConfigXML viewConfig) {
    this.viewConfig = viewConfig;
  }


  /**
   * Get the userContext
   *
   * @return the userContext
   */
  public IUserContext getUserContext() {
    return userContext;
  }


  /**
   * @param userContext the userContext to set
   */
  public void setUserContext(IUserContext userContext) {
    this.userContext = userContext;
  }


  /**
   * Getter of parameters
   *
   * @return the parameters
   */
  public HashMap<String, String> getParameters() {
    return parameters;
  }


  /**
   * Setter of parameters
   *
   * @param parameters the parameters to set
   */
  public void setParameters(HashMap<String, String> parameters) {
    this.parameters = parameters;
  }


  /**
   * Getter of rootContext
   *
   * @return the rootContext
   */
  @Override
  public IRootContext getRootContext() {
    return rootContext;
  }


  /**
   * Setter of rootContext
   *
   * @param rootContext the rootContext to set
   */
  @Override
  public void setRootContext(IRootContext rootContext) {
    this.rootContext = rootContext;
  }


  /**
   * Getter of viewConfigXML
   *
   * @return the viewConfigXML
   */
  public VLViewConfigXML getViewConfigXML() {
    return viewConfig;
  }


  /**
   * Setter of viewConfigXML
   *
   * @param viewConfigXML the viewConfigXML to set
   */
  public void setViewConfigXML(VLViewConfigXML viewConfigXML) {
    this.viewConfig = viewConfigXML;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void processFrom(VLViewConfigXML configXML, IRootContext context) {
    this.viewConfig = configXML;
  }
}
