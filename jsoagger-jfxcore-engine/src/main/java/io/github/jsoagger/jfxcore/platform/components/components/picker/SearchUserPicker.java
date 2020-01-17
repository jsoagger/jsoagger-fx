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

package io.github.jsoagger.jfxcore.platform.components.components.picker;



import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.model.ObjectModel;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class SearchUserPicker {
  // public class SearchUserPicker extends SearchObjectPicker<ObjectModel> {

  private Function<String, List<ObjectModel>> searchFunction = null;
  private Function<Object, Object> okCallBack;


  /*-----------------------------------------------------------------------------
  | CONSTRUCTOR
  *=============================================================================*/
  /**
   * Constructor
   *
   * @param attributeConfig
   * @param view
   */
  public SearchUserPicker(AbstractViewController view, VLViewComponentXML configuration) {}


  /**
   * Real search porcessing
   *
   * @param searchTerm
   * @return
   */
  protected List<ObjectModel> doSearch(String searchTerm) {
    if (searchFunction != null) {
      return searchFunction.apply(searchTerm);
    } else {
      // final List<Account> persons =
      // UIServicesLocator.getPrincipalsService().getAccountByLoginLike(searchTerm);
      List<ObjectModel> persons = new ArrayList<>();
      return persons;
    }
  }


  /**
   * @param okCallBack
   */
  public void setOKCallBack(Function<Object, Object> okCallBack) {
    this.okCallBack = okCallBack;
  }


  /**
   * @param object
   */
  public void setOnSearch(Function<String, List<ObjectModel>> searchFunction) {
    this.searchFunction = searchFunction;
  }
}
