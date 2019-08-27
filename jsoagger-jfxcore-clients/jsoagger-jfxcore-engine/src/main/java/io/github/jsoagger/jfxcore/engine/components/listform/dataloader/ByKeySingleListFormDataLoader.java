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




import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.listform.IListFormDataLoader;
import io.github.jsoagger.jfxcore.engine.components.listform.IListFormValue;
import io.github.jsoagger.jfxcore.engine.components.listform.ListFormValue;
import io.github.jsoagger.jfxcore.engine.components.presenter.PreferenceItemPresenterFactory;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.model.EnumeratedValueAdapter;
import io.github.jsoagger.jfxcore.engine.model.EnumeratedValueModel;
import com.google.gson.JsonObject;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Load single data from its key. And display it inside label.
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class ByKeySingleListFormDataLoader implements IListFormDataLoader {

  VLViewComponentXML configuration;
  PreferenceItemPresenterFactory presenter;
  IJSoaggerController controller;

  // needs GetPreferenceValueOperation
  IOperation getPreferenceValueOperation;

  // needs SetPreferenceValueOperation
  IOperation setPreferenceValueOperation;

  // needs ListvaluesOperation
  IOperation listValueOperation;

  /**
   * Constructor
   */
  public ByKeySingleListFormDataLoader() {
    super();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public List<IListFormValue> getCurrentValue() {
    JsonObject query = new JsonObject();
    query.addProperty("key", presenter.getKey());

    // this should never be null
    // configuration error!!
    if(presenter.getKey() == null) {
      throw new RuntimeException("Key is mandatory for querying preference value, "
          + "please provide one in your configuration file");
    }

    List<IListFormValue> datas = new ArrayList<>();
    if (getPreferenceValueOperation != null) {
      getPreferenceValueOperation.doOperation(query, res -> {
        if(res != null)
          datas.addAll(toFormValue(((MultipleResult) res).getData()));
      });
    }

    return datas;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCurrentValue(List<IListFormValue> selected) {
    JsonObject query = new JsonObject();
    query.addProperty("key", presenter.getKey());

    StringBuffer b = new StringBuffer();
    Iterator<IListFormValue> it = selected.iterator();
    while(it.hasNext()){
      b.append(it.next().getSavedValue());
      if(it.hasNext()) {
        b.append(",");
      }
    }
    query.addProperty("value", b.toString());

    if (setPreferenceValueOperation != null) {
      setPreferenceValueOperation.doOperation(query, res -> {
      });
    }
  }


  private List<IListFormValue> toFormValue(List<OperationData> datas) {
    List<IListFormValue> ds = new ArrayList<>();
    for (OperationData d : datas) {
      ListFormValue lfv = new ListFormValue(null);
      lfv.setDisplayedValue((String) d.getAttributes().get("displayedValue"));
      lfv.setSavedValue((String) d.getAttributes().get("savedValue"));
      ds.add(lfv);
    }

    return ds;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public List<EnumeratedValueModel> getSelectableValues() {
    JsonObject query = new JsonObject();
    query.addProperty("enumerationKey", presenter.getListValuekey());
    query.addProperty("containerPath", ((AbstractViewController)getController()).getModelContainerPath());
    query.addProperty("containerOid", ((AbstractViewController)getController()).getModelContainerFullId());
    List<EnumeratedValueModel> datas = new ArrayList<>();
    if (listValueOperation != null) {
      listValueOperation.doOperation(query, res -> {
        List<EnumeratedValueModel> vals = EnumeratedValueAdapter.toEnumeratedValues((MultipleResult) res);
        datas.addAll(vals);
      });

    }
    return datas;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Node getDisplayNode(List<IListFormValue> values) {
    final Label valueLabel = new Label();
    valueLabel.setWrapText(true);
    valueLabel.setMaxWidth(100);
    valueLabel.getStyleClass().add("ep-list-form-data-value");

    if (values != null && !values.isEmpty()) {
      valueLabel.setText(values.get(0).getDisplayedValue());
    }
    return valueLabel;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDisplayText(List<IListFormValue> values) {
    String val = "";
    if (values != null && !values.isEmpty()) {
      StringBuffer b = new StringBuffer();
      Iterator<IListFormValue> it = values.iterator();
      while(it.hasNext()) {
        b.append(it.next().getDisplayedValue());
        if(it.hasNext()) {
          b.append(",");
        }
      }
      val = b.toString();
    }

    return val;
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
   *
   * @param presenter
   */
  @Override
  public void setPresenter(PreferenceItemPresenterFactory presenter) {
    this.presenter = presenter;
  }


  /**
   * @return the getPreferenceValueOperation
   */
  public IOperation getGetPreferenceValueOperation() {
    return getPreferenceValueOperation;
  }


  /**
   * @param getPreferenceValueOperation the getPreferenceValueOperation to set
   */
  public void setGetPreferenceValueOperation(IOperation getPreferenceValueOperation) {
    this.getPreferenceValueOperation = getPreferenceValueOperation;
  }


  /**
   * @return the setPreferenceValueOperation
   */
  public IOperation getSetPreferenceValueOperation() {
    return setPreferenceValueOperation;
  }


  /**
   * @param setPreferenceValueOperation the setPreferenceValueOperation to set
   */
  public void setSetPreferenceValueOperation(IOperation setPreferenceValueOperation) {
    this.setPreferenceValueOperation = setPreferenceValueOperation;
  }


  /**
   * @return the listValueOperation
   */
  public IOperation getListValueOperation() {
    return listValueOperation;
  }


  /**
   * @param listValueOperation the listValueOperation to set
   */
  public void setListValueOperation(IOperation listValueOperation) {
    this.listValueOperation = listValueOperation;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public IJSoaggerController getController() {
    return controller;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void setController(IJSoaggerController controller) {
    this.controller = controller;
  }
}
