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

package io.github.jsoagger.jfxcore.engine.components.listform.dataloader;




import java.util.Arrays;
import java.util.List;

import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.listform.IListFormDataLoader;
import io.github.jsoagger.jfxcore.engine.components.listform.IListFormValue;
import io.github.jsoagger.jfxcore.engine.components.listform.ListFormValue;
import io.github.jsoagger.jfxcore.engine.components.presenter.PreferenceItemPresenterFactory;
import io.github.jsoagger.jfxcore.engine.model.EnumeratedValueModel;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ListFormDataLoader implements IListFormDataLoader {

  private VLViewComponentXML configuration;
  private PreferenceItemPresenterFactory presenter;

  /**
   * Constructor
   */
  public ListFormDataLoader() {
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public List<IListFormValue> getCurrentValue() {
    final ListFormValue preferenceValue = new ListFormValue("V0.1");
    preferenceValue.setDisplayedValue("V0.1");
    return Arrays.asList(preferenceValue);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public List<EnumeratedValueModel> getSelectableValues() {
    return null;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Node getDisplayNode(List<IListFormValue> values) {
    final Label valueLabel = new Label();
    valueLabel.setWrapText(true);
    valueLabel.setOpacity(0.44);

    if (values != null && values.isEmpty()) {
      valueLabel.setText(values.get(0).getDisplayedValue());
    }
    return valueLabel;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void setConfiguration(VLViewComponentXML configuration) {
    this.configuration = configuration;
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public VLViewComponentXML getConfiguration() {
    return configuration;
  }

  /**
   *
   * @return
   */
  @Override
  public PreferenceItemPresenterFactory getPresenter() {
    return presenter;
  }


  /**
   * @param presenter
   */
  @Override
  public void setPresenter(PreferenceItemPresenterFactory presenter) {
    this.presenter = presenter;
  }


  @Override
  public void setCurrentValue(List<IListFormValue> selected) {

  }


  @Override
  public IJSoaggerController getController() {
    return null;
  }


  @Override
  public void setController(IJSoaggerController controller) {

  }
}
