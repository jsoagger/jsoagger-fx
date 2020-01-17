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

package io.github.jsoagger.jfxcore.components.search.controller;


import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.engine.controller.utils.StandardViewUtils;
import com.google.gson.JsonObject;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;

/**
 * Can be used when searching for same type of objects. Results are displayed uniformly.
 * <p>
 * The search form is the same whatever the type/subtype selected.
 *
 * @author Ramilafananana  VONJISOA
 *
 */
public class UniformSearchController extends SearchController {

  SearchResultController searchResultController;
  String[] selectedTypes;

  /**
   * Constructor
   */
  public UniformSearchController() {
    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void process() {
    super.process();

    final ChangeListener<String> typeChangeListener = (a, b, c) -> {
      final String selectedTypesString = c;

      if (selectedTypesString != null) {
        // fullid of all selected types
        final String[] selectedTypes = selectedTypesString.split("#");
        updateFormToType(selectedTypes);
      }
    };

    // when selected type chand, we must load form associated to
    // this type if there is one
    // That is the purpose of this listener
    rootFormController.getSearchTypeAttribute().getOwner().currentInternalValueProperty().addListener(typeChangeListener);

    // select the first value if there is one
    rootFormController.getSearchTypeAttribute().getOwner().selectFirsEnumeratedValue();

    // if val is null, it means that no type have loaded
    // configuration problem
    Object val = rootFormController.getSearchTypeAttribute().getOwner().currentInternalValueProperty().get();
    if(val == null || "-".equals(val)) {
      System.out.println("[ WARNING ]Root form controller has selected no type, please verify"
          + " getInstanciableSubtypeOperation operation result.");
    }
  }

  @Override
  public void doSearch() {
    commitForm();

    final JsonObject cloneform = form.deepCopy();

    // types must be separated by#
    // in server side
    final String typestring = Stream.of(selectedTypes).map(i -> i.toString()).collect(Collectors.joining("#"));
    cloneform.addProperty("business.type", typestring);

    // may be need iteration class
    final String iterationClass = searchComponents.getPropertyValue("iterationClass");
    if (StringUtils.isNotBlank(iterationClass)) {
      cloneform.addProperty("iterationClass", iterationClass);
    }
    try {
      if (searchResultController == null) {
        if (getStructureContent() == null) {
          searchResultController = (SearchResultController) StandardViewUtils.forId(getRootStructure(), getSearchResultViewId(null));
        } else {
          searchResultController = (SearchResultController) StandardViewUtils.forId(getRootStructure(), getStructureContent(), getSearchResultViewId(null));
        }

        searchResultController.setParent(getParent());
      }

      searchResultController.doSearch(current.getSearchFormController(), cloneform);

      if (Platform.isFxApplicationThread()) {
        searchResultLayout.getChildren().clear();
        searchResultLayout.getChildren().add(searchResultController.processedView());
      } else {
        Platform.runLater(() -> {
          searchResultLayout.getChildren().clear();
          searchResultLayout.getChildren().add(searchResultController.processedView());
        });
      }
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Type and all subtypes of this type must have the same form. It is the reponsability of the
   * developper to handle visibility of each row.
   * <p>
   * All subtypes are displayed in same tab.
   *
   * @param type
   */
  protected void updateFormToType(String[] type) {
    selectedTypes = type;
    final String fullId = type[0];

    // get the real type
    final JsonObject query = new JsonObject();
    query.addProperty("fullId", fullId);
    query.addProperty("oid", fullId);
    final IOperation op = (IOperation) Services.getBean("PersistableLoadSimpleModelOperation");
    op.doOperation(query, res -> {

      final SingleResult sr = (SingleResult) res;
      final OperationData data = sr.getData();
      final String fullPath = (String) data.getAttributes().get("logicalPath");

      // ONE RESULT FOR EACH SELECTED SUBTYPES
      current = new SearchControllerWrapper(type, fullPath.split("/")[0], getConfiguration());

      // layout the current form
      current.getSearchFormController().setRootFormController(rootFormController);
      rootFormController.setAdditionalForm(current);
    }, ex -> {
      ex.printStackTrace();
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getSearchFormViewId(Map<String, Object> params) {
    return searchComponents.getPropertyValue("searchForm");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getSearchResultViewId(Map<String, Object> params) {
    return searchComponents.getPropertyValue("searchResult");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getSearchRootFormViewId(Map<String, Object> params) {
    return searchComponents.getPropertyValue("searchRootForm");
  }
}
