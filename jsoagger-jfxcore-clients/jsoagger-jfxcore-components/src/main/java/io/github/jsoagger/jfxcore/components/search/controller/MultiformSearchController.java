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

import com.google.gson.JsonObject;
import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.jfxcore.api.IViewResolver;
import io.github.jsoagger.jfxcore.api.services.Services;

import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class MultiformSearchController extends SearchController {

  private IViewResolver searchResultViewResolver;
  private IViewResolver searchFormViewResolver;


  /**
   * Constructor
   */
  public MultiformSearchController() {
    super();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected void process() {
    super.process();

    final ChangeListener<String> typeChangeListener = (a, b, c) -> {
      Task<Void> task = new Task<Void>() {

        @Override
        protected Void call() throws Exception {
          final String selectedTypesString = c;

          if (selectedTypesString != null) {
            final String[] selectedTypes = selectedTypesString.split("#");
            updateFormToType(selectedTypes);
          }
          return null;
        }
      };
      new Thread(task).start();
    };

    // when selected type change, we must load form associated to
    // this type if there is one
    // That is the purpose of this listener
    rootFormController.getSearchTypeAttribute().getOwner().currentInternalValueProperty().addListener(typeChangeListener);

    // select the first value if there is one
    rootFormController.getSearchTypeAttribute().getOwner().selectFirsEnumeratedValue();

    // if val is null, it means that no type have loaded
    // configuration problem
    Object val = rootFormController.getSearchTypeAttribute().getOwner().currentInternalValueProperty().get();
    if(val == null) {
      System.out.println("[WARNING ]Root form controller has selected no type, please verify"
          + " getInstanciableSubtypeOperation operation result.");
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
      // current = new SearchControllerWrapper(type,
      // fullPath.split("/")[0], getConfiguration());
      current = new SearchControllerWrapper(type, fullPath, getConfiguration());

      // layout the current form
      current.getSearchFormController().setRootFormController(rootFormController);
      rootFormController.setAdditionalForm(current);
    }, ex -> {
      ex.printStackTrace();
    });
  }


  /**
   * @return
   */
  public IViewResolver getSearchFormViewResolver() {
    return searchFormViewResolver;
  }


  /**
   * @param searchFormViewResolver
   */
  public void setSearchFormViewResolver(IViewResolver searchFormViewResolver) {
    this.searchFormViewResolver = searchFormViewResolver;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected String getSearchFormViewId(Map<String, Object> params) {
    String res = searchFormViewResolver.getViewName(getRootComponent(), params);
    return res;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected String getSearchResultViewId(Map<String, Object> params) {
    String res = searchResultViewResolver.getViewName(getRootComponent(), params);
    return res;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected String getSearchRootFormViewId(Map<String, Object> params) {
    return searchComponents.getPropertyValue("searchRootForm");
  }


  public IViewResolver getSearchResultViewResolver() {
    return searchResultViewResolver;
  }


  public void setSearchResultViewResolver(IViewResolver searchResultViewResolver) {
    this.searchResultViewResolver = searchResultViewResolver;
  }
}
