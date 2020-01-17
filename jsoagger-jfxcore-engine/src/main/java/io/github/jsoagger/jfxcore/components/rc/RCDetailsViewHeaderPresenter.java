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

package io.github.jsoagger.jfxcore.components.rc;



import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.ILocationProvider;
import io.github.jsoagger.jfxcore.api.presenter.IModelAttributePresenter;
import io.github.jsoagger.jfxcore.api.presenter.IModelTypePresenter;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.presenter.MultiPresenterFactory;
import io.github.jsoagger.jfxcore.engine.components.presenter.impl.ModelAttributePresenter;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 31 janv. 2018
 */
public class RCDetailsViewHeaderPresenter extends MultiPresenterFactory {

  protected ILocationProvider locationPresenter;
  protected IModelTypePresenter typePresenter;
  protected IModelAttributePresenter lifecycleStatusPresenter;
  protected IModelAttributePresenter workStatusPresenter;
  protected IModelAttributePresenter versionPresenter;


  /**
   * Default Constructor
   */
  public RCDetailsViewHeaderPresenter() {
    super();
  }


  /**
   * Getter of locationPresenter
   *
   * @return the locationPresenter
   */
  public ILocationProvider getLocationPresenter() {
    return locationPresenter;
  }


  /**
   * Setter of locationPresenter
   *
   * @param locationPresenter the locationPresenter to set
   */
  public void setLocationPresenter(ILocationProvider locationPresenter) {
    this.locationPresenter = locationPresenter;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {}


  /**
   * Getter of typePresenter
   *
   * @return the typePresenter
   */
  public IModelTypePresenter getTypePresenter() {
    return typePresenter;
  }


  /**
   * Setter of typePresenter
   *
   * @param typePresenter the typePresenter to set
   */
  public void setTypePresenter(IModelTypePresenter typePresenter) {
    this.typePresenter = typePresenter;
  }


  public IModelAttributePresenter getWorkStatusPresenter() {
    return workStatusPresenter;
  }


  public void setWorkStatusPresenter(ModelAttributePresenter workStatusPresenter) {
    this.workStatusPresenter = workStatusPresenter;
  }


  public IModelAttributePresenter getVersionPresenter() {
    return versionPresenter;
  }


  public void setVersionPresenter(ModelAttributePresenter versionPresenter) {
    this.versionPresenter = versionPresenter;
  }


  public IModelAttributePresenter getLifecycleStatusPresenter() {
    return lifecycleStatusPresenter;
  }


  public void setLifecycleStatusPresenter(ModelAttributePresenter lifecycleStatusPresenter) {
    this.lifecycleStatusPresenter = lifecycleStatusPresenter;
  }
}
