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

package io.github.jsoagger.jfxcore.engine.components.listform;




import java.util.List;
import java.util.function.Function;

import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.viewdefinition.json.xml.model.VLViewComponentXML;
import io.github.jsoagger.jfxcore.engine.components.list.impl.AbstractListCell;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ToggleGroup;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public interface IListFormCellPresenter extends IBuildable {

  default AbstractListCell getCell() {
    return null;
  }


  default void setCell(AbstractListCell cell) {

  }

  void setKey(String key);


  void setContext(String context);


  String getContext();


  String getKey();


  String getOwner();


  default void update(String value) {}


  default void setOnBack(Function<Void, Void> backFunction) {

  }


  default void setOnShow(Function<Node, Void> showFunction) {

  }


  default void setOnSave(Function<Object, Boolean> saveFunction) {

  }


  default IListFormDataLoader getDataLoader() {
    return null;
  }


  default void setDataLoader(IListFormDataLoader loader) {

  }


  default ObservableList<IListFormValue> getSelectedValues() {
    return FXCollections.observableArrayList();
  }


  default boolean isMultipleSection() {
    return false;
  }


  default void setMultipleSection(boolean multipleSection) {

  }


  default VLViewComponentXML getConfiguration() {
    return null;
  }


  default ToggleGroup getToggleGroup() {
    return null;
  }

  /**
   * Call server to udpdate the given value
   * 
   * @param selected
   */
  void processUpdate(List<IListFormValue> selected);
}
