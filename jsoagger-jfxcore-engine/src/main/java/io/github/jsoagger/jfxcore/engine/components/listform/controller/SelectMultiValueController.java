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

package io.github.jsoagger.jfxcore.engine.components.listform.controller;



import java.util.ArrayList;
import java.util.List;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.client.utils.NodeHelper;
import io.github.jsoagger.jfxcore.engine.components.list.utils.FixedSizeListView;
import io.github.jsoagger.jfxcore.engine.components.listform.BooleanListFormCellFactory;
import io.github.jsoagger.jfxcore.engine.components.listform.IListFormCellPresenter;
import io.github.jsoagger.jfxcore.engine.components.listform.IListFormValue;
import io.github.jsoagger.jfxcore.engine.components.listform.ListFormValue;
import io.github.jsoagger.jfxcore.engine.components.presenter.PreferenceItemPresenterFactory;
import io.github.jsoagger.jfxcore.engine.controller.main.AbstractApplicationRunner;
import io.github.jsoagger.jfxcore.engine.controller.main.StandardController;
import io.github.jsoagger.jfxcore.engine.model.EnumeratedValueModel;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class SelectMultiValueController extends StandardController {


  /**
   * Constructor
   */
  public SelectMultiValueController() {
    super();
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  protected void process() {
    super.process();

    final FixedSizeListView<IListFormValue> listDataView = new FixedSizeListView<>();
    listDataView.getStyleClass().addAll("ep-fixed-size-listview,ep-fixed-size-listview-small,ep-preferences-list-view".split(","));
    listDataView.setCellFactory(param -> {
      final BooleanListFormCellFactory cell = (BooleanListFormCellFactory) Services.getBean("BooleanListFormCellFactory");
      cell.setConfiguration(getRootComponent());
      cell.setController(this);
      return cell;
    });

    SingleResult model = (SingleResult) getModel();
    OperationData data = model.getData();

    List<EnumeratedValueModel> selectableValue = (List<EnumeratedValueModel>) data.getMeta().get("selectableValue");
    List<IListFormValue> currentValues = (List<IListFormValue>) data.getMeta().get("currentValue");
    PreferenceItemPresenterFactory presenterFactory = (PreferenceItemPresenterFactory) data.getMeta().get("presenter");

    final List<IListFormValue> selected = toListForm(selectableValue, currentValues, presenterFactory);
    listDataView.getItems().addAll(selected);

    for (final Object value : listDataView.getItems()) {
      if (((IListFormValue) value).selectedProperty().get()) {
        selected.add((IListFormValue) value);
      }
    }

    StackPane p = new StackPane();

    if(AbstractApplicationRunner.isDesktop()) {
      //p.setStyle("-fx-padding:32");
    }

    p.setAlignment(Pos.TOP_CENTER);
    p.getChildren().add(listDataView);
    NodeHelper.setHVGrow(p);
    NodeHelper.setHVGrow(listDataView);
    processedView(p);
  }

  private List<IListFormValue> toListForm(List<EnumeratedValueModel> vals, List<IListFormValue> currentValues, PreferenceItemPresenterFactory presenterFactory) {
    List<IListFormValue> res = new ArrayList<>();
    for (EnumeratedValueModel mod : vals) {
      ListFormValue v = new ListFormValue(null);
      v.setDisplayedValue(mod.getValue());
      v.setSavedValue(mod.getSavedValue());
      v.setPreferenceItem((IListFormCellPresenter) presenterFactory);

      for (IListFormValue val : currentValues) {
        if (val.getSavedValue().equalsIgnoreCase(mod.getSavedValue())) {
          v.selectedProperty().set(true);
        }
      }

      res.add(v);
    }

    return res;
  }
}
