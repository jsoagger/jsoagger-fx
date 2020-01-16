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

package io.github.jsoagger.jfxcore.engine.components.form.fieldset.layout.tabed;




import java.util.ArrayList;
import java.util.List;

import javafx.css.PseudoClass;
import javafx.scene.layout.HBox;

/**
 *
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class FieldsetTopTabedItemsSelector extends HBox {

  private List<FieldsetTopTabedItem> items = new ArrayList<>();


  /**
   * Constructor
   */
  public FieldsetTopTabedItemsSelector() {}

  public List<FieldsetTopTabedItem> items(){
    return items;
  }

  /**
   * @param groupItem
   */
  public void addItem(FieldsetTopTabedItem groupItem) {
    items.add(groupItem);
    // NodeHelper.setHVGrow(groupItem);
    getChildren().add(groupItem);
    for(FieldsetTopTabedItem e: items) {
      e.pseudoClassStateChanged(PseudoClass.getPseudoClass("first"), false);
      e.pseudoClassStateChanged(PseudoClass.getPseudoClass("last"), false);
    }

    if (items.size() > 1) {
      int itemsize = items.size();
      items.get(0).pseudoClassStateChanged(PseudoClass.getPseudoClass("first"), true);
      items.get(itemsize - 1).pseudoClassStateChanged(PseudoClass.getPseudoClass("last"), true);
    }
  }


  /**
   * Select the item with the given index
   *
   * @param i
   */
  public void selectItem(int i) {
    try {
      FieldsetTopTabedItem item = items.get(i);
      item.select(true);
    } catch (Exception e) {
    }
  }


  public FieldsetTopTabedItem getItem(int index) {
    try {
      FieldsetTopTabedItem item = items.get(index);
      return item;
    } catch (Exception e) {
    }

    return null;
  }


  /**
   * Unselect all items.
   */
  public void unSelectAll() {
    for(FieldsetTopTabedItem e: items) {
      e.select(false);
    }
  }
}
