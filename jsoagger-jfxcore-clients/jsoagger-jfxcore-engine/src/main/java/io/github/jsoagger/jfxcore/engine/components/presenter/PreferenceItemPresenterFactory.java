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

package io.github.jsoagger.jfxcore.engine.components.presenter;



import java.util.List;

import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.listform.IListFormDataLoader;
import io.github.jsoagger.jfxcore.engine.components.listform.IListFormValue;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public abstract class PreferenceItemPresenterFactory extends CellPresenterFactory {

  protected String key;
  protected String listValuekey;
  protected String singleSelectedValueKey;
  protected Label label = new Label();
  protected IListFormDataLoader dataLoader;
  protected List<IListFormValue> currentValues;

  /**
   * Constructor
   */
  public PreferenceItemPresenterFactory() {
    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    super.buildFrom(controller, configuration);

    String title = (String) getForData().getAttributes().get("title");
    if (StringUtils.isNotBlank(title)) {
      label.setText(controller.getLocalised(title));
      label.setStyle("-fx-font-family:'Roboto Light';-fx-font-size:12px");
    }

    this.listValuekey = (String) getForData().getAttributes().get("listValueKey");
    this.key = (String) getForData().getAttributes().get("key");
    this.singleSelectedValueKey = (String) getForData().getAttributes().get("key");

    String dtl = getForData().getAttributes().get("dataLoader") == null ? "ByKeySingleListFormDataLoader" : (String) getForData().getAttributes().get("dataLoader");
    dataLoader = (IListFormDataLoader) Services.getBean(dtl);
    dataLoader.setController(controller);

    if(StringUtils.isNotBlank(getKey())) {
      loadCurrentValue();
    }
  }

  protected void loadCurrentValue() {
    if (dataLoader != null) {
      if (dataLoader.getConfiguration() == null) {
        dataLoader.setConfiguration(configuration);
      }

      dataLoader.setPresenter(this);
      currentValues = dataLoader.getCurrentValue();
    }
  }


  public Node getCurrentValueDisplay() {
    if (currentValues == null && StringUtils.isNotBlank(getKey())) {
      loadCurrentValue();
    }

    return dataLoader.getDisplayNode(currentValues);
  }

  public String getCurrentDisplayText() {
    if (currentValues == null && StringUtils.isNotBlank(getKey())) {
      loadCurrentValue();
    }

    return dataLoader.getDisplayText(currentValues);
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getListValuekey() {
    return listValuekey;
  }

  public void setListValuekey(String listValuekey) {
    this.listValuekey = listValuekey;
  }

  /**
   * @return the dataLoader
   */
  public IListFormDataLoader getDataLoader() {
    return dataLoader;
  }

  /**
   * @param dataLoader the dataLoader to set
   */
  public void setDataLoader(IListFormDataLoader dataLoader) {
    this.dataLoader = dataLoader;
  }
}
