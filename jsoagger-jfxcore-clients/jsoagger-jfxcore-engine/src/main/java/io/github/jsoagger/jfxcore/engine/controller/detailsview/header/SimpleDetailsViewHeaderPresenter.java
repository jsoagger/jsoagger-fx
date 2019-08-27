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

package io.github.jsoagger.jfxcore.engine.controller.detailsview.header;



import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.ILocationProvider;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.presenter.MultiPresenterFactory;

/**
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 31 janv. 2018
 */
public class SimpleDetailsViewHeaderPresenter extends MultiPresenterFactory {

  protected ILocationProvider locationPresenter;


  /**
   * Default Constructor
   */
  public SimpleDetailsViewHeaderPresenter() {
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
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);
  }
}
