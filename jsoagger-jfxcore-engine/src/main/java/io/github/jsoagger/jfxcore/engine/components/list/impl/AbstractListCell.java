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

package io.github.jsoagger.jfxcore.engine.components.list.impl;



import java.util.HashMap;
import java.util.Map;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.presenter.CellPresenterFactory;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.api.services.Services;

import javafx.scene.control.ListCell;

/**
 * Factory populating the graphic displayed on each cell of the list view.
 *
 * @author Ramilafananana Vonjisoa
 * @mailTo yvonjisoa@nexitia.com
 * @date 27 janv. 2018
 */
public abstract class AbstractListCell<T> extends ListCell<T> {

  protected CellPresenterFactory presenter;
  protected AbstractViewController controller;
  protected VLViewComponentXML configuration;

  /** theses are extra parameters declared in root factory */
  protected Map<String, Object> parameters = new HashMap<>();

  /**
   * Default Constructor
   */
  public AbstractListCell() {
    super();
  }


  /**
   * Loads the presenter.
   *
   * @param data
   */
  protected void loadPresenter(OperationData data) {
    String presenterId = (String) data.getAttributes().get("presenter");

    // process extra params
    for(String k: getConfiguration().getProperties().keySet()) {
      if (k.startsWith("extra_param_")) {
        parameters.put(StringUtils.substringAfter(k, "extra_param_"), getConfiguration().getProperties().get(k));
      }
    }

    if (StringUtils.isNotBlank(presenterId)) {
      presenter = (CellPresenterFactory) Services.getBean(presenterId);
      setPresenter(presenter);
      presenter.setParameters(parameters);
      presenter.setCell(this);
      presenter.setConfiguration(getConfiguration());
      presenter.setController(controller);
    }
  }


  public AbstractViewController getController() {
    return controller;
  }


  public void setController(AbstractViewController controller) {
    this.controller = controller;
  }


  public VLViewComponentXML getConfiguration() {
    return configuration;
  }


  public void setConfiguration(VLViewComponentXML configuration) {
    this.configuration = configuration;
  }


  /**
   * Getter of presenter
   *
   * @return the presenter
   */
  public CellPresenterFactory getPresenter() {
    return presenter;
  }


  /**
   * Setter of presenter
   *
   * @param presenter the presenter to set
   */
  public void setPresenter(CellPresenterFactory presenter) {
    this.presenter = presenter;
  }
}
