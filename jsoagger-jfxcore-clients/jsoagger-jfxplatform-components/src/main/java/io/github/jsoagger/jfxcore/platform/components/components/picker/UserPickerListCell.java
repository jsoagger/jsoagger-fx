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



import io.github.jsoagger.jfxcore.engine.components.picker.PickerCell;
import io.github.jsoagger.jfxcore.engine.model.ObjectModel;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public class UserPickerListCell extends PickerCell<ObjectModel> {

  /**
   * Constructor
   */
  public UserPickerListCell() {
    super();
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  protected void updateItem(ObjectModel item, boolean empty) {
    super.updateItem(item, empty);
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public String getLabel() {
    return getItem().getName();
  }
}
